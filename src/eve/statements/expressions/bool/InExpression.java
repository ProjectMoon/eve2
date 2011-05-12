package eve.statements.expressions.bool;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public class InExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement op1, op2;
	
	public InExpression(ExpressionStatement op1, ExpressionStatement op2) {
		this.op1 = op1;
		this.op2 = op2;
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		op1.closureAnalysis(closureList);
		op2.closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		EveObject propName = op1.execute();
		if (propName.getType() != EveType.STRING) {
			throw new EveError("left hand of in expression must be a string");
		}
		
		EveObject eo = op2.execute();
		
		return new EveObject(eo.hasField(propName.getStringValue()));
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>();
		idents.addAll(op1.getIdentifiers());
		idents.addAll(op2.getIdentifiers());
		return idents;
	}
}
