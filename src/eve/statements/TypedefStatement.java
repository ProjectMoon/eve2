package eve.statements;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObjectFactory;
import eve.core.EveObject.EveType;
import eve.core.builtins.BuiltinCommons;
import eve.eji.ExternalTypes;
import eve.statements.expressions.ExpressionStatement;

public class TypedefStatement extends AbstractStatement implements EveStatement {
	private String ident;
	private ExpressionStatement expr;
	private boolean extern;
	
	public TypedefStatement(String ident) {
		this.ident = ident;
		this.extern = false;
	}
	
	public TypedefStatement(String ident, boolean extern) {
		this.ident = ident;
		this.extern = extern;
	}
	
	public TypedefStatement(String ident, ExpressionStatement expr) {
		this.ident = ident;
		this.expr = expr;
		extern = false;
	}
	
	@Override
	public void closureAnalysis(Deque<List<String>> closureList) {
		if (expr != null) {
			expr.closureAnalysis(closureList);
		}
	}

	@Override
	public EveObject execute() {
		EveObject type = null;
		if (!extern) {
			if (expr != null) {
				type = expr.execute();
			}
			else {
				type = EveObjectFactory.customType(ident);
			}
		}
		else {
			type = ExternalTypes.getType(ident);
			if (type == null) {
				throw new EveError("could not find external type " + ident);
			}
		}
 
		type = type.eveClone();
		type.setInternalType(EveType.PROTOTYPE);
		type.setTypeName(ident);
		
		BuiltinCommons.addType(ident, type);
		
		return null;
	}

	@Override
	public List<String> getIdentifiers() {
		List<String> idents = new ArrayList<String>();
		
		if (expr != null) {
			idents.addAll(expr.getIdentifiers());
		}
		
		return idents;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expr == null) ? 0 : expr.hashCode());
		result = prime * result + (extern ? 1231 : 1237);
		result = prime * result + ((ident == null) ? 0 : ident.hashCode());
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
		TypedefStatement other = (TypedefStatement) obj;
		if (expr == null) {
			if (other.expr != null)
				return false;
		} else if (!expr.equals(other.expr))
			return false;
		if (extern != other.extern)
			return false;
		if (ident == null) {
			if (other.ident != null)
				return false;
		} else if (!ident.equals(other.ident))
			return false;
		return true;
	}
}
