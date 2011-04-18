package eve.statements.expressions;

import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.interpreter.ErrorHandler;
import eve.statements.EveStatement;

public class NegationExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement exp;
	
	public NegationExpression(ExpressionStatement exp) {
		this.exp = exp;
	}
	
	@Override
	public EveObject execute() {
		EveObject op = exp.execute();
		EveObject result = new EveObject();
		
		if (op.getType() == EveType.INTEGER) {
			//negate int
			int res = -op.getIntValue();
			result.setIntValue(res);
		}
		else {
			//anything else = error
			ErrorHandler.unaryOperatorError("-", op);
		}
		
		return result;
	}

}
