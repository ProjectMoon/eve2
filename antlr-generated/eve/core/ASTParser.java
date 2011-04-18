// $ANTLR 3.3 Nov 30, 2010 12:50:56 /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g 2011-04-18 16:59:35

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NEGATION", "INIT_VARIABLE", "UPDATE_VARIABLE", "INIT_FUNCTION", "FUNCTION_PARAMETERS", "FUNCTION_BODY", "INVOKE_FUNCTION_STMT", "INVOKE_FUNCTION_EXPR", "IDENT", "INTEGER", "STRING_LITERAL", "MULTILINE_COMMENT", "CHAR_LITERAL", "LETTER", "DIGIT", "WS", "COMMENT", "'print'", "'('", "')'", "';'", "'='", "'def'", "'var'", "'proto'", "'{'", "'}'", "','", "'not'", "'+'", "'-'", "'*'", "'/'", "'%'", "'/='", "'<'", "'<='", "'>='", "'>'", "'and'", "'or'"
    };
    public static final int EOF=-1;
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
    public static final int T__43=43;
    public static final int T__44=44;
    public static final int NEGATION=4;
    public static final int INIT_VARIABLE=5;
    public static final int UPDATE_VARIABLE=6;
    public static final int INIT_FUNCTION=7;
    public static final int FUNCTION_PARAMETERS=8;
    public static final int FUNCTION_BODY=9;
    public static final int INVOKE_FUNCTION_STMT=10;
    public static final int INVOKE_FUNCTION_EXPR=11;
    public static final int IDENT=12;
    public static final int INTEGER=13;
    public static final int STRING_LITERAL=14;
    public static final int MULTILINE_COMMENT=15;
    public static final int CHAR_LITERAL=16;
    public static final int LETTER=17;
    public static final int DIGIT=18;
    public static final int WS=19;
    public static final int COMMENT=20;

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
    	



    // $ANTLR start "parameters"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:57:1: parameters returns [List<String> result] : IDENT ( IDENT )* ;
    public final List<String> parameters() throws RecognitionException {
        List<String> result = null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:58:2: ( IDENT ( IDENT )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:58:4: IDENT ( IDENT )*
            {
            match(input,IDENT,FOLLOW_IDENT_in_parameters79); if (state.failed) return result;
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:58:10: ( IDENT )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==IDENT) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:58:11: IDENT
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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:61:1: topdown : ( enterFunction | beginParameters | assignFunctionDown | codeStatement );
    public final void topdown() throws RecognitionException {
        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:62:2: ( enterFunction | beginParameters | assignFunctionDown | codeStatement )
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
            case 25:
                {
                alt2=3;
                }
                break;
            case INIT_VARIABLE:
            case UPDATE_VARIABLE:
            case INVOKE_FUNCTION_STMT:
            case 21:
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
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:62:4: enterFunction
                    {
                    pushFollow(FOLLOW_enterFunction_in_topdown97);
                    enterFunction();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:63:4: beginParameters
                    {
                    pushFollow(FOLLOW_beginParameters_in_topdown102);
                    beginParameters();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:64:4: assignFunctionDown
                    {
                    pushFollow(FOLLOW_assignFunctionDown_in_topdown107);
                    assignFunctionDown();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:65:4: codeStatement
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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:68:1: bottomup : ( exitFunction | endParameters | assignFunctionUp );
    public final void bottomup() throws RecognitionException {
        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:69:2: ( exitFunction | endParameters | assignFunctionUp )
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
            case 25:
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
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:69:4: exitFunction
                    {
                    pushFollow(FOLLOW_exitFunction_in_bottomup123);
                    exitFunction();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:70:4: endParameters
                    {
                    pushFollow(FOLLOW_endParameters_in_bottomup128);
                    endParameters();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:71:4: assignFunctionUp
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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:75:1: assignFunctionDown : ^( '=' INIT_FUNCTION IDENT ( . )* ) ;
    public final void assignFunctionDown() throws RecognitionException {
        CommonTree IDENT1=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:76:2: ( ^( '=' INIT_FUNCTION IDENT ( . )* ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:76:4: ^( '=' INIT_FUNCTION IDENT ( . )* )
            {
            match(input,25,FOLLOW_25_in_assignFunctionDown147); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            match(input,INIT_FUNCTION,FOLLOW_INIT_FUNCTION_in_assignFunctionDown149); if (state.failed) return ;
            IDENT1=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_assignFunctionDown151); if (state.failed) return ;
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:76:30: ( . )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>=NEGATION && LA4_0<=44)) ) {
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
              		currentFuncExpr = new FunctionDefExpression();
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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:85:1: assignFunctionUp : ^( '=' INIT_FUNCTION IDENT ( . )* ) ;
    public final void assignFunctionUp() throws RecognitionException {
        CommonTree IDENT2=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:86:2: ( ^( '=' INIT_FUNCTION IDENT ( . )* ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:86:4: ^( '=' INIT_FUNCTION IDENT ( . )* )
            {
            match(input,25,FOLLOW_25_in_assignFunctionUp170); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            match(input,INIT_FUNCTION,FOLLOW_INIT_FUNCTION_in_assignFunctionUp172); if (state.failed) return ;
            IDENT2=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_assignFunctionUp174); if (state.failed) return ;
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:86:30: ( . )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>=NEGATION && LA5_0<=44)) ) {
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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:96:1: enterFunction : ^( FUNCTION_BODY ( . )* ) ;
    public final void enterFunction() throws RecognitionException {
        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:97:2: ( ^( FUNCTION_BODY ( . )* ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:97:4: ^( FUNCTION_BODY ( . )* )
            {
            match(input,FUNCTION_BODY,FOLLOW_FUNCTION_BODY_in_enterFunction193); if (state.failed) return ;

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); if (state.failed) return ;
                // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:97:20: ( . )*
                loop6:
                do {
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( ((LA6_0>=NEGATION && LA6_0<=44)) ) {
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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:104:1: exitFunction : ( FUNCTION_BODY ( . )* ) ;
    public final void exitFunction() throws RecognitionException {
        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:105:2: ( ( FUNCTION_BODY ( . )* ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:105:4: ( FUNCTION_BODY ( . )* )
            {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:105:4: ( FUNCTION_BODY ( . )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:105:5: FUNCTION_BODY ( . )*
            {
            match(input,FUNCTION_BODY,FOLLOW_FUNCTION_BODY_in_exitFunction212); if (state.failed) return ;
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:105:19: ( . )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>=NEGATION && LA7_0<=44)) ) {
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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:114:1: beginParameters : ( FUNCTION_PARAMETERS type= . (s= IDENT )* ) ;
    public final void beginParameters() throws RecognitionException {
        CommonTree s=null;
        CommonTree type=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:115:2: ( ( FUNCTION_PARAMETERS type= . (s= IDENT )* ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:115:4: ( FUNCTION_PARAMETERS type= . (s= IDENT )* )
            {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:115:4: ( FUNCTION_PARAMETERS type= . (s= IDENT )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:115:5: FUNCTION_PARAMETERS type= . (s= IDENT )*
            {
            match(input,FUNCTION_PARAMETERS,FOLLOW_FUNCTION_PARAMETERS_in_beginParameters231); if (state.failed) return ;
            type=(CommonTree)input.LT(1);
            matchAny(input); if (state.failed) return ;
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:115:32: (s= IDENT )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==IDENT) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:115:33: s= IDENT
            	    {
            	    s=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_beginParameters240); if (state.failed) return ;
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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:122:1: endParameters : ( FUNCTION_PARAMETERS ) ;
    public final void endParameters() throws RecognitionException {
        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:123:2: ( ( FUNCTION_PARAMETERS ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:123:4: ( FUNCTION_PARAMETERS )
            {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:123:4: ( FUNCTION_PARAMETERS )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:123:5: FUNCTION_PARAMETERS
            {
            match(input,FUNCTION_PARAMETERS,FOLLOW_FUNCTION_PARAMETERS_in_endParameters261); if (state.failed) return ;

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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:129:1: codeStatement : ( printStatement | initVariableStatement | updateVariableStatement | invokeFunctionStatement );
    public final void codeStatement() throws RecognitionException {
        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:130:2: ( printStatement | initVariableStatement | updateVariableStatement | invokeFunctionStatement )
            int alt9=4;
            switch ( input.LA(1) ) {
            case 21:
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
            case INVOKE_FUNCTION_STMT:
                {
                alt9=4;
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
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:130:4: printStatement
                    {
                    pushFollow(FOLLOW_printStatement_in_codeStatement276);
                    printStatement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:131:4: initVariableStatement
                    {
                    pushFollow(FOLLOW_initVariableStatement_in_codeStatement281);
                    initVariableStatement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:132:4: updateVariableStatement
                    {
                    pushFollow(FOLLOW_updateVariableStatement_in_codeStatement286);
                    updateVariableStatement();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:133:4: invokeFunctionStatement
                    {
                    pushFollow(FOLLOW_invokeFunctionStatement_in_codeStatement291);
                    invokeFunctionStatement();

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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:136:1: printStatement : ^( 'print' e= expression ) ;
    public final void printStatement() throws RecognitionException {
        ExpressionStatement e = null;


        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:137:2: ( ^( 'print' e= expression ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:137:4: ^( 'print' e= expression )
            {
            match(input,21,FOLLOW_21_in_printStatement304); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            pushFollow(FOLLOW_expression_in_printStatement308);
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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:149:1: initVariableStatement : ^( INIT_VARIABLE IDENT e= expression ) ;
    public final void initVariableStatement() throws RecognitionException {
        CommonTree IDENT3=null;
        ExpressionStatement e = null;


        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:150:2: ( ^( INIT_VARIABLE IDENT e= expression ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:150:4: ^( INIT_VARIABLE IDENT e= expression )
            {
            match(input,INIT_VARIABLE,FOLLOW_INIT_VARIABLE_in_initVariableStatement324); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            IDENT3=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_initVariableStatement326); if (state.failed) return ;
            pushFollow(FOLLOW_expression_in_initVariableStatement330);
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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:162:1: updateVariableStatement : ^( UPDATE_VARIABLE IDENT e= expression ) ;
    public final void updateVariableStatement() throws RecognitionException {
        CommonTree IDENT4=null;
        ExpressionStatement e = null;


        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:163:2: ( ^( UPDATE_VARIABLE IDENT e= expression ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:163:4: ^( UPDATE_VARIABLE IDENT e= expression )
            {
            match(input,UPDATE_VARIABLE,FOLLOW_UPDATE_VARIABLE_in_updateVariableStatement345); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            IDENT4=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_updateVariableStatement347); if (state.failed) return ;
            pushFollow(FOLLOW_expression_in_updateVariableStatement351);
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


    // $ANTLR start "invokeFunctionStatement"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:175:1: invokeFunctionStatement : ^( INVOKE_FUNCTION_STMT IDENT (e= expression )* ) ;
    public final void invokeFunctionStatement() throws RecognitionException {
        CommonTree IDENT5=null;
        ExpressionStatement e = null;


        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:176:2: ( ^( INVOKE_FUNCTION_STMT IDENT (e= expression )* ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:176:4: ^( INVOKE_FUNCTION_STMT IDENT (e= expression )* )
            {
            match(input,INVOKE_FUNCTION_STMT,FOLLOW_INVOKE_FUNCTION_STMT_in_invokeFunctionStatement367); if (state.failed) return ;

            match(input, Token.DOWN, null); if (state.failed) return ;
            IDENT5=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_invokeFunctionStatement369); if (state.failed) return ;
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:176:33: (e= expression )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==NEGATION||(LA10_0>=INVOKE_FUNCTION_EXPR && LA10_0<=STRING_LITERAL)||(LA10_0>=33 && LA10_0<=37)) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:176:34: e= expression
            	    {
            	    pushFollow(FOLLOW_expression_in_invokeFunctionStatement374);
            	    e=expression();

            	    state._fsp--;
            	    if (state.failed) return ;
            	    if ( state.backtracking==1 ) {
            	       pushFunctionInvocationParam(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);


            match(input, Token.UP, null); if (state.failed) return ;
            if ( state.backtracking==1 ) {

              			//System.out.println("invoke function " + (IDENT5!=null?IDENT5.getText():null));
              			
              			//$result = new FunctionInvokeExpression((IDENT5!=null?IDENT5.getText():null), getFunctionInvocationParams());
              		
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
    // $ANTLR end "invokeFunctionStatement"


    // $ANTLR start "expression"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:184:1: expression returns [ExpressionStatement result] : ( ^( '+' op1= expression op2= expression ) | ^( '-' op1= expression op2= expression ) | ^( '*' op1= expression op2= expression ) | ^( '/' op1= expression op2= expression ) | ^( '%' op1= expression op2= expression ) | ^( NEGATION e= expression ) | ^( INVOKE_FUNCTION_EXPR IDENT (e= expression )* ) | IDENT | INTEGER | STRING_LITERAL );
    public final ExpressionStatement expression() throws RecognitionException {
        ExpressionStatement result = null;

        CommonTree IDENT6=null;
        CommonTree IDENT7=null;
        CommonTree INTEGER8=null;
        CommonTree STRING_LITERAL9=null;
        ExpressionStatement op1 = null;

        ExpressionStatement op2 = null;

        ExpressionStatement e = null;


        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:185:2: ( ^( '+' op1= expression op2= expression ) | ^( '-' op1= expression op2= expression ) | ^( '*' op1= expression op2= expression ) | ^( '/' op1= expression op2= expression ) | ^( '%' op1= expression op2= expression ) | ^( NEGATION e= expression ) | ^( INVOKE_FUNCTION_EXPR IDENT (e= expression )* ) | IDENT | INTEGER | STRING_LITERAL )
            int alt12=10;
            switch ( input.LA(1) ) {
            case 33:
                {
                alt12=1;
                }
                break;
            case 34:
                {
                alt12=2;
                }
                break;
            case 35:
                {
                alt12=3;
                }
                break;
            case 36:
                {
                alt12=4;
                }
                break;
            case 37:
                {
                alt12=5;
                }
                break;
            case NEGATION:
                {
                alt12=6;
                }
                break;
            case INVOKE_FUNCTION_EXPR:
                {
                alt12=7;
                }
                break;
            case IDENT:
                {
                alt12=8;
                }
                break;
            case INTEGER:
                {
                alt12=9;
                }
                break;
            case STRING_LITERAL:
                {
                alt12=10;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return result;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:185:4: ^( '+' op1= expression op2= expression )
                    {
                    match(input,33,FOLLOW_33_in_expression399); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression403);
                    op1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression407);
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
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:186:4: ^( '-' op1= expression op2= expression )
                    {
                    match(input,34,FOLLOW_34_in_expression416); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression420);
                    op1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression424);
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
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:187:4: ^( '*' op1= expression op2= expression )
                    {
                    match(input,35,FOLLOW_35_in_expression433); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression437);
                    op1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression441);
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
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:188:4: ^( '/' op1= expression op2= expression )
                    {
                    match(input,36,FOLLOW_36_in_expression450); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression454);
                    op1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression458);
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
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:189:4: ^( '%' op1= expression op2= expression )
                    {
                    match(input,37,FOLLOW_37_in_expression467); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression471);
                    op1=expression();

                    state._fsp--;
                    if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression475);
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
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:190:4: ^( NEGATION e= expression )
                    {
                    match(input,NEGATION,FOLLOW_NEGATION_in_expression484); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    pushFollow(FOLLOW_expression_in_expression488);
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
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:191:4: ^( INVOKE_FUNCTION_EXPR IDENT (e= expression )* )
                    {
                    match(input,INVOKE_FUNCTION_EXPR,FOLLOW_INVOKE_FUNCTION_EXPR_in_expression497); if (state.failed) return result;

                    match(input, Token.DOWN, null); if (state.failed) return result;
                    IDENT6=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_expression499); if (state.failed) return result;
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:191:33: (e= expression )*
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( (LA11_0==NEGATION||(LA11_0>=INVOKE_FUNCTION_EXPR && LA11_0<=STRING_LITERAL)||(LA11_0>=33 && LA11_0<=37)) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:191:34: e= expression
                    	    {
                    	    pushFollow(FOLLOW_expression_in_expression504);
                    	    e=expression();

                    	    state._fsp--;
                    	    if (state.failed) return result;
                    	    if ( state.backtracking==1 ) {
                    	       pushFunctionInvocationParam(e); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop11;
                        }
                    } while (true);


                    match(input, Token.UP, null); if (state.failed) return result;
                    if ( state.backtracking==1 ) {

                      			List<ExpressionStatement> params = getFunctionInvocationParams();
                      			System.out.println("invoking function " + (IDENT6!=null?IDENT6.getText():null) + " as expression with params " + params);
                      			result = new FunctionInvokeExpression((IDENT6!=null?IDENT6.getText():null), params);
                      		
                    }

                    }
                    break;
                case 8 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:196:4: IDENT
                    {
                    IDENT7=(CommonTree)match(input,IDENT,FOLLOW_IDENT_in_expression516); if (state.failed) return result;
                    if ( state.backtracking==1 ) {

                      			result = new IdentExpression((IDENT7!=null?IDENT7.getText():null));
                      		
                    }

                    }
                    break;
                case 9 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:199:4: INTEGER
                    {
                    INTEGER8=(CommonTree)match(input,INTEGER,FOLLOW_INTEGER_in_expression523); if (state.failed) return result;
                    if ( state.backtracking==1 ) {
                       
                      			result = new WrappedPrimitiveExpression(Integer.parseInt((INTEGER8!=null?INTEGER8.getText():null))); 
                      		
                    }

                    }
                    break;
                case 10 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/ASTParser.g:202:4: STRING_LITERAL
                    {
                    STRING_LITERAL9=(CommonTree)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_expression530); if (state.failed) return result;
                    if ( state.backtracking==1 ) {

                      			result = new WrappedPrimitiveExpression((STRING_LITERAL9!=null?STRING_LITERAL9.getText():null));
                      		
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


 

    public static final BitSet FOLLOW_IDENT_in_parameters79 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_IDENT_in_parameters82 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_enterFunction_in_topdown97 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_beginParameters_in_topdown102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignFunctionDown_in_topdown107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_codeStatement_in_topdown112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exitFunction_in_bottomup123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_endParameters_in_bottomup128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignFunctionUp_in_bottomup133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_assignFunctionDown147 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_INIT_FUNCTION_in_assignFunctionDown149 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_IDENT_in_assignFunctionDown151 = new BitSet(new long[]{0x00001FFFFFFFFFF8L});
    public static final BitSet FOLLOW_25_in_assignFunctionUp170 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_INIT_FUNCTION_in_assignFunctionUp172 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_IDENT_in_assignFunctionUp174 = new BitSet(new long[]{0x00001FFFFFFFFFF8L});
    public static final BitSet FOLLOW_FUNCTION_BODY_in_enterFunction193 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_FUNCTION_BODY_in_exitFunction212 = new BitSet(new long[]{0x00001FFFFFFFFFF2L});
    public static final BitSet FOLLOW_FUNCTION_PARAMETERS_in_beginParameters231 = new BitSet(new long[]{0x00001FFFFFFFFFF0L});
    public static final BitSet FOLLOW_IDENT_in_beginParameters240 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_FUNCTION_PARAMETERS_in_endParameters261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_printStatement_in_codeStatement276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_initVariableStatement_in_codeStatement281 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_updateVariableStatement_in_codeStatement286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_invokeFunctionStatement_in_codeStatement291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_printStatement304 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_printStatement308 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INIT_VARIABLE_in_initVariableStatement324 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_initVariableStatement326 = new BitSet(new long[]{0x0000003E00007810L});
    public static final BitSet FOLLOW_expression_in_initVariableStatement330 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UPDATE_VARIABLE_in_updateVariableStatement345 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_updateVariableStatement347 = new BitSet(new long[]{0x0000003E00007810L});
    public static final BitSet FOLLOW_expression_in_updateVariableStatement351 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INVOKE_FUNCTION_STMT_in_invokeFunctionStatement367 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_invokeFunctionStatement369 = new BitSet(new long[]{0x0000003E00007818L});
    public static final BitSet FOLLOW_expression_in_invokeFunctionStatement374 = new BitSet(new long[]{0x0000003E00007818L});
    public static final BitSet FOLLOW_33_in_expression399 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression403 = new BitSet(new long[]{0x0000003E00007810L});
    public static final BitSet FOLLOW_expression_in_expression407 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_34_in_expression416 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression420 = new BitSet(new long[]{0x0000003E00007810L});
    public static final BitSet FOLLOW_expression_in_expression424 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_35_in_expression433 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression437 = new BitSet(new long[]{0x0000003E00007810L});
    public static final BitSet FOLLOW_expression_in_expression441 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_36_in_expression450 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression454 = new BitSet(new long[]{0x0000003E00007810L});
    public static final BitSet FOLLOW_expression_in_expression458 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_37_in_expression467 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression471 = new BitSet(new long[]{0x0000003E00007810L});
    public static final BitSet FOLLOW_expression_in_expression475 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NEGATION_in_expression484 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression488 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INVOKE_FUNCTION_EXPR_in_expression497 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_expression499 = new BitSet(new long[]{0x0000003E00007818L});
    public static final BitSet FOLLOW_expression_in_expression504 = new BitSet(new long[]{0x0000003E00007818L});
    public static final BitSet FOLLOW_IDENT_in_expression516 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_expression523 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_expression530 = new BitSet(new long[]{0x0000000000000002L});

}