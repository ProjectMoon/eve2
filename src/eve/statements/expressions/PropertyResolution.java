package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.statements.AbstractStatement;
import eve.statements.EveStatement;

public class PropertyResolution extends ExpressionStatement implements EveStatement {
	private ExpressionStatement expression;
	private String identifier;
	
	public PropertyResolution(ExpressionStatement expr, String identifier) {
		this.setExpression(expr);
		this.setIdentifier(identifier);
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		getExpression().closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		EveObject obj = getExpression().execute();
		return obj.getField(getIdentifier());
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>(1);
		idents.add(getIdentifier());
		return idents;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setExpression(ExpressionStatement expression) {
		this.expression = expression;
	}

	public ExpressionStatement getExpression() {
		return expression;
	}
	
	@Override
	public String toString() {
		return expression.toString() + "." + identifier;
	}

}
