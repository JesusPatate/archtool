package fr.univ_nantes.alma.archtool.sourceModel;

import java.util.HashSet;
import java.util.Set;


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

    public Set<ComplexType> getTypes()
    {
        return new HashSet<ComplexType>(this.types);
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
