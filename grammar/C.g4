grammar C;


@header
{
	package fr.univ_nantes.alma.archtool.parsing;
	
	import java.util.Map;
	import java.util.Map.Entry;
	import java.util.HashMap;
	import java.util.HashSet;
	import java.util.Set;
	import fr.univ_nantes.alma.archtool.utils.MultiCounter;
	import fr.univ_nantes.alma.archtool.sourceModel.*;
	import fr.univ_nantes.alma.archtool.parsing.specifier.*;
}

@parser::members
{
	private File currentFile;
	private Map<String, Function> functions;
	private Map<String, ComplexType> complexTypes;
	private Map<String, GlobalVariable> globalVariables;
	private Map<String, Function> otherFunctions = 
	        new HashMap<String, Function>();
	private Map<String, ComplexType> otherComplexTypes = 
	        new HashMap<String, ComplexType>();
	
	private void addComplexType(String name)
	{
		if(name != null)
		{
			this.complexTypes.put(name, 
					new ComplexType(name, this.currentFile));
		}
	}
	
	public void setContext(Context context)
	{
		this.functions = context.getFunctions();
		this.complexTypes = context.getComplexTypes();
		this.globalVariables = context.getGlobalVariables();
	}
	
	public void setCurrentFile(File currentFile)
	{
		this.currentFile = currentFile;
	}
	
	public void cleanUp()
	{
		this.functions.clear();
		this.complexTypes.clear();
		this.globalVariables.clear();
	}
	
	public Set<Function> getFunctions()
	{
		return new HashSet<Function>(this.functions.values());
	}
	
	public Set<ComplexType> getComplexTypes()
	{
		return new HashSet<ComplexType>(this.complexTypes.values());
	}
	
	public Set<GlobalVariable> getGlobalVariables()
	{
		return new HashSet<GlobalVariable>(this.globalVariables.values());
	}
}


/************************************ Expressions ************************************/

primaryExpression returns [String name = null, 
        MultiCounter<String> variablesNameUsed = new MultiCounter<String>(), 
        CallCounter calls = new CallCounter(), Set<String> parameters = 
        new HashSet<String>()]
    : i=Identifier
{
    $name = $i.text;
}
    | Constant
    | StringLiteral+
    | '(' e=expression ')'
{
    $parameters.addAll($e.parameters);
    $variablesNameUsed.incrementAll($e.variablesNameUsed);
    $calls.incrementAll($e.calls);
}
    | gs=genericSelection
{
    $parameters.addAll($gs.parameters);
    $variablesNameUsed.incrementAll($gs.variablesNameUsed);
    $calls.incrementAll($gs.calls); 
}
    | '__extension__'? '(' cs=compoundStatement[null] ')'
{
        // TODO
}
    | '__builtin_va_arg' '(' ue=unaryExpression ',' typeName ')'
{
    $parameters.addAll($ue.parameters);
    $variablesNameUsed.incrementAll($ue.variablesNameUsed);
    $calls.incrementAll($ue.calls);
}
    | '__builtin_offsetof' '(' typeName ',' ue=unaryExpression ')'
{
    $parameters.addAll($ue.parameters);
    $variablesNameUsed.incrementAll($ue.variablesNameUsed);
    $calls.incrementAll($ue.calls);
}
    ;

genericSelection returns [MultiCounter variablesNameUsed = new MultiCounter(), 
        CallCounter calls = new CallCounter(), Set<String> parameters = 
        new HashSet<String>()]
    : '_Generic' '(' ae=assignmentExpression ',' gal=genericAssocList ')'
{
    $parameters.addAll($ae.parameters);
    $variablesNameUsed.incrementAll($ae.variablesNameUsed);
    $calls.incrementAll($ae.calls);
    $parameters.addAll($gal.parameters);
    $variablesNameUsed.incrementAll($gal.variablesNameUsed);
    $calls.incrementAll($gal.calls);
}
    ;

genericAssocList returns [MultiCounter variablesNameUsed = new MultiCounter(), 
        CallCounter calls = new CallCounter(), Set<String> parameters = 
        new HashSet<String>()]
    : ga=genericAssociation
{
    $parameters.addAll($ga.parameters);
    $variablesNameUsed.incrementAll($ga.variablesNameUsed);
    $calls.incrementAll($ga.calls);  
}
    | gal=genericAssocList ',' ga=genericAssociation
{
    $parameters.addAll($gal.parameters);
    $variablesNameUsed.incrementAll($gal.variablesNameUsed);
    $calls.incrementAll($gal.calls);
    $parameters.addAll($ga.parameters);
    $variablesNameUsed.incrementAll($ga.variablesNameUsed);
    $calls.incrementAll($ga.calls);    
}
    ;

genericAssociation returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter(), 
        Set<String> parameters = new HashSet<String>()]
    : typeName ':' ae=assignmentExpression
{
    $parameters.addAll($ae.parameters);
    $variablesNameUsed.incrementAll($ae.variablesNameUsed);
    $calls.incrementAll($ae.calls);     
}
    | 'default' ':' ae=assignmentExpression
{
    $parameters.addAll($ae.parameters);
    $variablesNameUsed.incrementAll($ae.variablesNameUsed);
    $calls.incrementAll($ae.calls);     
}
    ;

postfixExpression returns [String name = null, 
        MultiCounter<String> variablesNameUsed = new MultiCounter<String>(), 
        CallCounter calls = new CallCounter(),
        boolean isCall = false,
        Set<String> parameters = new HashSet<String>()]
