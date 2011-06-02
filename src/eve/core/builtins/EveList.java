package eve.core.builtins;

import java.util.Map;
import java.util.TreeMap;

import eve.core.EveError;
import eve.core.EveObject;
import eve.eji.DynamicField;
import eve.eji.EJIFunction;
import eve.eji.EJIHelper;

public class EveList extends EveObject {
	private static final EveList proto = new EveList();
	
	public static EveList getPrototype() {
		return proto;
	}
	
	private EveList() {
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("list");
	
		//properties.
		this.putField("length", lengthProperty());
		this.putField("create", new EveObject(new CreateListFunction()));
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
	
	private class CreateListFunction extends EJIFunction {
		public CreateListFunction() {
			this.addParameter("value");
		}
		
		@Override
		public EveObject execute(Map<String, EveObject> parameters) {
			EveObject value = parameters.get("value");
			
			if (value.getType() != EveType.LIST) {
				throw new EveError("list.create requires a list parameter");
			}
			
			return new EveObject(value.getListValue(), EJIHelper.self());
		}
	}
}
