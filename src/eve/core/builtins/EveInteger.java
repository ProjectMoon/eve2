package eve.core.builtins;

import java.util.Map;

import eve.core.EveError;
import eve.core.EveObject;
import eve.eji.EJIFunction;
import eve.eji.EJIHelper;
import eve.eji.EJIProperty;
import eve.eji.EJIType;

/**
 * The int prototype.
 * @author jeff
 *
 */
@EJIType("int")
public class EveInteger extends EveObject {
	public EveInteger() {}
	
	/**
	 * For prototypes, a value-based constructor serves as a way to create
	 * variables without literals.
	 * @param i
	 */
	public EveInteger(int i) {
		setIntValue(i);
		putField("type", new EveObject("int"));
	}
}
