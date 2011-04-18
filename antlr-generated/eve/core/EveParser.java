// $ANTLR 3.3 Nov 30, 2010 12:50:56 /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g 2011-04-18 16:59:34

	package eve.core;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class EveParser extends Parser {
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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:31:1: program : ( statement )* ;
    public final EveParser.program_return program() throws RecognitionException {
        EveParser.program_return retval = new EveParser.program_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        EveParser.statement_return statement1 = null;



        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:32:2: ( ( statement )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:32:4: ( statement )*
            {
            root_0 = (CommonTree)adaptor.nil();

            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:32:4: ( statement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==IDENT||LA1_0==21||(LA1_0>=26 && LA1_0<=28)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:32:4: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_program96);
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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:36:1: statement : ( codeStatement | protoStatement );
    public final EveParser.statement_return statement() throws RecognitionException {
        EveParser.statement_return retval = new EveParser.statement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        EveParser.codeStatement_return codeStatement2 = null;

        EveParser.protoStatement_return protoStatement3 = null;



        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:37:2: ( codeStatement | protoStatement )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==IDENT||LA2_0==21||(LA2_0>=26 && LA2_0<=27)) ) {
                alt2=1;
            }
            else if ( (LA2_0==28) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:37:4: codeStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_codeStatement_in_statement109);
                    codeStatement2=codeStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, codeStatement2.getTree());

                    }
                    break;
                case 2 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:38:4: protoStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_protoStatement_in_statement114);
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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:41:1: codeStatement : ( printStatement | assignmentStatement | initVariableStatement | functionInvocationStatement );
    public final EveParser.codeStatement_return codeStatement() throws RecognitionException {
        EveParser.codeStatement_return retval = new EveParser.codeStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        EveParser.printStatement_return printStatement4 = null;

        EveParser.assignmentStatement_return assignmentStatement5 = null;

        EveParser.initVariableStatement_return initVariableStatement6 = null;

        EveParser.functionInvocationStatement_return functionInvocationStatement7 = null;



        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:42:2: ( printStatement | assignmentStatement | initVariableStatement | functionInvocationStatement )
            int alt3=4;
            switch ( input.LA(1) ) {
            case 21:
                {
                alt3=1;
                }
                break;
            case IDENT:
                {
                int LA3_2 = input.LA(2);

                if ( (LA3_2==25) ) {
                    alt3=2;
                }
                else if ( (LA3_2==22) ) {
                    alt3=4;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 2, input);

                    throw nvae;
                }
                }
                break;
            case 26:
                {
                alt3=2;
                }
                break;
            case 27:
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
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:42:4: printStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_printStatement_in_codeStatement127);
                    printStatement4=printStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, printStatement4.getTree());

                    }
                    break;
                case 2 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:43:4: assignmentStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_assignmentStatement_in_codeStatement132);
                    assignmentStatement5=assignmentStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, assignmentStatement5.getTree());

                    }
                    break;
                case 3 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:44:4: initVariableStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_initVariableStatement_in_codeStatement137);
                    initVariableStatement6=initVariableStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, initVariableStatement6.getTree());

                    }
                    break;
                case 4 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:45:4: functionInvocationStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_functionInvocationStatement_in_codeStatement142);
                    functionInvocationStatement7=functionInvocationStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, functionInvocationStatement7.getTree());

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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:48:1: printStatement : 'print' '(' expression ')' ';' ;
    public final EveParser.printStatement_return printStatement() throws RecognitionException {
        EveParser.printStatement_return retval = new EveParser.printStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal8=null;
        Token char_literal9=null;
        Token char_literal11=null;
        Token char_literal12=null;
        EveParser.expression_return expression10 = null;


        CommonTree string_literal8_tree=null;
        CommonTree char_literal9_tree=null;
        CommonTree char_literal11_tree=null;
        CommonTree char_literal12_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:49:2: ( 'print' '(' expression ')' ';' )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:49:4: 'print' '(' expression ')' ';'
            {
            root_0 = (CommonTree)adaptor.nil();

            string_literal8=(Token)match(input,21,FOLLOW_21_in_printStatement154); 
            string_literal8_tree = (CommonTree)adaptor.create(string_literal8);
            root_0 = (CommonTree)adaptor.becomeRoot(string_literal8_tree, root_0);

            char_literal9=(Token)match(input,22,FOLLOW_22_in_printStatement157); 
            pushFollow(FOLLOW_expression_in_printStatement160);
            expression10=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression10.getTree());
            char_literal11=(Token)match(input,23,FOLLOW_23_in_printStatement162); 
            char_literal12=(Token)match(input,24,FOLLOW_24_in_printStatement165); 

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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:52:1: assignmentStatement : ( IDENT '=' expression ';' -> ^( UPDATE_VARIABLE IDENT expression ) | initFunction IDENT '=' function ( ';' )? );
    public final EveParser.assignmentStatement_return assignmentStatement() throws RecognitionException {
        EveParser.assignmentStatement_return retval = new EveParser.assignmentStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENT13=null;
        Token char_literal14=null;
        Token char_literal16=null;
        Token IDENT18=null;
        Token char_literal19=null;
        Token char_literal21=null;
        EveParser.expression_return expression15 = null;

        EveParser.initFunction_return initFunction17 = null;

        EveParser.function_return function20 = null;


        CommonTree IDENT13_tree=null;
        CommonTree char_literal14_tree=null;
        CommonTree char_literal16_tree=null;
        CommonTree IDENT18_tree=null;
        CommonTree char_literal19_tree=null;
        CommonTree char_literal21_tree=null;
        RewriteRuleTokenStream stream_IDENT=new RewriteRuleTokenStream(adaptor,"token IDENT");
        RewriteRuleTokenStream stream_24=new RewriteRuleTokenStream(adaptor,"token 24");
        RewriteRuleTokenStream stream_25=new RewriteRuleTokenStream(adaptor,"token 25");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:53:2: ( IDENT '=' expression ';' -> ^( UPDATE_VARIABLE IDENT expression ) | initFunction IDENT '=' function ( ';' )? )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==IDENT) ) {
                alt5=1;
            }
            else if ( (LA5_0==26) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:53:4: IDENT '=' expression ';'
                    {
                    IDENT13=(Token)match(input,IDENT,FOLLOW_IDENT_in_assignmentStatement178);  
                    stream_IDENT.add(IDENT13);

                    char_literal14=(Token)match(input,25,FOLLOW_25_in_assignmentStatement180);  
                    stream_25.add(char_literal14);

                    pushFollow(FOLLOW_expression_in_assignmentStatement182);
                    expression15=expression();

                    state._fsp--;

                    stream_expression.add(expression15.getTree());
                    char_literal16=(Token)match(input,24,FOLLOW_24_in_assignmentStatement184);  
                    stream_24.add(char_literal16);



                    // AST REWRITE
                    // elements: IDENT, expression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 53:29: -> ^( UPDATE_VARIABLE IDENT expression )
                    {
                        // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:53:32: ^( UPDATE_VARIABLE IDENT expression )
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
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:54:4: initFunction IDENT '=' function ( ';' )?
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_initFunction_in_assignmentStatement199);
                    initFunction17=initFunction();

                    state._fsp--;

                    adaptor.addChild(root_0, initFunction17.getTree());
                    IDENT18=(Token)match(input,IDENT,FOLLOW_IDENT_in_assignmentStatement201); 
                    IDENT18_tree = (CommonTree)adaptor.create(IDENT18);
                    adaptor.addChild(root_0, IDENT18_tree);

                    char_literal19=(Token)match(input,25,FOLLOW_25_in_assignmentStatement203); 
                    char_literal19_tree = (CommonTree)adaptor.create(char_literal19);
                    root_0 = (CommonTree)adaptor.becomeRoot(char_literal19_tree, root_0);

                    pushFollow(FOLLOW_function_in_assignmentStatement206);
                    function20=function();

                    state._fsp--;

                    adaptor.addChild(root_0, function20.getTree());
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:54:37: ( ';' )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0==24) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:54:37: ';'
                            {
                            char_literal21=(Token)match(input,24,FOLLOW_24_in_assignmentStatement208); 
                            char_literal21_tree = (CommonTree)adaptor.create(char_literal21);
                            adaptor.addChild(root_0, char_literal21_tree);


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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:57:1: initFunction : 'def' -> INIT_FUNCTION ;
    public final EveParser.initFunction_return initFunction() throws RecognitionException {
        EveParser.initFunction_return retval = new EveParser.initFunction_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal22=null;

        CommonTree string_literal22_tree=null;
        RewriteRuleTokenStream stream_26=new RewriteRuleTokenStream(adaptor,"token 26");

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:58:2: ( 'def' -> INIT_FUNCTION )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:58:4: 'def'
            {
            string_literal22=(Token)match(input,26,FOLLOW_26_in_initFunction220);  
            stream_26.add(string_literal22);



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
            // 58:10: -> INIT_FUNCTION
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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:61:1: initVariableStatement : initVariable IDENT '=' expression ';' ;
    public final EveParser.initVariableStatement_return initVariableStatement() throws RecognitionException {
        EveParser.initVariableStatement_return retval = new EveParser.initVariableStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENT24=null;
        Token char_literal25=null;
        Token char_literal27=null;
        EveParser.initVariable_return initVariable23 = null;

        EveParser.expression_return expression26 = null;


        CommonTree IDENT24_tree=null;
        CommonTree char_literal25_tree=null;
        CommonTree char_literal27_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:62:2: ( initVariable IDENT '=' expression ';' )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:62:4: initVariable IDENT '=' expression ';'
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_initVariable_in_initVariableStatement236);
            initVariable23=initVariable();

            state._fsp--;

            root_0 = (CommonTree)adaptor.becomeRoot(initVariable23.getTree(), root_0);
            IDENT24=(Token)match(input,IDENT,FOLLOW_IDENT_in_initVariableStatement239); 
            IDENT24_tree = (CommonTree)adaptor.create(IDENT24);
            adaptor.addChild(root_0, IDENT24_tree);

            char_literal25=(Token)match(input,25,FOLLOW_25_in_initVariableStatement241); 
            pushFollow(FOLLOW_expression_in_initVariableStatement244);
            expression26=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression26.getTree());
            char_literal27=(Token)match(input,24,FOLLOW_24_in_initVariableStatement246); 

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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:65:1: initVariable : 'var' -> INIT_VARIABLE ;
    public final EveParser.initVariable_return initVariable() throws RecognitionException {
        EveParser.initVariable_return retval = new EveParser.initVariable_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal28=null;

        CommonTree string_literal28_tree=null;
        RewriteRuleTokenStream stream_27=new RewriteRuleTokenStream(adaptor,"token 27");

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:66:2: ( 'var' -> INIT_VARIABLE )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:66:4: 'var'
            {
            string_literal28=(Token)match(input,27,FOLLOW_27_in_initVariable258);  
            stream_27.add(string_literal28);



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
            // 66:10: -> INIT_VARIABLE
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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:69:1: protoStatement : 'proto' IDENT '{' ( protoBlock )* '}' ;
    public final EveParser.protoStatement_return protoStatement() throws RecognitionException {
        EveParser.protoStatement_return retval = new EveParser.protoStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal29=null;
        Token IDENT30=null;
        Token char_literal31=null;
        Token char_literal33=null;
        EveParser.protoBlock_return protoBlock32 = null;


        CommonTree string_literal29_tree=null;
        CommonTree IDENT30_tree=null;
        CommonTree char_literal31_tree=null;
        CommonTree char_literal33_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:70:2: ( 'proto' IDENT '{' ( protoBlock )* '}' )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:70:4: 'proto' IDENT '{' ( protoBlock )* '}'
            {
            root_0 = (CommonTree)adaptor.nil();

            string_literal29=(Token)match(input,28,FOLLOW_28_in_protoStatement274); 
            string_literal29_tree = (CommonTree)adaptor.create(string_literal29);
            root_0 = (CommonTree)adaptor.becomeRoot(string_literal29_tree, root_0);

            IDENT30=(Token)match(input,IDENT,FOLLOW_IDENT_in_protoStatement277); 
            IDENT30_tree = (CommonTree)adaptor.create(IDENT30);
            adaptor.addChild(root_0, IDENT30_tree);

            char_literal31=(Token)match(input,29,FOLLOW_29_in_protoStatement279); 
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:70:24: ( protoBlock )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==IDENT||LA6_0==21||(LA6_0>=26 && LA6_0<=27)) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:70:24: protoBlock
            	    {
            	    pushFollow(FOLLOW_protoBlock_in_protoStatement282);
            	    protoBlock32=protoBlock();

            	    state._fsp--;

            	    adaptor.addChild(root_0, protoBlock32.getTree());

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            char_literal33=(Token)match(input,30,FOLLOW_30_in_protoStatement285); 

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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:73:1: protoBlock : codeStatement ;
    public final EveParser.protoBlock_return protoBlock() throws RecognitionException {
        EveParser.protoBlock_return retval = new EveParser.protoBlock_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        EveParser.codeStatement_return codeStatement34 = null;



        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:74:2: ( codeStatement )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:74:4: codeStatement
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_codeStatement_in_protoBlock298);
            codeStatement34=codeStatement();

            state._fsp--;

            adaptor.addChild(root_0, codeStatement34.getTree());

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

    public static class functionInvocationStatement_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "functionInvocationStatement"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:77:1: functionInvocationStatement : IDENT '(' functionInvocationParameters ')' -> ^( INVOKE_FUNCTION_STMT IDENT functionInvocationParameters ) ;
    public final EveParser.functionInvocationStatement_return functionInvocationStatement() throws RecognitionException {
        EveParser.functionInvocationStatement_return retval = new EveParser.functionInvocationStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENT35=null;
        Token char_literal36=null;
        Token char_literal38=null;
        EveParser.functionInvocationParameters_return functionInvocationParameters37 = null;


        CommonTree IDENT35_tree=null;
        CommonTree char_literal36_tree=null;
        CommonTree char_literal38_tree=null;
        RewriteRuleTokenStream stream_IDENT=new RewriteRuleTokenStream(adaptor,"token IDENT");
        RewriteRuleTokenStream stream_22=new RewriteRuleTokenStream(adaptor,"token 22");
        RewriteRuleTokenStream stream_23=new RewriteRuleTokenStream(adaptor,"token 23");
        RewriteRuleSubtreeStream stream_functionInvocationParameters=new RewriteRuleSubtreeStream(adaptor,"rule functionInvocationParameters");
        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:78:2: ( IDENT '(' functionInvocationParameters ')' -> ^( INVOKE_FUNCTION_STMT IDENT functionInvocationParameters ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:78:4: IDENT '(' functionInvocationParameters ')'
            {
            IDENT35=(Token)match(input,IDENT,FOLLOW_IDENT_in_functionInvocationStatement310);  
            stream_IDENT.add(IDENT35);

            char_literal36=(Token)match(input,22,FOLLOW_22_in_functionInvocationStatement312);  
            stream_22.add(char_literal36);

            pushFollow(FOLLOW_functionInvocationParameters_in_functionInvocationStatement314);
            functionInvocationParameters37=functionInvocationParameters();

            state._fsp--;

            stream_functionInvocationParameters.add(functionInvocationParameters37.getTree());
            char_literal38=(Token)match(input,23,FOLLOW_23_in_functionInvocationStatement316);  
            stream_23.add(char_literal38);



            // AST REWRITE
            // elements: IDENT, functionInvocationParameters
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 78:47: -> ^( INVOKE_FUNCTION_STMT IDENT functionInvocationParameters )
            {
                // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:78:50: ^( INVOKE_FUNCTION_STMT IDENT functionInvocationParameters )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(INVOKE_FUNCTION_STMT, "INVOKE_FUNCTION_STMT"), root_1);

                adaptor.addChild(root_1, stream_IDENT.nextNode());
                adaptor.addChild(root_1, stream_functionInvocationParameters.nextTree());

                adaptor.addChild(root_0, root_1);
                }

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
    // $ANTLR end "functionInvocationStatement"

    public static class functionInvocationParameters_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "functionInvocationParameters"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:82:1: functionInvocationParameters : expression ( ',' expression )* -> ( expression )* ;
    public final EveParser.functionInvocationParameters_return functionInvocationParameters() throws RecognitionException {
        EveParser.functionInvocationParameters_return retval = new EveParser.functionInvocationParameters_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal40=null;
        EveParser.expression_return expression39 = null;

        EveParser.expression_return expression41 = null;


        CommonTree char_literal40_tree=null;
        RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:83:2: ( expression ( ',' expression )* -> ( expression )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:83:4: expression ( ',' expression )*
            {
            pushFollow(FOLLOW_expression_in_functionInvocationParameters338);
            expression39=expression();

            state._fsp--;

            stream_expression.add(expression39.getTree());
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:83:15: ( ',' expression )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==31) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:83:16: ',' expression
            	    {
            	    char_literal40=(Token)match(input,31,FOLLOW_31_in_functionInvocationParameters341);  
            	    stream_31.add(char_literal40);

            	    pushFollow(FOLLOW_expression_in_functionInvocationParameters343);
            	    expression41=expression();

            	    state._fsp--;

            	    stream_expression.add(expression41.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);



            // AST REWRITE
            // elements: expression
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 83:33: -> ( expression )*
            {
                // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:83:36: ( expression )*
                while ( stream_expression.hasNext() ) {
                    adaptor.addChild(root_0, stream_expression.nextTree());

                }
                stream_expression.reset();

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
    // $ANTLR end "functionInvocationParameters"

    public static class function_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:86:1: function : parameters functionBody ;
    public final EveParser.function_return function() throws RecognitionException {
        EveParser.function_return retval = new EveParser.function_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        EveParser.parameters_return parameters42 = null;

        EveParser.functionBody_return functionBody43 = null;



        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:87:2: ( parameters functionBody )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:87:4: parameters functionBody
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_parameters_in_function366);
            parameters42=parameters();

            state._fsp--;

            adaptor.addChild(root_0, parameters42.getTree());
            pushFollow(FOLLOW_functionBody_in_function370);
            functionBody43=functionBody();

            state._fsp--;

            adaptor.addChild(root_0, functionBody43.getTree());

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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:91:1: functionBody : functionBodyToken ( codeStatement )* '}' ;
    public final EveParser.functionBody_return functionBody() throws RecognitionException {
        EveParser.functionBody_return retval = new EveParser.functionBody_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal46=null;
        EveParser.functionBodyToken_return functionBodyToken44 = null;

        EveParser.codeStatement_return codeStatement45 = null;


        CommonTree char_literal46_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:92:2: ( functionBodyToken ( codeStatement )* '}' )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:92:4: functionBodyToken ( codeStatement )* '}'
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_functionBodyToken_in_functionBody382);
            functionBodyToken44=functionBodyToken();

            state._fsp--;

            root_0 = (CommonTree)adaptor.becomeRoot(functionBodyToken44.getTree(), root_0);
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:92:23: ( codeStatement )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==IDENT||LA8_0==21||(LA8_0>=26 && LA8_0<=27)) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:92:23: codeStatement
            	    {
            	    pushFollow(FOLLOW_codeStatement_in_functionBody385);
            	    codeStatement45=codeStatement();

            	    state._fsp--;

            	    adaptor.addChild(root_0, codeStatement45.getTree());

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            char_literal46=(Token)match(input,30,FOLLOW_30_in_functionBody388); 

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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:95:1: functionBodyToken : '{' -> FUNCTION_BODY ;
    public final EveParser.functionBodyToken_return functionBodyToken() throws RecognitionException {
        EveParser.functionBodyToken_return retval = new EveParser.functionBodyToken_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal47=null;

        CommonTree char_literal47_tree=null;
        RewriteRuleTokenStream stream_29=new RewriteRuleTokenStream(adaptor,"token 29");

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:96:2: ( '{' -> FUNCTION_BODY )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:96:4: '{'
            {
            char_literal47=(Token)match(input,29,FOLLOW_29_in_functionBodyToken400);  
            stream_29.add(char_literal47);



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
            // 96:8: -> FUNCTION_BODY
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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:99:1: parameters : parametersStartToken parameter ( ',' parameter )* ')' ;
    public final EveParser.parameters_return parameters() throws RecognitionException {
        EveParser.parameters_return retval = new EveParser.parameters_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal50=null;
        Token char_literal52=null;
        EveParser.parametersStartToken_return parametersStartToken48 = null;

        EveParser.parameter_return parameter49 = null;

        EveParser.parameter_return parameter51 = null;


        CommonTree char_literal50_tree=null;
        CommonTree char_literal52_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:100:2: ( parametersStartToken parameter ( ',' parameter )* ')' )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:100:4: parametersStartToken parameter ( ',' parameter )* ')'
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_parametersStartToken_in_parameters416);
            parametersStartToken48=parametersStartToken();

            state._fsp--;

            root_0 = (CommonTree)adaptor.becomeRoot(parametersStartToken48.getTree(), root_0);
            pushFollow(FOLLOW_parameter_in_parameters419);
            parameter49=parameter();

            state._fsp--;

            adaptor.addChild(root_0, parameter49.getTree());
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:100:36: ( ',' parameter )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==31) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:100:37: ',' parameter
            	    {
            	    char_literal50=(Token)match(input,31,FOLLOW_31_in_parameters422); 
            	    pushFollow(FOLLOW_parameter_in_parameters425);
            	    parameter51=parameter();

            	    state._fsp--;

            	    adaptor.addChild(root_0, parameter51.getTree());

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            char_literal52=(Token)match(input,23,FOLLOW_23_in_parameters429); 

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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:103:1: parametersStartToken : '(' -> FUNCTION_PARAMETERS ;
    public final EveParser.parametersStartToken_return parametersStartToken() throws RecognitionException {
        EveParser.parametersStartToken_return retval = new EveParser.parametersStartToken_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal53=null;

        CommonTree char_literal53_tree=null;
        RewriteRuleTokenStream stream_22=new RewriteRuleTokenStream(adaptor,"token 22");

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:104:2: ( '(' -> FUNCTION_PARAMETERS )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:104:4: '('
            {
            char_literal53=(Token)match(input,22,FOLLOW_22_in_parametersStartToken442);  
            stream_22.add(char_literal53);



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
            // 104:8: -> FUNCTION_PARAMETERS
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "parameter"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:107:1: parameter : IDENT ;
    public final EveParser.parameter_return parameter() throws RecognitionException {
        EveParser.parameter_return retval = new EveParser.parameter_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENT54=null;

        CommonTree IDENT54_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:108:2: ( IDENT )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:108:4: IDENT
            {
            root_0 = (CommonTree)adaptor.nil();

            IDENT54=(Token)match(input,IDENT,FOLLOW_IDENT_in_parameter457); 
            IDENT54_tree = (CommonTree)adaptor.create(IDENT54);
            adaptor.addChild(root_0, IDENT54_tree);


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

    public static class functionInvocationExpression_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "functionInvocationExpression"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:111:1: functionInvocationExpression : IDENT '(' functionInvocationParameters ')' -> ^( INVOKE_FUNCTION_EXPR IDENT functionInvocationParameters ) ;
    public final EveParser.functionInvocationExpression_return functionInvocationExpression() throws RecognitionException {
        EveParser.functionInvocationExpression_return retval = new EveParser.functionInvocationExpression_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENT55=null;
        Token char_literal56=null;
        Token char_literal58=null;
        EveParser.functionInvocationParameters_return functionInvocationParameters57 = null;


        CommonTree IDENT55_tree=null;
        CommonTree char_literal56_tree=null;
        CommonTree char_literal58_tree=null;
        RewriteRuleTokenStream stream_IDENT=new RewriteRuleTokenStream(adaptor,"token IDENT");
        RewriteRuleTokenStream stream_22=new RewriteRuleTokenStream(adaptor,"token 22");
        RewriteRuleTokenStream stream_23=new RewriteRuleTokenStream(adaptor,"token 23");
        RewriteRuleSubtreeStream stream_functionInvocationParameters=new RewriteRuleSubtreeStream(adaptor,"rule functionInvocationParameters");
        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:112:2: ( IDENT '(' functionInvocationParameters ')' -> ^( INVOKE_FUNCTION_EXPR IDENT functionInvocationParameters ) )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:112:4: IDENT '(' functionInvocationParameters ')'
            {
            IDENT55=(Token)match(input,IDENT,FOLLOW_IDENT_in_functionInvocationExpression468);  
            stream_IDENT.add(IDENT55);

            char_literal56=(Token)match(input,22,FOLLOW_22_in_functionInvocationExpression470);  
            stream_22.add(char_literal56);

            pushFollow(FOLLOW_functionInvocationParameters_in_functionInvocationExpression472);
            functionInvocationParameters57=functionInvocationParameters();

            state._fsp--;

            stream_functionInvocationParameters.add(functionInvocationParameters57.getTree());
            char_literal58=(Token)match(input,23,FOLLOW_23_in_functionInvocationExpression474);  
            stream_23.add(char_literal58);



            // AST REWRITE
            // elements: IDENT, functionInvocationParameters
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 112:47: -> ^( INVOKE_FUNCTION_EXPR IDENT functionInvocationParameters )
            {
                // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:112:50: ^( INVOKE_FUNCTION_EXPR IDENT functionInvocationParameters )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(INVOKE_FUNCTION_EXPR, "INVOKE_FUNCTION_EXPR"), root_1);

                adaptor.addChild(root_1, stream_IDENT.nextNode());
                adaptor.addChild(root_1, stream_functionInvocationParameters.nextTree());

                adaptor.addChild(root_0, root_1);
                }

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
    // $ANTLR end "functionInvocationExpression"

    public static class term_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "term"
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:116:1: term : ( IDENT | '(' expression ')' | INTEGER | STRING_LITERAL | functionInvocationExpression );
    public final EveParser.term_return term() throws RecognitionException {
        EveParser.term_return retval = new EveParser.term_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENT59=null;
        Token char_literal60=null;
        Token char_literal62=null;
        Token INTEGER63=null;
        Token STRING_LITERAL64=null;
        EveParser.expression_return expression61 = null;

        EveParser.functionInvocationExpression_return functionInvocationExpression65 = null;


        CommonTree IDENT59_tree=null;
        CommonTree char_literal60_tree=null;
        CommonTree char_literal62_tree=null;
        CommonTree INTEGER63_tree=null;
        CommonTree STRING_LITERAL64_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:117:2: ( IDENT | '(' expression ')' | INTEGER | STRING_LITERAL | functionInvocationExpression )
            int alt10=5;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA10_1 = input.LA(2);

                if ( (LA10_1==22) ) {
                    alt10=5;
                }
                else if ( ((LA10_1>=23 && LA10_1<=25)||LA10_1==31||(LA10_1>=33 && LA10_1<=44)) ) {
                    alt10=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 1, input);

                    throw nvae;
                }
                }
                break;
            case 22:
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
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:117:4: IDENT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    IDENT59=(Token)match(input,IDENT,FOLLOW_IDENT_in_term496); 
                    IDENT59_tree = (CommonTree)adaptor.create(IDENT59);
                    adaptor.addChild(root_0, IDENT59_tree);


                    }
                    break;
                case 2 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:118:4: '(' expression ')'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    char_literal60=(Token)match(input,22,FOLLOW_22_in_term501); 
                    pushFollow(FOLLOW_expression_in_term504);
                    expression61=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression61.getTree());
                    char_literal62=(Token)match(input,23,FOLLOW_23_in_term506); 

                    }
                    break;
                case 3 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:119:4: INTEGER
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    INTEGER63=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_term512); 
                    INTEGER63_tree = (CommonTree)adaptor.create(INTEGER63);
                    adaptor.addChild(root_0, INTEGER63_tree);


                    }
                    break;
                case 4 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:120:4: STRING_LITERAL
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    STRING_LITERAL64=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_term517); 
                    STRING_LITERAL64_tree = (CommonTree)adaptor.create(STRING_LITERAL64);
                    adaptor.addChild(root_0, STRING_LITERAL64_tree);


                    }
                    break;
                case 5 :
                    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:121:4: functionInvocationExpression
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_functionInvocationExpression_in_term522);
                    functionInvocationExpression65=functionInvocationExpression();

                    state._fsp--;

                    adaptor.addChild(root_0, functionInvocationExpression65.getTree());

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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:124:1: boolNegation : ( 'not' )* term ;
    public final EveParser.boolNegation_return boolNegation() throws RecognitionException {
        EveParser.boolNegation_return retval = new EveParser.boolNegation_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal66=null;
        EveParser.term_return term67 = null;


        CommonTree string_literal66_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:125:2: ( ( 'not' )* term )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:125:4: ( 'not' )* term
            {
            root_0 = (CommonTree)adaptor.nil();

            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:125:4: ( 'not' )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==32) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:125:4: 'not'
            	    {
            	    string_literal66=(Token)match(input,32,FOLLOW_32_in_boolNegation534); 
            	    string_literal66_tree = (CommonTree)adaptor.create(string_literal66);
            	    adaptor.addChild(root_0, string_literal66_tree);


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            pushFollow(FOLLOW_term_in_boolNegation537);
            term67=term();

            state._fsp--;

            adaptor.addChild(root_0, term67.getTree());

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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:128:1: unary : ( '+' | negation )* boolNegation ;
    public final EveParser.unary_return unary() throws RecognitionException {
        EveParser.unary_return retval = new EveParser.unary_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal68=null;
        EveParser.negation_return negation69 = null;

        EveParser.boolNegation_return boolNegation70 = null;


        CommonTree char_literal68_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:129:2: ( ( '+' | negation )* boolNegation )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:129:4: ( '+' | negation )* boolNegation
            {
            root_0 = (CommonTree)adaptor.nil();

            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:129:4: ( '+' | negation )*
            loop12:
            do {
                int alt12=3;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==33) ) {
                    alt12=1;
                }
                else if ( (LA12_0==34) ) {
                    alt12=2;
                }


                switch (alt12) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:129:5: '+'
            	    {
            	    char_literal68=(Token)match(input,33,FOLLOW_33_in_unary550); 

            	    }
            	    break;
            	case 2 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:129:12: negation
            	    {
            	    pushFollow(FOLLOW_negation_in_unary555);
            	    negation69=negation();

            	    state._fsp--;

            	    root_0 = (CommonTree)adaptor.becomeRoot(negation69.getTree(), root_0);

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            pushFollow(FOLLOW_boolNegation_in_unary560);
            boolNegation70=boolNegation();

            state._fsp--;

            adaptor.addChild(root_0, boolNegation70.getTree());

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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:133:1: negation : '-' -> NEGATION ;
    public final EveParser.negation_return negation() throws RecognitionException {
        EveParser.negation_return retval = new EveParser.negation_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal71=null;

        CommonTree char_literal71_tree=null;
        RewriteRuleTokenStream stream_34=new RewriteRuleTokenStream(adaptor,"token 34");

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:134:2: ( '-' -> NEGATION )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:134:4: '-'
            {
            char_literal71=(Token)match(input,34,FOLLOW_34_in_negation574);  
            stream_34.add(char_literal71);



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
            // 134:8: -> NEGATION
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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:137:1: mult : unary ( ( '*' | '/' | '%' ) unary )* ;
    public final EveParser.mult_return mult() throws RecognitionException {
        EveParser.mult_return retval = new EveParser.mult_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal73=null;
        Token char_literal74=null;
        Token char_literal75=null;
        EveParser.unary_return unary72 = null;

        EveParser.unary_return unary76 = null;


        CommonTree char_literal73_tree=null;
        CommonTree char_literal74_tree=null;
        CommonTree char_literal75_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:138:2: ( unary ( ( '*' | '/' | '%' ) unary )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:138:4: unary ( ( '*' | '/' | '%' ) unary )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_unary_in_mult589);
            unary72=unary();

            state._fsp--;

            adaptor.addChild(root_0, unary72.getTree());
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:138:10: ( ( '*' | '/' | '%' ) unary )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>=35 && LA14_0<=37)) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:138:11: ( '*' | '/' | '%' ) unary
            	    {
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:138:11: ( '*' | '/' | '%' )
            	    int alt13=3;
            	    switch ( input.LA(1) ) {
            	    case 35:
            	        {
            	        alt13=1;
            	        }
            	        break;
            	    case 36:
            	        {
            	        alt13=2;
            	        }
            	        break;
            	    case 37:
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
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:138:12: '*'
            	            {
            	            char_literal73=(Token)match(input,35,FOLLOW_35_in_mult593); 
            	            char_literal73_tree = (CommonTree)adaptor.create(char_literal73);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal73_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:138:19: '/'
            	            {
            	            char_literal74=(Token)match(input,36,FOLLOW_36_in_mult598); 
            	            char_literal74_tree = (CommonTree)adaptor.create(char_literal74);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal74_tree, root_0);


            	            }
            	            break;
            	        case 3 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:138:26: '%'
            	            {
            	            char_literal75=(Token)match(input,37,FOLLOW_37_in_mult603); 
            	            char_literal75_tree = (CommonTree)adaptor.create(char_literal75);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal75_tree, root_0);


            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_unary_in_mult607);
            	    unary76=unary();

            	    state._fsp--;

            	    adaptor.addChild(root_0, unary76.getTree());

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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:141:1: add : mult ( ( '+' | '-' ) mult )* ;
    public final EveParser.add_return add() throws RecognitionException {
        EveParser.add_return retval = new EveParser.add_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal78=null;
        Token char_literal79=null;
        EveParser.mult_return mult77 = null;

        EveParser.mult_return mult80 = null;


        CommonTree char_literal78_tree=null;
        CommonTree char_literal79_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:142:2: ( mult ( ( '+' | '-' ) mult )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:142:4: mult ( ( '+' | '-' ) mult )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_mult_in_add621);
            mult77=mult();

            state._fsp--;

            adaptor.addChild(root_0, mult77.getTree());
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:142:9: ( ( '+' | '-' ) mult )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>=33 && LA16_0<=34)) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:142:10: ( '+' | '-' ) mult
            	    {
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:142:10: ( '+' | '-' )
            	    int alt15=2;
            	    int LA15_0 = input.LA(1);

            	    if ( (LA15_0==33) ) {
            	        alt15=1;
            	    }
            	    else if ( (LA15_0==34) ) {
            	        alt15=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 15, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt15) {
            	        case 1 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:142:11: '+'
            	            {
            	            char_literal78=(Token)match(input,33,FOLLOW_33_in_add625); 
            	            char_literal78_tree = (CommonTree)adaptor.create(char_literal78);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal78_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:142:18: '-'
            	            {
            	            char_literal79=(Token)match(input,34,FOLLOW_34_in_add630); 
            	            char_literal79_tree = (CommonTree)adaptor.create(char_literal79);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal79_tree, root_0);


            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_mult_in_add634);
            	    mult80=mult();

            	    state._fsp--;

            	    adaptor.addChild(root_0, mult80.getTree());

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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:145:1: relation : add ( ( '=' | '/=' | '<' | '<=' | '>=' | '>' ) add )* ;
    public final EveParser.relation_return relation() throws RecognitionException {
        EveParser.relation_return retval = new EveParser.relation_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal82=null;
        Token string_literal83=null;
        Token char_literal84=null;
        Token string_literal85=null;
        Token string_literal86=null;
        Token char_literal87=null;
        EveParser.add_return add81 = null;

        EveParser.add_return add88 = null;


        CommonTree char_literal82_tree=null;
        CommonTree string_literal83_tree=null;
        CommonTree char_literal84_tree=null;
        CommonTree string_literal85_tree=null;
        CommonTree string_literal86_tree=null;
        CommonTree char_literal87_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:146:2: ( add ( ( '=' | '/=' | '<' | '<=' | '>=' | '>' ) add )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:146:4: add ( ( '=' | '/=' | '<' | '<=' | '>=' | '>' ) add )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_add_in_relation647);
            add81=add();

            state._fsp--;

            adaptor.addChild(root_0, add81.getTree());
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:146:8: ( ( '=' | '/=' | '<' | '<=' | '>=' | '>' ) add )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==25||(LA18_0>=38 && LA18_0<=42)) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:146:9: ( '=' | '/=' | '<' | '<=' | '>=' | '>' ) add
            	    {
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:146:9: ( '=' | '/=' | '<' | '<=' | '>=' | '>' )
            	    int alt17=6;
            	    switch ( input.LA(1) ) {
            	    case 25:
            	        {
            	        alt17=1;
            	        }
            	        break;
            	    case 38:
            	        {
            	        alt17=2;
            	        }
            	        break;
            	    case 39:
            	        {
            	        alt17=3;
            	        }
            	        break;
            	    case 40:
            	        {
            	        alt17=4;
            	        }
            	        break;
            	    case 41:
            	        {
            	        alt17=5;
            	        }
            	        break;
            	    case 42:
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
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:146:10: '='
            	            {
            	            char_literal82=(Token)match(input,25,FOLLOW_25_in_relation651); 
            	            char_literal82_tree = (CommonTree)adaptor.create(char_literal82);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal82_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:146:17: '/='
            	            {
            	            string_literal83=(Token)match(input,38,FOLLOW_38_in_relation656); 
            	            string_literal83_tree = (CommonTree)adaptor.create(string_literal83);
            	            root_0 = (CommonTree)adaptor.becomeRoot(string_literal83_tree, root_0);


            	            }
            	            break;
            	        case 3 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:146:25: '<'
            	            {
            	            char_literal84=(Token)match(input,39,FOLLOW_39_in_relation661); 
            	            char_literal84_tree = (CommonTree)adaptor.create(char_literal84);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal84_tree, root_0);


            	            }
            	            break;
            	        case 4 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:146:32: '<='
            	            {
            	            string_literal85=(Token)match(input,40,FOLLOW_40_in_relation666); 
            	            string_literal85_tree = (CommonTree)adaptor.create(string_literal85);
            	            root_0 = (CommonTree)adaptor.becomeRoot(string_literal85_tree, root_0);


            	            }
            	            break;
            	        case 5 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:146:40: '>='
            	            {
            	            string_literal86=(Token)match(input,41,FOLLOW_41_in_relation671); 
            	            string_literal86_tree = (CommonTree)adaptor.create(string_literal86);
            	            root_0 = (CommonTree)adaptor.becomeRoot(string_literal86_tree, root_0);


            	            }
            	            break;
            	        case 6 :
            	            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:146:48: '>'
            	            {
            	            char_literal87=(Token)match(input,42,FOLLOW_42_in_relation676); 
            	            char_literal87_tree = (CommonTree)adaptor.create(char_literal87);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal87_tree, root_0);


            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_add_in_relation680);
            	    add88=add();

            	    state._fsp--;

            	    adaptor.addChild(root_0, add88.getTree());

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
    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:149:1: expression : relation ( ( 'and' | 'or' ) relation )* ;
    public final EveParser.expression_return expression() throws RecognitionException {
        EveParser.expression_return retval = new EveParser.expression_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token set90=null;
        EveParser.relation_return relation89 = null;

        EveParser.relation_return relation91 = null;


        CommonTree set90_tree=null;

        try {
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:150:2: ( relation ( ( 'and' | 'or' ) relation )* )
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:150:4: relation ( ( 'and' | 'or' ) relation )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_relation_in_expression694);
            relation89=relation();

            state._fsp--;

            adaptor.addChild(root_0, relation89.getTree());
            // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:150:13: ( ( 'and' | 'or' ) relation )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>=43 && LA19_0<=44)) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // /home/jeff/Projects/workspace/eve2/src/eve/core/Eve.g:150:14: ( 'and' | 'or' ) relation
            	    {
            	    set90=(Token)input.LT(1);
            	    if ( (input.LA(1)>=43 && input.LA(1)<=44) ) {
            	        input.consume();
            	        adaptor.addChild(root_0, (CommonTree)adaptor.create(set90));
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_relation_in_expression705);
            	    relation91=relation();

            	    state._fsp--;

            	    adaptor.addChild(root_0, relation91.getTree());

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


 

    public static final BitSet FOLLOW_statement_in_program96 = new BitSet(new long[]{0x000000001C201002L});
    public static final BitSet FOLLOW_codeStatement_in_statement109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_protoStatement_in_statement114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_printStatement_in_codeStatement127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentStatement_in_codeStatement132 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_initVariableStatement_in_codeStatement137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionInvocationStatement_in_codeStatement142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_printStatement154 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_printStatement157 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_expression_in_printStatement160 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_printStatement162 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_printStatement165 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_assignmentStatement178 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_assignmentStatement180 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_expression_in_assignmentStatement182 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_assignmentStatement184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_initFunction_in_assignmentStatement199 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_IDENT_in_assignmentStatement201 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_assignmentStatement203 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_function_in_assignmentStatement206 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_24_in_assignmentStatement208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_initFunction220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_initVariable_in_initVariableStatement236 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_IDENT_in_initVariableStatement239 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_initVariableStatement241 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_expression_in_initVariableStatement244 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_initVariableStatement246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_initVariable258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_protoStatement274 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_IDENT_in_protoStatement277 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_protoStatement279 = new BitSet(new long[]{0x000000004C201000L});
    public static final BitSet FOLLOW_protoBlock_in_protoStatement282 = new BitSet(new long[]{0x000000004C201000L});
    public static final BitSet FOLLOW_30_in_protoStatement285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_codeStatement_in_protoBlock298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_functionInvocationStatement310 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_functionInvocationStatement312 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_functionInvocationParameters_in_functionInvocationStatement314 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_functionInvocationStatement316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_functionInvocationParameters338 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_31_in_functionInvocationParameters341 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_expression_in_functionInvocationParameters343 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_parameters_in_function366 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_functionBody_in_function370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionBodyToken_in_functionBody382 = new BitSet(new long[]{0x000000004C201000L});
    public static final BitSet FOLLOW_codeStatement_in_functionBody385 = new BitSet(new long[]{0x000000004C201000L});
    public static final BitSet FOLLOW_30_in_functionBody388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_functionBodyToken400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parametersStartToken_in_parameters416 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_parameter_in_parameters419 = new BitSet(new long[]{0x0000000080800000L});
    public static final BitSet FOLLOW_31_in_parameters422 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_parameter_in_parameters425 = new BitSet(new long[]{0x0000000080800000L});
    public static final BitSet FOLLOW_23_in_parameters429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_parametersStartToken442 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_parameter457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_functionInvocationExpression468 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_functionInvocationExpression470 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_functionInvocationParameters_in_functionInvocationExpression472 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_functionInvocationExpression474 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_term496 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_term501 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_expression_in_term504 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_term506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_term512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_term517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionInvocationExpression_in_term522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_boolNegation534 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_term_in_boolNegation537 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_unary550 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_negation_in_unary555 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_boolNegation_in_unary560 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_negation574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unary_in_mult589 = new BitSet(new long[]{0x0000003800000002L});
    public static final BitSet FOLLOW_35_in_mult593 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_36_in_mult598 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_37_in_mult603 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_unary_in_mult607 = new BitSet(new long[]{0x0000003800000002L});
    public static final BitSet FOLLOW_mult_in_add621 = new BitSet(new long[]{0x0000000600000002L});
    public static final BitSet FOLLOW_33_in_add625 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_34_in_add630 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_mult_in_add634 = new BitSet(new long[]{0x0000000600000002L});
    public static final BitSet FOLLOW_add_in_relation647 = new BitSet(new long[]{0x000007C002000002L});
    public static final BitSet FOLLOW_25_in_relation651 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_38_in_relation656 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_39_in_relation661 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_40_in_relation666 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_41_in_relation671 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_42_in_relation676 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_add_in_relation680 = new BitSet(new long[]{0x000007C002000002L});
    public static final BitSet FOLLOW_relation_in_expression694 = new BitSet(new long[]{0x0000180000000002L});
    public static final BitSet FOLLOW_set_in_expression697 = new BitSet(new long[]{0x0000000700407000L});
    public static final BitSet FOLLOW_relation_in_expression705 = new BitSet(new long[]{0x0000180000000002L});

}