package eve.statements;

import eve.core.EveObject;
import eve.scope.ScopeManager;

public class PopScopeStatement implements EveStatement {

	@Override
	public EveObject execute() {
		ScopeManager.popScope();
		return null;
	}
	
	@Override
	public String toString() {
		return "PopScopeStatement";
	}

}
