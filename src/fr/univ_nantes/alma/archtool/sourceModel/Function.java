package fr.univ_nantes.alma.archtool.sourceModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fr.univ_nantes.alma.archtool.utils.MultiCounter;

/**
 * Classe du modèle de code source représentant une fonction.
 * 
 * <p>
 * Une fonction est composée de
 * <ul>
 * <li>son nom,</li>
 * <li>son type de retour,</li>
 * <li>l'ensemble de ses arguments,</li>
 * <li>le fichier dans lequel elle est définie,
 * <li>son corps (bloc de code)</li>
 * </ul>
 * </p>
 */
public class Function
{
    private final String name;

    private final boolean isStatic;

    private Set<LocalVariable> arguments = new HashSet<LocalVariable>();

    private final Type returnType;

    private File sourceFile = null;

    private Block body = null;
    
    private SourceCode sourceCode = null;
    
    // Cached data
    private Map<Function, Integer> totalCalledFunctions = null;
    
 // Cached data
    private Map<Function, Integer> coreCalledFunctions = null;
    
    // Cached data
    private Set<Call> calls = null;
    
    // Cached data
    private Map<LocalVariable, Integer> localVariablesUse = null;
    
    // Cached data
    private Map<GlobalVariable, Integer> globalVariablesUse = null;
    
    // Cached data
    private Map<ComplexType, Integer> totalComplexTypeUse = null;
    
 // Cached data
    private Map<ComplexType, Integer> coreComplexTypeUse = null;
    
    // Cached data
    private Map<Function, Integer> callingFunctions = null;

    /**
     * Constructeur pour les fonctions dont on ne connait pas la définition.
     * 
     * @param name
     * @param sourceFile
     */
    public Function(final String name, final Type returnType)
    {
        this(name, returnType, false);
    }

    public Function(final String name, Type returnType, boolean isStatic)
    {
        this.name = name;
        this.returnType = returnType;
        this.isStatic = isStatic;
        this.body = new Block();
    }

    public Function(final String name, final Type returnType, boolean isStatic,
            final Set<LocalVariable> arguments, final Block body,
            final File sourceFile)
    {
        this(name, returnType, isStatic);
        this.arguments = arguments;
        this.body = body;
        this.sourceFile = sourceFile;
    }

    /**
     * Retourne le nom de la fonction.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Retourne la liste des arguments de la fonction.
     */
    public Set<LocalVariable> getArguments()
    {
        return new HashSet<LocalVariable>(this.arguments);
    }

    /**
     * Retourne le type de retour de la fonction.
     */
    public Type getReturnType()
    {
        return this.returnType;
    }

    public boolean isStatic()
    {
        return this.isStatic;
    }
    
    public void setSourceCode(SourceCode sourceCode)
    {
        this.sourceCode = sourceCode;
    }

    /**
     * Retourne le fichier source dans lequel est définie la fonction.
     */
    public File getSourceFile()
    {
        return this.sourceFile;
    }

    public void update(Function definitionFunction)
    {
        this.arguments = definitionFunction.arguments;
        this.body = definitionFunction.body;
        this.sourceFile = definitionFunction.sourceFile;
    }

    /**
     * Renvoie l'ensemble des variables globales utilisées par la fonction.
     * 
     * @return Une map ayant pour clés les variables globales accédées dans le
     *         corps de la fonction et pour valeurs leur nombre d'accès.
     */
    public Map<GlobalVariable, Integer> getGlobalVariables()
    {
        if(this.globalVariablesUse == null)
        {
            this.globalVariablesUse = this.body.getGlobalVariables();
        }
        
        return new HashMap<GlobalVariable, Integer>(this.globalVariablesUse);
    }

    /**
     * Renvoie l'ensemble des variables globales au programme utilisées par la
     * fonction.
     * 
     * @return Une map ayant pour clés les variables globales au programme
     *         accédées dans le corps de la fonction et pour valeurs leur nombre
     *         d'accès.
     */
    public Map<GlobalVariable, Integer> getProgramGlobals()
    {
        return this.body.getProgramGlobals();
    }

    /**
     * Renvoie l'ensemble des variables globales au fichier utilisées par la
     * fonction.
     * 
     * @return Une map ayant pour clés les variables globales au fichier
     *         accédées dans le corps de la fonction et pour valeurs leur nombre
     *         d'accès.
     */
    public Map<GlobalVariable, Integer> getFileGlobals()
    {
        return this.body.getFileGlobals();
    }

    /**
     * Renvoie l'ensemble des variables locales de la fonction.
     * 
     * <p>
     * Les arguments ne sont pas considérés comme des variables locales.
     * </p>
     * 
     * @return Une map ayant pour clés les variables locales utilisées dans le
     *         corps de la fonction et pour valeurs leur nombre d'utilisations.
     */
    public Map<LocalVariable, Integer> getLocalVariables()
    {        
        if(this.localVariablesUse == null)
        {
            this.localVariablesUse = this.body.getLocalVariables();
        }
        
        return new HashMap<LocalVariable, Integer>(this.localVariablesUse);
    }

