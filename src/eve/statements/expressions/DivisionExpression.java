package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.interpreter.ErrorHandler;
import eve.statements.EveStatement;

public class DivisionExpression extends ExpressionStatement implements EveStatement {
	private ExpressionStatement exp1, exp2;
	
	public DivisionExpression(ExpressionStatement exp1, ExpressionStatement exp2) {
		this.exp1 = exp1;
		this.exp2 = exp2;
	}
	
	@Override
	public EveObject execute() {
		EveObject op1 = exp1.execute();
		EveObject op2 = exp2.execute();
		EveObject result = new EveObject();
		
		if (op1.getType() == EveType.INTEGER && op2.getType() == EveType.INTEGER) {
			//int / int = int math
			Integer res = op1.getIntValue() / op2.getIntValue();
			result.setIntValue(res);
		}
		else if (op1.getType() == EveType.DOUBLE && op2.getType() == EveType.DOUBLE) {
			Double res = op1.getDoubleValue() / op2.getDoubleValue();
			result.setDoubleValue(res);
		}
		else if (op1.getType() == EveType.DOUBLE && op2.getType() == EveType.INTEGER) {
			Double res = op1.getDoubleValue() / op2.getIntValue();
			result.setDoubleValue(res);
		}
		else if (op1.getType() == EveType.INTEGER && op2.getType() == EveType.DOUBLE) {
			Double res = op1.getIntValue() / op2.getDoubleValue();
			result.setDoubleValue(res);
		}
		else if (op1.getType() == EveType.JAVA && op2.getType() == EveType.JAVA) {
			result = handleForJava(op1.getJavaValue(), op2.getJavaValue());
		}
		else if (op1.getType() == EveType.INTEGER && op2.getType() == EveType.JAVA) {
			result = intAndJava(op1.getIntValue(), op2.getJavaValue());
		}
		else if (op1.getType() == EveType.JAVA && op2.getType() == EveType.INTEGER) {
			result = intAndJava(op1.getJavaValue(), op2.getIntValue());
		}
		else if (op1.getType() == EveType.DOUBLE && op2.getType() == EveType.JAVA) {
			result = doubleAndJava(op1.getDoubleValue(), op2.getJavaValue());
		}
		else if (op1.getType() == EveType.JAVA && op2.getType() == EveType.DOUBLE) {
			result = doubleAndJava(op1.getJavaValue()(), op2.getDoubleValue());
		}
		else {
			//anything else = error
			ErrorHandler.operatorError("/", op1, op2);
		}
		
		return result;
	}
	
	private EveObject handleForJava(Object op1, Object op2) {
		if (op1 instanceof Integer && op2 instanceof Integer) {
			return new EveObject((Integer)op1 / (Integer)op2);
		}
		else if (op1 instanceof Double && op2 instanceof Double) {
			return new EveObject((Double)op1 / (Double)op2);
		}
		else if (op1 instanceof Integer && op2 instanceof Double) {
			return new EveObject((Integer)op1 / (Double)op2);
		}
		else if (op1 instanceof Double && op2 instanceof Integer) {
			return new EveObject((Double)op1 / (Integer)op2);
		}
		else {
			//anything else = error
			throw new EveError("operator / is undefined for " + op1 + " and " + op2);
		}
	}
	
	private EveObject intAndJava(Integer op1, Object op2) {
		if (op2 instanceof Integer) {
			return new EveObject(op1 / (Integer)op2);
		}
		else if (op2 instanceof Double) {
			return new EveObject(op1 / (Double)op2);
		}
		else {
			throw new EveError("operator / is undefined for " + op1 + " and " + op2);
		}
	}
	
	private EveObject intAndJava(Object op1, Integer op2) {
		if (op1 instanceof Integer) {
			return new EveObject((Integer)op1 / op2);
		}
		else if (op1 instanceof Double) {
			return new EveObject((Double)op1 / op2);
		}
		else {
			throw new EveError("operator / is undefined for " + op1 + " and " + op2);
		}
	}
	
	private EveObject doubleAndJava(Double op1, Object op2) {
		if (op2 instanceof Integer) {
			return new EveObject(op1 / (Integer)op2);
		}
		else if (op2 instanceof Double) {
			return new EveObject(op1 / (Double)op2);
		}
		else {
			throw new EveError("operator / is undefined for " + op1 + " and " + op2);
		}		
	}
	
	private EveObject doubleAndJava(Object op1, Double op2) {
		if (op1 instanceof Integer) {
			return new EveObject((Integer)op1 / op2);
		}
		else if (op1 instanceof Double) {
			return new EveObject((Double)op1 / op2);
		}
		else {
			throw new EveError("operator / is undefined for " + op1 + " and " + op2);
		}		
	}
	
	@Override
	public String toString() {
		return exp1.toString() + " / " + exp2.toString();
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		exp1.closureAnalysis(closureList);
		exp2.closureAnalysis(closureList);
	}

	@Override
	public List<String> getIdentifiers() {
		ArrayList<String> idents = new ArrayList<String>();
		idents.addAll(exp1.getIdentifiers());
		idents.addAll(exp2.getIdentifiers());
		return idents;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exp1 == null) ? 0 : exp1.hashCode());
		result = prime * result + ((exp2 == null) ? 0 : exp2.hashCode());
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
		DivisionExpression other = (DivisionExpression) obj;
		if (exp1 == null) {
			if (other.exp1 != null)
				return false;
		} else if (!exp1.equals(other.exp1))
			return false;
		if (exp2 == null) {
			if (other.exp2 != null)
				return false;
		} else if (!exp2.equals(other.exp2))
			return false;
		return true;
	}
}
