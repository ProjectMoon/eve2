package eve.statements;

import java.util.List;

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
	
	@Override
	public boolean referencesClosure() {
		return expression.referencesClosure();
	}

	@Override
	public List<String> getIdentifiers() {
		return expression.getIdentifiers();		
	}

}
