package eve.core.builtins;

import eve.core.EveObject;

/**
 * The Boolean prototype.
 * @author jeff
 *
 */
public class EveBoolean extends EveObject {
	private static final EveBoolean proto = new EveBoolean();
	
	public static EveBoolean getPrototype() {
		return proto;
	}

	private EveBoolean() {
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("bool");
		putField("boolTest", new EveObject(1, false));
	}
}
