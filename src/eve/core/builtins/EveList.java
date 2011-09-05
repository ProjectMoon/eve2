package eve.core.builtins;

import java.util.TreeMap;

import eve.core.EveError;
import eve.core.EveObject;
import eve.eji.DynamicField;
import eve.eji.EJIBuiltinType;
import eve.eji.EJIHelper;
import eve.eji.EJIType;

@EJIType("list")
@EJIBuiltinType
public class EveList extends EveObject {
	public EveList() {
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("list");
	
		//properties.
		this.putField("length", lengthProperty());
	}
	
	public EveList(EveObject list) {
		super();
		if (list.getType() == EveType.LIST) {
			setListValue(list.getListValue());
		}
		else {
			//turn non-lists into lists.
			this.addIndexedValue(list);
		}
	}
	
	private DynamicField lengthProperty() {
		return new DynamicField() {
			@Override
			public EveObject get() {
				EveObject self = EJIHelper.self();
				TreeMap<Integer, EveObject> list = self.getListMap();		
				return new EveObject((list.lastKey() - list.firstKey()) + 1);
			}

			@Override
			public void set(EveObject value) {
				throw new EveError("length is a read-only property");
			}
		};
	}
}
