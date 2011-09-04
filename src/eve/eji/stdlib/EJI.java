package eve.eji.stdlib;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.builtins.BuiltinCommons;
import eve.eji.EJIHelper;
import eve.eji.EJINamespace;
import eve.eji.EJIScanner;

@EJINamespace("eji")
public class EJI {
	public static void init() {
		EJIHelper.createEJINamespace(EJI.class);
	}
	
	public static EveObject expose(String className) {
		return expose0(className, false);
	}
	
	public static EveObject exposeType(String className) {
		return expose0(className, true);
	}
	
	public static void scan(String pkg) {
		EJIScanner scanner = new EJIScanner();
		scanner.addPackage(pkg);
		scanner.scan();
	}
	
	private static EveObject resolveJavaPackageContainer(String fqcn) {
		String[] split = fqcn.split("\\.");
		
		EveObject pkgContainer = BuiltinCommons.getType(split[0]);
		if (pkgContainer == null) {
			pkgContainer = EveObject.prototypeType(split[0]);
			BuiltinCommons.addType(split[0], pkgContainer);
		}
		
		EveObject prevContainer = pkgContainer;
		for (int c = 1; c < split.length - 1; c++) {
			pkgContainer = pkgContainer.getField(split[c]);
			if (pkgContainer == null) {
				pkgContainer = EveObject.prototypeType(split[c]);
				prevContainer.putField(split[c], pkgContainer);
			}
			prevContainer = pkgContainer;
		}
		
		return pkgContainer;
	}
	
	private static EveObject expose0(String className, boolean exposeType) {
		try {
			Class<?> cl = Class.forName(className);
			EveObject ctorFunc = EJIHelper.createEJIConstructor(cl);
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
				BuiltinCommons.addType(simpleName, ctorFunc);
			}
		}
		catch (ClassNotFoundException e) {
			throw new EveError("EJI error: " + e.getMessage());
		}
		
		return null;
	}
}
