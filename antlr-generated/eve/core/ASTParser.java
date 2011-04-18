// $ANTLR 3.3 Nov 30, 2010 12:50:56 /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g 2011-04-18 12:26:09

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


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class ASTParser extends TreeFilter {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NEGATION", "INIT_VARIABLE", "UPDATE_VARIABLE", "INIT_FUNCTION", "FUNCTION_PARAMETERS", "FUNCTION_BODY", "IDENT", "INTEGER", "STRING_LITERAL", "MULTILINE_COMMENT", "CHAR_LITERAL", "LETTER", "DIGIT", "WS", "COMMENT", "'print'", "'('", "')'", "';'", "'='", "'def'", "'var'", "'proto'", "'{'", "'}'", "','", "'not'", "'+'", "'-'", "'*'", "'/'", "'%'", "'/='", "'<'", "'<='", "'>='", "'>'", "'and'", "'or'"
    };
    public static final int EOF=-1;
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
    public static final int T__42=42;
    public static final int NEGATION=4;
    public static final int INIT_VARIABLE=5;
    public static final int UPDATE_VARIABLE=6;
    public static final int INIT_FUNCTION=7;
    public static final int FUNCTION_PARAMETERS=8;
    public static final int FUNCTION_BODY=9;
    public static final int IDENT=10;
    public static final int INTEGER=11;
    public static final int STRING_LITERAL=12;
    public static final int MULTILINE_COMMENT=13;
    public static final int CHAR_LITERAL=14;
    public static final int LETTER=15;
    public static final int DIGIT=16;
    public static final int WS=17;
    public static final int COMMENT=18;

    // delegates
    // delegators


        public ASTParser(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public ASTParser(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return ASTParser.tokenNames; }
    public String getGrammarFileName() { return "/home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g"; }


    	//Parameter management.	
    	private StringBuilder currentParameters = new StringBuilder();
    	private List<String> getParams() {
    		List<String> params = Arrays.asList(currentParameters.toString().trim().split(" "));
    		currentParameters = new StringBuilder();
    		return params;
    	}
    	
    	private void pushParam(String param) {
    		currentParameters.append(param).append(" ");
    	}
    	
    	//Function management
    	FunctionExpression currentFuncExpr = null;
    	boolean inFunction = false;



    // $ANTLR start "parameters"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:45:1: parameters returns [List<String> result] : IDENT ( IDENT )* ;
    public final List<String> parameters() throws RecognitionException {
        List<String> result = null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:46:2: ( IDENT ( IDENT )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:46:4: IDENT ( IDENT )*
            {
            match(input,IDENT,FOLLOW_IDENT_in_parameters79); if (state.failed) return result;
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:46:10: ( IDENT )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==IDENT) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:46:11: IDENT
            	    {
            	    match(input,IDENT,FOLLOW_IDENT_in_parameters82); if (state.failed) return result;

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            if ( state.backtracking==1 ) {
               result = null; 
            }

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


    // $ANTLR start "topdown"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:49:1: topdown : ( enterFunction | beginParameters | assignFunctionDown | codeStatement );
    public final void topdown() throws RecognitionException {
        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:50:2: ( enterFunction | beginParameters | assignFunctionDown | codeStatement )
            int alt2=4;
            switch ( input.LA(1) ) {
            case FUNCTION_BODY:
                {
                alt2=1;
                }
                break;
            case FUNCTION_PARAMETERS:
                {
                alt2=2;
                }
                break;
            case 23:
                {
                alt2=3;
                }
                break;
            case INIT_VARIABLE:
            case UPDATE_VARIABLE:
            case 19:
                {
                alt2=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:50:4: enterFunction
                    {
                    pushFollow(FOLLOW_enterFunction_in_topdown97);
                    enterFunction();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:51:4: beginParameters
                    {
                    pushFollow(FOLLOW_beginParameters_in_topdown102);
                    beginParameters();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:52:4: assignFunctionDown
                    {
                    pushFollow(FOLLOW_assignFunctionDown_in_topdown107);
                    assignFunctionDown();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:53:4: codeStatement
                    {
                    pushFollow(FOLLOW_codeStatement_in_topdown112);
                    codeStatement();

                    state._fsp--;
                    if (state.failed) return ;

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
    // $ANTLR end "topdown"


    // $ANTLR start "bottomup"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:56:1: bottomup : ( exitFunction | endParameters | assignFunctionUp );
    public final void bottomup() throws RecognitionException {
        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:57:2: ( exitFunction | endParameters | assignFunctionUp )
            int alt3=3;
            switch ( input.LA(1) ) {
            case FUNCTION_BODY:
                {
                alt3=1;
                }
                break;
            case FUNCTION_PARAMETERS:
                {
                alt3=2;
                }
                break;
            case 23:
                {
                alt3=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:57:4: exitFunction
                    {
                    pushFollow(FOLLOW_exitFunction_in_bottomup123);
                    exitFunction();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:58:4: endParameters
                    {
                    pushFollow(FOLLOW_endParameters_in_bottomup128);
                    endParameters();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:59:4: assignFunctionUp
                    {
                    pushFollow(FOLLOW_assignFunctionUp_in_bottomup133);
                    assignFunctionUp();

                    state._fsp--;
                    if (state.failed) return ;

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
    // $ANTLR end "bottomup"


    // $ANTLR start "assignFunctionDown"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:62:1: assignFunctionDown : ^( '=' INIT_FUNCTION IDENT ( . )* ) ;
    public final void assignFunctionDown() throws RecognitionException {
        CommonTree IDENT1=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:63:2: ( ^( '=' INIT_FUNCTION IDENT ( . )* ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:63:4: ^( '=' INIT_FUNCTION IDENT ( . )* )
            {
            match(input,23,FOLLOW_23_in_assignFunctionDown146); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            match(input,INIT_FUNCTION,FOLLOW_INIT_FUNCTION_in_assignFunctionDown148); if (state.failed) return ;
            IDENT1=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_assignFunctionDown150); if (state.failed) return ;
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:63:30: ( . )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>=NEGATION && LA4_0<=42)) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:0:0: .
            	    {
            	    matchAny(input); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            match(input, Token.UP, null); if (state.failed) return ;
            if ( state.backtracking==1 ) {

              		//Create new FunctionExpression.
              		currentFuncExpr = new FunctionExpression();
              		//Push on to current function stack.
              		//implement later.
              		System.out.println("Creating new function expression for " + (IDENT1!=null?IDENT1.getText():null));
              	
            }

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
    // $ANTLR end "assignFunctionDown"


    // $ANTLR start "assignFunctionUp"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:72:1: assignFunctionUp : ^( '=' INIT_FUNCTION IDENT ( . )* ) ;
    public final void assignFunctionUp() throws RecognitionException {
        CommonTree IDENT2=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:73:2: ( ^( '=' INIT_FUNCTION IDENT ( . )* ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:73:4: ^( '=' INIT_FUNCTION IDENT ( . )* )
            {
            match(input,23,FOLLOW_23_in_assignFunctionUp169); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            match(input,INIT_FUNCTION,FOLLOW_INIT_FUNCTION_in_assignFunctionUp171); if (state.failed) return ;
            IDENT2=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_assignFunctionUp173); if (state.failed) return ;
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:73:30: ( . )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>=NEGATION && LA5_0<=42)) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:0:0: .
            	    {
            	    matchAny(input); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            match(input, Token.UP, null); if (state.failed) return ;
            if ( state.backtracking==1 ) {

              		//Create new Assignment statement.
              		AssignmentStatement as = new InitVariableStatement((IDENT2!=null?IDENT2.getText():null), currentFuncExpr);
              		currentFuncExpr = null; //clear for reuse.
              		//pop current function expression and use for assignment.
              		ExecutionTree.addStatement(as);
              		System.out.println("Assigning " + (IDENT2!=null?IDENT2.getText():null) + " function to current scope.");
              	
            }

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
    // $ANTLR end "assignFunctionUp"


    // $ANTLR start "enterFunction"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:83:1: enterFunction : ^( FUNCTION_BODY ( . )* ) ;
    public final void enterFunction() throws RecognitionException {
        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:84:2: ( ^( FUNCTION_BODY ( . )* ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:84:4: ^( FUNCTION_BODY ( . )* )
            {
            match(input,FUNCTION_BODY,FOLLOW_FUNCTION_BODY_in_enterFunction192); if (state.failed) return ;

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); if (state.failed) return ;
                // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:84:20: ( . )*
                loop6:
                do {
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( ((LA6_0>=NEGATION && LA6_0<=42)) ) {
                        alt6=1;
                    }


                    switch (alt6) {
                	case 1 :
                	    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:0:0: .
                	    {
                	    matchAny(input); if (state.failed) return ;

                	    }
                	    break;

                	default :
                	    break loop6;
                    }
                } while (true);


                match(input, Token.UP, null); if (state.failed) return ;
            }
            if ( state.backtracking==1 ) {

              			//Peek at current function and set scope to it.
              			System.out.println("Setting scope to current function.");
              			inFunction = true;
              		
            }

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
    // $ANTLR end "enterFunction"


    // $ANTLR start "exitFunction"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:91:1: exitFunction : ( FUNCTION_BODY ( . )* ) ;
    public final void exitFunction() throws RecognitionException {
        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:92:2: ( ( FUNCTION_BODY ( . )* ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:92:4: ( FUNCTION_BODY ( . )* )
            {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:92:4: ( FUNCTION_BODY ( . )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:92:5: FUNCTION_BODY ( . )*
            {
            match(input,FUNCTION_BODY,FOLLOW_FUNCTION_BODY_in_exitFunction211); if (state.failed) return ;
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:92:19: ( . )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>=NEGATION && LA7_0<=42)) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:0:0: .
            	    {
            	    matchAny(input); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }

            if ( state.backtracking==1 ) {

              			//pop scope stack.
              			System.out.println("Popping function scope.");
              			
              			//we know we are in a function (that we will shortly be leaving)
              			inFunction = false;
              		
            }

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
    // $ANTLR end "exitFunction"


    // $ANTLR start "beginParameters"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:101:1: beginParameters : ( FUNCTION_PARAMETERS type= . (s= IDENT )* ) ;
    public final void beginParameters() throws RecognitionException {
        CommonTree s=null;
        CommonTree type=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:102:2: ( ( FUNCTION_PARAMETERS type= . (s= IDENT )* ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:102:4: ( FUNCTION_PARAMETERS type= . (s= IDENT )* )
            {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:102:4: ( FUNCTION_PARAMETERS type= . (s= IDENT )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:102:5: FUNCTION_PARAMETERS type= . (s= IDENT )*
            {
            match(input,FUNCTION_PARAMETERS,FOLLOW_FUNCTION_PARAMETERS_in_beginParameters230); if (state.failed) return ;
            type=(CommonTree)input.LT(1);
            matchAny(input); if (state.failed) return ;
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:102:32: (s= IDENT )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==IDENT) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:102:33: s= IDENT
            	    {
            	    s=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_beginParameters239); if (state.failed) return ;
            	    if ( state.backtracking==1 ) {
            	       pushParam((s!=null?s.getText():null)); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }

            if ( state.backtracking==1 ) {

              			System.out.println("Adding parameters " + getParams() + " to current function def.");
              			//Gather parameters
              			//Add to current function def.
              		
            }

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
    // $ANTLR end "beginParameters"


    // $ANTLR start "endParameters"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:109:1: endParameters : ( FUNCTION_PARAMETERS ) ;
    public final void endParameters() throws RecognitionException {
        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:110:2: ( ( FUNCTION_PARAMETERS ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:110:4: ( FUNCTION_PARAMETERS )
            {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:110:4: ( FUNCTION_PARAMETERS )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:110:5: FUNCTION_PARAMETERS
            {
            match(input,FUNCTION_PARAMETERS,FOLLOW_FUNCTION_PARAMETERS_in_endParameters260); if (state.failed) return ;

            }

            if ( state.backtracking==1 ) {

              			//Ignore, i think?
              		
            }

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
    // $ANTLR end "endParameters"


    // $ANTLR start "codeStatement"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:116:1: codeStatement : ( printStatement | initVariableStatement | updateVariableStatement );
    public final void codeStatement() throws RecognitionException {
        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:117:2: ( printStatement | initVariableStatement | updateVariableStatement )
            int alt9=3;
            switch ( input.LA(1) ) {
            case 19:
                {
                alt9=1;
                }
                break;
            case INIT_VARIABLE:
                {
                alt9=2;
                }
                break;
            case UPDATE_VARIABLE:
                {
                alt9=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:117:4: printStatement
                    {
                    pushFollow(FOLLOW_printStatement_in_codeStatement275);
                    printStatement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:118:4: initVariableStatement
                    {
                    pushFollow(FOLLOW_initVariableStatement_in_codeStatement280);
                    initVariableStatement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:119:4: updateVariableStatement
                    {
                    pushFollow(FOLLOW_updateVariableStatement_in_codeStatement285);
                    updateVariableStatement();

                    state._fsp--;
                    if (state.failed) return ;

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


    // $ANTLR start "printStatement"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:122:1: printStatement : ^( 'print' e= expression ) ;
    public final void printStatement() throws RecognitionException {
        ExpressionStatement e = null;


        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:123:2: ( ^( 'print' e= expression ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:123:4: ^( 'print' e= expression )
            {
            match(input,19,FOLLOW_19_in_printStatement298); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            pushFollow(FOLLOW_expression_in_printStatement302);
            e=expression();

            state._fsp--;
            if (state.failed) return ;

            match(input, Token.UP, null); if (state.failed) return ;
            if ( state.backtracking==1 ) {

              			PrintStatement ps = new PrintStatement(e);
              			if (inFunction) {
              				currentFuncExpr.addStatement(ps);
              			}
              			else {
              				ExecutionTree.addStatement(ps);
              			}
              			System.out.println("print statement");
              		
            }

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


    // $ANTLR start "initVariableStatement"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:135:1: initVariableStatement : ^( INIT_VARIABLE IDENT e= expression ) ;
    public final void initVariableStatement() throws RecognitionException {
        CommonTree IDENT3=null;
        ExpressionStatement e = null;


        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:136:2: ( ^( INIT_VARIABLE IDENT e= expression ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:136:4: ^( INIT_VARIABLE IDENT e= expression )
            {
            match(input,INIT_VARIABLE,FOLLOW_INIT_VARIABLE_in_initVariableStatement318); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            IDENT3=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_initVariableStatement320); if (state.failed) return ;
            pushFollow(FOLLOW_expression_in_initVariableStatement324);
            e=expression();

            state._fsp--;
            if (state.failed) return ;

            match(input, Token.UP, null); if (state.failed) return ;
            if ( state.backtracking==1 ) {

              			System.out.println("Initialize " + (IDENT3!=null?IDENT3.getText():null) + " to " + e);
              			AssignmentStatement as = new InitVariableStatement((IDENT3!=null?IDENT3.getText():null), e);
              			if (inFunction) {
              				currentFuncExpr.addStatement(as);
              			}
              			else {
              				ExecutionTree.addStatement(as);
              			}
              		
            }

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


    // $ANTLR start "updateVariableStatement"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:148:1: updateVariableStatement : ^( UPDATE_VARIABLE IDENT e= expression ) ;
    public final void updateVariableStatement() throws RecognitionException {
        CommonTree IDENT4=null;
        ExpressionStatement e = null;


        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:149:2: ( ^( UPDATE_VARIABLE IDENT e= expression ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:149:4: ^( UPDATE_VARIABLE IDENT e= expression )
            {
            match(input,UPDATE_VARIABLE,FOLLOW_UPDATE_VARIABLE_in_updateVariableStatement339); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            IDENT4=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_updateVariableStatement341); if (state.failed) return ;
            pushFollow(FOLLOW_expression_in_updateVariableStatement345);
            e=expression();

            state._fsp--;
            if (state.failed) return ;

            match(input, Token.UP, null); if (state.failed) return ;
            if ( state.backtracking==1 ) {

              			System.out.println("Update variable " + (IDENT4!=null?IDENT4.getText():null) + " to " + e);
              			AssignmentStatement as = new UpdateVariableStatement((IDENT4!=null?IDENT4.getText():null), e);
              			if (inFunction) {
              				currentFuncExpr.addStatement(as);
              			}
              			else {
              				ExecutionTree.addStatement(as);
              			}
              		
            }

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
    // $ANTLR end "updateVariableStatement"


    // $ANTLR start "expression"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:162:1: expression returns [ExpressionStatement result] : ( ^( '+' op1= expression op2= expression ) | ^( '-' op1= expression op2= expression ) | ^( '*' op1= expression op2= expression ) | ^( '/' op1= expression op2= expression ) | ^( '%' op1= expression op2= expression ) | ^( NEGATION e= expression ) | IDENT | INTEGER | STRING_LITERAL );
    public final ExpressionStatement expression() throws RecognitionException {
        ExpressionStatement result = null;

        CommonTree IDENT5=null;
        CommonTree INTEGER6=null;
        CommonTree STRING_LITERAL7=null;
        ExpressionStatement op1 = null;

        ExpressionStatement op2 = null;

        ExpressionStatement e = null;


        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:163:2: ( ^( '+' op1= expression op2= expression ) | ^( '-' op1= expression op2= expression ) | ^( '*' op1= expression op2= expression ) | ^( '/' op1= expression op2= expression ) | ^( '%' op1= expression op2= expression ) | ^( NEGATION e= expression ) | IDENT | INTEGER | STRING_LITERAL )
            int alt10=9;
            switch ( input.LA(1) ) {
            case 31:
                {
                alt10=1;
                }
                break;
            case 32:
                {
                alt10=2;
                }
                break;
            case 33:
                {
                alt10=3;
                }
                break;
            case 34:
                {
                alt10=4;
                }
                break;
            case 35:
                {
                alt10=5;
                }
                break;
            case NEGATION:
                {
                alt10=6;
                }
                break;
            case IDENT:
                {
                alt10=7;
                }
                break;
            case INTEGER:
                {
                alt10=8;
                }
                break;
            case STRING_LITERAL:
                {
                alt10=9;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return result;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:163:4: ^( '+' op1= expression op2= expression )
                    {
                    match(input,31,FOLLOW_31_in_expression366); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression370);
                    op1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression374);
                    op2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==1 ) {
                       result = new PlusExpression(op1, op2);	
                    }

                    }
                    break;
                case 2 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:164:4: ^( '-' op1= expression op2= expression )
                    {
                    match(input,32,FOLLOW_32_in_expression383); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression387);
                    op1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression391);
                    op2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==1 ) {
                       result = new MinusExpression(op1, op2); 
                    }

                    }
                    break;
                case 3 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:165:4: ^( '*' op1= expression op2= expression )
                    {
                    match(input,33,FOLLOW_33_in_expression400); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression404);
                    op1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression408);
                    op2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==1 ) {
                       result = new MultiplicationExpression(op1, op2); 
                    }

                    }
                    break;
                case 4 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:166:4: ^( '/' op1= expression op2= expression )
                    {
                    match(input,34,FOLLOW_34_in_expression417); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression421);
                    op1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression425);
                    op2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==1 ) {
                       result = new DivisionExpression(op1, op1); 
                    }

                    }
                    break;
                case 5 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:167:4: ^( '%' op1= expression op2= expression )
                    {
                    match(input,35,FOLLOW_35_in_expression434); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression438);
                    op1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression442);
                    op2=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==1 ) {
                       result = new ModulusExpression(op1, op2); 
                    }

                    }
                    break;
                case 6 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:168:4: ^( NEGATION e= expression )
                    {
                    match(input,NEGATION,FOLLOW_NEGATION_in_expression451); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression455);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return result;

                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==1 ) {
                       result = new NegationExpression(e); 
                    }

                    }
                    break;
                case 7 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:169:4: IDENT
                    {
                    IDENT5=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_expression463); if (state.failed) return result;
                    if ( state.backtracking==1 ) {

                      			result = new IdentExpression((IDENT5!=null?IDENT5.getText():null));
                      		
                    }

                    }
                    break;
                case 8 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:172:4: INTEGER
                    {
                    INTEGER6=(CommonTree)match(input,INTEGER,FOLLOW_INTEGER_in_expression470); if (state.failed) return result;
                    if ( state.backtracking==1 ) {
                       
                      			result = new WrappedPrimitiveExpression(Integer.parseInt((INTEGER6!=null?INTEGER6.getText():null))); 
                      		
                    }

                    }
                    break;
                case 9 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:175:4: STRING_LITERAL
                    {
                    STRING_LITERAL7=(CommonTree)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_expression477); if (state.failed) return result;
                    if ( state.backtracking==1 ) {

                      			result = new WrappedPrimitiveExpression((STRING_LITERAL7!=null?STRING_LITERAL7.getText():null));
                      		
                    }

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

    // Delegated rules


 

    public static final BitSet FOLLOW_IDENT_in_parameters79 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_IDENT_in_parameters82 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_enterFunction_in_topdown97 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_beginParameters_in_topdown102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignFunctionDown_in_topdown107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_codeStatement_in_topdown112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exitFunction_in_bottomup123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_endParameters_in_bottomup128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignFunctionUp_in_bottomup133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_assignFunctionDown146 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_INIT_FUNCTION_in_assignFunctionDown148 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_assignFunctionDown150 = new BitSet(new long[]{0x000007FFFFFFFFF8L});
    public static final BitSet FOLLOW_23_in_assignFunctionUp169 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_INIT_FUNCTION_in_assignFunctionUp171 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_assignFunctionUp173 = new BitSet(new long[]{0x000007FFFFFFFFF8L});
    public static final BitSet FOLLOW_FUNCTION_BODY_in_enterFunction192 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_FUNCTION_BODY_in_exitFunction211 = new BitSet(new long[]{0x000007FFFFFFFFF2L});
    public static final BitSet FOLLOW_FUNCTION_PARAMETERS_in_beginParameters230 = new BitSet(new long[]{0x000007FFFFFFFFF0L});
    public static final BitSet FOLLOW_IDENT_in_beginParameters239 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_FUNCTION_PARAMETERS_in_endParameters260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_printStatement_in_codeStatement275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_initVariableStatement_in_codeStatement280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_updateVariableStatement_in_codeStatement285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_printStatement298 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_printStatement302 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INIT_VARIABLE_in_initVariableStatement318 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_initVariableStatement320 = new BitSet(new long[]{0x0000000F80001C10L});
    public static final BitSet FOLLOW_expression_in_initVariableStatement324 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UPDATE_VARIABLE_in_updateVariableStatement339 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_updateVariableStatement341 = new BitSet(new long[]{0x0000000F80001C10L});
    public static final BitSet FOLLOW_expression_in_updateVariableStatement345 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_31_in_expression366 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression370 = new BitSet(new long[]{0x0000000F80001C10L});
    public static final BitSet FOLLOW_expression_in_expression374 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_32_in_expression383 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression387 = new BitSet(new long[]{0x0000000F80001C10L});
    public static final BitSet FOLLOW_expression_in_expression391 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_33_in_expression400 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression404 = new BitSet(new long[]{0x0000000F80001C10L});
    public static final BitSet FOLLOW_expression_in_expression408 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_34_in_expression417 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression421 = new BitSet(new long[]{0x0000000F80001C10L});
    public static final BitSet FOLLOW_expression_in_expression425 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_35_in_expression434 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression438 = new BitSet(new long[]{0x0000000F80001C10L});
    public static final BitSet FOLLOW_expression_in_expression442 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NEGATION_in_expression451 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression455 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IDENT_in_expression463 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_expression470 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_expression477 = new BitSet(new long[]{0x0000000000000002L});

}