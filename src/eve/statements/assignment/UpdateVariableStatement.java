package eve.statements.assignment;

import java.util.ArrayList;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;
import eve.statements.expressions.FunctionDefExpression;
import eve.statements.expressions.IdentExpression;

public class UpdateVariableStatement extends AssignmentStatement implements EveStatement {
	private String identifier;
	private ExpressionStatement expression;
	
	public UpdateVariableStatement(String identifier, ExpressionStatement expression) {
		this.identifier = identifier;
		this.expression = expression;
	}
	
	@Override
	public EveObject execute() {
		//Verify that stuff exists.
		EveObject eo = ScopeManager.getVariable(identifier);
		
		if (eo == null && !identifier.contains(".") && !identifier.matches(".+\\[[0-9]+\\]*")) {
			throw new EveError("variable " + identifier + " does not exist in the current scope.");
		}
		
		if (expression instanceof IdentExpression) {
			throw new EveError("identifiers cannot be assigned directly. use clone.");
		}
				
		EveObject result = expression.execute();
		ScopeManager.putVariable(identifier, result);
		return result;
	}
	
	@Override
	public String toString() {
		return identifier + "=" + expression.toString();
	}
	
	@Override
	public boolean referencesClosure() {
		return super.analyzeForClosure(identifier);
	}

	@Override
	public List<String> getIdentifiers() {
		ArrayList<String> idents = new ArrayList<String>();
		idents.add(identifier);
		if (!(expression instanceof FunctionDefExpression)) {
			idents.addAll(expression.getIdentifiers());
		}
		return idents;
	}

}
