package eve.eji;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.Modifier;

import com.google.common.base.Predicate;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObject.EveType;
import eve.core.builtins.BuiltinCommons;
import eve.core.Function;
import eve.scope.ScopeManager;

/**
 * EJIHelper contains many utility functions to assist in bridging the Eve and Java environments.
 * Most of these methods are public, but end developers generally should not have to interact
 * with them, except perhaps the {@link #self()} method.
 * @author jeff
 *
 */
public class EJIHelper {
	//no instances of this class.
	private EJIHelper() {}
	
	private static final Multimap<Class<?>, EveType> typeMap = createTypeMap();
	private static final Map<Class<?>, Integer> weightMap = createWeightMap();
	
	/**
	 * Create the type map. Note that this does not cover every single mapping case.
	 * The type map is used as a helper for the map(Class<?>, EveObject) method.
	 * @return The type map.
	 */
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
	
	/**
	 * Create the weight map. Note that this does not cover every single weighing case.
	 * The weight map is used as a helper for the weigh(Class<?>, EveObject) method.
	 * @return The weight map.
	 */
	private static Map<Class<?>, Integer> createWeightMap() {
		Map<Class<?>, Integer> map = new HashMap<Class<?>, Integer>();
		
		map.put(Integer.class, 2);
		map.put(int.class, 1);
		map.put(Double.class, 2);
		map.put(double.class, 1);
		map.put(Boolean.class, 2);
		map.put(boolean.class, 1);
		//nothing else, because they are special cases.
		
		return map;
	}
	
	/**
	 * Convenience method for getting the current "self" reference.
	 * @return The current self reference. Null if it doesn't exist.
	 */
	public static EveObject self() {
		return ScopeManager.getVariable("self");
	}
	
	/**
	 * Core logic of phase 2 of the signature discovery process. Attempt to map a formal parameter
	 * (Class) to an actual parameter (EveObject). A single formal parameter can possibly map to one
	 * of several different actual parameters. Mapping rules are as follows:
	 * <ul>
	 * 	<li>int, Integer = EveType.INTEGER</li>
	 * 	<li>double, Double = EveType.DOUBLE</li>
	 * 	<li>boolean, Boolean = EveType.BOOLEAN</li>
	 * 	<li>char, Character, String = EveType.STRING</li>
	 * 	<li>java.util.List = EveType.LIST</li>
	 * 	<li>EveObject = EveType.PROTOTYPE, EveType.CUSTOM</li>
	 * 	<li>eve.core.Function = EveType.FUNCTION</li>
	 * 	<li>Everything else (Java types) = formalParam.isAssignableFrom(actualParam.getClass())</li>
	 * </ul>
	 * If no mappings are found, an empty collection is returned. This method never returns
	 * null.
	 * @param formalParam
	 * @param actualParam
	 * @return A Collection of EveTypes that the formal parameter maps to. Empty if no valid mapping found. 
	 */
	private static Collection<EveType> map(Class<?> formalParam, EveObject actualParam) {	
		if (actualParam.getType() != EveType.JAVA) {
			return typeMap.get(formalParam);
		}
		else {
			//everything else is a java mapping. in order for this mapping to be
			//valid, a widening conversion from actualParam to formalParam must exist.
			if (actualParam.getJavaValue() != null && formalParam.isAssignableFrom(actualParam.getJavaValue().getClass())) {
				Set<EveType> type = new HashSet<EveType>();
				type.add(EveType.JAVA);
				return type;
			}
			else {
				//empty collection means no mapping found.
				return new HashSet<EveType>();
			}
		}
	}
	
