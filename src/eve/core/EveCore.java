package eve.core;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Queue;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import eve.core.EveLexer;
import eve.core.EveParser;
import eve.core.EveParser.program_return;
import eve.eni.EveNativeFunction;
import eve.eni.NativeCode;
import eve.hooks.EveHook;
import eve.hooks.HookManager;
import eve.scope.ConstructionScope;
import eve.scope.ScopeManager;

public class EveCore {
	private boolean printSyntaxTree;

	private EveObject createGlobal() {
		EveObject global = EveObject.globalType();
		//need anything else?
		return global;
	}
	
	private void eni() {
		final NativeCode nc = new NativeCode() {
			@Override
			public EveObject execute() {
				System.out.println("nativity!");
				return null;
			}		
		};
		
		final EveNativeFunction nfunc = new EveNativeFunction(nc);
		final EveObject nfuncObject = new EveObject(nfunc);
		
		EveHook hook = new EveHook() {
			@Override
			public void instrument(EveObject eo) {
				eo.putField("nativeTest", nfuncObject);
			}	
		};
		
		HookManager.registerCloneHook(hook);
	}
	
	/**
	 * Parses options and returns the filename to load.
	 * @param args
	 * @return
	 */
	private String parseOptions(String[] args) {
		Options opts = new Options();
		opts.addOption("d", false, "debug mode");
		opts.addOption("t", false, "print syntax tree");
		
		CommandLineParser parser = new GnuParser();
		try {
			CommandLine line = parser.parse(opts, args);
			
			if (line.hasOption("d")) EveLogger.debugLevel();
			if (line.hasOption("t")) printSyntaxTree = true;
			//more options here...
			
			//Find the eve file to run.
			String[] fileArg = line.getArgs();
			if (fileArg.length > 0) {
				return fileArg[0];
			}
			else {
				System.err.println("no eve file specified.");
				System.exit(1);
			}
		}
		catch (ParseException e) {
			System.err.println("parsing failed: " + e.getMessage());
			System.exit(1);
		}
		
		return ""; //keeps compiler happy.
	}
	
	private void run(String file) throws RecognitionException, IOException {
		File inputFile = new File(file);
		
		if (!inputFile.exists()) {
			System.err.println("file " + inputFile + " not found. exiting.");
			System.exit(1);
		}
		
		InputStream input = new FileInputStream(file);
		CharStream stream = new ANTLRInputStream(input);
		
		eni();
		ScopeManager.setGlobalScope(createGlobal());
		ScopeManager.pushScope(ScopeManager.getGlobalScope());
	
		// ANTLRStringStream("def g = (q) { q.z = 6; print(\"q.z is \" ~ q.z); }; proto X { var y = 5; } var x = clone X; g(x);");
		EveLexer lexer = new EveLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		EveParser parser = new EveParser(tokenStream);
		program_return main = parser.program();
		
		if (parser.hasErrors()) {
			for (String error : parser.getErrors()) {
				System.err.println("error: " + error);
			}
			
			System.exit(1);
		}
		
		if (printSyntaxTree) {
			System.out.println(main.tree.toStringTree());
			System.exit(0);
		}
		
		//global is root construction scope.
		ScopeManager.pushConstructionScope(new Script());
		CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(main.getTree());
		ASTParser treeParser = new ASTParser(nodeStream);
		treeParser.downup(main.tree);
		
		//we should be back to global scope after construction phase.
		ConstructionScope cs = ScopeManager.popConstructionScope();
		if ((cs instanceof Script)) {
			Script script = (Script)cs;
			script.execute();
		}
		else {
			throw new EveError("Did not receive global scope from construction phase.");
		}		
	}
	
	public static void main(String[] args) throws RecognitionException, IOException {
		EveCore eve = new EveCore();
		String file = eve.parseOptions(args);
		eve.run(file);
	}
}
