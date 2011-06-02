package eve.core.builtins;

import java.util.Map;

import eve.core.EveError;
import eve.core.EveObject;
import eve.eji.EJIFunction;
import eve.eji.EJIHelper;

/**
 * The double prototype.
 * @author jeff
 *
 */
public class EveDouble extends EveObject {
	private static final EveDouble proto = new EveDouble();
	
	public static EveDouble getPrototype() {
		return proto;
	}
	
	private EveDouble() {
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("double");
		this.putField("create", new EveObject(new CreateDoubleFunction()));
	}
	
	private class CreateDoubleFunction extends EJIFunction {
		public CreateDoubleFunction() {
			this.addParameter("value");
		}
		
		@Override
		public EveObject execute(Map<String, EveObject> parameters) {
			EveObject value = parameters.get("value");
			
			if (value.getType() != EveType.DOUBLE) {
				throw new EveError("double.create requires a double parameter");
			}
			
			return new EveObject(value.getDoubleValue(), EJIHelper.self());
		}
	}
}
