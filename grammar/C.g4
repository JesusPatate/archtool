/*
 [The "BSD licence"]
 Copyright (c) 2013 Sam Harwell
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

/** C 2011 grammar built from the C11 Spec */
grammar C;


@header
{
	package fr.univ_nantes.alma.archtool.parsing;
	
	import java.util.Map;
	import java.util.HashMap;
	import java.util.HashSet;
	import java.util.Set;
	import fr.univ_nantes.alma.archtool.sourceModel.*;
	import fr.univ_nantes.alma.archtool.parsing.specifier.*;
}

@parser::members
{
	private String currentPath;
	private Map<String, Function> functions = new HashMap<String, Function>();
	private Map<String, ComplexType> complexTypes = new HashMap<String, ComplexType>();
	private Map<String, Variable> globalVariables = new HashMap<String, Variable>();
	
	private void addComplexType(String name)
	{
		if(name != null)
		{
			this.complexTypes.put(name, new ComplexType(name, null));
		}
	}
	
	private void declareTypedef(boolean isTypedef, String name, List<String> names)
	{
		if(isTypedef)
		{
			if(names.isEmpty())
			{
				addComplexType(name);
			}
			else
			{
				addComplexType(names.get(0));
			}
		}
	}
}


/************************************ Expressions ************************************/

primaryExpression
    : Identifier
    | Constant
    | StringLiteral+
    | '(' expression ')'
    | genericSelection
    | '__extension__'? '(' compoundStatement ')' // Blocks (GCC extension)
    | '__builtin_va_arg' '(' unaryExpression ',' typeName ')'
    | '__builtin_offsetof' '(' typeName ',' unaryExpression ')'
    ;

genericSelection
    : '_Generic' '(' assignmentExpression ',' genericAssocList ')'
    ;

genericAssocList
    : genericAssociation
    | genericAssocList ',' genericAssociation
    ;

genericAssociation
    : typeName ':' assignmentExpression
    | 'default' ':' assignmentExpression
    ;

postfixExpression
    : primaryExpression
    | postfixExpression '[' expression ']'
    | postfixExpression '(' argumentExpressionList? ')'
    | postfixExpression '.' Identifier
    | postfixExpression '->' Identifier
    | postfixExpression '++'
    | postfixExpression '--'
    | '(' typeName ')' '{' initializerList '}'
    | '(' typeName ')' '{' initializerList ',' '}'
    | '__extension__' '(' typeName ')' '{' initializerList '}'
    | '__extension__' '(' typeName ')' '{' initializerList ',' '}'
    ;

argumentExpressionList
    : assignmentExpression
    | argumentExpressionList ',' assignmentExpression
    ;

unaryExpression
    : postfixExpression
    | '++' unaryExpression
    | '--' unaryExpression
    | unaryOperator castExpression
    | 'sizeof' unaryExpression
    | 'sizeof' '(' typeName ')'
    | '_Alignof' '(' typeName ')'
    | '&&' Identifier // GCC extension address of label
    ;

unaryOperator
    : '&' | '*' | '+' | '-' | '~' | '!'
    ;

castExpression
    : unaryExpression
    | '(' typeName ')' castExpression
    | '__extension__' '(' typeName ')' castExpression
    ;

multiplicativeExpression
    : castExpression
    | multiplicativeExpression '*' castExpression
    | multiplicativeExpression '/' castExpression
    | multiplicativeExpression '%' castExpression
    ;

additiveExpression
    : multiplicativeExpression
    | additiveExpression '+' multiplicativeExpression
    | additiveExpression '-' multiplicativeExpression
    ;

shiftExpression
    : additiveExpression
    | shiftExpression '<<' additiveExpression
    | shiftExpression '>>' additiveExpression
    ;

relationalExpression
    : shiftExpression
    | relationalExpression '<' shiftExpression
    | relationalExpression '>' shiftExpression
    | relationalExpression '<=' shiftExpression
    | relationalExpression '>=' shiftExpression
    ;

equalityExpression
    : relationalExpression
    | equalityExpression '==' relationalExpression
    | equalityExpression '!=' relationalExpression
    ;

andExpression
    : equalityExpression
    | andExpression '&' equalityExpression
    ;

exclusiveOrExpression
    : andExpression
    | exclusiveOrExpression '^' andExpression
    ;

