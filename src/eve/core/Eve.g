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
	FUNCTION_PARAMETERS;
	FUNCTION_BODY;
	INVOKE_FUNCTION_STMT;
	INVOKE_FUNCTION_EXPR;
	INIT_PROTO;
}

@header {
	package eve.core;
}

@lexer::header {
	package eve.core;
	import java.util.List;
	import java.util.ArrayList;
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
	;
	
returnStatement
	:	'return'^ expression ';'!
	;
	
printStatement
	:	'print'^ '('! expression ')'! ';'!
	;
	
assignmentStatement
	:	IDENT '=' expression ';' -> ^(UPDATE_VARIABLE IDENT expression)
	|	initFunction IDENT '='^ function ';'?
	;

initFunction
	:	'def' -> INIT_FUNCTION
	;
	
initVariableStatement
	:	initVariable^ IDENT '='! expression ';'!
	;

initVariable
	:	'var' -> INIT_VARIABLE
	;
	
protoStatement
	: 'proto' IDENT '{' protoBlock '}' -> ^(INIT_PROTO IDENT protoBlock)
	;
	
protoBlock
	:	codeStatement*
	;
	
functionInvocationStatement
	:	IDENT '(' functionInvocationParameters ')' -> ^(INVOKE_FUNCTION_STMT IDENT functionInvocationParameters)
	;

//Function related stuff
functionInvocationParameters
	:	expression (',' expression)* -> (expression)* //apparently this somehow rewrites the entire thing to "p1 p2 p3 ..."
	;
	
function 
	:	parameters
		functionBody
	;
	
functionBody
	:	functionBodyToken^ codeStatement* '}'!
	;

functionBodyToken
	:	'{' -> FUNCTION_BODY
	;
	
parameters
	:	parametersStartToken^ parameter (','! parameter)* ')'!
	;
	
parametersStartToken
	:	'(' -> FUNCTION_PARAMETERS
	;

parameter
	:	IDENT
	;

functionInvocationExpression
	:	IDENT '(' functionInvocationParameters ')' -> ^(INVOKE_FUNCTION_EXPR IDENT functionInvocationParameters)
	;

//Expressions
term
	:	IDENT
	|	'('! expression ')'!
	|	INTEGER
	|	STRING_LITERAL
	|	functionInvocationExpression
	;
	
boolNegation
	:	'not'* term
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
	:	mult (('+'^ | '-'^) mult)*
	;

relation
	:	add (('='^ | '/='^ | '<'^ | '<='^ | '>='^ | '>'^) add)*
	;
	
expression
	:	relation (('and' | 'or') relation)*
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
INTEGER : DIGIT+ ;
IDENT : LETTER (LETTER | DIGIT)*;
WS : (' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};
COMMENT : '//' .* ('\n'|'\r') {$channel = HIDDEN;};