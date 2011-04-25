package eve.eni;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.Function;
import eve.scope.ScopeManager;

public class NativeHelper {
	public static Constructor<?> findConstructor(Class<?> cl, EveObject ... params) {
		List<Class<?>> clParams = new ArrayList<Class<?>>(params.length);
		
		for (EveObject param : params) {
			clParams.add(param.getTypeClass());
		}
		
		Class<?>[] clParamsArray = clParams.toArray(new Class<?>[0]);
		
		
		for (Constructor<?> c : cl.getConstructors()) {
			if (Arrays.equals(c.getParameterTypes(), clParamsArray)) {
				return c;
			}
		}
		
		throw new EveError("could not find constructor for " + cl.getName() + " matching " + params);
	}
	
	public static EveObject javaFunction() {
		final NativeCode nc = new NativeCode() {
			@Override
			public EveObject execute() {
				EveObject cname = ScopeManager.getVariable("cname");
				String className = cname.getStringValue();
				
				try {
					Constructor<?> ctor = findConstructor(Class.forName(className), null);
					Object o = ctor.newInstance(1);
					EveObject eo = EveObject.javaType(o);
					return eo;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}		
		};
		
		EveNativeFunction nfunc = new EveNativeFunction(nc);
		nfunc.addParameter("cname");
		return new EveObject(nfunc);
	}
}
