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
	import eve.statements.expressions.bool.*;
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
		
		//Gets rid of "" being put as a single element in the params array.
		//This means we actually have no parameters declared.
		if (params.size() == 1 && params.get(0).equals("")) {
			params = new ArrayList<String>(0);
		}
		
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
	
	//If statement management
	private EveStatement previousStatement;
	
}

parameters returns [List<String> result]
	:	IDENT (IDENT)* { $result = null; }
	;

topdown
	:	functionParametersDown
	|	assignFunctionDown
	|	updateFunctionDown
	|	functionNameDown
	|	createPrototypeDown
	|	ifStatementDown
	|	elseIfStatementDown
	|	elseStatementDown
	|	codeStatement
	;

bottomup
	:	assignFunctionUp
	|	updateFunctionUp
	|	ifStatementUp
	|	elseIfStatementUp
	|	elseStatementUp
	|	createPrototypeUp
	;
	
//Function declarations (not invocations)
assignFunctionDown
	:	^(INIT_FUNCTION IDENT .*) {
			//Create new FunctionExpression.
			FunctionDefExpression expr = new FunctionDefExpression();
			expr.setLine($IDENT.getLine());
			ScopeManager.pushConstructionScope(expr);
			EveLogger.debug("Creating new function expression for " + $IDENT.text);
	}
	;
	
updateFunctionDown
	:	^(UPDATE_FUNCTION IDENT .*) {
			//Create new FunctionExpression.
			FunctionDefExpression expr = new FunctionDefExpression();
			expr.setLine($IDENT.getLine());
			ScopeManager.pushConstructionScope(expr);
			EveLogger.debug("Creating new function update expression for " + $IDENT.text);	
		}
	;
	
functionNameDown
	:	^(FUNCTION_NAME IDENT .*) {
			//we have to be in a function definition!
			FunctionDefExpression expr = (FunctionDefExpression)ScopeManager.getCurrentConstructionScope();
			expr.setName($IDENT.text);
			EveLogger.debug("named function expression " + $IDENT.text);
		}
	;
	
assignFunctionUp
	:	^(INIT_FUNCTION IDENT .*) {
			//Create new Assignment statement.
			//This MUST be a function def, otherwise there's a serious problem.
			FunctionDefExpression expr = (FunctionDefExpression)ScopeManager.popConstructionScope();
			AssignmentStatement as = new InitVariableStatement($IDENT.text, expr);
	
			//we are now back on global (or proto).		
			ScopeManager.getCurrentConstructionScope().addStatement(as);
			EveLogger.debug("Assigning " + $IDENT.text + " function to current scope.");
	}
	;
		
updateFunctionUp
	:	^(UPDATE_FUNCTION IDENT .*) {
			FunctionDefExpression expr = (FunctionDefExpression)ScopeManager.popConstructionScope();
			AssignmentStatement as = new UpdateVariableStatement($IDENT.text, expr);
	
			//we are now back on global (or proto).		
			ScopeManager.getCurrentConstructionScope().addStatement(as);
			EveLogger.debug("Assigning " + $IDENT.text + " function to current scope.");	
		}
	;
	
functionParametersDown
	:	(FUNCTION_PARAMETERS type=. (s=IDENT { pushFunctionParam($s.text); })* ) {	
			//This MUST be a function definition, otherwise we have issues.
			FunctionDefExpression expr = (FunctionDefExpression)ScopeManager.getCurrentConstructionScope();
			expr.setParameters(getFunctionParams());
		}
	;
	
//Prototype creation (not cloning)
createPrototypeDown 
	:	^(INIT_PROTO IDENT .*) {
			//Current construction scope is now in a prototype.
			CreateProtoStatement createProto = new CreateProtoStatement($IDENT.text);
			createProto.setLine($IDENT.getLine());
			ScopeManager.pushConstructionScope(createProto);
			EveLogger.debug("init prototype " + $IDENT.text);
		}
	;
	
createPrototypeUp
	:	^(INIT_PROTO IDENT .*) {
			//ConstructionScope MUST be CreateProtoStatement, or we have issues.
			CreateProtoStatement createProto = (CreateProtoStatement)ScopeManager.popConstructionScope();
			
			//This should be global construction scope.
			ScopeManager.getCurrentConstructionScope().addStatement(createProto);
			EveLogger.debug("creating create proto statement for " + $IDENT.text);
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
			ps.setLine(e.getLine());
			ScopeManager.getCurrentConstructionScope().addStatement(ps);
			previousStatement = ps;
			EveLogger.debug("print statement");
		}
	;
	
returnStatement
	:	^('return' e=expression) {
			ReturnStatement ret = new ReturnStatement(e);
			ret.setLine(e.getLine());
			ScopeManager.getCurrentConstructionScope().addStatement(ret);
			previousStatement = ret;	
		}
	;

