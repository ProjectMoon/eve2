package eve.eji.stdlib;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.RecognitionException;

import eve.core.EveCore;
import eve.core.EveError;
import eve.core.Script;
import eve.eji.EJIFunctionName;
import eve.eji.EJIHelper;
import eve.eji.EJINamespace;
import eve.eji.EJIScanner;
import eve.scope.ScopeManager;

/**
 * The core library contains all the functions that should be accessible
 * everywhere from within eve.
 * @author jeff
 *
 */
@EJINamespace("_global")
public class Core {
	private static final List<File> IMPORTED_FILES = new ArrayList<File>();
	private static final List<Class<?>> IMPORTED_CLASSES = new ArrayList<Class<?>>();
	
	/**
	 * The import function imports eve modules and EJI namespaces. Eve modules
	 * are simply a string path to an Eve file. EJI namespaces can be imported
	 * via fully qualified classname or "package:namespace" short form (e.g.
	 * "com.mycompany.eve:mynamespace").
	 * @param filename
	 */
	@EJIFunctionName("import")
	public static void importFunction(String filename) {
		File file = new File(filename);
		
		//silently ignore already-imported files.
		if (IMPORTED_FILES.contains(file)) {
			return;
		}
		
		if (!file.exists()) {
			attemptEJIImport(filename); //actually considered classname here.
		}
		else if (file.exists() && !IMPORTED_FILES.contains(file)) {
			attemptFileImport(file);
		}
		else {
			throw new EveError("could not load module " + filename);
		}
	}
	
	private static void attemptFileImport(File file) {
		IMPORTED_FILES.add(file);
		
		EveCore core = new EveCore();
		try {
			Script script = core.getScript(file.getAbsolutePath());
			
			if (script.getNamespace().equals("_global")) {
				ScopeManager.setNamespace("_global");
				ScopeManager.pushScope(ScopeManager.getGlobalScope());
				script.execute();
				ScopeManager.popScope();
				ScopeManager.revertNamespace();
			}
			else {
				ScopeManager.defineNamespace(script.getNamespace(), script);
			}
		}
		catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void attemptEJIImport(String className) {
		//if this is not a file, try to load a class instead.
		try {
			importEJINamespace(className);
		}
		catch (ClassNotFoundException e) {
			//now try short-form class, based on @EJINamespace value (and possibly package)
			if (className.contains(":")) {
				String[] pkgAndNS = className.split(":");
				importNamespace(pkgAndNS[0], pkgAndNS[1]);
			}
			else {
				importNamespace("eve.eji.stdlib", className);
			}
		}
	}
	
	private static void importNamespace(String pkg, String namespace) {
		Class<?> cl = EJIScanner.findNamespace(pkg, namespace);
		
		if (cl != null) {
			importEJINamespace(cl);
		}
		else {
			throw new EveError("Could not find namespace " + namespace);
		}
	}
	
	private static void importEJINamespace(String className) throws ClassNotFoundException {
		Class<?> cl = Class.forName(className);
		importEJINamespace(cl);
	}
	
	private static void importEJINamespace(Class<?> cl) {	
		if (!IMPORTED_CLASSES.contains(cl)) {
			EJIHelper.createEJINamespace(cl);
			IMPORTED_CLASSES.add(cl);
		}
	}
}
