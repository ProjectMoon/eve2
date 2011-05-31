package eve.core.builtins;

import java.util.Map;

import eve.core.EveError;
import eve.core.EveObject;
import eve.eji.EJIFunction;
import eve.eji.EJIHelper;

/**
 * The Boolean prototype.
 * @author jeff
 *
 */
public class EveBoolean extends EveObject {
	private static final EveBoolean proto = new EveBoolean();
	
	public static EveBoolean getPrototype() {
		return proto;
	}

	private EveBoolean() {
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("bool");
		this.putField("create", new EveObject(new CreateBoolFunction()));
	}
	
	private class CreateBoolFunction extends EJIFunction {
		public CreateBoolFunction() {
			this.addParameter("value");
		}
		
		@Override
		public EveObject execute(Map<String, EveObject> parameters) {
			EveObject value = parameters.get("value");
			
			if (value.getType() != EveType.BOOLEAN) {
				throw new EveError("bool.create requires a bool parameter");
			}
			
			return new EveObject(value.getBooleanValue(), EJIHelper.self());
		}
	}
}