@after
{
    if($name != null && !$isCall)
    {
        $variablesNameUsed.increment($name);
        $parameters.add($name);
    }
}
    : px=primaryExpression
{
    $name = $px.name;
    $parameters.addAll($px.parameters);
    $variablesNameUsed.incrementAll($px.variablesNameUsed);
    $calls.incrementAll($px.calls);
}
    | pe=postfixExpression '[' e=expression ']'
{
    $name = $pe.name;
    $parameters.addAll($pe.parameters);
    $variablesNameUsed.incrementAll($pe.variablesNameUsed);
    $calls.incrementAll($pe.calls);
    $parameters.addAll($e.parameters);
    $variablesNameUsed.incrementAll($e.variablesNameUsed);
    $calls.incrementAll($e.calls);
}
    | pe=postfixExpression '(' ag=argumentExpressionList? ')'
{
    $isCall = true;
    Set<String> parameters = new HashSet<String>();
    $name = $pe.name;
    $parameters.addAll($pe.parameters);
    $variablesNameUsed.incrementAll($pe.variablesNameUsed);
    $calls.incrementAll($pe.calls);
    
    if($ag.text != null)
    {
        $variablesNameUsed.incrementAll($ag.variablesNameUsed);
        $calls.incrementAll($ag.calls);
        parameters = $ag.parameters;
    }
    
    $calls.increment($name, parameters);
}
    | pe=postfixExpression '.' Identifier
{
    $name = $pe.name;
    $parameters.addAll($pe.parameters);
    $variablesNameUsed.incrementAll($pe.variablesNameUsed);
    $calls.incrementAll($pe.calls);
}
    | pe=postfixExpression '->' Identifier
{
    $name = $pe.name;
    $parameters.addAll($pe.parameters);
    $variablesNameUsed.incrementAll($pe.variablesNameUsed);
    $calls.incrementAll($pe.calls);
}
    | pe=postfixExpression '++'
{
    $name = $pe.name;
    $parameters.addAll($pe.parameters);
    $variablesNameUsed.incrementAll($pe.variablesNameUsed);
    $calls.incrementAll($pe.calls);
}
    | pe=postfixExpression '--'
{            
    $name = $pe.name;
    $parameters.addAll($pe.parameters);
    $variablesNameUsed.incrementAll($pe.variablesNameUsed);
    $calls.incrementAll($pe.calls);
}
                
    | '(' typeName ')' '{' il=initializerList '}'
{
    // TODO
    /*$parameters.addAll($pe.parameters);
    $variablesNameUsed.addAll($il.variablesNameUsed);
    $calls.addAll($il.calls);*/
}
    | '(' typeName ')' '{' il=initializerList ',' '}'
{
        // TODO
}
    | '__extension__' '(' typeName ')' '{' il=initializerList '}'
{
        // TODO
}
    | '__extension__' '(' typeName ')' '{' il=initializerList ',' '}'
{
        // TODO
}
    ;

argumentExpressionList returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter(),
        Set<String> parameters = new HashSet<String>()]
    : ae=assignmentExpression
{
    $parameters.addAll($ae.parameters);
    $variablesNameUsed.incrementAll($ae.variablesNameUsed);
    $calls.incrementAll($ae.calls);
}
    | ael=argumentExpressionList ',' ae=assignmentExpression
{
    $parameters.addAll($ael.parameters);
    $variablesNameUsed.incrementAll($ael.variablesNameUsed);
    $calls.incrementAll($ael.calls);
    $parameters.addAll($ae.parameters);
    $variablesNameUsed.incrementAll($ae.variablesNameUsed);
    $calls.incrementAll($ae.calls);
}
    ;

unaryExpression returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter(),
        Set<String> parameters = new HashSet<String>()]
    : pe=postfixExpression
{
    $parameters.addAll($pe.parameters);
    $variablesNameUsed.incrementAll($pe.variablesNameUsed);
    $calls.incrementAll($pe.calls);
}
    | '++' ue=unaryExpression
{
    $parameters.addAll($ue.parameters);    
    $variablesNameUsed.incrementAll($ue.variablesNameUsed);
    $calls.incrementAll($ue.calls);    
}
    | '--' ue=unaryExpression
{
    $parameters.addAll($ue.parameters);
    $variablesNameUsed.incrementAll($ue.variablesNameUsed);
    $calls.incrementAll($ue.calls);    
}
    | unaryOperator ce=castExpression
{
    $parameters.addAll($ce.parameters);
    $variablesNameUsed.incrementAll($ce.variablesNameUsed);
    $calls.incrementAll($ce.calls);  
}
    | 'sizeof' ue=unaryExpression
{
    $parameters.addAll($ue.parameters);
    $variablesNameUsed.incrementAll($ue.variablesNameUsed);
    $calls.incrementAll($ue.calls);   
}
    | 'sizeof' '(' typeName ')'
    | '_Alignof' '(' typeName ')'
    | '&&' i=Identifier
{
    $variablesNameUsed.increment($i.text);
    $parameters.add($i.text);
}
    ;

unaryOperator
    : '&' | '*' | '+' | '-' | '~' | '!'
    ;

castExpression returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter(),
        Set<String> parameters = new HashSet<String>()]
    : ue=unaryExpression
{
    $parameters.addAll($ue.parameters);
    $variablesNameUsed.incrementAll($ue.variablesNameUsed);
    $calls.incrementAll($ue.calls);
}
    | '(' typeName ')' ce=castExpression
{
    $parameters.addAll($ce.parameters);
    $variablesNameUsed.incrementAll($ce.variablesNameUsed);
    $calls.incrementAll($ce.calls);    
}
    | '__extension__' '(' typeName ')' ce=castExpression
{
    $parameters.addAll($ce.parameters);
    $variablesNameUsed.incrementAll($ce.variablesNameUsed);
    $calls.incrementAll($ce.calls);    
}
    ;

multiplicativeExpression returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter(),
        Set<String> parameters = new HashSet<String>()]
    : ce=castExpression
{
    $parameters.addAll($ce.parameters);
    $variablesNameUsed.incrementAll($ce.variablesNameUsed);
    $calls.incrementAll($ce.calls);
}
    | me=multiplicativeExpression '*' ce=castExpression
{
    $parameters.addAll($me.parameters);
    $variablesNameUsed.incrementAll($me.variablesNameUsed);
    $calls.incrementAll($me.calls);
    $parameters.addAll($ce.parameters);
    $variablesNameUsed.incrementAll($ce.variablesNameUsed);
    $calls.incrementAll($ce.calls);
} 
    | me=multiplicativeExpression '/' ce=castExpression
{
    $parameters.addAll($me.parameters);
    $variablesNameUsed.incrementAll($me.variablesNameUsed);
    $calls.incrementAll($me.calls);
    $parameters.addAll($ce.parameters);
    $variablesNameUsed.incrementAll($ce.variablesNameUsed);
    $calls.incrementAll($ce.calls);
} 
    | me=multiplicativeExpression '%' ce=castExpression
{
    $parameters.addAll($me.parameters);
    $variablesNameUsed.incrementAll($me.variablesNameUsed);
    $calls.incrementAll($me.calls);
    $parameters.addAll($ce.parameters);
    $variablesNameUsed.incrementAll($ce.variablesNameUsed);
    $calls.incrementAll($ce.calls);
} 
    ;

