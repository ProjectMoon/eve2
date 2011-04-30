package eve.scope;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.core.Script;

public class ScopeManager {
	private static EveObject parentScope; //right below the current scope in the stack.
	private static Deque<EveObject> closureScope;
	
	//namespaces
	private static Map<String, EveObject> globalScopes = new HashMap<String, EveObject>();
	private static Deque<String> namespaceStack = new ArrayDeque<String>();
	private static Map<String, Deque<EveObject>> namespaces = new HashMap<String, Deque<EveObject>>();
	private static String previousNamespace; //right below current namespace on namespaceStack
	
	//construction only
	private static Stack<ConstructionScope> constructionScopeStack = new Stack<ConstructionScope>();
	private static ConstructionScope lastConstructionScope;
	private static Script script;
		
	/**
	 * Initialize the ScopeManager.
	 */
	static {
		//global is the referenceable pointer to the global namespace.
		//_global is the "actual" global namespace.
		Deque<EveObject> globalScope = new ArrayDeque<EveObject>();
		namespaces.put("global", globalScope);
		namespaces.put("_global", globalScope);
		setNamespace("_global");
	}
	
	public static EveObject getCurrentScope() {
		return getScopeStack().peek();
	}
	
	public static void pushScope(EveObject eo) {
		parentScope = (!getScopeStack().isEmpty()) ? getCurrentScope() : getGlobalScope(); 
		getScopeStack().push(eo);
	}
	
	public static EveObject popScope() {
		EveObject prevScope = getScopeStack().pop();
		parentScope = getScopeStack().peek();
		prevScope.deleteTempFields();
		return prevScope;
	}
	
