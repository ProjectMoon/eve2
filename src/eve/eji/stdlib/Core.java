package eve.eji.stdlib;

import java.io.IOException;
import java.util.Map;

import org.antlr.runtime.RecognitionException;

import eve.core.EveCore;
import eve.core.EveObject;
import eve.core.Script;
import eve.core.builtins.BuiltinCommons;
import eve.eji.EJIFunction;
import eve.scope.ScopeManager;

public class Core {
	public static void init() {
		ScopeManager.setNamespace("_global");
		ScopeManager.putVariable("import", importFunction());
		ScopeManager.revertNamespace();
	}
	
	private static EveObject importFunction() {
		class ImportFunction extends EJIFunction {
			public ImportFunction() {
				setParameters("file");
			}

			@Override
			public EveObject execute(Map<String, EveObject> parameters) {
				String file = ScopeManager.getVariable("file").getStringValue();
				
				EveCore core = new EveCore();
				try {
					Script script = core.getScript(file);
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
					ScopeManager.revertNamespace();
					
					if (!script.getNamespace().equals("_global")) {
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
				
				return null;
			}
		}
		
		return new EveObject(new ImportFunction());
	}
}
