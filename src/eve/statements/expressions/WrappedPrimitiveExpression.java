package eve.statements.expressions;

import eve.core.EveError;
import eve.core.EveObject;
import eve.statements.EveStatement;

public class WrappedPrimitiveExpression extends ExpressionStatement implements EveStatement {
	private Integer intOp;
	private String stringOp;
	
	public WrappedPrimitiveExpression(int op) {
		this.intOp = op;
	}
	
	public WrappedPrimitiveExpression(String op) {
		this.stringOp = op;
	}
	
	@Override
	public EveObject execute() {
		EveObject eo = new EveObject();
		
		if (intOp != null) {
			eo.setIntValue(intOp);
		}
		else if (stringOp != null) {
			eo.setStringValue(stringOp);
		}
		else {
			throw new EveError("unable to assign wrapped primitive");
		}
		
		return eo;
	}
	
	@Override
	public String toString() {
		if (intOp != null) {
			return intOp.toString();
		}
		else if (stringOp != null) {
			return stringOp.toString();
		}
		else {
			return "undefined";
		}
	}

}
