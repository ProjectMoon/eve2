package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;

public class FunctionInvokeExpression extends ExpressionStatement implements EveStatement {
	public ExpressionStatement funcExpr;
	private List<ExpressionStatement> parameters = new ArrayList<ExpressionStatement>();
	
	public FunctionInvokeExpression(ExpressionStatement funcExpr, List<ExpressionStatement> parameters) {
		this.funcExpr = funcExpr;
		this.parameters = parameters;
	}
	
	public FunctionInvokeExpression(ExpressionStatement funcExpr, ExpressionStatement parameter) {
		this.funcExpr = funcExpr;
		this.parameters.add(parameter);
	}
	
	public FunctionInvokeExpression(ExpressionStatement funcExpr) {
		this.funcExpr = funcExpr;
		this.parameters = null;
	}
	
	@Override
	public EveObject execute() {
		EveObject funcVariable = funcExpr.execute();
		
		if (funcVariable == null || (funcVariable.getType() != EveType.FUNCTION && funcVariable.getType() != EveType.PROTOTYPE)) {
			throw new EveError(funcVariable + " is not a function.");
		}
		
		//Pass down self for inner functions, and get the parent variable for
		//top level functions.
		EveObject objContext = ScopeManager.getVariable("self");
		if (objContext == null) {
			if (funcExpr instanceof PropertyResolution) {
				objContext = ((PropertyResolution)funcExpr).getExpression().execute();
			}
			else {
				objContext = ScopeManager.getCurrentScope();
			}
		}
		
		//Cannot have global as "self". Otherwise passing self into closures
		//gets screwed up.
		if (objContext == ScopeManager.getGlobalScope()) {
			objContext = null;
		}
		
		List<EveObject> actualParameters = getActualParameters();
		return funcVariable.invokeSelf(objContext, actualParameters);
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
		String res = funcExpr.toString() + "(";
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((funcExpr == null) ? 0 : funcExpr.hashCode());
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
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
		FunctionInvokeExpression other = (FunctionInvokeExpression) obj;
		if (funcExpr == null) {
			if (other.funcExpr != null)
				return false;
		} else if (!funcExpr.equals(other.funcExpr))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		return true;
	}

}
