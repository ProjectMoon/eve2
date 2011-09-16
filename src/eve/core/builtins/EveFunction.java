package eve.core.builtins;

import eve.core.EveObject;

public class EveFunction extends EveObject {
	protected EveFunction() {
		setType(EveType.FUNCTION);
	}
	
	@Override
	public EveObject eveClone() {
		EveObject clone = new EveFunctionPrototype();
		clone.cloneFrom(this);
		return clone;
	}
	
	public void test() {
		System.out.println("executing test with " + this.hashCode());
	}
}
