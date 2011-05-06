package eve.statements.assignment;

import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.AbstractStatement;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;
import eve.statements.expressions.IdentExpression;
import eve.statements.expressions.IndexedAccess;
import eve.statements.expressions.PropertyResolution;

public class UpdateVariableStatement extends AbstractStatement implements EveStatement {
	private ExpressionStatement assignmentExpr, valueExpr;
	
	public UpdateVariableStatement(ExpressionStatement assignmentExpr, ExpressionStatement valueExpr) {
		this.assignmentExpr = assignmentExpr;
		this.valueExpr = valueExpr;
	}
	
	@Override
	public EveObject execute() {
		EveObject value = valueExpr.execute();
		
		if (assignmentExpr instanceof IdentExpression) {
			String ident = ((IdentExpression)assignmentExpr).getIdentifier();
			ScopeManager.putVariable(ident, value);
		}
		else if (assignmentExpr instanceof PropertyResolution) {
			EveObject eo = ((PropertyResolution)assignmentExpr).getExpression().execute();
			String ident = ((PropertyResolution)assignmentExpr).getIdentifier();
			eo.putField(ident, value);
		}
		else if (assignmentExpr instanceof IndexedAccess) {
			EveObject eo = ((IndexedAccess)assignmentExpr).getObjExpression().execute();
			EveObject index = ((IndexedAccess)assignmentExpr).getAccessExpression().execute();
			eo.setIndexedProperty(index.getIntValue(), value);
		}
		else {
			throw new EveError("invalid left side of assignment statement.");
		}
	
		return null;
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getIdentifiers() {
		// TODO Auto-generated method stub
		return null;
	}
}
