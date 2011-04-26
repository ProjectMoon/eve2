package eve.statements;

import eve.core.EveError;
import eve.core.EveObject;

public abstract class AbstractStatement implements EveStatement {
	private int line;
	private EveObject pumpedValue;
	
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
	
	public void pumpValue(EveObject eo) {
		pumpedValue = eo;
	}
	
	public EveObject getPumpedValue() {
		return pumpedValue;
	}
	
	public abstract boolean referencesClosure();
	
	protected boolean analyzeForClosure(String identifier) {
		boolean isClosure = false;
		String[] split = identifier.split("::");
		if (split.length > 2) {
			throw new EveError("can only have one scope operator");
		}
		
		
		if (split.length == 2) {
			String scope = split[0];
			if (scope.equals("closure")) {
				isClosure = true;
			}
		}
	
		return isClosure;
	}

}
