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
		
		if (value.isCloneable()) {
			value = value.eveClone();
		}
		
		if (value.getInternalType() == EveType.CUSTOM) {
			executeForObject(value);
		}
		else if (value.getInternalType() == EveType.PROTOTYPE) {
			executeForType(value);
		}
		else if (value.getInternalType() == EveType.FUNCTION && value.getFunctionValue().isDelegateCreator()) {
			executeForDelegate(value);
		}
		else {
			throw new EveError("invalid left hand of push statement (type must be object, delegate, or type)");
		}
		
		return null;
	}
	
	private void executeForObject(EveObject obj) {
		EveObject eo = to.execute();

		for (Map.Entry<String, EveObject> entry : obj.getFields().entrySet()) {
			eo.putField(entry.getKey(), entry.getValue().getSelf());
		}
	}

	private void executeForType(EveObject type) {
		EveObject eo = to.execute();

		for (String field : type.getFieldNames()) {
			//make sure no one sneaks invalid crap in through getter methods. 
			EveObject value = type.getField(field).getSelf();

			//in the case of functions, only delegates are allowed to mix-in.
			if (value.getInternalType() == EveType.FUNCTION) {
				if (value.getFunctionValue().isDelegateCreator()) {
					EveObject delegatedMethod = createMixin(value);
					eo.putField(field, delegatedMethod);
				}
			}
			else {
				//everything else.
				value = value.eveClone();
				eo.putField(field, value);
			}
		}
	}
	
	private void executeForDelegate(EveObject delegate) {
		EveObject eo = to.execute();
		EveObject delegatedMethod = createMixin(delegate);
		eo.putField(delegatedMethod.getFunctionValue().getName(), delegatedMethod);
	}
	
	private EveObject createMixin(EveObject delegate) {
		EveObject delegatedMethod = delegate.eveClone();
		delegatedMethod.getFunctionValue().setDelegateCreator(false);
		delegatedMethod.getFunctionValue().setDelegate(false);
		delegatedMethod.getFunctionValue().setDelegateContext(null);
		return delegatedMethod;
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>();
		idents.addAll(from.getIdentifiers());
		idents.addAll(to.getIdentifiers());
		return idents;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
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
		PushExpression other = (PushExpression) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

}
