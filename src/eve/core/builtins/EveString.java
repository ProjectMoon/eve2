package eve.core.builtins;

import eve.core.EveObject;

/**
 * The string prototype.
 * @author jeff
 *
 */
public class EveString extends EveObject {
	private static final EveString proto = new EveString();
	
	public static EveString getPrototype() {
		return proto;
	}
	
	private EveString() {
		super(EveObjectPrototype.getPrototype());
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("string");
	}
}
