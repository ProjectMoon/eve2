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
	import java.util.List;
	import java.util.ArrayList;
}

@members {
	//Re-usable list to keep track of statements in a function.
	//Cleared after every function defintion.
	private List<EveStatement> funcStatements = new ArrayList<EveStatement>();
}

interpret
	: statement* EOF;

statement
	:	codeStatement
	;

printStatement
	:	^('print' e=expression) {
			PrintStatement ps = new PrintStatement(e);
			ScopeManager.getCurrentScope().addCode(ps);
		}
	;
	
assignmentStatement
	:	^('=' IDENT e=expression) { 
			System.out.println("assigning " + e + " to " + $IDENT.text);
			AssignmentStatement as = new AssignmentStatement($IDENT.text, e);
			ScopeManager.getCurrentScope().addCode(as);
		}
	|	^('=' INIT_FUNCTION IDENT ^(FUNCTION_PARAMETERS p=parameters) ^(fb=FUNCTION_BODY codeStatement*)) {
			//will happen after statements collected!
			FunctionExpression funcExpr = new FunctionExpression(funcStatements);
			AssignmentStatement as = new AssignmentStatement($IDENT.text, funcExpr);
			ScopeManager.getCurrentScope().addCode(as);
			System.out.println("initializing function " + $IDENT.text + " with " + funcStatements);
			
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