// Generated from CPreprocessor.g4 by ANTLR 4.0

	package fr.univ_nantes.alma.archtool.parsing;
	
	import java.util.HashSet;
	import java.util.Set;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CPreprocessorLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__1=1, T__0=2, HeaderName=3, BlockComment=4, LineComment=5, CCode=6, 
		Whitespace=7, IgnoredPreprocessingDirective=8, Newline=9;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'include'", "'#'", "HeaderName", "BlockComment", "LineComment", "CCode", 
		"Whitespace", "IgnoredPreprocessingDirective", "Newline"
	};
	public static final String[] ruleNames = {
		"T__1", "T__0", "HeaderName", "HCharSequence", "HChar", "QCharSequence", 
		"QChar", "BlockComment", "LineComment", "CCode", "Whitespace", "IgnoredPreprocessingDirective", 
		"IgnoredPreprocessingKeyword", "Newline"
	};


	public CPreprocessorLexer(CharStream input) {
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
		case 7: BlockComment_action((RuleContext)_localctx, actionIndex); break;

		case 8: LineComment_action((RuleContext)_localctx, actionIndex); break;

		case 9: CCode_action((RuleContext)_localctx, actionIndex); break;

		case 10: Whitespace_action((RuleContext)_localctx, actionIndex); break;

		case 11: IgnoredPreprocessingDirective_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void BlockComment_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}
	private void LineComment_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1: skip();  break;
		}
	}
	private void IgnoredPreprocessingDirective_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 4: skip();  break;
		}
	}
	private void Whitespace_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 3: skip();  break;
		}
	}
	private void CCode_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\13\u00b1\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b"+
		"\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5"+
		"\4\62\n\4\3\5\6\5\65\n\5\r\5\16\5\66\3\6\3\6\3\7\6\7<\n\7\r\7\16\7=\3"+
		"\b\3\b\3\t\3\t\3\t\3\t\7\tF\n\t\f\t\16\tI\13\t\3\t\3\t\3\t\3\t\3\t\3\n"+
		"\3\n\3\n\3\n\7\nT\n\n\f\n\16\nW\13\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\6"+
		"\f`\n\f\r\f\16\fa\3\f\3\f\3\r\3\r\3\r\7\ri\n\r\f\r\16\rl\13\r\5\rn\n\r"+
		"\3\r\5\rq\n\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\5\16\u00a9\n\16\3\17\3\17\5\17\u00ad\n\17\3\17\5\17\u00b0\n\17\3G\20"+
		"\3\3\1\5\4\1\7\5\1\t\2\1\13\2\1\r\2\1\17\2\1\21\6\2\23\7\3\25\b\4\27\t"+
		"\5\31\n\6\33\2\1\35\13\1\3\2\7\5\f\f\17\17@@\5\f\f\17\17$$\4\f\f\17\17"+
		"\4\13\13\"\"\4\f\f\17\17\u00c0\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\21"+
		"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\35\3\2"+
		"\2\2\3\37\3\2\2\2\5\'\3\2\2\2\7\61\3\2\2\2\t\64\3\2\2\2\138\3\2\2\2\r"+
		";\3\2\2\2\17?\3\2\2\2\21A\3\2\2\2\23O\3\2\2\2\25Z\3\2\2\2\27_\3\2\2\2"+
		"\31e\3\2\2\2\33\u00a8\3\2\2\2\35\u00af\3\2\2\2\37 \7k\2\2 !\7p\2\2!\""+
		"\7e\2\2\"#\7n\2\2#$\7w\2\2$%\7f\2\2%&\7g\2\2&\4\3\2\2\2\'(\7%\2\2(\6\3"+
		"\2\2\2)*\7>\2\2*+\5\t\5\2+,\7@\2\2,\62\3\2\2\2-.\7$\2\2./\5\r\7\2/\60"+
		"\7$\2\2\60\62\3\2\2\2\61)\3\2\2\2\61-\3\2\2\2\62\b\3\2\2\2\63\65\5\13"+
		"\6\2\64\63\3\2\2\2\65\66\3\2\2\2\66\64\3\2\2\2\66\67\3\2\2\2\67\n\3\2"+
		"\2\289\n\2\2\29\f\3\2\2\2:<\5\17\b\2;:\3\2\2\2<=\3\2\2\2=;\3\2\2\2=>\3"+
		"\2\2\2>\16\3\2\2\2?@\n\3\2\2@\20\3\2\2\2AB\7\61\2\2BC\7,\2\2CG\3\2\2\2"+
		"DF\13\2\2\2ED\3\2\2\2FI\3\2\2\2GH\3\2\2\2GE\3\2\2\2HJ\3\2\2\2IG\3\2\2"+
		"\2JK\7,\2\2KL\7\61\2\2LM\3\2\2\2MN\b\t\2\2N\22\3\2\2\2OP\7\61\2\2PQ\7"+
		"\61\2\2QU\3\2\2\2RT\n\4\2\2SR\3\2\2\2TW\3\2\2\2US\3\2\2\2UV\3\2\2\2VX"+
		"\3\2\2\2WU\3\2\2\2XY\b\n\3\2Y\24\3\2\2\2Z[\13\2\2\2[\\\3\2\2\2\\]\b\13"+
		"\4\2]\26\3\2\2\2^`\t\5\2\2_^\3\2\2\2`a\3\2\2\2a_\3\2\2\2ab\3\2\2\2bc\3"+
		"\2\2\2cd\b\f\5\2d\30\3\2\2\2em\7%\2\2fj\5\33\16\2gi\n\6\2\2hg\3\2\2\2"+
		"il\3\2\2\2jh\3\2\2\2jk\3\2\2\2kn\3\2\2\2lj\3\2\2\2mf\3\2\2\2mn\3\2\2\2"+
		"np\3\2\2\2oq\5\35\17\2po\3\2\2\2pq\3\2\2\2qr\3\2\2\2rs\b\r\6\2s\32\3\2"+
		"\2\2tu\7k\2\2u\u00a9\7h\2\2vw\7k\2\2wx\7h\2\2xy\7f\2\2yz\7g\2\2z\u00a9"+
		"\7h\2\2{|\7k\2\2|}\7h\2\2}~\7p\2\2~\177\7f\2\2\177\u0080\7g\2\2\u0080"+
		"\u00a9\7h\2\2\u0081\u0082\7g\2\2\u0082\u0083\7n\2\2\u0083\u0084\7k\2\2"+
		"\u0084\u00a9\7h\2\2\u0085\u0086\7g\2\2\u0086\u0087\7n\2\2\u0087\u0088"+
		"\7u\2\2\u0088\u00a9\7g\2\2\u0089\u008a\7g\2\2\u008a\u008b\7p\2\2\u008b"+
		"\u008c\7f\2\2\u008c\u008d\7k\2\2\u008d\u00a9\7h\2\2\u008e\u008f\7f\2\2"+
		"\u008f\u0090\7g\2\2\u0090\u0091\7h\2\2\u0091\u0092\7k\2\2\u0092\u0093"+
		"\7p\2\2\u0093\u00a9\7g\2\2\u0094\u0095\7w\2\2\u0095\u0096\7p\2\2\u0096"+
		"\u0097\7f\2\2\u0097\u0098\7g\2\2\u0098\u00a9\7h\2\2\u0099\u009a\7n\2\2"+
		"\u009a\u009b\7k\2\2\u009b\u009c\7p\2\2\u009c\u00a9\7g\2\2\u009d\u009e"+
		"\7g\2\2\u009e\u009f\7t\2\2\u009f\u00a0\7t\2\2\u00a0\u00a1\7q\2\2\u00a1"+
		"\u00a9\7t\2\2\u00a2\u00a3\7r\2\2\u00a3\u00a4\7t\2\2\u00a4\u00a5\7c\2\2"+
		"\u00a5\u00a6\7i\2\2\u00a6\u00a7\7o\2\2\u00a7\u00a9\7c\2\2\u00a8t\3\2\2"+
		"\2\u00a8v\3\2\2\2\u00a8{\3\2\2\2\u00a8\u0081\3\2\2\2\u00a8\u0085\3\2\2"+
		"\2\u00a8\u0089\3\2\2\2\u00a8\u008e\3\2\2\2\u00a8\u0094\3\2\2\2\u00a8\u0099"+
		"\3\2\2\2\u00a8\u009d\3\2\2\2\u00a8\u00a2\3\2\2\2\u00a9\34\3\2\2\2\u00aa"+
		"\u00ac\7\17\2\2\u00ab\u00ad\7\f\2\2\u00ac\u00ab\3\2\2\2\u00ac\u00ad\3"+
		"\2\2\2\u00ad\u00b0\3\2\2\2\u00ae\u00b0\7\f\2\2\u00af\u00aa\3\2\2\2\u00af"+
		"\u00ae\3\2\2\2\u00b0\36\3\2\2\2\17\2\61\66=GUajmp\u00a8\u00ac\u00af";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}