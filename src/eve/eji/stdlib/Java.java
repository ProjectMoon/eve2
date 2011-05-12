package eve.eji.stdlib;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import eve.core.EveObject;
import eve.eji.NativeCode;
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
		final NativeCode nc = new NativeCode() {
			@Override
			public EveObject execute() {
				EveObject cname = ScopeManager.getVariable("cname");
				String className = cname.getStringValue();
				EveObject ctorArgs = ScopeManager.getVariable("ctorArgs");
							
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
		};
		
		NativeFunction nfunc = new NativeFunction(nc);
		nfunc.addParameter("cname");
		nfunc.addParameter("ctorArgs");
		nfunc.setVarargs(true);
		nfunc.setVarargsIndex(1); //ctorArgs ...
		return new EveObject(nfunc);
	}
}
