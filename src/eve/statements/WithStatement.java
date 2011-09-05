package eve.statements;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.interpreter.Interpreter;
import eve.scope.ConstructionScope;
import eve.scope.ScopeManager;

public class WithStatement extends AbstractStatement implements EveStatement, ConstructionScope {
	private List<String> withVariables;
	private List<EveStatement> withBlock = new ArrayList<EveStatement>();
	
	public WithStatement(List<String> withVariables) {
		this.withVariables = withVariables;
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		for (EveStatement statement : withBlock) {
			statement.closureAnalysis(closureList);
		}
	}

	@Override
	public EveObject execute() {
		//this is a bit ghetto; FunctionDefExpression will check for the with
		//statement type name in order to determine that a with statement is
		//being used...
		EveObject with = EveObject.scopeType(EveObject.WITH_STATEMENT_TYPENAME);
		
		for (String ident : withVariables) {
			EveObject eo = ScopeManager.getVariable(ident);
			eo = eo.eventlessClone();
			with.putField(ident, eo);
		}
		
		ScopeManager.pushScope(with);
		EveObject retval = new Interpreter().executeStatements(withBlock);
		with = ScopeManager.popScope();
		
		//Need to pump values from the with statement scope back into the current
		//scope. Eve has no block scope, but here we have a block...
		for (String field : with.getFieldNames()) {
			ScopeManager.putVariable(field, with.getField(field));
		}
		
		return retval;
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>(withVariables);
		
		for (EveStatement statement : withBlock) {
			idents.addAll(statement.getIdentifiers());
		}
		
		return idents;
	}

	@Override
	public void addStatement(EveStatement statement) {
		withBlock.add(statement);		
	}
	
	@Override
	public String toString() {
		String res = "with (";
		
		for (String var : withVariables) {
			res += var + ", ";
		}
		
		//remove ", "
		res = res.substring(0, res.length() - 2);
		res += ")";
		
		return res;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((withBlock == null) ? 0 : withBlock.hashCode());
		result = prime * result
				+ ((withVariables == null) ? 0 : withVariables.hashCode());
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
		WithStatement other = (WithStatement) obj;
		if (withBlock == null) {
			if (other.withBlock != null)
				return false;
		} else if (!withBlock.equals(other.withBlock))
			return false;
		if (withVariables == null) {
			if (other.withVariables != null)
				return false;
		} else if (!withVariables.equals(other.withVariables))
			return false;
		return true;
	}

}
