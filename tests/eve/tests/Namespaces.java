package eve.tests;

import org.junit.Test;

import eve.core.EveCore;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import static org.junit.Assert.*;

public class Namespaces {
	private EveCore core = new EveCore();
	
	@Test
	public void namespaceAssignmentInFunction() {
		core.runCode("typedef g = {}; def f() { g::value = 1; } f();");
		EveObject g = ScopeManager.getVariable("g");
		
		assertTrue("g::value was not 1", g.getField("value").getIntValue() == 1);
	}
}
