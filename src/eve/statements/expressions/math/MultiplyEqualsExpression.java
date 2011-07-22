package eve.statements.expressions.math;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.statements.EveStatement;
import eve.statements.assignment.UpdateVariableStatement;
import eve.statements.expressions.ExpressionStatement;

public class MultiplyEqualsExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement expr1, expr2;
	
	public MultiplyEqualsExpression(ExpressionStatement expr1, ExpressionStatement expr2) {
		this.expr1 = expr1;
		this.expr2 = expr2;
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		expr1.closureAnalysis(closureList);
		expr2.closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		MultiplicationExpression mult = new MultiplicationExpression(expr1, expr2);
		UpdateVariableStatement update = new UpdateVariableStatement(expr1, mult);
		return update.execute();
	}


	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>();
		idents.addAll(expr1.getIdentifiers());
		idents.addAll(expr2.getIdentifiers());
		return idents;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expr1 == null) ? 0 : expr1.hashCode());
		result = prime * result + ((expr2 == null) ? 0 : expr2.hashCode());
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
		MultiplyEqualsExpression other = (MultiplyEqualsExpression) obj;
		if (expr1 == null) {
			if (other.expr1 != null)
				return false;
		} else if (!expr1.equals(other.expr1))
			return false;
		if (expr2 == null) {
			if (other.expr2 != null)
				return false;
		} else if (!expr2.equals(other.expr2))
			return false;
		return true;
	}

}
