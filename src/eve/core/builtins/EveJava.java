package eve.core.builtins;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import eve.core.EveObject;
import eve.eni.NativeFunction;
import eve.eni.NativeCode;
import eve.eni.NativeHelper;
import eve.scope.ScopeManager;

/**
 * 	The java prototype.
 * @author jeff
 *
 */
public class EveJava extends EveObject {
	private static final EveJava proto = new EveJava();
	
	public static EveJava getPrototype() {
		return proto;
	}
	
	private EveJava() {
		super(EveObjectPrototype.getPrototype());
		this.setTypeName("java");
		this.setType(EveType.PROTOTYPE);
	}
}
