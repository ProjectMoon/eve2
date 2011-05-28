package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.statements.EveStatement;
import eve.statements.VariableFindingStatement;
import eve.statements.assignment.Updateable;

public class PointerResolution extends ExpressionStatement implements EveStatement, Updateable, VariableFindingStatement {
	private ExpressionStatement expression;
	private String identifier;
	
	public PointerResolution(ExpressionStatement expr, String identifier) {
		this.setExpression(expr);
		this.setIdentifier(identifier);
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		getExpression().closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		if (!(getExpression() instanceof VariableFindingStatement)) {
			throw new EveError(getExpression() + " is not valid for pointer resolution");
		}
		
		VariableFindingStatement vfinder = (VariableFindingStatement)getExpression();
		vfinder.setUsingMutatorAccessor(false);
		EveObject obj = vfinder.execute();
		EveObject eo = obj.getField(getIdentifier());
		
		if (eo == null) {
			throw new EveError("property " + getIdentifier() + " of " + getExpression().toString() + " is undefined");
		}
		
		return eo;
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>(1);
		idents.add(getIdentifier());
		return idents;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setExpression(ExpressionStatement expression) {
		this.expression = expression;
	}

	public ExpressionStatement getExpression() {
		return expression;
	}
	
	@Override
	public String toString() {
		return expression.toString() + "->" + identifier;
	}

	@Override
	public void updateVariable(EveObject value) {
		if (!(getExpression() instanceof VariableFindingStatement)) {
			throw new EveError(getExpression() + " is not valid for pointer resolution");
		}
		
		VariableFindingStatement vfinder = (VariableFindingStatement)getExpression();
		vfinder.setUsingMutatorAccessor(false);
		
		EveObject eo = vfinder.execute();
		String ident = getIdentifier();
		
		EveObject existingField = eo.getField(ident);
		
		if (existingField != null && existingField.isMarkedForClone()) {
			existingField.deepClone();
		}
		
		eo.putField(ident, value);
	}

	@Override
	public boolean isUsingMutatorAccessor() {
		return false;
	}

	@Override
	public void setUsingMutatorAccessor(boolean using) {
		//Do nothing. PointerResolution never uses mutators and accessors.
	}

}
