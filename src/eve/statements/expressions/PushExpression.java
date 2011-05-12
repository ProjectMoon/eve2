package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;

public class PushExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement from, to;
	
	public PushExpression(ExpressionStatement from, ExpressionStatement to) {
		this.from = from;
		this.to = to;
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		from.closureAnalysis(closureList);
		to.closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		EveObject value = from.execute();
		value = value.eventlessClone();
		
		if (value.getType() == EveType.DICT) {
			executeForPropertyCollection(value);
			return null;
		}
		else {
			throw new EveError("invalid left hand of push statement");
		}
	}
	
	private void executeForPropertyCollection(EveObject clonedPropCollection) {
		EveObject eo = to.execute();
		
		for (Map.Entry<String, EveObject> entry : clonedPropCollection.getDictionaryValue().entrySet()) {
			eo.putField(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>();
		idents.addAll(from.getIdentifiers());
		idents.addAll(to.getIdentifiers());
		return idents;
	}

}
