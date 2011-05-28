package eve.statements.assignment;

import eve.core.EveObject;
import eve.statements.EveStatement;

/**
 * Statements that implement this interface indicate that they can do a variable
 * update. Property assignment, identifier assignment, etc all implement this
 * interface.
 * @author jeff
 *
 */
public interface Updateable extends EveStatement {
	public void updateVariable(EveObject value);
}
