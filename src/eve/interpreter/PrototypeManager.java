package eve.interpreter;

import java.util.HashMap;
import java.util.Map;

import eve.core.EveObject;

/**
 * Holds all prototype definitions.
 * @author jeff
 *
 */
public class PrototypeManager {
	private static Map<String, EveObject> prototypes = new HashMap<String, EveObject>();
	
	public static EveObject getPrototype(String name) {
		return prototypes.get(name);
	}
	
	public static void addPrototype(String name, EveObject prototype) {
		prototypes.put(name, prototype);
	}
	
	public static void deletePrototype(String name) {
		prototypes.remove(name);
	}
}
