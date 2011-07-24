package eve.eji;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eve.core.EveObject;

class StaticMethodInvocation extends EJIFunction {
	private Class<?> cl;
	private String methodName;
	private String realMethodName;
	
	public StaticMethodInvocation(Class<?> cl, String methodName) {
		this.cl = cl;
		this.methodName = methodName;
		this.realMethodName = methodName;
		setName(methodName);
		setParameters("args");
		setVarargs(true);
		setVarargsIndex(0);
	}
	
	public StaticMethodInvocation(Class<?> cl, String methodName, String realMethodName) {
		this.cl = cl;
		this.methodName = methodName;
		this.realMethodName = realMethodName;
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
			Method meth = EJIHelper.findMethod(cl, realMethodName, args);
			Object[] invokeArgs = EJIHelper.mapArguments(meth.getParameterTypes(), args);
			Object retVal = meth.invoke(null, invokeArgs);
			
			if (retVal != null) {
				EveObject eo = EJIHelper.createEJIType(retVal);
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
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}				
}