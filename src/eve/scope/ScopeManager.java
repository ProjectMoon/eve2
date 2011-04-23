package eve.scope;

import java.util.Stack;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;

public class ScopeManager {
	private static EveObject globalScope;
	private static EveObject parentScope; //right below the current scope in the stack.
	private static Stack<EveObject> scopeStack = new Stack<EveObject>();
	
	//construction only
	private static Stack<ConstructionScope> constructionScopeStack = new Stack<ConstructionScope>();
	
	public static EveObject getCurrentScope() {
		return scopeStack.peek();
	}
	
	public static void pushScope(EveObject eo) {
		parentScope = (!scopeStack.isEmpty()) ? getCurrentScope() : null; 
		scopeStack.push(eo);
	}
	
	public static EveObject popScope() {
		EveObject prevScope = scopeStack.pop();
		parentScope = scopeStack.peek();
		prevScope.deleteTempFields();
		return prevScope;
	}
	
	/**
	 * Gets the "parent" variable of a property in the current scope. When given
	 * a name such as "x.y.z", x.y will be returned. If there is no dot, then the
	 * scope above the current scope is returned. 
	 * @param name a valid identifier. e.g "x", "x.y".
	 * @return "parent" EveObject.
	 */
	public static EveObject getParentVariable(String name) {
		String[] split = name.split("\\.");
		
		if (split.length > 1) {
			String resolvedObj = split[0];
			
			EveObject eo = getCurrentScope().getField(resolvedObj);
			if (eo == null) {
				throw new EveError(resolvedObj + " is undefined");
			}
			
			for (int c = 1; c < split.length - 1; c++) {
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
			return parentScope;
		}		
	}
	
	public static EveObject getVariable(String name) {
		String[] split = name.split("\\.");
			
		if (split.length > 1) {
			String resolvedObj = split[0];
			
			EveObject eo = getCurrentScope().getField(resolvedObj);
			if (eo == null) {
				throw new EveError(resolvedObj + " is undefined");
			}
			
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
				name = split[c];
				if (c == split.length - 1) {
					//we possibly have an undefined property at the end.
					//if so, break out of the loop before it can be assigned
					//to obj.
					EveObject prop = obj.getField(name);
					if (prop == null) {
						break;
					}
				}

				obj = obj.getField(name);
					
				//allow undefined properties at the end, but not
				//during resolution.
				if (obj == null && c != split.length - 1) {
					throw new EveError("property " + name + " of " + resolvedObj + " is undefined");
				}
					
				resolvedObj += "." + name;
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
}
