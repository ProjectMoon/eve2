package eve.statements.expressions;

import eve.core.EveError;
import eve.core.EveObject;
import eve.statements.AbstractStatement;
import eve.statements.EveStatement;

public abstract class ExpressionStatement extends AbstractStatement implements EveStatement {
	public abstract EveObject execute();
}
