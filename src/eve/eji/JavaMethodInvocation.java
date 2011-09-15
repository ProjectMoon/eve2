package eve.eji;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eve.core.EveError;
import eve.core.EveObject;

class JavaMethodInvocation extends EJIFunction {
	private Object o;
	private String methodName;
	private Method method;
	
	/**
	 * Create a new method invocation function with a function name equal to the Java
	 * method name.
	 * @param o
	 * @param methodName
	 */
	public JavaMethodInvocation(Object o, String methodName) {
		this.o = o;
		this.methodName = methodName;
		this.method = null;
		setName(methodName);
		setParameters("args");
		setVarargs(true);
		setVarargsIndex(0);
	}
	
	/**
	 * Create a nameless function that invokes a specific method on a specific object.
	 * @param o
	 * @param method
	 */
	public JavaMethodInvocation(Object o, Method method) {
		this.o = o;
		this.method = method;
		this.methodName = null;
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
			//o = EJIHelper.self();
			Method meth = (method != null) ? method : EJIHelper.findMethod(o.getClass(), methodName, args);
			Object[] invokeArgs = EJIHelper.mapArguments(meth.getParameterTypes(), args);
			Object retVal = meth.invoke(o, invokeArgs);
			
			if (retVal != null) {
				EveObject eo = EJIHelper.createEJIObject(retVal);
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
			throw new EveError(e.getCause().getMessage());
		}
		catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}				
}