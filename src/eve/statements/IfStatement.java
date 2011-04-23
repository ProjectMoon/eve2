package eve.statements;

import java.util.ArrayList;
import java.util.List;

import org.antlr.grammar.v3.ANTLRv3Parser.throwsSpec_return;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.interpreter.ErrorHandler;
import eve.interpreter.Interpreter;
import eve.scope.ConstructionScope;
import eve.scope.ScopeManager;
import eve.statements.expressions.ExpressionStatement;

public class IfStatement extends AbstractStatement implements EveStatement, ConstructionScope {
	private ExpressionStatement ifExpression;
	private List<EveStatement> ifBlock = new ArrayList<EveStatement>();
	
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
			
			return result;
		}
		else {
			throw new EveError(exprResult + " must return a boolean value.");
		}
	}

	@Override
	public void addStatement(EveStatement statement) {
		ifBlock.add(statement);
	}
	

}
