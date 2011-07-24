package eve.eji;

import eve.core.EveObject;

@EJINamespace("native")
public class Test {
	public static void hello() {
		System.out.println("hello natively");
	}
	
	@EJIProperty("obj")
	public static int getObj() {
		return 5;
	}
}
