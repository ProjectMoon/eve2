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
	import eve.statements.loop.*;
	import eve.scope.*;
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
		List<String> params = new ArrayList<String>(Arrays.asList(currentParameters.toString().trim().split(" ")));
		
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
	
	//Error handling
    private List<String> errors = new ArrayList<String>();
    public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, tokenNames);
        errors.add(hdr + " " + msg);
    }
    
    public List<String> getErrors() {
        return errors;
    }
    
    public boolean hasErrors() {
    	return errors.size() > 0;
    }
	
}

parameters returns [List<String> result]
	:	IDENT (IDENT)* { $result = null; }
	;

topdown
	:	namespaceStatement
	|	withStatementDown
	|	assignFunctionDown
	|	functionNameDown
	|	createPrototypeDown
	|	ifStatementDown
	|	elseIfStatementDown
	|	elseStatementDown
	|	codeStatement
	|	nsSwitchDown
	|	foreachLoopDown
	|	whileLoopDown
	|	functionBodyDown
	;

bottomup
	:	withStatementUp
	|	assignFunctionUp
	|	functionParametersUp
	|	ifStatementUp
	|	elseIfStatementUp
	|	elseStatementUp
	|	createPrototypeUp
	|	nsSwitchUp
	|	foreachLoopUp
	|	whileLoopUp
	|	functionBodyUp
	;

//with statement
withStatementDown
	:	^(WITH identifiers+=IDENT+ .*) {
			List<String> idents = new ArrayList<String>($identifiers.size());
			
			for (Object ident : $identifiers) {
				idents.add(ident.toString());
			}
			
			WithStatement with = new WithStatement(idents);
			with.setLine($WITH.getLine());
			ScopeManager.pushConstructionScope(with);
			EveLogger.debug("pushing with statement " + with);
		}
	;
	
withStatementUp
	:	^(WITH IDENT+ .*) {
			WithStatement with = (WithStatement)ScopeManager.popConstructionScope();
			ScopeManager.getCurrentConstructionScope().addStatement(with);
			EveLogger.debug("popping with statement " + with);
		}
	;

//namespace declaration and switching
namespaceStatement
	:	^(NAMESPACE IDENT) {
			ScopeManager.getScript().setNamespace($IDENT.text);
			EveLogger.debug("set namespace for script to " + $IDENT.text);
		}
	;
	
nsSwitchDown
	:	^(NS_SWITCH_BLOCK IDENT .*) {
			NamespaceSwitchBlock expr = new NamespaceSwitchBlock($IDENT.text);
			expr.setLine($IDENT.getLine());
			ScopeManager.pushConstructionScope(expr);
			EveLogger.debug("Switch to namespace " + $IDENT.text);
		}
	;
	
nsSwitchUp
	:	^(NS_SWITCH_BLOCK IDENT .*) {
			NamespaceSwitchBlock expr = (NamespaceSwitchBlock)ScopeManager.popConstructionScope();
			ScopeManager.getCurrentConstructionScope().addStatement(expr);
		}
	;
//Function declarations (not invocations)
assignFunctionDown
	:	^(INIT_FUNCTION IDENT .*) {
			//Create new FunctionExpression.
			FunctionDefExpression expr = new FunctionDefExpression();
			expr.setLine($IDENT.getLine());
			//ScopeManager.pushConstructionScope(expr);
			currentFuncExpr = expr;
			EveLogger.debug("Creating new function expression for " + $IDENT.text);
	}
	;
	
functionNameDown
	:	^(FUNCTION_NAME IDENT .*) {
			//we have to be in a function definition!
			//FunctionDefExpression expr = (FunctionDefExpression)ScopeManager.getCurrentConstructionScope();
			currentFuncExpr.setName($IDENT.text);
			EveLogger.debug("named function expression " + $IDENT.text);
		}
	;
	
assignFunctionUp
	:	^(INIT_FUNCTION IDENT .*) {
			//Create new Assignment statement.
			//This MUST be a function def, otherwise there's a serious problem.
			//FunctionDefExpression expr = (FunctionDefExpression)ScopeManager.popConstructionScope();
			AssignmentStatement as = new InitVariableStatement($IDENT.text, currentFuncExpr);
	
			//we are now back on global (or proto).		
			ScopeManager.getCurrentConstructionScope().addStatement(as);
			EveLogger.debug("Assigning " + $IDENT.text + " function to current scope.");
		}
	;

functionBodyDown
	:	^(FUNCTION_BODY .*) {
			EveLogger.debug("pushing " + currentFuncExpr);
			ScopeManager.pushConstructionScope(currentFuncExpr);
		}
	;
	
