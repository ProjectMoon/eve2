package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.statements.AbstractStatement;
import eve.statements.EveStatement;

public class IndexedAccess extends ExpressionStatement implements EveStatement {
	private ExpressionStatement objExpression, accessExpression;
	
	public IndexedAccess(ExpressionStatement objExpr, ExpressionStatement accessExpr) {
		this.setObjExpression(objExpr);
		this.setAccessExpression(accessExpr);
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		getObjExpression().closureAnalysis(closureList);
		getAccessExpression().closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		EveObject eo = getObjExpression().execute();
		
		if (eo.getType() != EveType.LIST && eo.getType() != EveType.STRING) {
			throw new EveError(eo + " is not an indexed object.");			
		}
		
		EveObject index = getAccessExpression().execute();
		
		if (index.getType() != EveType.INTEGER) {
			throw new EveError("cannot use " + index + " to access " + eo);
		}
		
		return eo.getIndexedProperty(index.getIntValue());
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>();
		idents.addAll(getObjExpression().getIdentifiers());
		idents.addAll(getAccessExpression().getIdentifiers());	
		return idents;
	}

	public void setAccessExpression(ExpressionStatement accessExpression) {
		this.accessExpression = accessExpression;
	}

	public ExpressionStatement getAccessExpression() {
		return accessExpression;
	}

	public void setObjExpression(ExpressionStatement objExpression) {
		this.objExpression = objExpression;
	}

	public ExpressionStatement getObjExpression() {
		return objExpression;
	}

}
