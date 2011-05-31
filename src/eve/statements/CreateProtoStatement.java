package eve.statements;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.builtins.BuiltinCommons;
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
		BuiltinCommons.initialize(proto);
		
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((protoBlock == null) ? 0 : protoBlock.hashCode());
		result = prime * result
				+ ((protoName == null) ? 0 : protoName.hashCode());
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
		CreateProtoStatement other = (CreateProtoStatement) obj;
		if (protoBlock == null) {
			if (other.protoBlock != null)
				return false;
		} else if (!protoBlock.equals(other.protoBlock))
			return false;
		if (protoName == null) {
			if (other.protoName != null)
				return false;
		} else if (!protoName.equals(other.protoName))
			return false;
		return true;
	}

}
