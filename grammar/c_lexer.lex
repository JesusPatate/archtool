package cparser;

import java_cup.runtime.*;

%%

%public
%class CLexer

%unicode

%line
%column

%cup
%cupdebug

%{
	private Symbol symbol(int type)
	{
		return new Symbol(type, yyline + 1, yycolumn + 1);
	}

	private Symbol symbol(int type, Object value) 
	{
		return new Symbol(type, yyline + 1, yycolumn + 1, value);
	}
%}

O = [0-7]
D = [0-9]
NZ = [1-9]
L = [a-zA-Z_]
A = [a-zA-Z_0-9]
H = [a-fA-F0-9]
HP = (0[xX])
E = ([Ee][+-]?{D}+)
P = ([Pp][+-]?{D}+)
FS = (f|F|l|L)
IS = (((u|U)(l|L|ll|LL)?)|((l|L|ll|LL)(u|U)?))
CP = (u|U|L)
SP = (u8|u|U|L)
ES = (\\(['"\?\\abfnrtv]|[0-7]{1,3}|x[a-fA-F0-9]+))
WS = [ \t\v\n\f]

%%

<YYINITIAL> 
{
"/*"                                    { comment(); }
"//".*                                    { /* consume //-comment */ }

"auto"					{return(symbol(sym.AUTO);}
"break"					{return(symbol(sym.BREAK);}
"case"					{return(symbol(sym.CASE);}
"char"					{return(symbol(sym.CHAR);}
"const"					{return(symbol(sym.CONST);}
"continue"				{return(symbol(sym.CONTINUE);}
"default"				{return(symbol(sym.DEFAULT);}
"do"					{return(symbol(sym.DO);}
"double"				{return(symbol(sym.DOUBLE);}
"else"					{return(symbol(sym.ELSE);}
"enum"					{return(symbol(sym.ENUM);}
"extern"				{return(symbol(sym.EXTERN);}
"float"					{return(symbol(sym.FLOAT);}
"for"					{return(symbol(sym.FOR);}
"goto"					{return(symbol(sym.GOTO);}
"if"					{return(symbol(sym.IF);}
"inline"				{return(symbol(sym.INLINE);}
"int"					{return(symbol(sym.INT);}
"long"					{return(symbol(sym.LONG);}
"register"				{return(symbol(sym.REGISTER);}
"restrict"				{return(symbol(sym.RESTRICT);}
"return"				{return(symbol(sym.RETURN);}
"short"					{return(symbol(sym.SHORT);}
"signed"				{return(symbol(sym.SIGNED);}
"sizeof"				{return(symbol(sym.SIZEOF);}
"static"				{return(symbol(sym.STATIC);}
"struct"				{return(symbol(sym.STRUCT);}
"switch"				{return(symbol(sym.SWITCH);}
"typedef"				{return(symbol(sym.TYPEDEF);}
"union"					{return(symbol(sym.UNION);}
"unsigned"				{return(symbol(sym.UNSIGNED);}
"void"					{return(symbol(sym.VOID);}
"volatile"				{return(symbol(sym.VOLATILE);}
"while"					{return(symbol(sym.WHILE);}
"_Alignas"              {return(symbol(sym.ALIGNAS);}
"_Alignof"              {return(symbol(sym.ALIGNOF);}
"_Atomic"               {return(symbol(sym.ATOMIC);}
"_Bool"                 {return(symbol(sym.BOOL);}
"_Complex"              {return(symbol(sym.COMPLEX);}
"_Generic"              {return(symbol(sym.GENERIC);}
"_Imaginary"            {return(symbol(sym.IMAGINARY);}
"_Noreturn"             {return(symbol(sym.NORETURN);}
"_Static_assert"        {return(symbol(sym.STATIC_ASSERT);}
"_Thread_local"         {return(symbol(sym.THREAD_LOCAL);}
"__func__"              {return(symbol(sym.FUNC_NAME);}

{L}{A}*					{return(symbol(sym.IDENTIFIER, yytext());}

{HP}{H}+{IS}?			{return(symbol(sym.I_CONSTANT);}
{NZ}{D}*{IS}?			{return(symbol(sym.I_CONSTANT);}
"0"{O}*{IS}?				{return(symbol(sym.I_CONSTANT);}
{CP}?"'"([^'\\\n]|{ES})+"'"		{return(symbol(sym.I_CONSTANT);}

{D}+{E}{FS}?				{return(symbol(sym.F_CONSTANT);}
{D}*"."{D}+{E}?{FS}?		{return(symbol(sym.F_CONSTANT);}
{D}+"."{E}?{FS}?			{return(symbol(sym.F_CONSTANT);}
{HP}{H}+{P}{FS}?			{return(symbol(sym.F_CONSTANT);}
{HP}{H}*"."{H}+{P}{FS}?		{return(symbol(sym.F_CONSTANT);}
{HP}{H}+"."{P}{FS}?			{return(symbol(sym.F_CONSTANT);}

({SP}?\"([^"\\\n]|{ES})*\"{WS}*)+	{return(symbol(sym.STRING_LITERAL);}

"..."					{return(symbol(sym.ELLIPSIS);}
">>="					{return(symbol(sym.RIGHT_ASSIGN);}
"<<="					{return(symbol(sym.LEFT_ASSIGN);}
"+="					{return(symbol(sym.ADD_ASSIGN);}
"-="					{return(symbol(sym.SUB_ASSIGN);}
"*="					{return(symbol(sym.MUL_ASSIGN);}
"/="					{return(symbol(sym.DIV_ASSIGN);}
"%="					{return(symbol(sym.MOD_ASSIGN);}
"&="					{return(symbol(sym.AND_ASSIGN);}
"^="					{return(symbol(sym.XOR_ASSIGN);}
"|="					{return(symbol(sym.OR_ASSIGN);}
">>"					{return(symbol(sym.RIGHT_OP);}
"<<"					{return(symbol(sym.LEFT_OP);}
"++"					{return(symbol(sym.INC_OP);}
"--"					{return(symbol(sym.DEC_OP);}
"->"					{return(symbol(sym.PTR_OP);}
"&&"					{return(symbol(sym.AND_OP);}
"||"					{return(symbol(sym.OR_OP);}
"<="					{return(symbol(sym.LE_OP);}
">="					{return(symbol(sym.GE_OP);}
"=="					{return(symbol(sym.EQ_OP);}
"!="					{return(symbol(sym.NE_OP);}
";"						{return(symbol(sym.SEMI_COLON);}
("{"|"<%")				{return(symbol(sym.LEFT_BRACE);}
("}"|"%>")				{return(symbol(sym.RIGHT_BRACE);}
","						{return(symbol(sym.COMMA);}
":"						{return(symbol(sym.COLON);}
"="						{return(symbol(sym.ASSIGNMENT);}
"("						{return(symbol(sym.LEFT_PAREN);}
")"						{return(symbol(sym.RIGHT_PAREN);}
("["|"<:")				{return(symbol(sym.LEFT_BRACKET);}
("]"|":>")				{return(symbol(sym.RIGHT_BRACKET);}
"."						{return(symbol(sym.FIELD_OP);}
"&"						{return(symbol(sym.BAND_OP);}
"!"						{return(symbol(sym.NOT_OP);}
"~"						{return(symbol(sym.BNOT_OP);}
"-"						{return(symbol(sym.MINUS_OP);}
"+"						{return(symbol(sym.PLUS_OP);}
"*"						{return(symbol(sym.MUL_OP);}
"/"						{return(symbol(sym.DIV_OP);}
"%"						{return(symbol(sym.MOD_OP);}
"<"						{return(symbol(sym.INF_OP);}
">"						{return(symbol(sym.SUP_OP);}
"^"						{return(symbol(sym.BXOR_OP);}
"|"						{return(symbol(sym.BOR_OP);}
"?"						{return(symbol(sym.IS_OP);}

{WS}					{/* whitespace separates tokens */}
.						{return symbol(sym.error);}
<<EOF>>					{return symbol(sym.EOF);}