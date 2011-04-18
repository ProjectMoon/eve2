tree grammar ASTParser;

options {
  language = Java;
  tokenVocab = Eve;
  ASTLabelType = CommonTree;
  filter = true;
  backtrack = true;
}

@header {
	package eve.core;
	import eve.statements.*;
	import eve.statements.assignment.*;
	import eve.statements.expressions.*;
	import eve.scope.ScopeManager;
	import java.util.Queue;
	import java.util.Set;
	import java.util.LinkedList;
	import java.util.ArrayList;
	import java.util.Map;
	import java.util.HashMap;
	import java.util.Map.Entry;
	import java.util.Arrays;
}

@members {
	//Parameter management.	
	private StringBuilder currentParameters = new StringBuilder();
	private List<String> getParams() {
		List<String> params = Arrays.asList(currentParameters.toString().trim().split(" "));
		currentParameters = new StringBuilder();
		return params;
	}
	
	private void pushParam(String param) {
		currentParameters.append(param).append(" ");
	}
	
	//Function management
	FunctionDefExpression currentFuncExpr = null;
	boolean inFunction = false;
	
	private List<ExpressionStatement> actualParamsList = new ArrayList<ExpressionStatement>();
	private void pushFunctionInvocationParam(ExpressionStatement param) {
		actualParamsList.add(param);
	}
	
	private List<ExpressionStatement> getFunctionInvocationParams() {
		List<ExpressionStatement> p = actualParamsList;
		actualParamsList = new ArrayList<ExpressionStatement>(); //clear for reuse
		return p;
	}
	
}

parameters returns [List<String> result]
	:	IDENT (IDENT)* { $result = null; }
	;

topdown
	:	enterFunction
	|	beginParameters
	|	assignFunctionDown
	|	codeStatement
	;

bottomup
	:	exitFunction
	|	endParameters
	|	assignFunctionUp
	;
	
//Function declarations (not invocations)
assignFunctionDown
	:	^('=' INIT_FUNCTION IDENT .*) {
		//Create new FunctionExpression.
		currentFuncExpr = new FunctionDefExpression();
		//Push on to current function stack.
		//implement later.
		System.out.println("Creating new function expression for " + $IDENT.text);
	}
	;
	
assignFunctionUp
	:	^('=' INIT_FUNCTION IDENT .*) {
		//Create new Assignment statement.
		AssignmentStatement as = new InitVariableStatement($IDENT.text, currentFuncExpr);
		currentFuncExpr = null; //clear for reuse.
		//pop current function expression and use for assignment.
		ExecutionTree.addStatement(as);
		System.out.println("Assigning " + $IDENT.text + " function to current scope.");
	}
	;
	
enterFunction
	:	^(FUNCTION_BODY .*) {
			//Peek at current function and set scope to it.
			System.out.println("Setting scope to current function.");
			inFunction = true;
		}
	;
	
exitFunction
	:	(FUNCTION_BODY .*) {
			//pop scope stack.
			System.out.println("Popping function scope.");
			
			//we know we are in a function (that we will shortly be leaving)
			inFunction = false;
		}
	;
	
beginParameters
	:	(FUNCTION_PARAMETERS type=. (s=IDENT { pushParam($s.text); })* ) {
			System.out.println("Adding parameters " + getParams() + " to current function def.");
			//Gather parameters
			//Add to current function def.
		}
	;
	
endParameters
	:	(FUNCTION_PARAMETERS) {
			//Ignore, i think?
		}
	;

//Code Statements: can occur anywhere (global, in proto, or in function)
codeStatement
	:	printStatement
	|	initVariableStatement
	|	updateVariableStatement
	|	invokeFunctionStatement
	;
	
printStatement
	:	^('print' e=expression) {
			PrintStatement ps = new PrintStatement(e);
			if (inFunction) {
				currentFuncExpr.addStatement(ps);
			}
			else {
				ExecutionTree.addStatement(ps);
			}
			System.out.println("print statement");
		}
	;
	
initVariableStatement
	:	^(INIT_VARIABLE IDENT e=expression) {
			System.out.println("Initialize " + $IDENT.text + " to " + e);
			AssignmentStatement as = new InitVariableStatement($IDENT.text, e);
			if (inFunction) {
				currentFuncExpr.addStatement(as);
			}
			else {
				ExecutionTree.addStatement(as);
			}
		}
	;

updateVariableStatement
	:	^(UPDATE_VARIABLE IDENT e=expression) {
			System.out.println("Update variable " + $IDENT.text + " to " + e);
			AssignmentStatement as = new UpdateVariableStatement($IDENT.text, e);
			if (inFunction) {
				currentFuncExpr.addStatement(as);
			}
			else {
				ExecutionTree.addStatement(as);
			}
		}
	;
	
invokeFunctionStatement
	:	^(INVOKE_FUNCTION_STMT IDENT (e=expression { pushFunctionInvocationParam(e); })*) {
			//System.out.println("invoke function " + $IDENT.text);
			
			//$result = new FunctionInvokeExpression($IDENT.text, getFunctionInvocationParams());
		}
	;
	
//Expressions
expression returns [ExpressionStatement result]
	:	^('+' op1=expression op2=expression) { $result = new PlusExpression(op1, op2);	}
	|	^('-' op1=expression op2=expression) { $result = new MinusExpression(op1, op2); }
	|	^('*' op1=expression op2=expression) { $result = new MultiplicationExpression(op1, op2); }
	|	^('/' op1=expression op2=expression) { $result = new DivisionExpression(op1, op1); }
	|	^('%' op1=expression op2=expression) { $result = new ModulusExpression(op1, op2); }
	|	^(NEGATION e=expression) { $result = new NegationExpression(e); }
	|	^(INVOKE_FUNCTION_EXPR IDENT (e=expression { pushFunctionInvocationParam(e); })*) {
			List<ExpressionStatement> params = getFunctionInvocationParams();
			System.out.println("invoking function " + $IDENT.text + " as expression with params " + params);
			$result = new FunctionInvokeExpression($IDENT.text, params);
		}
	|	IDENT {
			$result = new IdentExpression($IDENT.text);
		}
	|	INTEGER { 
			$result = new WrappedPrimitiveExpression(Integer.parseInt($INTEGER.text)); 
		}
	|	STRING_LITERAL {
			$result = new WrappedPrimitiveExpression($STRING_LITERAL.text);
		}
	;	