additiveExpression returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter(),
        Set<String> parameters = new HashSet<String>()]
    : me=multiplicativeExpression
{
    $parameters.addAll($me.parameters);
    $variablesNameUsed.incrementAll($me.variablesNameUsed);
    $calls.incrementAll($me.calls);
}
    | ae=additiveExpression '+' me=multiplicativeExpression
{
    $parameters.addAll($ae.parameters);
    $variablesNameUsed.incrementAll($ae.variablesNameUsed);
    $calls.incrementAll($ae.calls);
    $parameters.addAll($me.parameters);
    $variablesNameUsed.incrementAll($me.variablesNameUsed);
    $calls.incrementAll($me.calls);
} 
    | ae=additiveExpression '-' me=multiplicativeExpression
{
    $parameters.addAll($ae.parameters);
    $variablesNameUsed.incrementAll($ae.variablesNameUsed);
    $calls.incrementAll($ae.calls);
    $parameters.addAll($me.parameters);
    $variablesNameUsed.incrementAll($me.variablesNameUsed);
    $calls.incrementAll($me.calls);
} 
    ;

shiftExpression returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter(),
        Set<String> parameters = new HashSet<String>()]
    : ae=additiveExpression
{
    $parameters.addAll($ae.parameters);
    $variablesNameUsed.incrementAll($ae.variablesNameUsed);
    $calls.incrementAll($ae.calls);
}
    | se=shiftExpression '<<' ae=additiveExpression
{
    $parameters.addAll($se.parameters);
    $variablesNameUsed.incrementAll($se.variablesNameUsed);
    $calls.incrementAll($se.calls);
    $parameters.addAll($ae.parameters);
    $variablesNameUsed.incrementAll($ae.variablesNameUsed);
    $calls.incrementAll($ae.calls);
} 
    | se=shiftExpression '>>' ae=additiveExpression
{
    $parameters.addAll($se.parameters);
    $variablesNameUsed.incrementAll($se.variablesNameUsed);
    $calls.incrementAll($se.calls);
    $parameters.addAll($ae.parameters);
    $variablesNameUsed.incrementAll($ae.variablesNameUsed);
    $calls.incrementAll($ae.calls);
} 
    ;

relationalExpression returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter(),
        Set<String> parameters = new HashSet<String>()]
    : se=shiftExpression
{
    $parameters.addAll($se.parameters);
    $variablesNameUsed.incrementAll($se.variablesNameUsed);
    $calls.incrementAll($se.calls);
}
    | re=relationalExpression '<' se=shiftExpression
{
    $parameters.addAll($re.parameters);
    $variablesNameUsed.incrementAll($re.variablesNameUsed);
    $calls.incrementAll($re.calls);
    $parameters.addAll($se.parameters);
    $variablesNameUsed.incrementAll($se.variablesNameUsed);
    $calls.incrementAll($se.calls);
} 
    | re=relationalExpression '>' se=shiftExpression
{
    $parameters.addAll($re.parameters);
    $variablesNameUsed.incrementAll($re.variablesNameUsed);
    $calls.incrementAll($re.calls);
    $parameters.addAll($se.parameters);
    $variablesNameUsed.incrementAll($se.variablesNameUsed);
    $calls.incrementAll($se.calls);
} 
    | re=relationalExpression '<=' se=shiftExpression
{
    $parameters.addAll($re.parameters);
    $variablesNameUsed.incrementAll($re.variablesNameUsed);
    $calls.incrementAll($re.calls);
    $parameters.addAll($se.parameters);
    $variablesNameUsed.incrementAll($se.variablesNameUsed);
    $calls.incrementAll($se.calls);
} 
    | re=relationalExpression '>=' se=shiftExpression
{
    $parameters.addAll($re.parameters);
    $variablesNameUsed.incrementAll($re.variablesNameUsed);
    $calls.incrementAll($re.calls);
    $parameters.addAll($se.parameters);
    $variablesNameUsed.incrementAll($se.variablesNameUsed);
    $calls.incrementAll($se.calls);
} 
    ;

equalityExpression returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter(),
        Set<String> parameters = new HashSet<String>()]
    : re=relationalExpression
{
    $parameters.addAll($re.parameters);
    $variablesNameUsed.incrementAll($re.variablesNameUsed);
    $calls.incrementAll($re.calls);
}
    | ee=equalityExpression '==' re=relationalExpression
{
    $parameters.addAll($ee.parameters);
    $variablesNameUsed.incrementAll($ee.variablesNameUsed);
    $calls.incrementAll($ee.calls);
    $parameters.addAll($re.parameters);
    $variablesNameUsed.incrementAll($re.variablesNameUsed);
    $calls.incrementAll($re.calls);
} 
    | ee=equalityExpression '!=' re=relationalExpression
{
    $parameters.addAll($ee.parameters);
    $variablesNameUsed.incrementAll($ee.variablesNameUsed);
    $calls.incrementAll($ee.calls);
    $parameters.addAll($re.parameters);
    $variablesNameUsed.incrementAll($re.variablesNameUsed);
    $calls.incrementAll($re.calls);
} 
    ;

andExpression returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter(),
        Set<String> parameters = new HashSet<String>()]
    : ee=equalityExpression
{
    $parameters.addAll($ee.parameters);
    $variablesNameUsed.incrementAll($ee.variablesNameUsed);
    $calls.incrementAll($ee.calls);
} 
    | ae=andExpression '&' ee=equalityExpression
{
    $parameters.addAll($ae.parameters);
    $variablesNameUsed.incrementAll($ae.variablesNameUsed);
    $calls.incrementAll($ae.calls);
    $parameters.addAll($ee.parameters);
    $variablesNameUsed.incrementAll($ee.variablesNameUsed);
    $calls.incrementAll($ee.calls);
} 
    ;

exclusiveOrExpression returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter(),
        Set<String> parameters = new HashSet<String>()]
    : ae=andExpression
{
    $parameters.addAll($ae.parameters);
    $variablesNameUsed.incrementAll($ae.variablesNameUsed);
    $calls.incrementAll($ae.calls);
}  
    | eoe=exclusiveOrExpression '^' ae=andExpression
{
    $parameters.addAll($eoe.parameters);
    $variablesNameUsed.incrementAll($eoe.variablesNameUsed);
    $calls.incrementAll($eoe.calls);
    $parameters.addAll($ae.parameters);
    $variablesNameUsed.incrementAll($ae.variablesNameUsed);
    $calls.incrementAll($ae.calls);
}            
    ;

