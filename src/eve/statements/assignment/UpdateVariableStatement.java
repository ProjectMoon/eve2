package eve.statements.assignment;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.statements.AbstractStatement;
import eve.statements.EveStatement;
import eve.statements.VariableFindingStatement;
import eve.statements.expressions.ExpressionStatement;

public class UpdateVariableStatement extends AbstractStatement implements EveStatement {
	private ExpressionStatement assignmentExpr, valueExpr;
	
	public UpdateVariableStatement(ExpressionStatement assignmentExpr, ExpressionStatement valueExpr) {
		this.assignmentExpr = assignmentExpr;
		this.valueExpr = valueExpr;
	}
	
	@Override
	public EveObject execute() {
		EveObject value = valueExpr.execute();
				
		if (!(assignmentExpr instanceof Updateable)) {
			throw new EveError("invalid left side of assignment statement.");
		}
		
		//for auto deep cloning.
		if (assignmentExpr instanceof VariableFindingStatement) {
			value = value.eveClone();
		}
		
		((Updateable)assignmentExpr).updateVariable(value);
				
		return null;
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		assignmentExpr.closureAnalysis(closureList);
		valueExpr.closureAnalysis(closureList);
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>();
		idents.addAll(assignmentExpr.getIdentifiers());
		idents.addAll(valueExpr.getIdentifiers());
		return idents;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((assignmentExpr == null) ? 0 : assignmentExpr.hashCode());
		result = prime * result
				+ ((valueExpr == null) ? 0 : valueExpr.hashCode());
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
		UpdateVariableStatement other = (UpdateVariableStatement) obj;
		if (assignmentExpr == null) {
			if (other.assignmentExpr != null)
				return false;
		} else if (!assignmentExpr.equals(other.assignmentExpr))
			return false;
		if (valueExpr == null) {
			if (other.valueExpr != null)
				return false;
		} else if (!valueExpr.equals(other.valueExpr))
			return false;
		return true;
	}
}
