package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.statements.EveStatement;

public class RangeExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement op1, op2;
	
	public RangeExpression(ExpressionStatement op1, ExpressionStatement op2) {
		this.op1 = op1;
		this.op2 = op2;
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		op1.closureAnalysis(closureList);
		op2.closureAnalysis(closureList);
	}

	@Override
	public EveObject execute() {
		EveObject val1 = op1.execute();
		EveObject val2 = op2.execute();
		
		if (val1.getType() != EveType.INTEGER || val2.getType() != EveType.INTEGER) {
			throw new EveError("both parts of a range expression must be ints");
		}
		
		int start = val1.getIntValue();
		int end = val2.getIntValue();
		
		ArrayList<EveObject> rangeList = new ArrayList<EveObject>();
		if (start < end) {
			rangeList.ensureCapacity(end - start);
			for (int c = start; c <= end; c++) {
				rangeList.add(new EveObject(c));
			}
		}
		else if (start > end) {
			rangeList.ensureCapacity(start - end);
			for (int c = start; c >= end; c--) {
				rangeList.add(new EveObject(c));
			}
		}
		else {
			rangeList.ensureCapacity(1);
			rangeList.add(new EveObject(0));
		}
		
		return new EveObject(rangeList);
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>();
		idents.addAll(op1.getIdentifiers());
		idents.addAll(op2.getIdentifiers());
		return idents;
	}

}
