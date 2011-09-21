package eve.core.builtins;

import eve.core.EveError;
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
	public EveString() {}
	
	/**
	 * For prototypes, a value-based constructor serves as a way to create
	 * variables without literals. This constructor should be viewed more
	 * like a method, as it gets transformed into the __create field on the
	 * type. Since we are an EveObject, we can just make use of the same
	 * constructors literals use.
	 * @param s
	 */
	public EveString(String s) {
		setValue(s);
	}
	
	@Override
	public EveObject eveClone() {
		EveObject clone = new EveString();
		clone.cloneFrom(this);
		return clone;
	}
	
	public int getLength() {
		return this.getStringValue().length();
	}
	
	@EJIIndexedAccessor
	public String get(int index) {
		return Character.toString(this.getStringValue().charAt(index));
	}
	
	@EJIIndexedMutator
	public void set(int index, String character) {
		if (character.length() > 1) {
			throw new EveError("can only update single characters with strings of length 1");
		}
		
		StringBuilder sb = new StringBuilder(this.getStringValue());
		char c = character.toCharArray()[0];
		sb.setCharAt(index, c);
			
		setValue(sb.toString());
	}
}
