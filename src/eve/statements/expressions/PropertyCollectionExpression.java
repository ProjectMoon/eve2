package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;

import eve.core.EveObject;
import eve.statements.EveStatement;

public class PropertyCollectionExpression extends ExpressionStatement implements EveStatement {
	private List<String> props;
	private ExpressionStatement objExpr;
	
	public PropertyCollectionExpression(ExpressionStatement objExpr, List<String> props) {
		this.objExpr = objExpr;
		this.props = props;
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		objExpr.closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		//Create a list of key-value pairs.
		EveObject dict = new EveObject(new HashMap<String, EveObject>());
		EveObject eo = objExpr.execute();
		
		for (String propName : eo.getFieldNames()) {
			if (props.contains(propName)) {
				dict.putDictValue(propName, eo.getField(propName));
			}
		}
		
		return dict;
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>();
		idents.addAll(props);
		idents.addAll(objExpr.getIdentifiers());
		return idents;
	}
	
	
}
