package eve.statements.assignment;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.scope.ScopeManager;
import eve.statements.AbstractStatement;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;
import eve.statements.expressions.IdentExpression;
import eve.statements.expressions.IndexedAccess;
import eve.statements.expressions.PointerResolution;
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
			executeForPropertyResolution((PropertyResolution)assignmentExpr, value);
		}
		else if (assignmentExpr instanceof PointerResolution) {
			executeForPointerResolution((PointerResolution)assignmentExpr, value);
		}
		else if (assignmentExpr instanceof IndexedAccess) {
			EveObject eo = ((IndexedAccess)assignmentExpr).getObjExpression().execute();
			EveObject index = ((IndexedAccess)assignmentExpr).getAccessExpression().execute();
			
			if (index.getType() == EveType.INTEGER) {
				eo.setIndexedProperty(index.getIntValue(), value);
			}
			else if (index.getType() == EveType.STRING) {
				eo.putDictValue(index.getStringValue(), value);
			}
			else {
				throw new EveError("invalid indexed accessor type");
			}
		}
		else {
			throw new EveError("invalid left side of assignment statement.");
		}
	
		return null;
	}

	private void executeForPropertyResolution(PropertyResolution propertyResolution, EveObject value) {
		EveObject eo = propertyResolution.getExpression().execute();
		String ident = propertyResolution.getIdentifier();
		
		//setter functionality.
		EveObject existingField = eo.getField(ident);
		if (existingField != null && existingField.hasField("set") && existingField.getField("set").getType() == EveType.FUNCTION) {
			existingField.getField("set").invoke(value);
		}
		else {
			eo.putField(ident, value);
		}
	}
	
	private void executeForPointerResolution(PointerResolution propertyResolution, EveObject value) {
		EveObject eo = propertyResolution.getExpression().execute();
		String ident = propertyResolution.getIdentifier();
		eo.putField(ident, value);
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		assignmentExpr.closureAnalysis(closureList);
		valueExpr.closureAnalysis(closureList);
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>();
		idents.addAll(assignmentExpr.getIdentifiers());
		idents.addAll(valueExpr.getIdentifiers());
		return idents;
	}
}
