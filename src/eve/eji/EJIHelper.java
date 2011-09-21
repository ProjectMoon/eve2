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
import eve.core.EveObjectFactory;
import eve.core.EveObject.EveType;
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
	
	public static boolean isEveBuiltin(Object o) {
		Class<?> cl = o.getClass();
		
		return cl.isPrimitive() ||
			o instanceof Integer || o instanceof Double || o instanceof Boolean ||
			o instanceof String || o instanceof Character || o instanceof List;  
	}
	
	/**
	 * Convenience method for getting the current "self" reference.
	 * @return The current self reference. Null if it doesn't exist.
	 */
	public static Object self() {
		EveObject self = ScopeManager.getVariable("self");
		
		//if we are a child of eveobject
		if (self.getInternalType() == EveType.JAVA) {
			return self.getJavaValue();
		}
		else if (self instanceof EveObject) {
			return self;
		}	
		else {
			return null; //not sure about this.
		}
		
		
		//return self.getValue(); //dangerous. what if we want an EO specifically?
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
		if (actualParam.getInternalType() != EveType.JAVA) {
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
		if (actualParam.getInternalType() == EveType.STRING) {
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
		else if (actualParam.getInternalType() == EveType.JAVA) {
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
		else if (actualParam.getInternalType() == EveType.FUNCTION || actualParam.getInternalType() == EveType.CUSTOM || 
				actualParam.getInternalType() == EveType.PROTOTYPE) {
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
					if (!mappedTypes.contains(actualParam.getInternalType())) {
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
				Object arg = null;
				
				//if we are expecting an EveObject as an argument, just pass this variable along.
				//otherwise, try and convert it.
				if (formalParam.isAssignableFrom(actualParam.getClass())) {
					arg = actualParam;
				}
				else {
					arg = formalParam.cast(actualParam.getValue());
				}
				args.add(arg);
			}
			else {
				//primitives automatically get unwrapped by Method#invoke.
				args.add(actualParam.getValue());
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
	 * Creates a constructor function for the given Java type. The produced function will
	 * perform EJI type coercion, e.g. converting String to EveString.
	 * @param type
	 * @return An executable function that will call the constructor(s).
	 */
	public static EveObject createEJIConstructor(Class<?> type) {
		return createEJIConstructor(type, false);
	}
	/**
	 * Creates a constructor function for the given Java type. This method allows optional
	 * bypassing of EJI type coercion. If bypassTypeCoercion is true, constructors executed
	 * will not attempt to automatically convert certain Java types to Eve types, e.g. String
	 * to EveStrign.
	 * @param type
	 * @return An executable function that will call the constructor(s).
	 */
	public static EveObject createEJIConstructor(Class<?> type, boolean bypassTypeCoercion) {
		EJIFunction ctorFunc = new JavaConstructorInvocation(type, bypassTypeCoercion);
		EveObject eo = EveObjectFactory.create(ctorFunc);
		return eo;
	}
	
	/**
	 * Given a type name and an EJI constructor (obtained from {@link #createEJIConstructor(Class)}),
	 * creates an Eve type object for use with the type pool.
	 * @param ctor
	 * @param typeName
	 * @return An Eve type.
	 * @throws IntrospectionException
	 */
	public static EveObject createEJIType(String typeName, EveObject ctor) throws IntrospectionException {
		EveObject eo = EveObjectFactory.prototypeType(typeName);
		eo.putField("__create", ctor);
		return eo;
	}
	
	public static EveObject createEJIType(Class<?> type, boolean bypassTypeCoercion) throws IntrospectionException, InstantiationException, IllegalAccessException {
		//step 0: get type name.
		String typename = null;
		if (type.isAnnotationPresent(EJIType.class)) {
			typename = type.getAnnotation(EJIType.class).value();
		}
		else {
			typename = type.getSimpleName(); 
		}
		
		EveObject eo = null;
		if (EveObject.class.isAssignableFrom(type)) {
			eo = (EveObject)type.newInstance();
		}
		else {
			eo = EveObjectFactory.prototypeType(typename);
		}
		
		//step 1: get constructor for __create (assigned later).
		EveObject ctor = createEJIConstructor(type, bypassTypeCoercion);
		
		//step 2: add all static fields/properties
		Map<String, Method> staticMethods = new HashMap<String, Method>();
		Map<String, Method> staticProperties = new HashMap<String, Method>();
	
		for (Method method : type.getMethods()) {
			if (Modifier.isStatic(method.getModifiers())) {
				if (method.isAnnotationPresent(EJIProperty.class)) {
					staticProperties.put(method.getAnnotation(EJIProperty.class).value(), method);
				}
				else {
					if (method.isAnnotationPresent(EJIFunctionName.class)) {
						staticMethods.put(method.getAnnotation(EJIFunctionName.class).value(), method);
					}
					else {
						staticMethods.put(method.getName(), method);
					}
				}
			}
		}
		
		for (Map.Entry<String, Method> entry : staticProperties.entrySet()) {
			EJIField field = new EJIField(entry.getValue());
			eo.putField(entry.getKey(), field);
		}
		
		for (Map.Entry<String, Method> entry : staticMethods.entrySet()) {
 			EJIFunction methodInvocation = EJIFunction.fromStatic(type, entry.getValue().getName(), bypassTypeCoercion);
			eo.putField(entry.getKey(), EveObjectFactory.create(methodInvocation));
		}
			
		//step 3: instance properties.
		try {
			Object o = type.newInstance();
			
			//step 4: add all instance properties.
			EveObject ejiObj = createEJIObject(o, bypassTypeCoercion);
			eo.mergeFrom(ejiObj);
			
			//step 5: set java value to the instance.
			eo.setValue(o);
			eo.setInternalType(EveType.PROTOTYPE); //fix internal type change from setValue.
			
			//almost certainly overwritten by the merge. this is a weird case that doesn't happen
			//unless merging between two EveType.PROTOTYPE objects with __create fields.
			eo.putField("__create", ctor); 
			
			return eo;
		}
		catch (IllegalAccessException e) {
			throw new EveError(e.getMessage());
		}
		catch (InstantiationException e) {
			throw new EveError(e.getMessage());
		}
	}

	/**
	 * Given any object, creates a Java wrapper EveObject around it. The wrapper object uses
	 * bean introspection to produce the EveObject. Thus, all get* and set* methods are changed
	 * into {@link EJIField}s, and all methods are attached to the wrapped object as callable
	 * functions. Static methods are ignored. This method will do automatic type coercion for
	 * the Java types that Eve considers built-in. Thus, Strings will become wrapped EveStrings,
	 * Integers will become wrapped EveIntegers,
	 * etc.
	 * @param obj
	 * @return The wrapped type.
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 */
	public static EveObject createEJIObject(Object obj) throws IntrospectionException, IllegalAccessException {
		return createEJIObject(obj, false);
	}
	
	/**
	 * Given any object, creates a Java wrapper EveObject around it. The wrapper object uses
	 * bean introspection to produce the EveObject. Thus, all get* and set* methods are changed
	 * into {@link EJIField}s, and all methods are attached to the wrapped object as callable
	 * functions. Static methods are ignored. This method allows optional bypassing of type
	 * coercion. If bypassTypeCoercion is true, the method will not attempt to change Strings
	 * to EveStrings, Integers to EveIntegers, etc.
	 * @param obj
	 * @param bypassTypeCoercion If true, will not attempt to coerce certain Java types to their Eve counterparts (ex: String -> EveString)
	 * @return The wrapped type.
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 */
	public static EveObject createEJIObject(Object obj, boolean bypassTypeCoercion) throws IntrospectionException, IllegalAccessException {
		if (obj instanceof EveObject) {
			return createEJIEveObject((EveObject)obj);
		}
		
		if (!bypassTypeCoercion && isEveBuiltin(obj)) {
			EveObject builtin = createEJIBuiltin(obj);
			if (builtin != null) return builtin;
		}
		
		EveObject eo = EveObjectFactory.javaType(obj);
		BeanInfo info = Introspector.getBeanInfo(obj.getClass());
		
		//handle properties
		for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
			eo.putField(pd.getName(), new EJIField(pd));	
		}
		
		//handle methods
		Set<String> methodNames = new HashSet<String>();
		Method indexedAccessor = null;
		Method indexedMutator = null;
		
		for (MethodDescriptor md : info.getMethodDescriptors()) {
			if (Modifier.isStatic(md.getMethod().getModifiers()) == false) {
				if (md.getMethod().isAnnotationPresent(EJIIndexedAccessor.class)) {
					if (indexedAccessor == null) {
						indexedAccessor = md.getMethod(); 
					}
					else {
						throw new EveError("EJI objects can only have one indexed accessor method.");
					}
				}
				else if (md.getMethod().isAnnotationPresent(EJIIndexedMutator.class)) {
					if (indexedMutator == null) {
						indexedMutator = md.getMethod(); 
					}
					else {
						throw new EveError("EJI objects can only have one indexed mutator method.");
					}
				}
				else {
					methodNames.add(md.getMethod().getName());
				}
			}
		}
		
		for (String methodName : methodNames) {
			EJIFunction methodInvocation = EJIFunction.fromJava(methodName, bypassTypeCoercion);
			eo.putField(methodName, EveObjectFactory.create(methodInvocation));
		}
		
		if (indexedAccessor != null) {
			EJIFunction accessor = EJIFunction.fromJava(indexedAccessor, bypassTypeCoercion);
			eo.setIndexedAccessor(EveObjectFactory.create(accessor));
		}
		
		if (indexedMutator != null) {
			EJIFunction mutator = EJIFunction.fromJava(indexedMutator, bypassTypeCoercion);
			eo.setIndexedMutator(EveObjectFactory.create(mutator));
		}
		
		return eo;
	}
	
	/**
	 * Special case for creating EJI objects that facilitates creating builtins (from eve.core.builtins).
	 * This is only an attempt. If it fails (returns null), the interpreter will proceed with creating a
	 * regular Java type. This method simply provides much more reliable type conversion between the builtin
	 * types and their Java counterparts.
	 * @param obj
	 * @return
	 * @throws IntrospectionException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static EveObject createEJIBuiltin(Object obj) throws IntrospectionException {
		if (obj instanceof Integer) {
			return createEJIEveObject(EveObjectFactory.create((Integer)obj));
		}
		else if (obj instanceof Double) {
			return createEJIEveObject(EveObjectFactory.create((Double)obj));
		}
		else if (obj instanceof Character) {
			return createEJIEveObject(EveObjectFactory.create((Character)obj));
		}
		else if (obj instanceof String) {
			return createEJIEveObject(EveObjectFactory.create((String)obj));
		}
		else if (obj instanceof Boolean) {
			return createEJIEveObject(EveObjectFactory.create((Boolean)obj));
		}
		else if (obj instanceof List) {
			return createEJIEveObject(EveObjectFactory.create((List)obj));
		}
		else {
			return null;
		}
	}
	
	/**
	 * Special case for creating EJI objects when the object we are trying to wrap happens to be an EveObject
	 * already. In this case, any annotated properties and methods that are NOT inherited (since a lot of
	 * stuff comes from EveObject itself) are added as properties/methods. It will not add the eveClone method
	 * to the object either. Static methods are ignored.
	 * @param eo
	 * @return
	 * @throws IntrospectionException 
	 */
	private static EveObject createEJIEveObject(EveObject eo) throws IntrospectionException {
		BeanInfo info = Introspector.getBeanInfo(eo.getClass());
		
		List<Method> methods = Arrays.asList(eo.getClass().getDeclaredMethods());
		
		//properties
		for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
			if (methods.contains(pd.getReadMethod()) || methods.contains(pd.getWriteMethod())) {
				eo.putField(pd.getName(), new EJIField(pd));
			}
		}
		
		//methods
		Set<String> methodNames = new HashSet<String>();
		Method indexedAccessor = null;
		Method indexedMutator = null;
		
		for (Method method : methods) {
			if (Modifier.isStatic(method.getModifiers()) == false && Modifier.isPublic(method.getModifiers()) && method.getName().equals("eveClone") == false) {				
				if (method.isAnnotationPresent(EJIIndexedAccessor.class)) {
					if (indexedAccessor == null) {
						indexedAccessor = method; 
					}
					else {
						throw new EveError("EJI objects can only have one indexed accessor method.");
					}
				}
				else if (method.isAnnotationPresent(EJIIndexedMutator.class)) {
					if (indexedMutator == null) {
						indexedMutator = method; 
					}
					else {
						throw new EveError("EJI objects can only have one indexed mutator method.");
					}
				}
				else {
					methodNames.add(method.getName());
				}
			}
		}
		
		for (String methodName : methodNames) {
			EJIFunction methodInvocation = EJIFunction.fromJava(methodName, false);
			eo.putField(methodName, EveObjectFactory.create(methodInvocation));
		}
		
		if (indexedAccessor != null) {
			EJIFunction accessor = EJIFunction.fromJava(indexedAccessor, false);
			eo.setIndexedAccessor(EveObjectFactory.create(accessor));
		}
		
		if (indexedMutator != null) {
			EJIFunction mutator = EJIFunction.fromJava(indexedMutator, false);
			eo.setIndexedMutator(EveObjectFactory.create(mutator));
		}
		
		return eo;
	}
	
	/**
	 * Creates a "native namespace" from the given Java class. The class must have the {@link EJIModule}
	 * annotation present. This method will find all public static methods in the class and convert
	 * them either to functions or read-only properties. If a method has the {@link EJIProperty} annotation,
	 * it will convert the method to a read-only property with the name specified by the annotation.
	 * If there is no annotation present, it will convert the static method to a function. Static
	 * variables, non-static variables, and non-static methods are all ignored.
	 * @param cl
	 * @return the created module type.
	 */
	public static EveObject createEJIModuleType(Class<?> cl) {
		if (!cl.isAnnotationPresent(EJIModule.class) && !cl.isAnnotationPresent(EJIMergeModule.class)) {
			throw new EveError(cl.getName() + " is not a valid EJI module.");
		}
		
		if (cl.isAnnotationPresent(EJIModule.class) && cl.isAnnotationPresent(EJIMergeModule.class)) {
			throw new EveError(cl.getName() + " must have either @EJIModule or @EJIMergeModule ONLY.");
		}
		
		boolean bypassTypeCoercion = false;
		if (cl.isAnnotationPresent(EJINoCoerce.class)) {
			bypassTypeCoercion = true;
		}
		
		String namespace = "";
		if (cl.isAnnotationPresent(EJIModule.class)) {
			namespace = cl.getAnnotation(EJIModule.class).value();
		}
		else {
			namespace = cl.getAnnotation(EJIMergeModule.class).value();
		}
		
		EveObject type = EveObjectFactory.prototypeType(namespace);
		
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
			EJIFunction methodInvocation = EJIFunction.fromStatic(cl, entry.getValue().getName(), bypassTypeCoercion);
			type.putField(entry.getKey(), EveObjectFactory.create(methodInvocation));
		}		

		//BuiltinCommons.addType(namespace, type);
		return type;
	}

}
