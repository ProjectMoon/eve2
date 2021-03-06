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
	
	//Have to initialize these 2 first because other things depend on them.
	static {
		EveFunction function = new EveFunction();
		EveGlobal global = new EveGlobal();
		
		typePool.put("function", function);
		typePool.put("global", global);
		
		//must do init after they're added to the type pool
		//because initialization depends on function existing.
		initialize(function);
		initialize(global);		
	}
	
	public static void addType(String name, EveObject type) {
		if (type == null || type.getType() != EveType.PROTOTYPE) {
			throw new EveError("can only add types to the type pool.");
		}
		
		if (typePool.get(name) != null) {
			return;
		}
		
		typePool.put(name, initialize(type));
	}
	
	public static void mergeType(String name, EveObject newProperties) {
		EveObject type = getType(name);
		newProperties = newProperties.eventlessClone();
		
		if (type == null) {
			type = EveObject.prototypeType(name);
			addType(name, type);
		}
		
		for (Map.Entry<String, EveObject> entry : newProperties.getFields().entrySet()) {
			type.putField(entry.getKey(), entry.getValue());
		}
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
