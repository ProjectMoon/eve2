package eve.statements.expressions.math;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.interpreter.ErrorHandler;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public class NegationExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement exp;
	
	public NegationExpression(ExpressionStatement exp) {
		this.exp = exp;
	}
	
	@Override
	public EveObject execute() {
		EveObject op = exp.execute();
		EveObject result = new EveObject();
		
		if (op.getType() == EveType.INTEGER) {
			//negate int
			Integer res = -op.getIntValue();
			result.setValue(res);
		}
		else if (op.getType() == EveType.DOUBLE) {
			Double res = -op.getDoubleValue();
			result.setValue(res);
		}
		else {
			//anything else = error
			ErrorHandler.unaryOperatorError("negation", op);
		}
		
		return result;
	}
		
	@Override
	public String toString() {
		return "-" + exp.toString();
	}

	@Override
	public List<String> getIdentifiers() {
		ArrayList<String> idents = new ArrayList<String>();
		idents.addAll(exp.getIdentifiers());
		return idents;
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		exp.closureAnalysis(closureList);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exp == null) ? 0 : exp.hashCode());
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
		NegationExpression other = (NegationExpression) obj;
		if (exp == null) {
			if (other.exp != null)
				return false;
		} else if (!exp.equals(other.exp))
			return false;
		return true;
	}

}
