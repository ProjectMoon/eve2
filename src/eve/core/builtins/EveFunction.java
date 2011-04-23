package eve.core.builtins;

import eve.core.EveObject;
import eve.core.EveObject.EveType;

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
		putField("funcTest", new EveObject(1, false));
	}
}
