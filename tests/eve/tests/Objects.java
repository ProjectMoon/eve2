package eve.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import eve.core.EveCore;
import eve.core.EveObject;
import eve.scope.ScopeManager;

public class Objects {
	private EveCore core = new EveCore();
	
	public Objects() {}

	@Test
	public void createObject() {
		core.runCode("var x = {};");
		EveObject x = ScopeManager.getVariable("x");
		
		assertTrue("x does not exist!", x != null);		
	}
	
	@Test
	public void createObjectWithProperty() {
		core.runCode("var y = { y: 5 };");
		EveObject y = ScopeManager.getVariable("y");
		
		assertTrue("x does not exist!", y != null);		
		assertTrue("could not find field y in y", y.hasField("y"));
		assertTrue("y.y != 5", y.getField("y").getIntValue() == 5);
	}
	
	@Test
	public void deleteObject() {
		core.runCode("var z = {};");	
		assertTrue("z does not exist for test!", ScopeManager.getVariable("z") != null);

		core.runCode("delete z;");
		assertTrue("z was not deleted", ScopeManager.getVariable("z") == null);
	}
}