	/**
	 * Decide how good of a fit the given formal parameter is for the given actual parameter.
	 * This is the core logic of phase 3 of the signature discovery process. The decision rules
	 * are somewhat complex, and are detailed inside the method itself.
	 * <br/><br/>
	 * In general, objects are preferred over primitives (e.g. Integer > int), and direct type 
	 * equality is preferred over widening conversion. In the case of an EveType.STRING actual
	 * parameter, Character and char are preferred over String if the actualParameter's string
	 * value is of length 1. Otherwise, String is required.
	 * @param formalParam
	 * @param actualParam
	 * @return A number representing how good of a fit this formal parameter is for an actual parameter.
	 * Higher is better.
	 */
	private static int weigh(Class<?> formalParam, EveObject actualParam) {
		//Note: we don't consider List here, because non-Lists are discarded
		//during type filtering.
		if (actualParam.getType() == EveType.STRING) {
			//For strings, we weigh differently based on the length of the string.
			//Length 1 prefers: java.lang.Character, char, java.lang.String
			//Length > 1: requires java.lang.String. Anything else is invalid.
			//Null: if actualParam is null, then assume java.lang.String
			String value = actualParam.getStringValue();
			
			if (value != null && value.length() > 1) {
				if (formalParam == String.class) {
					return 2;
				}
				else {
					return -999;
				}
			}
			else {
				if (formalParam == Character.class) {
					return 3;
				}
				else if (formalParam == char.class) {
					return 2;
				}
				else {
					return 1;
				}
			}
		}
		else if (actualParam.getType() == EveType.JAVA) {
			//For Java types, direct equality is weight 2.
			//isAssignableFrom is weight 1.
			//and while the last else clause should never be hit, we might
			//as well cover all our bases.
			Object javaValue = actualParam.getJavaValue();
			if (javaValue.getClass().equals(formalParam)) {
				return 2;
			}
			else if (formalParam.isAssignableFrom(javaValue.getClass())) {
				return 1;
			}
			else {
				return -999;
			}
		}
		else if (actualParam.getType() == EveType.FUNCTION || actualParam.getType() == EveType.CUSTOM || 
				actualParam.getType() == EveType.PROTOTYPE) {
			//These are all EveObject mappings. Just return 0 to acknowledge they're valid and move on.
			return 0;
		}
		else {
			//Query the weight map for int, double, and boolean.
			//In all cases, we prefer the object form (e.g. java.lang.Integer) over the primitive (e.g. int).
			return weightMap.get(formalParam);
		}
	}
	
