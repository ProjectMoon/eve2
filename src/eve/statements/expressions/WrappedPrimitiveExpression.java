package eve.statements.expressions;

import eve.core.EveError;
import eve.core.EveObject;
import eve.statements.EveStatement;

public class WrappedPrimitiveExpression extends ExpressionStatement implements EveStatement {
	private Integer intOp;
	private String stringOp;
	private Double doubleOp;
	private Boolean booleanOp;
	
	public WrappedPrimitiveExpression(Integer op) {
		this.intOp = op;
	}
	
	public WrappedPrimitiveExpression(String op) {
		this.stringOp = op;
	}
	
	public WrappedPrimitiveExpression(Double op) {
		this.doubleOp = op;
	}
	
	public WrappedPrimitiveExpression(Boolean op) {
		this.booleanOp = op;
	}
	
	@Override
	public EveObject execute() {	
		if (intOp != null) {
			return new EveObject(intOp);
		}
		else if (stringOp != null) {
			return new EveObject(stringOp);
		}
		else if (doubleOp != null) {
			return new EveObject(doubleOp);
		}
		else if (booleanOp != null) {
			return new EveObject(booleanOp);
		}
		else {
			throw new EveError("unable to assign wrapped primitive");
		}
	}
	
	@Override
	public String toString() {
		if (intOp != null) {
			return intOp.toString();
		}
		else if (stringOp != null) {
			return stringOp.toString();
		}
		else if (doubleOp != null) {
			return doubleOp.toString();
		}
		else {
			return "undefined";
		}
	}

}
