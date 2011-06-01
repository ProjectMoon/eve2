package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.statements.EveStatement;
import eve.statements.VariableFindingStatement;
import eve.statements.assignment.Updateable;

public class PointerResolution extends ExpressionStatement implements EveStatement, Updateable, VariableFindingStatement {
	private ExpressionStatement expression;
	private String identifier;
	
	public PointerResolution(ExpressionStatement expr, String identifier) {
		this.setExpression(expr);
		this.setIdentifier(identifier);
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		getExpression().closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		if (!(getExpression() instanceof VariableFindingStatement)) {
			throw new EveError(getExpression() + " is not valid for pointer resolution");
		}
		
		VariableFindingStatement vfinder = (VariableFindingStatement)getExpression();
		vfinder.setUsingMutatorAccessor(false);
		EveObject obj = vfinder.execute();
		EveObject eo = obj.getField(getIdentifier());
		
		if (eo == null) {
			throw new EveError("property " + getIdentifier() + " of " + getExpression().toString() + " is undefined");
		}
		
		return eo;
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
		return expression.toString() + "->" + identifier;
	}

	@Override
	public void updateVariable(EveObject value) {
		if (!(getExpression() instanceof VariableFindingStatement)) {
			throw new EveError(getExpression() + " is not valid for pointer resolution");
		}
		
		VariableFindingStatement vfinder = (VariableFindingStatement)getExpression();
		vfinder.setUsingMutatorAccessor(false);
		
		EveObject eo = vfinder.execute();
		String ident = getIdentifier();
		
		if (eo.isSealed()) {
			throw new EveError("object is sealed.");
		}
		
		EveObject existingField = eo.getField(ident);
		
		if (existingField != null) {		
			if (existingField.isMarkedForClone()) {
				existingField.deepClone();
			}
		}
		
		eo.putField(ident, value);
	}
	
	@Override
	public boolean deleteVariable() {
		EveObject eo = getExpression().execute();
		String ident = getIdentifier();
		
		if (eo.isSealed() || eo.isFrozen()) {
			throw new EveError("sealed/frozen objects cannot have properties removed.");
		}
		
		return eo.deleteField(ident);
	}

	@Override
	public boolean isUsingMutatorAccessor() {
		return false;
	}

	@Override
	public void setUsingMutatorAccessor(boolean using) {
		//Do nothing. PointerResolution never uses mutators and accessors.
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((expression == null) ? 0 : expression.hashCode());
		result = prime * result
				+ ((identifier == null) ? 0 : identifier.hashCode());
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
		PointerResolution other = (PointerResolution) obj;
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
		return true;
	}

}
