package eve.core.builtins;

import eve.core.EveObject;
import eve.eni.NativeHelper;

/**
 * 	The java prototype.
 * @author jeff
 *
 */
public class EveJava extends EveObject {
	private static final EveJava proto = new EveJava();
	
	public static EveJava getPrototype() {
		return proto;
	}
	
	private EveJava() {
		this.setTypeName("java");
		this.setType(EveType.PROTOTYPE);
		this.putField("create", NativeHelper.javaFunction());
		//this.putField("class", )
	}
}
