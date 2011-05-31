package eve.statements.expressions;

import java.util.List;
import java.util.Map;

import eve.core.EveObject;
import eve.core.Function;
import eve.eji.EJIFunction;
import eve.statements.EveStatement;

public class DelegateDefExpression extends FunctionDefExpression implements EveStatement {
	public DelegateDefExpression() {
		super();
	}
	
	public DelegateDefExpression(List<EveStatement> statements) {
		super(statements);
	}
	
	@Override
	public EveObject execute() {
		EveObject delegateFunc = super.execute();
		delegateFunc.putField("create", new EveObject(new CreateDelegateFunction(delegateFunc)));
		return delegateFunc;
	}
	
	private class CreateDelegateFunction extends EJIFunction {
		private EveObject delegateFunc;
		
		public CreateDelegateFunction(EveObject delegateFunc) {
			setParameters("context");
			this.delegateFunc = delegateFunc;
		}
		
		@Override
		public EveObject execute(Map<String, EveObject> parameters) {
			EveObject context = parameters.get("context");

			EveObject delegateClone = delegateFunc.eventlessClone();
			
			Function delegate = delegateClone.getFunctionValue();
			delegate.setDelegate(true);
			delegate.setDelegateContext(context);
			
			return delegateClone;
		}
	}
}
