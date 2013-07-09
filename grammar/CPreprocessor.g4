lexer grammar CPreprocessor;


@header
{
	package fr.univ_nantes.alma.archtool.parsing;
}

Whitespace
    : [ \t]+
        -> skip
    ;

NewlinePreprocessor
    : '\\' Newline
        -> skip
    ;

Newline
    : ( '\r' '\n'?
        | '\n'
        )
        -> skip
    ;

Comment
    : (BlockComment
            | LineComment
      )
          {System.out.println(getText());}
    ;             
        
fragment
BlockComment
    : '/*' .*? '*/'
    ;
fragment
LineComment
    : '//' ~[\r\n]*
    ;
    
Other 
	: .+ {System.out.println(getText());}
	;
