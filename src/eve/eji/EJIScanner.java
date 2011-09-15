package eve.eji;

import java.beans.IntrospectionException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.builtins.BuiltinCommons;

/**
 * The EJIScanner scans Java packages for types and namespaces. Its main purpose
 * is to load the standard library and built-in types. It also loads EJI code
 * created by users. Finally, it provides two static methods for finding namespace
 * classes.
 * @author jeff
 *
 */
public class EJIScanner {
	private static final Map<String, Class<?>> memoizedNamespaces = new HashMap<String, Class<?>>();
	
	private List<String> packages = new ArrayList<String>();
	
	public EJIScanner() {
		
	}
	
	/**
	 * Find a namespace in a package. Does not load the namespace; only returns
	 * the class. Use {@link EJIHelper#createEJIModuleType(Class)} for that.
	 * @param pkg
	 * @param namespace
	 * @return The class, if found. Null otherwise.
	 */
	public static Class<?> findNamespace(String pkg, String namespace) {
		//don't need to scan all the time...
		if (memoizedNamespaces.containsKey(pkg + ":" + namespace)) {
			return memoizedNamespaces.get(pkg + ":" + namespace);
		}
		
		FilterBuilder fb = new FilterBuilder();
		Set<URL> pkgUrls = new HashSet<URL>();
		
		fb.include(FilterBuilder.prefix(pkg));
		pkgUrls.addAll(ClasspathHelper.getUrlsForPackagePrefix(pkg));
		
		ConfigurationBuilder cb =
			new ConfigurationBuilder()
			.filterInputsBy(fb)
			.setUrls(pkgUrls)
			.setScanners(new TypeAnnotationsScanner());
		
		Reflections reflections = new Reflections(cb);
		
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(EJIModule.class);
		
		if (annotated.size() <= 0) {
			throw new EveError("could not find any standard namespaces! fatal!");
		}
		else {
			for (Class<?> cl : annotated) {
				EJIModule ns = cl.getAnnotation(EJIModule.class);
				if (ns.value().equals(namespace)) {
					memoizedNamespaces.put(pkg + ":" + namespace, cl);
					return cl;
				}
			}
			
			throw new EveError("could not find standard namespace " + namespace);
		}		
	}
	
	/**
	 * Find a standard namespace (from eve.eji.stdlib).Does not load the namespace; only returns
	 * the class. Use {@link EJIHelper#createEJIModuleType(Class)} for that.
	 * @param namespace
	 * @return The class, if found. Null otherwise.
	 */
	public static Class<?> findStandardNamespace(String namespace) {
		return findNamespace("eve.eji.stdlib", namespace);
	}
	
	/**
	 * Add a package to scan to this scanner.
	 * @param packageName
	 */
	public void addPackage(String packageName) {
		packages.add(packageName);
	}
	
	/**
	 * Creates a Reflections scanner based on the configured packages.
	 * @return the scanner.
	 */
	private Reflections createScanner() {
		FilterBuilder fb = new FilterBuilder();
		Set<URL> pkgUrls = new HashSet<URL>();
		
		for (String pkg : packages) {
			fb.include(FilterBuilder.prefix(pkg));
			pkgUrls.addAll(ClasspathHelper.getUrlsForPackagePrefix(pkg));
		}
		
		ConfigurationBuilder cb =
			new ConfigurationBuilder()
			.filterInputsBy(fb)
			.setUrls(pkgUrls)
			.setScanners(new TypeAnnotationsScanner().filterResultsBy(fb));
		
		return new Reflections(cb);
	}
	
	/**
	 * Scan the configured packages for EJI Types, and either add them to
	 * the type pool (if they are marked with EJIBuiltin), or to the external
	 * types map (if they are not marked).
	 */
	public void scanForTypes() {
		Reflections reflections = createScanner();
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(EJIType.class);
		
		try {
			createEJITypes(annotated);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Process all found EJITypes from a scanForTypes().
	 * @param types
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 */
	private void createEJITypes(Set<Class<?>> types) throws InstantiationException, IllegalAccessException, IntrospectionException {
		for (Class<?> type : types) {
			EJIType typeInfo = type.getAnnotation(EJIType.class);
			EveObject ctor = null;
			
			//bypass type coercion if the type specifies it.
			if (type.isAnnotationPresent(EJINoCoerce.class)) {
				ctor = EJIHelper.createEJIConstructor(type, true);
			}
			else {
				ctor = EJIHelper.createEJIConstructor(type, false);
			}
			
			EveObject eveType = EJIHelper.createEJIType(typeInfo.value(), ctor);
			
			//whether or not we should consider this a built-in type.
			//all eve types are built-in. all user-define types should
			//not be built-in.
			EJIBuiltinType builtin = type.getAnnotation(EJIBuiltinType.class);
			if (builtin != null) {
				BuiltinCommons.addType(typeInfo.value(), eveType);
			}
			else {
				ExternalTypes.addType(typeInfo.value(), eveType);
			}
		}
	}
	
	/**
	 * Scan the configured packages for EJI Namespaces, and add them into
	 * the interpreter environment.
	 */
	public void loadNamespaces() {
		//Due to a bug with the library, we must search *above*
		//eve.eji.stdlib. All standard namespaces are in eve.eji.stdlib
		//however. If we were to search eve.eji.stdlib, we would get no
		//classes.
		Reflections r = createScanner();
		
		Set<Class<?>> annotated = r.getTypesAnnotatedWith(EJIModule.class);
		
		for (Class<?> cl : annotated) {
			EveObject module = EJIHelper.createEJIModuleType(cl);
			BuiltinCommons.addType(cl.getAnnotation(EJIModule.class).value(), module);
		}
		
		Set<Class<?>> merged = r.getTypesAnnotatedWith(EJIMergeModule.class);
		
		for (Class<?> cl : merged) {
			EveObject module = EJIHelper.createEJIModuleType(cl);
			BuiltinCommons.mergeType(cl.getAnnotation(EJIMergeModule.class).value(), module);
		}
	}
}
