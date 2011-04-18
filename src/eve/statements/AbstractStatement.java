package eve.statements;

import eve.core.EveObject;

public abstract class AbstractStatement implements EveStatement {
	private int line;
	
	@Override
	public abstract EveObject execute();

	@Override
	public void setLine(int line) {
		this.line = line;
	}

	@Override
	public int getLine() {
		return line;
	}

}
