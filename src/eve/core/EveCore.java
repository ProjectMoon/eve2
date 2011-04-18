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
		 * Tree parser:
		 * Think state machine. Use list to keep track of statements, and clear out
		 * when switching scope. Maybe too simplistic. Keep list of statements per scope?
		 * Keep list of statements only for functions. But we need to make sure it is clear
		 * so only function statements get into it. What will make it stay clear?
		 * 
		 * Need to clear() pretty much everywhere, but only if not in a function.
		 * But we don't know if we're in a function until we've popped up the tree
		 * and figured out we need to make a function!
		 * 
		 * May need to subclass TreeAdaptor and provide a method to determine if we are
		 * in a function (i.e. getParent() == FUNCTION_BODY).
		 * 
		 * Keep in mind that everything is bottom-up! We cannot assume that we are in the
		 * logically correct scope when creating certain statements.
		 * !!!We must remember that scope will be set AFTER we process statements in that scope!!!!
		 * 
		 * !!!!!*******VICTORY*******!!!!!!!!
		 * During intial pass, all statements go into statement queue.
		 * Function eval clears out statement queue and adds statements to the FunctionExpression.
		 * FunctionExpression gets put into function queue.
		 * Proto clears out statement queue AND function queue, adding all of them as code.
		 * Proto is added to proto queue.
		 * At EOF, global obj clears all three queues, adding every single thing as code.
		 * Script is now in memory, and can be executed. <-- can add AoT "compile" step here.
		 * ScopeManager is used during execution to keep track of call stack.
		 */
		
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
		 */
		
		/*
		 * Statements:
		 * EveStatement: interface with public EveObject execute();
		 * AssignmentStatement(EveObject eo, ExpressionStatement expr);
		 * FunctionDefStatement(EveObject protoScope, EveFunction func);
		 * ProtoStatement(List<EveStatement> stmts);
		 * FunctionInvokeStatement(); <-- nothing because there will be scope, right?!
		 */
		
		/*
		 * ScopeManager:
		 * 	global (technically another object scope).
		 * 	object
		 * 	function
		 */
		
		/*
		 * Reference other scopes with scopeIdent::. global::func(), obj::method().
		 * Provide self.x as alias for obj::x
		 */
		
		/*
		 * Global is an uncloneable prototype. 
		 */
	}
}
