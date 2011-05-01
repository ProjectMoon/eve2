package eve.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import eve.core.builtins.EveBoolean;
import eve.core.builtins.EveDouble;
import eve.core.builtins.EveFunction;
import eve.core.builtins.EveGlobal;
import eve.core.builtins.EveInteger;
import eve.core.builtins.EveJava;
import eve.core.builtins.EveList;
import eve.core.builtins.EveObjectPrototype;
import eve.core.builtins.EveString;
import eve.hooks.HookManager;
import eve.scope.ScopeManager;

public class EveObject {
	public enum EveType { INTEGER, BOOLEAN, DOUBLE, STRING, CUSTOM, PROTOTYPE, FUNCTION, LIST, JAVA };
	
	//the type of this object.
	private EveType type;
	
	//base eve types.
	private Integer intValue;
	private String stringValue;
	private Double doubleValue;
	private Boolean booleanValue;
	private Function functionValue;
	private Object javaValue;
	private TreeMap<Integer, EveObject> listValues;
	
	private String typeName;
	
	//Properties of this object. Temp fields are deleted on scope exit.
	private Map<String, EveObject> fields = new HashMap<String, EveObject>();
	private Map<String, EveObject> tempFields = new HashMap<String, EveObject>();
	
	//internal object settings and state.
	private boolean cloneable = true;
	private boolean isMarkedForClone = false; //set to true by eveClone and used to detect modifications.
	
	//object family support.
	private EveObject cloneParent;
	
	/**
	 * The base constructor. Creates an EveObject with no properties set.
	 */
	public EveObject() {}
	
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
		this.tempFields = new HashMap<String, EveObject>(source.tempFields); //a new map with the same references.
		
		//prototypes can only exist as base objects. any clone is immediately custom.
		if (source.getType() == EveType.PROTOTYPE) {
			this.setType(EveType.CUSTOM);
		}
		else {
			this.setType(source.getType());
		}
		
		//mark all fields as cloned recursively, so that we will know to clone them later
		//when modifications come in
		this.markFieldsForClone();
		this.isMarkedForClone = false;
		
		this.setTypeName(source.getTypeName());
		this.cloneable = source.cloneable;
		this.intValue = source.intValue;
		this.functionValue = source.functionValue;
		this.stringValue = source.stringValue;
		this.doubleValue = source.doubleValue;
		this.booleanValue = source.booleanValue;
		this.listValues = (source.listValues != null) ? new TreeMap<Integer, EveObject>(source.listValues) : null;		
		
		HookManager.callCloneHooks(this);
		
