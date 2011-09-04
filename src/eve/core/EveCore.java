package eve.core;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import eve.core.EveParser.program_return;
import eve.eji.EJIScanner;
import eve.scope.ConstructionScope;
import eve.scope.ScopeManager;

public class EveCore {
	private boolean printSyntaxTree;
	private boolean repl = false;
	
	/**
	 * Parses options and returns the filename to load.
	 * @param args
	 * @return
	 */
	private String parseOptions(String[] args) {
		Options opts = new Options();
		opts.addOption("e", true, "evaluate code");
		opts.addOption("d", false, "debug mode");
		opts.addOption("t", false, "print syntax tree");
		opts.addOption("h", false, "print help");
		opts.addOption("i", true, "register instrumentation hook");
		opts.addOption("r", false, "enter repl mode");
		opts.addOption("eji", true, "make EJI types available");
		
		CommandLineParser parser = new GnuParser();
		
		try {
			CommandLine line = parser.parse(opts, args);
			
			if (line.hasOption("h")) printHelp(opts);
			if (line.hasOption("d")) EveLogger.debugLevel();
			if (line.hasOption("t")) printSyntaxTree = true;
			if (line.hasOption("eji")) handleEJI(line.getOptionValue("eji"));
			if (line.hasOption("r")) repl();
			if (line.hasOption("e")) {
				eval(line.getOptionValue("e"));
			}
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
	
	private void eval(String code) {
		Script script = getScriptFromCode(code);
		this.run(script);
		System.exit(0);
	}
	
	private void repl() {
		EveREPL repl = new EveREPL();
		repl.run();
		System.exit(0);
	}
	
	private void printHelp(Options opts) {
		HelpFormatter help = new HelpFormatter();
		help.printHelp("eve", opts);
		System.exit(0);
	}
	
	private void handleEJI(String ejiLine) {
		String[] pkgs = ejiLine.split(File.pathSeparator);
		
		EJIScanner scanner = new EJIScanner();
		
		for (String pkg : pkgs) {
			scanner.addPackage(pkg);
		}
		
		try {
			scanner.scan();
		}
		catch (EveError e) {
			System.err.println("EJI error: " + e.getMessage());
			System.exit(1);
		}
	}
	
	private void handleErrors(List<String> errors) {
		for (String error : errors) {
			System.err.println(error);
		}
		System.exit(1);
	}
	
	public void run(String file) throws RecognitionException, IOException {
		Script script = getScript(file);
		run(script);
	}
	
	public void run(Script script) {
		ScopeManager.setNamespace("_global");
		ScopeManager.createGlobalScope();
		
		if (!script.getNamespace().equals("_global")) {
			ScopeManager.setNamespace(script.getNamespace());
			ScopeManager.createGlobalScope();
		}
		
		eve.eji.stdlib.EJI.init();
		eve.eji.stdlib.Core.init();
				
		script.execute();
		ScopeManager.revertNamespace();
	}
	
	public void initForREPL() {
		if (!repl) {
			ScopeManager.setNamespace("_global");
			ScopeManager.createGlobalScope();
					
			eve.eji.stdlib.EJI.init();
			eve.eji.stdlib.Core.init();
			repl = true;
		}
	}
	
	public Script getScript(String file) throws RecognitionException, IOException {
		File inputFile = new File(file);
		
		if (!inputFile.exists()) {
			System.err.println("file " + inputFile.getAbsolutePath() + " not found. exiting.");
			System.exit(1);
		}
		
		InputStream input = new FileInputStream(file);
		CharStream stream = new ANTLRInputStream(input);
	
		return getScript0(stream);
	}

	public Script getScriptFromCode(String code) {
		CharStream stream = new ANTLRStringStream(code);
		return getScript0(stream);
	}
	
	private Script getScript0(CharStream stream) {
		EveLexer lexer = new EveLexer(stream);
		
		TokenStream tokenStream = new CommonTokenStream(lexer);
		EveParser parser = new EveParser(tokenStream);
		program_return main = null;
		
		try {
			main = parser.program();
		}
		catch (EveError e) {
			if (!repl) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
			else {
				throw e;
			}
		}
		catch (RecognitionException e) {
			if (!repl) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
			else {
				throw new EveError(e);
			}
		}
			
		if (parser.hasErrors()) {
			if (!repl) {
				handleErrors(parser.getErrors());
			}
			else {
				String message = "";
				for (String error : parser.getErrors()) {
					message += error + "\n";
				}
				throw new EveError(message);
			}
		}
		
		//If the file is empty, the tree will be null.
		//Thus, we have an empty script.
		if (main.tree == null) {
			return new Script();
		}
			
		//global is root construction scope.
		Script script = new Script();
		ScopeManager.setScript(script);
		ScopeManager.pushConstructionScope(script);
		CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(main.getTree());
		ASTParser treeParser = new ASTParser(nodeStream);
		treeParser.downup(main.tree);
		
		if (treeParser.hasErrors()) {
			handleErrors(parser.getErrors());
		}
		
		//we should be back to global scope after construction phase.
		ConstructionScope cs = ScopeManager.popConstructionScope();
		if (cs instanceof Script) {
			return script;
		}
		else {
			throw new EveError("Did not receive global scope from construction phase.");
		}		
	}
	
	private void printTree(String file) throws IOException, RecognitionException {
		File inputFile = new File(file);
		
		if (!inputFile.exists()) {
			System.err.println("file " + inputFile.getAbsolutePath() + " not found. exiting.");
			System.exit(1);
		}
		
		InputStream input = new FileInputStream(file);
		CharStream stream = new ANTLRInputStream(input);
	
		EveLexer lexer = new EveLexer(stream);
		
		TokenStream tokenStream = new CommonTokenStream(lexer);
		EveParser parser = new EveParser(tokenStream);
		program_return main = null;
		
		try {
			main = parser.program();
		}
		catch (EveError e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
			
		if (parser.hasErrors()) {
			handleErrors(parser.getErrors());
		}
							
		System.out.println(main.tree.toStringTree());
	}
	
	public boolean runCode(String code) {
		if (!repl) {
			this.initForREPL();
		}
		
		Script script = getScriptFromCode(code);
		
		try {
			if (!script.getNamespace().equals("_global")) {
				ScopeManager.setNamespace(script.getNamespace());
				ScopeManager.createGlobalScope();
			}
			
			script.execute();
			return true;
		}
		catch (Exception e) {
			System.err.println(e);
			return false;
		}
		
	}
	
	public static void main(String[] args) throws RecognitionException, IOException {
		EveCore eve = new EveCore();
		String file = eve.parseOptions(args);
		
		if (eve.printSyntaxTree) {
			eve.printTree(file);
		}
		else {
			eve.run(file);
		}
	}
}
