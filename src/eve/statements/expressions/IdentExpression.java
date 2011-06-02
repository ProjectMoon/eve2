package eve.statements.expressions;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;
import eve.statements.VariableFindingStatement;
import eve.statements.assignment.Updateable;


public class IdentExpression extends ExpressionStatement implements EveStatement, VariableFindingStatement, Updateable {
	private String identifier;
	private boolean usingMutatorAccessor = true;
	
	public IdentExpression(String identifier) {
		this.identifier = identifier;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	@Override
	public EveObject execute() {
		EveObject eo = ScopeManager.getVariable(identifier);
			
		if (eo != null) {
			if (isUsingMutatorAccessor() && eo.hasField("get") && eo.getField("get").getType() == EveType.FUNCTION) {
				return eo.getField("get").invokeSelf(eo);
			}
			else {
				return eo;
			}
		}
		else {
			throw new EveError(identifier + " not defined at current scope.");
		}
	}
	
	@Override
	public void updateVariable(EveObject value) {
		String ident = getIdentifier();
		
		EveObject existingField = ScopeManager.getVariable(ident);
		
		if (existingField != null) {
			if (existingField.isSealed()) {
				throw new EveError("object is sealed.");
			}
			
			//setter functionality
			if (isUsingMutatorAccessor() && existingField.hasField("set") && existingField.getField("set").getType() == EveType.FUNCTION) {
				existingField.getField("set").invokeSelf(existingField, value);
			}
			else {
				ScopeManager.putVariable(ident, value);
			}
		}
	}
	
	@Override
	public boolean deleteVariable() {
		EveObject toDelete = ScopeManager.getVariable(getIdentifier());
		
		if (toDelete == null) {
			return false;
		}
		
		if (toDelete.isSealed()) {
			throw new EveError("sealed objects cannot be deleted.");
		}
		
		EveObject parent = toDelete.getObjectParent();
		String field = parent.getFieldName(toDelete);
		return parent.deleteField(field);
	}
		
	@Override
	public String toString() {
		return identifier;
	}

	@Override
	public List<String> getIdentifiers() {
		ArrayList<String> idents = new ArrayList<String>(1);
		idents.add(identifier);
		return idents;
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		//no need to do closure analysis for ident expression
		//because there's nothing to the closureList down to.
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
		IdentExpression other = (IdentExpression) obj;
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
