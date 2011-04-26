package eve.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

import eve.scope.ConstructionScope;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;

public class Script implements ConstructionScope {
	private List<EveStatement> statements = new ArrayList<EveStatement>();
	
	public Script() {
		
	}

	public void setStatements(List<EveStatement> statements) {
		this.statements = statements;
	}

	public List<EveStatement> getStatements() {
		return statements;
	}
	
	public void addStatement(EveStatement statement) {
		statements.add(statement);
	}
	
	public void addStatements(Collection<EveStatement> statements) {
		this.statements.addAll(statements);
	}
	
	public void execute() {
		for (EveStatement statement : statements) {
			try {
				statement.execute();
			}
			catch (EveError e) {
				System.err.println("(line " + statement.getLine() + ") " + e.getMessage());
				
				Deque<EveObject> stack = ScopeManager.getScopeStack();
				
				while (!stack.isEmpty()) {
					EveObject scope = stack.pop();
					System.err.println("\tat " + scope);
				}
				System.exit(1);	
			}
		}
	}
	
	public void testExecute() {
		for (EveStatement statement : statements) {
			System.out.println(statement);
		}		
	}
	
}
