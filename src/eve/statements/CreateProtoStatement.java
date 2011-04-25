package eve.statements;

import java.util.ArrayList;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.interpreter.Interpreter;
import eve.scope.ConstructionScope;
import eve.scope.ScopeManager;

public class CreateProtoStatement extends AbstractStatement implements EveStatement, ConstructionScope {
	private List<EveStatement> protoBlock = new ArrayList<EveStatement>();
	private String protoName;
	private boolean referencesClosure;
	
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
		EveObject proto = EveObject.prototypeType(protoName);
		
		ScopeManager.pushScope(proto);
		Interpreter.executeStatements(protoBlock);
		ScopeManager.popScope();
		
		//This should be global.
		/*if (ScopeManager.getCurrentScope() == ScopeManager.getGlobalScope()) {
			ScopeManager.putVariable(protoName, proto);
		}
		else {
			throw new EveError("can't add prototype to non-global scope");
		}
		*/
		ScopeManager.putVariable(protoName, proto);
		return proto;
	}
	
	public boolean referencesClosure() {
		if (!referencesClosure) {
			for (EveStatement stmt : protoBlock) {
				referencesClosure = stmt.referencesClosure();
				if (referencesClosure) {
					break;
				}
			}
		}
		
		return referencesClosure;
	}

}
