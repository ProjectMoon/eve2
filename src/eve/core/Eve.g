grammar Eve;

options {
	language = Java;
	output = AST;
	ASTLabelType = CommonTree;
}

tokens {
	NEGATION;
	INIT_VARIABLE;
	UPDATE_VARIABLE;
	INIT_FUNCTION;
	UPDATE_FUNCTION;
	FUNCTION_NAME;
	FUNCTION_PARAMETERS;
	FUNCTION_BODY;
	INVOKE_FUNCTION_STMT;
	INVOKE_FUNCTION_EXPR;
	INIT_PROTO;
	CLONE_PROTO;
	IF_STATEMENT;
	ELSE_IF;
	ELSE;
}

@header {
	package eve.core;
}

@lexer::header {
	package eve.core;
	import java.util.List;
	import java.util.ArrayList;
}

@members {
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

//Statements
program
	:	statement*
	;

// Statements
statement
	:	codeStatement
	|	protoStatement
	;
	
codeStatement //Statements that can appear pretty much anywhere.
	:	printStatement
	|	returnStatement
	|	assignmentStatement
	|	initVariableStatement
	|	functionInvocationStatement
	|	ifStatement
	;
	
returnStatement
	:	'return'^ expression ';'!
	;
	
printStatement
	:	'print'^ '('! expression ')'! ';'!
	;
	
assignmentStatement
	:	IDENT '=' expression ';' -> ^(UPDATE_VARIABLE IDENT expression)
	|	prop=IDENT '=' name=IDENT? function ';'? -> ^(UPDATE_FUNCTION $prop ^(FUNCTION_NAME $name?) function)
	;
	
initVariableStatement
	:	'var' IDENT '=' expression ';' -> ^(INIT_VARIABLE IDENT expression)
	|	'def' prop=IDENT '=' name=IDENT? function ';'? -> ^(INIT_FUNCTION $prop ^(FUNCTION_NAME $name?) function)
	;

protoStatement
	: 'proto' IDENT '{' codeStatement* '}' ';'? -> ^(INIT_PROTO IDENT codeStatement*)
	;
	
functionInvocationStatement
	:	IDENT '(' functionInvocationParameters ')' ';' -> ^(INVOKE_FUNCTION_STMT IDENT functionInvocationParameters)
	|	IDENT '(' ')' ';' -> ^(INVOKE_FUNCTION_STMT IDENT)
	;

//Prototype cloning
cloneExpression
	:	'clone' IDENT -> ^(CLONE_PROTO IDENT)
	;

//Function related stuff
functionInvocationParameters
	:	expression (',' expression)* -> (expression)* //apparently this somehow rewrites the entire thing to "p1 p2 p3 ..."
	;
	
function
	:	'(' (IDENT (',' IDENT)*)* ')' '{' codeStatement* '}' -> ^(FUNCTION_PARAMETERS IDENT*) ^(FUNCTION_BODY codeStatement*)
	;
	
parameters
	:	'(' IDENT (',' IDENT)* ')' -> ^(FUNCTION_PARAMETERS IDENT*)
	;
	
functionInvocationExpression
	:	IDENT '(' functionInvocationParameters ')' -> ^(INVOKE_FUNCTION_EXPR IDENT functionInvocationParameters)
	|	IDENT '(' ')' -> ^(INVOKE_FUNCTION_EXPR IDENT)
	;

//If statements
ifStatement
	:	'if' '(' expression ')' '{' codeStatement* '}' -> ^(IF_STATEMENT expression codeStatement*)
	|	'else' 'if' '(' expression ')' '{' codeStatement* '}' -> ^(ELSE_IF expression codeStatement*)
	|	'else' '{' codeStatement* '}' -> ^(ELSE codeStatement*)
	;

//Expressions
term
	:	IDENT
	|	'('! expression ')'!
	|	INTEGER
	|	DOUBLE
	|	BOOLEAN
	|	STRING_LITERAL
	|	functionInvocationExpression
	|	cloneExpression
	;
	
boolNegation
	:	'!'* term
	;
	
unary
	:	('+'! | negation^)* boolNegation
	;
		
negation
	:	'-' -> NEGATION
	;

mult
	:	unary (('*'^ | '/'^ | '%'^) unary)*
	;
	
add
	:	mult (('+'^ | '-'^ | '~'^) mult)*
	;

relation
	:	add (('=='^ | '!='^ | '<'^ | '<='^ | '>='^ | '>'^) add)*
	;
	
expression
	:	relation (('&&'^ | '||'^) relation)*
	;
	
// Tokens
MULTILINE_COMMENT : '/*' .* '*/' {$channel = HIDDEN;} ;

STRING_LITERAL
	:	'"'
		{ StringBuilder b = new StringBuilder(); }
		(	'"' '"'				{ b.appendCodePoint('"');}
		|	c=~('"'|'\r'|'\n')	{ b.appendCodePoint(c);}
		)*
		'"'
		{ setText(b.toString()); }
	;
	
CHAR_LITERAL
	:	'\'' . '\'' {setText(getText().substring(1,2));}
	;

fragment LETTER : ('a'..'z' | 'A'..'Z') ;
fragment DIGIT : '0'..'9';
fragment DOT : '.' ;
INTEGER : DIGIT+ ;
DOUBLE : DIGIT+ '.' DIGIT+ ;
BOOLEAN : 'true' | 'false' ;
IDENT : LETTER (DOT | LETTER | DIGIT)*;

WS : (' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};
COMMENT : '//' .* ('\n'|'\r') {$channel = HIDDEN;};