	private static EveObject getObject(String name) {
		EveObject eo = null;
		if (getNamespace().equals("module")) {
			setNamespace(previousNamespace);
			eo = getGlobalScope().getField(name);
			revertNamespace();
			return eo;
		}
		
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
			}
			else {
				if (getNamespace().equals("global")) {
					eo = scope.getField(name);
					if (eo != null) {
						return eo;
					}					
				}
			}
		}
		
		if (getCurrentScope() != null && getCurrentScope() == getGlobalScope()) {
			return getCurrentScope().getField(name);
		}
	
		return null;
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
		String[] split = name.split("\\.");
		
		if (split.length > 1) {
			String resolvedObj = split[0];
			
			EveObject eo = getObject(resolvedObj);
			if (eo == null) {
				throw new EveError(resolvedObj + " is undefined");
			}
			
			for (int c = 1; c < split.length - 1; c++) {
				String ident = split[c];
				List<Integer> indices = indexOperatorAnalysis(ident);
				if (indices != null) {
					eo = eo.getField(stripIndices(ident));
					eo = getByIndex(eo, indices);
				}
				else {
					eo = eo.getField(ident);
				}
				
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
			
			EveObject eo = getObject(resolvedObj);
			if (eo == null) {
				throw new EveError(resolvedObj + " is undefined");
			}
			
			for (int c = 1; c < split.length; c++) {
				String ident = split[c];
				List<Integer> indices = indexOperatorAnalysis(ident);
				if (indices != null) {
					eo = eo.getField(stripIndices(ident));
					eo = getByIndex(eo, indices);
				}
				else {
					eo = eo.getField(ident);
				}
				
				if (eo == null && c != split.length - 1) {
					throw new EveError("property " + ident + " of " + resolvedObj + " is undefined");
				}
				
				resolvedObj += "." + ident;
			}
			
			return eo;
		}
		else {
			EveObject obj = null;
			List<Integer> indices = indexOperatorAnalysis(name);
			if (indices != null) {
				obj = getObject(stripIndices(name));
				obj = getByIndex(obj, indices);
			}
			else {
				obj = getObject(name);	
			}

			return obj;
		}
	}
	
	/**
	 * Removes [index]s from the given identifier. The name is assumed to be
	 * in the correct format. If there are no index accessors, it just returns
	 * the name that was passed in.
	 * @param name
	 * @return The name without any index properties.
	 */
	private static String stripIndices(String name) {
		if (name.contains("[")) {
			return name.substring(0, name.indexOf("["));
		}
		else {
			return name;
		}
	}
	
	private static EveObject getByIndex(EveObject obj, List<Integer> indices) {
		for (int index : indices) {
			obj = obj.getIndexedProperty(index);
		}
		
		return obj;
	}
	
	private static EveObject getParentByIndex(EveObject obj, List<Integer> indices) {
		for (int c = 0; c < indices.size() - 1; c++) {
			obj = obj.getIndexedProperty(indices.get(c));
		}
		
		return obj;
	}
	
	public static void putVariable(String name, EveObject eo) {
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
			Integer index = null; //only used if we find out that we need to assign an indexed prop.
			for (int c = 1; c < split.length; c++) {
				name = split[c];
				index = null;
				
				//do this instead of looping to length - 1
				//so name can be assigned at least once.
				if (c == split.length - 1) {
					//might have an index access on our hands...
					List<Integer> indices = indexOperatorAnalysis(name);
					if (indices != null) {
						obj = obj.getField(stripIndices(name));
						obj = getParentByIndex(obj, indices);
						index = indices.get(indices.size() - 1);
					}
					break;
				}

				List<Integer> indices = indexOperatorAnalysis(name);
				if (indices != null) {
					obj = obj.getField(stripIndices(name));
					obj = getByIndex(obj, indices);
					index = indices.get(indices.size() - 1);
				}
				else {
					obj = obj.getField(name);
				}
					
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
			
			//create a clone if we need to. 
			if (obj.isMarkedForClone()) {
				EveObject parent = null;
				if (resolvedObj.contains(".")) {
					parent = getParentVariable(resolvedObj);
				}
				else {
					parent = getVariable(resolvedObj);
				}
				String[] propSplit = resolvedObj.split("\\.");
				String prop = "";
				
				if (propSplit.length > 1) {
					prop = stripIndices(propSplit[propSplit.length - 1]);
				}
				else {
					prop = stripIndices(name);
				}
				
				obj = obj.eveClone();
				parent.putField(prop, obj);
			}
			
			//indexed property assignment vs regular property assignment.
			if (index != null) {
				obj.setIndexedProperty(index, eo);
			}
			else {
				obj.putField(name, eo);
			}
		}
		else {
			//simple non-property assignment.
			List<Integer> indices = indexOperatorAnalysis(name);
			if (indices != null) {
				obj = obj.getField(stripIndices(name));
				
				if (indices.size() > 1) {
					obj = getParentByIndex(obj, indices);
				}
				
				obj.setIndexedProperty(indices.get(indices.size() - 1), eo);
			}

			obj.putField(name, eo);
		}
	}
		
	public static boolean inFunction() {
		return getCurrentScope().getType() == EveType.FUNCTION;
	}
	
	public static EveObject getGlobalScope() {
		return globalScopes.get(getNamespace());
	}
	
	private static void setGlobalScope(EveObject scope) {
		globalScopes.put(getNamespace(), scope);
	}
	
	public static void createGlobalScope() {
		if (getGlobalScope() != null) {
			throw new EveError("global object already exists for namespace " + getNamespace());
		}
		
		EveObject global = EveObject.globalType();
		setGlobalScope(global);
		pushScope(global);
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
		
		while (desc.hasNext()) {
			EveObject eo = desc.next();
			if (eo.getType() == EveType.FUNCTION && closureStack.contains(eo) == false) {
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
		Deque<EveObject> scope = namespaces.get(namespaceStack.peek());
		
		if (scope == null) {
			throw new EveError("namespace " + namespaceStack.peek() + " is undefined.");
		}
		
		return scope;
	}

	public static void setScript(Script script) {
		ScopeManager.script = script;
	}

	public static Script getScript() {
		return script;
	}

	public static void setNamespaces(Map<String, Deque<EveObject>> namespaces) {
		ScopeManager.namespaces = namespaces;
	}

	public static Map<String, Deque<EveObject>> getNamespaces() {
		return namespaces;
	}

	public static void setNamespace(String namespace) {
		previousNamespace = namespaceStack.peek();
		namespaceStack.push(namespace);
		
		if (namespaces.get(namespace) == null) {
			namespaces.put(namespace, new ArrayDeque<EveObject>());
		}
	}
	
	public static String getNamespace() {
		return namespaceStack.peek();
	}
	
	public static String revertNamespace() {
		String ns = namespaceStack.pop();
		//String currNS = namespaceStack.peek();
		//previousNamespace = namespaceStack.peek();
		//namespaceStack.push(currNS);
		return ns;
	}
}
