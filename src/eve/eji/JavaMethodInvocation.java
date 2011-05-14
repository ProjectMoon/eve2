package eve.eji;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import eve.core.EveObject;
import eve.scope.ScopeManager;

class JavaMethodInvocation extends EJIFunction {
	private Object o;
	private Method meth;
	
	public JavaMethodInvocation(Object o, Method meth) {
		this.o = o;
		this.meth = meth;
		if (meth.getParameterTypes().length > 0) {
			setParameters("args");
			setVarargs(true);
			setVarargsIndex(0);
		}
	}
	
	@Override
	public EveObject execute(Map<String, EveObject> parameters) {
		EveObject eoArgs = ScopeManager.getVariable("args");
		try {
			Object[] args = (eoArgs != null) ? EJIHelper.mapToJava(eoArgs.getListValue()) : null;
			Object retVal = meth.invoke(o, args);
			if (retVal != null) {
				EveObject eo = EveObject.javaType(retVal);
				EJIHelper.mapJavaMethods(eo);
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
}