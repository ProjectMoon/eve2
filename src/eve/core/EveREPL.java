package eve.core;

import java.util.Scanner;

import eve.scope.ScopeManager;

public class EveREPL {
	public void run() {
		Scanner scanner = new Scanner(System.in);
		String buffer = "";
		EveCore core = new EveCore();
		String prompt = "> ";
		
		core.initForREPL();
		
		while (true) {
			System.out.print(prompt);
			buffer += scanner.nextLine();
			
			try {
				Script script = core.getScriptFromCode(buffer.trim());
				script.setREPL(true);
				
				if (!script.getNamespace().equals("_global")) {
					ScopeManager.setNamespace(script.getNamespace());
					ScopeManager.createGlobalScope();
				}
				
				script.execute();
				prompt = "> ";
				buffer = "";
			}
			catch (Exception e) {
				prompt = "... ";
				buffer += " ";
			}
		}
	}
}
