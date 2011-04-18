package eve.statements;

import eve.core.EveObject;
import eve.statements.expressions.ExpressionStatement;

public class ReturnStatement extends AbstractStatement implements EveStatement {
	private ExpressionStatement expression;
	
	public ReturnStatement(ExpressionStatement expression) {
		this.expression = expression;
	}
	@Override
	public EveObject execute() {
		return expression.execute();
	}

}
