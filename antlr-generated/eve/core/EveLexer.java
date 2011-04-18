// $ANTLR 3.3 Nov 30, 2010 12:50:56 /home/jeff/workspace/eve/src/eve/core/Eve.g 2011-04-18 08:01:49

	package eve.core;
	import java.util.List;
	import java.util.ArrayList;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class EveLexer extends Lexer {
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

    public EveLexer() {;} 
    public EveLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public EveLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/home/jeff/workspace/eve/src/eve/core/Eve.g"; }

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:13:7: ( 'print' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:13:9: 'print'
            {
            match("print"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:14:7: ( '(' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:14:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:15:7: ( ')' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:15:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:16:7: ( ';' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:16:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:17:7: ( '=' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:17:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:18:7: ( 'def' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:18:9: 'def'
            {
            match("def"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:19:7: ( 'var' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:19:9: 'var'
            {
            match("var"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:20:7: ( 'proto' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:20:9: 'proto'
            {
            match("proto"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:21:7: ( '{' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:21:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:22:7: ( '}' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:22:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:23:7: ( ',' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:23:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:24:7: ( 'not' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:24:9: 'not'
            {
            match("not"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:25:7: ( '+' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:25:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:26:7: ( '-' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:26:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:27:7: ( '*' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:27:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:28:7: ( '/' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:28:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:29:7: ( '%' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:29:9: '%'
            {
            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:30:7: ( '/=' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:30:9: '/='
            {
            match("/="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:31:7: ( '<' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:31:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:32:7: ( '<=' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:32:9: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:33:7: ( '>=' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:33:9: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:34:7: ( '>' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:34:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:35:7: ( 'and' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:35:9: 'and'
            {
            match("and"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__40"

    // $ANTLR start "T__41"
    public final void mT__41() throws RecognitionException {
        try {
            int _type = T__41;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:36:7: ( 'or' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:36:9: 'or'
            {
            match("or"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__41"

    // $ANTLR start "MULTILINE_COMMENT"
    public final void mMULTILINE_COMMENT() throws RecognitionException {
        try {
            int _type = MULTILINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:143:19: ( '/*' ( . )* '*/' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:143:21: '/*' ( . )* '*/'
            {
            match("/*"); 

            // /home/jeff/workspace/eve/src/eve/core/Eve.g:143:26: ( . )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='*') ) {
                    int LA1_1 = input.LA(2);

                    if ( (LA1_1=='/') ) {
                        alt1=2;
                    }
                    else if ( ((LA1_1>='\u0000' && LA1_1<='.')||(LA1_1>='0' && LA1_1<='\uFFFF')) ) {
                        alt1=1;
                    }


                }
                else if ( ((LA1_0>='\u0000' && LA1_0<=')')||(LA1_0>='+' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:143:26: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match("*/"); 

            _channel = HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MULTILINE_COMMENT"

    // $ANTLR start "STRING_LITERAL"
    public final void mSTRING_LITERAL() throws RecognitionException {
        try {
            int _type = STRING_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            int c;

            // /home/jeff/workspace/eve/src/eve/core/Eve.g:146:2: ( '\"' ( '\"' '\"' | c=~ ( '\"' | '\\r' | '\\n' ) )* '\"' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:146:4: '\"' ( '\"' '\"' | c=~ ( '\"' | '\\r' | '\\n' ) )* '\"'
            {
            match('\"'); 
             StringBuilder b = new StringBuilder(); 
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:148:3: ( '\"' '\"' | c=~ ( '\"' | '\\r' | '\\n' ) )*
            loop2:
            do {
                int alt2=3;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='\"') ) {
                    int LA2_1 = input.LA(2);

                    if ( (LA2_1=='\"') ) {
                        alt2=1;
                    }


                }
                else if ( ((LA2_0>='\u0000' && LA2_0<='\t')||(LA2_0>='\u000B' && LA2_0<='\f')||(LA2_0>='\u000E' && LA2_0<='!')||(LA2_0>='#' && LA2_0<='\uFFFF')) ) {
                    alt2=2;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:148:5: '\"' '\"'
            	    {
            	    match('\"'); 
            	    match('\"'); 
            	     b.appendCodePoint('"');

            	    }
            	    break;
            	case 2 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:149:5: c=~ ( '\"' | '\\r' | '\\n' )
            	    {
            	    c= input.LA(1);
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}

            	     b.appendCodePoint(c);

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            match('\"'); 
             setText(b.toString()); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING_LITERAL"

    // $ANTLR start "CHAR_LITERAL"
    public final void mCHAR_LITERAL() throws RecognitionException {
        try {
            int _type = CHAR_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:156:2: ( '\\'' . '\\'' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:156:4: '\\'' . '\\''
            {
            match('\''); 
            matchAny(); 
            match('\''); 
            setText(getText().substring(1,2));

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CHAR_LITERAL"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:159:17: ( ( 'a' .. 'z' | 'A' .. 'Z' ) )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:159:19: ( 'a' .. 'z' | 'A' .. 'Z' )
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "LETTER"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:160:16: ( '0' .. '9' )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:160:18: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "INTEGER"
    public final void mINTEGER() throws RecognitionException {
        try {
            int _type = INTEGER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:161:9: ( ( DIGIT )+ )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:161:11: ( DIGIT )+
            {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:161:11: ( DIGIT )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:161:11: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INTEGER"

    // $ANTLR start "IDENT"
    public final void mIDENT() throws RecognitionException {
        try {
            int _type = IDENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:162:7: ( LETTER ( LETTER | DIGIT )* )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:162:9: LETTER ( LETTER | DIGIT )*
            {
            mLETTER(); 
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:162:16: ( LETTER | DIGIT )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')||(LA4_0>='A' && LA4_0<='Z')||(LA4_0>='a' && LA4_0<='z')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IDENT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:163:4: ( ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+ )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:163:6: ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+
            {
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:163:6: ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='\t' && LA5_0<='\n')||(LA5_0>='\f' && LA5_0<='\r')||LA5_0==' ') ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);

            _channel = HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:164:9: ( '//' ( . )* ( '\\n' | '\\r' ) )
            // /home/jeff/workspace/eve/src/eve/core/Eve.g:164:11: '//' ( . )* ( '\\n' | '\\r' )
            {
            match("//"); 

            // /home/jeff/workspace/eve/src/eve/core/Eve.g:164:16: ( . )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='\n'||LA6_0=='\r') ) {
                    alt6=2;
                }
                else if ( ((LA6_0>='\u0000' && LA6_0<='\t')||(LA6_0>='\u000B' && LA6_0<='\f')||(LA6_0>='\u000E' && LA6_0<='\uFFFF')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/jeff/workspace/eve/src/eve/core/Eve.g:164:16: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            if ( input.LA(1)=='\n'||input.LA(1)=='\r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            _channel = HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMENT"

    public void mTokens() throws RecognitionException {
        // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:8: ( T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | MULTILINE_COMMENT | STRING_LITERAL | CHAR_LITERAL | INTEGER | IDENT | WS | COMMENT )
        int alt7=31;
        alt7 = dfa7.predict(input);
        switch (alt7) {
            case 1 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:10: T__18
                {
                mT__18(); 

                }
                break;
            case 2 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:16: T__19
                {
                mT__19(); 

                }
                break;
            case 3 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:22: T__20
                {
                mT__20(); 

                }
                break;
            case 4 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:28: T__21
                {
                mT__21(); 

                }
                break;
            case 5 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:34: T__22
                {
                mT__22(); 

                }
                break;
            case 6 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:40: T__23
                {
                mT__23(); 

                }
                break;
            case 7 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:46: T__24
                {
                mT__24(); 

                }
                break;
            case 8 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:52: T__25
                {
                mT__25(); 

                }
                break;
            case 9 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:58: T__26
                {
                mT__26(); 

                }
                break;
            case 10 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:64: T__27
                {
                mT__27(); 

                }
                break;
            case 11 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:70: T__28
                {
                mT__28(); 

                }
                break;
            case 12 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:76: T__29
                {
                mT__29(); 

                }
                break;
            case 13 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:82: T__30
                {
                mT__30(); 

                }
                break;
            case 14 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:88: T__31
                {
                mT__31(); 

                }
                break;
            case 15 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:94: T__32
                {
                mT__32(); 

                }
                break;
            case 16 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:100: T__33
                {
                mT__33(); 

                }
                break;
            case 17 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:106: T__34
                {
                mT__34(); 

                }
                break;
            case 18 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:112: T__35
                {
                mT__35(); 

                }
                break;
            case 19 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:118: T__36
                {
                mT__36(); 

                }
                break;
            case 20 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:124: T__37
                {
                mT__37(); 

                }
                break;
            case 21 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:130: T__38
                {
                mT__38(); 

                }
                break;
            case 22 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:136: T__39
                {
                mT__39(); 

                }
                break;
            case 23 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:142: T__40
                {
                mT__40(); 

                }
                break;
            case 24 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:148: T__41
                {
                mT__41(); 

                }
                break;
            case 25 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:154: MULTILINE_COMMENT
                {
                mMULTILINE_COMMENT(); 

                }
                break;
            case 26 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:172: STRING_LITERAL
                {
                mSTRING_LITERAL(); 

                }
                break;
            case 27 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:187: CHAR_LITERAL
                {
                mCHAR_LITERAL(); 

                }
                break;
            case 28 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:200: INTEGER
                {
                mINTEGER(); 

                }
                break;
            case 29 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:208: IDENT
                {
                mIDENT(); 

                }
                break;
            case 30 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:214: WS
                {
                mWS(); 

                }
                break;
            case 31 :
                // /home/jeff/workspace/eve/src/eve/core/Eve.g:1:217: COMMENT
                {
                mCOMMENT(); 

                }
                break;

        }

    }


    protected DFA7 dfa7 = new DFA7(this);
    static final String DFA7_eotS =
        "\1\uffff\1\30\4\uffff\2\30\3\uffff\1\30\3\uffff\1\41\1\uffff\1\43"+
        "\1\45\2\30\5\uffff\4\30\10\uffff\1\30\1\56\2\30\1\61\1\62\1\63\1"+
        "\64\1\uffff\2\30\4\uffff\1\67\1\70\2\uffff";
    static final String DFA7_eofS =
        "\71\uffff";
    static final String DFA7_minS =
        "\1\11\1\162\4\uffff\1\145\1\141\3\uffff\1\157\3\uffff\1\52\1\uffff"+
        "\2\75\1\156\1\162\5\uffff\1\151\1\146\1\162\1\164\10\uffff\1\144"+
        "\1\60\1\156\1\164\4\60\1\uffff\1\164\1\157\4\uffff\2\60\2\uffff";
    static final String DFA7_maxS =
        "\1\175\1\162\4\uffff\1\145\1\141\3\uffff\1\157\3\uffff\1\75\1\uffff"+
        "\2\75\1\156\1\162\5\uffff\1\157\1\146\1\162\1\164\10\uffff\1\144"+
        "\1\172\1\156\1\164\4\172\1\uffff\1\164\1\157\4\uffff\2\172\2\uffff";
    static final String DFA7_acceptS =
        "\2\uffff\1\2\1\3\1\4\1\5\2\uffff\1\11\1\12\1\13\1\uffff\1\15\1\16"+
        "\1\17\1\uffff\1\21\4\uffff\1\32\1\33\1\34\1\35\1\36\4\uffff\1\22"+
        "\1\31\1\37\1\20\1\24\1\23\1\25\1\26\10\uffff\1\30\2\uffff\1\6\1"+
        "\7\1\14\1\27\2\uffff\1\1\1\10";
    static final String DFA7_specialS =
        "\71\uffff}>";
    static final String[] DFA7_transitionS = {
            "\2\31\1\uffff\2\31\22\uffff\1\31\1\uffff\1\25\2\uffff\1\20\1"+
            "\uffff\1\26\1\2\1\3\1\16\1\14\1\12\1\15\1\uffff\1\17\12\27\1"+
            "\uffff\1\4\1\21\1\5\1\22\2\uffff\32\30\6\uffff\1\23\2\30\1\6"+
            "\11\30\1\13\1\24\1\1\5\30\1\7\4\30\1\10\1\uffff\1\11",
            "\1\32",
            "",
            "",
            "",
            "",
            "\1\33",
            "\1\34",
            "",
            "",
            "",
            "\1\35",
            "",
            "",
            "",
            "\1\37\4\uffff\1\40\15\uffff\1\36",
            "",
            "\1\42",
            "\1\44",
            "\1\46",
            "\1\47",
            "",
            "",
            "",
            "",
            "",
            "\1\50\5\uffff\1\51",
            "\1\52",
            "\1\53",
            "\1\54",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\55",
            "\12\30\7\uffff\32\30\6\uffff\32\30",
            "\1\57",
            "\1\60",
            "\12\30\7\uffff\32\30\6\uffff\32\30",
            "\12\30\7\uffff\32\30\6\uffff\32\30",
            "\12\30\7\uffff\32\30\6\uffff\32\30",
            "\12\30\7\uffff\32\30\6\uffff\32\30",
            "",
            "\1\65",
            "\1\66",
            "",
            "",
            "",
            "",
            "\12\30\7\uffff\32\30\6\uffff\32\30",
            "\12\30\7\uffff\32\30\6\uffff\32\30",
            "",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | MULTILINE_COMMENT | STRING_LITERAL | CHAR_LITERAL | INTEGER | IDENT | WS | COMMENT );";
        }
    }
 

}