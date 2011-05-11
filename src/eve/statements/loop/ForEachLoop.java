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

public class ForEachLoop extends LoopStatement implements EveStatement, ConstructionScope {
	private List<EveStatement> statements = new ArrayList<EveStatement>();
	private String variable, of;
	
	public ForEachLoop(String variable, String of) {
		this.setVariable(variable);
		this.setOf(of);
	}
	
	public ForEachLoop(String variable, String of, List<EveStatement> statements) {
		this(variable, of);
		this.setStatements(statements);
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		for (EveStatement statement : getStatements()) {
			statement.closureAnalysis(closureList);
		}
	}

	@Override
	public EveObject execute() {
		EveObject eo = ScopeManager.getVariable(of);
		
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
		idents.add(of);
		
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

	public void setOf(String of) {
		this.of = of;
	}

	public String getOf() {
		return of;
	}

}
