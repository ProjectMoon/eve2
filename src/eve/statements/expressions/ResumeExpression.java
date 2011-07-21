package eve.statements.expressions;

import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;
import org.apache.commons.javaflow.Continuation;

public class ResumeExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement expr, withExpr;
	
	public ResumeExpression(ExpressionStatement expr, ExpressionStatement withExpr) {
		this.expr = expr;
		this.withExpr = withExpr;
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		expr.closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		EveObject coroutine = expr.execute();
		EveObject with = null;

		if (withExpr != null) {
			with = withExpr.execute();
		}
		
		ScopeManager.invokeCoroutine(coroutine, with);
		EveObject resumedValue = ScopeManager.getYieldedValue();
		return resumedValue;
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
		ResumeExpression other = (ResumeExpression) obj;
		if (expr == null) {
			if (other.expr != null)
				return false;
		} else if (!expr.equals(other.expr))
			return false;
		return true;
	}

}
