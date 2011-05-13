package eve.eji.stdlib;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import eve.core.EveObject;
import eve.eji.NativeFunction;
import eve.eji.NativeHelper;
import eve.scope.ScopeManager;

public class Java {
	public static void init() {
		ScopeManager.setNamespace("java");
		ScopeManager.createGlobalScope();
		ScopeManager.putVariable("create", javaFunction());
		ScopeManager.revertNamespace();
	}
	
	private static EveObject javaFunction() {
		class JavaFunction extends NativeFunction {
			public JavaFunction() {
				setParameters("cname", "ctorArgs");
				setVarargs(true);
				setVarargsIndex(1);
			}

			@Override
			public EveObject execute(Map<String, EveObject> parameters) {
				EveObject cname = parameters.get("cname");
				String className = cname.getStringValue();
				EveObject ctorArgs = parameters.get("ctorArgs");
							
				try {
					Constructor<?> ctor = NativeHelper.findConstructor(Class.forName(className), ctorArgs.getListValue());
					Object o = ctor.newInstance(NativeHelper.mapToJava(ctorArgs.getListValue()));
					EveObject eo = EveObject.javaType(o);
					NativeHelper.mapJavaMethods(eo);
					return eo;
				}
				catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}
		
		return new EveObject(new JavaFunction());
	}
}