	/**
	 * Given a set of formal parameters (such as from a method or constructor) and a set of actual
	 * parameters (from the interpreter), attempt to discover the best signature that fits
	 * the actual parameters. This method works in 3 phases: argument length testing, argument
	 * type testing, and argument type weighing. Each phase progressively whittles down the
	 * list of signatures until one or more best-fit signatures are left. If one signature is
	 * found, everything is good and that signature is returned. If more than one is found, an
	 * EveError is thrown.
	 * <br/><br/>
	 * Because of Java's statically typed nature, there are limits on this algorithm. Most Eve
	 * types map to two different Java types (primitive and object equivalent). Some map to more
	 * or less. Like Jython, this implementation maps based on a preferred order. In general,
	 * object formal parameters are preferred over primitive ones. The main impediment here is
	 * that the Java reflection API does NOT handle auto-boxing. java.lang.Integer and int are
	 * considered two different types to the reflection API.
	 * <br/><br/> 
	 * The signature discovery is a very expensive process, albeit a necessary one. To that end,
	 * some memoization should be implemented (although this is currently not the case).
	 * @param signatures
	 * @param params
	 * @return The best-fit signature for the given set of actual parameters.
	 */
	private static Class<?>[] findSignature(List<Class<?>[]> signatures, EveObject ... params) {
		//Designed with great inspiration from:
		//http://www.jython.org/archive/22/userguide.html#overloaded-java-method-signatures
		//http://ironpython.net/documentation/dotnet/dotnet.html#appendix-type-conversion-rules
		
		List<Class<?>[]> possibilities = new ArrayList<Class<?>[]>();
		
		//Phase 1: discard signatures based on length.
		for (Class<?>[] signature : signatures) {
			if (params.length == signature.length) {
				possibilities.add(signature);
			}
		}
			
		//Phase 2: discard signatures based on type. See map function for details.
		List<Class<?>[]> sigsToDiscard = new ArrayList<Class<?>[]>();
		for (Class<?>[] signature : possibilities) {	
			//no-args.
			if (signature.length == 0) {
				if (params != null && params.length != 0) {
					sigsToDiscard.add(signature);
				}
			}
			else {
				//args signature
				for (int c = 0; c < params.length; c++) {
					Class<?> formalParam = signature[c];
					EveObject actualParam = params[c];
					Collection<EveType> mappedTypes = map(formalParam, actualParam);
					if (!mappedTypes.contains(actualParam.getType())) {
						sigsToDiscard.add(signature);
					}
				}
			}
		}
		
		possibilities.removeAll(sigsToDiscard);
			
		//Phase 3, decide which signatures are the best.
		Comparator<Class<?>[]> signatureComparator = new Comparator<Class<?>[]>() {
			@Override
			public int compare(Class<?>[] arg0, Class<?>[] arg1) {
				if (Arrays.equals(arg0, arg1)) {
					return 0;
				}
				else {
					return -1;
				}
			}
		};
		
		TreeMultimap<Integer, Class<?>[]> weights = TreeMultimap.create(Ordering.natural(), signatureComparator);
		for (Class<?>[] signature : possibilities) {
			int weight = 0;
			for (int c = 0; c < params.length; c++) {
				EveObject actualParam = params[c];
				Class<?> formalParam = signature[c];
				weight += weigh(formalParam, actualParam);
			}
			
			weights.put(weight, signature);
		}
		
		//Phase 4: return a signature. there can only be one valid signature. if our
		//decision process has resulted in more than best-fit signature, we throw an ambiguity error.
		if (!weights.isEmpty()) {
			int highestWeight = weights.asMap().lastKey();
			if (highestWeight >= 0) {
				Collection<Class<?>[]> winner = weights.get(highestWeight);
				
				if (winner.size() > 1) {
					throw new EveError("ambiguous signatures. use explicit types instead.");
				}
				
				return Iterables.get(winner, 0);
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}
	
	/**
	 * Find a constructor for the given class and parameters.
	 * @param cl
	 * @param actualParameters
	 * @return A The best-fit Constructor, if one is found. Null if none are found.
	 * @throws EveError if there is an ambiguity error during signature discovery.
	 */
	public static Constructor<?> findConstructor(Class<?> cl, List<EveObject> actualParameters) {
		if (actualParameters == null) {
			throw new NullPointerException("actualParameters cannot be null");
		}
		return findConstructor(cl, actualParameters.toArray(new EveObject[0]));
	}
	
	/**
	 * Find a constructor for the given class and parameters.
	 * @param cl
	 * @param actualParameters
	 * @return The best-fit Constructor, if one is found. Null if none are found.
	 * @throws EveError if there is an ambiguity error during signature discovery.
	 */
	public static Constructor<?> findConstructor(Class<?> cl, EveObject ... actualParameters) {
		if (actualParameters == null) {
			throw new NullPointerException("actualParameters cannot be null");
		}
		
		Constructor<?>[] ctors = cl.getConstructors();
		List<Class<?>[]> sigs = new ArrayList<Class<?>[]>(ctors.length);
		
		for (Constructor<?> ctor : ctors) {
			sigs.add(ctor.getParameterTypes());
		}
		
		Class<?>[] sig = findSignature(sigs, actualParameters);
		
		for (Constructor<?> ctor : ctors) {
			if (Arrays.equals(ctor.getParameterTypes(), sig)) {
				return ctor;
			}
		}
		
		return null;
	}
	
	/**
	 * Given a class, method name, and set of actual parameters, this method attempts to find
	 * the best-fit Method object to invoke. This method discovery process follows the same
	 * rules as constructor discovery: object types are preferred over primitive types, and
	 * for single character strings, character types are preferred over java.lang.String.
	 * @param cl
	 * @param name
	 * @param actualParameters
	 * @return The best-fit Method object found. Null if none are found.
	 * @throws EveError if there is an ambiguity error during signature discovery.
	 */
	public static Method findMethod(Class<?> cl, final String name, EveObject ... actualParameters) {
		if (actualParameters == null) {
			throw new NullPointerException("actualParameters cannot be null");
		}
		
		Predicate<Method> filter = new Predicate<Method>() {
			@Override
			public boolean apply(Method input) {
				return input.getName().equals(name);
			}
			
		};
		
		Iterable<Method> possibleMethods = Iterables.filter(Arrays.asList(cl.getMethods()), filter);
		
		List<Class<?>[]> sigs = new ArrayList<Class<?>[]>(); 
		for (Method meth : possibleMethods) {
			sigs.add(meth.getParameterTypes());
		}
		
		Class<?>[] sig = findSignature(sigs, actualParameters);
		
		for (Method meth : possibleMethods) {
			if (Arrays.equals(meth.getParameterTypes(), sig)) {
				return meth;
			}
		}

		return null;
	}
	
	/**
	 * Delegates to {@link #findMethod(Class, String, EveObject...)}].
	 * @param cl
	 * @param name
	 * @param actualParameters
	 * @return The best-fit Method object found. Null if none are found.
	 * @throws EveError if there is an ambiguity error during signature discovery.
	 */
	public static Method findMethod(Class<?> cl, String name, List<EveObject> actualParameters) {
		if (actualParameters == null) {
			throw new NullPointerException("actualParamters cannot be null.");
		}
		
		return findMethod(cl, name, actualParameters.toArray(new EveObject[0]));
	}
	
	/**
	 * Given a set of formal parameters and a set of actual parameters, this method attempts to
	 * type coerce the EveObject arguments to Java arguments specified in the formal parameter
	 * list.
	 * @param formalParams
	 * @param actualParameters
	 */
	public static Object[] mapArguments(Class<?>[] formalParams, EveObject ... actualParameters) {
		if (actualParameters == null) {
			throw new NullPointerException("actualParameters cannot be null.");
		}
		
		if (formalParams.length != actualParameters.length) {
			throw new EveError("formal parameters and actual parameters argument length do not match");
		}
		
		List<Object> args = new ArrayList<Object>(actualParameters.length);
		
		for (int c = 0; c < formalParams.length; c++) {
			Class<?> formalParam = formalParams[c];
			EveObject actualParam = actualParameters[c];
			
			if (!formalParam.isPrimitive()) {
				Object arg = formalParam.cast(actualParam.getObjectValue());
				args.add(arg);
			}
			else {
				//primitives automatically get unwrapped by Method#invoke.
				args.add(actualParam.getObjectValue());
			}
			
		}
		
		return args.toArray();
	}
	
	/**
	 * Maps EveObjects to Java types for constructor/method invocation.
	 * @param formalParams
	 * @param actualParameters
	 * @return
	 */
	public static Object[] mapArguments(Class<?>[] formalParams, List<EveObject> actualParameters) {
		if (actualParameters == null) {
			throw new NullPointerException("actualParameters cannot be null.");
		}
		return mapArguments(formalParams, actualParameters.toArray(new EveObject[0]));
	}
	
	/**
	 * Creates a constructor function for the given Java type.
	 * @param type
	 * @return
	 */
	public static EveObject createEJIConstructor(Class<?> type) {
		EJIFunction ctorFunc = new JavaConstructorInvocation(type);
		EveObject eo = new EveObject(ctorFunc);
		return eo;
	}
	
	/**
	 * Given a type name and an EJI constructor (obtained from {@link #createEJIConstructor(Class)}),
	 * creates an Eve type object for use with the type pool.
	 * @param ctor
	 * @param typeName
	 * @return An Eve type.
	 */
	public static EveObject createEJIType(String typeName, EveObject ctor) {
		EveObject eo = EveObject.prototypeType(typeName);
		eo.putField("__create", ctor);
		return eo;
	}
	
	/**
	 * Given any object, creates a Java wrapper EveObject around it. The wrapper object uses
	 * bean introspection to produce the EveObject. Thus, all get* and set* methods are changed
	 * into {@link DynamicField}s, and all methods are attached to the wrapped object as callable
	 * functions.
	 * @param obj
	 * @return The wrapped type.
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 */
	public static EveObject createEJIObject(Object obj) throws IntrospectionException, IllegalAccessException {
		EveObject eo = EveObject.javaType(obj);
		BeanInfo info = Introspector.getBeanInfo(obj.getClass());
		
		//handle properties
		for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
			eo.putField(pd.getName(), new EJIField(obj, pd));	
		}
		
		//handle methods
		Set<String> methodNames = new HashSet<String>();
		
		for (MethodDescriptor md : info.getMethodDescriptors()) {
			methodNames.add(md.getMethod().getName());
		}
		
		for (String methodName : methodNames) {
			EJIFunction methodInvocation = EJIFunction.fromJava(obj, methodName);
			eo.putField(methodName, new EveObject(methodInvocation));
		}
		
		return eo;
	}
	
	/**
	 * Creates a "native namespace" from the given Java class. The class must have the {@link EJIModuleType}
	 * annotation present. This method will find all public static methods in the class and convert
	 * them either to functions or read-only properties. If a method has the {@link EJIProperty} annotation,
	 * it will convert the method to a read-only property with the name specified by the annotation.
	 * If there is no annotation present, it will convert the static method to a function. Static
	 * variables, non-static variables, and non-static methods are all ignored.
	 * @param cl
	 */
	public static void createEJINamespace(Class<?> cl) {
		if (!cl.isAnnotationPresent(EJIModuleType.class)) {
			throw new EveError(cl.getName() + " is not a valid EJI namespace.");
		}
		
		String namespace = cl.getAnnotation(EJIModuleType.class).value();
		
		EveObject type = EveObject.prototypeType(namespace);
		
		Map<String, Method> methods = new HashMap<String, Method>();
		Map<String, Method> properties = new HashMap<String, Method>();
	
		//methods and properties.
		for (Method method : cl.getMethods()) {
			if (Modifier.isStatic(method.getModifiers())) {
				if (method.isAnnotationPresent(EJIProperty.class)) {
					properties.put(method.getAnnotation(EJIProperty.class).value(), method);
				}
				else {
					if (method.isAnnotationPresent(EJIFunctionName.class)) {
						methods.put(method.getAnnotation(EJIFunctionName.class).value(), method);
					}
					else {
						methods.put(method.getName(), method);
					}
				}
			}
		}
		
		for (Map.Entry<String, Method> entry : properties.entrySet()) {
			EJIField field = new EJIField(entry.getValue());
			type.putField(entry.getKey(), field);
		}
		
		for (Map.Entry<String, Method> entry : methods.entrySet()) {
			//always force the real method name (entry.getValue().getName()), but we
			//could possibly use a custom function name (entry.getKey())
			EJIFunction methodInvocation = EJIFunction.fromStatic(cl, entry.getValue().getName());
			type.putField(entry.getKey(), new EveObject(methodInvocation));
		}		

		BuiltinCommons.addType(namespace, type);		
	}

}