inclusiveOrExpression returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter(),
        Set<String> parameters = new HashSet<String>()]
    : eoe=exclusiveOrExpression
{
    $parameters.addAll($eoe.parameters);
    $variablesNameUsed.incrementAll($eoe.variablesNameUsed);
    $calls.incrementAll($eoe.calls);
}
    | ioe=inclusiveOrExpression '|' eoe=exclusiveOrExpression
{
    $parameters.addAll($ioe.parameters);
    $variablesNameUsed.incrementAll($ioe.variablesNameUsed);
    $calls.incrementAll($ioe.calls);
    $parameters.addAll($eoe.parameters);
    $variablesNameUsed.incrementAll($eoe.variablesNameUsed);
    $calls.incrementAll($eoe.calls);
}
    ;

logicalAndExpression returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter(),
        Set<String> parameters = new HashSet<String>()]
    : ioe=inclusiveOrExpression
{
    $parameters.addAll($ioe.parameters);
    $variablesNameUsed.incrementAll($ioe.variablesNameUsed);
    $calls.incrementAll($ioe.calls);
}
    | lae=logicalAndExpression '&&' ioe=inclusiveOrExpression
{
    $parameters.addAll($lae.parameters);
    $variablesNameUsed.incrementAll($lae.variablesNameUsed);
    $calls.incrementAll($lae.calls);
    $parameters.addAll($ioe.parameters);
    $variablesNameUsed.incrementAll($ioe.variablesNameUsed);
    $calls.incrementAll($ioe.calls);
}
    ;

logicalOrExpression returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter(),
        Set<String> parameters = new HashSet<String>()]
    : lae=logicalAndExpression
{
    $parameters.addAll($lae.parameters);
    $variablesNameUsed.incrementAll($lae.variablesNameUsed);
    $calls.incrementAll($lae.calls);
}
    | loe=logicalOrExpression '||' lae=logicalAndExpression
{
    $parameters.addAll($loe.parameters);
    $variablesNameUsed.incrementAll($loe.variablesNameUsed);
    $calls.incrementAll($loe.calls);
    $parameters.addAll($lae.parameters);
    $variablesNameUsed.incrementAll($lae.variablesNameUsed);
    $calls.incrementAll($lae.calls);
}
    ;

conditionalExpression returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter(),
        Set<String> parameters = new HashSet<String>()]
    : loe=logicalOrExpression ('?' e=expression ':' ce=conditionalExpression)?
{
    $parameters.addAll($loe.parameters);
    $variablesNameUsed.incrementAll($loe.variablesNameUsed);
    $calls.incrementAll($loe.calls);
    
    if($e.text != null)
    {
        $parameters.addAll($e.parameters);
        $variablesNameUsed.incrementAll($e.variablesNameUsed);
        $calls.incrementAll($e.calls);
        $parameters.addAll($ce.parameters);
        $variablesNameUsed.incrementAll($ce.variablesNameUsed);
        $calls.incrementAll($ce.calls);
    }
}
    ;

assignmentExpression returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter(),
        Set<String> parameters = new HashSet<String>()]
    : ce=conditionalExpression
{
    $parameters.addAll($ce.parameters);    
    $variablesNameUsed.incrementAll($ce.variablesNameUsed);
    $calls.incrementAll($ce.calls);
}
    | ue=unaryExpression assignmentOperator ae=assignmentExpression
{
    $parameters.addAll($ue.parameters);
    $variablesNameUsed.incrementAll($ue.variablesNameUsed);
    $calls.incrementAll($ue.calls);
    $parameters.addAll($ae.parameters);
    $variablesNameUsed.incrementAll($ae.variablesNameUsed);
    $calls.incrementAll($ae.calls);
}
    ;

assignmentOperator
    : '=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>>=' | '&=' | '^=' | '|='
    ;

expression returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter(),
        Set<String> parameters = new HashSet<String>()]
    : ae=assignmentExpression
{
    $parameters.addAll($ae.parameters);
    $variablesNameUsed.incrementAll($ae.variablesNameUsed);
    $calls.incrementAll($ae.calls);
}
    | e=expression ',' ae=assignmentExpression
{
    $parameters.addAll($e.parameters);
    $variablesNameUsed.incrementAll($e.variablesNameUsed);
    $calls.incrementAll($e.calls);
    $parameters.addAll($ae.parameters);
    $variablesNameUsed.incrementAll($ae.variablesNameUsed);
    $calls.incrementAll($ae.calls);
}
    ;

constantExpression returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter()]
    : ce=conditionalExpression
{
    $variablesNameUsed.incrementAll($ce.variablesNameUsed);
    $calls.incrementAll($ce.calls);
}
    ;


/************************************ DECLARATIONS ************************************/

declaration returns [List<String> variableNames = new ArrayList<String>(),
        MultiCounter<String> variablesNameUsed = new MultiCounter<String>(), 
        CallCounter calls = new CallCounter(), String name = null,
		Type type = null, boolean isStatic, boolean isExtern, 
		boolean isFunction, boolean isDeclarationType, 
		boolean isAnonymousTypeDeclaration]
    : ds=declarationSpecifiers initDeclaratorList? ';'  
{    
	$name = $ds.name;
	$type = $ds.type;
	$type = $ds.type;
	$isStatic = $ds.isStatic;
	$isExtern = $ds.isExtern;
	$isDeclarationType = $ds.isDeclarationType;
	$isAnonymousTypeDeclaration = $ds.isAnonymousTypeDeclaration;
	          
    if($ds.isTypedef)
    {
        if($variableNames.isEmpty())
        {
            this.addComplexType($ds.name);
        }
        else
        {
            this.addComplexType($variableNames.get(0));
        }
    }
    
    if($variableNames.isEmpty())
    {
        $variableNames.add($ds.name);
    }
}
    | staticAssertDeclaration
    ;

declarationSpecifiers 
    returns [Type type, String name = null, boolean isStatic = false,
        boolean isExtern = false, boolean isTypedef = false, 
        boolean isDeclarationType = false, boolean isAnonymousTypeDeclaration =
        false]
    locals [DeclarationSpecifier specifier = new NullSpecifier()]
    : declarationSpecifier+
{
    if(!$isTypedef)
    {
        $type = $specifier.getType();
    }
}
    ;

