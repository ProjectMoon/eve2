package eve.core;
import java.util.List;
import java.util.Queue;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import eve.core.EveLexer;
import eve.core.EveParser;
import eve.core.EveParser.program_return;
import eve.scope.ScopeManager;
import eve.statements.EveStatement;

public class EveCore {
	private static EveObject createGlobal() {
		EveObject global = EveObject.globalType();
		//need anything else?
		return global;
	}
	
	public static void main(String[] args) throws RecognitionException {
		ScopeManager.setGlobalScope(createGlobal());
		ScopeManager.pushScope(ScopeManager.getGlobalScope());
		
		CharStream stream = new ANTLRStringStream("def y = (a) {}; var x = 4; print(x + 1); x = 10; print(x); print(y);");
		EveLexer lexer = new EveLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		EveParser parser = new EveParser(tokenStream);
		program_return main = parser.program();
		System.out.println(main.tree.toStringTree());
		
		
		CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(main.tree);
		/*
		EveInterpreter interp = new EveInterpreter(nodeStream);
		interp.interpret();
		
		Queue<EveStatement> code = ScopeManager.getGlobalScope().getCode();
		for (EveStatement statement : code) {
			System.out.println(code);
			statement.execute();
		}
		
		System.out.println("global fields: " + ScopeManager.getGlobalScope().getFields());
		*/
		ASTParser tp = new ASTParser(nodeStream);
		tp.downup(main.tree);
		ExecutionTree.testExecute();
		ExecutionTree.execute();
		
		//In EveInterpreter.g, we want to construct EveStatements and store them according to scope.
		//EveStatements are then all executed later, in sequence.
		//ex: EveAssignmentStatement is eo = EveExpressionStatement.execute();
		//Handling scope: build state machine into EveInterpreter.g
			//Scope goes: global -> object -> function
			//object scope is defined when proto is created.
			//function scope is during execution of a function.
			//single threaded, so only one scope, set globally by the interpreter.
				
		/*
		 * Separate execution tree:
		 * We would need a separate ChangeScope statement that gets created by the tree parser.
		 * ChangeScope takes an EveObject to put as the new scope.
		 * This might be good, because it will allow easier :: implementation later.
		 * But now we have to be aware of execution tree...
		 * tree can be implemented by adding List<Statement> to every EveStatement.
		 * we should then be able to recursively evalute script.
		 * however, what about functions? do not want to call them unless called!
		 * we need to give the option for Statements to terminate execution: i.e. FunctionExpression
		 * would stop the recursive descent if the call stack parent is not a FunctionInvocation.
		 * 
		 * does the execution tree need to be a tree? with changescope statement, we can flatten...
		 * in theory.
		 * 
		 * execution tree is simply a list of Statements, but functions collect Statements themselves.
		 * Prototypes will do the same.
		 * 
		 */
	}
}
