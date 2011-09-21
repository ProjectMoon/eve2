package eve.eji;

import java.beans.IntrospectionException;
import java.util.HashSet;
import java.util.Set;

import com.impetus.annovention.ClasspathDiscoverer;
import com.impetus.annovention.Discoverer;
import com.impetus.annovention.listener.ClassAnnotationDiscoveryListener;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.builtins.BuiltinCommons;

/**
 * The EJIScanner scans the classpath for EJI types. Its main purpose is to load the
 * standard library and built-in types. It also loads EJI code created by users.
 * @author jeff
 *
 */
public class EJIScanner {	
	public EJIScanner() {
		
	}	
		
	/**
	 * Scan the classpath for EJI types, and either add them to the type pool
	 * (if they are marked with EJIBuiltin), or to the external types map
	 * (if they are not marked). Builtins are always created before external
	 * types.
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
		
		
		Set<Class<?>> builtins = new HashSet<Class<?>>();
		Set<Class<?>> externals = new HashSet<Class<?>>();
		
		for (String className : foundTypes) {
			try {
				Class<?> type = Class.forName(className);
				
				if (type.isAnnotationPresent(EJIBuiltinType.class)) {
					builtins.add(type);
				}
				else {
					externals.add(type);
				}
				
			}
			catch (ClassNotFoundException e) {
				throw new EveError("EJI error: EJI class not found: " + e.getMessage());
			}
		}
				
		try {
			createEJITypes(builtins);
			createEJITypes(externals);
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
		//need to process these in order... builtins before anything else.
		
		for (Class<?> type : types) {
			EJIType typeInfo = type.getAnnotation(EJIType.class);
			
			//bypass type coercion if the type specifies it.
			boolean bypassTypeCoercion = false;
			
			if (type.isAnnotationPresent(EJINoCoerce.class)) {
				bypassTypeCoercion = true;
			}
			
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