inclusiveOrExpression
    : exclusiveOrExpression
    | inclusiveOrExpression '|' exclusiveOrExpression
    ;

logicalAndExpression
    : inclusiveOrExpression
    | logicalAndExpression '&&' inclusiveOrExpression
    ;

logicalOrExpression
    : logicalAndExpression
    | logicalOrExpression '||' logicalAndExpression
    ;

conditionalExpression
    : logicalOrExpression ('?' expression ':' conditionalExpression)?
    ;

assignmentExpression
    : conditionalExpression
    | unaryExpression assignmentOperator assignmentExpression
    ;

assignmentOperator
    : '=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>>=' | '&=' | '^=' | '|='
    ;

expression
    : assignmentExpression
    | expression ',' assignmentExpression
    ;

constantExpression
    : conditionalExpression
    ;


/************************************ DECLARATIONS ************************************/

declaration returns [List<String> names = new ArrayList<String>(), Type type = null, boolean isStatic, boolean isFunction]
    : ds=declarationSpecifiers idl=initDeclaratorList? ';'  {declareTypedef($ds.isTypedef, $ds.name, $names); $isStatic = $ds.isStatic;
    														if(!$ds.isTypedef) $type = $ds.specifier.getType();}
    | staticAssertDeclaration
    ;

declarationSpecifiers returns [DeclarationSpecifier specifier = new NullSpecifier(), String name = null, boolean isStatic = false, boolean isTypedef = false]
    : declarationSpecifier+
    ;

declarationSpecifier
    : storageClassSpecifier
    | ts=typeSpecifier {$declarationSpecifiers::specifier = $declarationSpecifiers::specifier.merge($ts.specifier); 
    					if($ts.name != null) $declarationSpecifiers::name = $ts.name;}
    | typeQualifier
    | functionSpecifier
    | alignmentSpecifier
    ;

initDeclaratorList 
    : initDeclarator
    | initDeclaratorList ',' initDeclarator
    ;

initDeclarator
    : d=declarator {$declaration::names.add($d.name); $declaration::isFunction = $d.isFunction;}
    | d=declarator '=' initializer {$declaration::names.add($d.name);} //PRENDRE EN COMPTE LES VALEURS D'INITIALISATION QUI PEUVENT ETRE DES VARIABLES
    ;

storageClassSpecifier
    : 'typedef' {$declarationSpecifiers::isTypedef = true;}
    | 'extern'
    | 'static' {$declarationSpecifiers::isStatic = true;}
    | '_Thread_local'
    | 'auto'
    | 'register'
    ;

typeSpecifier returns [DeclarationSpecifier specifier = new NullSpecifier(), String name = null]
    : ('void' {$specifier = new VoidSpecifier();}
    | 'char' {$specifier = new CharSpecifier();}
    | 'short' {$specifier = new ShortSpecifier();}
    | 'int' {$specifier = new IntSpecifier();}
    | 'long' {$specifier = new LongSpecifier();}
    | 'float' {$specifier = new FloatSpecifier();}
    | 'double' {$specifier = new DoubleSpecifier();}
    | 'signed' {$specifier = new SignedSpecifier();}
    | 'unsigned' {$specifier = new UnsignedSpecifier();}
    | '_Bool'
    | '_Complex'
    | '__m128'
    | '__m128d'
    | '__m128i')
    | '__extension__' '(' ('__m128' | '__m128d' | '__m128i') ')'
    | atomicTypeSpecifier
    | structOrUnionSpecifier
    | enumSpecifier
    | typedefName
    | '__typeof__' '(' constantExpression ')' // GCC extension
    ;

structOrUnionSpecifier
    : structOrUnion i=Identifier? '{' structDeclarationList '}' {this.addComplexType($i.text); $typeSpecifier::specifier = new StructOrUnionSpecifier($i.text, this.complexTypes);}
    | structOrUnion i=Identifier {$typeSpecifier::specifier = new StructOrUnionSpecifier($i.text, this.complexTypes);}
    ;

structOrUnion
    : 'struct'
    | 'union'
    ;

structDeclarationList
    : structDeclaration
    | structDeclarationList structDeclaration
    ;

structDeclaration
    : specifierQualifierList structDeclaratorList? ';'
    | staticAssertDeclaration
    ;

specifierQualifierList
    : typeSpecifier specifierQualifierList?
    | typeQualifier specifierQualifierList?
    ;

