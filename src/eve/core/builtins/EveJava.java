package eve.core.builtins;

import eve.core.EveObject;
import eve.eji.EJIBuiltinType;
import eve.eji.EJIType;

/**
 * 	The java prototype.
 * @author jeff
 *
 */
@EJIType("java")
@EJIBuiltinType
public class EveJava extends EveObject {
	/**
	 * For prototypes, the empty constructor is used when cloning from it.
	 * Usually used by literals (sometimes other stuff). Cannot be created
	 * by invoking the type name.
	 */
	public EveJava() {}
	
	@Override
	public EveObject eveClone() {
		EveObject clone = new EveJava();
		clone.cloneFrom(this);
		return clone;
	}
	
	public String getType() {
		return "java";
	}
}
