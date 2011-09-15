package eve.core;

import java.util.List;

import eve.core.EveObject.EveType;
import eve.core.builtins.BuiltinCommons;
import eve.core.builtins.EveFunction;
import eve.core.builtins.EveList;

/**
 * Creates EveObjects.
 * 
 * Startup problem: try to invoke various __create methods, but 
 * @author jeff
 *
 */
public class EveObjectFactory {
	private static class BareObject extends EveObject {
		@Override
		public EveObject eveClone() {
			return this; //will almost certainly cause reference problems... maybe use that cloning lib.
		}
	}
	
	public static EveObject empty() {
		return new BareObject(); 
	}
	
	private static EveObject bare(Object value) {
		//TODO: make something that overrides eveClone.
		EveObject eo = empty();
		eo.setValue(value);
		return eo;
	}
	
	private static EveObject createBuiltin(String builtinName, Object value) {
		EveObject eo = BuiltinCommons.getType(builtinName).__create(bare(value));
		return eo;
	}
	
	public static EveObject create(Integer i) {
		return createBuiltin("int", i);
	}
	
	public static EveObject create(Boolean b) {
		return createBuiltin("bool", b);
	}
	
	public static EveObject create(String s) {
		return createBuiltin("string", s);
	}
	
	public static EveObject create(Character c) {
		return createBuiltin("string", c);
	}
	
	public static EveObject create(Double d) {
		return createBuiltin("double", d);
	}
	
	public static EveObject create(Function f) {
		EveObject eo = new EveFunction();
		eo.cloneFrom(BuiltinCommons.getType("function"));
		eo.setValue(f);
		
		return eo;		
	}
	
	public static EveObject create(List<EveObject> l) {
		//cannot call __create of list or we get an infinite recursive loop since
		//function invocation creates an eve list.
		EveObject eo = new EveList();
		eo.cloneFrom(BuiltinCommons.getType("list"));
		eo.setValue(l);
		
		return eo;
	}
	
	public static EveObject globalType() {
		//TODO: figure out how we get cloned from EveGlobal if we're creating an empty Eveobject...
		EveObject global = empty();
		global.cloneFrom(BuiltinCommons.getType("global"));
		
		global.setType(EveType.SCOPE);
		global.setTypeName("scope(global)");
		return global;
	}
	
	public static EveObject javaType(Object o) {
		EveObject eo = empty();
		eo.cloneFrom(BuiltinCommons.getType("java"));
		
		eo.setType(EveType.JAVA);
		eo.setTypeName(o.getClass().getName()); //TODO: only set type name if exposeTyped
		eo.setValue(o);
		return eo;
	}
		
	public static EveObject customType(String typeName) {
		EveObject eo = createBuiltin("object", null);
		eo.setType(EveType.CUSTOM);
		eo.setTypeName(typeName);
		//BuiltinCommons.initialize(eo); //TODO: replace with ....?
		return eo;
	}
	
	public static EveObject prototypeType(String typeName) {
		EveObject eo = empty();
		eo.setType(EveType.PROTOTYPE);
		eo.setTypeName(typeName);
		return eo;
	}
		
	public static EveObject scopeType(String scopeName) {
		EveObject eo = customType(scopeName);
		eo.setType(EveType.SCOPE);
		eo.setTypeName("scope(" + scopeName + ")");
		return eo;
	}
		
	public static EveObject nullType() {
		EveObject eo = empty();
		eo.setType(EveType.NULL);
		eo.setTypeName("null");
		return eo;
	}
}
