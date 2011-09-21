package eve.core.builtins;

import eve.core.EveObject;

public class EveFunction extends EveObject {
	protected EveFunction() {
		setInternalType(EveType.FUNCTION);
	}
	
	@Override
	public EveObject eveClone() {
		EveObject clone = new EveFunctionPrototype();
		clone.cloneFrom(this);
		return clone;
	}
	
	public String getType() {
		return "function";
	}
}
