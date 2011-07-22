package eve.tests;

import org.junit.Test;

import eve.core.EveCore;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import static org.junit.Assert.*;

public class Namespaces {
	private EveCore core = new EveCore();
	
	@Test
	public void namespaceInFunction() {
		core.runCode("var g = 0; def f() { global::g = 1; } f();");
		EveObject g = ScopeManager.getVariable("g");
		
		assertTrue("g was 0, but should be 1", g.getIntValue() == 1);
	}
}
