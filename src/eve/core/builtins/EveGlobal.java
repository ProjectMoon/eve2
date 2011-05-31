package eve.core.builtins;

import java.util.HashMap;
import java.util.Map;

import eve.core.EveError;
import eve.core.EveObject;

public class EveGlobal extends EveObject {
	private static final EveGlobal proto = new EveGlobal();
	
	public static EveGlobal getPrototype() {
		return proto;
	}
	
	private EveGlobal() {
		//super(EveObjectPrototype.getPrototype());
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("global");
	}
	
	/**
	 * Internal copy constructor
	 * @param source
	 * @param clone
	 */
	private EveGlobal(EveGlobal source, boolean clone) {
		super(source, clone);
	}
	
	@Override
	public EveObject eveClone() {
		return new EveGlobal(this, true);
	}
	
	@Override
	public EveObject eventlessClone() {
		return new EveGlobal(this, false);
	}
}
