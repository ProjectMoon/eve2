package eve.statements.expressions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.core.Function;
import eve.scope.ConstructionScope;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;

public class FunctionDefExpression extends ExpressionStatement implements EveStatement, ConstructionScope {
	private String name;
	private List<String> parameters = new ArrayList<String>();
	private List<EveStatement> statements = new ArrayList<EveStatement>();
	private boolean isVarargs = false;
	private int varargsIndex;
	
	private Function func;
	
	//closure analysis
	private boolean isPossibleClosure = false;
	private boolean closureStatusKnown = false;
		
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

	public void setVarargs(boolean isVarargs) {
		this.isVarargs = isVarargs;
	}

	public boolean isVarargs() {
		return isVarargs;
	}

	public void setVarargsIndex(int varargsIndex) {
		this.varargsIndex = varargsIndex;
	}

	public int getVarargsIndex() {
		return varargsIndex;
	}

	@Override
	public EveObject execute() {
		closureAnalysis(null);
		func = new Function();
		func.setName(name);
		func.addStatements(getStatements());
		func.setParameters(this.parameters);
		
		if (isVarargs) {
			func.setVarargs(true);
			func.setVarargsIndex(varargsIndex);
		}
		
		if (isPossibleClosure) {
			func.setPossibleClosure(true);
		}

		//with statement scope?
		if (ScopeManager.getCurrentScope().getType() == EveType.SCOPE &&
				ScopeManager.getCurrentScope().getTypeName().equals(EveObject.WITH_STATEMENT_TYPENAME)) {
			EveObject with = ScopeManager.getCurrentScope();
			func.setWithScope(with);
			func.setClosure(true);
		}
		
		EveObject eo = new EveObject(func);
		eo.recursePossibleClosures(null);
		return eo;
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		//this is if we are calling it from a top-level function.
		if (closureList == null) {
			closureList = new ArrayDeque<List<String>>();
		}
		
		if (!this.closureStatusKnown) {
			this.closureStatusKnown = true;
			
			List<String> functionVariables = getIdentifiers();
			
			outer:
			for (List<String> variables : closureList) {
				for (String variable : variables) {
					if (functionVariables.contains(variable)) {
						//this function is possibly a closure!
						this.isPossibleClosure = true;
						break outer;
					}
				}
			}
			
			//now analyze sub functions.
			closureList.push(functionVariables);
			
			for (EveStatement statement : getStatements()) {
				statement.closureAnalysis(closureList);
			}
			
			closureList.pop();
		}
	}

	@Override
	public String toString() {
		String name = (getName() != null) ? getName() : "function";
		return name + "(" + getParameters().toString() + ")";
	}
	
	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>(getParameters()); //defensive copy!
		
		for (EveStatement statement : getStatements()) {
			idents.addAll(statement.getIdentifiers());
		}
		
		return idents;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((func == null) ? 0 : func.hashCode());
		result = prime * result + (isVarargs ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result
				+ ((statements == null) ? 0 : statements.hashCode());
		result = prime * result + varargsIndex;
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
		FunctionDefExpression other = (FunctionDefExpression) obj;
		if (func == null) {
			if (other.func != null)
				return false;
		} else if (!func.equals(other.func))
			return false;
		if (isVarargs != other.isVarargs)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		if (statements == null) {
			if (other.statements != null)
				return false;
		} else if (!statements.equals(other.statements))
			return false;
		if (varargsIndex != other.varargsIndex)
			return false;
		return true;
	}
}
