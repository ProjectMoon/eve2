package eve.eji.stdlib;

import java.beans.IntrospectionException;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.EveObjectFactory;
import eve.core.builtins.BuiltinCommons;
import eve.eji.EJIHelper;
import eve.eji.EJIModule;
import eve.eji.EJIScanner;

/**
 * The eji namespace exposes functions that allows Java classes to be used directly
 * from within Eve.
 * @author jeff
 *
 */
@EJIModule("eji")
public class EJI {	
	/**
	 * "Import" a Java type into the interpreter via fully-qualified class
	 * name. The class's constructor will become a function available in Eve
	 * as the fully qualified name (e.g. eji::expose("java.util.List") creates
	 * function java.util.List).
	 * @param className
	 * @return
	 */
	public static EveObject expose(String className) {
		return expose0(className, false);
	}
	
	/**
	 * "Import" a Java type into the interpreter and then turn it into an Eve type.
	 * This is different from {@link #expose(String)} in that it exposes the constructor
	 * as an invokeable type (as if it were created via typedef).
	 * @param className
	 * @return
	 */
	public static EveObject exposeType(String className) {
		return expose0(className, true);
	}
	
	/**
	 * Exposes the EJIScanner package scanner to Eve code to allow developers
	 * to programmatically import  their external types. typedef extern is
	 * required to use the found types.
	 * @param pkg The package name to scan.
	 */
	public static void scan(String pkg) {
		EJIScanner scanner = new EJIScanner();
		scanner.addPackage(pkg);
		scanner.scanForTypes();
	}
	
	private static EveObject resolveJavaPackageContainer(String fqcn) {
		String[] split = fqcn.split("\\.");
		
		EveObject pkgContainer = BuiltinCommons.getType(split[0]);
		if (pkgContainer == null) {
			pkgContainer = EveObjectFactory.prototypeType(split[0]);
			BuiltinCommons.addType(split[0], pkgContainer);
		}
		
		EveObject prevContainer = pkgContainer;
		for (int c = 1; c < split.length - 1; c++) {
			pkgContainer = pkgContainer.getField(split[c]);
			if (pkgContainer == null) {
				pkgContainer = EveObjectFactory.prototypeType(split[c]);
				prevContainer.putField(split[c], pkgContainer);
			}
			prevContainer = pkgContainer;
		}
		
		return pkgContainer;
	}
	
	private static EveObject expose0(String className, boolean exposeType) {
		try {
			Class<?> cl = Class.forName(className);
			EveObject ctorFunc = EJIHelper.createEJIConstructor(cl, true); //bypass type coercion to get actual java types.
			EveObject pkgContainer = resolveJavaPackageContainer(className);
			
			if (cl.isAnonymousClass()) {
				throw new EveError("EJI currently does not support anonymous classes");
			}
			
			String simpleName = cl.getSimpleName();
			if (cl.isArray()) {
				simpleName = simpleName.substring(0, simpleName.indexOf("["));
				simpleName += "_Array";
			}
			
			pkgContainer.putField(simpleName, ctorFunc);
			if (exposeType) {
				//TODO: should we switch to the new createEJIType(Class<?>) here?
				//BuiltinCommons.addType(simpleName, EJIHelper.createEJIType(simpleName, ctorFunc));
				BuiltinCommons.addType(simpleName, EJIHelper.createEJIType(cl, true));
			}
		}
		catch (ClassNotFoundException e) {
			throw new EveError("EJI error: " + e.getMessage());
		}
		catch (IntrospectionException e) {
			throw new EveError("EJI error: " + e.getMessage());
		}
		
		return null;
	}
}
