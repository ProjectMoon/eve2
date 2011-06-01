package eve.statements.expressions.bool;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.interpreter.ErrorHandler;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public class OrExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement exp1, exp2;
	
	public OrExpression(ExpressionStatement exp1, ExpressionStatement exp2) {
		this.exp1 = exp1;
		this.exp2 = exp2;
	}
	
	public EveObject execute() {
		EveObject op1 = exp1.execute();
		EveObject op2 = exp2.execute();
		EveObject result = new EveObject();
		
		Object v1 = op1.getObjectValue();
		Object v2 = op2.getObjectValue();
		
		if (v1 instanceof Boolean && v2 instanceof Boolean) {
			result.setBooleanValue((Boolean)v1 || (Boolean)v2);
		}
		else {
			ErrorHandler.operatorError("||", op1, op2);
		}
		
		return result;
	}
	
	@Override
	public List<String> getIdentifiers() {
		ArrayList<String> idents = new ArrayList<String>();
		idents.addAll(exp1.getIdentifiers());
		idents.addAll(exp2.getIdentifiers());
		return idents;
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		exp1.closureAnalysis(closureList);
		exp2.closureAnalysis(closureList);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exp1 == null) ? 0 : exp1.hashCode());
		result = prime * result + ((exp2 == null) ? 0 : exp2.hashCode());
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
		OrExpression other = (OrExpression) obj;
		if (exp1 == null) {
			if (other.exp1 != null)
				return false;
		} else if (!exp1.equals(other.exp1))
			return false;
		if (exp2 == null) {
			if (other.exp2 != null)
				return false;
		} else if (!exp2.equals(other.exp2))
			return false;
		return true;
	}
}