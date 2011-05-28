package eve.statements;

public interface VariableFindingStatement extends EveStatement {
	public boolean isUsingMutatorAccessor();
	public void setUsingMutatorAccessor(boolean using);
}
