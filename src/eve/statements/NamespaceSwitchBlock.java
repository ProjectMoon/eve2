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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((namespace == null) ? 0 : namespace.hashCode());
		result = prime * result
				+ ((statement == null) ? 0 : statement.hashCode());
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
		NamespaceSwitchBlock other = (NamespaceSwitchBlock) obj;
		if (namespace == null) {
			if (other.namespace != null)
				return false;
		} else if (!namespace.equals(other.namespace))
			return false;
		if (statement == null) {
			if (other.statement != null)
				return false;
		} else if (!statement.equals(other.statement))
			return false;
		return true;
	}

}
