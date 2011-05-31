package eve.statements;

import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.statements.expressions.ExpressionStatement;

public class SealStatement extends AbstractStatement implements EveStatement {
	private ExpressionStatement expr;
	
	public SealStatement(ExpressionStatement expr) {
		this.expr = expr;
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		expr.closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		EveObject eo = expr.execute();
		
		if (eo.isSealed()) {
			throw new EveError("the object is already sealed.");
		}
				
		eo.setSealed(true);
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
		SealStatement other = (SealStatement) obj;
		if (expr == null) {
			if (other.expr != null)
				return false;
		} else if (!expr.equals(other.expr))
			return false;
		return true;
	}
}
