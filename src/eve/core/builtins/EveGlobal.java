package eve.core.builtins;

import eve.core.EveObject;
import eve.eji.EJIType;

/**
 * The global prototype. Every namespace (including the global namespace)
 * has a global type attached to it. It is best to think of the global type
 * as a scope container. This type is not annotated with EJIType because
 * it must exist before the EJIScanner runs. It is added to the type pool
 * immediately, along with EveFunction.
 * @author jeff
 *
 */
public class EveGlobal extends EveObject {
	/**
	 * For prototypes, the empty constructor is used when cloning from it.
	 * Usually used by literals (sometimes other stuff). Cannot be created
	 * by invoking the type name.
	 */
	public EveGlobal() {
		setType(EveType.PROTOTYPE);
		setTypeName("global");
	}
}
