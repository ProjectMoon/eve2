package eve.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eve.core.builtins.EveBoolean;
import eve.core.builtins.EveDouble;
import eve.core.builtins.EveFunction;
import eve.core.builtins.EveInteger;
import eve.core.builtins.EveString;
import eve.hooks.HookManager;
import eve.scope.ScopeManager;

public class EveObject {
	public enum EveType { INTEGER, BOOLEAN, DOUBLE, STRING, CUSTOM, PROTOTYPE, FUNCTION };
	
	//the type of this object.
	private EveType type;
	
	//base eve types.
	private Integer intValue;
	private String stringValue;
	private Double doubleValue;
	private Boolean booleanValue;
	private Function functionValue;
	
	private String typeName;
	
	//Properties of this object. Temp fields are deleted on scope exit.
	private Map<String, EveObject> fields = new HashMap<String, EveObject>();
	private Map<String, EveObject> tempFields = new HashMap<String, EveObject>();
	
	//internal object settings and state.
	private boolean cloneable = true;
	
	//object family support.
	private EveObject cloneParent;
	
	
	public EveObject() {
		//putField("self", this); //Does not seem to work as we want it to.
	}
	
	/**
	 * Copy constructor. Used for cloning.
	 * @param source The object to clone.
	 */
	public EveObject(EveObject source) {
		if (!source.isCloneable()) {
			throw new EveError("attempting to clone uncloneable prototype");
		}
				
		//Object is cloned to initially use references of this object to save memory.
		//Later, assignment statements will change the references for us.
		this.cloneParent = source;
		this.fields = new HashMap<String, EveObject>(source.fields); //a new map with the same references.
		
		//prototypes can only exist as base objects. any clone is immediately custom.
		if (source.getType() == EveType.PROTOTYPE) {
			this.setType(EveType.CUSTOM);
		}
		else {
			this.setType(source.getType());
		}
		
		this.setTypeName(source.getTypeName());
		this.cloneable = source.cloneable;
		this.intValue = source.intValue;
		this.functionValue = source.functionValue;
		this.stringValue = source.stringValue;
		this.doubleValue = source.doubleValue;
		this.booleanValue = source.booleanValue;
		
		
		HookManager.callCloneHooks(this);
		
		//onClone special function.
		if (source.hasField(SpecialFunctions.ON_CLONE)) {
			source.getField(SpecialFunctions.ON_CLONE).putTempField("self", this);
			source.getField(SpecialFunctions.ON_CLONE).invoke(this);
		}
	}
	
	public EveObject(Integer i) {
		this(EveInteger.getPrototype());
		setIntValue(i);
	}
	
	public EveObject(Integer i, boolean clone) {
		this();
		setIntValue(i);
	}
	
	public EveObject(String s) {
		this(EveString.getPrototype());
		setStringValue(s);
	}
	
	public EveObject(String s, boolean clone) {
		this();
		setStringValue(s);
	}
	
	public EveObject(Double d) {
		this(EveDouble.getPrototype());
		setDoubleValue(d);
	}
	
	public EveObject(Double d, boolean clone) {
		this();
		setDoubleValue(d);
	}
	
	public EveObject(Function func) {
		this(EveFunction.getPrototype());
		setFunctionValue(func);
	}
	
	public EveObject(Function func, boolean clone) {
		this();
		setFunctionValue(func);
	}
	
	public EveObject(Boolean b) {
		this(EveBoolean.getPrototype());
		setBooleanValue(b);
	}
	
	public EveObject(Boolean b, boolean clone) {
		this();
		setBooleanValue(b);
	}
		
	public static EveObject customType(String typeName) {
		EveObject eo = new EveObject();
		eo.setType(EveType.CUSTOM);
		eo.setTypeName(typeName);
		return eo;
	}
	
	public static EveObject prototypeType(String typeName) {
		EveObject eo = new EveObject();
		eo.setType(EveType.PROTOTYPE);
		eo.setTypeName(typeName);
		return eo;
	}
	
	public static EveObject globalType() {
		EveObject eo = new EveObject();
		eo.setType(EveType.CUSTOM);
		eo.setTypeName("global");
		eo = eo.eveClone(); //even global is cloned once.
		eo.cloneable = false;
		return eo;
	}
	
	public boolean isCloneable() {
		return cloneable;
	}
	
	public void setIntValue(Integer intValue) {
		this.setType(EveType.INTEGER);
		this.intValue = intValue;
	}
	
	public Integer getIntValue() {
		if (this.getType() != EveType.INTEGER) {
			throw new EveError(this + " is not an int!");
		}
		return intValue;
	}
	
	public void setStringValue(String stringValue) {
		this.setType(EveType.STRING);
		this.stringValue = stringValue;
	}
	
	public String getStringValue() {
		if (this.getType() != EveType.STRING) {
			throw new EveError(this + " is not a string!");
		}
		return stringValue;
	}
	
	public void setDoubleValue(Double d) {
		this.setType(EveType.DOUBLE);
		this.doubleValue = d;
	}
	
	public Double getDoubleValue() {
		if (this.getType() != EveType.DOUBLE){ 
			throw new EveError(this + " is not a double!");
		}
		return doubleValue;
	}
	
	public void setBooleanValue(Boolean booleanValue) {
		this.setType(EveType.BOOLEAN);
		this.booleanValue = booleanValue;
	}
	
	public Boolean getBooleanValue() {
		if (this.getType() != EveType.BOOLEAN){
			throw new EveError(this + " is not a boolean!");
		}
		return booleanValue;
	}
	
	public void setFunctionValue(Function functionValue) {
		this.setType(EveType.FUNCTION);
		this.functionValue = functionValue;
	}

