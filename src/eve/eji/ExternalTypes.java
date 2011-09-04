package eve.eji;

import java.util.HashMap;
import java.util.Map;

import eve.core.EveObject;

/**
 * For defining external types that can then be referenced by eve with the typedef statement.
 * Generally they are Java classes, that get picked up by the EJIScanner. Theoretically,
 * they could come from other places though as long as they can be wrapped in an EveObject.
 * @author jeff
 *
 */
public class ExternalTypes {
	private static Map<String, EveObject> types = new HashMap<String, EveObject>();
	
	public static void addType(String name, EveObject type) {
		types.put(name, type);
	}
	
	public static EveObject getType(String name) {
		return types.get(name);
	}
}
