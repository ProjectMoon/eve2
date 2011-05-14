package eve.core.builtins;

import java.util.Map;
import java.util.TreeMap;

import eve.core.EveError;
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
	
		//properties.
		this.putField("length", lengthProperty());
	}
	
	private DynamicField lengthProperty() {
		class Length extends DynamicField {
			@Override
			public EveObject get() {
				EveObject self = NativeHelper.self();
				TreeMap<Integer, EveObject> list = self.getListMap();		
				return new EveObject((list.lastKey() - list.firstKey()) + 1);
			}

			@Override
			public void set(EveObject value) {
				throw new EveError("length is a read-only property");
			}
		}
		
		return new Length();
	}
}
