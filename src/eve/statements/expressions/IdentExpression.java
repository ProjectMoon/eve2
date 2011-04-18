package eve.statements.expressions;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;


public class IdentExpression extends ExpressionStatement implements EveStatement {
	private String identifier;
	
	public IdentExpression(String identifier) {
		this.identifier = identifier;
	}
	@Override
	public EveObject execute() {
		return ScopeManager.getVariable(identifier);
	}

}
