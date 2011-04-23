package eve.statements.expressions;

import java.util.ArrayList;
import java.util.List;

import eve.core.Function;
import eve.core.EveObject;
import eve.scope.ConstructionScope;
import eve.statements.EveStatement;

public class FunctionDefExpression extends ExpressionStatement implements EveStatement, ConstructionScope {
	private String name;
	private List<String> parameters = new ArrayList<String>();
	private List<EveStatement> statements = new ArrayList<EveStatement>();
	
	public FunctionDefExpression() {}
	
	public FunctionDefExpression(List<EveStatement> statements) {
		this.setStatements(statements);
	}
	
	public void addStatement(EveStatement statement) {
		this.getStatements().add(statement);
	}
	
	public void setStatements(List<EveStatement> statements) {
		this.statements = statements;
	}

	public List<EveStatement> getStatements() {
		return statements;
	}

	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	public List<String> getParameters() {
		return parameters;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public EveObject execute() {
		Function func = new Function();
		func.setName(name);
		func.addStatements(getStatements());
		func.setParameters(this.parameters);
		EveObject eo = new EveObject(func);
		return eo;
	}
	
	@Override
	public String toString() {
		String res = "FunctionDefStatement { ";
		for (EveStatement statement : getStatements()) {
			res += statement.toString() + ";";
		}
		res += "}";
		return res;
	}
}