	public Function getFunctionValue() {
		if (this.getType() != EveType.FUNCTION) {
			throw new EveError(this + " is not a function!");
		}
		return functionValue;
	}

	public void setType(EveType type) {
		this.type = type;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	
	public boolean hasField(String field) {
		return this.fields.containsKey(field);
	}
	
	public EveType getType() {
		return this.type;
	}
	
	public String getTypeName() {
		switch (getType()) {
			case INTEGER:
				return "int";
			case DOUBLE:
				return "double";
			case BOOLEAN:
				return "boolean";
			case STRING:
				return "string";
			case FUNCTION:
				return "function";
			case CUSTOM:
				return this.typeName;
			case PROTOTYPE:
				return this.typeName;
		}
		
		return "unknown type";
	}
	
	public String toString() {
		//Utilize custom toString() if present.
		if (hasField("toString")) {
			EveObject toString = this.getField("toString");
			if (toString.getType() == EveType.FUNCTION) {
				EveObject res = toString.invoke();
				if (res != null) {
					return res.getStringValue();
				}
			}
		}
		
		//Otherwise, default.
		switch (getType()) {
			case INTEGER:
				return this.getIntValue().toString();
			case DOUBLE:
				return this.getDoubleValue().toString();
			case BOOLEAN:
				return this.getBooleanValue().toString();
			case STRING:
				return this.getStringValue();
			case FUNCTION:
				return this.getFunctionValue().toString();
			case CUSTOM:
				return "[custom " + this.getTypeName() + "]";
			case PROTOTYPE:
				return "[prototype " + this.getTypeName() + "]";
		}
		
		return "[unknown]";
	}
	
	public EveObject invoke() {
		return invoke0(null);
	}
	
	public EveObject invoke(EveObject ... actualParameters) {
		return invoke0(Arrays.asList(actualParameters));
	}
	
	public EveObject invoke(List<EveObject> actualParameters) {
		return invoke0(actualParameters);
	}
	
	private EveObject invoke0(List<EveObject> actualParameters) {
		if (this.getType() != EveType.FUNCTION) {
			throw new EveError(this + " is not a function.");
		}
		
		Function func = this.getFunctionValue();
		
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
		EveObject retval = func.execute();
		ScopeManager.popScope();

		return retval;
	}
	
	/**
	 * Clone this EveObject. All properties will be shallowly replicated.
	 * This method also handles managing of the object's family.
	 * @return A clone of this EveObject.
	 */
	public EveObject eveClone() {
		return new EveObject(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((booleanValue == null) ? 0 : booleanValue.hashCode());
		result = prime * result + (cloneable ? 1231 : 1237);
		result = prime * result
				+ ((doubleValue == null) ? 0 : doubleValue.hashCode());
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result
				+ ((functionValue == null) ? 0 : functionValue.hashCode());
		result = prime * result
				+ ((intValue == null) ? 0 : intValue.hashCode());
		result = prime * result
				+ ((stringValue == null) ? 0 : stringValue.hashCode());
		result = prime * result
				+ ((tempFields == null) ? 0 : tempFields.hashCode());
		result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
		result = prime * result
				+ ((getTypeName() == null) ? 0 : getTypeName().hashCode());
		return result;
	}

	/**
	 * Enables boolean comparison of Eve objects.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		
		if (!(o instanceof EveObject)) {
			return false;
		}
		
		EveObject other = (EveObject)o;
		
		//All of the possible comparison types:
		//boolean, string, int, double, function, prototype, custom.
		//int-double and double-int are also type coerced for this.
		//no other type coercion exists for equality checking.
		//for custom types, they must define their own equals function.
		if (this.getType() == EveType.BOOLEAN && other.getType() == EveType.BOOLEAN) {
			return this.getBooleanValue().equals(other.getBooleanValue()); 
		}
		else if (this.getType() == EveType.INTEGER && other.getType() == EveType.INTEGER) {
			return this.getIntValue().equals(other.getIntValue());
		}
		else if (this.getType() == EveType.DOUBLE && other.getType() == EveType.DOUBLE){
			return this.getDoubleValue().equals(other.getDoubleValue());
		}
		else if (this.getType() == EveType.STRING && other.getType() == EveType.STRING){
			return this.getStringValue().equals(other.getStringValue());
		}
		else if (this.getType() == EveType.FUNCTION && other.getType() == EveType.FUNCTION) {
			return functionEquals(other);
		}
		else if (this.getType() == EveType.PROTOTYPE && other.getType() == EveType.PROTOTYPE) {
			return prototypeEquals(other);
		}
		else if (this.getType() == EveType.CUSTOM && other.getType() == EveType.CUSTOM) {
			return customTypeEquals(other);
		}
		//Type coerced equals: only for int and double.
		else if (this.getType() == EveType.INTEGER && other.getType() == EveType.DOUBLE) {
			return this.getIntValue().equals(other.getDoubleValue());
		}
		else if (this.getType() == EveType.DOUBLE && other.getType() == EveType.INTEGER){
			return this.getDoubleValue().equals(other.getIntValue());
		}
		else {
			//the two are not comparable.
			return false;
		}
	}

	/**
	 * Determine equality between two functions. Equality is determined first by
	 * reference since when we clone, the runtime simply copies references. If
	 * that fails, we must try the significantly more complex function analysis.
	 * @param other
	 * @return
	 */
	private boolean functionEquals(EveObject other) {
		if (this.getFunctionValue() == other.getFunctionValue()) {
			return true;		
		}
		else {
			//function equality analysis is defined in EveFunction .equals
			return this.getFunctionValue().equals(other.getFunctionValue());
		}
	}
	
	private boolean prototypeEquals(EveObject other) {
		return true;
	}
	
	private boolean customTypeEquals(EveObject other) {
		return true;
	}
}
