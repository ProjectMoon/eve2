package eve.eji.stdlib;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;

import eve.core.EveCore;
import eve.core.EveObject;
import eve.core.Script;
import eve.eji.NativeCode;
import eve.eji.NativeFunction;
import eve.scope.ScopeManager;

public class Core {
	public static void init() {
		ScopeManager.setNamespace("_global");
		ScopeManager.putVariable("import", importFunction());
		ScopeManager.revertNamespace();
	}
	
	private static EveObject importFunction() {
		final NativeCode nc = new NativeCode() {
			@Override
			public EveObject execute() {
				String file = ScopeManager.getVariable("file").getStringValue();
				
				EveCore core = new EveCore();
				try {
					Script script = core.getScript(file);
					ScopeManager.setNamespace("_global");
					ScopeManager.pushScope(ScopeManager.getGlobalScope());

					if (!script.getNamespace().equals("_global")) {
						ScopeManager.setNamespace(script.getNamespace());
						ScopeManager.createGlobalScope();
					}
					
					eve.eji.stdlib.Java.init();
					eve.eji.stdlib.Core.init();
					script.execute();
					ScopeManager.revertNamespace();
					
					if (!script.getNamespace().equals("_global")) {
						ScopeManager.revertNamespace();
					}
					
				} catch (RecognitionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
			}		
		};
		
		NativeFunction nfunc = new NativeFunction(nc);
		nfunc.addParameter("file");
		return new EveObject(nfunc);
	}
}
