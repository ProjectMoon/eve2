package eve.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import eve.interpreter.Interpreter;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;
import eve.statements.ReturnStatement;
import eve.statements.expressions.ExpressionStatement;

public class EveObject {
	public enum EveType { INTEGER, STRING, CUSTOM, FUNCTION };
	
	//the type of this object.
	private EveType type;
	
	//base eve types.
	private int intValue;
	private String stringValue;
	private EveFunction functionValue;
	
	//user-defined type specific.
	private String typeName;
	
	//Everything is an object.
	private Map<String, EveObject> fields = new HashMap<String, EveObject>();
	private Map<String, EveObject> tempFields = new HashMap<String, EveObject>();
	
	//internal object settings and state.
	private boolean cloneable = true;
	
	public EveObject() {}
	
	public EveObject(int i) {
		setIntValue(i);
	}
	
	public EveObject(String s) {
		setStringValue(s);
	}
	
	public static EveObject customType(String typeName) {
		EveObject eo = new EveObject();
		eo.type = EveType.CUSTOM;
		eo.typeName = typeName;
		return eo;
	}
	
	public static EveObject globalType() {
		EveObject eo = new EveObject();
		eo.type = EveType.CUSTOM;
		eo.typeName = "global";
		eo.cloneable = false;
		return eo;
	}
	
	public boolean isCloneable() {
		return cloneable;
	}
	
	public void setIntValue(int intValue) {
		this.type = EveType.INTEGER;
		this.intValue = intValue;
	}
	
	public int getIntValue() {
		if (this.type != EveType.INTEGER) {
			throw new EveError(this + " is not an int!");
		}
		return intValue;
	}
	
	public void setStringValue(String stringValue) {
		this.type = EveType.STRING;
		this.stringValue = stringValue;
	}
	
	public String getStringValue() {
		if (this.type != EveType.STRING) {
			throw new EveError(this + " is not a string!");
		}
		return stringValue;
	}
	
	public void setFunctionValue(EveFunction functionValue) {
		this.type = EveType.FUNCTION;
		this.functionValue = functionValue;
	}

	public EveFunction getFunctionValue() {
		if (this.type != EveType.FUNCTION) {
			throw new EveError(this + " is not a function!");
		}
		return functionValue;
	}

	public void setFields(Map<String, EveObject> fields) {
		this.fields = fields;
	}	

	public Map<String, EveObject> getFields() {
		return fields;
	}
	
	public void putField(String name, EveObject eo) {
		this.fields.put(name, eo);
	}
	
	public void putTempField(String name, EveObject eo) {
		this.tempFields.put(name, eo);
	}
	
	public EveObject getField(String name) {
		EveObject eo = this.fields.get(name);
		
		//non-existent? try temp fields.
		if (eo == null) {
			eo = this.tempFields.get(name);
		}
		
		return eo;
	}
	
	/**
	 * Called by ScopeManager.
	 */
	public void deleteTempFields() {
		this.tempFields.clear();
	}
	
	public EveType getType() {
		return this.type;
	}
	
	public String getTypeName() {
		switch (type) {
			case INTEGER:
				return "int";
			case STRING:
				return "string";
			case FUNCTION:
				return "function";
			case CUSTOM:
				return this.typeName;
		}
		
		return "undefined";
	}
	
	public String toString() {
		switch (type) {
		case INTEGER:
			return new Integer(this.getIntValue()).toString();
		case STRING:
			return this.getStringValue();
		case FUNCTION:
			return "[function]";
		case CUSTOM:
			return "[custom type " + this.typeName + "]";
		}
		
		return "undefined";
	}
	
	public EveObject invoke() {
		return invoke(null);
	}
	
	public EveObject invoke(List<EveObject> actualParameters) {
		if (this.getType() != EveType.FUNCTION) {
			throw new EveError(this + " is not a function.");
		}
		
		EveFunction func = this.getFunctionValue();
		
		//make sure parameter lengths match up.
		if (actualParameters != null) {
			if (actualParameters.size() != func.getParameters().size()) {
				throw new EveError(this + " expects " + func.getParameters().size() + " arguments. " + actualParameters.size() + " were passed."); 
			}
		}
		else {
			if (func.getParameters().size() > 0) {
				throw new EveError(this + " requires " + func.getParameters().size() + " arguments. none were passed.");
			}
		}
		
		//Copy function parameters as temp fields.
		if (actualParameters != null) {
			for (int c = 0; c < actualParameters.size(); c++) {
				this.putTempField(func.getParameters().get(c), actualParameters.get(c));
			}
		}
		
		//switch to function scope and run.
		ScopeManager.pushScope(this);
		EveObject retval = Interpreter.executeStatements(func.getStatements());
		ScopeManager.popScope();

		return retval;
	}
	
	public EveObject eveClone() {
		if (!this.isCloneable()) {
			return null;
		}
		
		EveObject clone = new EveObject();
		
		//Object is cloned to initially use references of this object to save memory.
		//Later, assignment statements will change the references for us.
		clone.fields = this.fields;
		clone.type = this.type;
		clone.typeName = this.typeName;
		clonePrimitives(clone);
		clone.cloneable = this.cloneable;
		
		return clone;
	}
	
	private void clonePrimitives(EveObject clone) {
		clone.intValue = this.intValue;
		clone.functionValue = this.functionValue;
		clone.stringValue = this.stringValue;		
	}
}
