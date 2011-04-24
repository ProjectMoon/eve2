package eve.statements.assignment;

import eve.core.EveObject;
import eve.statements.AbstractStatement;
import eve.statements.EveStatement;

public abstract class AssignmentStatement extends AbstractStatement implements EveStatement {
	@Override
	public abstract EveObject execute();

}
