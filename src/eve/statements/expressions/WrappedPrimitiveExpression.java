package eve.statements.expressions;

import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.statements.EveStatement;

public class WrappedPrimitiveExpression extends ExpressionStatement implements EveStatement {
	private Integer intOp;
	private String stringOp;
	private Double doubleOp;
	private Boolean booleanOp;
	private List<EveObject> listOp;
	
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
	
	public WrappedPrimitiveExpression(List<EveObject> op) {
		this.listOp = op;
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
		else if (listOp != null) {
			return new EveObject(listOp);
		}
		else {
			throw new EveError("unable to assign wrapped primitive");
		}
	}
		
	@Override
	public boolean referencesClosure() {
		return false;
	}
	
	private Object getOp() {
		if (intOp != null) {
			return intOp;
		}
		else if (stringOp != null) {
			return stringOp;
		}
		else if (doubleOp != null) {
			return doubleOp;
		}
		else if (booleanOp != null) {
			return booleanOp;
		}
		else if (listOp != null) {
			return listOp;
		}
		else {
			throw new EveError("unable to find operator");
		}	
	}
	
	@Override
	public String toString() {
		return "WrappedPrimitive(" + getOp().toString() + ")"; 
	}

}
