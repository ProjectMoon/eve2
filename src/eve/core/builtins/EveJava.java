package eve.core.builtins;

import eve.core.EveObject;

/**
 * 	The java prototype.
 * @author jeff
 *
 */
public class EveJava extends EveObject {
	private static final EveJava proto = new EveJava();
	
	public static EveJava getPrototype() {
		return proto;
	}
	
	private EveJava() {
		//super(EveObjectPrototype.getPrototype());
		this.setTypeName("java");
		this.setType(EveType.PROTOTYPE);
	}
}