		//onClone special function.
		if (source.hasField(SpecialFunctions.ON_CLONE)) {
			source.getField(SpecialFunctions.ON_CLONE).putTempField("self", source);
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
	
	public EveObject(List<EveObject> l) {
		this(EveList.getPrototype());
		setListValue(l);
	}
	
	public EveObject(List<EveObject> l, boolean clone) {
		this();
		setListValue(l);
	}
	
	public EveObject(char c) {
		this(EveString.getPrototype());
		setStringValue(Character.toString(c));
	}
	
	public EveObject(char c, boolean clone) {
		this();
		setStringValue(Character.toString(c));
	}

	public static EveObject globalType() {
		EveObject global = new EveObject(EveGlobal.getPrototype());
		
		global.putField(EveObjectPrototype.getPrototype().getTypeName(), EveObjectPrototype.getPrototype());
		global.putField(EveInteger.getPrototype().getTypeName(), EveInteger.getPrototype());
		global.putField(EveString.getPrototype().getTypeName(), EveString.getPrototype());
		global.putField(EveDouble.getPrototype().getTypeName(), EveDouble.getPrototype());
		global.putField(EveBoolean.getPrototype().getTypeName(), EveBoolean.getPrototype());
		global.putField(EveFunction.getPrototype().getTypeName(), EveFunction.getPrototype());
		global.putField(EveList.getPrototype().getTypeName(), EveList.getPrototype());
		global.putField(EveJava.getPrototype().getTypeName(), EveJava.getPrototype());
		
		return global;
	}
	
	public static EveObject javaType(Object o) {
		EveObject eo = new EveObject(EveJava.getPrototype());
		eo.setType(EveType.JAVA);
		eo.setTypeName(o.getClass().getName());
		eo.setObjectValue(o);
		return eo;
	}
		
	public static EveObject customType(String typeName) {
		EveObject eo = new EveObject(EveObjectPrototype.getPrototype());
		eo.setType(EveType.CUSTOM);
		eo.setTypeName(typeName);
		return eo;
	}
	
	public static EveObject prototypeType(String typeName) {
		EveObject eo = new EveObject(EveObjectPrototype.getPrototype());
		eo.setType(EveType.PROTOTYPE);
		eo.setTypeName(typeName);
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

	public void setListValue(List<EveObject> listValue) {
		this.setType(EveType.LIST);
		this.listValues = new TreeMap<Integer, EveObject>();
		
		for (int c = 0; c < listValue.size(); c++) {
			this.listValues.put(c, listValue.get(c));
		}
	}

	public List<EveObject> getListValue() {
		if (this.getType() != EveType.LIST) {
			throw new EveError(this + " is not a list!");
		}
		
		List<EveObject> results = new ArrayList<EveObject>(this.listValues.size());
		for (Map.Entry<Integer, EveObject> entry : this.listValues.entrySet()) {
			results.add(entry.getValue());
		}
		
		return results;
	}
	
	public Map<Integer, EveObject> getListMap() {
		if (this.getType() != EveType.LIST){
			throw new EveError(this + " is not a list!");
		}
		
		return this.listValues;
	}
	
	public void setObjectValue(Object objectValue) {
		this.javaValue = objectValue;
	}

	public Object getJavaValue() {
		return javaValue;
	}
	
	/**
	 * Returns the value of this object based on its type.
	 * @return
	 */
	public Object getObjectValue() {
		switch (getType()) {
		case INTEGER:
			return this.getIntValue();
		case DOUBLE:
			return this.getDoubleValue();
		case BOOLEAN:
			return this.getBooleanValue();
		case STRING:
			return this.getStringValue();
		case FUNCTION:
			return this.getFunctionValue();
		case LIST:
			return this.getListValue();
		case CUSTOM:
			return this;
		case PROTOTYPE:
			return this;
		case JAVA:
			return this.getJavaValue();
		}
	
		throw new EveError("unrecognized type " + getType() + " for getObjectValue()");		
	}

	/**
	 * Gets an indexed property. Only works on strings and lists. For
	 * strings, it returns the character at the specified index. For
	 * lists, it returns the object at the specified index.
	 * @param index The index to get.
	 * @return An EveObject.
	 */
	public EveObject getIndexedProperty(int index) {
		if (this.getType() == EveType.STRING) {
			Character c = this.getStringValue().charAt(index);
			return new EveObject(c.toString());
		}
		else if (this.getType() == EveType.LIST) {
			return this.listValues.get(index);
		}
		else {
			throw new EveError("can't use indexer access on non-indexed property");
		}
	}
	
	public void setIndexedProperty(int index, EveObject eo) {
		if (this.getType() == EveType.STRING) {
			if (eo.getType() != EveType.STRING) {
				throw new EveError("can't insert a non-string into a string");
			}
			if (eo.getStringValue().length() > 1) {
				throw new EveError("can only insert one character at index " + index);
			}
			
			String str = this.getStringValue();
			String firstPart = str.substring(0, index - 1);
			String lastPart = str.substring(index, str.length());
			this.setStringValue(firstPart + eo.getStringValue() + lastPart);
		}
		else if (this.getType() == EveType.LIST) {
			this.listValues.put(index, eo);
		}
		else {
			throw new EveError("can't use indexer access on non-indexed property");
		}
	}
	
	public boolean isIndexed() {
		return this.getType() == EveType.LIST || this.getType() == EveType.STRING;
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
		if (this.isMarkedForClone()) {
			
		}
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
			case LIST:
				return "list";
			case CUSTOM:
				return this.typeName;
			case PROTOTYPE:
				return this.typeName;
			case JAVA:
				return this.typeName;
		}
		
		throw new EveError("unrecognized type " + getType() + " for getTypeName()");
	}
	
	public Class<?> getTypeClass() {
		switch (getType()) {
		case INTEGER:
			return int.class;
		case STRING:
			return String.class;
		case BOOLEAN:
			return boolean.class;
		case DOUBLE:
			return double.class;
		case LIST:
			return List.class;
		case CUSTOM:
			return EveObject.class;
		case FUNCTION:
			return Function.class;
		case JAVA:
			return this.getJavaValue().getClass();
		}
		
		throw new EveError("unrecognized type " + getType() + " for getTypeClass()");
	}
	
	public void setMarkedForClone(boolean markedForClone) {
		this.isMarkedForClone = markedForClone;
	}

	public boolean isMarkedForClone() {
		return isMarkedForClone;
	}

	public String toString() {
		//Utilize custom toString() if present.
		if (hasField("toString")) {
			EveObject toString = this.getField("toString");
			if (toString.getType() == EveType.FUNCTION) {
				toString.putTempField("self", this);
				EveObject res = toString.invoke();
				if (res != null) {
					//prevents an infinite recursion of toString() calls
					//because of the way NativeHelper.mapJavaMethods works.
					if (res.getType() != EveType.JAVA) {
						return res.toString();
					}
					else {
						return res.getJavaValue().toString();
					}
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
			case LIST:
				return this.getListValue().toString();
			case CUSTOM:
				return "[custom " + this.getTypeName() + "]";
			case PROTOTYPE:
				return "[prototype " + this.getTypeName() + "]";
			case JAVA:
				return this.getJavaValue().toString();
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
			if (!func.isVarargs() && actualParameters.size() != func.getParameters().size()) {
				throw new EveError(this + " expects " + func.getParameters().size() + " arguments. " + actualParameters.size() + " were passed."); 
			}
			else {
				//var-args validation: need AT LEAST the right amount of params.
				if (!func.isVarargs() && actualParameters.size() < func.getParameters().size()) {
					throw new EveError(this + " expects " + func.getParameters().size() + " arguments. " + actualParameters.size() + " were passed."); 
				}
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
				if (func.isVarargs() && c == func.getVarargsIndex()) {
					//everything from here on out is a list of var-args.
					EveObject varargs = new EveObject(new ArrayList<EveObject>(0));
					for (int v = c; v < actualParameters.size(); v++) {
						varargs.setIndexedProperty(v - c, actualParameters.get(v));
					}
					
					if (func.isClosure()) {
						this.putField(func.getParameters().get(c), varargs);
					}
					else {
						this.putTempField(func.getParameters().get(c), varargs);
					}
					
					break;
				}
				else {
					//normal parameter handling
					if (func.isClosure()) {
						this.putField(func.getParameters().get(c), actualParameters.get(c));
					}
					else {
						this.putTempField(func.getParameters().get(c), actualParameters.get(c));
					}
				}
			}
		}
				
		if (func.isClosure()) {
			ScopeManager.setClosureStack(func.getClosureStack());
		}
				
		//switch to function scope and run.
		ScopeManager.pushScope(this);
		EveObject retval = func.execute();
			
		//do we have a closure?
		if (retval != null) {
			retval.recursePossibleClosures();
		}
		
		if (func.isClosure()) {
			ScopeManager.setClosureStack(null);
		}
		
		ScopeManager.popScope();
		
		return retval;
	}
	
	private void recursePossibleClosures() {
		//first, this one.
		if (this.getType() == EveType.FUNCTION) {
			Function func = this.getFunctionValue();
			if (func.isPossibleClosure()) {
				func.setClosure(true);
				func.setClosureStack(ScopeManager.createClosureStack());
			}
		}
		
		//and now all its fields (and their fields)
		for (EveObject field : getFields().values()) {
			if (field.getType() == EveType.FUNCTION) {
				field.recursePossibleClosures();
			}
		}
	}
	
	/**
	 * Clone this EveObject. All properties will be shallowly replicated.
	 * This method also handles managing of the object's family.
	 * @return A clone of this EveObject.
	 */
	public EveObject eveClone() {
		return new EveObject(this);
	}
	
	private void markFieldsForClone() {
		Set<Map.Entry<String, EveObject>> entries = this.fields.entrySet();
		
		for (Map.Entry<String, EveObject> entry : entries) {
			entry.getValue().setMarkedForClone(true);
			entry.getValue().markFieldsForClone();
		}
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

	public void transferTempFields() {
		for (Map.Entry<String, EveObject> entry : this.tempFields.entrySet()) {
			this.putField(entry.getKey(), entry.getValue());
		}
		
		this.tempFields.clear();
		this.markFieldsForClone();
	}
}