structDeclaratorList
    : structDeclarator
    | structDeclaratorList ',' structDeclarator
    ;

structDeclarator
    : declarator
    | declarator? ':' constantExpression
    ;

enumSpecifier
    : 'enum' i=Identifier? '{' enumeratorList '}' {this.addComplexType($i.text); $typeSpecifier::specifier = new EnumSpecifier($i.text, this.complexTypes);}
    | 'enum' i=Identifier? '{' enumeratorList ',' '}' {this.addComplexType($i.text); $typeSpecifier::specifier = new EnumSpecifier($i.text, this.complexTypes);}
    | 'enum' i=Identifier {$typeSpecifier::specifier = new EnumSpecifier($i.text, this.complexTypes);}
    ;

enumeratorList
    : enumerator
    | enumeratorList ',' enumerator
    ;

enumerator
    : enumerationConstant
    | enumerationConstant '=' constantExpression
    ;

enumerationConstant
    : Identifier
    ;

atomicTypeSpecifier
    : '_Atomic' '(' typeName ')'
    ;

typeQualifier
    : 'const'
    | 'restrict'
    | 'volatile'
    | '_Atomic'
    ;

functionSpecifier
    : ('inline'
    | '_Noreturn'
    | '__inline__' // GCC extension
    | '__stdcall')
    | gccAttributeSpecifier
    | '__declspec' '(' Identifier ')'
    ;

alignmentSpecifier
    : '_Alignas' '(' typeName ')'
    | '_Alignas' '(' constantExpression ')'
    ;

declarator returns [String name, Set<LocalVariable> arguments, boolean isFunction]
    : pointer? dd=directDeclarator gccDeclaratorExtension* {$name = $dd.name; $arguments = $dd.arguments; $isFunction = $dd.isFunction;}
    ;

directDeclarator returns [String name, Set<LocalVariable> arguments, boolean isFunction = false]
    : i=Identifier {$name = $i.text;}
    | '(' d=declarator ')' {$name = $d.name;}
    | dd=directDeclarator '[' typeQualifierList? assignmentExpression? ']' {$name = $dd.name;}
    | dd=directDeclarator '[' 'static' typeQualifierList? assignmentExpression ']' {$name = $dd.name;}
    | dd=directDeclarator '[' typeQualifierList 'static' assignmentExpression ']' {$name = $dd.name;}
    | dd=directDeclarator '[' typeQualifierList? '*' ']' {$name = $dd.name;}
    | dd=directDeclarator '(' ptl=parameterTypeList ')' {$name = $dd.name; $arguments = $ptl.arguments; $isFunction = true;}
    | dd=directDeclarator '(' identifierList? ')' {$name = $dd.name; $isFunction = true;}
    ;

gccDeclaratorExtension
    : '__asm' '(' StringLiteral+ ')'
    | gccAttributeSpecifier
    ;

gccAttributeSpecifier
    : '__attribute__' '(' '(' gccAttributeList ')' ')'
    ;

gccAttributeList
    : gccAttribute (',' gccAttribute)*
    | // empty
    ;

gccAttribute
    : ~(',' | '(' | ')') // relaxed def for "identifier or reserved word"
        ('(' argumentExpressionList? ')')?
    | // empty
    ;

nestedParenthesesBlock
    : ( ~('(' | ')')
        | '(' nestedParenthesesBlock ')'
        )*
    ;

pointer
    : '*' typeQualifierList?
    | '*' typeQualifierList? pointer
    | '^' typeQualifierList? // Blocks language extension
    | '^' typeQualifierList? pointer // Blocks language extension
    ;

typeQualifierList
    : typeQualifier
    | typeQualifierList typeQualifier
    ;

parameterTypeList returns [Set<LocalVariable> arguments = new HashSet<LocalVariable>()]
    : parameterList
    | parameterList ',' '...'
    ;

parameterList
    : parameterDeclaration
    | parameterList ',' parameterDeclaration
    ;

parameterDeclaration
    : ds=declarationSpecifiers d=declarator {$parameterTypeList::arguments.add(new LocalVariable($d.name, $ds.specifier.getType()));}
    | ds=declarationSpecifiers abstractDeclarator?
    ;

identifierList
    : Identifier
    | identifierList ',' Identifier
    ;

typeName
    : specifierQualifierList abstractDeclarator?
    ;

