package eve.statements.expressions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObjectFactory;
import eve.statements.EveStatement;

public class WrappedPrimitiveExpression extends ExpressionStatement implements EveStatement {
	private Integer intOp;
	private String stringOp;
	private Double doubleOp;
	private Boolean booleanOp;
	private List<EveObject> listOp;
	private Map<String, EveObject> dictOp;
	private boolean nullValue = false;
	
	/**
	 * Initialize a new null value.
	 */
	public WrappedPrimitiveExpression() {
		nullValue = true;
	}
	
	public WrappedPrimitiveExpression(Integer op) {
		this.intOp = op;
	}
	
	public WrappedPrimitiveExpression(String op) {
		this.stringOp = op;
	}
	
	public WrappedPrimitiveExpression(Double op) {
		this.doubleOp = op;
	}
	
	public WrappedPrimitiveExpression(Boolean op) {
		this.booleanOp = op;
	}
	
	public WrappedPrimitiveExpression(List<EveObject> op) {
		this.listOp = op;
	}
	
	public WrappedPrimitiveExpression(Map<String, EveObject> op) {
		this.dictOp = op;
	}
	
	@Override
	public EveObject execute() {
		if (nullValue) {
			return EveObjectFactory.nullType();
		}
		else if (intOp != null) {
			return EveObjectFactory.create(intOp);
		}
		else if (stringOp != null) {
			return EveObjectFactory.create(stringOp);
		}
		else if (doubleOp != null) {
			return EveObjectFactory.create(doubleOp);
		}
		else if (booleanOp != null) {
			return EveObjectFactory.create(booleanOp);
		}
		else if (listOp != null) {
			return EveObjectFactory.create(listOp);
		}
		else {
			throw new EveError("unable to assign wrapped primitive");
		}
	}
			
	private Object getOp() {
		if (nullValue) {
			return EveObjectFactory.nullType();
		}
		else if (intOp != null) {
			return intOp;
		}
		else if (stringOp != null) {
			return stringOp;
		}
		else if (doubleOp != null) {
			return doubleOp;
		}
		else if (booleanOp != null) {
			return booleanOp;
		}
		else if (listOp != null) {
			return listOp;
		}
		else if (dictOp != null) {
			return dictOp;
		}
		else {
			throw new EveError("unable to find operator");
		}	
	}
	
	@Override
	public String toString() {
		Object op = getOp();
		return "WrappedPrimitive(" + op.toString() + ")";
	}

	@Override
	public List<String> getIdentifiers() {
		return new ArrayList<String>(0);
	}

	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((booleanOp == null) ? 0 : booleanOp.hashCode());
		result = prime * result
				+ ((doubleOp == null) ? 0 : doubleOp.hashCode());
		result = prime * result + ((intOp == null) ? 0 : intOp.hashCode());
		result = prime * result + ((listOp == null) ? 0 : listOp.hashCode());
		result = prime * result
				+ ((stringOp == null) ? 0 : stringOp.hashCode());
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
		WrappedPrimitiveExpression other = (WrappedPrimitiveExpression) obj;
		if (booleanOp == null) {
			if (other.booleanOp != null)
				return false;
		} else if (!booleanOp.equals(other.booleanOp))
			return false;
		if (doubleOp == null) {
			if (other.doubleOp != null)
				return false;
		} else if (!doubleOp.equals(other.doubleOp))
			return false;
		if (intOp == null) {
			if (other.intOp != null)
				return false;
		} else if (!intOp.equals(other.intOp))
			return false;
		if (listOp == null) {
			if (other.listOp != null)
				return false;
		} else if (!listOp.equals(other.listOp))
			return false;
		if (stringOp == null) {
			if (other.stringOp != null)
				return false;
		} else if (!stringOp.equals(other.stringOp))
			return false;
		return true;
	}

}
