tree grammar EveInterpreter;

options {
  language = Java;
  tokenVocab = Eve;
  ASTLabelType = CommonTree;
}

@header {
	package eve.core;
	import eve.statements.*;
	import eve.statements.assignment.AssignmentStatement;
	import eve.statements.expressions.*;
	import eve.scope.ScopeManager;
	import java.util.Queue;
	import java.util.Set;
	import java.util.LinkedList;
	import java.util.ArrayList;
	import java.util.Map;
	import java.util.HashMap;
	import java.util.Map.Entry;
}

@members {
	//Re-usable list to keep track of statements in a function.
	//Cleared after every function defintion.
	private Queue<EveStatement> statementQueue = new LinkedList<EveStatement>();
	//private Queue<FunctionExpression> functionQueue = new LinkedList<FunctionExpression>();
	private Map<String, EveObject> protos = new HashMap<String, EveObject>();
	
	private void queueStatement(EveStatement statement) {
		statementQueue.add(statement);
	}
	
	private FunctionExpression createFunction() {
		List<EveStatement> funcStatements = new ArrayList<EveStatement>();
		EveStatement statement = null;
		while ((statement = statementQueue.poll()) != null) {
			funcStatements.add(statement);
		}
		
		return new FunctionExpression(funcStatements);
	}
	
	private void queuePrototype(String protoName) {
		EveObject proto = EveObject.customType(protoName);
		
		/*
		FunctionExpression funcExpr = null;
		while ((funcExpr = functionQueue.poll()) != null) {
			proto.addCode(funcExpr);
		}
		*/
		
		EveStatement statement = null;
		while ((statement = statementQueue.poll()) != null) {
			proto.addCode(statement);
		}
		
		protos.put(protoName, proto);
	}
	
	private void globalSetup() {
		EveObject global = ScopeManager.getGlobalScope();
		
		EveObject proto = null;
		Set<Map.Entry<String, EveObject>> entries = protos.entrySet();
		for (Map.Entry<String, EveObject> entry : entries) {
			global.putField(entry.getKey(), entry.getValue());
		}
		
		protos.clear();
		
		/*
		FunctionExpression funcExpr = null;
		while ((funcExpr = functionQueue.poll()) != null) {
			global.addCode(funcExpr);
		}
		*/
		
		EveStatement statement = null;
		while ((statement = statementQueue.poll()) != null) {
			global.addCode(statement);
		}
	}
}

interpret
	:	statement* EOF {
			globalSetup();
		}
	;

statement
	:	codeStatement
	;

printStatement
	:	^('print' e=expression) {
			PrintStatement ps = new PrintStatement(e);
			queueStatement(ps);
		}
	;
	
assignmentStatement
	:	^('=' IDENT e=expression) { 
			System.out.println("assigning " + e + " to " + $IDENT.text);
			AssignmentStatement as = new AssignmentStatement($IDENT.text, e);
			queueStatement(as);
		}
	|	^('=' INIT_FUNCTION IDENT ^(FUNCTION_PARAMETERS p=parameters) ^(fb=FUNCTION_BODY codeStatement*)) {
			//will happen after statements collected!
			FunctionExpression funcExpr = createFunction();
			AssignmentStatement as = new AssignmentStatement($IDENT.text, funcExpr);
			queueStatement(as);
		}
	;
	
codeStatement
	:	initVariableStatement
	|	printStatement
	|	assignmentStatement
	;

initVariableStatement
	:	^(INIT_VARIABLE IDENT e=expression) {
			System.out.println("initializing " + $IDENT.text + " to " + e);
			//funcStatements.clear();
		}
	;

expression returns [ExpressionStatement result]
	:	^('+' op1=expression op2=expression) { $result = new PlusExpression(op1, op2);	}
	|	^('-' op1=expression op2=expression) { $result = new MinusExpression(op1, op2); }
	|	^('*' op1=expression op2=expression) { $result = new MultiplicationExpression(op1, op2); }
	|	^('/' op1=expression op2=expression) { $result = new DivisionExpression(op1, op1); }
	|	^('%' op1=expression op2=expression) { $result = new ModulusExpression(op1, op2); }
	|	^(NEGATION e=expression) { $result = new NegationExpression(e); }
	|	INTEGER { 
			$result = new WrappedPrimitiveExpression(Integer.parseInt($INTEGER.text)); 
		}
	|	STRING_LITERAL {
			$result = new WrappedPrimitiveExpression($STRING_LITERAL.text);
		}
	;	

parameters returns [List<String> result]
	:	IDENT (IDENT)* { $result = null; }
	;