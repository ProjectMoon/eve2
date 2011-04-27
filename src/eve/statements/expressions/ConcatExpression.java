package eve.statements.expressions;

import java.util.ArrayList;
import java.util.List;

import eve.core.EveObject;
import eve.statements.EveStatement;

public class ConcatExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement exp1, exp2;
	
	public ConcatExpression(ExpressionStatement op1, ExpressionStatement op2) {
		this.exp1 = op1;
		this.exp2 = op2;
	}
	
	@Override
	public EveObject execute() {
		EveObject op1 = exp1.execute();
		EveObject op2 = exp2.execute();
		EveObject eo = new EveObject();
		eo.setStringValue(op1.toString() + op2.toString());
		return eo;
	}
	
	@Override
	public boolean referencesClosure() {
		return exp1.referencesClosure() || exp2.referencesClosure();
	}
	
	@Override
	public String toString() {
		return exp1.toString() + " ~ " + exp2.toString();
	}

	@Override
	public List<String> getIdentifiers() {
		ArrayList<String> idents = new ArrayList<String>();
		idents.addAll(exp1.getIdentifiers());
		idents.addAll(exp2.getIdentifiers());
		return idents;
	}
}
