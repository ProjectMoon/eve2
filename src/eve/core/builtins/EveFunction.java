package eve.core.builtins;

import eve.core.EveObject;

/**
 * The Function prototype.
 * @author jeff
 *
 */
public class EveFunction extends EveObject {
	private static final EveFunction proto = new EveFunction();
	
	public static EveFunction getPrototype() {
		return proto;
	}
	
	private EveFunction() {
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("function");
	}
}
