package eve.statements;

import eve.core.EveObject;
import eve.scope.ScopeManager;

public class PushScopeStatement extends AbstractStatement implements EveStatement {
	private EveObject newScope;
	
	public PushScopeStatement(EveObject newScope) {
		this.newScope = newScope;
	}
	
	@Override
	public EveObject execute() {
		ScopeManager.pushScope(newScope);
		return null;
	}
	
	@Override
	public String toString() {
		return "PushScopeStatement: " + newScope;
	}

}
