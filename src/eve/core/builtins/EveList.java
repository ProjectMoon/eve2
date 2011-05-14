package eve.core.builtins;

import eve.core.EveObject;
import eve.eji.DynamicField;
import eve.eji.NativeHelper;

public class EveList extends EveObject {
	private static final EveList proto = new EveList();
	
	public static EveList getPrototype() {
		return proto;
	}
	
	private EveList() {
		super(EveObjectPrototype.getPrototype());
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("list");
		
		class Length extends DynamicField {
			@Override
			public EveObject get() {
				EveObject self = NativeHelper.self();
				return new EveObject(self.getListValue().size());
			}

			@Override
			public void set(EveObject value) {
				System.out.println("can't set list length!");
			}
		}
		
		this.putField("length", new Length());
	}
}
