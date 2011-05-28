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
		EveObject dict = new EveObject(new HashMap<String, EveObject>());
		EveObject eo = objExpr.execute();
	
		if (props != null) {
			for (ExpressionStatement propExpr : props) {
				EveObject propObj = propExpr.execute();
				
				if (propObj.getType() != EveType.STRING) {
					throw new EveError(propExpr + " does not evaluate to string in object collection statement");
				}
				
				String prop = propObj.getStringValue();
				
				if (eo.getFieldNames().contains(prop)) {
					dict.putDictValue(prop, eo.getField(prop));
				}
				else {
					throw new EveError("property " + prop + " not found on object " + eo);
				}
			}
		}
		else {
			//this supports the "all" version.
			for (String propName : eo.getFieldNames()) {
				dict.putDictValue(propName, eo.getField(propName));
			}			
		}
		
		return dict;
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>();
		idents.addAll(objExpr.getIdentifiers());
		
		for (ExpressionStatement statement : props) {
			idents.addAll(statement.getIdentifiers());
		}
		return idents;
	}
	
	
}
