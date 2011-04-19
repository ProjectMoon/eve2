package eve.scope;

import java.util.Stack;

import eve.core.EveError;
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
		String[] split = name.split("\\.");
			
		if (split.length > 1) {
			String resolvedObj = split[0];
			
			EveObject eo = getCurrentScope();
			for (int c = 1; c < split.length; c++) {
				String ident = split[c];
				eo = eo.getField(ident);
				
				if (eo == null) {
					throw new EveError("property " + ident + " of " + resolvedObj + " is undefined");
				}
				
				resolvedObj += "." + ident;
			}
			
			return eo;
		}
		else {
			return getCurrentScope().getField(name);
		}
	}
	
	public static void putVariable(String name, EveObject eo) {
		String[] split = name.split("\\.");
		
		EveObject obj = getCurrentScope();
		
		if (split.length > 1) {
			String resolvedObj = split[0];
			obj = obj.getField(resolvedObj);
			if (obj == null) {
				throw new EveError(resolvedObj + " is undefined");
			}

			for (int c = 1; c < split.length; c++) {
				String ident = split[c];
				if (c == split.length - 1) {
					//potentially undefined property.
					//if so, we assign it to the next to last
					//object.
					EveObject prop = obj.getField(ident);
					if (prop == null) {
						name = ident;
						break;
					}
				}
				else {	
					name = ident;
					obj = obj.getField(ident);
					
					//allow undefined properties at the end, but not
					//during resolution.
					if (obj == null && c != split.length - 1) {
						throw new EveError("property " + ident + " of " + resolvedObj + " is undefined");
					}
					
					resolvedObj += "." + ident;
				}
			}
			
			if (obj == null) {
				throw new EveError(resolvedObj + " is undefined in current scope");
			}
		}
		else {
			EveObject prop = obj.getField(name);
			
			//Handle new assignment and update.
			if (prop != null) {
				obj = prop;
			}
		}
				
		obj.putField(name, eo);
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

	public static EveObject getPrototype(String name) {
		EveObject globalScope = getGlobalScope();
		return globalScope.getField(name);
	}
}
