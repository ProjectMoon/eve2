package eve.core.builtins;

import eve.core.EveObject;
import eve.eji.EJIBuiltinType;
import eve.eji.EJIIndexedAccessor;
import eve.eji.EJIIndexedMutator;
import eve.eji.EJIType;

/**
 * The string prototype.
 * @author jeff
 *
 */
@EJIType("string")
@EJIBuiltinType
public class EveString extends EveObject {
	/**
	 * For prototypes, the empty constructor is used when cloning from it.
	 * Usually used by literals (sometimes other stuff).
	 */
	public EveString() {
		System.out.println("sup string empty");
	}
	
	/**
	 * For prototypes, a value-based constructor serves as a way to create
	 * variables without literals. This constructor should be viewed more
	 * like a method, as it gets transformed into the __create field on the
	 * type. Since we are an EveObject, we can just make use of the same
	 * constructors literals use.
	 * @param s
	 */
	public EveString(String s) {
		super(s);
		System.out.println("sup string");
	}
	
	public int getLength() {
		return this.getStringValue().length();
	}
	
	@EJIIndexedAccessor
	public String character(int index) {
		return Character.toString(this.getStringValue().charAt(index));
	}
	
	@EJIIndexedMutator
	public void characterSet(int index, String character) {
		System.out.println("setting " + index + " to " + character);
	}
	
}