declarationSpecifier
    : storageClassSpecifier
    | ts=typeSpecifier
{
    if($declarationSpecifiers::isTypedef || $ts.name == null || 
    		$declarationSpecifiers::specifier.isNull())
    {
	    $declarationSpecifiers::specifier = 
	            $declarationSpecifiers::specifier.merge($ts.specifier);
    }
    
    if($ts.isDeclarationType)
    {
        $declarationSpecifiers::isDeclarationType = $ts.isDeclarationType;
    }
    
    if($ts.isAnonymousTypeDeclaration)
    {
        $declarationSpecifiers::isAnonymousTypeDeclaration =
        		$ts.isAnonymousTypeDeclaration;
    }
    
    if($ts.name != null)
    {
        $declarationSpecifiers::name = $ts.name;
    }
}
    | typeQualifier
    | functionSpecifier
    | alignmentSpecifier
    ;

initDeclaratorList 
    : initDeclarator
    | initDeclaratorList ',' initDeclarator
    ;

initDeclarator
    : d=declarator
{
    $declaration::variableNames.add($d.name);
    $declaration::isFunction = $d.isFunction;
}
    | d=declarator '=' i=initializer
{
    $declaration::variableNames.add($d.name);
    $declaration::variablesNameUsed.incrementAll($i.variablesNameUsed);
    $declaration::calls.incrementAll($i.calls);
}
    ;

storageClassSpecifier
    : 'typedef'
{
    $declarationSpecifiers::isTypedef = true;
    $declarationSpecifiers::isDeclarationType = true;
}
    | 'extern'
{
    $declarationSpecifiers::isExtern = true;
}
    | 'static'
{
    $declarationSpecifiers::isStatic = true;
}
    | '_Thread_local'
    | 'auto'
    | 'register'
    ;

typeSpecifier returns [DeclarationSpecifier specifier = new NullSpecifier(),
		String name = null, boolean isDeclarationType = false,
		boolean isAnonymousTypeDeclaration = false]
    : ('void' 
{
    $specifier = new VoidSpecifier();
}
    | 'char' 
{
    $specifier = new CharSpecifier();
}
    | 'short' 
{
    $specifier = new ShortSpecifier();
}
    | 'int'
{
    $specifier = new IntSpecifier();
}
    | 'long'
{
    $specifier = new LongSpecifier();
}
    | 'float'
{
    $specifier = new FloatSpecifier();
}
    | 'double'
{
    $specifier = new DoubleSpecifier();
}
    | 'signed'
{
    $specifier = new SignedSpecifier();
}
    | 'unsigned'
{
    $specifier = new UnsignedSpecifier();
}
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
    | '__typeof__' '(' constantExpression ')'
    ;

structOrUnionSpecifier
    : structOrUnion i=Identifier? '{' structDeclarationList '}' 
{
    this.addComplexType($i.text);
    $typeSpecifier::isDeclarationType = true;
    
    if($i.text == null)
    {
    	$typeSpecifier::isAnonymousTypeDeclaration = true;
    }
    
    $typeSpecifier::name = $i.text;
    $typeSpecifier::specifier = 
            new StructOrUnionSpecifier($i.text, this.complexTypes, this.otherComplexTypes);
}
    | structOrUnion i=Identifier
{
    $typeSpecifier::specifier = 
            new StructOrUnionSpecifier($i.text, this.complexTypes, this.otherComplexTypes);
}
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
    : 'enum' i=Identifier? '{' enumeratorList '}'
{
    this.addComplexType($i.text);
    $typeSpecifier::isDeclarationType = true;
    
    if($i.text == null)
    {
    	$typeSpecifier::isAnonymousTypeDeclaration = true;
    }
    
    $typeSpecifier::name = $i.text;
    $typeSpecifier::specifier = new EnumSpecifier($i.text, this.complexTypes,
    		this.otherComplexTypes);
}
    | 'enum' i=Identifier? '{' enumeratorList ',' '}'
{
    this.addComplexType($i.text);
    $typeSpecifier::isDeclarationType = true;
    
    if($i.text == null)
    {
    	$typeSpecifier::isAnonymousTypeDeclaration = true;
    }
    
    $typeSpecifier::name = $i.text;
    $typeSpecifier::specifier = new EnumSpecifier($i.text, this.complexTypes,
    		this.otherComplexTypes);
}
    | 'enum' i=Identifier
{
    $typeSpecifier::specifier = new EnumSpecifier($i.text, this.complexTypes,
    		this.otherComplexTypes);
}
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
    | '__inline__'
    | '__stdcall')
    | gccAttributeSpecifier
    | '__declspec' '(' Identifier ')'
    ;

alignmentSpecifier
    : '_Alignas' '(' typeName ')'
    | '_Alignas' '(' constantExpression ')'
    ;

declarator returns [String name, Set<LocalVariable> arguments, 
          boolean isFunction]
    : pointer? dd=directDeclarator gccDeclaratorExtension*
{
    $name = $dd.name;
    $arguments = $dd.arguments;
    $isFunction = $dd.isFunction;
}
    ;

directDeclarator returns [String name, Set<LocalVariable> arguments = 
        new HashSet<LocalVariable>(), boolean isFunction = false, 
        Set<String> names = new HashSet<String>()]
    : i=Identifier
{
    $name = $i.text;
}
    | '(' d=declarator ')'
{
    $name = $d.name;
    $isFunction = true;
}
    | dd=directDeclarator '[' typeQualifierList? assignmentExpression? ']'
{
    $name = $dd.name;
}
    | dd=directDeclarator '[' 'static' typeQualifierList? assignmentExpression ']'
{
    $name = $dd.name;
}
    | dd=directDeclarator '[' typeQualifierList 'static' assignmentExpression ']'
{
    $name = $dd.name;
}
    | dd=directDeclarator '[' typeQualifierList? '*' ']' 
{
    $name = $dd.name;
}
    | dd=directDeclarator '(' ptl=parameterTypeList ')' 
{
    $name = $dd.name;
    $arguments.addAll($ptl.arguments);
    $isFunction = true;
}
    | dd=directDeclarator '(' il=identifierList? ')' 
{
    $name = $dd.name;
    
    if($il.text != null)
    {
        $names.addAll($il.names);
    }
    
    $isFunction = true;
}
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

parameterTypeList returns [Set<LocalVariable> arguments = 
		new HashSet<LocalVariable>()]
    : parameterList
    | parameterList ',' '...'
    ;

parameterList
    : parameterDeclaration
    | parameterList ',' parameterDeclaration
    ;

