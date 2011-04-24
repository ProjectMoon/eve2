package eve.statements.expressions.bool;

import eve.core.EveObject;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public class NotEqualsExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement exp1, exp2;
	
	public NotEqualsExpression(ExpressionStatement exp1, ExpressionStatement exp2) {
		this.exp1 = exp1;
		this.exp2 = exp2;
	}
	
	@Override
	public EveObject execute() {
		EveObject op1 = exp1.execute();
		EveObject op2 = exp2.execute();
		
		return new EveObject(op1.equals(op2) == false);
	}
	
	@Override
	public boolean referencesClosure() {
		return exp1.referencesClosure() || exp2.referencesClosure();
	}
}
