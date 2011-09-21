package eve.eji;

import java.util.Map;

import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.core.EveObjectFactory;

public abstract class DynamicField {
	public abstract EveObject get();
	public abstract void set(EveObject value);
	
	public EveObject createObject() {
		class DynamicGetter extends EJIFunction {
			@Override
			public EveObject execute(Map<String, EveObject> parameters) {
				return DynamicField.this.get();
			}
		}
		
		class DynamicSetter extends EJIFunction {
			public DynamicSetter() {
				setParameters("value");
			}

			@Override
			public EveObject execute(Map<String, EveObject> parameters) {
				EveObject value = parameters.get("value");
				DynamicField.this.set(value);
				return null;
			}
		}
		
		EveObject eo = EveObjectFactory.empty();
		eo.setTypeName("eji");
		eo.setInternalType(EveType.PROTOTYPE);
		//TODO: examine these; they may not work right
		eo.putField("get", EveObjectFactory.create(new DynamicGetter()));
		eo.putField("set", EveObjectFactory.create(new DynamicSetter()));
		return eo;
	}
}
