package eve.statements.expressions;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.IdentifierHelper;

import eve.core.EveError;
import eve.core.Function;
import eve.core.EveObject;
import eve.scope.ConstructionScope;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;

public class FunctionDefExpression extends ExpressionStatement implements EveStatement, ConstructionScope {
	private String name;
	private List<String> parameters = new ArrayList<String>();
	private List<EveStatement> statements = new ArrayList<EveStatement>();
	private boolean isClosureDef = false;
	private boolean isVarargs = false;
	private int varargsIndex;
	
	public FunctionDefExpression() {}
	
	public FunctionDefExpression(List<EveStatement> statements) {
		this.setStatements(statements);
	}
	
	public void addStatement(EveStatement statement) {
		this.getStatements().add(statement);
		if (!this.isClosureDef) {
			this.isClosureDef = analyzeForClosure(statement);
		}
	}
	
	public void setStatements(List<EveStatement> statements) {
		this.statements = statements;
		if (!this.isClosureDef) {
			this.isClosureDef = analyzeForClosure(statements);
		}
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
		Function func = new Function();
		func.setName(name);
		func.addStatements(getStatements());
		func.setParameters(this.parameters);
		
		if (isVarargs) {
			func.setVarargs(true);
			func.setVarargsIndex(varargsIndex);
		}
		
		if (isClosureDef) {
			func.setClosureStack(ScopeManager.createClosureStack());
		}
		
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
	
	private boolean analyzeForClosure(List<EveStatement> statements) {
		boolean isClosure = false;
		
		for (EveStatement stmt : statements) {
			isClosure = analyzeForClosure(stmt);
			if (isClosure) {
				break;
			}
		}
		
		return isClosure;
	}
	
	private boolean analyzeForClosure(EveStatement stmt) {
		return stmt.referencesClosure();
	}
	
	@Override
	public boolean referencesClosure() {
		return isClosureDef;
	}
}
