package eve.core.builtins;

import java.util.HashMap;
import java.util.Map;

import eve.core.EveObject;

public class EveGlobal extends EveObject {
	private static final EveGlobal proto = new EveGlobal();
	private static final Map<String, EveObject> typePool = new HashMap<String, EveObject>();
	
	static {
		typePool.put(EveObjectPrototype.getPrototype().getTypeName(), EveObjectPrototype.getPrototype());
		typePool.put(EveInteger.getPrototype().getTypeName(), EveInteger.getPrototype());
		typePool.put(EveString.getPrototype().getTypeName(), EveString.getPrototype());
		typePool.put(EveDouble.getPrototype().getTypeName(), EveDouble.getPrototype());
		typePool.put(EveBoolean.getPrototype().getTypeName(), EveBoolean.getPrototype());
		typePool.put(EveFunction.getPrototype().getTypeName(), EveFunction.getPrototype());
		typePool.put(EveList.getPrototype().getTypeName(), EveList.getPrototype());
		typePool.put(EveJava.getPrototype().getTypeName(), EveJava.getPrototype());
		typePool.put(EveDictionary.getPrototype().getTypeName(), EveDictionary.getPrototype());
	}
	
	public static EveGlobal getPrototype() {
		return proto;
	}
	
	public static void addType(String name, EveObject type) {
		typePool.put(name, type);
	}
	
	private EveGlobal() {
		super(EveObjectPrototype.getPrototype());
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
	
	@Override
	public EveObject getField(String name) {
		//search type pool first.
		EveObject eo = typePool.get(name);
		
		if (eo != null) {
			return eo;
		}
		else {
			return super.getField(name);
		}
	}
}
