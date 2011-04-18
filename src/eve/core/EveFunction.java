package eve.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eve.scope.ScopeManager;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public class EveFunction {
	private List<EveStatement> statements = new ArrayList<EveStatement>();
	private String name;
	private List<String> parameters = new ArrayList<String>();

	public void setStatements(List<EveStatement> statements) {
		this.statements = statements;
	}

	public List<EveStatement> getStatements() {
		return statements;
	}
	
	public void addStatement(EveStatement statement) {
		this.statements.add(statement);
	}
	
	public void addStatements(Collection<EveStatement> statements) {
		this.statements.addAll(statements);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	public List<String> getParameters() {
		return parameters;
	}
	
	public void addParameter(String param) {
		parameters.add(param);
	}
}