    /**
     * Renvoie l'ensemble des appels réalisés dans la fonction.
     * 
     * @return Un set contenant les appels réalisés dans le corps de la
     *         fonction.
     */
    public Set<Call> getCalls()
    {
        if(this.calls == null)
        {
            this.calls = this.body.getCalls();
        }
        
        return new HashSet<Call>(this.calls);
    }

    public boolean calls(final Function fct)
    {
        return this.getTotalCalledFunctions().containsKey(fct);
    }

    /**
     * Renvoie l'ensemble des types complexes utilisés par la fonction.
     * 
     * <p>
     Sont pris en compte les types des variables utilisées, le type de retour,
      et les types des arguments.
     * </p>
     * 
     * @return Un set contenant les types utilisés dans le corps de la fonction.
     */
    public Map<ComplexType, Integer> getTotalComplexTypes()
    {
        if(this.totalComplexTypeUse == null)
        {
            MultiCounter<ComplexType> complextypesUse =
                    new MultiCounter<ComplexType>();
            complextypesUse.incrementAll(this.body.getComplexTypes());
            
            for (LocalVariable argument : this.arguments)
            { 
                Type type =  argument.getType();
    
                if(type.isComplex && type != ComplexType.anonymousType)
                {
                    complextypesUse.increment((ComplexType) type);
                }
            }
    
            this.totalComplexTypeUse = complextypesUse.getCounters();
        }
        
        return new HashMap<ComplexType, Integer>(this.totalComplexTypeUse);
    }
    
    public Map<ComplexType, Integer> getCoreComplexTypes()
    {
        if(this.sourceCode != null && (this.coreComplexTypeUse == null ||
                this.sourceCode.hasChanged()))
        {            
            this.coreComplexTypeUse = new HashMap<ComplexType, Integer>();
                
            for(Entry<ComplexType, Integer> typeCounter : 
                this.getTotalComplexTypes().entrySet())
            {
                if(this.sourceCode.getCoreComplexTypes().contains(typeCounter.getKey()))
                {
                    this.coreComplexTypeUse.put(typeCounter.getKey(), 
                            typeCounter.getValue());
                }
            }
        }
            
        return this.coreComplexTypeUse == null ?
                new HashMap<ComplexType, Integer>() : 
                new HashMap<ComplexType, Integer>(this.coreComplexTypeUse);
    }
    
    public Map<Function, Integer> getTotalCalledFunctions()
    {
        if(this.totalCalledFunctions == null)
        {
            MultiCounter<Function> functionCounter = 
                    new MultiCounter<Function>();
            
            for(Call call : this.getCalls())
            {
                functionCounter.increment(call.getFunction());
            }
            
            this.totalCalledFunctions = functionCounter.getCounters();
        }
        
        return new HashMap<Function, Integer>(this.totalCalledFunctions);
    }
    
    public Map<Function, Integer> getCoreCalledFunctions()
    {        
        if(this.sourceCode != null && (this.coreCalledFunctions == null ||
                this.sourceCode.hasChanged()))
        {            
            this.coreCalledFunctions = new HashMap<Function, Integer>();
            
            for(Entry<Function, Integer> functionCounter : 
                this.getTotalCalledFunctions().entrySet())
            {
                if(this.sourceCode.getCoreFunctions().
                        contains(functionCounter.getKey()))
                {
                    this.coreCalledFunctions.put(functionCounter.getKey(), 
                            functionCounter.getValue());
                }
            }
        }
        
        return this.coreCalledFunctions == null ?
                new HashMap<Function, Integer>() : 
                new HashMap<Function, Integer>(this.coreCalledFunctions);
    }
    
    public Map<Function, Integer> getCallingFunctions()
    {
        if(this.sourceCode != null && (this.callingFunctions == null ||
                this.sourceCode.hasChanged()))
        {            
            this.callingFunctions = new HashMap<Function, Integer>();
            
            for(Function function : this.sourceCode.getCoreFunctions())
            {
                Map<Function, Integer> calledFunctions = 
                        function.getCoreCalledFunctions();
                
                if(calledFunctions.containsKey(this))
                {
                    this.callingFunctions.put(function,
                            calledFunctions.get(this));
                }
            }
        }
        
        return this.callingFunctions == null ?
                new HashMap<Function, Integer>() : 
                new HashMap<Function, Integer>(this.callingFunctions);
    }   

    @Override
    public String toString()
    {
        return this.name;
        
        /*StringBuffer function = new StringBuffer(this.returnType.getName()
                + " ");

        function.append(this.name);

        if (this.isStatic)
        {
            function.append("static ");
        }

        function.append("(");

        for (LocalVariable arg : this.arguments)
        {
            function.append(arg + ", ");
        }

        function.append(")");
        
        function.append("\n" + this.body.getCalls());
        
        return function.toString();*/
    }
}
