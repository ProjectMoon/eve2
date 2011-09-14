package eve.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import eve.core.builtins.BuiltinCommons;
import eve.eji.DynamicField;
import eve.hooks.HookManager;
import eve.scope.ScopeManager;

public class EveObject {
	public enum EveType { INTEGER, BOOLEAN, DOUBLE, STRING, CUSTOM, PROTOTYPE, FUNCTION, LIST, JAVA, NULL, SCOPE };
	public static final String WITH_STATEMENT_TYPENAME = "with-statement";
	
	//the type and type name of this object.
	//the type is an internal representation, while the type name is what is
	//displayed to the user.
	private EveType type;
	private String typeName;
	
	//custom toString without making a toString field.
	//useful for EJI.
	private String stringRepresentation = null;
	
	//base eve types.
	private Integer intValue;
	private String stringValue;
	private Double doubleValue;
	private Boolean booleanValue;
	private Function functionValue;
	private Object javaValue;
	private TreeMap<Integer, EveObject> listValues;
	
	//Properties of this object. Temp fields are deleted on scope exit.
	private Map<String, EveObject> fields = new TreeMap<String, EveObject>();
	private Map<String, EveObject> tempFields = new TreeMap<String, EveObject>();
	
	//internal object settings and state.
	private boolean cloneable = true;
	private boolean markedForClone = false;
	private EveObject objectParent = null;
	
	 //for int-based property access, e.g. myobj[0] or myobj["0"]
	private EveObject indexedAccessor;
	private EveObject indexedMutator;
	
	//freeze and seal support
	private boolean isFrozen = false;
	private boolean isSealed = false;
	
	//object family support.
	private EveObject cloneParent;
	
	/**
	 * The base constructor. Creates an EveObject with no properties set.
	 */
	public EveObject() {}
	
	public EveObject(EveObject source) {
		this(source, true);
	}
	
	/**
	 * Copy constructor. Used for cloning.
	 * @param source The object to clone.
	 */
	private EveObject(EveObject source, boolean onClone) {
		if (source == null) {
			throw new EveError("cannot clone from null.");
		}
		
		if (!source.isCloneable()) {
			throw new EveError("attempting to clone uncloneable object");
		}
				
		//Object is cloned to initially use references of this object to save memory.
		//Later, assignment statements and automated deep cloning will change the references
		//for us.
		this.cloneParent = source;
		this.fields = new TreeMap<String, EveObject>(source.fields); //a new map with the same references.
		this.tempFields = new TreeMap<String, EveObject>(source.tempFields); //a new map with the same references.
		
		this.setType(source.getType());
		this.setTypeName(source.getTypeName());
		
		this.cloneable = source.cloneable;
		this.objectParent = source.objectParent;
		
		this.intValue = source.intValue;
		this.functionValue = source.functionValue;
		this.stringValue = source.stringValue;
		this.doubleValue = source.doubleValue;
		this.booleanValue = source.booleanValue;
		this.listValues = (source.listValues != null) ? new TreeMap<Integer, EveObject>(source.listValues) : null;
		
		this.indexedAccessor = source.indexedAccessor;
		this.indexedMutator = source.indexedMutator;
		
		HookManager.callCloneHooks(this);
		
		//onClone special function.
		if (onClone && source.hasField(SpecialFunctions.ON_CLONE)) {
			source.getField(SpecialFunctions.ON_CLONE).putTempField("self", source);
			source.getField(SpecialFunctions.ON_CLONE).invoke(this);
		}
		
		//mark fields for clone. this means that we deep clone as necessary for the new fields.
		markFieldsForClone();
	}
	
	public EveObject(Integer i) {
		this(BuiltinCommons.getType("int"));
		setIntValue(i);
	}
	
	public EveObject(Integer i, boolean clone) {
		this();
		setIntValue(i);
	}
	
	public EveObject(Integer i, EveObject prototype) {
		this(prototype);
		setIntValue(i);
	}
	
	public EveObject(String s) {
		this(BuiltinCommons.getType("string").__create(new EveObject(s, false))); //need better option than no-cloning...
		setStringValue(s);
	}
	
