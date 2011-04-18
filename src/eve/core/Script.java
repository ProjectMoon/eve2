package eve.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eve.scope.ConstructionScope;
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
		try {
			for (EveStatement statement : statements) {
				statement.execute();
			}
		}
		catch (EveError e) {
			System.err.println(e);
			System.exit(1);
		}
	}
	
	public void testExecute() {
		for (EveStatement statement : statements) {
			System.out.println(statement);
		}		
	}
	
}
