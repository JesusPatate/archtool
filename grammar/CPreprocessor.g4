grammar CPreprocessor;


@header
{
	package fr.univ_nantes.alma.archtool.parsing;
	
	import java.util.HashSet;
	import java.util.Set;
}

@parser::members
{
	private Set<String> nonStandardIncludes = new HashSet<String>();
	
	public Set<String> getNonStandardIncludes()
	{
		return new HashSet<String>(this.nonStandardIncludes);
	}
	
	public void cleanUp()
	{
		this.nonStandardIncludes.clear();
	}
}


preprocessingFile
	: group? EOF
	;

group
	: groupPart+
	;

groupPart
	: '#' 'include' h=HeaderName Newline? 
{
    if($h.text.charAt(0) != '<')
    {
        this.nonStandardIncludes.add($h.text.substring(1, $h.text.length() - 1));
    }
}
	| Newline
	| HeaderName
	;

HeaderName
	: '<' HCharSequence '>'
	| '"' QCharSequence '"'
	;
	
fragment
HCharSequence
	: HChar+
	;

fragment
HChar
	: ~[>\r\n]
	;

fragment
QCharSequence
	: QChar+
	;

fragment
QChar
	: ~["\r\n]
	;

BlockComment
    : '/*' .*? '*/'
		-> skip
    ;

LineComment
    : '//' ~[\r\n]*
        -> skip
    ;

CCode
	: .
		-> skip
	;

Whitespace
    : [ \t]+
	    -> skip
	;

IgnoredPreprocessingDirective
	: '#' (IgnoredPreprocessingKeyword ~[\r\n]*)? Newline?
		-> skip
	;

fragment
IgnoredPreprocessingKeyword
    : 'if' | 'ifdef' | 'ifndef' | 'elif' | 'else' | 'endif' 
    | 'define' | 'undef' | 'line' | 'error' | 'pragma'
    ;

Newline
    : ( '\r' '\n'?
        | '\n'
        )
    ;