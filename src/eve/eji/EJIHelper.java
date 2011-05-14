package eve.eji;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.scope.ScopeManager;

public class EJIHelper {
	public static EveObject self() {
		return ScopeManager.getVariable("self");
	}
	
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
			
		Object o = eo.getObjectValue();
		for (Method meth : o.getClass().getMethods()) {
			EJIFunction methodInvocation = EJIFunction.fromJava(o, meth);
			eo.putField(meth.getName(), new EveObject(methodInvocation));
		}
	}
	
	public static EveObject createEJIType(Object obj) throws IntrospectionException {
		EveObject eo = EveObject.customType(obj.getClass().getName());
		BeanInfo info = Introspector.getBeanInfo(obj.getClass());
		
		//handle properties
		for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
			eo.putField(pd.getName(), new EJIField(obj, pd));	
		}
		
		//handle methods
		for (MethodDescriptor md : info.getMethodDescriptors()) {
			EveObject mappedMethod = new EveObject(EJIFunction.fromJava(obj, md.getMethod()));
			eo.putField(md.getName(), mappedMethod);
		}
		
		return eo;
	}

}
