package eve.eji;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.scope.ScopeManager;

public class NativeHelper {
	public static Constructor<?> findConstructor(Class<?> cl, List<EveObject> params) {
		return findConstructor(cl, params.toArray(new EveObject[0]));
	}
	
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
		
		throw new EveError("could not find constructor for " + cl.getName() + " matching " + Arrays.asList(params));
	}
	
	public static Method findMethod(Class<?> cl, EveObject ... params) {
		List<Class<?>> methParams = new ArrayList<Class<?>>(params.length);
		
		for (EveObject param : params) {
			methParams.add(param.getTypeClass());
		}
		
		Class<?>[] clParamsArray = methParams.toArray(new Class<?>[0]);
		
		for (Method meth : cl.getMethods()) {
			if (Arrays.equals(meth.getParameterTypes(), clParamsArray)) {
				return meth;
			}
		}
		
		throw new EveError("could not find method for " + cl.getName() + " matching " + params);
	}
	
	public static Object[] mapToJava(List<EveObject> eoArgs) {
		Object[] args = new Object[eoArgs.size()];
		
		for (int c = 0; c < args.length; c++) {
			args[c] = eoArgs.get(c).getObjectValue();
		}
		
		return args;
	}
	
	public static void mapJavaMethods(EveObject eo) {
		if (eo.getType() != EveType.JAVA) {
			throw new EveError("can only map java methods to java types.");
		}
		
		
		final Object o = eo.getObjectValue();
		for (final Method meth : o.getClass().getMethods()) {
			final NativeCode nc = new NativeCode() {
				@Override
				public EveObject execute() {
					EveObject eoArgs = ScopeManager.getVariable("args");
					try {
						Object[] args = (eoArgs != null) ? NativeHelper.mapToJava(eoArgs.getListValue()) : null;
						Object retVal = meth.invoke(o, args);
						if (retVal != null) {
							EveObject eo = EveObject.javaType(retVal);
							mapJavaMethods(eo);
							return eo;
						}
						else {
							return null;
						}
					}
					catch (IllegalArgumentException e) {
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
					
					return null;
				}		
			};
			
			NativeFunction nfunc = new NativeFunction(nc);
			if (meth.getParameterTypes().length > 0) {
				nfunc.addParameter("args");
				nfunc.setVarargs(true);
				nfunc.setVarargsIndex(0); //args ...
			}
			eo.putField(meth.getName(), new EveObject(nfunc));
		}
	}
	

}
