package eve.statements.expressions;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;


public class IdentExpression extends ExpressionStatement implements EveStatement {
	private String identifier;
	
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
			if (eo.hasField("get") && eo.getField("get").getType() == EveType.FUNCTION) {
				return eo.getField("get").invoke();
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
		//TODO need to analyze closure scope for IdentExpression.
	}
}
