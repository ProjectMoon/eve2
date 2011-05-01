package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;

public class CloneExpression extends ExpressionStatement implements EveStatement {
	private String identifier;
	
	public CloneExpression(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public EveObject execute() {
		EveObject proto = ScopeManager.getVariable(identifier);
		if (proto != null && proto.isCloneable()) {
			return proto.eveClone();
		}
		else {
			throw new EveError("prototype is undefined or uncloneable");
		}
	}
	
	@Override
	public String toString() {
		return "clone " + identifier;
	}

	@Override
	public List<String> getIdentifiers() {
		ArrayList<String> idents = new ArrayList<String>(1);
		idents.add(identifier);
		return idents;
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		//TODO need to analyze closure scope for CloneExpression.		
	}

}
