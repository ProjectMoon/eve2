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
	private List<String> getFunctionParams() {
		List<String> params = Arrays.asList(currentParameters.toString().trim().split(" "));
		currentParameters = new StringBuilder();
		return params;
	}
	
	private void pushFunctionParam(String param) {
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
	|	createPrototypeDown
	|	codeStatement
	;

bottomup
	:	exitFunction
	|	endParameters
	|	assignFunctionUp
	|	createPrototypeUp
	;
	
//Function declarations (not invocations)
assignFunctionDown
	:	^('=' INIT_FUNCTION IDENT .*) {
		//Create new FunctionExpression.
		ScopeManager.pushConstructionScope(new FunctionDefExpression());
		//Push on to current function stack.
		//implement later.
		System.out.println("Creating new function expression for " + $IDENT.text);
	}
	;
	
assignFunctionUp
	:	^('=' INIT_FUNCTION IDENT .*) {
		//Create new Assignment statement.
		//This MUST be a function def, otherwise there's a serious problem.
		FunctionDefExpression expr = (FunctionDefExpression)ScopeManager.popConstructionScope();
		AssignmentStatement as = new InitVariableStatement($IDENT.text, expr);

		//we are now back on global (or proto).		
		ScopeManager.getCurrentConstructionScope().addStatement(as);
		System.out.println("Assigning " + $IDENT.text + " function to current scope.");
	}
	;
	
enterFunction
	:	^(FUNCTION_BODY .*) {
			//Peek at current function and set scope to it.
			//Can ignore this, because we scope push at assignFunctionDown 
		}
	;
	
exitFunction
	:	(FUNCTION_BODY .*) {
			//pop scope stack.
			//Can ignore this because we pop scope at assignFunctionUp
		}
	;
	
beginParameters
	:	(FUNCTION_PARAMETERS type=. (s=IDENT { pushFunctionParam($s.text); })* ) {	
			//This MUST be a function definition, otherwise we have issues.
			FunctionDefExpression expr = (FunctionDefExpression)ScopeManager.getCurrentConstructionScope();
			expr.setParameters(getFunctionParams());
		}
	;
	
endParameters
	:	(FUNCTION_PARAMETERS) {
			//Ignore, i think?
		}
	;

//Prototype creation (not cloning)
createPrototypeDown 
	:	^(INIT_PROTO IDENT .*) {
			//Current construction scope is now in a prototype.
			ScopeManager.pushConstructionScope(new CreateProtoStatement($IDENT.text));
			System.out.println("init prototype " + $IDENT.text);
		}
	;
	
createPrototypeUp
	:	^(INIT_PROTO IDENT .*) {
			//ConstructionScope MUST be CreateProtoStatement, or we have issues.
			CreateProtoStatement createProto = (CreateProtoStatement)ScopeManager.popConstructionScope();
			
			//This should be global construction scope.
			ScopeManager.getCurrentConstructionScope().addStatement(createProto);
			System.out.println("creating create proto statement for " + $IDENT.text);
		}
	;

//Code Statements: can occur anywhere (global, in proto, or in function)
codeStatement
	:	printStatement
	|	returnStatement
	|	initVariableStatement
	|	updateVariableStatement
	|	invokeFunctionStatement
	;
	
printStatement
	:	^('print' e=expression) {
			PrintStatement ps = new PrintStatement(e);
			ScopeManager.getCurrentConstructionScope().addStatement(ps);
			System.out.println("print statement");
		}
	;
	
returnStatement
	:	^('return' e=expression) {
			ReturnStatement ret = new ReturnStatement(e);
			ScopeManager.getCurrentConstructionScope().addStatement(ret);	
		}
	;
	
initVariableStatement
	:	^(INIT_VARIABLE IDENT e=expression) {
			System.out.println("Initialize " + $IDENT.text + " to " + e);
			AssignmentStatement as = new InitVariableStatement($IDENT.text, e);
			ScopeManager.getCurrentConstructionScope().addStatement(as);
		}
	;

updateVariableStatement
	:	^(UPDATE_VARIABLE IDENT e=expression) {
			System.out.println("Update variable " + $IDENT.text + " to " + e);
			AssignmentStatement as = new UpdateVariableStatement($IDENT.text, e);
			ScopeManager.getCurrentConstructionScope().addStatement(as);
		}
	;
	
invokeFunctionStatement
	:	^(INVOKE_FUNCTION_STMT IDENT (e=expression { pushFunctionInvocationParam(e); })*) {
			List<ExpressionStatement> params = getFunctionInvocationParams();
			System.out.println("invoking function " + $IDENT.text + " as expression with params " + params);
			FunctionInvokeExpression expr = new FunctionInvokeExpression($IDENT.text, params);
			ScopeManager.getCurrentConstructionScope().addStatement(expr);
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