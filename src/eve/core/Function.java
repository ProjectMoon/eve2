package eve.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import eve.interpreter.Interpreter;
import eve.statements.EveStatement;

/**
 * Holds the internal information of an EveFunction: its name, statements, and
 * parameters. The interpreter does not really interact with this class directly
 * except to create a FunctionDefStatement. EveObject contains an invoke method
 * which delegates to this class.
 * @author jeff
 *
 */
public class Function {
	private List<EveStatement> statements = new ArrayList<EveStatement>();
	private String name;
	private List<String> parameters = new ArrayList<String>();
	private Deque<EveObject> closureStack = new ArrayDeque<EveObject>();
	private EveObject withScope;
	private boolean isVarargs;
	private int varargsIndex;
	private boolean isPossibleClosure = false;
	private boolean isClosure = false;
	private boolean isDelegate;
	private EveObject delegateContext;
	
	public void setStatements(List<EveStatement> statements) {
		this.statements = statements;
	}

	public List<EveStatement> getStatements() {
		return statements;
	}
	
	public void addStatement(EveStatement statement) {
		this.statements.add(statement);
	}
	
	public void addStatements(Collection<EveStatement> statements) {
		this.statements.addAll(statements);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	public List<String> getParameters() {
		return parameters;
	}
	
	public void addParameter(String param) {
		if (this.isVarargs) {
			throw new EveError("cannot add more parameters to a var-args function");
		}
		
		parameters.add(param);
	}
	
	public void addVarargsParameter(String param) {
		parameters.add(param);
		this.setVarargs(true);
		this.setVarargsIndex(parameters.size() - 1);
	}
	
	/**
	 * Execute this function.
	 * @return
	 */
	public EveObject execute() {
		return new Interpreter().executeStatements(this.getStatements());
	}
	
	@Override
	public String toString() {
		String res = "";
		if (isDelegate()) {
			res = "[delegate@" + getDelegateContext();
		}
		else {
			res = "[function";
		}
		
		if (name != null) {
			res += " " + name; 
		}
		
		res += "(";
		if (getParameters().size() > 0) {
			for (String param : getParameters()) {
				res += param + ", ";
			}
			
			res = res.substring(0, res.length() - 2);
			
			if (isVarargs) {
				res += " ...";
			}
		}
		res += ")]";
		return res;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result
				+ ((statements == null) ? 0 : statements.hashCode());
		return result;
	}

	/**
	 * Determines if two functions are equal by function analysis.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) { 
			return true;
		}
		if (!(o instanceof Function)) {
			return false;
		}
		
		//Function analysis: if all statements and parameter names are
		//equal, then the two functions are equal.
		Function other = (Function)o;
		
		//Parameters: length and names.
		if (this.getParameters().size() != other.getParameters().size()) {
			return false;
		}
		
		for (int c = 0; c < this.getParameters().size(); c++) {
			String thisParam = this.getParameters().get(c);
			String otherParam = other.getParameters().get(c);
			if (thisParam.equals(otherParam) == false) {
				return false;
			}
		}
		
		//Statement equality: delegates to statement equality methods.
		if (this.getStatements().size() != other.getStatements().size()) {
			return false;
		}
		
		for (int c = 0; c < this.getStatements().size(); c++) {
			EveStatement thisStatement = this.getStatements().get(c);
			EveStatement otherStatement = other.getStatements().get(c);
			if (thisStatement.equals(otherStatement) == false) {
				return false;
			}
		}
		
		//We have finally verified that these functions are equal.
		return true;
	}

		
	public boolean isClosure() {
		return this.isClosure;
	}
	
	public void setClosure(boolean isClosure) {
		this.isClosure = isClosure;
	}
	
	/**
	 * Returns true if this is a possible closure. Returns false if it closure status has
	 * been decided (use isClosure() to check for that).
	 * @return
	 */
	public boolean isPossibleClosure() {
		return this.isPossibleClosure;
	}
	
	public void setPossibleClosure(boolean isPossibleClosure) {
		this.isPossibleClosure = isPossibleClosure;
	}

	public void setClosureStack(Deque<EveObject> closureStack) {
		this.closureStack = closureStack;
	}
	
	public void setWithScope(EveObject withScope) {
		this.withScope = withScope;
	}
	
	public EveObject getWithScope() {
		return this.withScope;
	}
	
	public void pushClosures(Deque<EveObject> closures) {
		Iterator<EveObject> closureDesc = closures.descendingIterator();
		
		while (closureDesc.hasNext()) {
			EveObject eo = closureDesc.next();
			EveObject clone = eo.eveClone();
			clone.transferTempFields();
			closureStack.push(clone);	
		}
	}

	public void setVarargs(boolean isVarargs) {
		this.isVarargs = isVarargs;
	}

	public boolean isVarargs() {
		return isVarargs;
	}

	public Deque<EveObject> getClosureStack() {
		if (getWithScope() != null) {
			Deque<EveObject> closure = new ArrayDeque<EveObject>(this.closureStack);
			closure.addFirst(getWithScope());
			return closure;
		}
		else {
			return closureStack;
		}
	}

	public void setVarargsIndex(int varargsIndex) {
		this.varargsIndex = varargsIndex;
	}
	
	public int getVarargsIndex() {
		return this.varargsIndex;
	}

	public void setDelegate(boolean isDelegate) {
		this.isDelegate = isDelegate;
	}

	public boolean isDelegate() {
		return isDelegate;
	}

	public void setDelegateContext(EveObject delegateContext) {
		this.delegateContext = delegateContext;
	}

	public EveObject getDelegateContext() {
		return delegateContext;
	}
}
