package eve.scope;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.core.Script;
import eve.core.builtins.BuiltinCommons;

public class ScopeManager {
	private static Deque<EveObject> closureScope;
	
	private static EveObject globalScope;
	private static Deque<EveObject> stack = new ArrayDeque<EveObject>(); //the call stack!
	
	//construction only
	private static Stack<ConstructionScope> constructionScopeStack = new Stack<ConstructionScope>();
	private static ConstructionScope lastConstructionScope;
	private static Script script;
		
	public static EveObject getCurrentScope() {
		return getScopeStack().peek();
	}
	
	public static void pushScope(EveObject eo) {
		getScopeStack().push(eo);
	}
	
	public static EveObject popScope() {
		EveObject prevScope = getScopeStack().pop();
		prevScope.deleteTempFields();
		return prevScope;
	}
	
	private static EveObject getObject(String name) {
		EveObject eo = null;
		
		if (closureScope != null) {
			for (EveObject closure : closureScope) {
				eo = closure.getField(name);
				if (eo != null) {
					return eo;
				}
			}
		}
			
		for (EveObject scope : getScopeStack()) {
			if (scope != getGlobalScope()) {
				eo = scope.getField(name);
				if (eo != null) {
					return eo;
				}
				
				//scope statements prevent searching from higher up
				//on the chain.
				if (scope.getType() == EveType.SCOPE) {
					break;
				}
			}
		}
		
		//look in the global scope if we are in the global scope OR if global scope
		//has been pushed on to the stack via scope(global).
		if (getCurrentScope() != null && getCurrentScope() == getGlobalScope()) {
			eo = getCurrentScope().getField(name);
		}
		
		//last resort: type pool
		if (eo == null) {
			eo = BuiltinCommons.getType(name);
		}
	
		return eo;
	}
	
	public static EveObject getScopeForVariable(String name) {
		EveObject eo = null;
		
		if (closureScope != null) {
			for (EveObject closure : closureScope) {
				eo = closure.getField(name);
				if (eo != null) {
					return closure;
				}
			}
		}
			
		for (EveObject scope : getScopeStack()) {
			if (scope != getGlobalScope()) {
				eo = scope.getField(name);
				if (eo != null) {
					return scope;
				}
				
				//scope statements prevent searching from higher up
				//on the chain.
				if (scope.getType() == EveType.SCOPE) {
					break;
				}
			}
		}
		
		//type pool is global.
		eo = BuiltinCommons.getType(name);
		if (eo != null) {
			return getGlobalScope();
		}
		
		//if all else fails, return the current scope.
		//this allows us to assign new variables.
		//it also covers the global scope
		return getCurrentScope();
	}
		
	public static EveObject getVariable(String name) {
		return getObject(name);	
	}
	
	/**
	 * Places a variable at the current scope. The name passed in must be a valid
	 * identifier.
	 * @param name The identifier to place at the current scope.
	 * @param eo The value to place at the current scope.
	 */
	public static void putVariable(String name, EveObject eo) {
		EveObject scope = getScopeForVariable(name);
		
		//this or condition prevents namespaced references in functions
		//from going to the temp fields map.
		if (!inFunction() || globalScope == scope) {
			scope.putField(name, eo);
		}
		else {
			scope.putTempField(name, eo);
		}
	}
		
	public static boolean inFunction() {
		return getCurrentScope().getType() == EveType.FUNCTION;
	}
	
	public static EveObject getGlobalScope() {
		return globalScope;
	}
		
	private static void setGlobalScope(EveObject scope) {
		globalScope = scope;
	}
	
	public static void createGlobalScope() {
		if (getGlobalScope() != null) {
			return;
		}
		
		EveObject global = EveObject.globalType();
		setGlobalScope(global);
		pushScope(global);
		putVariable("__global", global);
	}
	
	public static void setClosureStack(Deque<EveObject> closureScope) {
		ScopeManager.closureScope = closureScope;
	}
	
	public static Deque<EveObject> createClosureStack() {
		Deque<EveObject> closureStack = new ArrayDeque<EveObject>();
		
		//first add current closures if they exist.
		if (getClosureScope() != null) {
			Iterator<EveObject> closureDesc = getClosureScope().descendingIterator();
			
			while (closureDesc.hasNext()) {
				EveObject eo = closureDesc.next();
				EveObject clone = eo.eveClone();
				clone.transferTempFields();
				closureStack.push(clone);	
			}
		}
		
		Iterator<EveObject> desc = getScopeStack().descendingIterator();
		
		//now add any functions or other scopes.
		while (desc.hasNext()) {
			EveObject eo = desc.next();
			if ((eo.getType() == EveType.FUNCTION  || eo.getType() == EveType.SCOPE) && closureStack.contains(eo) == false) {
				EveObject clone = eo.eveClone();
				clone.transferTempFields();
				closureStack.push(clone);
			}
		}
		
		return closureStack;		
	}
	
	public static Deque<EveObject> getClosureScope() {
		return closureScope;
	}
	
	public static void pushConstructionScope(ConstructionScope cs) {
		constructionScopeStack.push(cs);
	}
	
	public static ConstructionScope getCurrentConstructionScope() {
		return constructionScopeStack.peek();
	}
	
	public static ConstructionScope popConstructionScope() {
		lastConstructionScope = constructionScopeStack.pop();
		return lastConstructionScope;
	}
	
	public static ConstructionScope getLastConstructionScope() {
		return lastConstructionScope;
	}

	public static Deque<EveObject> getScopeStack() {
		return stack;
	}

	public static void setScript(Script script) {
		ScopeManager.script = script;
	}

	public static Script getScript() {
		return script;
	}
}
