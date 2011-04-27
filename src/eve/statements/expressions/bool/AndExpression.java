package eve.statements.expressions.bool;

import java.util.ArrayList;
import java.util.List;

import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.interpreter.ErrorHandler;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public class AndExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement exp1, exp2;
	
	public AndExpression(ExpressionStatement exp1, ExpressionStatement exp2) {
		this.exp1 = exp1;
		this.exp2 = exp2;
	}
	
	public EveObject execute() {
		EveObject op1 = exp1.execute();
		EveObject op2 = exp2.execute();
		EveObject result = new EveObject();
		
		if (op1.getType() == EveType.BOOLEAN && op2.getType() == EveType.BOOLEAN) {
			result.setBooleanValue(op1.getBooleanValue() && op2.getBooleanValue());
		}
		else {
			ErrorHandler.operatorError("&&", op1, op2);
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		return exp1.toString() + " && " + exp2.toString();
	}
	
	@Override
	public boolean referencesClosure() {
		return exp1.referencesClosure() || exp2.referencesClosure();
	}

	@Override
	public List<String> getIdentifiers() {
		ArrayList<String> idents = new ArrayList<String>();
		idents.addAll(exp1.getIdentifiers());
		idents.addAll(exp2.getIdentifiers());
		return idents;
	}
}
