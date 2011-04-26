package eve.core.builtins;

import eve.core.EveObject;

public class EveGlobal extends EveObject {
	private static final EveGlobal proto = new EveGlobal();
	
	public static EveGlobal getPrototype() {
		return proto;
	}
	
	private EveGlobal() {
		super(EveObjectPrototype.getPrototype());
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("global");
	}
}
