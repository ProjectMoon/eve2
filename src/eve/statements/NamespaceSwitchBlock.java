package eve.statements;

import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.scope.ConstructionScope;
import eve.scope.ScopeManager;

public class NamespaceSwitchBlock extends AbstractStatement implements EveStatement, ConstructionScope {
	private String namespace;
	private EveStatement statement;
	
	public NamespaceSwitchBlock(String namespace) {
		this.namespace = namespace;
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		statement.closureAnalysis(closureList);		
	}

	@Override
	public EveObject execute() {
		ScopeManager.setNamespace(namespace);
		EveObject eo = statement.execute();
		ScopeManager.revertNamespace();
		return eo;
	}

	@Override
	public List<String> getIdentifiers() {
		return statement.getIdentifiers();
	}

	@Override
	public void addStatement(EveStatement statement) {
		if (this.statement != null) {
			throw new EveError("Can only add one statement to a namespace switch block");
		}
		
		this.statement = statement;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setStatement(EveStatement statement) {
		this.statement = statement;
	}

	public EveStatement getStatement() {
		return statement;
	}

}
