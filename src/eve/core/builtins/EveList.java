package eve.core.builtins;

import eve.core.EveObject;

public class EveList extends EveObject {
	private static final EveList proto = new EveList();
	
	public static EveList getPrototype() {
		return proto;
	}
	
	private EveList() {
		super(EveObjectPrototype.getPrototype());
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("list");
	}
}
