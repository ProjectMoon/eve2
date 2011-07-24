package eve.eji;

import java.util.HashMap;
import java.util.Map;

import eve.core.Function;
import eve.core.EveObject;
import eve.scope.ScopeManager;

public abstract class EJIFunction extends Function {
	public static EJIFunction fromJava(Object obj, String methodName) {
		return new JavaMethodInvocation(obj, methodName);
	}
	
	public static EJIFunction fromStatic(Class<?> cl, String methodName) {
		return new StaticMethodInvocation(cl, methodName);
	}
	
	public static EJIFunction fromStatic(Class<?> cl, String methodName, String realMethodName) {
		return new StaticMethodInvocation(cl, methodName, realMethodName);
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
