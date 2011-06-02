package eve.core.builtins;

import java.util.Map;

import eve.core.EveObject;
import eve.eji.EJIFunction;
import eve.eji.EJIHelper;

/**
 * Not to be confused with EveObject in the core package. This represents
 * the "object" builtin type, although internally it is actually a custom type
 * EveObject. The object type basically just provides a clean slate to make an
 * object from.
 * @author jeff
 *
 */
public class EveBuiltinObject extends EveObject {
	 private static EveBuiltinObject proto = new EveBuiltinObject();
	 
	 public static EveBuiltinObject getPrototype() {
		 return proto;
	 }
	 
	 private EveBuiltinObject() {
		 this.setType(EveType.CUSTOM);
		 this.setTypeName("object");
		 this.putField("create", new EveObject(new CreateBuiltinObjectFunction()));
	 }
	 
	 private class CreateBuiltinObjectFunction extends EJIFunction {

		@Override
		public EveObject execute(Map<String, EveObject> parameters) {
			EveObject eo = new EveObject(EJIHelper.self());
			return eo;
		}
	 }
}
