package eve.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eve.interpreter.Interpreter;
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
	
	/**
	 * Execute this function.
	 * @return
	 */
	public EveObject execute() {
		return Interpreter.executeStatements(this.getStatements());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result
				+ ((statements == null) ? 0 : statements.hashCode());
		return result;
	}

	/**
	 * Determines if two functions are equal by function analysis.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) { 
			return true;
		}
		if (!(o instanceof EveFunction)) {
			return false;
		}
		
		//Function analysis: if all statements and parameter names are
		//equal, then the two functions are equal.
		EveFunction other = (EveFunction)o;
		boolean equal = true;
		
		//Parameters: length and names.
		if (this.getParameters().size() != other.getParameters().size()) {
			return false;
		}
		
		for (int c = 0; c < this.getParameters().size(); c++) {
			String thisParam = this.getParameters().get(c);
			String otherParam = other.getParameters().get(c);
			if (thisParam.equals(otherParam) == false) {
				return false;
			}
		}
		
		//Statement equality: delegates to statement equality methods.
		if (this.getStatements().size() != other.getStatements().size()) {
			return false;
		}
		
		for (int c = 0; c < this.getStatements().size(); c++) {
			EveStatement thisStatement = this.getStatements().get(c);
			EveStatement otherStatement = this.getStatements().get(c);
			if (thisStatement.equals(otherStatement) == false) {
				return false;
			}
		}
		
		//We have finally verified that these functions are equal.
		return true;
	}
}
