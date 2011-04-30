package eve.eni.stdlib;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;

import eve.core.EveCore;
import eve.core.EveObject;
import eve.eni.NativeCode;
import eve.eni.NativeFunction;
import eve.scope.ScopeManager;

public class Core {
	public static void init() {
		ScopeManager.setNamespace("global");
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
					core.run(file);
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
