package eve.core;

import eve.core.builtins.EveGlobal;
import eve.scope.ScopeManager;
import eve.statements.PrintStatement;
import eve.statements.expressions.IdentExpression;
import eve.statements.expressions.WrappedPrimitiveExpression;
import eve.statements.expressions.YieldExpression;

public class CoroutinesTest {
	public static void main(String[] args) {
		EveObject func1 = function1();
		EveObject func2 = function2();
		
		ScopeManager.setNamespace("global");
		ScopeManager.createGlobalScope();
		ScopeManager.putVariable("func1", func1);
		ScopeManager.putVariable("func2", func2);
		
		ScopeManager.invokeCoroutine(func1);
	}
	
	public static EveObject function1() {	
		WrappedPrimitiveExpression expr = new WrappedPrimitiveExpression("hi from function 1");
		PrintStatement ps = new PrintStatement(expr);
		ps.setPrintNewline(true);
		
		YieldExpression yield = new YieldExpression(new IdentExpression("func1"));
		
		WrappedPrimitiveExpression expr2 = new WrappedPrimitiveExpression("after yield in func1");
		PrintStatement ps2 = new PrintStatement(expr2);
		ps2.setPrintNewline(true);
		
		Function func = new Function();
		func.addStatement(ps);
		func.addStatement(yield);
		func.addStatement(ps2);
		
		return new EveObject(func);
	}
	
	public static EveObject function2() {	
		WrappedPrimitiveExpression expr = new WrappedPrimitiveExpression("hi from function 2");
		PrintStatement ps = new PrintStatement(expr);
		ps.setPrintNewline(true);
		
		YieldExpression yield = new YieldExpression(new IdentExpression("func1"));
		
		WrappedPrimitiveExpression expr2 = new WrappedPrimitiveExpression("after yield in func2");
		PrintStatement ps2 = new PrintStatement(expr2);
		ps2.setPrintNewline(true);
		
		Function func = new Function();
		func.addStatement(ps);
		func.addStatement(yield);
		func.addStatement(ps2);
		
		return new EveObject(func);
	}
}
