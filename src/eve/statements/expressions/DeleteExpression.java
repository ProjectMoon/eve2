package eve.statements.expressions;

import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObjectFactory;
import eve.statements.EveStatement;
import eve.statements.assignment.Updateable;

public class DeleteExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement expr;

	public DeleteExpression(ExpressionStatement expr) {
		this.expr = expr;
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		expr.closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		if (!(expr instanceof Updateable)) {
			throw new EveError("invalid delete expression");
		}
		
		return EveObjectFactory.create(((Updateable)expr).deleteVariable());
		/*
		EveObject toDelete = expr.execute();
		EveObject parent = toDelete.getObjectParent();
		
		if (expr instanceof IndexedAccess) {
			EveObject index = ((IndexedAccess)expr).getAccessExpression().execute();
			return new EveObject(parent.deleteIndexedProperty(index));
		}
		else {
			String field = parent.getFieldName(toDelete);
			return new EveObject(parent.deleteField(field));
		}
		*/
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
		DeleteExpression other = (DeleteExpression) obj;
		if (expr == null) {
			if (other.expr != null)
				return false;
		} else if (!expr.equals(other.expr))
			return false;
		return true;
	}

}
