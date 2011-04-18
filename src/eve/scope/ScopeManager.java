package eve.scope;

import java.util.Stack;

import eve.core.EveObject;
import eve.core.EveObject.EveType;

public class ScopeManager {
	private static EveObject globalScope;
	private static Stack<EveObject> scopeStack = new Stack<EveObject>();
	
	public static EveObject getCurrentScope() {
		return scopeStack.peek();
	}
	
	public static void pushScope(EveObject eo) {
		scopeStack.push(eo);
	}
	
	public static void popScope() {
		scopeStack.pop();
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
}
