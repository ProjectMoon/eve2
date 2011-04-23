package eve.eni;

import eve.core.Function;
import eve.core.EveObject;

public class EveNativeFunction extends Function {
	private NativeCode code;
	
	public EveNativeFunction(NativeCode code) {
		this.code = code;
	}
	
	@Override
	public EveObject execute() {
		return code.execute();
	}
}
