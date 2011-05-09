package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
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
		
		if (from instanceof PropertyCollectionExpression) {
			executeForPropertyCollection(value);
		}
		else {
			if (to instanceof PropertyResolution) {
				EveObject eo = ((PropertyResolution)to).getExpression().execute();
				String ident = ((PropertyResolution)to).getIdentifier();
				eo.putField(ident, value);
			}
			else if (to instanceof IdentExpression) {
				String ident = ((IdentExpression)to).getIdentifier();
				ScopeManager.putVariable(ident, value);
			}
			else {
				throw new EveError("invalid right hand of push statement");
			}
		}
		
		return null;
	}
	
	private void executeForPropertyCollection(EveObject clonedPropCollection) {
		EveObject eo = to.execute();
		
		for (EveObject entry : clonedPropCollection.getListValue()) {
			String prop = entry.getField("key").toString();
			EveObject value = entry.getField("value");
			eo.putField(prop, value);
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
