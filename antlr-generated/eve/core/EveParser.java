// $ANTLR 3.3 Nov 30, 2010 12:50:56 /home/jeff/workspace/eve/src/eve/core/Eve.g 2011-04-18 07:59:36

	package eve.core;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class EveParser extends Parser {
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
    public String getGrammarFileName() { return "/home/jeff/workspace/eve/src/eve/core/Eve.g"; }


    public static class program_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "program"
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:28:1: program : ( statement )* ;
    public final EveParser.program_return program() throws RecognitionException {
        EveParser.program_return retval = new EveParser.program_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        EveParser.statement_return statement1 = null;



        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:29:2: ( ( statement )* )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:29:4: ( statement )*
            {
            root_0 = (CommonTree)adaptor.nil();

            // /home/jeff/workspace/eve/src/eve/core/Eve.g:29:4: ( statement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==IDENT||LA1_0==18||(LA1_0>=23 && LA1_0<=25)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:29:4: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_program84);
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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:33:1: statement : ( codeStatement | protoStatement );
    public final EveParser.statement_return statement() throws RecognitionException {
        EveParser.statement_return retval = new EveParser.statement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        EveParser.codeStatement_return codeStatement2 = null;

        EveParser.protoStatement_return protoStatement3 = null;



        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:34:2: ( codeStatement | protoStatement )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==IDENT||LA2_0==18||(LA2_0>=23 && LA2_0<=24)) ) {
                alt2=1;
            }
            else if ( (LA2_0==25) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // /home/jeff/workspace/eve/src/eve/core/Eve.g:34:4: codeStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_codeStatement_in_statement97);
                    codeStatement2=codeStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, codeStatement2.getTree());

                    }
                    break;
                case 2 :
                    // /home/jeff/workspace/eve/src/eve/core/Eve.g:35:4: protoStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_protoStatement_in_statement102);
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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:38:1: codeStatement : ( printStatement | assignmentStatement | initVariableStatement );
    public final EveParser.codeStatement_return codeStatement() throws RecognitionException {
        EveParser.codeStatement_return retval = new EveParser.codeStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        EveParser.printStatement_return printStatement4 = null;

        EveParser.assignmentStatement_return assignmentStatement5 = null;

        EveParser.initVariableStatement_return initVariableStatement6 = null;



        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:39:2: ( printStatement | assignmentStatement | initVariableStatement )
            int alt3=3;
            switch ( input.LA(1) ) {
            case 18:
                {
                alt3=1;
                }
                break;
            case IDENT:
            case 23:
                {
                alt3=2;
                }
                break;
            case 24:
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
                    // /home/jeff/workspace/eve/src/eve/core/Eve.g:39:4: printStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_printStatement_in_codeStatement115);
                    printStatement4=printStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, printStatement4.getTree());

                    }
                    break;
                case 2 :
                    // /home/jeff/workspace/eve/src/eve/core/Eve.g:40:4: assignmentStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_assignmentStatement_in_codeStatement120);
                    assignmentStatement5=assignmentStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, assignmentStatement5.getTree());

                    }
                    break;
                case 3 :
                    // /home/jeff/workspace/eve/src/eve/core/Eve.g:41:4: initVariableStatement
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_initVariableStatement_in_codeStatement125);
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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:44:1: printStatement : 'print' '(' expression ')' ';' ;
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
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:45:2: ( 'print' '(' expression ')' ';' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:45:4: 'print' '(' expression ')' ';'
            {
            root_0 = (CommonTree)adaptor.nil();

            string_literal7=(Token)match(input,18,FOLLOW_18_in_printStatement137); 
            string_literal7_tree = (CommonTree)adaptor.create(string_literal7);
            root_0 = (CommonTree)adaptor.becomeRoot(string_literal7_tree, root_0);

            char_literal8=(Token)match(input,19,FOLLOW_19_in_printStatement140); 
            pushFollow(FOLLOW_expression_in_printStatement143);
            expression9=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression9.getTree());
            char_literal10=(Token)match(input,20,FOLLOW_20_in_printStatement145); 
            char_literal11=(Token)match(input,21,FOLLOW_21_in_printStatement148); 

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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:48:1: assignmentStatement : ( IDENT '=' expression ';' | initFunction IDENT '=' function );
    public final EveParser.assignmentStatement_return assignmentStatement() throws RecognitionException {
        EveParser.assignmentStatement_return retval = new EveParser.assignmentStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENT12=null;
        Token char_literal13=null;
        Token char_literal15=null;
        Token IDENT17=null;
        Token char_literal18=null;
        EveParser.expression_return expression14 = null;

        EveParser.initFunction_return initFunction16 = null;

        EveParser.function_return function19 = null;


        CommonTree IDENT12_tree=null;
        CommonTree char_literal13_tree=null;
        CommonTree char_literal15_tree=null;
        CommonTree IDENT17_tree=null;
        CommonTree char_literal18_tree=null;

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:49:2: ( IDENT '=' expression ';' | initFunction IDENT '=' function )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==IDENT) ) {
                alt4=1;
            }
            else if ( (LA4_0==23) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // /home/jeff/workspace/eve/src/eve/core/Eve.g:49:4: IDENT '=' expression ';'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    IDENT12=(Token)match(input,IDENT,FOLLOW_IDENT_in_assignmentStatement161); 
                    IDENT12_tree = (CommonTree)adaptor.create(IDENT12);
                    adaptor.addChild(root_0, IDENT12_tree);

                    char_literal13=(Token)match(input,22,FOLLOW_22_in_assignmentStatement163); 
                    char_literal13_tree = (CommonTree)adaptor.create(char_literal13);
                    root_0 = (CommonTree)adaptor.becomeRoot(char_literal13_tree, root_0);

                    pushFollow(FOLLOW_expression_in_assignmentStatement166);
                    expression14=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression14.getTree());
                    char_literal15=(Token)match(input,21,FOLLOW_21_in_assignmentStatement168); 

                    }
                    break;
                case 2 :
                    // /home/jeff/workspace/eve/src/eve/core/Eve.g:50:4: initFunction IDENT '=' function
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_initFunction_in_assignmentStatement174);
                    initFunction16=initFunction();

                    state._fsp--;

                    adaptor.addChild(root_0, initFunction16.getTree());
                    IDENT17=(Token)match(input,IDENT,FOLLOW_IDENT_in_assignmentStatement176); 
                    IDENT17_tree = (CommonTree)adaptor.create(IDENT17);
                    adaptor.addChild(root_0, IDENT17_tree);

                    char_literal18=(Token)match(input,22,FOLLOW_22_in_assignmentStatement178); 
                    char_literal18_tree = (CommonTree)adaptor.create(char_literal18);
                    root_0 = (CommonTree)adaptor.becomeRoot(char_literal18_tree, root_0);

                    pushFollow(FOLLOW_function_in_assignmentStatement181);
                    function19=function();

                    state._fsp--;

                    adaptor.addChild(root_0, function19.getTree());

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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:53:1: initFunction : 'def' -> INIT_FUNCTION ;
    public final EveParser.initFunction_return initFunction() throws RecognitionException {
        EveParser.initFunction_return retval = new EveParser.initFunction_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal20=null;

        CommonTree string_literal20_tree=null;
        RewriteRuleTokenStream stream_23=new RewriteRuleTokenStream(adaptor,"token 23");

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:54:2: ( 'def' -> INIT_FUNCTION )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:54:4: 'def'
            {
            string_literal20=(Token)match(input,23,FOLLOW_23_in_initFunction192);  
            stream_23.add(string_literal20);



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
            // 54:10: -> INIT_FUNCTION
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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:57:1: initVariableStatement : initVariable IDENT '=' expression ';' ;
    public final EveParser.initVariableStatement_return initVariableStatement() throws RecognitionException {
        EveParser.initVariableStatement_return retval = new EveParser.initVariableStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENT22=null;
        Token char_literal23=null;
        Token char_literal25=null;
        EveParser.initVariable_return initVariable21 = null;

        EveParser.expression_return expression24 = null;


        CommonTree IDENT22_tree=null;
        CommonTree char_literal23_tree=null;
        CommonTree char_literal25_tree=null;

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:58:2: ( initVariable IDENT '=' expression ';' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:58:4: initVariable IDENT '=' expression ';'
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_initVariable_in_initVariableStatement208);
            initVariable21=initVariable();

            state._fsp--;

            root_0 = (CommonTree)adaptor.becomeRoot(initVariable21.getTree(), root_0);
            IDENT22=(Token)match(input,IDENT,FOLLOW_IDENT_in_initVariableStatement211); 
            IDENT22_tree = (CommonTree)adaptor.create(IDENT22);
            adaptor.addChild(root_0, IDENT22_tree);

            char_literal23=(Token)match(input,22,FOLLOW_22_in_initVariableStatement213); 
            pushFollow(FOLLOW_expression_in_initVariableStatement216);
            expression24=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression24.getTree());
            char_literal25=(Token)match(input,21,FOLLOW_21_in_initVariableStatement218); 

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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:61:1: initVariable : 'var' -> INIT_VARIABLE ;
    public final EveParser.initVariable_return initVariable() throws RecognitionException {
        EveParser.initVariable_return retval = new EveParser.initVariable_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal26=null;

        CommonTree string_literal26_tree=null;
        RewriteRuleTokenStream stream_24=new RewriteRuleTokenStream(adaptor,"token 24");

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:62:2: ( 'var' -> INIT_VARIABLE )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:62:4: 'var'
            {
            string_literal26=(Token)match(input,24,FOLLOW_24_in_initVariable230);  
            stream_24.add(string_literal26);



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
            // 62:10: -> INIT_VARIABLE
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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:65:1: protoStatement : 'proto' IDENT '{' ( protoBlock )* '}' ;
    public final EveParser.protoStatement_return protoStatement() throws RecognitionException {
        EveParser.protoStatement_return retval = new EveParser.protoStatement_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal27=null;
        Token IDENT28=null;
        Token char_literal29=null;
        Token char_literal31=null;
        EveParser.protoBlock_return protoBlock30 = null;


        CommonTree string_literal27_tree=null;
        CommonTree IDENT28_tree=null;
        CommonTree char_literal29_tree=null;
        CommonTree char_literal31_tree=null;

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:66:2: ( 'proto' IDENT '{' ( protoBlock )* '}' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:66:4: 'proto' IDENT '{' ( protoBlock )* '}'
            {
            root_0 = (CommonTree)adaptor.nil();

            string_literal27=(Token)match(input,25,FOLLOW_25_in_protoStatement246); 
            string_literal27_tree = (CommonTree)adaptor.create(string_literal27);
            root_0 = (CommonTree)adaptor.becomeRoot(string_literal27_tree, root_0);

            IDENT28=(Token)match(input,IDENT,FOLLOW_IDENT_in_protoStatement249); 
            IDENT28_tree = (CommonTree)adaptor.create(IDENT28);
            adaptor.addChild(root_0, IDENT28_tree);

            char_literal29=(Token)match(input,26,FOLLOW_26_in_protoStatement251); 
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:66:24: ( protoBlock )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==IDENT||LA5_0==18||(LA5_0>=23 && LA5_0<=24)) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:66:24: protoBlock
            	    {
            	    pushFollow(FOLLOW_protoBlock_in_protoStatement254);
            	    protoBlock30=protoBlock();

            	    state._fsp--;

            	    adaptor.addChild(root_0, protoBlock30.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            char_literal31=(Token)match(input,27,FOLLOW_27_in_protoStatement257); 

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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:69:1: protoBlock : codeStatement ;
    public final EveParser.protoBlock_return protoBlock() throws RecognitionException {
        EveParser.protoBlock_return retval = new EveParser.protoBlock_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        EveParser.codeStatement_return codeStatement32 = null;



        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:70:2: ( codeStatement )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:70:4: codeStatement
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_codeStatement_in_protoBlock270);
            codeStatement32=codeStatement();

            state._fsp--;

            adaptor.addChild(root_0, codeStatement32.getTree());

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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:74:1: actualParameters : expression ( ',' expression )* ;
    public final EveParser.actualParameters_return actualParameters() throws RecognitionException {
        EveParser.actualParameters_return retval = new EveParser.actualParameters_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal34=null;
        EveParser.expression_return expression33 = null;

        EveParser.expression_return expression35 = null;


        CommonTree char_literal34_tree=null;

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:75:2: ( expression ( ',' expression )* )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:75:4: expression ( ',' expression )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_expression_in_actualParameters282);
            expression33=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression33.getTree());
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:75:15: ( ',' expression )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==28) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:75:16: ',' expression
            	    {
            	    char_literal34=(Token)match(input,28,FOLLOW_28_in_actualParameters285); 
            	    char_literal34_tree = (CommonTree)adaptor.create(char_literal34);
            	    adaptor.addChild(root_0, char_literal34_tree);

            	    pushFollow(FOLLOW_expression_in_actualParameters287);
            	    expression35=expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, expression35.getTree());

            	    }
            	    break;

            	default :
            	    break loop6;
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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:79:1: function : parameters functionBody ;
    public final EveParser.function_return function() throws RecognitionException {
        EveParser.function_return retval = new EveParser.function_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        EveParser.parameters_return parameters36 = null;

        EveParser.functionBody_return functionBody37 = null;



        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:80:2: ( parameters functionBody )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:80:4: parameters functionBody
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_parameters_in_function303);
            parameters36=parameters();

            state._fsp--;

            adaptor.addChild(root_0, parameters36.getTree());
            pushFollow(FOLLOW_functionBody_in_function307);
            functionBody37=functionBody();

            state._fsp--;

            adaptor.addChild(root_0, functionBody37.getTree());

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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:84:1: functionBody : functionBodyToken ( codeStatement )* '}' ;
    public final EveParser.functionBody_return functionBody() throws RecognitionException {
        EveParser.functionBody_return retval = new EveParser.functionBody_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal40=null;
        EveParser.functionBodyToken_return functionBodyToken38 = null;

        EveParser.codeStatement_return codeStatement39 = null;


        CommonTree char_literal40_tree=null;

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:85:2: ( functionBodyToken ( codeStatement )* '}' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:85:4: functionBodyToken ( codeStatement )* '}'
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_functionBodyToken_in_functionBody319);
            functionBodyToken38=functionBodyToken();

            state._fsp--;

            root_0 = (CommonTree)adaptor.becomeRoot(functionBodyToken38.getTree(), root_0);
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:85:23: ( codeStatement )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==IDENT||LA7_0==18||(LA7_0>=23 && LA7_0<=24)) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:85:23: codeStatement
            	    {
            	    pushFollow(FOLLOW_codeStatement_in_functionBody322);
            	    codeStatement39=codeStatement();

            	    state._fsp--;

            	    adaptor.addChild(root_0, codeStatement39.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            char_literal40=(Token)match(input,27,FOLLOW_27_in_functionBody325); 

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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:88:1: functionBodyToken : '{' -> FUNCTION_BODY ;
    public final EveParser.functionBodyToken_return functionBodyToken() throws RecognitionException {
        EveParser.functionBodyToken_return retval = new EveParser.functionBodyToken_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal41=null;

        CommonTree char_literal41_tree=null;
        RewriteRuleTokenStream stream_26=new RewriteRuleTokenStream(adaptor,"token 26");

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:89:2: ( '{' -> FUNCTION_BODY )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:89:4: '{'
            {
            char_literal41=(Token)match(input,26,FOLLOW_26_in_functionBodyToken337);  
            stream_26.add(char_literal41);



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
            // 89:8: -> FUNCTION_BODY
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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:92:1: parameters : parametersStartToken parameter ( ',' parameter )* ')' ;
    public final EveParser.parameters_return parameters() throws RecognitionException {
        EveParser.parameters_return retval = new EveParser.parameters_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal44=null;
        Token char_literal46=null;
        EveParser.parametersStartToken_return parametersStartToken42 = null;

        EveParser.parameter_return parameter43 = null;

        EveParser.parameter_return parameter45 = null;


        CommonTree char_literal44_tree=null;
        CommonTree char_literal46_tree=null;

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:93:2: ( parametersStartToken parameter ( ',' parameter )* ')' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:93:4: parametersStartToken parameter ( ',' parameter )* ')'
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_parametersStartToken_in_parameters353);
            parametersStartToken42=parametersStartToken();

            state._fsp--;

            root_0 = (CommonTree)adaptor.becomeRoot(parametersStartToken42.getTree(), root_0);
            pushFollow(FOLLOW_parameter_in_parameters356);
            parameter43=parameter();

            state._fsp--;

            adaptor.addChild(root_0, parameter43.getTree());
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:93:36: ( ',' parameter )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==28) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:93:37: ',' parameter
            	    {
            	    char_literal44=(Token)match(input,28,FOLLOW_28_in_parameters359); 
            	    pushFollow(FOLLOW_parameter_in_parameters362);
            	    parameter45=parameter();

            	    state._fsp--;

            	    adaptor.addChild(root_0, parameter45.getTree());

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            char_literal46=(Token)match(input,20,FOLLOW_20_in_parameters366); 

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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:96:1: parametersStartToken : '(' -> FUNCTION_PARAMETERS ;
    public final EveParser.parametersStartToken_return parametersStartToken() throws RecognitionException {
        EveParser.parametersStartToken_return retval = new EveParser.parametersStartToken_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal47=null;

        CommonTree char_literal47_tree=null;
        RewriteRuleTokenStream stream_19=new RewriteRuleTokenStream(adaptor,"token 19");

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:97:2: ( '(' -> FUNCTION_PARAMETERS )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:97:4: '('
            {
            char_literal47=(Token)match(input,19,FOLLOW_19_in_parametersStartToken379);  
            stream_19.add(char_literal47);



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
            // 97:8: -> FUNCTION_PARAMETERS
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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:100:1: parameter returns [String param] : IDENT ;
    public final EveParser.parameter_return parameter() throws RecognitionException {
        EveParser.parameter_return retval = new EveParser.parameter_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENT48=null;

        CommonTree IDENT48_tree=null;

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:101:2: ( IDENT )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:101:4: IDENT
            {
            root_0 = (CommonTree)adaptor.nil();

            IDENT48=(Token)match(input,IDENT,FOLLOW_IDENT_in_parameter398); 
            IDENT48_tree = (CommonTree)adaptor.create(IDENT48);
            adaptor.addChild(root_0, IDENT48_tree);

             retval.param = (IDENT48!=null?IDENT48.getText():null); 

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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:105:1: term : ( IDENT | '(' expression ')' | INTEGER | STRING_LITERAL | IDENT '(' actualParameters ')' );
    public final EveParser.term_return term() throws RecognitionException {
        EveParser.term_return retval = new EveParser.term_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token IDENT49=null;
        Token char_literal50=null;
        Token char_literal52=null;
        Token INTEGER53=null;
        Token STRING_LITERAL54=null;
        Token IDENT55=null;
        Token char_literal56=null;
        Token char_literal58=null;
        EveParser.expression_return expression51 = null;

        EveParser.actualParameters_return actualParameters57 = null;


        CommonTree IDENT49_tree=null;
        CommonTree char_literal50_tree=null;
        CommonTree char_literal52_tree=null;
        CommonTree INTEGER53_tree=null;
        CommonTree STRING_LITERAL54_tree=null;
        CommonTree IDENT55_tree=null;
        CommonTree char_literal56_tree=null;
        CommonTree char_literal58_tree=null;

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:106:2: ( IDENT | '(' expression ')' | INTEGER | STRING_LITERAL | IDENT '(' actualParameters ')' )
            int alt9=5;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA9_1 = input.LA(2);

                if ( (LA9_1==19) ) {
                    alt9=5;
                }
                else if ( ((LA9_1>=20 && LA9_1<=22)||LA9_1==28||(LA9_1>=30 && LA9_1<=41)) ) {
                    alt9=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 1, input);

                    throw nvae;
                }
                }
                break;
            case 19:
                {
                alt9=2;
                }
                break;
            case INTEGER:
                {
                alt9=3;
                }
                break;
            case STRING_LITERAL:
                {
                alt9=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // /home/jeff/workspace/eve/src/eve/core/Eve.g:106:4: IDENT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    IDENT49=(Token)match(input,IDENT,FOLLOW_IDENT_in_term412); 
                    IDENT49_tree = (CommonTree)adaptor.create(IDENT49);
                    adaptor.addChild(root_0, IDENT49_tree);


                    }
                    break;
                case 2 :
                    // /home/jeff/workspace/eve/src/eve/core/Eve.g:107:4: '(' expression ')'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    char_literal50=(Token)match(input,19,FOLLOW_19_in_term417); 
                    pushFollow(FOLLOW_expression_in_term420);
                    expression51=expression();

                    state._fsp--;

                    adaptor.addChild(root_0, expression51.getTree());
                    char_literal52=(Token)match(input,20,FOLLOW_20_in_term422); 

                    }
                    break;
                case 3 :
                    // /home/jeff/workspace/eve/src/eve/core/Eve.g:108:4: INTEGER
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    INTEGER53=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_term428); 
                    INTEGER53_tree = (CommonTree)adaptor.create(INTEGER53);
                    adaptor.addChild(root_0, INTEGER53_tree);


                    }
                    break;
                case 4 :
                    // /home/jeff/workspace/eve/src/eve/core/Eve.g:109:4: STRING_LITERAL
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    STRING_LITERAL54=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_term433); 
                    STRING_LITERAL54_tree = (CommonTree)adaptor.create(STRING_LITERAL54);
                    adaptor.addChild(root_0, STRING_LITERAL54_tree);


                    }
                    break;
                case 5 :
                    // /home/jeff/workspace/eve/src/eve/core/Eve.g:110:4: IDENT '(' actualParameters ')'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    IDENT55=(Token)match(input,IDENT,FOLLOW_IDENT_in_term438); 
                    IDENT55_tree = (CommonTree)adaptor.create(IDENT55);
                    adaptor.addChild(root_0, IDENT55_tree);

                    char_literal56=(Token)match(input,19,FOLLOW_19_in_term440); 
                    char_literal56_tree = (CommonTree)adaptor.create(char_literal56);
                    adaptor.addChild(root_0, char_literal56_tree);

                    pushFollow(FOLLOW_actualParameters_in_term442);
                    actualParameters57=actualParameters();

                    state._fsp--;

                    adaptor.addChild(root_0, actualParameters57.getTree());
                    char_literal58=(Token)match(input,20,FOLLOW_20_in_term444); 
                    char_literal58_tree = (CommonTree)adaptor.create(char_literal58);
                    adaptor.addChild(root_0, char_literal58_tree);


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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:113:1: boolNegation : ( 'not' )* term ;
    public final EveParser.boolNegation_return boolNegation() throws RecognitionException {
        EveParser.boolNegation_return retval = new EveParser.boolNegation_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal59=null;
        EveParser.term_return term60 = null;


        CommonTree string_literal59_tree=null;

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:114:2: ( ( 'not' )* term )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:114:4: ( 'not' )* term
            {
            root_0 = (CommonTree)adaptor.nil();

            // /home/jeff/workspace/eve/src/eve/core/Eve.g:114:4: ( 'not' )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==29) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:114:4: 'not'
            	    {
            	    string_literal59=(Token)match(input,29,FOLLOW_29_in_boolNegation456); 
            	    string_literal59_tree = (CommonTree)adaptor.create(string_literal59);
            	    adaptor.addChild(root_0, string_literal59_tree);


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            pushFollow(FOLLOW_term_in_boolNegation459);
            term60=term();

            state._fsp--;

            adaptor.addChild(root_0, term60.getTree());

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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:117:1: unary : ( '+' | negation )* boolNegation ;
    public final EveParser.unary_return unary() throws RecognitionException {
        EveParser.unary_return retval = new EveParser.unary_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal61=null;
        EveParser.negation_return negation62 = null;

        EveParser.boolNegation_return boolNegation63 = null;


        CommonTree char_literal61_tree=null;

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:118:2: ( ( '+' | negation )* boolNegation )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:118:4: ( '+' | negation )* boolNegation
            {
            root_0 = (CommonTree)adaptor.nil();

            // /home/jeff/workspace/eve/src/eve/core/Eve.g:118:4: ( '+' | negation )*
            loop11:
            do {
                int alt11=3;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==30) ) {
                    alt11=1;
                }
                else if ( (LA11_0==31) ) {
                    alt11=2;
                }


                switch (alt11) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:118:5: '+'
            	    {
            	    char_literal61=(Token)match(input,30,FOLLOW_30_in_unary472); 

            	    }
            	    break;
            	case 2 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:118:12: negation
            	    {
            	    pushFollow(FOLLOW_negation_in_unary477);
            	    negation62=negation();

            	    state._fsp--;

            	    root_0 = (CommonTree)adaptor.becomeRoot(negation62.getTree(), root_0);

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            pushFollow(FOLLOW_boolNegation_in_unary482);
            boolNegation63=boolNegation();

            state._fsp--;

            adaptor.addChild(root_0, boolNegation63.getTree());

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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:122:1: negation : '-' -> NEGATION ;
    public final EveParser.negation_return negation() throws RecognitionException {
        EveParser.negation_return retval = new EveParser.negation_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal64=null;

        CommonTree char_literal64_tree=null;
        RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:123:2: ( '-' -> NEGATION )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:123:4: '-'
            {
            char_literal64=(Token)match(input,31,FOLLOW_31_in_negation496);  
            stream_31.add(char_literal64);



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
            // 123:8: -> NEGATION
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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:126:1: mult : unary ( ( '*' | '/' | '%' ) unary )* ;
    public final EveParser.mult_return mult() throws RecognitionException {
        EveParser.mult_return retval = new EveParser.mult_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal66=null;
        Token char_literal67=null;
        Token char_literal68=null;
        EveParser.unary_return unary65 = null;

        EveParser.unary_return unary69 = null;


        CommonTree char_literal66_tree=null;
        CommonTree char_literal67_tree=null;
        CommonTree char_literal68_tree=null;

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:127:2: ( unary ( ( '*' | '/' | '%' ) unary )* )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:127:4: unary ( ( '*' | '/' | '%' ) unary )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_unary_in_mult511);
            unary65=unary();

            state._fsp--;

            adaptor.addChild(root_0, unary65.getTree());
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:127:10: ( ( '*' | '/' | '%' ) unary )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>=32 && LA13_0<=34)) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:127:11: ( '*' | '/' | '%' ) unary
            	    {
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:127:11: ( '*' | '/' | '%' )
            	    int alt12=3;
            	    switch ( input.LA(1) ) {
            	    case 32:
            	        {
            	        alt12=1;
            	        }
            	        break;
            	    case 33:
            	        {
            	        alt12=2;
            	        }
            	        break;
            	    case 34:
            	        {
            	        alt12=3;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 12, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt12) {
            	        case 1 :
            	            // /home/jeff/workspace/eve/src/eve/core/Eve.g:127:12: '*'
            	            {
            	            char_literal66=(Token)match(input,32,FOLLOW_32_in_mult515); 
            	            char_literal66_tree = (CommonTree)adaptor.create(char_literal66);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal66_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /home/jeff/workspace/eve/src/eve/core/Eve.g:127:19: '/'
            	            {
            	            char_literal67=(Token)match(input,33,FOLLOW_33_in_mult520); 
            	            char_literal67_tree = (CommonTree)adaptor.create(char_literal67);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal67_tree, root_0);


            	            }
            	            break;
            	        case 3 :
            	            // /home/jeff/workspace/eve/src/eve/core/Eve.g:127:26: '%'
            	            {
            	            char_literal68=(Token)match(input,34,FOLLOW_34_in_mult525); 
            	            char_literal68_tree = (CommonTree)adaptor.create(char_literal68);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal68_tree, root_0);


            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_unary_in_mult529);
            	    unary69=unary();

            	    state._fsp--;

            	    adaptor.addChild(root_0, unary69.getTree());

            	    }
            	    break;

            	default :
            	    break loop13;
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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:130:1: add : mult ( ( '+' | '-' ) mult )* ;
    public final EveParser.add_return add() throws RecognitionException {
        EveParser.add_return retval = new EveParser.add_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal71=null;
        Token char_literal72=null;
        EveParser.mult_return mult70 = null;

        EveParser.mult_return mult73 = null;


        CommonTree char_literal71_tree=null;
        CommonTree char_literal72_tree=null;

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:131:2: ( mult ( ( '+' | '-' ) mult )* )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:131:4: mult ( ( '+' | '-' ) mult )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_mult_in_add543);
            mult70=mult();

            state._fsp--;

            adaptor.addChild(root_0, mult70.getTree());
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:131:9: ( ( '+' | '-' ) mult )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( ((LA15_0>=30 && LA15_0<=31)) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:131:10: ( '+' | '-' ) mult
            	    {
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:131:10: ( '+' | '-' )
            	    int alt14=2;
            	    int LA14_0 = input.LA(1);

            	    if ( (LA14_0==30) ) {
            	        alt14=1;
            	    }
            	    else if ( (LA14_0==31) ) {
            	        alt14=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 14, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt14) {
            	        case 1 :
            	            // /home/jeff/workspace/eve/src/eve/core/Eve.g:131:11: '+'
            	            {
            	            char_literal71=(Token)match(input,30,FOLLOW_30_in_add547); 
            	            char_literal71_tree = (CommonTree)adaptor.create(char_literal71);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal71_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /home/jeff/workspace/eve/src/eve/core/Eve.g:131:18: '-'
            	            {
            	            char_literal72=(Token)match(input,31,FOLLOW_31_in_add552); 
            	            char_literal72_tree = (CommonTree)adaptor.create(char_literal72);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal72_tree, root_0);


            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_mult_in_add556);
            	    mult73=mult();

            	    state._fsp--;

            	    adaptor.addChild(root_0, mult73.getTree());

            	    }
            	    break;

            	default :
            	    break loop15;
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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:134:1: relation : add ( ( '=' | '/=' | '<' | '<=' | '>=' | '>' ) add )* ;
    public final EveParser.relation_return relation() throws RecognitionException {
        EveParser.relation_return retval = new EveParser.relation_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal75=null;
        Token string_literal76=null;
        Token char_literal77=null;
        Token string_literal78=null;
        Token string_literal79=null;
        Token char_literal80=null;
        EveParser.add_return add74 = null;

        EveParser.add_return add81 = null;


        CommonTree char_literal75_tree=null;
        CommonTree string_literal76_tree=null;
        CommonTree char_literal77_tree=null;
        CommonTree string_literal78_tree=null;
        CommonTree string_literal79_tree=null;
        CommonTree char_literal80_tree=null;

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:135:2: ( add ( ( '=' | '/=' | '<' | '<=' | '>=' | '>' ) add )* )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:135:4: add ( ( '=' | '/=' | '<' | '<=' | '>=' | '>' ) add )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_add_in_relation569);
            add74=add();

            state._fsp--;

            adaptor.addChild(root_0, add74.getTree());
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:135:8: ( ( '=' | '/=' | '<' | '<=' | '>=' | '>' ) add )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==22||(LA17_0>=35 && LA17_0<=39)) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:135:9: ( '=' | '/=' | '<' | '<=' | '>=' | '>' ) add
            	    {
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:135:9: ( '=' | '/=' | '<' | '<=' | '>=' | '>' )
            	    int alt16=6;
            	    switch ( input.LA(1) ) {
            	    case 22:
            	        {
            	        alt16=1;
            	        }
            	        break;
            	    case 35:
            	        {
            	        alt16=2;
            	        }
            	        break;
            	    case 36:
            	        {
            	        alt16=3;
            	        }
            	        break;
            	    case 37:
            	        {
            	        alt16=4;
            	        }
            	        break;
            	    case 38:
            	        {
            	        alt16=5;
            	        }
            	        break;
            	    case 39:
            	        {
            	        alt16=6;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 16, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt16) {
            	        case 1 :
            	            // /home/jeff/workspace/eve/src/eve/core/Eve.g:135:10: '='
            	            {
            	            char_literal75=(Token)match(input,22,FOLLOW_22_in_relation573); 
            	            char_literal75_tree = (CommonTree)adaptor.create(char_literal75);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal75_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /home/jeff/workspace/eve/src/eve/core/Eve.g:135:17: '/='
            	            {
            	            string_literal76=(Token)match(input,35,FOLLOW_35_in_relation578); 
            	            string_literal76_tree = (CommonTree)adaptor.create(string_literal76);
            	            root_0 = (CommonTree)adaptor.becomeRoot(string_literal76_tree, root_0);


            	            }
            	            break;
            	        case 3 :
            	            // /home/jeff/workspace/eve/src/eve/core/Eve.g:135:25: '<'
            	            {
            	            char_literal77=(Token)match(input,36,FOLLOW_36_in_relation583); 
            	            char_literal77_tree = (CommonTree)adaptor.create(char_literal77);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal77_tree, root_0);


            	            }
            	            break;
            	        case 4 :
            	            // /home/jeff/workspace/eve/src/eve/core/Eve.g:135:32: '<='
            	            {
            	            string_literal78=(Token)match(input,37,FOLLOW_37_in_relation588); 
            	            string_literal78_tree = (CommonTree)adaptor.create(string_literal78);
            	            root_0 = (CommonTree)adaptor.becomeRoot(string_literal78_tree, root_0);


            	            }
            	            break;
            	        case 5 :
            	            // /home/jeff/workspace/eve/src/eve/core/Eve.g:135:40: '>='
            	            {
            	            string_literal79=(Token)match(input,38,FOLLOW_38_in_relation593); 
            	            string_literal79_tree = (CommonTree)adaptor.create(string_literal79);
            	            root_0 = (CommonTree)adaptor.becomeRoot(string_literal79_tree, root_0);


            	            }
            	            break;
            	        case 6 :
            	            // /home/jeff/workspace/eve/src/eve/core/Eve.g:135:48: '>'
            	            {
            	            char_literal80=(Token)match(input,39,FOLLOW_39_in_relation598); 
            	            char_literal80_tree = (CommonTree)adaptor.create(char_literal80);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal80_tree, root_0);


            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_add_in_relation602);
            	    add81=add();

            	    state._fsp--;

            	    adaptor.addChild(root_0, add81.getTree());

            	    }
            	    break;

            	default :
            	    break loop17;
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
    // /home/jeff/workspace/eve/src/eve/core/Eve.g:138:1: expression : relation ( ( 'and' | 'or' ) relation )* ;
    public final EveParser.expression_return expression() throws RecognitionException {
        EveParser.expression_return retval = new EveParser.expression_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token set83=null;
        EveParser.relation_return relation82 = null;

        EveParser.relation_return relation84 = null;


        CommonTree set83_tree=null;

        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:139:2: ( relation ( ( 'and' | 'or' ) relation )* )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:139:4: relation ( ( 'and' | 'or' ) relation )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_relation_in_expression616);
            relation82=relation();

            state._fsp--;

            adaptor.addChild(root_0, relation82.getTree());
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:139:13: ( ( 'and' | 'or' ) relation )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>=40 && LA18_0<=41)) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:139:14: ( 'and' | 'or' ) relation
            	    {
            	    set83=(Token)input.LT(1);
            	    if ( (input.LA(1)>=40 && input.LA(1)<=41) ) {
            	        input.consume();
            	        adaptor.addChild(root_0, (CommonTree)adaptor.create(set83));
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_relation_in_expression627);
            	    relation84=relation();

            	    state._fsp--;

            	    adaptor.addChild(root_0, relation84.getTree());

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
    // $ANTLR end "expression"

    // Delegated rules


 

    public static final BitSet FOLLOW_statement_in_program84 = new BitSet(new long[]{0x0000000003840202L});
    public static final BitSet FOLLOW_codeStatement_in_statement97 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_protoStatement_in_statement102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_printStatement_in_codeStatement115 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentStatement_in_codeStatement120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_initVariableStatement_in_codeStatement125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_printStatement137 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_printStatement140 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_expression_in_printStatement143 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_printStatement145 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_printStatement148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_assignmentStatement161 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_assignmentStatement163 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_expression_in_assignmentStatement166 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_assignmentStatement168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_initFunction_in_assignmentStatement174 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_IDENT_in_assignmentStatement176 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_assignmentStatement178 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_function_in_assignmentStatement181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_initFunction192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_initVariable_in_initVariableStatement208 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_IDENT_in_initVariableStatement211 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_initVariableStatement213 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_expression_in_initVariableStatement216 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_initVariableStatement218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_initVariable230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_protoStatement246 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_IDENT_in_protoStatement249 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_protoStatement251 = new BitSet(new long[]{0x0000000009840200L});
    public static final BitSet FOLLOW_protoBlock_in_protoStatement254 = new BitSet(new long[]{0x0000000009840200L});
    public static final BitSet FOLLOW_27_in_protoStatement257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_codeStatement_in_protoBlock270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_actualParameters282 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_actualParameters285 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_expression_in_actualParameters287 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_parameters_in_function303 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_functionBody_in_function307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionBodyToken_in_functionBody319 = new BitSet(new long[]{0x0000000009840200L});
    public static final BitSet FOLLOW_codeStatement_in_functionBody322 = new BitSet(new long[]{0x0000000009840200L});
    public static final BitSet FOLLOW_27_in_functionBody325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_functionBodyToken337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parametersStartToken_in_parameters353 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_parameter_in_parameters356 = new BitSet(new long[]{0x0000000010100000L});
    public static final BitSet FOLLOW_28_in_parameters359 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_parameter_in_parameters362 = new BitSet(new long[]{0x0000000010100000L});
    public static final BitSet FOLLOW_20_in_parameters366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_parametersStartToken379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_parameter398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_term412 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_term417 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_expression_in_term420 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_term422 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_term428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_term433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_term438 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_term440 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_actualParameters_in_term442 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_term444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_boolNegation456 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_term_in_boolNegation459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_unary472 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_negation_in_unary477 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_boolNegation_in_unary482 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_negation496 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unary_in_mult511 = new BitSet(new long[]{0x0000000700000002L});
    public static final BitSet FOLLOW_32_in_mult515 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_33_in_mult520 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_34_in_mult525 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_unary_in_mult529 = new BitSet(new long[]{0x0000000700000002L});
    public static final BitSet FOLLOW_mult_in_add543 = new BitSet(new long[]{0x00000000C0000002L});
    public static final BitSet FOLLOW_30_in_add547 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_31_in_add552 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_mult_in_add556 = new BitSet(new long[]{0x00000000C0000002L});
    public static final BitSet FOLLOW_add_in_relation569 = new BitSet(new long[]{0x000000F800400002L});
    public static final BitSet FOLLOW_22_in_relation573 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_35_in_relation578 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_36_in_relation583 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_37_in_relation588 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_38_in_relation593 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_39_in_relation598 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_add_in_relation602 = new BitSet(new long[]{0x000000F800400002L});
    public static final BitSet FOLLOW_relation_in_expression616 = new BitSet(new long[]{0x0000030000000002L});
    public static final BitSet FOLLOW_set_in_expression619 = new BitSet(new long[]{0x00000000E0080E00L});
    public static final BitSet FOLLOW_relation_in_expression627 = new BitSet(new long[]{0x0000030000000002L});

}