parameterDeclaration
    : ds=declarationSpecifiers d=declarator
{
    $parameterTypeList::arguments.add(new LocalVariable($d.name, $ds.type));
}
    | ds=declarationSpecifiers abstractDeclarator?
    ;

identifierList returns [Set<String> names = new HashSet<String>()]
    : i=Identifier
{
    $names.add($i.text);
}
    | il=identifierList ',' i=Identifier
{
    $names.addAll($il.names); 
    $names.add($i.text);
}
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
    : i=Identifier
{
    $typeSpecifier::specifier = 
            new TypedefNameSpecifier($i.text, this.complexTypes,
            		this.otherComplexTypes);
    $typeSpecifier::name = $i.text;
}
    ;

initializer returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter()]
    : ae=assignmentExpression
{
    $variablesNameUsed.incrementAll($ae.variablesNameUsed);
    $calls.incrementAll($ae.calls);
}
    | '{' il=initializerList '}'
{
    $variablesNameUsed.incrementAll($il.variablesNameUsed);
    $calls.incrementAll($il.calls);    
}
    | '{' il=initializerList ',' '}'
{
    $variablesNameUsed.incrementAll($il.variablesNameUsed);
    $calls.incrementAll($il.calls);   
}
    ;

initializerList returns [MultiCounter<String> variablesNameUsed = 
        new MultiCounter<String>(), CallCounter calls = new CallCounter()]
    : designation? i=initializer
{
    $variablesNameUsed.incrementAll($i.variablesNameUsed);
    $calls.incrementAll($i.calls);
}
    | il=initializerList ',' designation? i=initializer
{
    $variablesNameUsed.incrementAll($il.variablesNameUsed);
    $calls.incrementAll($il.calls);
    $variablesNameUsed.incrementAll($i.variablesNameUsed);
    $calls.incrementAll($i.calls);    
}
    ;

designation
    : designatorList '='
    ;

designatorList
    : designator
    | designatorList designator
    ;

designator
    : '[' ce=constantExpression ']'
{
    $initializerList::variablesNameUsed.incrementAll($ce.variablesNameUsed);
    $initializerList::calls.incrementAll($ce.calls);
}
    | '.' Identifier
    ;

staticAssertDeclaration
    : '_Static_assert' '(' constantExpression ',' StringLiteral+ ')' ';'
    ;


/************************************ Statements ************************************/

statement returns [MultiCounter<String> variablesNameUsed = 
		new MultiCounter<String>(), CallCounter calls = new CallCounter(),
		Set<Block> blocks = new HashSet<Block>()]
		locals[Map<String, LocalVariable> parentLocals = 
        new HashMap<String, LocalVariable>()]
@init
{
    if($compoundStatement::parentLocals != null)
    {
        $parentLocals.putAll($compoundStatement::parentLocals);
        $parentLocals.putAll($compoundStatement::locals);
    }
}
    : ls=labeledStatement
{
	$variablesNameUsed.incrementAll($ls.variablesNameUsed);
	$calls.incrementAll($ls.calls);
	$blocks.addAll($ls.blocks);
}
    | cs=compoundStatement[$parentLocals]
{
    $blocks.add($cs.block);
}
    | es=expressionStatement
{
    $variablesNameUsed.incrementAll($es.variablesNameUsed);
    $calls.incrementAll($es.calls);
    $blocks.addAll($es.blocks);
}
    | ss=selectionStatement
{
    $variablesNameUsed.incrementAll($ss.variablesNameUsed);
    $calls.incrementAll($ss.calls);
    $blocks.addAll($ss.blocks);
}
    | is=iterationStatement
{
    $variablesNameUsed.incrementAll($is.variablesNameUsed);
    $calls.incrementAll($is.calls);
    $blocks.addAll($is.blocks);
}
    | js=jumpStatement
{
    $variablesNameUsed.incrementAll($js.variablesNameUsed);
    $calls.incrementAll($js.calls);
    $blocks.addAll($js.blocks);
}
    | ('__asm' | '__asm__') ('volatile' | '__volatile__') '(' (loe1=logicalOrExpression (',' loe2=logicalOrExpression)*)? (':' (loe3=logicalOrExpression (',' loe4=logicalOrExpression)*)?)* ')' ';'
{
    // TODO	
}
    ;

labeledStatement returns [MultiCounter<String> variablesNameUsed = 
		new MultiCounter<String>(), CallCounter calls = new CallCounter(),
		Set<Block> blocks = new HashSet<Block>()]
    : Identifier ':' statement
    | 'case' constantExpression ':' s=statement
{
	$variablesNameUsed.incrementAll($s.variablesNameUsed);
    $calls.incrementAll($s.calls);
    $blocks.addAll($s.blocks);
}
    | 'default' ':' s=statement
{
    $variablesNameUsed.incrementAll($s.variablesNameUsed);
    $calls.incrementAll($s.calls);
    $blocks.addAll($s.blocks);
}
    ;

compoundStatement[Map<String, LocalVariable> parentLocals]
        returns [Block block]
        locals [Set<Call> calls = new HashSet<Call>(), 
        Map<String, LocalVariable> locals = 
        new HashMap<String, LocalVariable>(),
        MultiCounter<LocalVariable> localsUse = 
        new MultiCounter<LocalVariable>(),
        MultiCounter<GlobalVariable> globalsUse = 
        new MultiCounter<GlobalVariable>(), 
        Set<Block> subBlocks = new HashSet<Block>()]
@init
{
    $locals.putAll($parentLocals);
}

@after
{
    $block = new Block($calls, $globalsUse.getCounters(), 
            $localsUse.getCounters(), $subBlocks);    
}
    : '{' blockItemList? '}'
    ;

blockItemList
    : blockItem
    | blockItemList blockItem
    ;

