package eve.statements.assignment;

import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public class InitVariableStatement extends AssignmentStatement implements EveStatement {
	private String identifier;
	private ExpressionStatement expression;
	
	public InitVariableStatement(String identifier, ExpressionStatement expression) {
		this.identifier = identifier;
		this.expression = expression;
	}
	
	@Override
	public EveObject execute() {
		EveObject result = expression.execute();
		ScopeManager.putVariable(identifier, result);
		return result;
	}
	
	@Override
	public String toString() {
		return "InitVariableStatement: " + identifier + "=" + expression.toString();
	}
	
	@Override
	public boolean referencesClosure() {
		return super.analyzeForClosure(identifier) || expression.referencesClosure();
	}
}
