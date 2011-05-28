package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;

public class CloneExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement expression;
	
	public CloneExpression(ExpressionStatement expression) {
		this.expression = expression;
	}

	@Override
	public EveObject execute() {
		EveObject eo = expression.execute();
		if (eo != null && eo.isCloneable()) {
			return eo.eveClone();
		}
		else {
			throw new EveError("prototype is undefined or uncloneable");
		}
	}
	
	@Override
	public String toString() {
		return "clone " + expression.toString();
	}

	@Override
	public List<String> getIdentifiers() {
		ArrayList<String> idents = new ArrayList<String>();
		idents.addAll(expression.getIdentifiers());
		return idents;
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		//TODO need to analyze closure scope for CloneExpression.		
	}

}
