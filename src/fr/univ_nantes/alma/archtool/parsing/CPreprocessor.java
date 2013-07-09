// Generated from CPreprocessor.g4 by ANTLR 4.0

	package fr.univ_nantes.alma.archtool.parsing;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CPreprocessor extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Whitespace=1, NewlinePreprocessor=2, Newline=3, Comment=4, Other=5;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"Whitespace", "NewlinePreprocessor", "Newline", "Comment", "Other"
	};
	public static final String[] ruleNames = {
		"Whitespace", "NewlinePreprocessor", "Newline", "Comment", "BlockComment", 
		"LineComment", "Other"
	};


	public CPreprocessor(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CPreprocessor.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 0: Whitespace_action((RuleContext)_localctx, actionIndex); break;

		case 1: NewlinePreprocessor_action((RuleContext)_localctx, actionIndex); break;

		case 2: Newline_action((RuleContext)_localctx, actionIndex); break;

		case 3: Comment_action((RuleContext)_localctx, actionIndex); break;

		case 6: Other_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void Newline_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 4: skip();  break;
		}
	}
	private void Other_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1: System.out.println(getText()); break;
		}
	}
	private void Comment_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: System.out.println(getText()); break;
		}
	}
	private void NewlinePreprocessor_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 3: skip();  break;
		}
	}
	private void Whitespace_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\7H\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3"+
		"\2\6\2\23\n\2\r\2\16\2\24\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\5\4 \n\4"+
		"\3\4\5\4#\n\4\3\4\3\4\3\5\3\5\5\5)\n\5\3\5\3\5\3\6\3\6\3\6\3\6\7\6\61"+
		"\n\6\f\6\16\6\64\13\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\7\7=\n\7\f\7\16\7@\13"+
		"\7\3\b\6\bC\n\b\r\b\16\bD\3\b\3\b\3\62\t\3\3\4\5\4\5\7\5\6\t\6\2\13\2"+
		"\1\r\2\1\17\7\3\3\2\4\4\13\13\"\"\4\f\f\17\17L\2\3\3\2\2\2\2\5\3\2\2\2"+
		"\2\7\3\2\2\2\2\t\3\2\2\2\2\17\3\2\2\2\3\22\3\2\2\2\5\30\3\2\2\2\7\"\3"+
		"\2\2\2\t(\3\2\2\2\13,\3\2\2\2\r8\3\2\2\2\17B\3\2\2\2\21\23\t\2\2\2\22"+
		"\21\3\2\2\2\23\24\3\2\2\2\24\22\3\2\2\2\24\25\3\2\2\2\25\26\3\2\2\2\26"+
		"\27\b\2\4\2\27\4\3\2\2\2\30\31\7^\2\2\31\32\5\7\4\2\32\33\3\2\2\2\33\34"+
		"\b\3\5\2\34\6\3\2\2\2\35\37\7\17\2\2\36 \7\f\2\2\37\36\3\2\2\2\37 \3\2"+
		"\2\2 #\3\2\2\2!#\7\f\2\2\"\35\3\2\2\2\"!\3\2\2\2#$\3\2\2\2$%\b\4\6\2%"+
		"\b\3\2\2\2&)\5\13\6\2\')\5\r\7\2(&\3\2\2\2(\'\3\2\2\2)*\3\2\2\2*+\b\5"+
		"\2\2+\n\3\2\2\2,-\7\61\2\2-.\7,\2\2.\62\3\2\2\2/\61\13\2\2\2\60/\3\2\2"+
		"\2\61\64\3\2\2\2\62\63\3\2\2\2\62\60\3\2\2\2\63\65\3\2\2\2\64\62\3\2\2"+
		"\2\65\66\7,\2\2\66\67\7\61\2\2\67\f\3\2\2\289\7\61\2\29:\7\61\2\2:>\3"+
		"\2\2\2;=\n\3\2\2<;\3\2\2\2=@\3\2\2\2><\3\2\2\2>?\3\2\2\2?\16\3\2\2\2@"+
		">\3\2\2\2AC\13\2\2\2BA\3\2\2\2CD\3\2\2\2DB\3\2\2\2DE\3\2\2\2EF\3\2\2\2"+
		"FG\b\b\3\2G\20\3\2\2\2\n\2\24\37\"(\62>D";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}