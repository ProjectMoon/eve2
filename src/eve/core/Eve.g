grammar Eve;

options {
	language = Java;
	output = AST;
	ASTLabelType = CommonTree;
}

tokens {
	NAMESPACE;
	NS_SWITCH_BLOCK;
	NS_SWITCH_EXPR;
	NEGATION;
	INIT_VARIABLE;
	UPDATE_VARIABLE;
	PUSH_VARIABLE;
	INIT_FUNCTION;
	FUNCTION_NAME;
	FUNCTION_PARAMETERS;
	FUNCTION_BODY;
	INVOKE_FUNCTION_STMT;
	INVOKE_FUNCTION_EXPR;
	CLONE;
	IF_STATEMENT;
	ELSE_IF;
	ELSE;
	FOREACH;
	WHILE;
	LOOP_BODY;
	PRINT_EXPR;
	PRINTLN_EXPR;
	PRINTLN_EMPTY;
	ARRAY_ACCESS;
	ARRAY_IDENT;
	PROPERTY;
	EXPR_STATEMENT;
	PROP_COLLECTION;
	PROP_COLLECTION_ALL;
	POINTER;
	DEREF;
	WITH;
	WITH_BODY;
	DELEGATE;
	FREEZE;
	SEAL;
	DELETE;
	JSON;
	JSON_NAME;
	JSON_ENTRY;
}

@header {
	package eve.core;
}

@lexer::header {
	package eve.core;
	import java.util.List;
	import java.util.ArrayList;
}

@lexer::members {
    public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, tokenNames);
        throw new EveError(hdr + " " + msg);
    }
        
    public void reportError(RecognitionException e) {
    	String hdr = getErrorHeader(e);
    	throw new EveError(hdr + " " + e.toString());
    }
}

@parser::members {
    private List<String> errors = new ArrayList<String>();
    public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, tokenNames);
        errors.add(hdr + " " + msg);
    }
    
    public void recover(RecognitionException e) {
    	errors.add(e.getMessage());
    }
    
    public List<String> getErrors() {
        return errors;
    }
    
    public boolean hasErrors() {
    	return errors.size() > 0;
    }
}

//Top level
program
	:	namespace? statement*
	;
	
namespace
	:	'namespace' IDENT ';' -> ^(NAMESPACE IDENT)
	;

// Statements
statement
	:	codeStatement
	;
	
codeStatement //Statements that can appear pretty much anywhere.
	:	printStatement
	|	returnStatement
	|	initVariableStatement
	|	ifStatement
	|	foreachLoop
	|	whileLoop
	|	expressionStatement
	|	withStatement
	;
	
withStatement
	:	'with' '(' idents+=IDENT (',' idents+=IDENT)* ')' '{' codeStatement* '}'
		-> ^(WITH $idents+ ^(WITH_BODY codeStatement*))
	;
		
expressionStatement
	:	';'!
	|	expression ';' -> ^(EXPR_STATEMENT expression)
	;
returnStatement
	:	'return'^ expression? ';'!
	;
	
printStatement
	:	'print' '(' expression ')' ';' -> ^(PRINT_EXPR expression)
	|	'println' '(' expression ')' ';' -> ^(PRINTLN_EXPR expression)
	|	'println' '(' ')' ';' -> ^(PRINTLN_EMPTY)
	;
		
initVariableStatement
	:	'var' IDENT '=' expression ';' -> ^(INIT_VARIABLE IDENT expression)
	|	'def' prop=IDENT '=' name=IDENT? function -> ^(INIT_FUNCTION $prop ^(FUNCTION_NAME $name?) function)
	|	'delegate' prop=IDENT '=' name=IDENT? function -> ^(DELEGATE $prop ^(FUNCTION_NAME $name?) function)
	;

//Loops
foreachLoop
	:	'for' '(' i1=IDENT ':' e=expression ')' '{' codeStatement* '}' -> ^(FOREACH $i1 $e ^(LOOP_BODY codeStatement*))
	;

