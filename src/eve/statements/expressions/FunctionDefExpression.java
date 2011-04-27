package eve.statements.expressions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.core.Function;
import eve.scope.ConstructionScope;
import eve.statements.EveStatement;
import eve.statements.IfStatement;
import eve.statements.assignment.InitVariableStatement;

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
				
		EveObject eo = new EveObject(func);
		return eo;
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		//this is if we are calling it from a top-level function.
		//but this can be ........ problematic for inner functions!
		if (closureList == null) {
			closureList = new ArrayDeque<List<String>>();
		}
		
		if (!this.closureStatusKnown) {
			this.closureStatusKnown = true;
			
			List<String> functionVariables = getIdentifiers();
			
			for (List<String> variables : closureList) {
				for (String variable : variables) {
					if (functionVariables.contains(variable)) {
						//this function is possibly a closure!
						this.isPossibleClosure = true;
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
		return "def + " + name + "(" + getParameters().toString() + ")";
	}
	
	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>(getParameters()); //defensive copy!
		
		for (EveStatement statement : getStatements()) {
			idents.addAll(statement.getIdentifiers());
		}
		
		return idents;
	}
}
