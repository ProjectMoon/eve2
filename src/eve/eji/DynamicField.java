package eve.eji;

import eve.core.EveObject;

public abstract class DynamicField {
	public abstract EveObject get();
	public abstract void set(EveObject value);
}
