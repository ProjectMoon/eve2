package eve.interpreter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eve.core.EveObject;
import eve.statements.EveStatement;
import eve.statements.ReturnStatement;

public class Interpreter {
	//Whether or not we returned early from execution.
	//That is, returned == true if we hit a return statement.
	private boolean returned = false;
	
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
			if (statement.getPumpedValue() != null) {
				returned = true;
				retval = statement.getPumpedValue();
				break;
			}
		}
		
		if (returned || retval != null) {
			return retval;
		}
		else {
			return null;
		}
	}
	
	public boolean isReturned() {
		return returned;
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
