package eve.core.builtins;

import eve.core.EveObject;

/**
 * The int prototype.
 * @author jeff
 *
 */
public class EveInteger extends EveObject {
	private static final EveInteger proto = new EveInteger();
	
	public static EveInteger getPrototype() {
		return proto;
	}
	
	private EveInteger() {
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("int");
	}
}
