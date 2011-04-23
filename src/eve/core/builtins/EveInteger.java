package eve.core.builtins;

import eve.core.EveObject;

public class EveInteger extends EveObject {
	private static final EveInteger proto = new EveInteger();
	
	public static EveInteger getPrototype() {
		return proto;
	}
	
	private EveInteger() {
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("int");
		putField("stringTest", new EveObject(1, false));
	}
}
