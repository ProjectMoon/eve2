package eve.statements;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.statements.expressions.ExpressionStatement;

public class ReturnStatement extends AbstractStatement implements EveStatement {
	private ExpressionStatement expression;
	
	public ReturnStatement() {}
	
	public ReturnStatement(ExpressionStatement expression) {
		this.expression = expression;
	}
	
	@Override
	public EveObject execute() {
		if (expression != null) {
			this.pumpValue(expression.execute());
		}
		
		this.setReturned(true);
		return null;
	}

	@Override
	public List<String> getIdentifiers() {
		if (expression != null) {
			return expression.getIdentifiers();
		}
		else {
			return new ArrayList<String>(0);
		}
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		if (expression != null) {
			expression.closureAnalysis(closureList);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((expression == null) ? 0 : expression.hashCode());
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
		ReturnStatement other = (ReturnStatement) obj;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		return true;
	}

}
