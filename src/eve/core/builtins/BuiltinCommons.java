package eve.core.builtins;

import java.util.HashMap;
import java.util.Map;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.eji.DynamicField;
import eve.eji.EJIHelper;

/**
 * The object prototype, from which everything is cloned.
 * @author jeff
 *
 */
public class BuiltinCommons {
	private static final Map<String, EveObject> typePool = new HashMap<String, EveObject>();
	
	static {
		//initialize the type pool with the default (built-in) types.
		//each type is initialized with common properties by the initialize() method.
		typePool.put(EveBuiltinObject.getPrototype().getTypeName(), initialize(EveBuiltinObject.getPrototype()));
		typePool.put(EveInteger.getPrototype().getTypeName(), initialize(EveInteger.getPrototype()));
		typePool.put(EveString.getPrototype().getTypeName(), initialize(EveString.getPrototype()));
		typePool.put(EveDouble.getPrototype().getTypeName(), initialize(EveDouble.getPrototype()));
		typePool.put(EveBoolean.getPrototype().getTypeName(), initialize(EveBoolean.getPrototype()));
		typePool.put(EveFunction.getPrototype().getTypeName(), initialize(EveFunction.getPrototype()));
		typePool.put(EveList.getPrototype().getTypeName(), initialize(EveList.getPrototype()));
		typePool.put(EveJava.getPrototype().getTypeName(), initialize(EveJava.getPrototype()));
		typePool.put(EveDictionary.getPrototype().getTypeName(), initialize(EveDictionary.getPrototype()));
	}
	
	public static void addType(String name, EveObject type) {
		if (typePool.get(name) != null) {
			return;
		}
		
		typePool.put(name, initialize(type));
	}
	
	public static EveObject getType(String name) {
		return typePool.get(name);
	}

	/**
	 * Initialize a given EveObject with the properties standard to all EveObjects (such as type).
	 * @param prototype
	 * @return The EveObject.
	 */
	public static EveObject initialize(EveObject prototype) {
		prototype.putField("type", typeProperty());
		return prototype;
	}
	
	private static DynamicField typeProperty() {
		return new DynamicField() {
			@Override
			public EveObject get() {
				EveObject self = EJIHelper.self();
				return new EveObject(self.getTypeName());
			}

			@Override
			public void set(EveObject value) {
				throw new EveError("type is a read-only property");
			}
		};
	}
}
