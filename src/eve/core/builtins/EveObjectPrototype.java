package eve.core.builtins;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.eji.DynamicField;
import eve.eji.NativeHelper;

/**
 * The object prototype, from which everything is cloned.
 * @author jeff
 *
 */
public class EveObjectPrototype extends EveObject {
	private static final EveObjectPrototype proto = new EveObjectPrototype();
	private static boolean propertiesSetup = false;
	
	public static EveObjectPrototype getPrototype() {
		if (!propertiesSetup) {
			setupProperties();
		}
		return proto;
	}
	
	private EveObjectPrototype() {
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("object");
	}
	
	private static DynamicField typeProperty() {
		class Type extends DynamicField {
			@Override
			public EveObject get() {
				EveObject self = NativeHelper.self();
				return new EveObject(self.getTypeName());
			}

			@Override
			public void set(EveObject value) {
				throw new EveError("type is a read-only property");
			}
		}
		
		return new Type();
	}
	
	private static void setupProperties() {
		propertiesSetup = true;
		proto.putField("type", typeProperty());
	}
}
