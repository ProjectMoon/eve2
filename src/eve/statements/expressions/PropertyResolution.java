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
		
		if (eo != null && eo.isSealed()) {
			throw new EveError("object is sealed.");
		}
		
		//setter functionality.
		EveObject existingField = eo.getField(ident);
		
		if (existingField != null && existingField.isMarkedForClone()) {
			existingField.deepClone();
		}
		
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((expression == null) ? 0 : expression.hashCode());
		result = prime * result
				+ ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + (usingMutatorAccessor ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyResolution other = (PropertyResolution) obj;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		if (usingMutatorAccessor != other.usingMutatorAccessor)
			return false;
		return true;
	}
}
