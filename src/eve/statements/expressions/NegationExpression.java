package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.interpreter.ErrorHandler;
import eve.statements.EveStatement;

public class NegationExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement exp;
	
	public NegationExpression(ExpressionStatement exp) {
		this.exp = exp;
	}
	
	@Override
	public EveObject execute() {
		EveObject op = exp.execute();
		EveObject result = new EveObject();
		
		if (op.getType() == EveType.INTEGER) {
			//negate int
			Integer res = -op.getIntValue();
			result.setIntValue(res);
		}
		if (op.getType() == EveType.DOUBLE) {
			Double res = -op.getDoubleValue();
			result.setDoubleValue(res);
		}
		else {
			//anything else = error
			ErrorHandler.unaryOperatorError("-", op);
		}
		
		return result;
	}
		
	@Override
	public String toString() {
		return "-" + exp.toString();
	}

	@Override
	public List<String> getIdentifiers() {
		ArrayList<String> idents = new ArrayList<String>();
		idents.addAll(exp.getIdentifiers());
		return idents;
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		exp.closureAnalysis(closureList);
	}

}
