package eve.statements;

import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;

public abstract class AbstractStatement implements EveStatement {
	private int line;
	private EveObject pumpedValue;
	private boolean isReturned;
	
	@Override
	public abstract EveObject execute();
	
	@Override
	public abstract List<String> getIdentifiers();
	
	@Override
	public abstract boolean equals(Object other);
	
	@Override
	public abstract int hashCode();

	@Override
	public void setLine(int line) {
		this.line = line;
	}

	@Override
	public int getLine() {
		return line;
	}
	
	public void pumpValue(EveObject eo) {
		pumpedValue = eo;
	}
	
	public EveObject getPumpedValue() {
		return pumpedValue;
	}
	
	public boolean isReturned() {
		return isReturned;
	}
	
	public void setReturned(boolean isReturned) {
		this.isReturned = isReturned;
	}
}
