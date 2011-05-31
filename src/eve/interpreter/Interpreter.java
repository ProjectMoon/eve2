package eve.interpreter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eve.core.EveObject;
import eve.statements.EveStatement;
import eve.statements.ReturnStatement;

public class Interpreter {
	/**
	 * Execute arbitrary statements in the current scope. This method is delegated 
	 * to by anything that wants to execute large portions of code at once.
	 * @param statements
	 * @return
	 */
	public EveObject executeStatements(Collection<EveStatement> statements) {
		EveObject retval = null;
		for (EveStatement statement : statements) {
			retval = statement.execute();
			if (statement.isReturned()) {
				retval = statement.getPumpedValue();
				break;
			}
		}
		
		return retval;
	}
		
	/**
	 * Execute a single statement in the current scope.
	 * @param statement
	 * @return
	 */
	public EveObject executeStatement(EveStatement statement) {
		List<EveStatement> oneStatement = new ArrayList<EveStatement>(1);
		oneStatement.add(statement);
		return executeStatements(oneStatement);
	}
}