abstractDeclarator
    : pointer
    | pointer? directAbstractDeclarator gccDeclaratorExtension*
    ;

directAbstractDeclarator
    : '(' abstractDeclarator ')' gccDeclaratorExtension*
    | '[' typeQualifierList? assignmentExpression? ']'
    | '[' 'static' typeQualifierList? assignmentExpression ']'
    | '[' typeQualifierList 'static' assignmentExpression ']'
    | '[' '*' ']'
    | '(' parameterTypeList? ')' gccDeclaratorExtension*
    | directAbstractDeclarator '[' typeQualifierList? assignmentExpression? ']'
    | directAbstractDeclarator '[' 'static' typeQualifierList? assignmentExpression ']'
    | directAbstractDeclarator '[' typeQualifierList 'static' assignmentExpression ']'
    | directAbstractDeclarator '[' '*' ']'
    | directAbstractDeclarator '(' parameterTypeList? ')' gccDeclaratorExtension*
    ;

typedefName
    : i=Identifier {$typeSpecifier::specifier = new TypedefNameSpecifier($i.text, this.complexTypes); $typeSpecifier::name = $i.text;}
    ;

initializer
    : assignmentExpression
    | '{' initializerList '}'
    | '{' initializerList ',' '}'
    ;

initializerList
    : designation? initializer
    | initializerList ',' designation? initializer
    ;

designation
    : designatorList '='
    ;

designatorList
    : designator
    | designatorList designator
    ;

designator
    : '[' constantExpression ']'
    | '.' Identifier
    ;

staticAssertDeclaration
    : '_Static_assert' '(' constantExpression ',' StringLiteral+ ')' ';'
    ;


/************************************ Statements ************************************/

statement
    : labeledStatement
    | compoundStatement
    | expressionStatement
    | selectionStatement
    | iterationStatement
    | jumpStatement
    | ('__asm' | '__asm__') ('volatile' | '__volatile__') '(' (logicalOrExpression (',' logicalOrExpression)*)? (':' (logicalOrExpression (',' logicalOrExpression)*)?)* ')' ';'
    ;

labeledStatement
    : Identifier ':' statement
    | 'case' constantExpression ':' statement
    | 'default' ':' statement
    ;

compoundStatement
    : '{' blockItemList? '}'
    ;

blockItemList
    : blockItem
    | blockItemList blockItem
    ;

blockItem
    : declaration
    | statement
    ;

expressionStatement
    : expression? ';'
    ;

selectionStatement
    : 'if' '(' expression ')' statement ('else' statement)?
    | 'switch' '(' expression ')' statement
    ;

iterationStatement
    : 'while' '(' expression ')' statement
    | 'do' statement 'while' '(' expression ')' ';'
    | 'for' '(' expression? ';' expression? ';' expression? ')' statement
    | 'for' '(' declaration expression? ';' expression? ')' statement
    ;

jumpStatement
    : 'goto' Identifier ';'
    | 'continue' ';'
    | 'break' ';'
    | 'return' expression? ';'
    | 'goto' unaryExpression ';' // GCC extension
    ;


/************************************ External definitions ************************************/

compilationUnit
    : translationUnit? EOF
    ;

translationUnit
    : externalDeclaration
    | translationUnit externalDeclaration
    ;

externalDeclaration
    : functionDefinition
    | d=declaration {System.out.println($d.text);}
    | ';' // stray ;
    ;

functionDefinition returns [Function result]
    : ds=declarationSpecifiers? d=declarator declarationList? compoundStatement {/*System.out.println($d.name + ": " + $d.arguments);*/} //AJOUTER FONCTION DANS LA TABLE
    ;

declarationList
    : declaration
    | declarationList declaration
    ;


/************************************ Identifiers ************************************/

Identifier
    : IdentifierNondigit
        ( IdentifierNondigit
        | Digit
        )*
    ;

fragment
IdentifierNondigit
    : Nondigit
    | UniversalCharacterName
    ;

fragment
Nondigit
    : [a-zA-Z_]
    ;

fragment
Digit
    : [0-9]
    ;

fragment
UniversalCharacterName
    : '\\u' HexQuad
    | '\\U' HexQuad HexQuad
    ;

fragment
HexQuad
    : HexadecimalDigit HexadecimalDigit HexadecimalDigit HexadecimalDigit
    ;

