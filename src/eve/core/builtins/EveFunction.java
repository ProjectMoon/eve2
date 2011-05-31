package eve.core.builtins;

import java.util.Map;

import eve.core.EveError;
import eve.core.EveObject;
import eve.eji.EJIFunction;
import eve.eji.EJIHelper;

/**
 * The Function prototype.
 * @author jeff
 *
 */
public class EveFunction extends EveObject {
	private static final EveFunction proto = new EveFunction();
	
	public static EveFunction getPrototype() {
		return proto;
	}
	
	private EveFunction() {
		this.setType(EveType.PROTOTYPE);
		this.setTypeName("function");
		
		//we cannot use the normal method of cloning from EveFunction when
		//adding a function to the EveFunction prototype... so CreateFunctionFunction
		//must not be cloned from anything (or at least not function).
		//TODO: fix this in case dynamic properties need to be added to all function objects.
		this.putField("create", new EveObject(new CreateFunctionFunction(), false));
	}
	
	private class CreateFunctionFunction extends EJIFunction {
		public CreateFunctionFunction() {
			this.addParameter("value");
		}
		
		@Override
		public EveObject execute(Map<String, EveObject> parameters) {
			EveObject value = parameters.get("value");
			
			if (value.getType() != EveType.FUNCTION) {
				throw new EveError("function.create requires a function parameter");
			}
			
			return new EveObject(value.getFunctionValue(), EJIHelper.self());
		}
	}
}
