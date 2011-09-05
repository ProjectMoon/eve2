package eve.statements;

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
		//may not want to do it this way for private ones?
		//or maybe not at all...
		for (EveStatement statement : statements) {
			statement.closureAnalysis(closureList);
		}
	}

	@Override
	public EveObject execute() {
		//for private scope statement, nothing escapes this scope object,
		//lest it be exported.
		if (type.equals("private")) {
			EveObject scope = EveObject.customType("scope(private)");
			ScopeManager.pushScope(scope);
			new Interpreter().executeStatements(statements);
			ScopeManager.popScope();
			
			return scope;
		}
		else {
			return null;
		}
	}

	@Override
	public List<String> getIdentifiers() {
		// TODO Auto-generated method stub
		return null;
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