	public EveObject(String s, boolean clone) {
		this();
		setStringValue(s);
	}
	
	public EveObject(String s, EveObject prototype) {
		this(prototype);
		setStringValue(s);
	}
	
	public EveObject(Double d) {
		this(BuiltinCommons.getType("double"));
		setDoubleValue(d);
	}
	
	public EveObject(Double d, boolean clone) {
		this();
		setDoubleValue(d);
	}
	
	public EveObject(Double d, EveObject prototype) {
		this(prototype);
		setDoubleValue(d);
	}
	
	public EveObject(Function func) {
		this(BuiltinCommons.getType("function"));
		setFunctionValue(func);
	}
	
	public EveObject(Function func, boolean clone) {
		this();
		setFunctionValue(func);
	}
	
	public EveObject(Function func, EveObject prototype) {
		this(prototype);
		setFunctionValue(func);
	}
	
	public EveObject(Boolean b) {
		this(BuiltinCommons.getType("bool"));
		setBooleanValue(b);
	}
	
	public EveObject(Boolean b, boolean clone) {
		this();
		setBooleanValue(b);
	}
	
	public EveObject(Boolean b, EveObject prototype) {
		this(prototype);
		setBooleanValue(b);
	}
	
	public EveObject(List<EveObject> l) {
		this(BuiltinCommons.getType("list"));
		setListValue(l);
	}
	
	public EveObject(List<EveObject> l, boolean clone) {
		this();
		setListValue(l);
	}
	
	public EveObject(List<EveObject> l, EveObject prototype) {
		this(prototype);
		setListValue(l);
	}
	
	public EveObject(char c) {
		this(BuiltinCommons.getType("string"));
		setStringValue(c);
	}
	
	public EveObject(char c, boolean clone) {
		this();
		setStringValue(c);
	}
	
	public EveObject(char c, EveObject prototype) {
		this(prototype);
		setStringValue(c);
	}

	public static EveObject globalType() {
		EveObject global = new EveObject(BuiltinCommons.getType("global"));
		global.setType(EveType.SCOPE);
		global.setTypeName("scope(global)");
		return global;
	}
	
	public static EveObject javaType(Object o) {
		EveObject eo = new EveObject(BuiltinCommons.getType("java"));
		eo.setType(EveType.JAVA);
		eo.setTypeName(o.getClass().getName());
		eo.setJavaValue(o);
		return eo;
	}
		
	public static EveObject customType(String typeName) {
		EveObject eo = new EveObject();
		eo.setType(EveType.CUSTOM);
		eo.setTypeName(typeName);
		BuiltinCommons.initialize(eo);
		return eo;
	}
	
	public static EveObject prototypeType(String typeName) {
		EveObject eo = new EveObject();
		eo.setType(EveType.PROTOTYPE);
		eo.setTypeName(typeName);
		return eo;
	}
	
	public static EveObject namespaceType(String nsName) {
		EveObject eo = EveObject.prototypeType("namespace_" + nsName);
		eo.cloneable = false;
		eo.putField("ns", new EveObject(nsName));
		eo.putField("type", new EveObject("namespace"));
		eo.setStringRepresentation("<namespace " + nsName + ">");
		return eo;
	}
	
	public static EveObject scopeType(String scopeName) {
		EveObject eo = new EveObject();
		eo.setType(EveType.SCOPE);
		eo.setTypeName("scope(" + scopeName + ")");
		return eo;
	}
	
	public static EveObject withStatementType() {
		EveObject eo = new EveObject();
		eo.setType(EveType.CUSTOM);
		eo.setTypeName(WITH_STATEMENT_TYPENAME);
		return eo;
	}
	
	public static EveObject nullType() {
		EveObject eo = new EveObject();
		eo.setType(EveType.NULL);
		eo.setTypeName("null");
		return eo;
	}
	
