package eve.statements;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.statements.expressions.ExpressionStatement;

public class IndexedAccess extends AbstractStatement implements EveStatement {
	private ExpressionStatement objExpr, accessExpr;
	
	public IndexedAccess(ExpressionStatement objExpr, ExpressionStatement accessExpr) {
		this.objExpr = objExpr;
		this.accessExpr = accessExpr;
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		objExpr.closureAnalysis(closureList);
		accessExpr.closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		EveObject eo = objExpr.execute();
		
		if (eo.getType() != EveType.LIST && eo.getType() != EveType.STRING) {
			throw new EveError(eo + " is not an indexed object.");			
		}
		
		EveObject index = accessExpr.execute();
		
		if (index.getType() != EveType.INTEGER) {
			throw new EveError("cannot use " + index + " to access " + eo);
		}
		
		return eo.getIndexedProperty(index.getIntValue());
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>();
		idents.addAll(objExpr.getIdentifiers());
		idents.addAll(accessExpr.getIdentifiers());	
		return idents;
	}

}
