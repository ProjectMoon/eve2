package eve.statements;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.statements.expressions.ExpressionStatement;

public class PropertyResolution extends AbstractStatement implements EveStatement {
	private ExpressionStatement expr;
	private String identifier;
	
	public PropertyResolution(ExpressionStatement expr, String identifier) {
		this.expr = expr;
		this.identifier = identifier;
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		expr.closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		EveObject obj = expr.execute();
		return obj.getField(identifier);
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>(1);
		idents.add(identifier);
		return idents;
	}

}
