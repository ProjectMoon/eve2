package eve.core.builtins;

import eve.core.EveObject;
import eve.core.EveObject.EveType;

/**
 * The object prototype, from which everything is cloned.
 * @author jeff
 *
 */
public class EveObjectPrototype extends EveObject {
	private static final EveObjectPrototype proto = new EveObjectPrototype();
	
	public static EveObjectPrototype getPrototype() {
		return proto;
	}
	
	private EveObjectPrototype() {
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("object");
	}
}
