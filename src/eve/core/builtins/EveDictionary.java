package eve.core.builtins;

import eve.core.EveObject;

public class EveDictionary extends EveObject {
	private static final EveDictionary proto = new EveDictionary();
	
	public static EveDictionary getPrototype() {
		return proto;
	}
	
	private EveDictionary() {
		super(EveObjectPrototype.getPrototype());
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("dict");
	}
}