Constant
    : IntegerConstant
    | FloatingConstant
    | CharacterConstant
    ;

fragment
IntegerConstant
    : DecimalConstant IntegerSuffix?
    | OctalConstant IntegerSuffix?
    | HexadecimalConstant IntegerSuffix?
    ;

fragment
DecimalConstant
    : NonzeroDigit Digit*
    ;

fragment
OctalConstant
    : '0' OctalDigit*
    ;

fragment
HexadecimalConstant
    : HexadecimalPrefix HexadecimalDigit+
    ;

fragment
HexadecimalPrefix
    : '0' [xX]
    ;

fragment
NonzeroDigit
    : [1-9]
    ;

fragment
OctalDigit
    : [0-7]
    ;

fragment
HexadecimalDigit
    : [0-9a-fA-F]
    ;

fragment
IntegerSuffix
    : UnsignedSuffix LongSuffix?
    | UnsignedSuffix LongLongSuffix
    | LongSuffix UnsignedSuffix?
    | LongLongSuffix UnsignedSuffix?
    ;

fragment
UnsignedSuffix
    : [uU]
    ;

fragment
LongSuffix
    : [lL]
    ;

fragment
LongLongSuffix
    : 'll' | 'LL'
    ;

fragment
FloatingConstant
    : DecimalFloatingConstant
    | HexadecimalFloatingConstant
    ;

fragment
DecimalFloatingConstant
    : FractionalConstant ExponentPart? FloatingSuffix?
    | DigitSequence ExponentPart FloatingSuffix?
    ;

fragment
HexadecimalFloatingConstant
    : HexadecimalPrefix HexadecimalFractionalConstant BinaryExponentPart FloatingSuffix?
    | HexadecimalPrefix HexadecimalDigitSequence BinaryExponentPart FloatingSuffix?
    ;

fragment
FractionalConstant
    : DigitSequence? '.' DigitSequence
    | DigitSequence '.'
    ;

fragment
ExponentPart
    : 'e' Sign? DigitSequence
    | 'E' Sign? DigitSequence
    ;

fragment
Sign
    : '+' | '-'
    ;

fragment
DigitSequence
    : Digit+
    ;

fragment
HexadecimalFractionalConstant
    : HexadecimalDigitSequence? '.' HexadecimalDigitSequence
    | HexadecimalDigitSequence '.'
    ;

fragment
BinaryExponentPart
    : 'p' Sign? DigitSequence
    | 'P' Sign? DigitSequence
    ;

fragment
HexadecimalDigitSequence
    : HexadecimalDigit+
    ;

fragment
FloatingSuffix
    : 'f' | 'l' | 'F' | 'L'
    ;

fragment
CharacterConstant
    : '\'' CCharSequence '\''
    | 'L\'' CCharSequence '\''
    | 'u\'' CCharSequence '\''
    | 'U\'' CCharSequence '\''
    ;

fragment
CCharSequence
    : CChar+
    ;

fragment
CChar
    : ~['\\\r\n]
    | EscapeSequence
    ;

fragment
EscapeSequence
    : SimpleEscapeSequence
    | OctalEscapeSequence
    | HexadecimalEscapeSequence
    | UniversalCharacterName
    ;

fragment
SimpleEscapeSequence
    : '\\' ['"?abfnrtv\\]
    ;

fragment
OctalEscapeSequence
    : '\\' OctalDigit
    | '\\' OctalDigit OctalDigit
    | '\\' OctalDigit OctalDigit OctalDigit
    ;

fragment
HexadecimalEscapeSequence
    : '\\x' HexadecimalDigit+
    ;

StringLiteral
    : EncodingPrefix? '"' SCharSequence? '"'
    ;

fragment
EncodingPrefix
    : 'u8'
    | 'u'
    | 'U'
    | 'L'
    ;

fragment
SCharSequence
    : SChar+
    ;

fragment
SChar
    : ~["\\\r\n]
    | EscapeSequence
    ;

PreprocessingDirective
    : '#' ~[\r\n]*
        -> skip
    ;

Whitespace
    : [ \t]+
        -> skip
    ;

Newline
    : ( '\r' '\n'?
        | '\n'
        )
        -> skip
    ;

BlockComment
    : '/*' .*? '*/'
        -> skip
    ;

LineComment
    : '//' ~[\r\n]*
        -> skip
    ;