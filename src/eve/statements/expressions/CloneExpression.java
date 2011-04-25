package eve.statements.expressions;

import eve.core.EveError;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;

public class CloneExpression extends ExpressionStatement implements EveStatement {
	private String identifier;
	
	public CloneExpression(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public EveObject execute() {
		EveObject proto = ScopeManager.getVariable(identifier);
		if (proto != null && proto.isCloneable()) {
			return proto.eveClone();
		}
		else {
			throw new EveError("prototype is undefined or uncloneable");
		}
	}
	
	@Override
	public boolean referencesClosure() {
		return super.analyzeForClosure(identifier);
	}

}
