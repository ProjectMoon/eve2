package eve.statements;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObjectFactory;
import eve.interpreter.Interpreter;
import eve.scope.ConstructionScope;
import eve.scope.ScopeManager;

public class ScopeStatement extends AbstractStatement implements EveStatement, ConstructionScope {
	private String type;
	private List<EveStatement> statements = new ArrayList<EveStatement>();
	
	public ScopeStatement(String type) {
		this.type = type;
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		//this is if we are calling it from a top-level function.
		if (closureList == null) {
			closureList = new ArrayDeque<List<String>>();
		}

		closureList.push(getIdentifiers());
		for (EveStatement statement : statements) {
			statement.closureAnalysis(closureList);
		}
	}

	@Override
	public EveObject execute() {
		//for private scope statement, nothing escapes this scope object,
		//lest it be "exported" via typedef.
		if (type.equals("private")) {
			closureAnalysis(null); //have to call this here for closures to work, apparently.
			EveObject scope = EveObjectFactory.scopeType("private");
			ScopeManager.pushScope(scope);
			new Interpreter().executeStatements(statements);
			ScopeManager.popScope();
			
			return scope;
		}
		else if (type.equals("global")) {
			EveObject global = ScopeManager.getGlobalScope();
			ScopeManager.pushScope(global);
			new Interpreter().executeStatements(statements);
			ScopeManager.popScope();
			
			return global;
		}
		else {
			throw new EveError("unrecognized scope type " + type);
		}
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>();
		
		for (EveStatement statement : statements) {
			idents.addAll(statement.getIdentifiers());
		}
		
		return idents;
	}

	@Override
	public boolean equals(Object other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addStatement(EveStatement statement) {
		statements.add(statement);		
	}

}