blockItem
    : d=declaration
{
	// Function call with one argument
	if($d.isFunction)
	{
	    if($d.name != null)
	    {
    		Function f = null;
    		
    		if(this.functions.containsKey($d.name))
        	{
               f = this.functions.get($d.name);
        	}
    		else if(this.otherFunctions.containsKey($d.name))
        	{
    			f = this.otherFunctions.get($d.name);
    	    }
    		else
    		{
    			f = new Function($d.name, ComplexType.anonymousType);
        	    this.otherFunctions.put($d.name, f);
    		}
                
            Set<Variable> parameters = new HashSet<Variable>();
            Variable v = null;
             
            if(this.globalVariables.containsKey($d.variableNames.get(0)))
            {
            	GlobalVariable g = this.globalVariables.get(
            			$d.variableNames.get(0));
                $compoundStatement::globalsUse.increment(g);
                v = g;
            }
            else if($compoundStatement::locals.containsKey($d.variableNames.get(0)))
            {
            	LocalVariable l = $compoundStatement::locals.get(
            			$d.variableNames.get(0));
                $compoundStatement::localsUse.increment(l);
                v = l;
            }
                
            if(v != null)
            {
            	parameters.add(v);
            }
                
            $compoundStatement::calls.add(new Call(f, parameters));
	    }
	}
	// Variable declaration
	else if(!$d.isDeclarationType || $d.isAnonymousTypeDeclaration)
    {
        for(String variableName : $d.variableNames)
        {   
            LocalVariable variable = new LocalVariable(variableName, $d.type);  
            $compoundStatement::locals.put(variable.getName(), variable);
        }
        
        // Update use of variables.
        for(Entry<String, Integer> counter : 
                $d.variablesNameUsed.getCounters().entrySet())
        {
            if(this.globalVariables.containsKey(counter.getKey()))
            {
                GlobalVariable v = this.globalVariables.get(counter.getKey());
                $compoundStatement::globalsUse.increment(v, counter.getValue());
            }
            else if($compoundStatement::locals.containsKey(counter.getKey()))
            {
                LocalVariable v = 
                		$compoundStatement::locals.get(counter.getKey());
                $compoundStatement::localsUse.increment(v, counter.getValue());
            }
        }
        
        // Update calls.
        for(Entry<String, Set<Set<String>>> function : 
            $d.calls.getCalls().entrySet())
        {
            if(function.getKey() != null)
            {  
                Function f = null;
                
            	if(this.functions.containsKey(function.getKey()))
            	{
    	            f = this.functions.get(function.getKey());
            	}
            	else if(this.otherFunctions.containsKey(function.getKey()))
            	{
    	            f = this.otherFunctions.get(function.getKey());
            	}
            	else
            	{
            	    f = new Function(function.getKey(), ComplexType.anonymousType);
            	    this.otherFunctions.put(function.getKey(), f);
            	}
    	            
    	        for(Set<String> functionCall : function.getValue())
    	        {
    	            Set<Variable> parameters = new HashSet<Variable>();
    	                
    	            for(String parameter : functionCall)
    	            {
    	                Variable v = null;
    	                    
    	                if(this.globalVariables.containsKey(parameter))
    	                {
    	                    v = this.globalVariables.get(parameter);
    	                }
    	                else if($compoundStatement::locals.containsKey(parameter))
    	                {
    	                    v = $compoundStatement::locals.get(parameter);
    	                }
    	                    
    	                if(v != null)
    	                {
    	                    parameters.add(v);
    	                }
    	            }
    	                
    	            $compoundStatement::calls.add(new Call(f, parameters));
            	}
            }
        }
    }
}
    | s=statement
{
    // Update use of variables.
    for(Entry<String, Integer> counter : 
    		$s.variablesNameUsed.getCounters().entrySet())
    {
        if(this.globalVariables.containsKey(counter.getKey()))
        {
        	GlobalVariable v = this.globalVariables.get(counter.getKey());
            $compoundStatement::globalsUse.increment(v, counter.getValue());
        }
        else if($compoundStatement::locals.containsKey(counter.getKey()))
        {
            LocalVariable v = $compoundStatement::locals.get(counter.getKey());
            $compoundStatement::localsUse.increment(v, counter.getValue());
        }
    }
        
    // Update calls.
    for(Entry<String, Set<Set<String>>> function : 
    		$s.calls.getCalls().entrySet())
    {
        Function f = null;
        
        if(this.functions.containsKey(function.getKey()))
        {
            f = this.functions.get(function.getKey());
        }
        else if(this.otherFunctions.containsKey(function.getKey()))
        {
            f = this.otherFunctions.get(function.getKey());
        }
        else
        {
            f = new Function(function.getKey(), ComplexType.anonymousType);
            this.otherFunctions.put(function.getKey(), f);
        }
	            
	    for(Set<String> functionCall : function.getValue())
	    {
	        Set<Variable> parameters = new HashSet<Variable>();
	                
	        for(String parameter : functionCall)
	        {
	            Variable v = null;
	                    
	            if(this.globalVariables.containsKey(parameter))
	            {
	                v = this.globalVariables.get(parameter);
	            }
	            else if($compoundStatement::locals.containsKey(parameter))
	            {
	                v = $compoundStatement::locals.get(parameter);
	            }
	                    
	            if(v != null)
	            {
	                parameters.add(v);
	            }
	        }
	                
	        $compoundStatement::calls.add(new Call(f, parameters));
    	}
    }
    
    $compoundStatement::subBlocks.addAll($s.blocks);
}
    ;

expressionStatement returns [MultiCounter<String> variablesNameUsed = 
		new MultiCounter<String>(), CallCounter calls = new CallCounter(),
		Set<Block> blocks = new HashSet<Block>()]
    : e=expression? ';'
{
	if($e.text != null)
	{
		$variablesNameUsed.incrementAll($e.variablesNameUsed);
		$calls.incrementAll($e.calls);
	}
}
    ;

selectionStatement returns [MultiCounter<String> variablesNameUsed = 
		new MultiCounter<String>(), CallCounter calls = new CallCounter(),
		Set<Block> blocks = new HashSet<Block>()]
    : 'if' '(' e=expression ')' s1=statement ('else' s2=statement)?
{
    $variablesNameUsed.incrementAll($e.variablesNameUsed);
    $calls.incrementAll($e.calls);
    $variablesNameUsed.incrementAll($s1.variablesNameUsed);
    $calls.incrementAll($s1.calls);
    $blocks.addAll($s1.blocks);
    
    if($s2.text != null)
	{
    	$variablesNameUsed.incrementAll($s2.variablesNameUsed);
        $calls.incrementAll($s2.calls);
        $blocks.addAll($s2.blocks);
	}
 	
}
    | 'switch' '(' e=expression ')' s=statement
{
    $variablesNameUsed.incrementAll($e.variablesNameUsed);
    $calls.incrementAll($e.calls);
    $variablesNameUsed.incrementAll($s.variablesNameUsed);
    $calls.incrementAll($s.calls);
    $blocks.addAll($s.blocks);	
}
    ;

