package eve.statements;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.interpreter.Interpreter;
import eve.scope.ConstructionScope;
import eve.scope.ScopeManager;

public class CreateProtoStatement extends AbstractStatement implements EveStatement, ConstructionScope {
	private List<EveStatement> protoBlock = new ArrayList<EveStatement>();
	private String protoName;
	
	public CreateProtoStatement(String protoName) {
		this.protoName = protoName;
	}
	
	public CreateProtoStatement(String protoName, List<EveStatement> protoBlock) {
		this.protoName = protoName;
		this.protoBlock = protoBlock;
	}
	
	public void addStatement(EveStatement statement) {
		protoBlock.add(statement);
	}
	
	@Override
	public EveObject execute() {
		verifyNoReturns();
		EveObject proto = EveObject.customType(protoName);
		
		ScopeManager.pushScope(proto);
		new Interpreter().executeStatements(protoBlock);
		ScopeManager.popScope();

		ScopeManager.putVariable(protoName, proto);
		return proto;
	}
	
	private void verifyNoReturns() {
		for (EveStatement statement : protoBlock) {
			if (statement instanceof ReturnStatement) {
				throw new EveError("return statements not allowed in proto creation block"); 
			}
		}
	}

	@Override
	public List<String> getIdentifiers() {
		ArrayList<String> idents = new ArrayList<String>();
		
		for (EveStatement statement : protoBlock) {
			idents.addAll(statement.getIdentifiers());
		}
		
		return idents;
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		for (EveStatement statement : protoBlock) {
			statement.closureAnalysis(closureList);
		}
	}

}
