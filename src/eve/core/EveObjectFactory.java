package eve.core;

import java.beans.IntrospectionException;
import java.util.List;

import eve.core.EveObject.EveType;
import eve.core.builtins.BuiltinCommons;
import eve.core.builtins.EveBoolean;
import eve.core.builtins.EveBuiltinObject;
import eve.core.builtins.EveDouble;
import eve.core.builtins.EveFunction;
import eve.core.builtins.EveInteger;
import eve.core.builtins.EveList;
import eve.core.builtins.EveString;
import eve.eji.EJIHelper;

/**
 * Creates EveObjects.
 * 
 * Current problems: 
 * BareObject acquires toString from its contained object, but the EJIHelper.self() method returns the BareObj... and since it's by method name, we invoke
 * EveObject#toString. More tracing on this issue puts it as EJIHelper.createEJIObject somehow getting EveTypes that are not JAVA. So the if statement that
 * stops the recursion normally doesn't work.
 * Current way of creating builtins makes them lose their special properties... unless we DONT clone from pr457ototypes (need some kind of merge) 
 * @author jeff
 *
 */
public class EveObjectFactory {
	private static class BareObject extends EveObject {
		public BareObject() {
			
		}
		
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
	
	public static EveObject create(Integer i) {
		EveObject eo = new EveInteger();
		//eo.cloneFrom(BuiltinCommons.getType("int"));
		eo.setValue(i);
		return eo;
	}
	
	public static EveObject create(Boolean b) {
		EveObject eo = new EveBoolean();
		eo.cloneFrom(BuiltinCommons.getType("bool"));
		eo.setValue(b);
		return eo;
	}
	
	public static EveObject create(String s) {
		EveObject eo;
		try {
			eo = EJIHelper.createEJIObject(new EveString());
			//eo.cloneFrom(BuiltinCommons.getType("string"));
			eo.setValue(s);
			return eo;
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static EveObject create(Character c) {
		EveObject eo = new EveString();
		eo.cloneFrom(BuiltinCommons.getType("string"));
		eo.setValue(c);
		return eo;
	}
	
	public static EveObject create(Double d) {
		EveObject eo = new EveDouble();
		eo.cloneFrom(BuiltinCommons.getType("double"));
		eo.setValue(d);
		return eo;
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
		
		//must set type after since setValue will also set the type...
		eo.setValue(o);
		eo.setType(EveType.JAVA);
		eo.setTypeName(o.getClass().getName()); //TODO: only set type name if exposeTyped
		
		return eo;
	}
		
	public static EveObject customType(String typeName) {
		EveObject eo = new EveBuiltinObject();
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
