package eve.statements.expressions;

import eve.core.EveObject;
import eve.statements.EveStatement;

public abstract class ExpressionStatement implements EveStatement {
	public abstract EveObject execute();
}
