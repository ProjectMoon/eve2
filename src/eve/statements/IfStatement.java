package eve.statements;

import java.util.ArrayList;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.interpreter.Interpreter;
import eve.scope.ConstructionScope;
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
			EveObject result = new EveObject();
						
			if (exprResult.getBooleanValue() == true) {
				result = Interpreter.executeStatements(ifBlock);
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
	public boolean referencesClosure() {
		return ifExpression.referencesClosure();
	}
	

}
