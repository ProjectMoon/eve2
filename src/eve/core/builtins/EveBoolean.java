package eve.core.builtins;

import eve.core.EveObject;
import eve.eji.EJIBuiltinType;
import eve.eji.EJIType;

/**
 * The Boolean prototype.
 * @author jeff
 *
 */
@EJIType("bool")
@EJIBuiltinType
public class EveBoolean extends EveObject {
	/**
	 * For prototypes, the empty constructor is used when cloning from it.
	 * Usually used by literals (sometimes other stuff).
	 */
	public EveBoolean() {}
	
	/**
	 * For prototypes, a value-based constructor serves as a way to create
	 * variables without literals. This constructor should be viewed more
	 * like a method, as it gets transformed into the __create field on the
	 * type. Since we are an EveObject, we can just make use of the same
	 * constructors literals use. 
	 * @param b
	 */
	public EveBoolean(boolean b) {
		super(b);
	}
}
