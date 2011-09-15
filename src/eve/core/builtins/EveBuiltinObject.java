package eve.core.builtins;

import eve.core.EveObject;
import eve.eji.EJIBuiltinType;
import eve.eji.EJIType;

/**
 * Not to be confused with EveObject in the core package. This represents
 * the "object" builtin type, although internally it is actually a custom type
 * EveObject. The object type basically just provides a clean slate to make an
 * object from. Not annotated with @EJIType because it must be available
 * immediately.
 * @author jeff
 *
 */
public class EveBuiltinObject extends EveObject {
	/**
	 * For prototypes, the empty constructor is used when cloning from it.
	 * Usually used by literals (sometimes other stuff). EveBuiltinObject
	 * cannot be created by invoking the type name (unlike other built-ins).
	 */
	public EveBuiltinObject() {
		setType(EveType.PROTOTYPE);
		setTypeName("object");
		System.out.println("object empty");
	}
	
	public EveBuiltinObject(Object value) {
		System.out.println("object with value");
	}
	
	@Override
	public EveObject eveClone() {
		EveObject clone = new EveBuiltinObject();
		clone.cloneFrom(this);
		return clone;
	}
}
