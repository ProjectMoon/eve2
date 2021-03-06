package eve.statements.loop;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.interpreter.Interpreter;
import eve.scope.ConstructionScope;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public class WhileLoop extends LoopStatement implements EveStatement, ConstructionScope {
	private ExpressionStatement loopCondition;
	private List<EveStatement> statements = new ArrayList<EveStatement>();
	
	public WhileLoop(ExpressionStatement loopCondition) {
		this.loopCondition = loopCondition;
	}
	
	public WhileLoop(ExpressionStatement loopCondition, List<EveStatement> statements) {
		this(loopCondition);
		setStatements(statements);
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		for (EveStatement statement : getStatements()) {
			statement.closureAnalysis(closureList);
		}
	}

	@Override
	public EveObject execute() {
		EveObject result = loopCondition.execute();
		
		if (result.getType() == EveType.BOOLEAN) {
			while (result.getBooleanValue() == true) {
				EveObject retval = loop();
				
				if (retval != null) {
					return retval;
				}
				
				result = loopCondition.execute();
			}
		}
		else {
			throw new EveError("while loop condition must be a boolean expression");
		}
		
		return null;
	}
	
	private EveObject loop() {
		return new Interpreter().executeStatements(getStatements());
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = loopCondition.getIdentifiers();
		
		for (EveStatement statement : getStatements()) {
			idents.addAll(statement.getIdentifiers());
		}
		
		return idents;
	}

	@Override
	public void addStatement(EveStatement statement) {
		statements.add(statement);
	}

	public void setStatements(List<EveStatement> statements) {
		this.statements = statements;
	}

	public List<EveStatement> getStatements() {
		return statements;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((loopCondition == null) ? 0 : loopCondition.hashCode());
		result = prime * result
				+ ((statements == null) ? 0 : statements.hashCode());
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
		WhileLoop other = (WhileLoop) obj;
		if (loopCondition == null) {
			if (other.loopCondition != null)
				return false;
		} else if (!loopCondition.equals(other.loopCondition))
			return false;
		if (statements == null) {
			if (other.statements != null)
				return false;
		} else if (!statements.equals(other.statements))
			return false;
		return true;
	}

}
