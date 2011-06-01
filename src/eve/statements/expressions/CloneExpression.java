package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.statements.EveStatement;

public class CloneExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement expression;
	
	public CloneExpression(ExpressionStatement expression) {
		this.expression = expression;
	}

	@Override
	public EveObject execute() {
		EveObject eo = expression.execute();
		if (eo != null && eo.isCloneable()) {
			return eo.eveClone();
		}
		else {
			throw new EveError("prototype is undefined or uncloneable");
		}
	}
	
	@Override
	public String toString() {
		return "clone " + expression.toString();
	}

	@Override
	public List<String> getIdentifiers() {
		ArrayList<String> idents = new ArrayList<String>();
		idents.addAll(expression.getIdentifiers());
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
		CloneExpression other = (CloneExpression) obj;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		return true;
	}

}
