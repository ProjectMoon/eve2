package eve.eni;

import eve.core.EveFunction;
import eve.core.EveObject;

public class EveNativeFunction extends EveFunction {
	private NativeCode code;
	
	public EveNativeFunction(NativeCode code) {
		this.code = code;
	}
	
	@Override
	public EveObject execute() {
		return code.execute();
	}
}
