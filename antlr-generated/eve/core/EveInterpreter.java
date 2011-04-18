// $ANTLR 3.3 Nov 30, 2010 12:50:56 /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g 2011-04-18 08:01:50

	package eve.core;
	import eve.statements.*;
	import eve.statements.assignment.AssignmentStatement;
	import eve.statements.expressions.*;
	import eve.scope.ScopeManager;
	import java.util.Queue;
	import java.util.Set;
	import java.util.LinkedList;
	import java.util.ArrayList;
	import java.util.Map;
	import java.util.HashMap;
	import java.util.Map.Entry;


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
    	private Queue<EveStatement> statementQueue = new LinkedList<EveStatement>();
    	//private Queue<FunctionExpression> functionQueue = new LinkedList<FunctionExpression>();
    	private Map<String, EveObject> protos = new HashMap<String, EveObject>();
    	
    	private void queueStatement(EveStatement statement) {
    		statementQueue.add(statement);
    	}
    	
    	private FunctionExpression createFunction() {
    		List<EveStatement> funcStatements = new ArrayList<EveStatement>();
    		EveStatement statement = null;
    		while ((statement = statementQueue.poll()) != null) {
    			funcStatements.add(statement);
    		}
    		
    		return new FunctionExpression(funcStatements);
    	}
    	
    	private void queuePrototype(String protoName) {
    		EveObject proto = EveObject.customType(protoName);
    		
    		/*
    		FunctionExpression funcExpr = null;
    		while ((funcExpr = functionQueue.poll()) != null) {
    			proto.addCode(funcExpr);
    		}
    		*/
    		
    		EveStatement statement = null;
    		while ((statement = statementQueue.poll()) != null) {
    			proto.addCode(statement);
    		}
    		
    		protos.put(protoName, proto);
    	}
    	
    	private void globalSetup() {
    		EveObject global = ScopeManager.getGlobalScope();
    		
    		EveObject proto = null;
    		Set<Map.Entry<String, EveObject>> entries = protos.entrySet();
    		for (Map.Entry<String, EveObject> entry : entries) {
    			global.putField(entry.getKey(), entry.getValue());
    		}
    		
    		protos.clear();
    		
    		/*
    		FunctionExpression funcExpr = null;
    		while ((funcExpr = functionQueue.poll()) != null) {
    			global.addCode(funcExpr);
    		}
    		*/
    		
    		EveStatement statement = null;
    		while ((statement = statementQueue.poll()) != null) {
    			global.addCode(statement);
    		}
    	}



    // $ANTLR start "interpret"
    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:88:1: interpret : ( statement )* EOF ;
    public final void interpret() throws RecognitionException {
        try {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:89:2: ( ( statement )* EOF )
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:89:4: ( statement )* EOF
            {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:89:4: ( statement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==INIT_VARIABLE||LA1_0==18||LA1_0==22) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:89:4: statement
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

            			globalSetup();
            		

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
    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:94:1: statement : codeStatement ;
    public final void statement() throws RecognitionException {
        try {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:95:2: ( codeStatement )
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:95:4: codeStatement
            {
            pushFollow(FOLLOW_codeStatement_in_statement73);
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
    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:98:1: printStatement : ^( 'print' e= expression ) ;
    public final void printStatement() throws RecognitionException {
        ExpressionStatement e = null;


        try {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:99:2: ( ^( 'print' e= expression ) )
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:99:4: ^( 'print' e= expression )
            {
            match(input,18,FOLLOW_18_in_printStatement85); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_expression_in_printStatement89);
            e=expression();

            state._fsp--;


            match(input, Token.UP, null); 

            			PrintStatement ps = new PrintStatement(e);
            			queueStatement(ps);
            		

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
    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:105:1: assignmentStatement : ( ^( '=' IDENT e= expression ) | ^( '=' INIT_FUNCTION IDENT ^( FUNCTION_PARAMETERS p= parameters ) ^(fb= FUNCTION_BODY ( codeStatement )* ) ) );
    public final void assignmentStatement() throws RecognitionException {
        CommonTree fb=null;
        CommonTree IDENT1=null;
        CommonTree IDENT2=null;
        ExpressionStatement e = null;

        List<String> p = null;


        try {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:106:2: ( ^( '=' IDENT e= expression ) | ^( '=' INIT_FUNCTION IDENT ^( FUNCTION_PARAMETERS p= parameters ) ^(fb= FUNCTION_BODY ( codeStatement )* ) ) )
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
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:106:4: ^( '=' IDENT e= expression )
                    {
                    match(input,22,FOLLOW_22_in_assignmentStatement105); 

                    match(input, Token.DOWN, null); 
                    IDENT1=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_assignmentStatement107); 
                    pushFollow(FOLLOW_expression_in_assignmentStatement111);
                    e=expression();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     
                    			System.out.println("assigning " + e + " to " + (IDENT1!=null?IDENT1.getText():null));
                    			AssignmentStatement as = new AssignmentStatement((IDENT1!=null?IDENT1.getText():null), e);
                    			queueStatement(as);
                    		

                    }
                    break;
                case 2 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:111:4: ^( '=' INIT_FUNCTION IDENT ^( FUNCTION_PARAMETERS p= parameters ) ^(fb= FUNCTION_BODY ( codeStatement )* ) )
                    {
                    match(input,22,FOLLOW_22_in_assignmentStatement120); 

                    match(input, Token.DOWN, null); 
                    match(input,INIT_FUNCTION,FOLLOW_INIT_FUNCTION_in_assignmentStatement122); 
                    IDENT2=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_assignmentStatement124); 
                    match(input,FUNCTION_PARAMETERS,FOLLOW_FUNCTION_PARAMETERS_in_assignmentStatement127); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_parameters_in_assignmentStatement131);
                    p=parameters();

                    state._fsp--;


                    match(input, Token.UP, null); 
                    fb=(CommonTree)match(input,FUNCTION_BODY,FOLLOW_FUNCTION_BODY_in_assignmentStatement137); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:111:85: ( codeStatement )*
                        loop2:
                        do {
                            int alt2=2;
                            int LA2_0 = input.LA(1);

                            if ( (LA2_0==INIT_VARIABLE||LA2_0==18||LA2_0==22) ) {
                                alt2=1;
                            }


                            switch (alt2) {
                        	case 1 :
                        	    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:111:85: codeStatement
                        	    {
                        	    pushFollow(FOLLOW_codeStatement_in_assignmentStatement139);
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
                    			FunctionExpression funcExpr = createFunction();
                    			AssignmentStatement as = new AssignmentStatement((IDENT2!=null?IDENT2.getText():null), funcExpr);
                    			queueStatement(as);
                    		

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
    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:119:1: codeStatement : ( initVariableStatement | printStatement | assignmentStatement );
    public final void codeStatement() throws RecognitionException {
        try {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:120:2: ( initVariableStatement | printStatement | assignmentStatement )
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
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:120:4: initVariableStatement
                    {
                    pushFollow(FOLLOW_initVariableStatement_in_codeStatement156);
                    initVariableStatement();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:121:4: printStatement
                    {
                    pushFollow(FOLLOW_printStatement_in_codeStatement161);
                    printStatement();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:122:4: assignmentStatement
                    {
                    pushFollow(FOLLOW_assignmentStatement_in_codeStatement166);
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
    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:125:1: initVariableStatement : ^( INIT_VARIABLE IDENT e= expression ) ;
    public final void initVariableStatement() throws RecognitionException {
        CommonTree IDENT3=null;
        ExpressionStatement e = null;


        try {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:126:2: ( ^( INIT_VARIABLE IDENT e= expression ) )
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:126:4: ^( INIT_VARIABLE IDENT e= expression )
            {
            match(input,INIT_VARIABLE,FOLLOW_INIT_VARIABLE_in_initVariableStatement178); 

            match(input, Token.DOWN, null); 
            IDENT3=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_initVariableStatement180); 
            pushFollow(FOLLOW_expression_in_initVariableStatement184);
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
    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:132:1: expression returns [ExpressionStatement result] : ( ^( '+' op1= expression op2= expression ) | ^( '-' op1= expression op2= expression ) | ^( '*' op1= expression op2= expression ) | ^( '/' op1= expression op2= expression ) | ^( '%' op1= expression op2= expression ) | ^( NEGATION e= expression ) | INTEGER | STRING_LITERAL );
    public final ExpressionStatement expression() throws RecognitionException {
        ExpressionStatement result = null;

        CommonTree INTEGER4=null;
        CommonTree STRING_LITERAL5=null;
        ExpressionStatement op1 = null;

        ExpressionStatement op2 = null;

        ExpressionStatement e = null;


        try {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:133:2: ( ^( '+' op1= expression op2= expression ) | ^( '-' op1= expression op2= expression ) | ^( '*' op1= expression op2= expression ) | ^( '/' op1= expression op2= expression ) | ^( '%' op1= expression op2= expression ) | ^( NEGATION e= expression ) | INTEGER | STRING_LITERAL )
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
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:133:4: ^( '+' op1= expression op2= expression )
                    {
                    match(input,30,FOLLOW_30_in_expression203); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression207);
                    op1=expression();

                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_expression211);
                    op2=expression();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new PlusExpression(op1, op2);	

                    }
                    break;
                case 2 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:134:4: ^( '-' op1= expression op2= expression )
                    {
                    match(input,31,FOLLOW_31_in_expression220); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression224);
                    op1=expression();

                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_expression228);
                    op2=expression();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new MinusExpression(op1, op2); 

                    }
                    break;
                case 3 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:135:4: ^( '*' op1= expression op2= expression )
                    {
                    match(input,32,FOLLOW_32_in_expression237); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression241);
                    op1=expression();

                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_expression245);
                    op2=expression();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new MultiplicationExpression(op1, op2); 

                    }
                    break;
                case 4 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:136:4: ^( '/' op1= expression op2= expression )
                    {
                    match(input,33,FOLLOW_33_in_expression254); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression258);
                    op1=expression();

                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_expression262);
                    op2=expression();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new DivisionExpression(op1, op1); 

                    }
                    break;
                case 5 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:137:4: ^( '%' op1= expression op2= expression )
                    {
                    match(input,34,FOLLOW_34_in_expression271); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression275);
                    op1=expression();

                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_expression279);
                    op2=expression();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new ModulusExpression(op1, op2); 

                    }
                    break;
                case 6 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:138:4: ^( NEGATION e= expression )
                    {
                    match(input,NEGATION,FOLLOW_NEGATION_in_expression288); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression292);
                    e=expression();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     result = new NegationExpression(e); 

                    }
                    break;
                case 7 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:139:4: INTEGER
                    {
                    INTEGER4=(CommonTree)match(input,INTEGER,FOLLOW_INTEGER_in_expression300); 
                     
                    			result = new WrappedPrimitiveExpression(Integer.parseInt((INTEGER4!=null?INTEGER4.getText():null))); 
                    		

                    }
                    break;
                case 8 :
                    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:142:4: STRING_LITERAL
                    {
                    STRING_LITERAL5=(CommonTree)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_expression307); 

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
    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:147:1: parameters returns [List<String> result] : IDENT ( IDENT )* ;
    public final List<String> parameters() throws RecognitionException {
        List<String> result = null;

        try {
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:148:2: ( IDENT ( IDENT )* )
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:148:4: IDENT ( IDENT )*
            {
            match(input,IDENT,FOLLOW_IDENT_in_parameters325); 
            // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:148:10: ( IDENT )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==IDENT) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/EveInterpreter.g:148:11: IDENT
            	    {
            	    match(input,IDENT,FOLLOW_IDENT_in_parameters328); 

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
    public static final BitSet FOLLOW_codeStatement_in_statement73 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_printStatement85 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_printStatement89 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_22_in_assignmentStatement105 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_assignmentStatement107 = new BitSet(new long[]{0x00000007C0000C10L});
    public static final BitSet FOLLOW_expression_in_assignmentStatement111 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_22_in_assignmentStatement120 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_INIT_FUNCTION_in_assignmentStatement122 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_IDENT_in_assignmentStatement124 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_FUNCTION_PARAMETERS_in_assignmentStatement127 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_parameters_in_assignmentStatement131 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FUNCTION_BODY_in_assignmentStatement137 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_codeStatement_in_assignmentStatement139 = new BitSet(new long[]{0x0000000000440028L});
    public static final BitSet FOLLOW_initVariableStatement_in_codeStatement156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_printStatement_in_codeStatement161 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentStatement_in_codeStatement166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INIT_VARIABLE_in_initVariableStatement178 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_initVariableStatement180 = new BitSet(new long[]{0x00000007C0000C10L});
    public static final BitSet FOLLOW_expression_in_initVariableStatement184 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_30_in_expression203 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression207 = new BitSet(new long[]{0x00000007C0000C10L});
    public static final BitSet FOLLOW_expression_in_expression211 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_31_in_expression220 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression224 = new BitSet(new long[]{0x00000007C0000C10L});
    public static final BitSet FOLLOW_expression_in_expression228 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_32_in_expression237 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression241 = new BitSet(new long[]{0x00000007C0000C10L});
    public static final BitSet FOLLOW_expression_in_expression245 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_33_in_expression254 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression258 = new BitSet(new long[]{0x00000007C0000C10L});
    public static final BitSet FOLLOW_expression_in_expression262 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_34_in_expression271 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression275 = new BitSet(new long[]{0x00000007C0000C10L});
    public static final BitSet FOLLOW_expression_in_expression279 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NEGATION_in_expression288 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression292 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INTEGER_in_expression300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_expression307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_parameters325 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_IDENT_in_parameters328 = new BitSet(new long[]{0x0000000000000202L});

}