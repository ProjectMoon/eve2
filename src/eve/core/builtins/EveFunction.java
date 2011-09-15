package eve.core.builtins;

import eve.core.EveObject;
import eve.eji.EJIType;

/**
 * The function prototype. This type is not annotated with EJIType because
 * it must exist before the EJIScanner runs. It is added to the type pool
 * immediately.
 * @author jeff
 *
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
	
	@Override
	public EveObject eveClone() {
		EveObject clone = new EveFunction();
		clone.cloneFrom(this);
		return clone;
	}
}
