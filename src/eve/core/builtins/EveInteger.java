package eve.core.builtins;

import java.util.Map;

import eve.core.EveError;
import eve.core.EveObject;
import eve.eji.EJIFunction;

/**
 * The int prototype.
 * @author jeff
 *
 */
public class EveInteger extends EveObject {
	private static final EveInteger proto = new EveInteger();
	
	public static EveInteger getPrototype() {
		return proto;
	}
	
	private EveInteger() {
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("int");
		this.putField("create", new EveObject(new EveIntCreateFunction()));
	}
	
	private class EveIntCreateFunction extends EJIFunction {
		public EveIntCreateFunction() {
			this.addParameter("value");
		}
		
		@Override
		public EveObject execute(Map<String, EveObject> parameters) {
			EveObject value = parameters.get("value");
			
			if (value.getType() != EveType.INTEGER) {
				throw new EveError("int.create requires an int parameter");
			}
			
			return new EveObject(value.getIntValue());
		}
		
		
	}
}
