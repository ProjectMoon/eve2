package eve.core.builtins;

import java.util.Map;

import eve.core.EveError;
import eve.core.EveObject;
import eve.eji.EJIFunction;
import eve.eji.EJIHelper;

public class EveDictionary extends EveObject {
	private static final EveDictionary proto = new EveDictionary();
	
	public static EveDictionary getPrototype() {
		return proto;
	}
	
	private EveDictionary() {
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("dict");
		this.putField("create", new EveObject(new CreateDictFunction()));
	}
	
	private class CreateDictFunction extends EJIFunction {
		public CreateDictFunction() {
			this.addParameter("value");
		}
		
		@Override
		public EveObject execute(Map<String, EveObject> parameters) {
			EveObject value = parameters.get("value");
			
			if (value.getType() != EveType.DICT) {
				throw new EveError("dict.create requires a dict parameter");
			}
			
			return new EveObject(value.getDictionaryValue(), EJIHelper.self());
		}
	}
}