ifStatementDown
	:	^(IF_STATEMENT e=expression .*) {
			IfStatement expr = new IfStatement(e);
			expr.setLine($IF_STATEMENT.getLine());
			
			//If this is true, that means we have an if inside an if
			if (ScopeManager.getCurrentConstructionScope() instanceof IfStatement) {
				IfStatement parentIf = (IfStatement)ScopeManager.getCurrentConstructionScope();
				parentIf.setChildIf(expr);
			}
			
			ScopeManager.pushConstructionScope(expr);
			previousStatement = expr;
			EveLogger.debug("Creating if statement for " + e);	
		}
	;
	
ifStatementUp
	:	^(IF_STATEMENT expression .*) {
			//ConstructionScope MUST be IfStatement, or we have issues.
			IfStatement ifStatement = (IfStatement)ScopeManager.popConstructionScope();
			previousStatement = ifStatement;
			ScopeManager.getCurrentConstructionScope().addStatement(ifStatement);
			EveLogger.debug("Finished creating if statement at " + ScopeManager.getCurrentConstructionScope());
		}
	;
	
elseIfStatementDown
	:	^(ELSE_IF e=expression .*) {
			//the last statement must have been an if.
			if (ScopeManager.getLastConstructionScope() instanceof IfStatement && previousStatement instanceof IfStatement) {
				EveLogger.debug("Creating else-if at " + $ELSE_IF.getText());
				IfStatement elseIf = new IfStatement(e);
				IfStatement parentIf = (IfStatement)ScopeManager.getLastConstructionScope();
				parentIf.setChildIf(elseIf);
				ScopeManager.pushConstructionScope(elseIf);
				previousStatement = elseIf;
			}
			else {
				throw new EveError("else if statement must follow an if or an else if");
			}
		}
	;
	
elseIfStatementUp
	:	^(ELSE_IF expression .*) {
			IfStatement ifStatement = (IfStatement)ScopeManager.popConstructionScope();
			previousStatement = ifStatement;
			//ScopeManager.getCurrentConstructionScope().addStatement(ifStatement);
			//current construction scope would not be the if statement, so we do not add here.
			//that is taken care of going down.
			EveLogger.debug("Finished creating else-if statement at " + ScopeManager.getCurrentConstructionScope());	
		}
	;
	
elseStatementDown
	:	^(ELSE .*) {
			//we must be inside of an if statement to append an else if or else.
			System.out.println("current scope: " + ScopeManager.getCurrentConstructionScope().getClass().getName());
			if (ScopeManager.getLastConstructionScope() instanceof IfStatement && previousStatement instanceof IfStatement) {
				EveLogger.debug("Creating else at " + $ELSE.getLine());
				
				//An else is just an else-if (true)
				IfStatement elseStatement = new IfStatement(new WrappedPrimitiveExpression(true));
				IfStatement parentIf = (IfStatement)ScopeManager.getLastConstructionScope();
				parentIf.setChildIf(elseStatement);
				ScopeManager.pushConstructionScope(elseStatement);
				previousStatement = elseStatement;
			}
			else {
				throw new EveError("else statement must follow an if or an else if");
			}
		}
	;
	
elseStatementUp
	:	^(ELSE .*) {
			IfStatement elseStatement = (IfStatement)ScopeManager.popConstructionScope();
			previousStatement = elseStatement;
			//ScopeManager.getCurrentConstructionScope().addStatement(elseStatement);
			//current construction scope is not the if statmeent, so we do not add here.
			//that is taken care of going down.
			EveLogger.debug("Finished creating else statement at " + ScopeManager.getCurrentConstructionScope());	
		}
	;	
	
initVariableStatement
	:	^(INIT_VARIABLE IDENT e=expression) {
			EveLogger.debug("Initialize " + $IDENT.text + " to " + e);
			AssignmentStatement as = new InitVariableStatement($IDENT.text, e);
			as.setLine($IDENT.getLine());
			ScopeManager.getCurrentConstructionScope().addStatement(as);
			previousStatement = as;
		}
	;

updateVariableStatement
	:	^(UPDATE_VARIABLE IDENT e=expression) {
			EveLogger.debug("Update variable " + $IDENT.text + " to " + e);
			AssignmentStatement as = new UpdateVariableStatement($IDENT.text, e);
			as.setLine($IDENT.getLine());
			ScopeManager.getCurrentConstructionScope().addStatement(as);
			previousStatement = as;
		}
	;
	
