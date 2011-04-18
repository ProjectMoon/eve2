package eve.statements;

import eve.core.EveObject;
import eve.statements.expressions.ExpressionStatement;

public class PrintStatement extends AbstractStatement implements EveStatement {
	private ExpressionStatement expression;

	public PrintStatement(ExpressionStatement expression) {
		this.expression = expression;
	}
	
	@Override
	public EveObject execute() {
		System.out.println(expression.execute());
		return null;
	}

}
