package eve.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eve.statements.EveStatement;

public class ExecutionTree {
	private static List<EveStatement> statements = new ArrayList<EveStatement>();
	
	public ExecutionTree() {
		
	}

	public static void setStatements(List<EveStatement> statements) {
		ExecutionTree.statements = statements;
	}

	public static List<EveStatement> getStatements() {
		return statements;
	}
	
	public static void addStatement(EveStatement statement) {
		statements.add(statement);
	}
	
	public static void addStatements(Collection<EveStatement> statements) {
		ExecutionTree.statements.addAll(statements);
	}
	
	public static void execute() {
		for (EveStatement statement : statements) {
			statement.execute();
		}
	}
	
	public static void testExecute() {
		for (EveStatement statement : statements) {
			System.out.println(statement);
		}		
	}
	
}
