package eve.eji;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eve.core.EveError;
import eve.core.EveObject;

class StaticMethodInvocation extends EJIFunction {
	private Class<?> cl;
	private String methodName;
	private boolean bypassTypeCoercion;

	public StaticMethodInvocation(Class<?> cl, String methodName, boolean bypassTypeCoercion) {
		this.cl = cl;
		this.methodName = methodName;
		this.bypassTypeCoercion = bypassTypeCoercion;
		setName(methodName);
		setParameters("args");
		setVarargs(true);
		setVarargsIndex(0);
	}
		
	@Override
	public EveObject execute(Map<String, EveObject> parameters) {
		List<EveObject> args = new ArrayList<EveObject>(0);	
		EveObject argObj = parameters.get("args");
		if (argObj != null) args = argObj.getListValue();
		
		try {
			Method meth = EJIHelper.findMethod(cl, methodName, args);
			
			if (meth == null) {
				throw new EveError("static method \"" + methodName + "\" not found (Java type: " + cl.getName() + ")");
			}
			
			Object[] invokeArgs = EJIHelper.mapArguments(meth.getParameterTypes(), args);
			Object retVal = meth.invoke(null, invokeArgs);
			
			if (retVal != null) {
				EveObject eo = EJIHelper.createEJIObject(retVal, bypassTypeCoercion);
				return eo;
			}
			else {
				return null;
			}
		}
		catch (IllegalArgumentException e) {
			throw new EveError(e);
		}
		catch (IllegalAccessException e) {
			throw new EveError(e);
		}
		catch (InvocationTargetException e) {
			//an error happened in the java code.
			if (e.getCause() != null) {
				if (e.getCause() instanceof EveError) {
					throw new EveError(e.getCause().getMessage());
				}
				else {
					throw new EveError(e.getCause());
				}
			}
			else {
				throw new EveError(e);
			}
		}
		catch (IntrospectionException e) {
			throw new EveError(e);
		}
	}				
}