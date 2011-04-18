package eve.statements.assignment;

import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public abstract class AssignmentStatement implements EveStatement {

	@Override
	public abstract EveObject execute();


}
