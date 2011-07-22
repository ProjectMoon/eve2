package eve.tests;

import org.junit.Test;

import eve.core.EveCore;
import eve.core.EveObject;
import eve.scope.ScopeManager;
import static org.junit.Assert.*;

public class EqualsExpressions {
	private EveCore core = new EveCore();
	
	@Test
	public void plus() {
		core.runCode("var x = 5; x += 5;");
		EveObject x = ScopeManager.getVariable("x");
		assertTrue("x should be 10", x.getIntValue() == 10);
	}
	
	@Test
	public void minus() {
		core.runCode("var x2 = 10; x2 -= 5;");
		EveObject x = ScopeManager.getVariable("x2");
		assertTrue("x should be 5", x.getIntValue() == 5);		
	}
	
	@Test
	public void multiply() {
		core.runCode("var x3 = 5; x3 *= 5;");
		EveObject x = ScopeManager.getVariable("x3");
		assertTrue("x should be 25", x.getIntValue() == 25);
	}
	
	@Test
	public void divide() {
		core.runCode("var x4 = 25; x4 /= 5;");
		EveObject x = ScopeManager.getVariable("x4");
		assertTrue("x should be 5", x.getIntValue() == 5);
	}
	
	@Test
	public void modulus() {
		core.runCode("var x5 = 5; x5 %= 5;");
		EveObject x = ScopeManager.getVariable("x5");
		assertTrue("x should be 0", x.getIntValue() == 0);
	}
	
	@Test
	public void concat() {
		core.runCode("var y = \"hi \"; y ~= \"there\";");
		EveObject y = ScopeManager.getVariable("y");
		assertTrue("y should be \"hi there\"", y.getStringValue().equals("hi there"));
	}
}
