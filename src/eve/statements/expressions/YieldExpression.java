package eve.statements.expressions;

import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;

public class YieldExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement expr;
	
	public YieldExpression(ExpressionStatement expr) {
		this.expr = expr;
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		expr.closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		EveObject eo = expr.execute();
		ScopeManager.yieldTo(eo);
		System.out.println("resuming from yield for " + ScopeManager.getCurrentScope());
		return null;
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
		YieldExpression other = (YieldExpression) obj;
		if (expr == null) {
			if (other.expr != null)
				return false;
		} else if (!expr.equals(other.expr))
			return false;
		return true;
	}

}
