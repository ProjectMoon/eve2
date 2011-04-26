package eve.eni;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;

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
		
		throw new EveError("could not find constructor for " + cl.getName() + " matching " + params);
	}
	
	public static Object[] mapToJava(List<EveObject> ctorArgs) {
		Object[] args = new Object[ctorArgs.size()];
		
		for (int c = 0; c < args.length; c++) {
			args[c] = ctorArgs.get(c).getJavaValue();
		}
		
		return args;
	}
	

}
