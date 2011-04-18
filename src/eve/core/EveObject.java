package eve.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import eve.statements.EveStatement;

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
	
	//internal object settings and state.
	private boolean cloneable = true;
	private Queue<EveStatement> code = new LinkedList<EveStatement>();
	
	public EveObject() {}
	
	public EveObject(int i) {
		setIntValue(i);
	}
	
	public EveObject(String s) {
		setStringValue(s);
	}
	
	public static EveObject customEveType(String typeName) {
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
	
	public EveObject getField(String name) {
		return this.fields.get(name);
	}
	
	public void setCode(Queue<EveStatement> code) {
		this.code = code;
	}

	public Queue<EveStatement> getCode() {
		return code;
	}
	
	public void addCode(EveStatement statement) {
		this.code.add(statement);
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
}
