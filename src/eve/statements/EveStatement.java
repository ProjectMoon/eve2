package eve.statements;

import eve.core.EveObject;

public interface EveStatement {
	public EveObject execute();
	public void setLine(int line);
	public int getLine();
}
