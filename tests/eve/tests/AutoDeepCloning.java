package eve.tests;

import org.junit.Test;

import eve.core.EveCore;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import static org.junit.Assert.*;

public class AutoDeepCloning {
	private EveCore core = new EveCore();
	
	@Test
	public void gettersAndSetters() {
		//They will be === to begin with,
		//but auto deep cloning will take care of changing them.
		String code = "var x = {};" +
				"x.y = {};" +
				"x.y->get = () {" +
				"return self->py;" +
				"};" +
				"x.y->set = (value) { self->py = value; };" +
				"var z = clone x;" +
				"x.y = 5; z.y = 6;";
		
		core.runCode(code);
		
		EveObject x = ScopeManager.getVariable("x");
		EveObject z = ScopeManager.getVariable("z");
		
		assertTrue("x.y->get is equal to z.y->get!", x.getField("y").getField("get") != z.getField("y").getField("get"));
		assertTrue("x.y->set is equal to z.y->set!", x.getField("y").getField("set") != z.getField("y").getField("set"));
	}
}
