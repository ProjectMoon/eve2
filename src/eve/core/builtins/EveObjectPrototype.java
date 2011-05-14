package eve.core.builtins;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.eji.DynamicField;
import eve.eji.EJIHelper;

/**
 * The object prototype, from which everything is cloned.
 * @author jeff
 *
 */
public class EveObjectPrototype extends EveObject {
	private static final EveObjectPrototype proto = new EveObjectPrototype();
	private static volatile Boolean propertiesSetup = false;
	
	public static EveObjectPrototype getPrototype() {
		//Lazy initialization of properties, because otherwise
		//the properties will try to clone this prototype which does
		//not yet exist.
		if (!propertiesSetup) {
			synchronized (propertiesSetup) {
				if (!propertiesSetup) {
					setupProperties();
				}
			}
		}
		return proto;
	}
	
	private EveObjectPrototype() {
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("object");
	}
	
	private static DynamicField typeProperty() {
		return new DynamicField() {
			@Override
			public EveObject get() {
				EveObject self = EJIHelper.self();
				return new EveObject(self.getTypeName());
			}

			@Override
			public void set(EveObject value) {
				throw new EveError("type is a read-only property");
			}
		};
	}
	
	private static void setupProperties() {
		propertiesSetup = true;
		proto.putField("type", typeProperty());
	}
}
