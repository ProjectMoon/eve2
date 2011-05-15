package eve.eji;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.core.Function;
import eve.scope.ScopeManager;

public class EJIHelper {
	private static final Multimap<Class<?>, EveType> typeMap = createTypeMap();
	
	private static Multimap<Class<?>, EveType> createTypeMap() {
		HashMultimap<Class<?>, EveType> map = HashMultimap.create();
		
		map.put(int.class, EveType.INTEGER);
		map.put(Integer.class, EveType.INTEGER);
		map.put(double.class, EveType.DOUBLE);
		map.put(Double.class, EveType.DOUBLE);
		map.put(boolean.class, EveType.BOOLEAN);
		map.put(Boolean.class, EveType.BOOLEAN);
		map.put(String.class, EveType.STRING);
		map.put(char.class, EveType.STRING);
		map.put(Character.class, EveType.STRING);
		map.put(List.class, EveType.LIST);
		map.put(Function.class, EveType.FUNCTION);
		map.put(EveObject.class, EveType.PROTOTYPE);
		map.put(EveObject.class, EveType.CUSTOM);
		//EveType.JAVA not mapped here, because of how it maps to any given class.
		
		return map;
	}
	
	public static EveObject self() {
		return ScopeManager.getVariable("self");
	}
	
	private static Collection<EveType> map(Class<?> ctorParam) {
		Collection<EveType> types = typeMap.get(ctorParam);
		
		if (!types.isEmpty()) {
			return types;
		}
		else {
			Set<EveType> type = new HashSet<EveType>();
			type.add(EveType.JAVA);
			return type;
		}
	}
	
	public static Constructor<?> findConstructor(Class<?> cl, List<EveObject> params) {
		return findConstructor(cl, params.toArray(new EveObject[0]));
	}
	
	public static void main(String[] args) {
		EveObject p1 = new EveObject();
		p1.setType(EveType.JAVA);
		
		Constructor<?> ctor = findConstructor(ArrayList.class, p1);
	}
	
	public static Constructor<?> findConstructor(Class<?> cl, EveObject ... params) {
		//http://ironpython.net/documentation/dotnet/dotnet.html#appendix-type-conversion-rules
		
		//Phase 0: check that lengths match up.
		
		//Phase 1: Determine possible constructors.
		//for each constructor C:
		//	for each parameter P of C:
		//		retrieve mapped EveType for P
		//		if mapped EveType == actualParameter A.getType(), continue.
		//		else, ignore ctor C.
		//	add C to the list of possible constructors.
		List<Constructor<?>> possibleCtors = new ArrayList<Constructor<?>>();
		
		OUTER:
		for (Constructor<?> ctor : cl.getConstructors()) {
			Class<?>[] ctorParams = ctor.getParameterTypes();
			
			//no-args constructor.
			if (ctorParams.length == 0) {
				if (params != null || params.length != 0) {
					continue OUTER;
				}
			}
			else {
				//args constructor.
				for (int c = 0; c < params.length; c++) {
					Class<?> ctorParam = ctorParams[c];
					EveObject actualParam = params[c];
					Collection<EveType> mappedTypes = map(ctorParam);
					if (!mappedTypes.contains(actualParam.getType())) {
						continue OUTER; //skip this constructor.
					}
				}
			}
			
			//we have determined this ctor is possible.
			possibleCtors.add(ctor);
		}
		
		System.out.println(possibleCtors);
		
		//Phase 2: determine best constructor (assuming we have any)
		//the least-wide method is chosen. this means that the least amount of widening will be taken.
		//take jython approach and define an order:
		//for most, prefer objects (Integer, etc) over primitives (int)
		//for strings, if the actual param is length 1, prefer Character over char over String.
		
		//Phase 3: return constructor!
		
		/*
		
		
		List<Class<?>> clParams = new ArrayList<Class<?>>(params.length);
		
		for (EveObject param : params) {
			clParams.add(param.getTypeClass());
		}
		
		Class<?>[] clParamsArray = clParams.toArray(new Class<?>[0]);
		
		
		for (Constructor<?> c : cl.getConstructors()) {
			if (Arrays.equals(c.getParameterTypes(), clParamsArray)) {
				return c;
			}
		}
		
		throw new EveError("could not find constructor for " + cl.getName() + " matching " + Arrays.asList(params));
		*/
		return null;
	}
	
	public static Method findMethod(Class<?> cl, EveObject ... params) {
		List<Class<?>> methParams = new ArrayList<Class<?>>(params.length);
		
		for (EveObject param : params) {
			methParams.add(param.getTypeClass());
		}
		
		Class<?>[] clParamsArray = methParams.toArray(new Class<?>[0]);
		
		for (Method meth : cl.getMethods()) {
			if (Arrays.equals(meth.getParameterTypes(), clParamsArray)) {
				return meth;
			}
		}
		
		throw new EveError("could not find method for " + cl.getName() + " matching " + params);
	}
	
	public static Object[] mapToJava(List<EveObject> eoArgs) {
		Object[] args = new Object[eoArgs.size()];
		
		for (int c = 0; c < args.length; c++) {
			args[c] = eoArgs.get(c).getObjectValue();
		}
		
		return args;
	}
	
	public static void mapJavaMethods(EveObject eo) {
		if (eo.getType() != EveType.JAVA) {
			throw new EveError("can only map java methods to java types.");
		}
			
		Object o = eo.getObjectValue();
		for (Method meth : o.getClass().getMethods()) {
			EJIFunction methodInvocation = EJIFunction.fromJava(o, meth);
			eo.putField(meth.getName(), new EveObject(methodInvocation));
		}
	}
	
	public static EveObject createEJIType(Class<?> type) throws IntrospectionException, IllegalAccessException {
		Object obj;
		
		try {
			obj = type.newInstance();
		}
		catch (InstantiationException e) {
			throw new EveError(type.getName() + ": EJI currently only supports JavaBeans");
		}
		
		EveObject eo = EveObject.customType(obj.getClass().getName());
		BeanInfo info = Introspector.getBeanInfo(obj.getClass());
		
		//handle properties
		for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
			eo.putField(pd.getName(), new EJIField(obj, pd));	
		}
		
		//handle methods
		for (MethodDescriptor md : info.getMethodDescriptors()) {
			EveObject mappedMethod = new EveObject(EJIFunction.fromJava(obj, md.getMethod()));
			eo.putField(md.getName(), mappedMethod);
		}
		
		return eo;
	}

}
