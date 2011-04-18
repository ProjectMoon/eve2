package eve.statements.expressions;

import java.util.ArrayList;
import java.util.List;

import eve.core.EveFunction;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;

public class FunctionExpression extends ExpressionStatement implements EveStatement {
	private List<EveStatement> statements = new ArrayList<EveStatement>();
	
	public FunctionExpression(List<EveStatement> statements) {
		this.statements = statements;
	}
	@Override
	public EveObject execute() {
		EveFunction func = new EveFunction();
		EveObject eo = new EveObject();
		func.setStatements(statements);
		eo.setFunctionValue(func);
		return eo;
	}
}
