package eve.statements.expressions;

import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.interpreter.ErrorHandler;
import eve.statements.EveStatement;

public class DivisionExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement exp1, exp2;
	
	public DivisionExpression(ExpressionStatement exp1, ExpressionStatement exp2) {
		this.exp1 = exp1;
		this.exp2 = exp2;
	}
	
	@Override
	public EveObject execute() {
		EveObject op1 = exp1.execute();
		EveObject op2 = exp2.execute();
		EveObject result = new EveObject();
		
		if (op1.getType() == EveType.INTEGER && op2.getType() == EveType.INTEGER) {
			//int / int = int math
			int res = op1.getIntValue() / op2.getIntValue();
			result.setIntValue(res);
		}
		else {
			//anything else = error
			ErrorHandler.operatorError("/", op1, op2);
		}
		
		return result;
	}

}
