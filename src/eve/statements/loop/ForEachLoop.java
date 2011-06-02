package eve.statements.loop;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.interpreter.Interpreter;
import eve.scope.ConstructionScope;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public class ForEachLoop extends LoopStatement implements EveStatement, ConstructionScope {
	private List<EveStatement> statements = new ArrayList<EveStatement>();
	private String variable;
	private ExpressionStatement of;
	
	public ForEachLoop(String variable, ExpressionStatement of) {
		this.setVariable(variable);
		this.setOf(of);
	}
	
	public ForEachLoop(String variable, ExpressionStatement of, List<EveStatement> statements) {
		this(variable, of);
		this.setStatements(statements);
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		of.closureAnalysis(closureList);
		for (EveStatement statement : getStatements()) {
			statement.closureAnalysis(closureList);
		}
	}

	@Override
	public EveObject execute() {
		EveObject eo = getOf().execute();
		
		if (eo.getType() == EveType.LIST) {
			return executeForList(eo);
		}
		else if (eo.getType() == EveType.STRING) {
			return executeForString(eo);
		}
		else if (eo.getType() == EveType.DICT) {
			return executeForDict(eo);
		}
		else {
			return executeLoop(eo);
		}
	}

	private EveObject executeLoop(EveObject eo) {
		Map<String, EveObject> props = eo.getFields();
		
		for (Map.Entry<String, EveObject> entry : props.entrySet()) {
			ScopeManager.putVariable(variable, entry.getValue());
			EveObject retval = loop();
			if (retval != null) {
				return retval;
			}
		}
		
		return null;
	}

	private EveObject executeForString(EveObject eo) {
		char[] values = eo.getStringValue().toCharArray();
		
		for (char val : values) {
			ScopeManager.putVariable(variable, new EveObject(val));
			EveObject retval = loop();
			if (retval != null) {
				return retval;
			}
		}
		
		return null;
	}

	private EveObject executeForList(EveObject eo) {
		Map<Integer, EveObject> list = eo.getListMap();
		
		for (Map.Entry<Integer, EveObject> entry : list.entrySet()) {
			ScopeManager.putVariable(variable, new EveObject(entry.getValue()));
			EveObject retval = loop();
			if (retval != null) {
				return retval;
			}
		}
		
		return null;
	}
	
	private EveObject executeForDict(EveObject eo) {
		Map<String, EveObject> dict = eo.getDictionaryValue();
		
		for (Map.Entry<String, EveObject> entry : dict.entrySet()) {
			ScopeManager.putVariable(variable, new EveObject(entry.getKey()));
			EveObject retval = loop();
			if (retval != null) {
				return retval;
			}
		}
		
		return null;
	}
	
	private EveObject loop() {
		return new Interpreter().executeStatements(getStatements());
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>();
		idents.addAll(of.getIdentifiers());
		
		for (EveStatement statement : getStatements()) {
			idents.addAll(statement.getIdentifiers());
		}
		
		return idents;
	}

	@Override
	public void addStatement(EveStatement statement) {
		getStatements().add(statement);
	}

	public void setStatements(List<EveStatement> statements) {
		this.statements = statements;
	}

	public List<EveStatement> getStatements() {
		return statements;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public String getVariable() {
		return variable;
	}

	public void setOf(ExpressionStatement of) {
		this.of = of;
	}

	public ExpressionStatement getOf() {
		return of;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((of == null) ? 0 : of.hashCode());
		result = prime * result
				+ ((statements == null) ? 0 : statements.hashCode());
		result = prime * result
				+ ((variable == null) ? 0 : variable.hashCode());
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
		ForEachLoop other = (ForEachLoop) obj;
		if (of == null) {
			if (other.of != null)
				return false;
		} else if (!of.equals(other.of))
			return false;
		if (statements == null) {
			if (other.statements != null)
				return false;
		} else if (!statements.equals(other.statements))
			return false;
		if (variable == null) {
			if (other.variable != null)
				return false;
		} else if (!variable.equals(other.variable))
			return false;
		return true;
	}

}