iterationStatement returns [MultiCounter<String> variablesNameUsed = 
		new MultiCounter<String>(), CallCounter calls = new CallCounter(),
		Set<Block> blocks = new HashSet<Block>()]
    : 'while' '(' e=expression ')' s=statement
{
	$variablesNameUsed.incrementAll($e.variablesNameUsed);
    $calls.incrementAll($e.calls);
    $variablesNameUsed.incrementAll($s.variablesNameUsed);
    $calls.incrementAll($s.calls);
    $blocks.addAll($s.blocks);
}
    | 'do' s=statement 'while' '(' e=expression ')' ';'
{
    $variablesNameUsed.incrementAll($s.variablesNameUsed);
    $calls.incrementAll($s.calls);
    $blocks.addAll($s.blocks);
    $variablesNameUsed.incrementAll($e.variablesNameUsed);
    $calls.incrementAll($e.calls);
}
    | 'for' '(' e1=expression? ';' e2=expression? ';' e3=expression? ')' s=statement
{
    if($e1.text != null)
    {
        $variablesNameUsed.incrementAll($e1.variablesNameUsed);
        $calls.incrementAll($e1.calls);
    }
    
    if($e2.text != null)
    {
        $variablesNameUsed.incrementAll($e2.variablesNameUsed);
        $calls.incrementAll($e2.calls);
    }
    
    if($e3.text != null)
    {
        $variablesNameUsed.incrementAll($e3.variablesNameUsed);
        $calls.incrementAll($e3.calls);
    }
    	
    $variablesNameUsed.incrementAll($s.variablesNameUsed);
    $calls.incrementAll($s.calls);
    $blocks.addAll($s.blocks);
}
    | 'for' '(' d=declaration e1=expression? ';' e2=expression? ')' s=statement
{
    if($e1.text != null)
    {
        $variablesNameUsed.incrementAll($e1.variablesNameUsed);
        $calls.incrementAll($e1.calls);
    }
        
    if($e2.text != null)
    {
        $variablesNameUsed.incrementAll($e2.variablesNameUsed);
        $calls.incrementAll($e2.calls);
    }
        	
    $variablesNameUsed.incrementAll($s.variablesNameUsed);
    $calls.incrementAll($s.calls);
    $blocks.addAll($s.blocks);	
}
    ;

jumpStatement returns [MultiCounter<String> variablesNameUsed = 
		new MultiCounter<String>(), CallCounter calls = new CallCounter(),
		Set<Block> blocks = new HashSet<Block>()]
    : 'goto' Identifier ';'
    | 'continue' ';'
    | 'break' ';'
    | 'return' e=expression? ';'
{
    if($e.text != null)
    {
        $variablesNameUsed.incrementAll($e.variablesNameUsed);
        $calls.incrementAll($e.calls);
    }	
}
    | 'goto' ue=unaryExpression ';'
{
    $variablesNameUsed.incrementAll($ue.variablesNameUsed);
    $calls.incrementAll($ue.calls);	
}
    ;


/************************************ External definitions ************************************/

compilationUnit
    : translationUnit? EOF
{
}
    ;

translationUnit
    : externalDeclaration
    | translationUnit externalDeclaration
    ;

externalDeclaration
    : fd=functionDefinition
{
    // We already know the prototype of the function
    if(this.otherFunctions.containsKey($fd.result.getName()))
    {
    	Function prototype = this.otherFunctions.get($fd.result.getName());
    	prototype.update($fd.result);
    	this.otherFunctions.remove($fd.result.getName());
    	this.functions.put(prototype.getName(), prototype);
    }
    else
    {
    	this.functions.put($fd.result.getName(), $fd.result);
    }
}
    | d=declaration 
{
    // Function declaration
    if($d.isFunction)
	{
		Function declaredFunction = new Function($d.variableNames.get(0),
		        $d.type, $d.isStatic);
		this.otherFunctions.put(declaredFunction.getName(), declaredFunction);
	}
    // Global variable(s) declaration
	else if(!$d.isDeclarationType || $d.isAnonymousTypeDeclaration)
	{    
		for(String variableName : $d.variableNames)
		{	
		    GlobalVariable variable = null;
		    
		    if(this.globalVariables.containsKey(variableName))
		    {
		        variable = this.globalVariables.get(variableName);
		        
		        if(!$d.isExtern)
		        {
		            variable.setSourceFile(this.currentFile);
		        }
		    }
		    else
		    {    		    
    		    if($d.isExtern)
    		    {
    		        variable = new GlobalVariable(variableName, $d.type,
    	                    $d.isStatic);
    		    }
    		    else
    		    {
    		        variable = new GlobalVariable(variableName, $d.type,
    					$d.isStatic, this.currentFile);
    		    }
    		    
    		    this.globalVariables.put(variable.getName(), variable);
		    }
		}
	}
}
    | ';'
    | 'extern' '"C"' '{' translationUnit '}' ';'
    ;

functionDefinition returns [Function result] 
        locals [Map<String, LocalVariable> arguments = 
        new HashMap<String, LocalVariable>()]
    : ds=declarationSpecifiers? d=declarator dl=declarationList? cs=compoundStatement[$arguments]
{
    Type returnType = $ds.type == null ? PrimitiveType.voidType : $ds.type;
    Set<LocalVariable> arguments = 
            $dl.text == null ? $d.arguments : $dl.arguments;
    
    if(arguments == null)
    {
        System.out.println("KO : " + $d.name);
        
    }
    
    for(LocalVariable argument : arguments)
    {
        $arguments.put(argument.getName(), argument);
    }
    
    $result = new Function($d.name, returnType, $ds.isStatic, arguments, $cs.block,
            this.currentFile);
} 
    ;

declarationList returns [Set<LocalVariable> arguments = 
        new HashSet<LocalVariable>()]
    : d=declaration
{
    for(String argumentName : $d.variableNames)
    {   
        LocalVariable argument = new LocalVariable(argumentName, $d.type);
        $arguments.add(argument);
    }    
}
    | dl=declarationList d=declaration
{
    $arguments.addAll($dl.arguments);
    
    for(String argumentName : $d.variableNames)
    {   
        LocalVariable argument = new LocalVariable(argumentName, $d.type);
        $arguments.add(argument);
    }
}
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
          -> skip
    ;             
        
fragment
BlockComment
    : '/*' .*? '*/'
    ;
fragment
LineComment
    : '//' ~[\r\n]*
    ;
 
PreprocessingDirective
    : '#' ~[\r\n/]*
        -> skip
    ;