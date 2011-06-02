package eve.statements.assignment;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;
import eve.statements.VariableFindingStatement;
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
		EveObject existing = ScopeManager.getVariable(identifier);
		
		if (existing != null) {
			throw new EveError(identifier + " shadows or overwrites " + identifier + " at scope " + ScopeManager.getScopeForVariable(identifier));
		}
		
		EveObject result = getExpression().execute();
		
		//mark for clone if we are assigning an ident, property, etc.
		//this will make sure the new variable is deep cloned when necessary.
		//otherwise, we get reference problems.
		if (getExpression() instanceof VariableFindingStatement) {
			result = result.eventlessClone();
		}
		
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((expression == null) ? 0 : expression.hashCode());
		result = prime * result
				+ ((identifier == null) ? 0 : identifier.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InitVariableStatement other = (InitVariableStatement) obj;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}
}
