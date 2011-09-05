package eve.core.builtins;

import eve.core.EveObject;
import eve.eji.EJIType;

/**
 * The Function prototype.
 * @author jeff
 */
public class EveFunction extends EveObject {
	/**
	 * For prototypes, the empty constructor is used when cloning from it.
	 * Usually used by literals (sometimes other stuff). Eve functions
	 * cannot be created by invoking the type name.
	 */
	public EveFunction() {
		setType(EveType.PROTOTYPE);
		setTypeName("function");
	}
}
