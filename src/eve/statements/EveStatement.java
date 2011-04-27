package eve.statements;

import java.util.List;

import eve.core.EveObject;

public interface EveStatement {
	public EveObject execute();
	public void setLine(int line);
	public int getLine();
	public boolean referencesClosure();
	public List<String> getIdentifiers();
	public EveObject getPumpedValue();
	public void pumpValue(EveObject eo);
}
