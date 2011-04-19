package eve.statements.assignment;

import eve.core.EveError;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public class UpdateVariableStatement extends AssignmentStatement implements EveStatement {
	private String identifier;
	private ExpressionStatement expression;
	
	public UpdateVariableStatement(String identifier, ExpressionStatement expression) {
		this.identifier = identifier;
		this.expression = expression;
	}
	
	@Override
	public EveObject execute() {
		/*
		EveObject eo = ScopeManager.getVariable(identifier);
		
		if (eo == null) {
			throw new EveError("variable " + identifier + " does not exist in the current scope.");
		}
		*/
				
		EveObject result = expression.execute();
		ScopeManager.putVariable(identifier, result);
		return result;
	}
	
	@Override
	public String toString() {
		return "InitVariableStatement: " + identifier + "=" + expression.toString();
	}

}
