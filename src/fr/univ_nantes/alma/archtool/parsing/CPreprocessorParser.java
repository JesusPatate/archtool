// Generated from CPreprocessor.g4 by ANTLR 4.0

	package fr.univ_nantes.alma.archtool.parsing;
	
	import java.util.HashSet;
	import java.util.Set;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CPreprocessorParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__1=1, T__0=2, HeaderName=3, BlockComment=4, LineComment=5, CCode=6, 
		IgnoredPreprocessingDirective=7, Whitespace=8, Newline=9;
	public static final String[] tokenNames = {
		"<INVALID>", "'include'", "'#'", "HeaderName", "BlockComment", "LineComment", 
		"CCode", "IgnoredPreprocessingDirective", "Whitespace", "Newline"
	};
	public static final int
		RULE_preprocessingFile = 0, RULE_group = 1, RULE_groupPart = 2;
	public static final String[] ruleNames = {
		"preprocessingFile", "group", "groupPart"
	};

	@Override
	public String getGrammarFileName() { return "CPreprocessor.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }


		private Set<String> nonStandardIncludes = new HashSet<String>();
		
		public Set<String> getNonStandardIncludes()
		{
			return new HashSet<String>(this.nonStandardIncludes);
		}
		
		public void cleanUp()
		{
			this.nonStandardIncludes.clear();
		}

	public CPreprocessorParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class PreprocessingFileContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(CPreprocessorParser.EOF, 0); }
		public GroupContext group() {
			return getRuleContext(GroupContext.class,0);
		}
		public PreprocessingFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_preprocessingFile; }
	}

	public final PreprocessingFileContext preprocessingFile() throws RecognitionException {
		PreprocessingFileContext _localctx = new PreprocessingFileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_preprocessingFile);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(7);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 2) | (1L << HeaderName) | (1L << Newline))) != 0)) {
				{
				setState(6); group();
				}
			}

			setState(9); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroupContext extends ParserRuleContext {
		public List<GroupPartContext> groupPart() {
			return getRuleContexts(GroupPartContext.class);
		}
		public GroupPartContext groupPart(int i) {
			return getRuleContext(GroupPartContext.class,i);
		}
		public GroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_group; }
	}

	public final GroupContext group() throws RecognitionException {
		GroupContext _localctx = new GroupContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_group);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(12); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(11); groupPart();
				}
				}
				setState(14); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 2) | (1L << HeaderName) | (1L << Newline))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroupPartContext extends ParserRuleContext {
		public Token h;
		public TerminalNode HeaderName() { return getToken(CPreprocessorParser.HeaderName, 0); }
		public TerminalNode Newline() { return getToken(CPreprocessorParser.Newline, 0); }
		public GroupPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupPart; }
	}

	public final GroupPartContext groupPart() throws RecognitionException {
		GroupPartContext _localctx = new GroupPartContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_groupPart);
		try {
			setState(25);
			switch (_input.LA(1)) {
			case 2:
				enterOuterAlt(_localctx, 1);
				{
				setState(16); match(2);
				setState(17); match(1);
				setState(18); ((GroupPartContext)_localctx).h = match(HeaderName);
				setState(20);
				switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
				case 1:
					{
					setState(19); match(Newline);
					}
					break;
				}

				    if((((GroupPartContext)_localctx).h!=null?((GroupPartContext)_localctx).h.getText():null).charAt(0) != '<')
				    {
				        this.nonStandardIncludes.add((((GroupPartContext)_localctx).h!=null?((GroupPartContext)_localctx).h.getText():null).substring(1, (((GroupPartContext)_localctx).h!=null?((GroupPartContext)_localctx).h.getText():null).length() - 1));
				    }

				}
				break;
			case Newline:
				enterOuterAlt(_localctx, 2);
				{
				setState(23); match(Newline);
				}
				break;
			case HeaderName:
				enterOuterAlt(_localctx, 3);
				{
				setState(24); match(HeaderName);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\2\3\13\36\4\2\t\2\4\3\t\3\4\4\t\4\3\2\5\2\n\n\2\3\2\3\2\3\3\6\3\17\n"+
		"\3\r\3\16\3\20\3\4\3\4\3\4\3\4\5\4\27\n\4\3\4\3\4\3\4\5\4\34\n\4\3\4\2"+
		"\5\2\4\6\2\2\37\2\t\3\2\2\2\4\16\3\2\2\2\6\33\3\2\2\2\b\n\5\4\3\2\t\b"+
		"\3\2\2\2\t\n\3\2\2\2\n\13\3\2\2\2\13\f\7\1\2\2\f\3\3\2\2\2\r\17\5\6\4"+
		"\2\16\r\3\2\2\2\17\20\3\2\2\2\20\16\3\2\2\2\20\21\3\2\2\2\21\5\3\2\2\2"+
		"\22\23\7\4\2\2\23\24\7\3\2\2\24\26\7\5\2\2\25\27\7\13\2\2\26\25\3\2\2"+
		"\2\26\27\3\2\2\2\27\30\3\2\2\2\30\34\b\4\1\2\31\34\7\13\2\2\32\34\7\5"+
		"\2\2\33\22\3\2\2\2\33\31\3\2\2\2\33\32\3\2\2\2\34\7\3\2\2\2\6\t\20\26"+
		"\33";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}