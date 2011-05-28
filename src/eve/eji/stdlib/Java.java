package eve.eji.stdlib;

import java.util.Map;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.builtins.EveGlobal;
import eve.eji.EJIFunction;
import eve.eji.EJIHelper;
import eve.scope.ScopeManager;

public class Java {

	public static void init() {
		ScopeManager.setNamespace("java");
		ScopeManager.createGlobalScope();
		ScopeManager.putVariable("expose", new EveObject(new JavaFunction(false)));
		ScopeManager.putVariable("exposeType", new EveObject(new JavaFunction(true)));
		ScopeManager.revertNamespace();
	}
}
