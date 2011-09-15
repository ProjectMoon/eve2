package eve.statements.expressions;

import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.core.EveObjectFactory;
import eve.statements.EveStatement;

public class FreezeExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement expr;
	
	public FreezeExpression(ExpressionStatement expr) {
		this.expr = expr;
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		expr.closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		EveObject eo = expr.execute();
		
		if (eo.isFrozen() || eo.isSealed()) {
			return EveObjectFactory.create(false);
		}
		
		eo.setFrozen(true);
		return EveObjectFactory.create(true);
	}

	@Override
	public List<String> getIdentifiers() {
		return expr.getIdentifiers();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expr == null) ? 0 : expr.hashCode());
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
		FreezeExpression other = (FreezeExpression) obj;
		if (expr == null) {
			if (other.expr != null)
				return false;
		} else if (!expr.equals(other.expr))
			return false;
		return true;
	}
}
