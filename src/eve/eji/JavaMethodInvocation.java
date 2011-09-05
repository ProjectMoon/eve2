package eve.eji;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eve.core.EveObject;

class JavaMethodInvocation extends EJIFunction {
	private Object o;
	private String methodName;
	
	public JavaMethodInvocation(Object o, String methodName) {
		this.o = o;
		this.methodName = methodName;
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
			Method meth = EJIHelper.findMethod(o.getClass(), methodName, args);
			Object[] invokeArgs = EJIHelper.mapArguments(meth.getParameterTypes(), args);
			Object retVal = meth.invoke(o, invokeArgs);
			
			if (retVal != null) {
				if (retVal instanceof EveObject) {
					return (EveObject)retVal;
				}
				else {
					EveObject eo = EJIHelper.createEJIObject(retVal);
					return eo;
				}
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