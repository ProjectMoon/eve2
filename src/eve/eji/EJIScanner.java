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

public class EJIScanner {
	private static final Map<String, Class<?>> memoizedNamespaces = new HashMap<String, Class<?>>();
	
	private List<String> packages = new ArrayList<String>();
	
	public EJIScanner() {
		
	}
	
	public void addPackage(String packageName) {
		packages.add(packageName);
	}
	
	public void scan() {
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
		
		Reflections reflections = new Reflections(cb);
		
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
	
	public Class<?> findNamespace(String pkg, String namespace) {
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
		
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(EJINamespace.class);
		
		if (annotated.size() <= 0) {
			throw new EveError("could not find any standard namespaces! fatal!");
		}
		else {
			for (Class<?> cl : annotated) {
				EJINamespace ns = cl.getAnnotation(EJINamespace.class);
				if (ns.value().equals(namespace)) {
					memoizedNamespaces.put(pkg + ":" + namespace, cl);
					return cl;
				}
			}
			
			throw new EveError("could not find standard namespace " + namespace);
		}		
	}
	
	public Class<?> findStandardNamespace(String namespace) {
		return findNamespace("eve.eji.stdlib", namespace);
	}
	
	private void createEJITypes(Set<Class<?>> types) throws InstantiationException, IllegalAccessException, IntrospectionException {
		for (Class<?> type : types) {
			EJIType typeInfo = type.getAnnotation(EJIType.class);
			EveObject ctor = EJIHelper.createEJIConstructor(type);
			EveObject eo = EveObject.prototypeType(typeInfo.value());
			eo.putField("__create", ctor);
			
			//whether or not we should consider this a built-in type.
			//all eve types are built-in. all user-define types should
			//not be built-in.
			EJIBuiltinType builtin = type.getAnnotation(EJIBuiltinType.class);
			if (builtin != null) {
				BuiltinCommons.addType(typeInfo.value(), eo);
			}
			else {
				ExternalTypes.addType(typeInfo.value(), eo);
			}
		}
	}
}
