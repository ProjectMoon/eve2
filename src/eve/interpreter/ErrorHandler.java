package eve.interpreter;

import eve.core.EveError;
import eve.core.EveObject;

public class ErrorHandler {
	public static void unaryOperatorError(String op, EveObject eo) {
		throw new EveError("operator " + op + " does not apply to " + eo.getInternalType());
	}
	
	public static void operatorError(String op, EveObject eo1, EveObject eo2) {
		throw new EveError("operator " + op + " does not apply to " + eo1.getInternalType() + " and " + eo2.getInternalType());
	}
}
