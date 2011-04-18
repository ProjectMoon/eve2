// $ANTLR 3.3 Nov 30, 2010 12:50:56 /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g 2011-04-18 12:26:07

	package eve.core;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class EveParser extends Parser {
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


        public EveParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public EveParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return EveParser.tokenNames; }
    public String getGrammarFileName() { return "/home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g"; }


    public static class program_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "program"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:29:1: program : ( statement )* ;
    public final EveParser.program_return program() throws RecognitionException {
        EveParser.program_return retval = new EveParser.program_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        EveParser.statement_return statement1 = null;



        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:30:2: ( ( statement )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:30:4: ( statement )*
            {
            root_0 = (CommonTree)adaptor.nil();

            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:30:4: ( statement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==IDENT||LA1_0==19||(LA1_0>=24 && LA1_0<=26)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:30:4: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_program88);
            	    statement1=statement();

            	    state._fsp--;

            	    adaptor.addChild(root_0, statement1.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "program"

    public static class statement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statement"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:34:1: statement : ( codeStatement | protoStatement );
    public final EveParser.statement_return statement() throws RecognitionException {
        EveParser.statement_return retval = new EveParser.statement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        EveParser.codeStatement_return codeStatement2 = null;

        EveParser.protoStatement_return protoStatement3 = null;



        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:35:2: ( codeStatement | protoStatement )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==IDENT||LA2_0==19||(LA2_0>=24 && LA2_0<=25)) ) {
                alt2=1;
            }
            else if ( (LA2_0==26) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:35:4: codeStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_codeStatement_in_statement101);
                    codeStatement2=codeStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, codeStatement2.getTree());

                    }
                    break;
                case 2 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:36:4: protoStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_protoStatement_in_statement106);
                    protoStatement3=protoStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, protoStatement3.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "statement"

    public static class codeStatement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "codeStatement"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:39:1: codeStatement : ( printStatement | assignmentStatement | initVariableStatement );
    public final EveParser.codeStatement_return codeStatement() throws RecognitionException {
        EveParser.codeStatement_return retval = new EveParser.codeStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        EveParser.printStatement_return printStatement4 = null;

        EveParser.assignmentStatement_return assignmentStatement5 = null;

        EveParser.initVariableStatement_return initVariableStatement6 = null;



        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:40:2: ( printStatement | assignmentStatement | initVariableStatement )
            int alt3=3;
            switch ( input.LA(1) ) {
            case 19:
                {
                alt3=1;
                }
                break;
            case IDENT:
            case 24:
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
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:40:4: printStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_printStatement_in_codeStatement119);
                    printStatement4=printStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, printStatement4.getTree());

                    }
                    break;
                case 2 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:41:4: assignmentStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_assignmentStatement_in_codeStatement124);
                    assignmentStatement5=assignmentStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, assignmentStatement5.getTree());

                    }
                    break;
                case 3 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:42:4: initVariableStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_initVariableStatement_in_codeStatement129);
                    initVariableStatement6=initVariableStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, initVariableStatement6.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "codeStatement"

    public static class printStatement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "printStatement"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:45:1: printStatement : 'print' '(' expression ')' ';' ;
    public final EveParser.printStatement_return printStatement() throws RecognitionException {
        EveParser.printStatement_return retval = new EveParser.printStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal7=null;
        Token char_literal8=null;
        Token char_literal10=null;
        Token char_literal11=null;
        EveParser.expression_return expression9 = null;


        CommonTree string_literal7_tree=null;
        CommonTree char_literal8_tree=null;
        CommonTree char_literal10_tree=null;
        CommonTree char_literal11_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:46:2: ( 'print' '(' expression ')' ';' )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:46:4: 'print' '(' expression ')' ';'
            {
            root_0 = (CommonTree)adaptor.nil();

            string_literal7=(Token)match(input,19,FOLLOW_19_in_printStatement141); 
            string_literal7_tree = (CommonTree)adaptor.create(string_literal7);
            root_0 = (CommonTree)adaptor.becomeRoot(string_literal7_tree, root_0);

            char_literal8=(Token)match(input,20,FOLLOW_20_in_printStatement144); 
            pushFollow(FOLLOW_expression_in_printStatement147);
            expression9=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression9.getTree());
            char_literal10=(Token)match(input,21,FOLLOW_21_in_printStatement149); 
            char_literal11=(Token)match(input,22,FOLLOW_22_in_printStatement152); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "printStatement"

    public static class assignmentStatement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "assignmentStatement"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:49:1: assignmentStatement : ( IDENT '=' expression ';' -> ^( UPDATE_VARIABLE IDENT expression ) | initFunction IDENT '=' function ( ';' )? );
    public final EveParser.assignmentStatement_return assignmentStatement() throws RecognitionException {
        EveParser.assignmentStatement_return retval = new EveParser.assignmentStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENT12=null;
        Token char_literal13=null;
        Token char_literal15=null;
        Token IDENT17=null;
        Token char_literal18=null;
        Token char_literal20=null;
        EveParser.expression_return expression14 = null;

        EveParser.initFunction_return initFunction16 = null;

        EveParser.function_return function19 = null;


        CommonTree IDENT12_tree=null;
        CommonTree char_literal13_tree=null;
        CommonTree char_literal15_tree=null;
        CommonTree IDENT17_tree=null;
        CommonTree char_literal18_tree=null;
        CommonTree char_literal20_tree=null;
        RewriteRuleTokenStream stream_IDENT=new RewriteRuleTokenStream(adaptor,"token IDENT");
        RewriteRuleTokenStream stream_22=new RewriteRuleTokenStream(adaptor,"token 22");
        RewriteRuleTokenStream stream_23=new RewriteRuleTokenStream(adaptor,"token 23");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:50:2: ( IDENT '=' expression ';' -> ^( UPDATE_VARIABLE IDENT expression ) | initFunction IDENT '=' function ( ';' )? )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==IDENT) ) {
                alt5=1;
            }
            else if ( (LA5_0==24) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:50:4: IDENT '=' expression ';'
                    {
                    IDENT12=(Token)match(input,IDENT,FOLLOW_IDENT_in_assignmentStatement165);  
                    stream_IDENT.add(IDENT12);

                    char_literal13=(Token)match(input,23,FOLLOW_23_in_assignmentStatement167);  
                    stream_23.add(char_literal13);

                    pushFollow(FOLLOW_expression_in_assignmentStatement169);
                    expression14=expression();

                    state._fsp--;

                    stream_expression.add(expression14.getTree());
                    char_literal15=(Token)match(input,22,FOLLOW_22_in_assignmentStatement171);  
                    stream_22.add(char_literal15);



                    // AST REWRITE
                    // elements: expression, IDENT
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 50:29: -> ^( UPDATE_VARIABLE IDENT expression )
                    {
                        // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:50:32: ^( UPDATE_VARIABLE IDENT expression )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(UPDATE_VARIABLE, "UPDATE_VARIABLE"), root_1);

                        adaptor.addChild(root_1, stream_IDENT.nextNode());
                        adaptor.addChild(root_1, stream_expression.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:51:4: initFunction IDENT '=' function ( ';' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_initFunction_in_assignmentStatement186);
                    initFunction16=initFunction();

                    state._fsp--;

                    adaptor.addChild(root_0, initFunction16.getTree());
                    IDENT17=(Token)match(input,IDENT,FOLLOW_IDENT_in_assignmentStatement188); 
                    IDENT17_tree = (CommonTree)adaptor.create(IDENT17);
                    adaptor.addChild(root_0, IDENT17_tree);

                    char_literal18=(Token)match(input,23,FOLLOW_23_in_assignmentStatement190); 
                    char_literal18_tree = (CommonTree)adaptor.create(char_literal18);
                    root_0 = (CommonTree)adaptor.becomeRoot(char_literal18_tree, root_0);

                    pushFollow(FOLLOW_function_in_assignmentStatement193);
                    function19=function();

                    state._fsp--;

                    adaptor.addChild(root_0, function19.getTree());
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:51:37: ( ';' )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0==22) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:51:37: ';'
                            {
                            char_literal20=(Token)match(input,22,FOLLOW_22_in_assignmentStatement195); 
                            char_literal20_tree = (CommonTree)adaptor.create(char_literal20);
                            adaptor.addChild(root_0, char_literal20_tree);


                            }
                            break;

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "assignmentStatement"

    public static class initFunction_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "initFunction"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:54:1: initFunction : 'def' -> INIT_FUNCTION ;
    public final EveParser.initFunction_return initFunction() throws RecognitionException {
        EveParser.initFunction_return retval = new EveParser.initFunction_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal21=null;

        CommonTree string_literal21_tree=null;
        RewriteRuleTokenStream stream_24=new RewriteRuleTokenStream(adaptor,"token 24");

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:55:2: ( 'def' -> INIT_FUNCTION )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:55:4: 'def'
            {
            string_literal21=(Token)match(input,24,FOLLOW_24_in_initFunction207);  
            stream_24.add(string_literal21);



            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 55:10: -> INIT_FUNCTION
            {
                adaptor.addChild(root_0, (CommonTree)adaptor.create(INIT_FUNCTION, "INIT_FUNCTION"));

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "initFunction"

    public static class initVariableStatement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "initVariableStatement"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:58:1: initVariableStatement : initVariable IDENT '=' expression ';' ;
    public final EveParser.initVariableStatement_return initVariableStatement() throws RecognitionException {
        EveParser.initVariableStatement_return retval = new EveParser.initVariableStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENT23=null;
        Token char_literal24=null;
        Token char_literal26=null;
        EveParser.initVariable_return initVariable22 = null;

        EveParser.expression_return expression25 = null;


        CommonTree IDENT23_tree=null;
        CommonTree char_literal24_tree=null;
        CommonTree char_literal26_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:59:2: ( initVariable IDENT '=' expression ';' )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:59:4: initVariable IDENT '=' expression ';'
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_initVariable_in_initVariableStatement223);
            initVariable22=initVariable();

            state._fsp--;

            root_0 = (CommonTree)adaptor.becomeRoot(initVariable22.getTree(), root_0);
            IDENT23=(Token)match(input,IDENT,FOLLOW_IDENT_in_initVariableStatement226); 
            IDENT23_tree = (CommonTree)adaptor.create(IDENT23);
            adaptor.addChild(root_0, IDENT23_tree);

            char_literal24=(Token)match(input,23,FOLLOW_23_in_initVariableStatement228); 
            pushFollow(FOLLOW_expression_in_initVariableStatement231);
            expression25=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression25.getTree());
            char_literal26=(Token)match(input,22,FOLLOW_22_in_initVariableStatement233); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "initVariableStatement"

    public static class initVariable_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "initVariable"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:62:1: initVariable : 'var' -> INIT_VARIABLE ;
    public final EveParser.initVariable_return initVariable() throws RecognitionException {
        EveParser.initVariable_return retval = new EveParser.initVariable_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal27=null;

        CommonTree string_literal27_tree=null;
        RewriteRuleTokenStream stream_25=new RewriteRuleTokenStream(adaptor,"token 25");

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:63:2: ( 'var' -> INIT_VARIABLE )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:63:4: 'var'
            {
            string_literal27=(Token)match(input,25,FOLLOW_25_in_initVariable245);  
            stream_25.add(string_literal27);



            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 63:10: -> INIT_VARIABLE
            {
                adaptor.addChild(root_0, (CommonTree)adaptor.create(INIT_VARIABLE, "INIT_VARIABLE"));

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "initVariable"

    public static class protoStatement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "protoStatement"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:66:1: protoStatement : 'proto' IDENT '{' ( protoBlock )* '}' ;
    public final EveParser.protoStatement_return protoStatement() throws RecognitionException {
        EveParser.protoStatement_return retval = new EveParser.protoStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal28=null;
        Token IDENT29=null;
        Token char_literal30=null;
        Token char_literal32=null;
        EveParser.protoBlock_return protoBlock31 = null;


        CommonTree string_literal28_tree=null;
        CommonTree IDENT29_tree=null;
        CommonTree char_literal30_tree=null;
        CommonTree char_literal32_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:67:2: ( 'proto' IDENT '{' ( protoBlock )* '}' )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:67:4: 'proto' IDENT '{' ( protoBlock )* '}'
            {
            root_0 = (CommonTree)adaptor.nil();

            string_literal28=(Token)match(input,26,FOLLOW_26_in_protoStatement261); 
            string_literal28_tree = (CommonTree)adaptor.create(string_literal28);
            root_0 = (CommonTree)adaptor.becomeRoot(string_literal28_tree, root_0);

            IDENT29=(Token)match(input,IDENT,FOLLOW_IDENT_in_protoStatement264); 
            IDENT29_tree = (CommonTree)adaptor.create(IDENT29);
            adaptor.addChild(root_0, IDENT29_tree);

            char_literal30=(Token)match(input,27,FOLLOW_27_in_protoStatement266); 
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:67:24: ( protoBlock )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==IDENT||LA6_0==19||(LA6_0>=24 && LA6_0<=25)) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:67:24: protoBlock
            	    {
            	    pushFollow(FOLLOW_protoBlock_in_protoStatement269);
            	    protoBlock31=protoBlock();

            	    state._fsp--;

            	    adaptor.addChild(root_0, protoBlock31.getTree());

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            char_literal32=(Token)match(input,28,FOLLOW_28_in_protoStatement272); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "protoStatement"

    public static class protoBlock_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "protoBlock"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:70:1: protoBlock : codeStatement ;
    public final EveParser.protoBlock_return protoBlock() throws RecognitionException {
        EveParser.protoBlock_return retval = new EveParser.protoBlock_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        EveParser.codeStatement_return codeStatement33 = null;



        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:71:2: ( codeStatement )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:71:4: codeStatement
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_codeStatement_in_protoBlock285);
            codeStatement33=codeStatement();

            state._fsp--;

            adaptor.addChild(root_0, codeStatement33.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "protoBlock"

    public static class actualParameters_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actualParameters"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:75:1: actualParameters : expression ( ',' expression )* ;
    public final EveParser.actualParameters_return actualParameters() throws RecognitionException {
        EveParser.actualParameters_return retval = new EveParser.actualParameters_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal35=null;
        EveParser.expression_return expression34 = null;

        EveParser.expression_return expression36 = null;


        CommonTree char_literal35_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:76:2: ( expression ( ',' expression )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:76:4: expression ( ',' expression )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_expression_in_actualParameters297);
            expression34=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression34.getTree());
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:76:15: ( ',' expression )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==29) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:76:16: ',' expression
            	    {
            	    char_literal35=(Token)match(input,29,FOLLOW_29_in_actualParameters300); 
            	    char_literal35_tree = (CommonTree)adaptor.create(char_literal35);
            	    adaptor.addChild(root_0, char_literal35_tree);

            	    pushFollow(FOLLOW_expression_in_actualParameters302);
            	    expression36=expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, expression36.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "actualParameters"

    public static class function_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:80:1: function : parameters functionBody ;
    public final EveParser.function_return function() throws RecognitionException {
        EveParser.function_return retval = new EveParser.function_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        EveParser.parameters_return parameters37 = null;

        EveParser.functionBody_return functionBody38 = null;



        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:81:2: ( parameters functionBody )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:81:4: parameters functionBody
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_parameters_in_function318);
            parameters37=parameters();

            state._fsp--;

            adaptor.addChild(root_0, parameters37.getTree());
            pushFollow(FOLLOW_functionBody_in_function322);
            functionBody38=functionBody();

            state._fsp--;

            adaptor.addChild(root_0, functionBody38.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "function"

    public static class functionBody_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "functionBody"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:85:1: functionBody : functionBodyToken ( codeStatement )* '}' ;
    public final EveParser.functionBody_return functionBody() throws RecognitionException {
        EveParser.functionBody_return retval = new EveParser.functionBody_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal41=null;
        EveParser.functionBodyToken_return functionBodyToken39 = null;

        EveParser.codeStatement_return codeStatement40 = null;


        CommonTree char_literal41_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:86:2: ( functionBodyToken ( codeStatement )* '}' )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:86:4: functionBodyToken ( codeStatement )* '}'
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_functionBodyToken_in_functionBody334);
            functionBodyToken39=functionBodyToken();

            state._fsp--;

            root_0 = (CommonTree)adaptor.becomeRoot(functionBodyToken39.getTree(), root_0);
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:86:23: ( codeStatement )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==IDENT||LA8_0==19||(LA8_0>=24 && LA8_0<=25)) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:86:23: codeStatement
            	    {
            	    pushFollow(FOLLOW_codeStatement_in_functionBody337);
            	    codeStatement40=codeStatement();

            	    state._fsp--;

            	    adaptor.addChild(root_0, codeStatement40.getTree());

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            char_literal41=(Token)match(input,28,FOLLOW_28_in_functionBody340); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "functionBody"

    public static class functionBodyToken_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "functionBodyToken"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:89:1: functionBodyToken : '{' -> FUNCTION_BODY ;
    public final EveParser.functionBodyToken_return functionBodyToken() throws RecognitionException {
        EveParser.functionBodyToken_return retval = new EveParser.functionBodyToken_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal42=null;

        CommonTree char_literal42_tree=null;
        RewriteRuleTokenStream stream_27=new RewriteRuleTokenStream(adaptor,"token 27");

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:90:2: ( '{' -> FUNCTION_BODY )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:90:4: '{'
            {
            char_literal42=(Token)match(input,27,FOLLOW_27_in_functionBodyToken352);  
            stream_27.add(char_literal42);



            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 90:8: -> FUNCTION_BODY
            {
                adaptor.addChild(root_0, (CommonTree)adaptor.create(FUNCTION_BODY, "FUNCTION_BODY"));

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "functionBodyToken"

    public static class parameters_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "parameters"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:93:1: parameters : parametersStartToken parameter ( ',' parameter )* ')' ;
    public final EveParser.parameters_return parameters() throws RecognitionException {
        EveParser.parameters_return retval = new EveParser.parameters_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal45=null;
        Token char_literal47=null;
        EveParser.parametersStartToken_return parametersStartToken43 = null;

        EveParser.parameter_return parameter44 = null;

        EveParser.parameter_return parameter46 = null;


        CommonTree char_literal45_tree=null;
        CommonTree char_literal47_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:94:2: ( parametersStartToken parameter ( ',' parameter )* ')' )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:94:4: parametersStartToken parameter ( ',' parameter )* ')'
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_parametersStartToken_in_parameters368);
            parametersStartToken43=parametersStartToken();

            state._fsp--;

            root_0 = (CommonTree)adaptor.becomeRoot(parametersStartToken43.getTree(), root_0);
            pushFollow(FOLLOW_parameter_in_parameters371);
            parameter44=parameter();

            state._fsp--;

            adaptor.addChild(root_0, parameter44.getTree());
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:94:36: ( ',' parameter )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==29) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:94:37: ',' parameter
            	    {
            	    char_literal45=(Token)match(input,29,FOLLOW_29_in_parameters374); 
            	    pushFollow(FOLLOW_parameter_in_parameters377);
            	    parameter46=parameter();

            	    state._fsp--;

            	    adaptor.addChild(root_0, parameter46.getTree());

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            char_literal47=(Token)match(input,21,FOLLOW_21_in_parameters381); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "parameters"

    public static class parametersStartToken_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "parametersStartToken"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:97:1: parametersStartToken : '(' -> FUNCTION_PARAMETERS ;
    public final EveParser.parametersStartToken_return parametersStartToken() throws RecognitionException {
        EveParser.parametersStartToken_return retval = new EveParser.parametersStartToken_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal48=null;

        CommonTree char_literal48_tree=null;
        RewriteRuleTokenStream stream_20=new RewriteRuleTokenStream(adaptor,"token 20");

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:98:2: ( '(' -> FUNCTION_PARAMETERS )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:98:4: '('
            {
            char_literal48=(Token)match(input,20,FOLLOW_20_in_parametersStartToken394);  
            stream_20.add(char_literal48);



            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 98:8: -> FUNCTION_PARAMETERS
            {
                adaptor.addChild(root_0, (CommonTree)adaptor.create(FUNCTION_PARAMETERS, "FUNCTION_PARAMETERS"));

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "parametersStartToken"

    public static class parameter_return extends ParserRuleReturnScope {
        public String param;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "parameter"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:101:1: parameter returns [String param] : IDENT ;
    public final EveParser.parameter_return parameter() throws RecognitionException {
        EveParser.parameter_return retval = new EveParser.parameter_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENT49=null;

        CommonTree IDENT49_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:102:2: ( IDENT )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:102:4: IDENT
            {
            root_0 = (CommonTree)adaptor.nil();

            IDENT49=(Token)match(input,IDENT,FOLLOW_IDENT_in_parameter413); 
            IDENT49_tree = (CommonTree)adaptor.create(IDENT49);
            adaptor.addChild(root_0, IDENT49_tree);

             retval.param = (IDENT49!=null?IDENT49.getText():null); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "parameter"

    public static class term_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "term"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:106:1: term : ( IDENT | '(' expression ')' | INTEGER | STRING_LITERAL | IDENT '(' actualParameters ')' );
    public final EveParser.term_return term() throws RecognitionException {
        EveParser.term_return retval = new EveParser.term_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENT50=null;
        Token char_literal51=null;
        Token char_literal53=null;
        Token INTEGER54=null;
        Token STRING_LITERAL55=null;
        Token IDENT56=null;
        Token char_literal57=null;
        Token char_literal59=null;
        EveParser.expression_return expression52 = null;

        EveParser.actualParameters_return actualParameters58 = null;


        CommonTree IDENT50_tree=null;
        CommonTree char_literal51_tree=null;
        CommonTree char_literal53_tree=null;
        CommonTree INTEGER54_tree=null;
        CommonTree STRING_LITERAL55_tree=null;
        CommonTree IDENT56_tree=null;
        CommonTree char_literal57_tree=null;
        CommonTree char_literal59_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:107:2: ( IDENT | '(' expression ')' | INTEGER | STRING_LITERAL | IDENT '(' actualParameters ')' )
            int alt10=5;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA10_1 = input.LA(2);

                if ( (LA10_1==20) ) {
                    alt10=5;
                }
                else if ( ((LA10_1>=21 && LA10_1<=23)||LA10_1==29||(LA10_1>=31 && LA10_1<=42)) ) {
                    alt10=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 1, input);

                    throw nvae;
                }
                }
                break;
            case 20:
                {
                alt10=2;
                }
                break;
            case INTEGER:
                {
                alt10=3;
                }
                break;
            case STRING_LITERAL:
                {
                alt10=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:107:4: IDENT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    IDENT50=(Token)match(input,IDENT,FOLLOW_IDENT_in_term427); 
                    IDENT50_tree = (CommonTree)adaptor.create(IDENT50);
                    adaptor.addChild(root_0, IDENT50_tree);


                    }
                    break;
                case 2 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:108:4: '(' expression ')'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    char_literal51=(Token)match(input,20,FOLLOW_20_in_term432); 
                    pushFollow(FOLLOW_expression_in_term435);
                    expression52=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression52.getTree());
                    char_literal53=(Token)match(input,21,FOLLOW_21_in_term437); 

                    }
                    break;
                case 3 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:109:4: INTEGER
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    INTEGER54=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_term443); 
                    INTEGER54_tree = (CommonTree)adaptor.create(INTEGER54);
                    adaptor.addChild(root_0, INTEGER54_tree);


                    }
                    break;
                case 4 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:110:4: STRING_LITERAL
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    STRING_LITERAL55=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_term448); 
                    STRING_LITERAL55_tree = (CommonTree)adaptor.create(STRING_LITERAL55);
                    adaptor.addChild(root_0, STRING_LITERAL55_tree);


                    }
                    break;
                case 5 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:111:4: IDENT '(' actualParameters ')'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    IDENT56=(Token)match(input,IDENT,FOLLOW_IDENT_in_term453); 
                    IDENT56_tree = (CommonTree)adaptor.create(IDENT56);
                    adaptor.addChild(root_0, IDENT56_tree);

                    char_literal57=(Token)match(input,20,FOLLOW_20_in_term455); 
                    char_literal57_tree = (CommonTree)adaptor.create(char_literal57);
                    adaptor.addChild(root_0, char_literal57_tree);

                    pushFollow(FOLLOW_actualParameters_in_term457);
                    actualParameters58=actualParameters();

                    state._fsp--;

                    adaptor.addChild(root_0, actualParameters58.getTree());
                    char_literal59=(Token)match(input,21,FOLLOW_21_in_term459); 
                    char_literal59_tree = (CommonTree)adaptor.create(char_literal59);
                    adaptor.addChild(root_0, char_literal59_tree);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "term"

    public static class boolNegation_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "boolNegation"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:114:1: boolNegation : ( 'not' )* term ;
    public final EveParser.boolNegation_return boolNegation() throws RecognitionException {
        EveParser.boolNegation_return retval = new EveParser.boolNegation_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal60=null;
        EveParser.term_return term61 = null;


        CommonTree string_literal60_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:115:2: ( ( 'not' )* term )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:115:4: ( 'not' )* term
            {
            root_0 = (CommonTree)adaptor.nil();

            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:115:4: ( 'not' )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==30) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:115:4: 'not'
            	    {
            	    string_literal60=(Token)match(input,30,FOLLOW_30_in_boolNegation471); 
            	    string_literal60_tree = (CommonTree)adaptor.create(string_literal60);
            	    adaptor.addChild(root_0, string_literal60_tree);


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            pushFollow(FOLLOW_term_in_boolNegation474);
            term61=term();

            state._fsp--;

            adaptor.addChild(root_0, term61.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "boolNegation"

    public static class unary_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unary"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:118:1: unary : ( '+' | negation )* boolNegation ;
    public final EveParser.unary_return unary() throws RecognitionException {
        EveParser.unary_return retval = new EveParser.unary_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal62=null;
        EveParser.negation_return negation63 = null;

        EveParser.boolNegation_return boolNegation64 = null;


        CommonTree char_literal62_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:119:2: ( ( '+' | negation )* boolNegation )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:119:4: ( '+' | negation )* boolNegation
            {
            root_0 = (CommonTree)adaptor.nil();

            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:119:4: ( '+' | negation )*
            loop12:
            do {
                int alt12=3;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==31) ) {
                    alt12=1;
                }
                else if ( (LA12_0==32) ) {
                    alt12=2;
                }


                switch (alt12) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:119:5: '+'
            	    {
            	    char_literal62=(Token)match(input,31,FOLLOW_31_in_unary487); 

            	    }
            	    break;
            	case 2 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:119:12: negation
            	    {
            	    pushFollow(FOLLOW_negation_in_unary492);
            	    negation63=negation();

            	    state._fsp--;

            	    root_0 = (CommonTree)adaptor.becomeRoot(negation63.getTree(), root_0);

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            pushFollow(FOLLOW_boolNegation_in_unary497);
            boolNegation64=boolNegation();

            state._fsp--;

            adaptor.addChild(root_0, boolNegation64.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "unary"

    public static class negation_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "negation"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:123:1: negation : '-' -> NEGATION ;
    public final EveParser.negation_return negation() throws RecognitionException {
        EveParser.negation_return retval = new EveParser.negation_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal65=null;

        CommonTree char_literal65_tree=null;
        RewriteRuleTokenStream stream_32=new RewriteRuleTokenStream(adaptor,"token 32");

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:124:2: ( '-' -> NEGATION )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:124:4: '-'
            {
            char_literal65=(Token)match(input,32,FOLLOW_32_in_negation511);  
            stream_32.add(char_literal65);



            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 124:8: -> NEGATION
            {
                adaptor.addChild(root_0, (CommonTree)adaptor.create(NEGATION, "NEGATION"));

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "negation"

    public static class mult_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "mult"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:127:1: mult : unary ( ( '*' | '/' | '%' ) unary )* ;
    public final EveParser.mult_return mult() throws RecognitionException {
        EveParser.mult_return retval = new EveParser.mult_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal67=null;
        Token char_literal68=null;
        Token char_literal69=null;
        EveParser.unary_return unary66 = null;

        EveParser.unary_return unary70 = null;


        CommonTree char_literal67_tree=null;
        CommonTree char_literal68_tree=null;
        CommonTree char_literal69_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:128:2: ( unary ( ( '*' | '/' | '%' ) unary )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:128:4: unary ( ( '*' | '/' | '%' ) unary )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_unary_in_mult526);
            unary66=unary();

            state._fsp--;

            adaptor.addChild(root_0, unary66.getTree());
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:128:10: ( ( '*' | '/' | '%' ) unary )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>=33 && LA14_0<=35)) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:128:11: ( '*' | '/' | '%' ) unary
            	    {
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:128:11: ( '*' | '/' | '%' )
            	    int alt13=3;
            	    switch ( input.LA(1) ) {
            	    case 33:
            	        {
            	        alt13=1;
            	        }
            	        break;
            	    case 34:
            	        {
            	        alt13=2;
            	        }
            	        break;
            	    case 35:
            	        {
            	        alt13=3;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 13, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt13) {
            	        case 1 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:128:12: '*'
            	            {
            	            char_literal67=(Token)match(input,33,FOLLOW_33_in_mult530); 
            	            char_literal67_tree = (CommonTree)adaptor.create(char_literal67);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal67_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:128:19: '/'
            	            {
            	            char_literal68=(Token)match(input,34,FOLLOW_34_in_mult535); 
            	            char_literal68_tree = (CommonTree)adaptor.create(char_literal68);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal68_tree, root_0);


            	            }
            	            break;
            	        case 3 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:128:26: '%'
            	            {
            	            char_literal69=(Token)match(input,35,FOLLOW_35_in_mult540); 
            	            char_literal69_tree = (CommonTree)adaptor.create(char_literal69);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal69_tree, root_0);


            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_unary_in_mult544);
            	    unary70=unary();

            	    state._fsp--;

            	    adaptor.addChild(root_0, unary70.getTree());

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "mult"

    public static class add_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "add"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:131:1: add : mult ( ( '+' | '-' ) mult )* ;
    public final EveParser.add_return add() throws RecognitionException {
        EveParser.add_return retval = new EveParser.add_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal72=null;
        Token char_literal73=null;
        EveParser.mult_return mult71 = null;

        EveParser.mult_return mult74 = null;


        CommonTree char_literal72_tree=null;
        CommonTree char_literal73_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:132:2: ( mult ( ( '+' | '-' ) mult )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:132:4: mult ( ( '+' | '-' ) mult )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_mult_in_add558);
            mult71=mult();

            state._fsp--;

            adaptor.addChild(root_0, mult71.getTree());
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:132:9: ( ( '+' | '-' ) mult )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>=31 && LA16_0<=32)) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:132:10: ( '+' | '-' ) mult
            	    {
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:132:10: ( '+' | '-' )
            	    int alt15=2;
            	    int LA15_0 = input.LA(1);

            	    if ( (LA15_0==31) ) {
            	        alt15=1;
            	    }
            	    else if ( (LA15_0==32) ) {
            	        alt15=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 15, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt15) {
            	        case 1 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:132:11: '+'
            	            {
            	            char_literal72=(Token)match(input,31,FOLLOW_31_in_add562); 
            	            char_literal72_tree = (CommonTree)adaptor.create(char_literal72);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal72_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:132:18: '-'
            	            {
            	            char_literal73=(Token)match(input,32,FOLLOW_32_in_add567); 
            	            char_literal73_tree = (CommonTree)adaptor.create(char_literal73);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal73_tree, root_0);


            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_mult_in_add571);
            	    mult74=mult();

            	    state._fsp--;

            	    adaptor.addChild(root_0, mult74.getTree());

            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "add"

    public static class relation_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "relation"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:135:1: relation : add ( ( '=' | '/=' | '<' | '<=' | '>=' | '>' ) add )* ;
    public final EveParser.relation_return relation() throws RecognitionException {
        EveParser.relation_return retval = new EveParser.relation_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal76=null;
        Token string_literal77=null;
        Token char_literal78=null;
        Token string_literal79=null;
        Token string_literal80=null;
        Token char_literal81=null;
        EveParser.add_return add75 = null;

        EveParser.add_return add82 = null;


        CommonTree char_literal76_tree=null;
        CommonTree string_literal77_tree=null;
        CommonTree char_literal78_tree=null;
        CommonTree string_literal79_tree=null;
        CommonTree string_literal80_tree=null;
        CommonTree char_literal81_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:136:2: ( add ( ( '=' | '/=' | '<' | '<=' | '>=' | '>' ) add )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:136:4: add ( ( '=' | '/=' | '<' | '<=' | '>=' | '>' ) add )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_add_in_relation584);
            add75=add();

            state._fsp--;

            adaptor.addChild(root_0, add75.getTree());
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:136:8: ( ( '=' | '/=' | '<' | '<=' | '>=' | '>' ) add )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==23||(LA18_0>=36 && LA18_0<=40)) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:136:9: ( '=' | '/=' | '<' | '<=' | '>=' | '>' ) add
            	    {
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:136:9: ( '=' | '/=' | '<' | '<=' | '>=' | '>' )
            	    int alt17=6;
            	    switch ( input.LA(1) ) {
            	    case 23:
            	        {
            	        alt17=1;
            	        }
            	        break;
            	    case 36:
            	        {
            	        alt17=2;
            	        }
            	        break;
            	    case 37:
            	        {
            	        alt17=3;
            	        }
            	        break;
            	    case 38:
            	        {
            	        alt17=4;
            	        }
            	        break;
            	    case 39:
            	        {
            	        alt17=5;
            	        }
            	        break;
            	    case 40:
            	        {
            	        alt17=6;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 17, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt17) {
            	        case 1 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:136:10: '='
            	            {
            	            char_literal76=(Token)match(input,23,FOLLOW_23_in_relation588); 
            	            char_literal76_tree = (CommonTree)adaptor.create(char_literal76);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal76_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:136:17: '/='
            	            {
            	            string_literal77=(Token)match(input,36,FOLLOW_36_in_relation593); 
            	            string_literal77_tree = (CommonTree)adaptor.create(string_literal77);
            	            root_0 = (CommonTree)adaptor.becomeRoot(string_literal77_tree, root_0);


            	            }
            	            break;
            	        case 3 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:136:25: '<'
            	            {
            	            char_literal78=(Token)match(input,37,FOLLOW_37_in_relation598); 
            	            char_literal78_tree = (CommonTree)adaptor.create(char_literal78);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal78_tree, root_0);


            	            }
            	            break;
            	        case 4 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:136:32: '<='
            	            {
            	            string_literal79=(Token)match(input,38,FOLLOW_38_in_relation603); 
            	            string_literal79_tree = (CommonTree)adaptor.create(string_literal79);
            	            root_0 = (CommonTree)adaptor.becomeRoot(string_literal79_tree, root_0);


            	            }
            	            break;
            	        case 5 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:136:40: '>='
            	            {
            	            string_literal80=(Token)match(input,39,FOLLOW_39_in_relation608); 
            	            string_literal80_tree = (CommonTree)adaptor.create(string_literal80);
            	            root_0 = (CommonTree)adaptor.becomeRoot(string_literal80_tree, root_0);


            	            }
            	            break;
            	        case 6 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:136:48: '>'
            	            {
            	            char_literal81=(Token)match(input,40,FOLLOW_40_in_relation613); 
            	            char_literal81_tree = (CommonTree)adaptor.create(char_literal81);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal81_tree, root_0);


            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_add_in_relation617);
            	    add82=add();

            	    state._fsp--;

            	    adaptor.addChild(root_0, add82.getTree());

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "relation"

    public static class expression_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:139:1: expression : relation ( ( 'and' | 'or' ) relation )* ;
    public final EveParser.expression_return expression() throws RecognitionException {
        EveParser.expression_return retval = new EveParser.expression_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token set84=null;
        EveParser.relation_return relation83 = null;

        EveParser.relation_return relation85 = null;


        CommonTree set84_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:140:2: ( relation ( ( 'and' | 'or' ) relation )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:140:4: relation ( ( 'and' | 'or' ) relation )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_relation_in_expression631);
            relation83=relation();

            state._fsp--;

            adaptor.addChild(root_0, relation83.getTree());
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:140:13: ( ( 'and' | 'or' ) relation )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>=41 && LA19_0<=42)) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:140:14: ( 'and' | 'or' ) relation
            	    {
            	    set84=(Token)input.LT(1);
            	    if ( (input.LA(1)>=41 && input.LA(1)<=42) ) {
            	        input.consume();
            	        adaptor.addChild(root_0, (CommonTree)adaptor.create(set84));
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_relation_in_expression642);
            	    relation85=relation();

            	    state._fsp--;

            	    adaptor.addChild(root_0, relation85.getTree());

            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expression"

    // Delegated rules


 

    public static final BitSet FOLLOW_statement_in_program88 = new BitSet(new long[]{0x0000000007080402L});
    public static final BitSet FOLLOW_codeStatement_in_statement101 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_protoStatement_in_statement106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_printStatement_in_codeStatement119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentStatement_in_codeStatement124 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_initVariableStatement_in_codeStatement129 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_printStatement141 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_printStatement144 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_expression_in_printStatement147 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_printStatement149 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_printStatement152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_assignmentStatement165 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_assignmentStatement167 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_expression_in_assignmentStatement169 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_assignmentStatement171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_initFunction_in_assignmentStatement186 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_assignmentStatement188 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_assignmentStatement190 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_function_in_assignmentStatement193 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_22_in_assignmentStatement195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_initFunction207 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_initVariable_in_initVariableStatement223 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_initVariableStatement226 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_initVariableStatement228 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_expression_in_initVariableStatement231 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_initVariableStatement233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_initVariable245 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_protoStatement261 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_protoStatement264 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_protoStatement266 = new BitSet(new long[]{0x0000000013080400L});
    public static final BitSet FOLLOW_protoBlock_in_protoStatement269 = new BitSet(new long[]{0x0000000013080400L});
    public static final BitSet FOLLOW_28_in_protoStatement272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_codeStatement_in_protoBlock285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_actualParameters297 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_29_in_actualParameters300 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_expression_in_actualParameters302 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_parameters_in_function318 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_functionBody_in_function322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionBodyToken_in_functionBody334 = new BitSet(new long[]{0x0000000013080400L});
    public static final BitSet FOLLOW_codeStatement_in_functionBody337 = new BitSet(new long[]{0x0000000013080400L});
    public static final BitSet FOLLOW_28_in_functionBody340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_functionBodyToken352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parametersStartToken_in_parameters368 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_parameter_in_parameters371 = new BitSet(new long[]{0x0000000020200000L});
    public static final BitSet FOLLOW_29_in_parameters374 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_parameter_in_parameters377 = new BitSet(new long[]{0x0000000020200000L});
    public static final BitSet FOLLOW_21_in_parameters381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_parametersStartToken394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_parameter413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_term427 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_term432 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_expression_in_term435 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_term437 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_term443 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_term448 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_term453 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_term455 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_actualParameters_in_term457 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_term459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_boolNegation471 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_term_in_boolNegation474 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_unary487 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_negation_in_unary492 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_boolNegation_in_unary497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_negation511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unary_in_mult526 = new BitSet(new long[]{0x0000000E00000002L});
    public static final BitSet FOLLOW_33_in_mult530 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_34_in_mult535 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_35_in_mult540 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_unary_in_mult544 = new BitSet(new long[]{0x0000000E00000002L});
    public static final BitSet FOLLOW_mult_in_add558 = new BitSet(new long[]{0x0000000180000002L});
    public static final BitSet FOLLOW_31_in_add562 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_32_in_add567 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_mult_in_add571 = new BitSet(new long[]{0x0000000180000002L});
    public static final BitSet FOLLOW_add_in_relation584 = new BitSet(new long[]{0x000001F000800002L});
    public static final BitSet FOLLOW_23_in_relation588 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_36_in_relation593 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_37_in_relation598 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_38_in_relation603 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_39_in_relation608 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_40_in_relation613 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_add_in_relation617 = new BitSet(new long[]{0x000001F000800002L});
    public static final BitSet FOLLOW_relation_in_expression631 = new BitSet(new long[]{0x0000060000000002L});
    public static final BitSet FOLLOW_set_in_expression634 = new BitSet(new long[]{0x00000001C0101C00L});
    public static final BitSet FOLLOW_relation_in_expression642 = new BitSet(new long[]{0x0000060000000002L});

}