invokeFunctionStatement
	:	^(INVOKE_FUNCTION_STMT IDENT (e=expression { pushFunctionInvocationParam(e); })*) {
			//args invocation
			List<ExpressionStatement> params = getFunctionInvocationParams();
			EveLogger.debug("invoking function " + $IDENT.text + " as expression with params " + params);
			FunctionInvokeExpression expr = new FunctionInvokeExpression($IDENT.text, params);
			expr.setLine($IDENT.getLine());
			ScopeManager.getCurrentConstructionScope().addStatement(expr);
			previousStatement = expr;
		}
	|	^(INVOKE_FUNCTION_STMT IDENT) {
			//no-args invocation
			FunctionInvokeExpression expr = new FunctionInvokeExpression($IDENT.text);
			expr.setLine($IDENT.getLine());
			ScopeManager.getCurrentConstructionScope().addStatement(expr);
			previousStatement = expr;
		}
	;
	
//Expressions
expression returns [ExpressionStatement result]
	//Operators
	:	^('~' op1=expression op2=expression) {$result = new ConcatExpression(op1, op2); $result.setLine(op1.getLine()); }
	|	^('+' op1=expression op2=expression) { $result = new PlusExpression(op1, op2); $result.setLine(op1.getLine()); }
	|	^('-' op1=expression op2=expression) { $result = new MinusExpression(op1, op2); $result.setLine(op1.getLine()); }
	|	^('*' op1=expression op2=expression) { $result = new MultiplicationExpression(op1, op2); $result.setLine(op1.getLine()); }
	|	^('/' op1=expression op2=expression) { $result = new DivisionExpression(op1, op1); $result.setLine(op1.getLine()); }
	|	^('%' op1=expression op2=expression) { $result = new ModulusExpression(op1, op2); $result.setLine(op1.getLine()); }
	|	^(NEGATION e=expression) { $result = new NegationExpression(e); $result.setLine($NEGATION.getLine()); }
	
	//Boolean comparison.
	|	^('&&' op1=expression op2=expression) { $result = new AndExpression(op1, op2); $result.setLine(op1.getLine()); }
	|	^('||' op1=expression op2=expression) { $result = new OrExpression(op1, op2); $result.setLine(op1.getLine()); }
	|	^('==' op1=expression op2=expression) { $result = new EqualsExpression(op1, op2); $result.setLine(op1.getLine()); }
	|	^('!=' op1=expression op2=expression) { $result = new NotEqualsExpression(op1, op2); $result.setLine(op1.getLine()); }
	|	^('>' op1=expression op2=expression) { $result = new GreaterThanExpression(op1, op2); $result.setLine(op1.getLine()); }
	|	^('<' op1=expression op2=expression) { $result = new LessThanExpression(op1, op2); $result.setLine(op1.getLine()); }
	|	^('>=' op1=expression op2=expression) { $result = new GreaterThanOrEqualToExpression(op1, op2); $result.setLine(op1.getLine()); }
	|	^('<=' op1=expression op2=expression) { $result = new LessThanOrEqualToExpression(op1, op2); $result.setLine(op1.getLine()); }
	
	//Everything else.
	|	^(INVOKE_FUNCTION_EXPR IDENT (e=expression { pushFunctionInvocationParam(e); })*) {
			List<ExpressionStatement> params = getFunctionInvocationParams();
			EveLogger.debug("invoking function " + $IDENT.text + " as expression with params " + params);
			$result = new FunctionInvokeExpression($IDENT.text, params);
			$result.setLine($IDENT.getLine());
		}
	|	^(INVOKE_FUNCTION_EXPR IDENT) {
			$result = new FunctionInvokeExpression($IDENT.text);
			$result.setLine($IDENT.getLine());
		}
	|	^(CLONE_PROTO IDENT) {
			$result = new CloneExpression($IDENT.text);
			$result.setLine($IDENT.getLine());				
		}
	|	IDENT {
			$result = new IdentExpression($IDENT.text);
			$result.setLine($IDENT.getLine());
		}
	|	INTEGER {
			$result = new WrappedPrimitiveExpression(new Integer($INTEGER.text));
			$result.setLine($INTEGER.getLine());
		}
	|	DOUBLE {
			$result = new WrappedPrimitiveExpression(new Double($DOUBLE.text));
			$result.setLine($DOUBLE.getLine());
		}
	|	BOOLEAN {
			$result = new WrappedPrimitiveExpression(new Boolean($BOOLEAN.text));
			$result.setLine($BOOLEAN.getLine());
		}
	|	STRING_LITERAL {
			$result = new WrappedPrimitiveExpression($STRING_LITERAL.text);
			$result.setLine($STRING_LITERAL.getLine());
		}
	|	LIST_LITERAL {
			$result = new WrappedPrimitiveExpression(new ArrayList<EveObject>());
			$result.setLine($LIST_LITERAL.getLine());
		}
	;	