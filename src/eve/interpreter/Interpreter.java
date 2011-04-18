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
	public static EveObject executeStatements(Collection<EveStatement> statements) {
		boolean returned = false;
		EveObject retval = null;
		for (EveStatement statement : statements) {
			retval = statement.execute();
			if (statement instanceof ReturnStatement) {
				returned = true;
				break;
			}
		}
		
		if (returned) {
			return retval;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Execute a single statement in the current scope.
	 * @param statement
	 * @return
	 */
	public static EveObject executeStatement(EveStatement statement) {
		List<EveStatement> oneStatement = new ArrayList<EveStatement>(1);
		oneStatement.add(statement);
		return executeStatements(oneStatement);
	}
}
