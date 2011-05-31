package eve.core.builtins;

import java.util.Map;

import eve.core.EveError;
import eve.core.EveObject;
import eve.eji.EJIFunction;
import eve.eji.EJIHelper;

/**
 * The string prototype.
 * @author jeff
 *
 */
public class EveString extends EveObject {
	private static final EveString proto = new EveString();
	
	public static EveString getPrototype() {
		return proto;
	}
	
	private EveString() {
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("string");
		this.putField("create", new EveObject(new CreateStringFunction()));
	}
	
	private class CreateStringFunction extends EJIFunction {
		public CreateStringFunction() {
			this.addParameter("value");
		}
		
		@Override
		public EveObject execute(Map<String, EveObject> parameters) {
			EveObject value = parameters.get("value");
			
			if (value.getType() != EveType.STRING) {
				throw new EveError("string.create requires a string parameter");
			}
			
			return new EveObject(value.getStringValue(), EJIHelper.self());
		}
	}
}
