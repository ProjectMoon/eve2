package eve.core.builtins;

import eve.core.EveObject;
import eve.eji.EJIType;

public class EveGlobal extends EveObject {
	/**
	 * For prototypes, the empty constructor is used when cloning from it.
	 * Usually used by literals (sometimes other stuff). Cannot be created
	 * by invoking the type name.
	 */
	public EveGlobal() {
		setType(EveType.PROTOTYPE);
		setTypeName("global");
	}
}
