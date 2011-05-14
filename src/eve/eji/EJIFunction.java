package eve.eji;

import java.util.HashMap;
import java.util.Map;

import eve.core.EveError;
import eve.core.Function;
import eve.core.EveObject;
import eve.scope.ScopeManager;

public abstract class EJIFunction extends Function {
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
