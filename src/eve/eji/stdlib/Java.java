package eve.eji.stdlib;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import eve.core.EveObject;
import eve.core.builtins.EveGlobal;
import eve.eji.EJIFunction;
import eve.eji.EJIHelper;
import eve.scope.ScopeManager;

public class Java {
	public static void init() {
		ScopeManager.setNamespace("java");
		ScopeManager.createGlobalScope();
		ScopeManager.putVariable("expose", javaFunction());
		ScopeManager.revertNamespace();
	}
	
	private static EveObject javaFunction() {
		class JavaFunction extends EJIFunction {
			public JavaFunction() {
				setParameters("className");
			}

			@Override
			public EveObject execute(Map<String, EveObject> parameters) {
				String className = parameters.get("className").getStringValue();
				try {
					Class<?> cl = Class.forName(className);
					EveObject ctorFunc = EJIHelper.createEJIConstructor(cl);
					EveGlobal.addType(cl.getSimpleName(), ctorFunc);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
			}
		}
		
		return new EveObject(new JavaFunction());
	}
}
