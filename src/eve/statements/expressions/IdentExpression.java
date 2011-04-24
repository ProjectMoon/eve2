package eve.statements.expressions;
import eve.core.EveError;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;


public class IdentExpression extends ExpressionStatement implements EveStatement {
	private String identifier;
	
	public IdentExpression(String identifier) {
		this.identifier = identifier;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	@Override
	public EveObject execute() {
		EveObject eo = ScopeManager.getVariable(identifier);
		if (eo != null) {
			return eo;
		}
		else {
			throw new EveError(identifier + " not defined at current scope.");
		}
	}
	
	@Override
	public boolean referencesClosure() {
		return super.analyzeForClosure(identifier);
	}

}
