package eve.eji.stdlib;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
		ScopeManager.putVariable("expose", javaFunction());
		ScopeManager.revertNamespace();
	}
	
	private static EveObject javaFunction() {
		class JavaFunction extends EJIFunction {
			public JavaFunction() {
				setParameters("fqcn");
			}
			
			private EveObject resolveJavaPackageContainer(String fqcn) {
				String[] split = fqcn.split("\\.");
				String className = split[split.length - 1];
				
				EveObject pkgContainer = ScopeManager.getVariable(split[0]);
				if (pkgContainer == null) {
					pkgContainer = EveObject.prototypeType(split[0]);
					EveGlobal.addType(split[0], pkgContainer);
				}
				
				EveObject prevContainer = pkgContainer;
				for (int c = 1; c < split.length - 1; c++) {
					pkgContainer = pkgContainer.getField(split[c]);
					if (pkgContainer == null) {
						pkgContainer = EveObject.prototypeType(split[c]);
						prevContainer.putField(split[c], pkgContainer);
					}
					prevContainer = pkgContainer;
				}
				
				return pkgContainer;
			}

			@Override
			public EveObject execute(Map<String, EveObject> parameters) {
				String className = parameters.get("fqcn").getStringValue();
				try {
					Class<?> cl = Class.forName(className);
					EveObject ctorFunc = EJIHelper.createEJIConstructor(cl);
					EveObject pkgContainer = resolveJavaPackageContainer(className);
					
					if (cl.isAnonymousClass()) {
						throw new EveError("EJI currently does not support anonymous classes");
					}
					
					if (cl.isArray()) {
						String simpleName = cl.getSimpleName();
						simpleName = simpleName.substring(0, simpleName.indexOf("["));
						simpleName += "_Array";
						pkgContainer.putField(simpleName, ctorFunc);
					}
					else {
						pkgContainer.putField(cl.getSimpleName(), ctorFunc);
					}
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
