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
		//create new eve object for scope.
		//eventless clone all idents
		//push scope
		EveObject with = EveObject.withStatementType();
		
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

}