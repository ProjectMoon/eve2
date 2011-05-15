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
import eve.statements.VariableFindingStatement;
import eve.statements.assignment.Updateable;

public class PropertyResolution extends ExpressionStatement implements EveStatement, VariableFindingStatement, Updateable {
	private ExpressionStatement expression;
	private String identifier;
	private boolean usingMutatorAccessor = true;
	
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
		if (isUsingMutatorAccessor() && eo.hasField("get") && eo.getField("get").getType() == EveType.FUNCTION) {
			return eo.getField("get").invokeSelf(obj);
		}
		else {
			return eo;
		}
	}
	
	@Override
	public void updateVariable(EveObject value) {
		EveObject eo = getExpression().execute();
		String ident = getIdentifier();
		
		//setter functionality.
		EveObject existingField = eo.getField(ident);
		if (isUsingMutatorAccessor() && existingField != null && existingField.hasField("set") &&
				existingField.getField("set").getType() == EveType.FUNCTION) {
			existingField.getField("set").invokeSelf(existingField, value);
		}
		else {
			eo.putField(ident, value);
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

	@Override
	public boolean isUsingMutatorAccessor() {
		return usingMutatorAccessor;
	}

	@Override
	public void setUsingMutatorAccessor(boolean using) {
		usingMutatorAccessor = using;
	}
}
