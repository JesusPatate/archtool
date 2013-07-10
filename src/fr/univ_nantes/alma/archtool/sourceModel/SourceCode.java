package fr.univ_nantes.alma.archtool.sourceModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fr.univ_nantes.alma.archtool.utils.Pair;

/**
 * Classe du modèle de code source regroupant l'ensemble des éléments d'un
 * programme.
 */
public class SourceCode
{
    private final Set<Function> functions = new HashSet<Function>();

    private final Set<GlobalVariable> globals =
            new HashSet<GlobalVariable>();

    private final Set<ComplexType> types = new HashSet<ComplexType>();
    
    // For mediator
    private Map<Function, Set<Pair<Function, Integer>>> functionsCalled =
            new HashMap<Function, Set<Pair<Function, Integer>>>();
    
    private Map<Function, Set<Pair<Function, Integer>>> functionsCalling = 
            new HashMap<Function, Set<Pair<Function, Integer>>>();
    
    private Map<Function, Set<Pair<ComplexType, Integer>>> typesUsedByFunction = 
            new HashMap<Function, Set<Pair<ComplexType, Integer>>>();
    
    private Map<ComplexType, Set<Pair<Function, Integer>>> typesUsingFunction = 
            new HashMap<ComplexType, Set<Pair<Function, Integer>>>();
    
    private Map<Function, Set<Pair<GlobalVariable, Integer>>> globalsUsed = 
            new HashMap<Function, Set<Pair<GlobalVariable, Integer>>>();
    
    private Map<GlobalVariable, Set<Pair<Function, Integer>>> globalsUsing = 
            new HashMap<GlobalVariable, Set<Pair<Function, Integer>>>();
    
    private Map<ComplexType, Set<Pair<GlobalVariable, Integer>>> typesUsingGlobals = 
            new HashMap<ComplexType, Set<Pair<GlobalVariable, Integer>>>();
    
    
    public void createRelations()
    {
        // Beetween functions and functions, types, variables
        for(Function f : this.functions)
        {
            for(Call call : f.getCalls())
            {
                
            }
            
            for(Entry<GlobalVariable, Integer> variableUse : 
                f.getFileGlobals().entrySet())
            {
                
            }
            
            for(Entry<Type, Integer> typeUse : f.getUsedTypes().entrySet())
            {
                
            }
        }
    }
    
    

    public Set<Function> getFunctions()
    {
        return new HashSet<Function>(this.functions);
    }

    public void addFunction(final Function function)
    {
        this.functions.add(function);
    }

    public void addFunctions(final Set<Function> functions)
    {
        this.functions.addAll(functions);
    }

    public Set<GlobalVariable> getProgramGlobals()
    {
        Set<GlobalVariable> programGlobals = 
                new HashSet<GlobalVariable>();
        
        for(GlobalVariable gv : this.globals)
        {
            if(!gv.isStatic())
            {
                programGlobals.add(gv);
            }
        }
        
        return programGlobals;
    }
    
    public Set<GlobalVariable> getFileGlobals()
    {
        Set<GlobalVariable> programGlobals = 
                new HashSet<GlobalVariable>();
        
        for(GlobalVariable gv : this.globals)
        {
            if(gv.isStatic())
            {
                programGlobals.add(gv);
            }
        }
        
        return programGlobals;
    }
    
    public Set<GlobalVariable> getGlobalVariables()
    {
        return new HashSet<GlobalVariable>(this.globals);
    }

    public void addGlobal(final GlobalVariable var)
    {
        this.globals.add(var);
    }

    public void addGlobals(final Set<GlobalVariable> vars)
    {
        this.globals.addAll(vars);
    }

    public Set<Type> getTypes()
    {
        return new HashSet<Type>(this.types);
    }

    public void addType(final ComplexType t1)
    {
        this.types.add(t1);
    }

    public void addTypes(final Set<ComplexType> types)
    {
        this.types.addAll(types);
    }

    public boolean isComplexType(final Type type)
    {
        return this.types.contains(type);
    }
}
