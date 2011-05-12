package eve.eji;

import eve.core.Function;
import eve.core.EveObject;

public class NativeFunction extends Function {
	private NativeCode code;
	
	public NativeFunction(NativeCode code) {
		this.code = code;
	}
	
	@Override
	public EveObject execute() {
		return code.execute();
	}
}
