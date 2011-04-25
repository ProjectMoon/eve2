package eve.statements.expressions.bool;

import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public class LessThanOrEqualToExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement exp1, exp2;
	
	public LessThanOrEqualToExpression(ExpressionStatement exp1, ExpressionStatement exp2) {
		this.exp1 = exp1;
		this.exp2 = exp2;
	}
	
	@Override
	public EveObject execute() {
		EveObject op1 = exp1.execute();
		EveObject op2 = exp2.execute();
		
		//the comparison operators work on int, string, and double.
		if (op1.getType() == EveType.INTEGER && op2.getType() == EveType.INTEGER) {
			return new EveObject(op1.getIntValue() <= op2.getIntValue());
		}
		else if (op1.getType() == EveType.DOUBLE && op2.getType() == EveType.DOUBLE) {
			return new EveObject(op1.getDoubleValue() <= op2.getDoubleValue());
		}
		else if (op1.getType() == EveType.DOUBLE && op2.getType() == EveType.INTEGER) {
			return new EveObject(op1.getDoubleValue() <= op2.getIntValue());
		}
		else if (op1.getType() == EveType.INTEGER && op2.getType() == EveType.DOUBLE) {
			return new EveObject(op1.getIntValue() <= op2.getDoubleValue());
		}
		else if (op1.getType() == EveType.STRING && op2.getType() == EveType.STRING) {
			return new EveObject(op1.getStringValue().compareTo(op2.getStringValue()) <= 0);
		}
		
		return null;
	}
	
	@Override
	public boolean referencesClosure() {
		return exp1.referencesClosure() || exp2.referencesClosure();
	}
}
