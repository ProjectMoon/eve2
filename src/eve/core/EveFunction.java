package eve.core;

import java.util.ArrayList;
import java.util.List;

import eve.statements.EveStatement;

public class EveFunction {
	private List<EveStatement> statements = new ArrayList<EveStatement>();

	public void setStatements(List<EveStatement> statements) {
		this.statements = statements;
	}

	public List<EveStatement> getStatements() {
		return statements;
	}
	
}
