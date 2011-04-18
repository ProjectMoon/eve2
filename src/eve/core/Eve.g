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
	|	assignmentStatement
	|	initVariableStatement
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
	: 'proto'^ IDENT '{'! protoBlock* '}'!
	;
	
protoBlock
	:	codeStatement
	;

//Function related stuff
actualParameters
	:	expression (',' expression)*
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

parameter returns [String param]
	:	IDENT { $param = $IDENT.text; }
	;

//Expressions
term
	:	IDENT
	|	'('! expression ')'!
	|	INTEGER
	|	STRING_LITERAL
	|	IDENT '(' actualParameters ')'
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