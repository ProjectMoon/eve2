package eve.scope;

import java.util.Stack;

import eve.core.EveObject;
import eve.core.EveObject.EveType;

public class ScopeManager {
	private static EveObject globalScope;
	private static Stack<EveObject> scopeStack = new Stack<EveObject>();
	
	//construction only
	private static Stack<ConstructionScope> constructionScopeStack = new Stack<ConstructionScope>();
	
	public static EveObject getCurrentScope() {
		return scopeStack.peek();
	}
	
	public static void pushScope(EveObject eo) {
		scopeStack.push(eo);
	}
	
	public static EveObject popScope() {
		EveObject prevScope = scopeStack.pop();
		prevScope.deleteTempFields();
		return prevScope;
	}
	
	public static EveObject getVariable(String name) {
		return getCurrentScope().getField(name);
	}
	
	public static void putVariable(String name, EveObject eo) {
		getCurrentScope().putField(name, eo);
	}
		
	public static boolean inFunction() {
		return getCurrentScope().getType() == EveType.FUNCTION;
	}
	
	public static EveObject getGlobalScope() {
		return globalScope;
	}
	
	public static void setGlobalScope(EveObject scope) {
		globalScope = scope;
	}
	
	public static void pushConstructionScope(ConstructionScope cs) {
		constructionScopeStack.push(cs);
	}
	
	public static ConstructionScope getCurrentConstructionScope() {
		return constructionScopeStack.peek();
	}
	
	public static ConstructionScope popConstructionScope() {
		return constructionScopeStack.pop();
	}
}
