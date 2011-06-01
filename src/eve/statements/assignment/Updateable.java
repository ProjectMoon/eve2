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
	/**
	 * Update the given variable with a new value.
	 * @param value
	 */
	public void updateVariable(EveObject value);
	
	/**
	 * Delete this variable.
	 * @return true if the delete was a success, false otherwise.
	 */
	public boolean deleteVariable();
}