	public void markFieldsForClone() {
		for (EveObject eo : getFields().values()) {
			if (eo != null && this != eo) {
				eo.markedForClone = true;
				eo.markFieldsForClone();
			}
		}
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
	
	public void setStringValue(Character c) {
		this.setType(EveType.STRING);
		this.stringValue = Character.toString(c);
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
			throw new EveError(this + " is not a function.");
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
		for (Map.Entry<String, EveObject> entry : this.getFields().entrySet()) {
			if (isNumericalField(entry.getKey())) {
				results.add(entry.getValue()); 
			}
		}
		
		return results;
	}
	
	private boolean isNumericalField(String fieldName) {
		try {
			Integer.parseInt(fieldName);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
				
	public void setJavaValue(Object objectValue) {
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
		case NULL:
			return null;
		default:
			return null;
		}		
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

	public void setMarkedForClone(boolean markedForClone) {
		this.markedForClone = markedForClone;
	}

	public boolean isMarkedForClone() {
		return markedForClone;
	}

	public Map<String, EveObject> getFields() {
		return fields;
	}
	
	public void putField(String name, EveObject eo) {
		if (isNumericalField(name)) {
			putField(Integer.parseInt(name), eo);
		}
		else {
			putField0(name, eo);
		}
	}
	
	public void putField(int index, EveObject eo) {
		if (getIndexedMutator() != null) {
			getIndexedMutator().invokeSelf(this, new EveObject(index), eo);
		}
		else {
			putField0(Integer.toString(index), eo);
		}
	}
	
	private void putField0(String name, EveObject eo) {
		if (isNull()) {
			throw new EveError("null objects cannot have fields.");
		}
		
		if ((isFrozen() || isSealed()) && !hasField(name)) {
			throw new EveError("frozen/sealed objects cannot have properties added.");
		}
		
		if (eo != null) {
			eo.objectParent = this;
		}
		
		this.fields.put(name, eo);
	}
	
	public void putField(String name, DynamicField ejiField) {
		EveObject eji = ejiField.createObject();
		putField(name, eji);
	}
	
	public void putField(int index, DynamicField ejiField) {
		putField(Integer.toString(index), ejiField);
	}
	
	public void putTempField(String name, EveObject eo) {
		if (isNull()) {
			throw new EveError("null objects cannot have fields.");
		}
		
		if ((isFrozen() || isSealed()) && !hasField(name)) {
			throw new EveError("frozen/sealed objects cannot have properties added.");
		}
		
		if (eo != null) {
			eo.objectParent = this;
		}
		
		this.tempFields.put(name, eo);
	}

	public void putTempField(int index, EveObject eo) {
		putTempField(Integer.toString(index), eo);
	}
	
	public void putTempField(String name, DynamicField ejiField) {
		EveObject eji = ejiField.createObject();
		putTempField(name, eji);		
	}
	
	public void putTempField(int index, DynamicField ejiField) {
		putTempField(Integer.toString(index), ejiField);
	}
	
	public void setIndexedAccessor(EveObject accessor) {
		if (accessor.getType() != EveType.FUNCTION) {
			throw new EveError("indexed accessor must be a function.");
		}
		
		this.indexedAccessor = accessor;
	}
	
	public EveObject getIndexedAccessor() {
		return indexedAccessor;
	}
	
	public void setIndexedMutator(EveObject indexedMutator) {
		this.indexedMutator = indexedMutator;
	}

	public EveObject getIndexedMutator() {
		return indexedMutator;
	}
	
	/**
	 * Deep clones this EveObject, unique-ifying its object hierarchy
	 * as necessary. This method is called by the interpreter to implement
	 * the magic auto deep cloning at the interpreter level. At the interpreter
	 * level, everything is automatically deep cloned. But to save memory, we
	 * only want to deep clone when necessary and use the same references for
	 * everything else.
	 */
	public void deepClone() {
		//first, collect a queue of fields that need to be cloned.
		EveObject eo = this;
		Deque<String> fieldNames = new ArrayDeque<String>();
		
		while (eo.objectParent != null) {
			String fieldName = eo.objectParent.getFieldName(eo);		
			fieldNames.addFirst(fieldName);
			eo = eo.objectParent;
		}
		
		//now that eo is the top of the object hierarchy, we go back down through
		//the hierarchy and clone each field. We do not need to clone eo, because
		//eo is already unique via a clone operation somewhere along the way.
		while (!fieldNames.isEmpty()) {
			String fieldName = fieldNames.pop();
			EveObject field = null;
					
			field = eo.getField(fieldName);
			
			//handle the cloning.
			if (field.isMarkedForClone()) {
				field = field.eventlessClone();
				field.setMarkedForClone(false);
				
				//handle dynamic fields
				if (field.hasField("get") && field.getField("get").getType() == EveType.FUNCTION) {
					EveObject get = field.getField("get");
					get.setMarkedForClone(false);
					field.putField("get", get.eventlessClone());
				}
				
				if (field.hasField("set") && field.getField("set").getType() == EveType.FUNCTION) {
					EveObject set = field.getField("set");
					set.setMarkedForClone(false);
					field.putField("set", set.eventlessClone());
				}
			}
			
			field.objectParent = eo; //necessary?
			eo.putField(fieldName, field);
			eo = field;
		}
				
		this.setMarkedForClone(false);
		
	}
	
	/**
	 * Given an EveObject, attempts to find the field name attached to it. This
	 * method checks reference equality to determine which field name to return.
	 * It does not check via the .equals() method.
	 * @param value
	 * @return The name if found, null otherwise.
	 */
	public String getFieldName(EveObject value) {
		for (String fieldName : getFieldNames()) {
			EveObject eo = getField(fieldName);
			if (eo == value) {
				return fieldName;
			}
		}
		
		return null;
	}
	
	public List<String> getFieldNames() {
		List<String> fieldNames = new ArrayList<String>(fields.size());
		
		for (Map.Entry<String, EveObject> entry : fields.entrySet()) {
			fieldNames.add(entry.getKey());
		}
		
		return fieldNames;
	}
	
	public String getDynamicFieldName(EveObject value) {
		for (String fieldName : getFieldNames()) {
			EveObject eo = getField(fieldName);
			
			if (eo != null && eo.hasField("get")) {
				eo = eo.getField("get").invokeSelf(this);
				if (eo == value) {
					return fieldName;
				}
			}
		}
		
		return null;
	}
			
	public EveObject getField(String name) {
		if (isNumericalField(name)) {
			return getField(Integer.parseInt(name));
		}
		else {
			return getField0(name);
		}
	}
	
	public EveObject getField(int index) {
		//string characters as indices
		if (indexedAccessor != null) {
			EveObject indexedValue = indexedAccessor.invokeSelf(this, new EveObject(index));
			
			if (indexedValue != null) {
				return indexedValue;
			}
			else {
				//not sure if this behavior is good or not,
				//because it could be misleading.
				return getField0(Integer.toString(index));
			}
		}
		else {
			return getField0(Integer.toString(index));
		}
	}
	
	private EveObject getField0(String name) {
		EveObject eo = this.fields.get(name);
		
		//non-existent? try temp fields.
		if (eo == null) {
			eo = this.tempFields.get(name);
		}
		
		return eo;
	}
	
	public boolean deleteField(String name) {
		if (isFrozen() || isSealed()) {
			throw new EveError("frozen/sealed objects cannot have properties removed.");
		}
		
		return this.fields.remove(name) != null;
	}
	
	/**
	 * If this EveObject has a "get" field, returns the EveObject from the getter.
	 * The method will recurse through accessor fields until it cannot find one.
	 * If there is no accessor field on this EveObject, this EveObject is returned.
	 * This is the recommended way to get the "real" value of an EveObject.
	 * @return An EveObject
	 */
	public EveObject getSelf() {
		if (hasField("get") && getField("get").getType() == EveType.FUNCTION) {
			return getField("get").invokeSelf(this).getSelf();
		}
		else {
			return this;
		}
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
			case NULL:
				return "null";
			default:
				return this.typeName;
		}
	}
		
	public void setStringRepresentation(String stringRepresentation) {
		this.stringRepresentation = stringRepresentation;
	}

	public String getStringRepresentation() {
		return stringRepresentation;
	}

	public void setSealed(boolean isSealed) {
		this.isSealed = isSealed;
		setFrozen(isSealed); //a sealed object is also frozen (or vice versa).
	}

	public boolean isSealed() {
		return isSealed;
	}

	public void setFrozen(boolean isFrozen) {
		if (isSealed() && isFrozen() && isFrozen == false) {
			throw new EveError("sealed objects must be unsealed before being unfrozen.");
		}
		
		this.isFrozen = isFrozen;
	}

	public boolean isFrozen() {
		return isFrozen;
	}
	
	public boolean isNull() {
		return getType() == EveType.NULL;
	}

	public String toString() {
		//utilize the java-defined stringRepresentation if present, over all others.
		if (getStringRepresentation() != null) {
			return getStringRepresentation();
		}
		
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
		try {
			switch (getType()) {
				case PROTOTYPE:
					return "<type " + this.getTypeName() + ">";
				case CUSTOM:
					return "<" + this.getTypeName() + ">";
				case NULL:
					return "null";
				case SCOPE:
					return "<" + this.getTypeName() + ">";
				default:
					return this.getObjectValue().toString();
			}
		}
		catch (NullPointerException e) {
			return "null";
		}
	}
	
	public EveObject getObjectParent() {
		return this.objectParent;
	}
	
	public EveObject __create(EveObject ... actualParameters) {
		return __create(Arrays.asList(actualParameters));
	}
	
	public EveObject __create(List<EveObject> actualParameters) {
		if (this.getType() != EveType.PROTOTYPE) {
			throw new EveError(this + " is not a type.");
		}
		
		EveObject __create = getField("__create");
		
		if (__create == null || __create.getType() != EveType.FUNCTION) {
			throw new EveError(this.getTypeName() + "::__create is undefined or is not a function.");
		}
		
		return __create.invokeSelf(this, actualParameters);
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
	
	public EveObject invokeSelf(EveObject self) {
		this.putTempField("self", self);
		return invoke0(null);
	}
	
	public EveObject invokeSelf(EveObject self, EveObject ... actualParameters) {
		this.putTempField("self", self);
		return invoke0(Arrays.asList(actualParameters));
	}
	
	public EveObject invokeSelf(EveObject self, List<EveObject> actualParameters) {
		this.putTempField("self", self);
		return invoke0(actualParameters);
	}
	
	public EveObject invokeSetter(EveObject self, EveObject value) {
		this.putTempField("self", self);
		value.objectParent = this;
		return invoke0(Arrays.asList(value));
	}
	
	private EveObject invoke0(List<EveObject> actualParameters) {
		//types must define a __create field in order to have a "constructor".
		if (this.getType() == EveType.PROTOTYPE) {
			EveObject __create = this.getField("__create");
			if (__create != null) {
				return __create.invoke(actualParameters);
			}
			else {
				throw new EveError("type " + this.getTypeName() + " cannot be created through invocation.");
			}
		}
		
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
			//var-args function where the var-arg is the only parameter can invoke with 0 params.
			if (!(func.isVarargs() && func.getParameters().size() == 1) && func.getParameters().size() > 0) {
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
						varargs.putField(v - c, actualParameters.get(v));
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
		
		//are we a closure?
		if (func.isClosure()) {
			ScopeManager.setClosureStack(func.getClosureStack());
		}
		
		//delegate?
		if (func.isDelegate()) {
			this.putTempField("self", func.getDelegateContext());
		}
		
		//named function expression?
		if (func.getName() != null) {
			this.putTempField(func.getName(), this);
		}
				
		//switch to function scope and run.
		ScopeManager.pushScope(this);
		EveObject retval = func.execute();
			
		//do we have a closure?
		if (retval != null) {
			retval.recursePossibleClosures(null);
		}
		
		//remove closure stack if it exists.
		if (func.isClosure()) {
			ScopeManager.setClosureStack(null);
		}
		
		ScopeManager.popScope();
		
		return retval;
	}
	
	/**
	 * Recursively discover and create closures on this EveObject. Looks for values
	 * across all properties (regular and indexed) and then attempts to turn functions into
	 * closures if it was established during function definition that they could become
	 * a closure. The method should be called with a null parameter.
	 * @param closureStack The current closure stack. This should be passed in as null.
	 * @return The closure stack, but it can be ignored. It's for recursion.
	 */
	public Deque<EveObject> recursePossibleClosures(Deque<EveObject> closureStack) {
		//first, this one.
		if (this.getType() == EveType.FUNCTION) {
			Function func = this.getFunctionValue();
			if (!func.isClosure() && func.isPossibleClosure()) {
				func.setClosure(true);
				if (closureStack == null) {
					closureStack = ScopeManager.createClosureStack();
				}
				func.pushClosures(closureStack);
			}
		}
		else if (this.getType() == EveType.LIST) {
			for (EveObject value : getListValue()) {
				closureStack = value.recursePossibleClosures(closureStack);
			}
		}
		
		//and now all its fields (and their fields)
		for (EveObject field : getFields().values()) {
			if (this != field) {
				closureStack = field.recursePossibleClosures(closureStack);
			}
		}
		
		return closureStack;
	}
	
	/**
	 * Clone this EveObject. All properties will be shallowly replicated.
	 * This method also handles managing of the object's family.
	 * @return A clone of this EveObject.
	 */
	public EveObject eveClone() {
		return new EveObject(this, true);
	}
	
	public EveObject eventlessClone() {
		return new EveObject(this, false);
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((booleanValue == null) ? 0 : booleanValue.hashCode());
		result = prime * result
				+ ((doubleValue == null) ? 0 : doubleValue.hashCode());
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result
				+ ((functionValue == null) ? 0 : functionValue.hashCode());
		result = prime * result
				+ ((intValue == null) ? 0 : intValue.hashCode());
		result = prime * result
				+ ((javaValue == null) ? 0 : javaValue.hashCode());
		result = prime * result
				+ ((listValues == null) ? 0 : listValues.hashCode());
		result = prime * result
				+ ((stringValue == null) ? 0 : stringValue.hashCode());
		result = prime * result
				+ ((tempFields == null) ? 0 : tempFields.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result
				+ ((typeName == null) ? 0 : typeName.hashCode());
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
		if (this.isNull() && other.isNull()) {
			return true;
		}
		else if (this.getType() == EveType.BOOLEAN && other.getType() == EveType.BOOLEAN) {
			return this.getBooleanValue().equals(other.getBooleanValue()); 
		}
		else if (this.getType() == EveType.INTEGER && other.getType() == EveType.INTEGER) {
			return this.getIntValue().equals(other.getIntValue());
		}
		else if (this.getType() == EveType.DOUBLE && other.getType() == EveType.DOUBLE) {
			return this.getDoubleValue().equals(other.getDoubleValue());
		}
		else if (this.getType() == EveType.STRING && other.getType() == EveType.STRING){
			return this.getStringValue().equals(other.getStringValue());
		}
		else if (this.getType() == EveType.FUNCTION && other.getType() == EveType.FUNCTION) {
			return functionEquals(other);
		}
		else if (this.getType() == EveType.LIST && other.getType() == EveType.LIST) {
			return this.getListValue().equals(other.getListValue());
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
		else if (this.getType() == EveType.DOUBLE && other.getType() == EveType.INTEGER) {
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
		//because of the type pool, we only need to check references.
		return this == other;
	}
	
	private boolean customTypeEquals(EveObject other) {
		EveObject equalsFunc = this.getField("equals");
		
		//default behavior is like java.
		if (equalsFunc == null) {
			return this == other;
		}
		else {
			EveObject equal = equalsFunc.invokeSelf(this, other);
			
			if (equal.getType() != EveType.BOOLEAN) {
				throw new EveError("equals function must return a boolean value.");
			}
			
			return equal.getBooleanValue();
		}
	}

	public void transferTempFields() {
		for (Map.Entry<String, EveObject> entry : this.tempFields.entrySet()) {
			this.putField(entry.getKey(), entry.getValue());
		}
		
		this.tempFields.clear();
	}
}
