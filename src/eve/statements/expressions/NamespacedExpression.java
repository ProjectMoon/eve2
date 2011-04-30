package eve.statements.expressions;

import java.util.Deque;
import java.util.List;

import eve.core.EveObject;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;

public class NamespacedExpression extends ExpressionStatement implements EveStatement {
	private String namespace;
	private ExpressionStatement expression;
	
	public NamespacedExpression(String namespace, ExpressionStatement expression) {
		this.namespace = namespace;
		this.expression = expression;
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		expression.closureAnalysis(closureList);	
	}

	@Override
	public EveObject execute() {
		ScopeManager.setNamespace(namespace);
		EveObject eo = expression.execute();
		ScopeManager.revertNamespace();
		return eo;
	}

	@Override
	public List<String> getIdentifiers() {
		return expression.getIdentifiers();
	}
	
	@Override
	public String toString(){
		return namespace + "::" + expression.toString();
	}

}
