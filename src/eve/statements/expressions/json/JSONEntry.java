package eve.statements.expressions.json;

import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public class JSONEntry extends ExpressionStatement implements EveStatement {
	private String identifier;
	private ExpressionStatement expression;
	
	public JSONEntry(String identifier, ExpressionStatement expression) {
		this.setIdentifier(identifier);
		this.setExpression(expression);
	}
	
	public String toString() {
		return getIdentifier() + ": " + getExpression().toString();
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		expression.closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		return null;
	}

	@Override
	public List<String> getIdentifiers() {
		return expression.getIdentifiers();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getExpression() == null) ? 0 : getExpression().hashCode());
		result = prime * result + ((getIdentifier() == null) ? 0 : getIdentifier().hashCode());
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
		JSONEntry other = (JSONEntry) obj;
		if (getExpression() == null) {
			if (other.getExpression() != null)
				return false;
		} else if (!getExpression().equals(other.getExpression()))
			return false;
		if (getIdentifier() == null) {
			if (other.getIdentifier() != null)
				return false;
		} else if (!getIdentifier().equals(other.getIdentifier()))
			return false;
		return true;
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

}
