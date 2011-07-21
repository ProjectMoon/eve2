package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.statements.EveStatement;
import eve.statements.assignment.Updateable;

public class IndexedAccess extends ExpressionStatement implements EveStatement, Updateable {
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
		
		if (eo.getType() != EveType.LIST && eo.getType() != EveType.STRING && eo.getType() != EveType.DICT) {
			throw new EveError(eo + " is not an indexed object.");			
		}
		
		if (eo.getType() == EveType.LIST || eo.getType() == EveType.STRING) {
			EveObject index = getAccessExpression().execute();
			
			if (index.getType() != EveType.INTEGER) {
				throw new EveError("cannot use " + index + " to access " + eo);
			}
			
			return eo.getIndexedProperty(index.getIntValue());
		}
		else if (eo.getType() == EveType.DICT) {
			EveObject key = getAccessExpression().execute();
			
			if (key.getType() != EveType.STRING) {
				throw new EveError("cannot use " + key + " to access " + eo);
			}
			
			return eo.getDictValue(key.getStringValue());
		}
		else {
			return null;
		}
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

	@Override
	public void updateVariable(EveObject value) {
		EveObject eo = getObjExpression().execute();
		EveObject index = getAccessExpression().execute();
		
		if (eo.isSealed()) {
			throw new EveError("object is sealed.");
		}
		
		if (index.getType() == EveType.INTEGER) {
			EveObject existing = eo.getIndexedProperty(index.getIntValue());
			
			if (existing != null && existing.isMarkedForClone()) {
				existing.deepClone();
			}
			
			eo.setIndexedProperty(index.getIntValue(), value);
		}
		else if (index.getType() == EveType.STRING) {
			EveObject existing = eo.getDictValue(index.getStringValue());
			
			if (existing != null && existing.isMarkedForClone()) {
				existing.deepClone();
			}
			
			eo.putDictValue(index.getStringValue(), value);
		}
		else {
			throw new EveError("invalid indexed accessor type");
		}		
	}
	
	@Override
	public boolean deleteVariable() {
		EveObject eo = getObjExpression().execute();
		EveObject index = getAccessExpression().execute();
		
		if (eo.isSealed()) {
			throw new EveError("object is sealed.");
		}
		
		return eo.deleteIndexedProperty(index);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((accessExpression == null) ? 0 : accessExpression.hashCode());
		result = prime * result
				+ ((objExpression == null) ? 0 : objExpression.hashCode());
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
		IndexedAccess other = (IndexedAccess) obj;
		if (accessExpression == null) {
			if (other.accessExpression != null)
				return false;
		} else if (!accessExpression.equals(other.accessExpression))
			return false;
		if (objExpression == null) {
			if (other.objExpression != null)
				return false;
		} else if (!objExpression.equals(other.objExpression))
			return false;
		return true;
	}

}
