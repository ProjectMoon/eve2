package eve.statements.expressions;

import java.util.ArrayList;
import java.util.List;

import eve.core.EveFunction;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;
import eve.statements.PopScopeStatement;
import eve.statements.PushScopeStatement;

public class FunctionExpression extends ExpressionStatement implements EveStatement {
	private List<EveStatement> statements = new ArrayList<EveStatement>();
	
	public FunctionExpression() {}
	
	public FunctionExpression(List<EveStatement> statements) {
		this.statements = statements;
	}
	
	public void addStatement(EveStatement statement) {
		this.statements.add(statement);
	}
	
	@Override
	public EveObject execute() {
		EveFunction func = new EveFunction();
		EveObject eo = new EveObject();
		
		//We must switch to function scope before execution!
		func.addStatement(new PushScopeStatement(eo));
		func.addStatements(statements);
		func.addStatement(new PopScopeStatement());
		
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
