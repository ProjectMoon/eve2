package eve.statements.expressions.bool;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.core.EveObjectFactory;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public class InExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement op1, op2;
	
	public InExpression(ExpressionStatement op1, ExpressionStatement op2) {
		this.op1 = op1;
		this.op2 = op2;
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		op1.closureAnalysis(closureList);
		op2.closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		EveObject propName = op1.execute();
		if (propName.getType() != EveType.STRING) {
			throw new EveError("left hand of in expression must be a string");
		}
		
		EveObject eo = op2.execute();
		
		return EveObjectFactory.create(eo.hasField(propName.getStringValue()));
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>();
		idents.addAll(op1.getIdentifiers());
		idents.addAll(op2.getIdentifiers());
		return idents;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((op1 == null) ? 0 : op1.hashCode());
		result = prime * result + ((op2 == null) ? 0 : op2.hashCode());
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
		InExpression other = (InExpression) obj;
		if (op1 == null) {
			if (other.op1 != null)
				return false;
		} else if (!op1.equals(other.op1))
			return false;
		if (op2 == null) {
			if (other.op2 != null)
				return false;
		} else if (!op2.equals(other.op2))
			return false;
		return true;
	}
}
