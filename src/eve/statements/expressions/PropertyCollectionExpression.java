package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.statements.EveStatement;

public class PropertyCollectionExpression extends ExpressionStatement implements EveStatement {
	private List<ExpressionStatement> props;
	private ExpressionStatement objExpr;
	
	public PropertyCollectionExpression(ExpressionStatement objExpr) {
		this.objExpr = objExpr;
	}
	
	public PropertyCollectionExpression(ExpressionStatement objExpr, List<ExpressionStatement> props) {
		this.objExpr = objExpr;
		this.props = props;
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		objExpr.closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		EveObject dict = EveObject.customType("property_collection");
		EveObject eo = objExpr.execute();
	
		if (props != null) {
			for (ExpressionStatement propExpr : props) {
				EveObject propObj = propExpr.execute();
				
				if (propObj.getType() != EveType.STRING) {
					throw new EveError(propExpr + " does not evaluate to string in object collection statement");
				}
				
				String prop = propObj.getStringValue();
				
				if (eo.getFieldNames().contains(prop)) {
					dict.putField(prop, eo.getField(prop));
				}
				else {
					throw new EveError("property " + prop + " not found on object " + eo);
				}
			}
		}
		else {
			//this supports the "all" version.
			for (String propName : eo.getFieldNames()) {
				dict.putField(propName, eo.getField(propName));
			}			
		}
		
		return dict;
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>();
		idents.addAll(objExpr.getIdentifiers());
		
		if (props != null) {
			for (ExpressionStatement statement : props) {
				idents.addAll(statement.getIdentifiers());
			}
		}
		return idents;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((objExpr == null) ? 0 : objExpr.hashCode());
		result = prime * result + ((props == null) ? 0 : props.hashCode());
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
		PropertyCollectionExpression other = (PropertyCollectionExpression) obj;
		if (objExpr == null) {
			if (other.objExpr != null)
				return false;
		} else if (!objExpr.equals(other.objExpr))
			return false;
		if (props == null) {
			if (other.props != null)
				return false;
		} else if (!props.equals(other.props))
			return false;
		return true;
	}
	
	
}
