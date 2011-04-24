package eve.scope;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;

public class ScopeManager {
	private static EveObject globalScope;
	private static EveObject parentScope; //right below the current scope in the stack.
	private static Stack<EveObject> scopeStack = new Stack<EveObject>();
	
	//construction only
	private static Stack<ConstructionScope> constructionScopeStack = new Stack<ConstructionScope>();
	private static ConstructionScope lastConstructionScope;
	
	//state.
	private static boolean jumpedScope;
	
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
	 * Analyze an identifier to determine if it has a scope operator.
	 * If it does have one, set the proper scope.
	 * @param name
	 * @return the identifier without the scope identifier.
	 */
	private static String scopeOperatorAnalysis(String name) {
		String[] split = name.split("::");
		if (split.length > 2) {
			throw new EveError("can only have one scope operator");
		}
		
		if (split.length == 2) {
			String scope = split[0];
			if (scope.equals("global")) {
				pushScope(getGlobalScope());
				jumpedScope = true;
				return split[1];
			}
			else {
				throw new EveError("unrecognized scope " + scope);
			}
		}
		else {
			return name;
		}
	}
	
	/**
	 * Fix the scope if and only if we pushed a new scope via :: operator.
	 */
	private static void scopeOperatorEnsure() {
		if (jumpedScope) {
			popScope();
			jumpedScope = false;
		}
	}
	
	private static List<Integer> indexOperatorAnalysis(String name) {
		if (name != null && name.matches(".+\\[[0-9]+\\]*")) {
			List<Integer> indices = new ArrayList<Integer>();
			Pattern pattern = Pattern.compile("\\[[0-9]+\\]");
			Matcher matcher = pattern.matcher(name);
			
			while (matcher.find()) {
				String indexStr = matcher.group();
				int start = indexStr.indexOf("[") + 1;
				int end = indexStr.indexOf("]");
				String index = indexStr.substring(start, end);
				indices.add(new Integer(index));
			}
			
			return indices;
		}
		else {
			return null;
		}
	}
	/**
	 * Gets the "parent" variable of a property in the current scope. When given
	 * a name such as "x.y.z", x.y will be returned. If there is no dot, then the
	 * scope above the current scope is returned. 
	 * @param name a valid identifier. e.g "x", "x.y".
	 * @return "parent" EveObject.
	 */
	public static EveObject getParentVariable(String name) {
		name = scopeOperatorAnalysis(name);
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
			
			scopeOperatorEnsure();
			return eo;
		}
		else {
			scopeOperatorEnsure();
			return parentScope;
		}
	}
	
	public static EveObject getVariable(String name) {
		name = scopeOperatorAnalysis(name);
		String[] split = name.split("\\.");
			
		if (split.length > 1) {
			String resolvedObj = split[0];
			
			EveObject eo = getCurrentScope().getField(resolvedObj);
			if (eo == null) {
				throw new EveError(resolvedObj + " is undefined");
			}
			
			for (int c = 1; c < split.length; c++) {
				String ident = split[c];
				indexOperatorAnalysis(name);
				eo = eo.getField(ident);
				
				if (eo == null && c != split.length - 1) {
					throw new EveError("property " + ident + " of " + resolvedObj + " is undefined");
				}
				
				resolvedObj += "." + ident;
			}
			
			scopeOperatorEnsure();
			return eo;
		}
		else {
			scopeOperatorEnsure();
			indexOperatorAnalysis(name);
			//System.out.println("index is " + index);
			return getCurrentScope().getField(name);
		}
	}
	
	public static void putVariable(String name, EveObject eo) {
		name = scopeOperatorAnalysis(name);
		String[] split = name.split("\\.");
		
		EveObject obj = getCurrentScope();
		
		//can either be property (dot) assignment, or simple assignment.
		if (split.length > 1) {
			String resolvedObj = split[0];
			obj = obj.getField(split[0]);
			if (obj == null) {
				throw new EveError(resolvedObj + " is undefined");
			}

			//resolve down to the object to the property before the one we want.
			for (int c = 1; c < split.length; c++) {
				name = split[c];
				
				//do this instead of looping to length - 1
				//so name can be assigned at least once.
				if (c == split.length - 1) {
					break;
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
			
			obj.putField(name, eo);
		}
		else {
			//simple non-property assignment.
			obj.putField(name, eo);
		}
		
		scopeOperatorEnsure();
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
		lastConstructionScope = constructionScopeStack.pop();
		return lastConstructionScope;
	}
	
	public static ConstructionScope getLastConstructionScope() {
		return lastConstructionScope;
	}
}
