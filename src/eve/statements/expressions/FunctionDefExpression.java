package eve.statements.expressions;

import java.util.ArrayList;
import java.util.List;

import eve.core.EveFunction;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;
import eve.statements.PopScopeStatement;
import eve.statements.PushScopeStatement;

public class FunctionDefExpression extends ExpressionStatement implements EveStatement {
	private List<EveStatement> statements = new ArrayList<EveStatement>();
	
	public FunctionDefExpression() {}
	
	public FunctionDefExpression(List<EveStatement> statements) {
		this.statements = statements;
	}
	
	public void addStatement(EveStatement statement) {
		this.statements.add(statement);
	}
	
	@Override
	public EveObject execute() {
		EveFunction func = new EveFunction();
		EveObject eo = new EveObject();
		func.addStatements(statements);
		eo.setFunctionValue(func);
		return eo;
	}
	
	@Override
	public String toString() {
		String res = "FunctionDefStatement { ";
		for (EveStatement statement : statements) {
			res += statement.toString() + ";";
		}
		res += "}";
		return res;
	}
}
