package eve.statements.assignment;

import eve.core.EveError;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public class UpdateVariableStatement extends AssignmentStatement implements EveStatement {
	
	public UpdateVariableStatement(String identifier, ExpressionStatement expression) {
		super(identifier, expression);
	}
	
	@Override
	public EveObject execute() {
		//Verify that stuff exists.
		String identifier = this.getIdentifier();
		EveObject eo = ScopeManager.getVariable(identifier);
		
		if (eo == null && !identifier.contains(".") && !identifier.matches(".+\\[[0-9]+\\]*")) {
			throw new EveError("variable " + identifier + " does not exist in the current scope.");
		}
		
		return super.execute();
	}
}
