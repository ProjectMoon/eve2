package eve.statements.expressions.bool;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.interpreter.ErrorHandler;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public class OrExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement exp1, exp2;
	
	public OrExpression(ExpressionStatement exp1, ExpressionStatement exp2) {
		this.exp1 = exp1;
		this.exp2 = exp2;
	}
	
	public EveObject execute() {
		EveObject op1 = exp1.execute();
		EveObject op2 = exp2.execute();
		EveObject result = new EveObject();
		
		if (op1.getType() == EveType.BOOLEAN && op2.getType() == EveType.BOOLEAN) {
			result.setBooleanValue(op1.getBooleanValue() || op2.getBooleanValue());
		}
		else {
			ErrorHandler.operatorError("||", op1, op2);
		}
		
		return result;
	}
	
	@Override
	public List<String> getIdentifiers() {
		ArrayList<String> idents = new ArrayList<String>();
		idents.addAll(exp1.getIdentifiers());
		idents.addAll(exp2.getIdentifiers());
		return idents;
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		exp1.closureAnalysis(closureList);
		exp2.closureAnalysis(closureList);
	}
}