package eve.statements.assignment;

import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public class InitVariableStatement extends AssignmentStatement implements EveStatement {
	
	public InitVariableStatement(String identifier, ExpressionStatement expression) {
		super(identifier, expression);
	}
}
