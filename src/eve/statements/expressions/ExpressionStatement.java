package eve.statements.expressions;

import eve.core.EveObject;
import eve.statements.AbstractStatement;
import eve.statements.EveStatement;

public abstract class ExpressionStatement extends AbstractStatement implements EveStatement {
	public abstract EveObject execute();
	public abstract boolean equals(Object other);
	public abstract int hashCode();
}
