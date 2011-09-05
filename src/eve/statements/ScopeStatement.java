package eve.statements;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
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
		//may not want to do it this way for private ones?
		//or maybe not at all...
		closureList.push(getIdentifiers());
		for (EveStatement statement : statements) {
			statement.closureAnalysis(closureList);
		}
	}

	@Override
	public EveObject execute() {
		//for private scope statement, nothing escapes this scope object,
		//lest it be exported.
		if (type.equals("private")) {
			closureAnalysis(null);
			EveObject scope = EveObject.scopeType("private");
			ScopeManager.pushScope(scope);
			new Interpreter().executeStatements(statements);
			
			
			scope.recursePossibleClosures(null);
			ScopeManager.popScope();
			//we need to force anything that lives outside of the scope (aka typedefs)
			//to recurse for closures.
			
			return scope;
		}
		else {
			return null;
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
