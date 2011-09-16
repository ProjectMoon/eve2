package eve.eji;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import eve.core.Function;
import eve.core.EveObject;
import eve.scope.ScopeManager;

public abstract class EJIFunction extends Function {
	/**
	 * Creates a named Eve function from the specified non-static method.
	 * name.
	 * @param methodName
	 * @return An invokable EJI function.
	 */
	public static EJIFunction fromJava(String methodName, boolean bypassTypeCoercion) {
		return new JavaMethodInvocation(methodName, bypassTypeCoercion);
	}
	
	/**
	 * Create a nameless Eve function from the specified non-static method.
	 * @param method
	 * @return An invokable EJI function.
	 */
	public static EJIFunction fromJava(Method method, boolean bypassTypeCoercion) {
		return new JavaMethodInvocation(method, bypassTypeCoercion);
	}
	
	/**
	 * Create a named Eve function from a static method of a class,
	 * identified by the method name.
	 * @param cl
	 * @param methodName
	 * @return An invokable EJI function.
	 */
	public static EJIFunction fromStatic(Class<?> cl, String methodName, boolean bypassTypeCoercion) {
		return new StaticMethodInvocation(cl, methodName, bypassTypeCoercion);
	}
	
	@Override
	public EveObject execute() {
		Map<String, EveObject> params = new HashMap<String, EveObject>();
		
		for (String paramName : getParameters()) {
			EveObject paramValue = ScopeManager.getVariable(paramName);
			params.put(paramName, paramValue);
		}
		
		//handle self.
		EveObject self = ScopeManager.getVariable("self");
		if (self != null) {
			params.put("self", self);
		}
		
		return execute(params);
	}
	
	public void setParameters(String ... paramNames) {
		super.getParameters().clear();
		for (String param : paramNames) {
			super.addParameter(param);
		}
	}
	
	public abstract EveObject execute(Map<String, EveObject> parameters);
}
