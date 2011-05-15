package eve.eji.stdlib;

import java.util.Map;

import eve.core.EveError;
import eve.core.EveObject;
import eve.core.builtins.EveGlobal;
import eve.eji.EJIFunction;
import eve.eji.EJIHelper;
import eve.scope.ScopeManager;

class JavaFunction extends EJIFunction {
	private boolean exposeType;
	public JavaFunction(boolean exposeType) {
		setParameters("fqcn");
		this.exposeType = exposeType;
	}
	
	private EveObject resolveJavaPackageContainer(String fqcn) {
		String[] split = fqcn.split("\\.");
		
		EveObject pkgContainer = ScopeManager.getVariable(split[0]);
		if (pkgContainer == null) {
			pkgContainer = EveObject.prototypeType(split[0]);
			EveGlobal.addType(split[0], pkgContainer);
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

	@Override
	public EveObject execute(Map<String, EveObject> parameters) {
		String className = parameters.get("fqcn").getStringValue();
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
				EveGlobal.addType(simpleName, ctorFunc);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
