package eve.core.builtins;

import eve.core.EveObject;
import eve.eji.EJIBuiltinType;
import eve.eji.EJIType;

/**
 * The double prototype.
 * @author jeff
 *
 */
@EJIType("double")
@EJIBuiltinType
public class EveDouble extends EveObject {
	/**
	 * For prototypes, the empty constructor is used when cloning from it.
	 * Usually used by literals (sometimes other stuff).
	 */
	public EveDouble() {}
	
	/**
	 * For prototypes, a value-based constructor serves as a way to create
	 * variables without literals. This constructor should be viewed more
	 * like a method, as it gets transformed into the __create field on the
	 * type. Since we are an EveObject, we can just make use of the same
	 * constructors literals use.
	 * @param i
	 */
	public EveDouble(double d) {
		setValue(d);
	}
	
	@Override
	public EveObject eveClone() {
		EveObject clone = new EveDouble();
		clone.cloneFrom(this);
		return clone;
	}
	
	public String getType() {
		return "double";
	}
}
