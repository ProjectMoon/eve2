package eve.core.builtins;

import eve.core.EveObject;

/**
 * The double prototype.
 * @author jeff
 *
 */
public class EveDouble extends EveObject {
	private static final EveDouble proto = new EveDouble();
	
	public static EveDouble getPrototype() {
		return proto;
	}
	
	private EveDouble() {
		//super(EveObjectPrototype.getPrototype());
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("double");
	}
}
