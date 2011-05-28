package eve.statements;

import java.util.Deque;
import java.util.List;

import eve.core.EveObject;

public interface EveStatement {
	public EveObject execute();
	public void setLine(int line);
	public int getLine();
	
	/**
	 * Retrieves the identifiers found in this statement. This method is used during
	 * closure analysis. Implementations of this method should return all variables in
	 * their statement and any variables in a block under them. The exception is function
	 * declarations. To prevent recursing into inner functions before it is time, function
	 * declarations will be skipped. That code is defined in AssignmentStatement.java 
	 * @return a list of identifiers.
	 */
	public List<String> getIdentifiers();
	
	/**
	 * Performs analysis on a FUNCTION statement to determine if the function being
	 * declared is possibly a closure. This method is defined at interface level to
	 * force every other EveStatement to have it. All of those implementations are
	 * passthroughs to the version defined in FunctionDefExpression.
	 * @param closureList The stack of closure variables found thus far.
	 */
	public void closureAnalysis(Deque<List<String>> closureList);
	public EveObject getPumpedValue();
	public void pumpValue(EveObject eo);
}
