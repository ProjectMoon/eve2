package eve.statements;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.interpreter.Interpreter;
import eve.scope.ConstructionScope;
import eve.statements.assignment.InitVariableStatement;
import eve.statements.expressions.ExpressionStatement;

public class IfStatement extends AbstractStatement implements EveStatement, ConstructionScope {
	private ExpressionStatement ifExpression;
	private List<EveStatement> ifBlock = new ArrayList<EveStatement>();
	private IfStatement childIf;
	
	public IfStatement(ExpressionStatement ifExpression) {
		this.ifExpression = ifExpression;
	}
	
	@Override
	public EveObject execute() {
		EveObject exprResult = ifExpression.execute();
		
		if (exprResult.getType() == EveType.BOOLEAN) {
			EveObject result = null;
						
			if (exprResult.getBooleanValue() == true) {
				Interpreter interp = new Interpreter();
				result = interp.executeStatements(ifBlock);
				
				//If we executed a return statement, we need to pump the return back up.
				if (interp.isReturned()) {
					this.pumpValue(result);
					return result;
				}
			}
			else {
				//This implements else-if and else statements.
				if (childIf != null) {
					result = childIf.execute();
				}
			}
			
			return result;
		}
		else {
			throw new EveError(exprResult + " must return a boolean value.");
		}
	}
	
	public void setChildIf(IfStatement childIf) {
		this.childIf = childIf;
	}
	
	public IfStatement getChildIf() {
		return childIf;
	}

	@Override
	public void addStatement(EveStatement statement) {
		ifBlock.add(statement);
	}
	
	@Override
	public String toString() {
		return "if (" + ifExpression.toString() + ")";
	}
	
	public List<String> getClosureVariables() {
		List<String> variables = new ArrayList<String>();
		
		for (EveStatement statement : ifBlock) {
			if (statement instanceof InitVariableStatement) {
				variables.add(((InitVariableStatement)statement).getIdentifier());
			}
		}
		
		return variables;
	}

	@Override
	public List<String> getIdentifiers() {
		ArrayList<String> idents = new ArrayList<String>();
		
		idents.addAll(ifExpression.getIdentifiers());
		
		for (EveStatement statement : ifBlock) {
			idents.addAll(statement.getIdentifiers());
		}
		
		if (childIf != null) {
			idents.addAll(childIf.getIdentifiers());
		}
		
		return idents;
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		for (EveStatement statement : ifBlock) {
			statement.closureAnalysis(closureList);
		}
		
		if (childIf != null) {
			childIf.closureAnalysis(closureList);
		}
	}
	

}