whileLoop
	:	'while' '(' expression ')' '{' codeStatement* '}' -> ^(WHILE expression ^(LOOP_BODY codeStatement*))
	;

//Function related stuff
functionInvocationParameters
	:	expression (',' expression)* -> (expression)* //apparently this somehow rewrites the entire thing to "p1 p2 p3 ..."
	;
	
function
	:	'(' (IDENT (',' IDENT)*)* '...'? ')' '{' codeStatement* '}' -> ^(FUNCTION_PARAMETERS IDENT* '...'?) ^(FUNCTION_BODY codeStatement*)
	;
	
//If statements
ifStatement
	:	'if' '(' expression ')' '{' codeStatement* '}' -> ^(IF_STATEMENT expression codeStatement*)
	|	'else' 'if' '(' expression ')' '{' codeStatement* '}' -> ^(ELSE_IF expression codeStatement*)
	|	'else' '{' codeStatement* '}' -> ^(ELSE codeStatement*)
	;

//Expressions
json
	:	name=IDENT? '{' jsonEntry (',' jsonEntry)* '}' -> ^(JSON ^(JSON_NAME $name?) jsonEntry*)	
	;
	
jsonEntry
	:	IDENT ':' expression -> ^(JSON_ENTRY IDENT expression)
	;
	
atom
	:	IDENT
	|	'*' IDENT -> ^(DEREF IDENT)
	|	'('! expression ')'!
	|	json
 	|	INTEGER
 	|	DOUBLE
	|	BOOLEAN
	|	STRING_LITERAL
	|	LIST_LITERAL
	|	DICT_LITERAL
	|	NULL
	|	name=IDENT? function -> ^(INIT_FUNCTION ^(FUNCTION_NAME $name?) function)
	|	ns=IDENT '::' i=IDENT -> ^(NS_SWITCH_EXPR $ns ^($i))
	;

term
	:	(atom -> atom) ( suffix[$term.tree] -> suffix )*
	;
	
modifiers 
	:	expression (','! expression)*
	;

suffix [CommonTree t]
	:	( x='(' modifiers? ')' -> ^(INVOKE_FUNCTION_EXPR {$t} modifiers? ))
	|	( x='[' modifiers  ']' -> ^(ARRAY_IDENT {$t} modifiers) )
	|	( x='.' (p=IDENT) -> ^(PROPERTY {$t} $p) )
	|	( '-' '>' (p=IDENT) -> ^(POINTER {t} $p) )
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
	:	mult (('+'^ | '-'^ | '~'^ | 'to'^) mult)*
	//object collections here, because assignment takes precedence!
	|	'{'	expression (',' expression)* '}' 'of' mult -> ^(PROP_COLLECTION mult expression*)
	|	'all' 'of' mult -> ^(PROP_COLLECTION_ALL mult)
	;

relation
	:	add ((assignment^ | '=='^ | '!='^ | '<'^ | '<='^ | '>='^ | '>'^ | 'in'^) add)*
	;
	
assignment
	:	'=' -> UPDATE_VARIABLE
	|	'=>' -> PUSH_VARIABLE
	;
	
andOr
	:	relation (('&&'^ | '||'^) relation)*
	;
	
expression
	:	'clone' andOr -> ^(CLONE andOr)
	|	'freeze' andOr -> ^(FREEZE andOr)
	|	'seal'	andOr -> ^(SEAL andOr)
	|	'delete' andOr -> ^(DELETE andOr)
	|	andOr
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
	
LIST_LITERAL
	:	'[' ']' ;
	
DICT_LITERAL
	:	'{' '}' ;

fragment LETTER : ('a'..'z' | 'A'..'Z') ;
fragment DIGIT : '0'..'9';
fragment UNDERSCORE : '_' ;
INTEGER : DIGIT+ ;
DOUBLE : DIGIT+ '.' DIGIT+ ;
BOOLEAN : 'true' | 'false' ;
NULL : 'null' ;
IDENT : LETTER ( LETTER | UNDERSCORE | DIGIT)*;

WS : (' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN; };
COMMENT : '//' .* ('\n'|'\r') {$channel = HIDDEN; };
