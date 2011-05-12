package eve.statements.assignment;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;
import eve.statements.expressions.FunctionDefExpression;
import eve.statements.expressions.IdentExpression;

public class InitVariableStatement extends AssignmentStatement implements EveStatement {
	private String identifier;
	private ExpressionStatement expression;
	
	public InitVariableStatement(String identifier, ExpressionStatement expression) {
		this.setIdentifier(identifier);
		this.setExpression(expression);
	}
	
	@Override
	public EveObject execute() {
		if (getExpression() instanceof IdentExpression) {
			throw new EveError("identifiers cannot be assigned directly. use clone.");
		}
		
		EveObject result = getExpression().execute();
		ScopeManager.putVariable(getIdentifier(), result);
		return result;
	}
	
	@Override
	public String toString() {
		return "var " + getIdentifier() + "=" + getExpression().toString();
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setExpression(ExpressionStatement expression) {
		this.expression = expression;
	}

	public ExpressionStatement getExpression() {
		return expression;
	}
	
	@Override
	public List<String> getIdentifiers() {
		ArrayList<String> idents = new ArrayList<String>();
		idents.add(identifier);
		
		if (!(expression instanceof FunctionDefExpression)) {
			idents.addAll(expression.getIdentifiers());
		}
		return idents;
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		expression.closureAnalysis(closureList);
	}
}
