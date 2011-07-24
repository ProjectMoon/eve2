package eve.eji.stdlib;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.RecognitionException;

import eve.core.EveCore;
import eve.core.EveObject;
import eve.core.Script;
import eve.core.builtins.BuiltinCommons;
import eve.eji.EJIFunction;
import eve.eji.EJIHelper;
import eve.eji.EJINamespace;
import eve.eji.EJIProperty;
import eve.scope.ScopeManager;

@EJINamespace("_global")
public class Core {
	private static EveObject importFunction = new EveObject(new ImportFunction());
	
	private static final List<File> IMPORTED_FILES = new ArrayList<File>();
	
	public static void init() {
		EJIHelper.createEJINamespace(Core.class);
	}
	
	//Done this way for now because we can't name the method import...
	@EJIProperty("import")
	public static EveObject importf() {
		return importFunction;
	}
		
	private static class ImportFunction extends EJIFunction {
		public ImportFunction() {
			setParameters("file");
		}

		@Override
		public EveObject execute(Map<String, EveObject> parameters) {
			File file = new File(ScopeManager.getVariable("file").getStringValue());
			
			if (!IMPORTED_FILES.contains(file)) {
				IMPORTED_FILES.add(file);
				
				EveCore core = new EveCore();
				try {
					Script script = core.getScript(file.getAbsolutePath());
					ScopeManager.setNamespace("_global");
					ScopeManager.pushScope(ScopeManager.getGlobalScope());

					if (!script.getNamespace().equals("_global")) {
						ScopeManager.setNamespace(script.getNamespace());
						ScopeManager.createGlobalScope();
						BuiltinCommons.addType(script.getNamespace(), EveObject.namespaceType(script.getNamespace()));
					}
					
					eve.eji.stdlib.Java.init();
					eve.eji.stdlib.Core.init();
					script.execute();
					ScopeManager.popScope();
					ScopeManager.revertNamespace();
					
					if (!script.getNamespace().equals("_global")) {
						ScopeManager.popScope();
						ScopeManager.revertNamespace();
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
			
			return null;
		}
	}
	
}
