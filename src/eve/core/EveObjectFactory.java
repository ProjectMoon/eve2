package eve.core;

import java.beans.IntrospectionException;
import java.util.List;

import eve.core.EveObject.EveType;
import eve.core.builtins.BuiltinCommons;
import eve.core.builtins.EveBoolean;
import eve.core.builtins.EveBuiltinObject;
import eve.core.builtins.EveDouble;
import eve.core.builtins.EveFunction;
import eve.core.builtins.EveFunctionPrototype;
import eve.core.builtins.EveInteger;
import eve.core.builtins.EveList;
import eve.core.builtins.EveString;
import eve.eji.EJIHelper;

/**
 * Creates EveObjects. 
 * @author jeff
 *
 */
public class EveObjectFactory {
	private static class BareObject extends EveObject {
		public BareObject() {
			
		}
		
		@Override
		public EveObject eveClone() {
			EveObject eo = new BareObject();
			eo.mergeFrom(this);
			return eo;
		}
	}
	
	public static EveObject empty() {
		return new BareObject(); 
	}
	
	private static EveObject ejiInit(EveObject eo) {
		try {
			return EJIHelper.createEJIObject(eo);
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static EveObject create(Integer i) {
		EveObject eo = ejiInit(new EveInteger());
		eo.mergeFrom(BuiltinCommons.getType("int"));
		eo.setValue(i);
		return eo;
	}
	
	public static EveObject create(Boolean b) {
		EveObject eo = ejiInit(new EveBoolean());
		eo.mergeFrom(BuiltinCommons.getType("bool"));
		eo.setValue(b);
		return eo;
	}
	
	public static EveObject create(String s) {
		EveObject eo = ejiInit(new EveString());
		eo.mergeFrom(BuiltinCommons.getType("string"));
		eo.setValue(s);
		return eo;
	}
	
	public static EveObject create(Character c) {
		EveObject eo = ejiInit(new EveString());
		eo.mergeFrom(BuiltinCommons.getType("string"));
		eo.setValue(c);
		return eo;
	}
	
	public static EveObject create(Double d) {
		EveObject eo = ejiInit(new EveDouble());
		eo.mergeFrom(BuiltinCommons.getType("double"));
		eo.setValue(d);
		return eo;
	}
	
	public static EveObject create(Function f) {
		EveObject eo = EveFunctionPrototype.createInstance();
		eo.mergeFrom(BuiltinCommons.getType("function"));
		eo.setValue(f);
		
		return eo;		
	}
	
	public static EveObject create(List<EveObject> l) {
		EveObject eo = ejiInit(new EveList());
		eo.mergeFrom(BuiltinCommons.getType("list"));
		eo.setValue(l);
		
		return eo;
	}
	
	public static EveObject globalType() {
		//TODO: figure out how we get cloned from EveGlobal if we're creating an empty Eveobject...
		EveObject global = empty();
		global.mergeFrom(BuiltinCommons.getType("global"));
		
		global.setType(EveType.SCOPE);
		global.setTypeName("scope(global)");
		return global;
	}
	
	public static EveObject javaType(Object o) {
		EveObject eo = empty();
		eo.mergeFrom(BuiltinCommons.getType("java"));
		
		//must set type after since setValue will also set the type...
		eo.setValue(o);
		eo.setType(EveType.JAVA);
		eo.setTypeName(o.getClass().getName()); //TODO: only set type name if exposeTyped
		
		return eo;
	}
		
	public static EveObject customType(String typeName) {
		EveObject eo = ejiInit(new EveBuiltinObject());
		eo.setType(EveType.CUSTOM);
		eo.setTypeName(typeName);
		//BuiltinCommons.initialize(eo); //TODO: replace with ....?
		return eo;
	}
	
	/**
	 * Used for creating objects that act like an internal type, but are not
	 * actually cloned from that builtin type's prototype. Example: function
	 * invocation with variable arguments creates a "list" to put variables in.
	 * It is an internal type with EveType.LIST but is otherwise custom.
	 * @param typeName
	 * @param internalType
	 * @return
	 */
	public static EveObject internalType(String typeName, EveType internalType) {
		EveObject eo = ejiInit(new EveBuiltinObject());
		eo.setType(internalType);
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
