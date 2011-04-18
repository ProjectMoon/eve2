// $ANTLR 3.3 Nov 30, 2010 12:50:56 /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g 2011-04-17 23:49:50

	package eve.core;
	import eve.statements.*;
	import eve.statements.assignment.AssignmentStatement;
	import eve.statements.expressions.*;
	import eve.scope.ScopeManager;
	import java.util.List;
	import java.util.ArrayList;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class EveInterpreter extends TreeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NEGATION", "INIT_VARIABLE", "INIT_FUNCTION", "FUNCTION_PARAMETERS", "FUNCTION_BODY", "IDENT", "INTEGER", "STRING_LITERAL", "MULTILINE_COMMENT", "CHAR_LITERAL", "LETTER", "DIGIT", "WS", "COMMENT", "'print'", "'('", "')'", "';'", "'='", "'def'", "'var'", "'proto'", "'{'", "'}'", "','", "'not'", "'+'", "'-'", "'*'", "'/'", "'%'", "'/='", "'<'", "'<='", "'>='", "'>'", "'and'", "'or'"
    };
    public static final int EOF=-1;
    public static final int T__18=18;
    public static final int T__19=19;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int NEGATION=4;
    public static final int INIT_VARIABLE=5;
    public static final int INIT_FUNCTION=6;
    public static final int FUNCTION_PARAMETERS=7;
    public static final int FUNCTION_BODY=8;
    public static final int IDENT=9;
    public static final int INTEGER=10;
    public static final int STRING_LITERAL=11;
    public static final int MULTILINE_COMMENT=12;
    public static final int CHAR_LITERAL=13;
    public static final int LETTER=14;
    public static final int DIGIT=15;
    public static final int WS=16;
    public static final int COMMENT=17;

    // delegates
    // delegators


        public EveInterpreter(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public EveInterpreter(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return EveInterpreter.tokenNames; }
    public String getGrammarFileName() { return "/home/jeff/workspace/eve/src/eve/core/EveInterpreter.g"; }


    	//Re-usable list to keep track of statements in a function.
    	//Cleared after every function defintion.
    	private List<EveStatement> funcStatements = new ArrayList<EveStatement>();



    // $ANTLR start "interpret"
    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:25:1: interpret : ( statement )* EOF ;
    public final void interpret() throws RecognitionException {
        try {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:26:2: ( ( statement )* EOF )
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:26:4: ( statement )* EOF
            {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:26:4: ( statement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==INIT_VARIABLE||LA1_0==18||LA1_0==22) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:26:4: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_interpret57);
            	    statement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_interpret60); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "interpret"


    // $ANTLR start "statement"
    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:28:1: statement : codeStatement ;
    public final void statement() throws RecognitionException {
        try {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:29:2: ( codeStatement )
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:29:4: codeStatement
            {
            pushFollow(FOLLOW_codeStatement_in_statement69);
            codeStatement();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "statement"


    // $ANTLR start "printStatement"
    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:32:1: printStatement : ^( 'print' e= expression ) ;
    public final void printStatement() throws RecognitionException {
        ExpressionStatement e = null;


        try {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:33:2: ( ^( 'print' e= expression ) )
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:33:4: ^( 'print' e= expression )
            {
            match(input,18,FOLLOW_18_in_printStatement81); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_expression_in_printStatement85);
            e=expression();

            state._fsp--;


            match(input, Token.UP, null); 

            			PrintStatement ps = new PrintStatement(e);
            			ScopeManager.getCurrentScope().addCode(ps);
            		

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "printStatement"


    // $ANTLR start "assignmentStatement"
    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:39:1: assignmentStatement : ( ^( '=' IDENT e= expression ) | ^( '=' INIT_FUNCTION IDENT ^( FUNCTION_PARAMETERS p= parameters ) ^(fb= FUNCTION_BODY ( codeStatement )* ) ) );
    public final void assignmentStatement() throws RecognitionException {
        CommonTree fb=null;
        CommonTree IDENT1=null;
        CommonTree IDENT2=null;
        ExpressionStatement e = null;

        List<String> p = null;


        try {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:40:2: ( ^( '=' IDENT e= expression ) | ^( '=' INIT_FUNCTION IDENT ^( FUNCTION_PARAMETERS p= parameters ) ^(fb= FUNCTION_BODY ( codeStatement )* ) ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==22) ) {
                int LA3_1 = input.LA(2);

                if ( (LA3_1==DOWN) ) {
                    int LA3_2 = input.LA(3);

                    if ( (LA3_2==IDENT) ) {
                        alt3=1;
                    }
                    else if ( (LA3_2==INIT_FUNCTION) ) {
                        alt3=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:40:4: ^( '=' IDENT e= expression )
                    {
                    match(input,22,FOLLOW_22_in_assignmentStatement101); 

                    match(input, Token.DOWN, null); 
                    IDENT1=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_assignmentStatement103); 
                    pushFollow(FOLLOW_expression_in_assignmentStatement107);
                    e=expression();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     
                    			System.out.println("assigning " + e + " to " + (IDENT1!=null?IDENT1.getText():null));
                    			AssignmentStatement as = new AssignmentStatement((IDENT1!=null?IDENT1.getText():null), e);
                    			ScopeManager.getCurrentScope().addCode(as);
                    		

                    }
                    break;
                case 2 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:45:4: ^( '=' INIT_FUNCTION IDENT ^( FUNCTION_PARAMETERS p= parameters ) ^(fb= FUNCTION_BODY ( codeStatement )* ) )
                    {
                    match(input,22,FOLLOW_22_in_assignmentStatement116); 

                    match(input, Token.DOWN, null); 
                    match(input,INIT_FUNCTION,FOLLOW_INIT_FUNCTION_in_assignmentStatement118); 
                    IDENT2=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_assignmentStatement120); 
                    match(input,FUNCTION_PARAMETERS,FOLLOW_FUNCTION_PARAMETERS_in_assignmentStatement123); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_parameters_in_assignmentStatement127);
                    p=parameters();

                    state._fsp--;


                    match(input, Token.UP, null); 
                    fb=(CommonTree)match(input,FUNCTION_BODY,FOLLOW_FUNCTION_BODY_in_assignmentStatement133); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:45:85: ( codeStatement )*
                        loop2:
                        do {
                            int alt2=2;
                            int LA2_0 = input.LA(1);

                            if ( (LA2_0==INIT_VARIABLE||LA2_0==18||LA2_0==22) ) {
                                alt2=1;
                            }


                            switch (alt2) {
                        	case 1 :
                        	    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:45:85: codeStatement
                        	    {
                        	    pushFollow(FOLLOW_codeStatement_in_assignmentStatement135);
                        	    codeStatement();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop2;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }

                    match(input, Token.UP, null); 

                    			//will happen after statements collected!
                    			FunctionExpression funcExpr = new FunctionExpression(funcStatements);
                    			AssignmentStatement as = new AssignmentStatement((IDENT2!=null?IDENT2.getText():null), funcExpr);
                    			ScopeManager.getCurrentScope().addCode(as);
                    			System.out.println("initializing function " + (IDENT2!=null?IDENT2.getText():null) + " with " + funcStatements);
                    			
                    		

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "assignmentStatement"


    // $ANTLR start "codeStatement"
    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:55:1: codeStatement : ( initVariableStatement | printStatement | assignmentStatement );
    public final void codeStatement() throws RecognitionException {
        try {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:56:2: ( initVariableStatement | printStatement | assignmentStatement )
            int alt4=3;
            switch ( input.LA(1) ) {
            case INIT_VARIABLE:
                {
                alt4=1;
                }
                break;
            case 18:
                {
                alt4=2;
                }
                break;
            case 22:
                {
                alt4=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:56:4: initVariableStatement
                    {
                    pushFollow(FOLLOW_initVariableStatement_in_codeStatement152);
                    initVariableStatement();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:57:4: printStatement
                    {
                    pushFollow(FOLLOW_printStatement_in_codeStatement157);
                    printStatement();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:58:4: assignmentStatement
                    {
                    pushFollow(FOLLOW_assignmentStatement_in_codeStatement162);
                    assignmentStatement();

                    state._fsp--;


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "codeStatement"


    // $ANTLR start "initVariableStatement"
    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:61:1: initVariableStatement : ^( INIT_VARIABLE IDENT e= expression ) ;
    public final void initVariableStatement() throws RecognitionException {
        CommonTree IDENT3=null;
        ExpressionStatement e = null;


        try {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:62:2: ( ^( INIT_VARIABLE IDENT e= expression ) )
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:62:4: ^( INIT_VARIABLE IDENT e= expression )
            {
            match(input,INIT_VARIABLE,FOLLOW_INIT_VARIABLE_in_initVariableStatement174); 

            match(input, Token.DOWN, null); 
            IDENT3=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_initVariableStatement176); 
            pushFollow(FOLLOW_expression_in_initVariableStatement180);
            e=expression();

            state._fsp--;


            match(input, Token.UP, null); 

            			System.out.println("initializing " + (IDENT3!=null?IDENT3.getText():null) + " to " + e);
            			//funcStatements.clear();
            		

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "initVariableStatement"


    // $ANTLR start "expression"
    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:68:1: expression returns [ExpressionStatement result] : ( ^( '+' op1= expression op2= expression ) | ^( '-' op1= expression op2= expression ) | ^( '*' op1= expression op2= expression ) | ^( '/' op1= expression op2= expression ) | ^( '%' op1= expression op2= expression ) | ^( NEGATION e= expression ) | INTEGER | STRING_LITERAL );
    public final ExpressionStatement expression() throws RecognitionException {
        ExpressionStatement result = null;

        CommonTree INTEGER4=null;
        CommonTree STRING_LITERAL5=null;
        ExpressionStatement op1 = null;

        ExpressionStatement op2 = null;

        ExpressionStatement e = null;


        try {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:69:2: ( ^( '+' op1= expression op2= expression ) | ^( '-' op1= expression op2= expression ) | ^( '*' op1= expression op2= expression ) | ^( '/' op1= expression op2= expression ) | ^( '%' op1= expression op2= expression ) | ^( NEGATION e= expression ) | INTEGER | STRING_LITERAL )
            int alt5=8;
            switch ( input.LA(1) ) {
            case 30:
                {
                alt5=1;
                }
                break;
            case 31:
                {
                alt5=2;
                }
                break;
            case 32:
                {
                alt5=3;
                }
                break;
            case 33:
                {
                alt5=4;
                }
                break;
            case 34:
                {
                alt5=5;
                }
                break;
            case NEGATION:
                {
                alt5=6;
                }
                break;
            case INTEGER:
                {
                alt5=7;
                }
                break;
            case STRING_LITERAL:
                {
                alt5=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:69:4: ^( '+' op1= expression op2= expression )
                    {
                    match(input,30,FOLLOW_30_in_expression199); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression203);
                    op1=expression();

                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_expression207);
                    op2=expression();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new PlusExpression(op1, op2);	

                    }
                    break;
                case 2 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:70:4: ^( '-' op1= expression op2= expression )
                    {
                    match(input,31,FOLLOW_31_in_expression216); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression220);
                    op1=expression();

                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_expression224);
                    op2=expression();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new MinusExpression(op1, op2); 

                    }
                    break;
                case 3 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:71:4: ^( '*' op1= expression op2= expression )
                    {
                    match(input,32,FOLLOW_32_in_expression233); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression237);
                    op1=expression();

                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_expression241);
                    op2=expression();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new MultiplicationExpression(op1, op2); 

                    }
                    break;
                case 4 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:72:4: ^( '/' op1= expression op2= expression )
                    {
                    match(input,33,FOLLOW_33_in_expression250); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression254);
                    op1=expression();

                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_expression258);
                    op2=expression();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new DivisionExpression(op1, op1); 

                    }
                    break;
                case 5 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:73:4: ^( '%' op1= expression op2= expression )
                    {
                    match(input,34,FOLLOW_34_in_expression267); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression271);
                    op1=expression();

                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_expression275);
                    op2=expression();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new ModulusExpression(op1, op2); 

                    }
                    break;
                case 6 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:74:4: ^( NEGATION e= expression )
                    {
                    match(input,NEGATION,FOLLOW_NEGATION_in_expression284); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression288);
                    e=expression();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new NegationExpression(e); 

                    }
                    break;
                case 7 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:75:4: INTEGER
                    {
                    INTEGER4=(CommonTree)match(input,INTEGER,FOLLOW_INTEGER_in_expression296); 
                     
                    			result = new WrappedPrimitiveExpression(Integer.parseInt((INTEGER4!=null?INTEGER4.getText():null))); 
                    		

                    }
                    break;
                case 8 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:78:4: STRING_LITERAL
                    {
                    STRING_LITERAL5=(CommonTree)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_expression303); 

                    			result = new WrappedPrimitiveExpression((STRING_LITERAL5!=null?STRING_LITERAL5.getText():null));
                    		

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "expression"


    // $ANTLR start "parameters"
    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:83:1: parameters returns [List<String> result] : IDENT ( IDENT )* ;
    public final List<String> parameters() throws RecognitionException {
        List<String> result = null;

        try {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:84:2: ( IDENT ( IDENT )* )
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:84:4: IDENT ( IDENT )*
            {
            match(input,IDENT,FOLLOW_IDENT_in_parameters321); 
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:84:10: ( IDENT )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==IDENT) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:84:11: IDENT
            	    {
            	    match(input,IDENT,FOLLOW_IDENT_in_parameters324); 

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

             result = null; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "parameters"

    // Delegated rules


 

    public static final BitSet FOLLOW_statement_in_interpret57 = new BitSet(new long[]{0x0000000000440020L});
    public static final BitSet FOLLOW_EOF_in_interpret60 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_codeStatement_in_statement69 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_printStatement81 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_printStatement85 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_22_in_assignmentStatement101 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_assignmentStatement103 = new BitSet(new long[]{0x00000007C0000C10L});
    public static final BitSet FOLLOW_expression_in_assignmentStatement107 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_22_in_assignmentStatement116 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_INIT_FUNCTION_in_assignmentStatement118 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_IDENT_in_assignmentStatement120 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_FUNCTION_PARAMETERS_in_assignmentStatement123 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_parameters_in_assignmentStatement127 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FUNCTION_BODY_in_assignmentStatement133 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_codeStatement_in_assignmentStatement135 = new BitSet(new long[]{0x0000000000440028L});
    public static final BitSet FOLLOW_initVariableStatement_in_codeStatement152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_printStatement_in_codeStatement157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentStatement_in_codeStatement162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INIT_VARIABLE_in_initVariableStatement174 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_initVariableStatement176 = new BitSet(new long[]{0x00000007C0000C10L});
    public static final BitSet FOLLOW_expression_in_initVariableStatement180 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_30_in_expression199 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression203 = new BitSet(new long[]{0x00000007C0000C10L});
    public static final BitSet FOLLOW_expression_in_expression207 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_31_in_expression216 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression220 = new BitSet(new long[]{0x00000007C0000C10L});
    public static final BitSet FOLLOW_expression_in_expression224 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_32_in_expression233 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression237 = new BitSet(new long[]{0x00000007C0000C10L});
    public static final BitSet FOLLOW_expression_in_expression241 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_33_in_expression250 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression254 = new BitSet(new long[]{0x00000007C0000C10L});
    public static final BitSet FOLLOW_expression_in_expression258 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_34_in_expression267 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression271 = new BitSet(new long[]{0x00000007C0000C10L});
    public static final BitSet FOLLOW_expression_in_expression275 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NEGATION_in_expression284 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression288 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INTEGER_in_expression296 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_expression303 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_parameters321 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_IDENT_in_parameters324 = new BitSet(new long[]{0x0000000000000202L});

}