functionBodyUp
	:	^(FUNCTION_BODY .*) {
			ConstructionScope cs = ScopeManager.getCurrentConstructionScope();
			if (cs instanceof FunctionDefExpression) {
				EveLogger.debug("popping current func expr " + cs);
				currentFuncExpr = (FunctionDefExpression)cs;
				ScopeManager.popConstructionScope();
			}
		}
	;		
		
functionParametersUp
	:	^(FUNCTION_PARAMETERS (s=IDENT { pushFunctionParam($s.text); })* varargs='...'?) {
			//This MUST be a function definition, otherwise we have issues.
			EveLogger.debug("Function parameters for current function");
			//FunctionDefExpression expr = (FunctionDefExpression)ScopeManager.getCurrentConstructionScope();
			List<String> params = getFunctionParams();
			currentFuncExpr.setParameters(params);
			
			if (varargs != null) {
				currentFuncExpr.setVarargs(true);
				currentFuncExpr.setVarargsIndex(params.size() - 1);
			}
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
	|	expressionStatement
	;
	
expressionStatement
	:	^(EXPR_STATEMENT e=expression) {
			e.setLine($EXPR_STATEMENT.getLine());
			ScopeManager.getCurrentConstructionScope().addStatement(e);
			previousStatement = e;
		}
	;

printStatement
	:	^(PRINT_EXPR e=expression) {
			PrintStatement ps = new PrintStatement(e);
			ps.setLine(e.getLine());
			ScopeManager.getCurrentConstructionScope().addStatement(ps);
			previousStatement = ps;
			EveLogger.debug("print statement");
		}
	|	^(PRINTLN_EXPR e=expression) {
			PrintStatement ps = new PrintStatement(e);
			ps.setPrintNewline(true);
			ps.setLine(e.getLine());
			ScopeManager.getCurrentConstructionScope().addStatement(ps);
			previousStatement = ps;
			EveLogger.debug("println statement");			
		}
	|	^(PRINTLN_EMPTY .*) {
			PrintStatement ps = new PrintStatement(null);
			ps.setPrintNewline(true);
			ps.setLine($PRINTLN_EMPTY.getLine());
			ScopeManager.getCurrentConstructionScope().addStatement(ps);
			previousStatement = ps;
			EveLogger.debug("empty println statement");
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
			//current construction scope would not be the if statement, so we do not add here.
			//that is taken care of going down.
			EveLogger.debug("Finished creating else-if statement at " + ScopeManager.getCurrentConstructionScope());	
		}
	;
	
elseStatementDown
	:	^(ELSE .*) {
			//we must be inside of an if statement to append an else if or else.
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
	:	^(UPDATE_VARIABLE e1=expression e2=expression) {
			EveLogger.debug("Update variable " + e1 + " to " + e2 + " in " + ScopeManager.getCurrentConstructionScope());
			UpdateVariableStatement uv = new UpdateVariableStatement(e1, e2);
			uv.setLine($UPDATE_VARIABLE.getLine());
			ScopeManager.getCurrentConstructionScope().addStatement(uv);
			previousStatement = uv;
		}
	|	^(PUSH_VARIABLE from=expression to=expression) {
			EveLogger.debug("push variable statement " + from + " to " + to);
			PushExpression push = new PushExpression(from, to);
			push.setLine($PUSH_VARIABLE.getLine());
			ScopeManager.getCurrentConstructionScope().addStatement(push);
			previousStatement = push;
		}
	;
	
//Loops
foreachLoopDown
	:	^(FOREACH variable=IDENT of=expression .*) {
			ForEachLoop loop = new ForEachLoop($variable.text, of);
			loop.setLine($variable.getLine());
			ScopeManager.pushConstructionScope(loop);
			previousStatement = loop;
			EveLogger.debug("creating for (" + $variable.text + " : " + of + ")");
		}
	;
	
foreachLoopUp
	:	^(FOREACH variable=IDENT of=expression .*) {
			ForEachLoop loop = (ForEachLoop)ScopeManager.popConstructionScope();
			ScopeManager.getCurrentConstructionScope().addStatement(loop);
			EveLogger.debug("completed for (" + $variable.text + " : " + of + ")");
		}
	;
	
whileLoopDown
	:	^(WHILE e=expression .*) {
			WhileLoop loop = new WhileLoop(e);
			loop.setLine($WHILE.getLine());
			ScopeManager.pushConstructionScope(loop);
			previousStatement = loop;
			EveLogger.debug("while loop (" + e + ")");
		}
	;
	
whileLoopUp
	:	^(WHILE e=expression .*) {
			WhileLoop loop = (WhileLoop)ScopeManager.popConstructionScope();
			ScopeManager.getCurrentConstructionScope().addStatement(loop);
			EveLogger.debug("completed while (" + e +")");
		}
	;
	
//Expressions
expression returns [ExpressionStatement result]
	//Array access and properties.
	:	^(PROPERTY e=expression prop=IDENT) {
			$result = new PropertyResolution(e, prop.getText());
			$result.setLine($PROPERTY.getLine());
			previousStatement = $result;
		}
	|	^(POINTER e=expression prop=IDENT) {
			$result = new PointerResolution(e, prop.getText());
			$result.setLine($POINTER.getLine());
			previousStatement = $result;
		}
	|	^(ARRAY_IDENT e=expression access=expression) {
			$result = new IndexedAccess(e, access);
			$result.setLine($ARRAY_IDENT.getLine());
			previousStatement = $result;
		}
	
	//Operators
	|	^('~' op1=expression op2=expression) { $result = new ConcatExpression(op1, op2); $result.setLine(op1.getLine()); }
	|	^('+' op1=expression op2=expression) { $result = new PlusExpression(op1, op2); $result.setLine(op1.getLine()); }
	|	^('-' op1=expression op2=expression) { $result = new MinusExpression(op1, op2); $result.setLine(op1.getLine()); }
	|	^('*' op1=expression op2=expression) { $result = new MultiplicationExpression(op1, op2); $result.setLine(op1.getLine()); }
	|	^('/' op1=expression op2=expression) { $result = new DivisionExpression(op1, op1); $result.setLine(op1.getLine()); }
	|	^('%' op1=expression op2=expression) { $result = new ModulusExpression(op1, op2); $result.setLine(op1.getLine()); }
	|	^('to' op1=expression op2=expression) { $result = new RangeExpression(op1, op2); $result.setLine(op1.getLine()); }
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
	|	^('in' op1=expression op2=expression) { $result = new InExpression(op1, op2); $result.setLine(op1.getLine()); }
	
	//Everything else.
	|	^(PROP_COLLECTION e=expression (p=expression { pushFunctionInvocationParam(p); })*) {
			$result = new PropertyCollectionExpression(e, getFunctionInvocationParams());
			$result.setLine($PROP_COLLECTION.getLine());
			EveLogger.debug("obj collection " + e);
		}
	|	^(PROP_COLLECTION_ALL e=expression) {
			$result = new PropertyCollectionExpression(e);
			$result.setLine($PROP_COLLECTION_ALL.getLine());
			EveLogger.debug("obj collection " + e + " with all props");
		}
	|	^(NS_SWITCH_EXPR IDENT e=expression) {
			$result = new NamespacedExpression($IDENT.text, e);
			$result.setLine($IDENT.getLine());
		}
	|	^(INIT_FUNCTION .*) {
			FunctionDefExpression funcExpr = new FunctionDefExpression();
			funcExpr.setLine($INIT_FUNCTION.getLine());
			//ScopeManager.pushConstructionScope(funcExpr);
			currentFuncExpr = funcExpr;
			EveLogger.debug("pushing func expr " + funcExpr);
			$result = funcExpr;	
		}
	|	^(INVOKE_FUNCTION_EXPR i=expression (e=expression { pushFunctionInvocationParam(e); })*) {
			List<ExpressionStatement> params = getFunctionInvocationParams();
			EveLogger.debug("invoking function " + i + " as expression with params " + params);
			$result = new FunctionInvokeExpression(i, params);
			$result.setLine($INVOKE_FUNCTION_EXPR.getLine());
		}
	|	^(INVOKE_FUNCTION_EXPR i=expression) {
			System.out.println("invoke function expr " + i);
			$result = new FunctionInvokeExpression(i);
			$result.setLine($INVOKE_FUNCTION_EXPR.getLine());
		}
	|	^(CLONE e=expression) {
			$result = new CloneExpression(e);
			$result.setLine($CLONE.getLine());				
		}
	|	IDENT {
			$result = new IdentExpression($IDENT.text);
			$result.setLine($IDENT.getLine());
		}
	|	^(DEREF IDENT) {
			IdentExpression deref = new IdentExpression($IDENT.text);
			deref.setUsingMutatorAccessor(false);
			$result = deref;
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
	|	DICT_LITERAL {
			$result = new WrappedPrimitiveExpression(new HashMap<String, EveObject>());
			$result.setLine($DICT_LITERAL.getLine());
		}
	;	