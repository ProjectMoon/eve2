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

public class ScopeManager {
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
		//_global is the default namespace. This allows getObject to enforce
		//use of global:: for finding global variables.
		Deque<EveObject> globalScope = new ArrayDeque<EveObject>();
		namespaces.put("global", globalScope);
		namespaces.put("_global", globalScope);
		//setNamespace("_global");
	}
	
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
		getCurrentScope().putField(name, eo);
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
			return;
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
