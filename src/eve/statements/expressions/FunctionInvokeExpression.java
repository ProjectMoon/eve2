package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;

public class FunctionInvokeExpression extends ExpressionStatement implements EveStatement {
	private String identifier;
	private List<ExpressionStatement> parameters = new ArrayList<ExpressionStatement>();
	
	public FunctionInvokeExpression(String identifier, List<ExpressionStatement> parameters) {
		this.identifier = identifier;
		this.parameters = parameters;
	}
	
	public FunctionInvokeExpression(String identifier, ExpressionStatement parameter) {
		this.identifier = identifier;
		this.parameters.add(parameter);
	}
	
	public FunctionInvokeExpression(String identifier) {
		this.identifier = identifier;
		this.parameters = null;
	}
	
	@Override
	public EveObject execute() {
		EveObject result = new EveObject();
		EveObject funcVariable = ScopeManager.getVariable(identifier);
		
		//Pass down self for inner functions, and get the parent variable for
		//top level functions.
		EveObject objContext = ScopeManager.getVariable("self");
		if (objContext == null) {
			objContext = ScopeManager.getParentVariable(identifier);
		}
		
		if (funcVariable != null && funcVariable.getType() == EveType.FUNCTION) {
			List<EveObject> actualParameters = getActualParameters();
			funcVariable.putTempField("self", objContext);
			result = funcVariable.invoke(actualParameters);
		}
		
		return result;
	}
	
	private List<EveObject> getActualParameters() {
		if (parameters == null) {
			return null;
		}
		
		List<EveObject> params = new ArrayList<EveObject>(parameters.size());
		
		for (ExpressionStatement statement : parameters) {
			params.add(statement.execute());
		}
		
		return params;
	}
	
	@Override
	public String toString() {
		String res = identifier + "(";
		for (ExpressionStatement statement : parameters) {
			res += statement.toString() + ",";
		}
		//strip last ,
		if (res.endsWith(",")) {
			res = res.substring(0, res.length() - 1);
		}
		res += ")";
		return res;
	}
	
	@Override
	public List<String> getIdentifiers() {
		ArrayList<String> idents = new ArrayList<String>();
		for (ExpressionStatement expr : parameters) {
			idents.addAll(expr.getIdentifiers());
		}
		return idents;
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		for (ExpressionStatement expr : parameters) {
			expr.closureAnalysis(closureList);
		}
	}

}
