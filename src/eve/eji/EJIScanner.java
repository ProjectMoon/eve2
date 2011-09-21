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

import com.impetus.annovention.ClasspathDiscoverer;
import com.impetus.annovention.Discoverer;
import com.impetus.annovention.listener.ClassAnnotationDiscoveryListener;

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
	public EJIScanner() {
		
	}	
		
	/**
	 * Scan the classpath for EJI types, and either add them to the type pool
	 * (if they are marked with EJIBuiltin), or to the external types map
	 * (if they are not marked).
	 */	
	public void scanForTypes() {
		final Set<String> foundTypes = new HashSet<String>();
		Discoverer d = new ClasspathDiscoverer();
		
		d.addAnnotationListener(new ClassAnnotationDiscoveryListener() {
			@Override public String[] supportedAnnotations() {
				return new String[] { EJIType.class.getName() };
			}
			
			@Override public void discovered(String className, String annotation) {
				foundTypes.add(className);
			}
		});
		
		d.discover(true, false, false, true, false);
		
		Set<Class<?>> types = new HashSet<Class<?>>();
		
		for (String className : foundTypes) {
			try {
				types.add(Class.forName(className));
			}
			catch (ClassNotFoundException e) {
				throw new EveError("EJI error: EJI class not found: " + e.getMessage());
			}
		}
		
		try {
			createEJITypes(types);
		}
		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IntrospectionException e) {
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
			boolean bypassTypeCoercion = false;
			if (type.isAnnotationPresent(EJINoCoerce.class)) {
				//ctor = EJIHelper.createEJIConstructor(type, true);
				bypassTypeCoercion = true;
			}
			else {
				//ctor = EJIHelper.createEJIConstructor(type, false);
			}
			
			//EveObject eveType = EJIHelper.createEJIType(typeInfo.value(), ctor);
			EveObject eveType = EJIHelper.createEJIType(type, bypassTypeCoercion);
			
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
}
