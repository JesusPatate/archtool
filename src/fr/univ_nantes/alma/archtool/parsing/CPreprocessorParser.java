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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(6); group();
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
			setState(9); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(8); groupPart();
				}
				}
				setState(11); 
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
			setState(22);
			switch (_input.LA(1)) {
			case 2:
				enterOuterAlt(_localctx, 1);
				{
				setState(13); match(2);
				setState(14); match(1);
				setState(15); ((GroupPartContext)_localctx).h = match(HeaderName);
				setState(17);
				switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
				case 1:
					{
					setState(16); match(Newline);
					}
					break;
				}
				if((((GroupPartContext)_localctx).h!=null?((GroupPartContext)_localctx).h.getText():null).charAt(0) != '<') this.nonStandardIncludes.add((((GroupPartContext)_localctx).h!=null?((GroupPartContext)_localctx).h.getText():null).substring(1, (((GroupPartContext)_localctx).h!=null?((GroupPartContext)_localctx).h.getText():null).length() - 1));
				}
				break;
			case Newline:
				enterOuterAlt(_localctx, 2);
				{
				setState(20); match(Newline);
				}
				break;
			case HeaderName:
				enterOuterAlt(_localctx, 3);
				{
				setState(21); match(HeaderName);
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
		"\2\3\13\33\4\2\t\2\4\3\t\3\4\4\t\4\3\2\3\2\3\3\6\3\f\n\3\r\3\16\3\r\3"+
		"\4\3\4\3\4\3\4\5\4\24\n\4\3\4\3\4\3\4\5\4\31\n\4\3\4\2\5\2\4\6\2\2\33"+
		"\2\b\3\2\2\2\4\13\3\2\2\2\6\30\3\2\2\2\b\t\5\4\3\2\t\3\3\2\2\2\n\f\5\6"+
		"\4\2\13\n\3\2\2\2\f\r\3\2\2\2\r\13\3\2\2\2\r\16\3\2\2\2\16\5\3\2\2\2\17"+
		"\20\7\4\2\2\20\21\7\3\2\2\21\23\7\5\2\2\22\24\7\13\2\2\23\22\3\2\2\2\23"+
		"\24\3\2\2\2\24\25\3\2\2\2\25\31\b\4\1\2\26\31\7\13\2\2\27\31\7\5\2\2\30"+
		"\17\3\2\2\2\30\26\3\2\2\2\30\27\3\2\2\2\31\7\3\2\2\2\5\r\23\30";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}