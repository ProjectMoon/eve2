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

public abstract class EveObject {
	public enum EveType { INTEGER, BOOLEAN, DOUBLE, STRING, CUSTOM, PROTOTYPE, FUNCTION, LIST, JAVA, NULL, SCOPE };
	
	//the type and type name of this object.
	//the type is an internal representation, while the type name is what is
	//displayed to the user.
	private EveType type;
	private String typeName;
	
	//custom toString without making a toString field.
	//useful for EJI.
	private String stringRepresentation = null;
	
	//base eve types.
	private Object value;
	
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
		
	/**
	 * Copy constructor. Used for cloning.
	 * @param source The object to clone.
	 */
	public void cloneFrom(EveObject source) {
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
		
		this.value = source.value;
		
		this.indexedAccessor = source.indexedAccessor;
		this.indexedMutator = source.indexedMutator;
		
		HookManager.callCloneHooks(this);
				
		//mark fields for clone. this means that we deep clone as necessary for the new fields.
		markFieldsForClone();
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
	
	private void setTypeFor(Object value) {
		//prototype, scope, and java are handled differently.
		if (value instanceof Integer) {
			setType(EveType.INTEGER);
		}
		else if (value instanceof String) {
			setType(EveType.STRING);
		}
		else if (value instanceof Double) {
			setType(EveType.DOUBLE);
		}
		else if (value instanceof Boolean) {
			setType(EveType.BOOLEAN);
		}
		else if (value instanceof List) {
			//...? maybe.
			setType(EveType.LIST);
		}
		else if (value instanceof Function) {
			setType(EveType.FUNCTION);
		}
		else {
			setType(EveType.CUSTOM);
		}
	}
	
	public void setValue(Object value) {
		setTypeFor(value);
		this.value = value; 
	}
	
	public Object getValue() {
		return value;
	}
		
	public Integer getIntValue() {
		if (this.getType() != EveType.INTEGER) {
			throw new EveError(this + " is not an int!");
		}
		
		return (Integer)value;
	}
	
	public String getStringValue() {
		if (this.getType() != EveType.STRING) {
			throw new EveError(this + " is not a string!");
		}
		
		if (getValue() instanceof Character) {
			return Character.toString((Character)value);
		}
		else {
			return (String)value;
		}
	}
	
	public Double getDoubleValue() {
		if (this.getType() != EveType.DOUBLE){ 
			throw new EveError(this + " is not a double!");
		}
		
		return (Double)value;
	}
	
	public Boolean getBooleanValue() {
		if (this.getType() != EveType.BOOLEAN){
			throw new EveError(this + " is not a boolean!");
		}
		
		return (Boolean)value;
	}
	
	public Function getFunctionValue() {
		if (this.getType() != EveType.FUNCTION) {
			throw new EveError(this + " is not a function.");
		}
		
		return (Function)value;
	}

	public List<EveObject> getListValue() {
		if (this.getType() != EveType.LIST) {
			throw new EveError(this + " is not a list!");
		}
		
		//TODO: fill in empty indices with null
		List<EveObject> results = new ArrayList<EveObject>();
		for (Map.Entry<String, EveObject> entry : this.getFields().entrySet()) {
			if (isNumericalField(entry.getKey())) {
				results.add(entry.getValue()); 
			}
		}
		
		return results;
	}
	
	public Object getJavaValue() {
		return value;
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
			getIndexedMutator().invokeSelf(this, EveObjectFactory.create(index), eo);
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
				field = field.eveClone();
				field.setMarkedForClone(false);
				
				//handle dynamic fields
				if (field.hasField("get") && field.getField("get").getType() == EveType.FUNCTION) {
					EveObject get = field.getField("get");
					get.setMarkedForClone(false);
					field.putField("get", get.eveClone());
				}
				
				if (field.hasField("set") && field.getField("set").getType() == EveType.FUNCTION) {
					EveObject set = field.getField("set");
					set.setMarkedForClone(false);
					field.putField("set", set.eveClone());
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
			EveObject indexedValue = indexedAccessor.invokeSelf(this, EveObjectFactory.create(index));
			
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
		setFrozen(isSealed); //a sealed object is also frozen.
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
					//because of the way EJIHelper.mapJavaMethods works.
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
					return this.getValue().toString();
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
					EveObject varargs = EveObjectFactory.create(new ArrayList<EveObject>(0));
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
		throw new EveError(this + " does not override eveClone");
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result
				+ ((typeName == null) ? 0 : typeName.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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

		if (this.getType() == EveType.FUNCTION && other.getType() == EveType.FUNCTION) {
			return functionEquals(other);
		}
		else if (this.getType() == EveType.PROTOTYPE && other.getType() == EveType.PROTOTYPE) {
			return prototypeEquals(other);
		}
		else if (this.getType() == EveType.CUSTOM && other.getType() == EveType.CUSTOM) {
			return customTypeEquals(other);
		}
		else {
			if (this.getValue() != null && other.getValue() != null) {
				return this.getValue().equals(other.getValue());
			}
			else {
				//true if both are null.
				return this.getValue() == other.getValue();
			}
		}
	}
}
