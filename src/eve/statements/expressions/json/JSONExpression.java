package eve.statements.expressions.json;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.core.EveObjectFactory;
import eve.scope.ConstructionScope;
import eve.statements.EveStatement;
import eve.statements.expressions.ExpressionStatement;

public class JSONExpression extends ExpressionStatement implements EveStatement, ConstructionScope {
	private List<JSONEntry> entries = new ArrayList<JSONEntry>();
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addEntry(JSONEntry entry) {
		entries.add(entry);
	}
	
	@Override
	public void addStatement(EveStatement statement) {
		throw new UnsupportedOperationException("cannot add statements to an object literal");
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		for (JSONEntry entry : entries) {
			entry.closureAnalysis(closureList);
		}
	}

	@Override
	public EveObject execute() {
		EveObject eo = null;
		if (getName() != null) {
			eo = EveObjectFactory.customType(getName());
		}
		else {
			eo = EveObjectFactory.customType("object_literal");
		}
		
		for (JSONEntry entry : entries) {
			eo.putField(entry.getIdentifier(), entry.getExpression().execute());
		}
		
		return eo;
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>();
		
		for (JSONEntry entry : entries) {
			idents.addAll(entry.getIdentifiers());
		}
		
		return idents;
	}
	
	@Override
	public String toString() {
		String res = "{ ";
		for (JSONEntry entry : entries) {
			res += entry.toString() + ", ";
		}
		res += " }";
		
		return res;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entries == null) ? 0 : entries.hashCode());
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
		JSONExpression other = (JSONExpression) obj;
		if (entries == null) {
			if (other.entries != null)
				return false;
		} else if (!entries.equals(other.entries))
			return false;
		return true;
	}
}
