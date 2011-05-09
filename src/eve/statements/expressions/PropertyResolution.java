package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.core.builtins.EveDictionary;
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
		EveObject eo = obj.getField(getIdentifier());
		
		if (eo == null) {
			throw new EveError("property " + getIdentifier() + " of " + getExpression().toString() + " is undefined");
		}
		
		//getter functionality.
		if (eo.hasField("get") && eo.getField("get").getType() == EveType.FUNCTION) {
			return eo.getField("get").invoke();
		}
		else {
			return eo;
		}
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
