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
	INIT_PROTO;
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
	|	protoStatement
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
	:	'return'^ expression ';'!
	;
	
printStatement
	:	'print' '(' expression ')' ';' -> ^(PRINT_EXPR expression)
	|	'println' '(' expression ')' ';' -> ^(PRINTLN_EXPR expression)
	|	'println' '(' ')' ';' -> ^(PRINTLN_EMPTY)
	;
		
initVariableStatement
	:	'var' IDENT '=' expression ';' -> ^(INIT_VARIABLE IDENT expression)
	|	'def' prop=IDENT '=' name=IDENT? function -> ^(INIT_FUNCTION $prop ^(FUNCTION_NAME $name?) function)
	;

protoStatement
	: 'proto' IDENT '{' codeStatement* '}' -> ^(INIT_PROTO IDENT codeStatement*)
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
atom
	:	IDENT
	|	'*' IDENT -> ^(DEREF IDENT)
	|	'('! expression ')'!
 	|	INTEGER
 	|	DOUBLE
	|	BOOLEAN
	|	STRING_LITERAL
	|	LIST_LITERAL
	|	DICT_LITERAL
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
	|	'{'	p+=IDENT (',' p+=IDENT)* '}' 'of' mult -> ^(PROP_COLLECTION mult $p+)
	|	'all' 'of' mult -> ^(PROP_COLLECTION_ALL mult)
	;

relation
	:	add ((assignment^ | '=='^ | '!='^ | '<'^ | '<='^ | '>='^ | '>'^) add)*
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
fragment SCOPE_OP : ':' ':' ;
INTEGER : DIGIT+ ;
DOUBLE : DIGIT+ '.' DIGIT+ ;
BOOLEAN : 'true' | 'false' ;
IDENT : LETTER ( LETTER | DIGIT)*;

WS : (' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN; };
COMMENT : '//' .* ('\n'|'\r') {$channel = HIDDEN; };
