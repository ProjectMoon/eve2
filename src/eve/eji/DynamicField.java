package eve.eji;

import java.util.Map;

import eve.core.EveObject;
import eve.core.EveObject.EveType;

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
		
		EveObject eo = new EveObject();
		eo.setTypeName("eji");
		eo.setType(EveType.PROTOTYPE);
		eo.putField("get", new EveObject(new DynamicGetter()));
		eo.putField("set", new EveObject(new DynamicSetter()));
		return eo;
	}
}
