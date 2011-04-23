package eve.core.builtins;

import eve.core.EveObject;
import eve.core.EveObject.EveType;

public class EveString extends EveObject {
	private static final EveString proto = new EveString();
	
	public static EveString getPrototype() {
		return proto;
	}
	
	private EveString() {
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("string");
		putField("stringTest", new EveObject(1, false));
	}
}
