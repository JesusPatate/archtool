package fr.univ_nantes.alma.archtool.sourceModel;

import java.util.HashSet;
import java.util.Set;


/**
 * Classe du modèle de code source regroupant l'ensemble des éléments d'un
 * programme.
 */
public class SourceCode
{    
    private final Set<Function> coreFunctions = new HashSet<Function>();

    private final Set<GlobalVariable> coreGlobalVariables =
            new HashSet<GlobalVariable>();

    private final Set<ComplexType> coreComplexTypes = 
            new HashSet<ComplexType>();

    private boolean hasChanged = false;
    
    public Set<Function> getCoreFunctions()
    {
        return new HashSet<Function>(this.coreFunctions);
    }
    
    public Set<Function> getTotalFunctions()
    {
        Set<Function> total = new HashSet<Function>(this.coreFunctions);
        
        for(Function f : this.coreFunctions)
        {
            for(Call c : f.getTotalCalls())
            {
                total.add(c.getFunction());
            }
        }
        
        return total;
    }
    
    public boolean hasChanged()
    {
        return this.hasChanged;
    }
    
    public void addFunction(final Function function)
    {
        if(!this.coreFunctions.contains(function))
        {
            this.hasChanged = true;
        }
           
        this.coreFunctions.add(function);
        function.setSourceCode(this);
    }

    public void addFunctions(final Set<Function> functions)
    {
        for(Function function : functions)
        {
            this.addFunction(function);
        }
    }
    
    public Set<GlobalVariable> getCoreGlobalVariables()
    {
        return new HashSet<GlobalVariable>(this.coreGlobalVariables);
    }

    public void addGlobalVariable(final GlobalVariable globalVariable)
    {
        if(!this.coreGlobalVariables.contains(globalVariable))
        {
            this.hasChanged = true;
        }
        
        this.coreGlobalVariables.add(globalVariable);
        globalVariable.setSourceCode(this);
    }

    public void addGlobalVariables(final Set<GlobalVariable> globalVariables)
    {
        for(GlobalVariable globalVariable : globalVariables)
        {
            this.addGlobalVariable(globalVariable);
        }
    }

    public Set<ComplexType> getCoreComplexTypes()
    {
        return new HashSet<ComplexType>(this.coreComplexTypes);
    }

    public void addComplexType(final ComplexType type)
    {
        if(!this.coreComplexTypes.contains(type))
        {
            this.hasChanged = true;
        }
        
        this.coreComplexTypes.add(type);
        type.setSourceCode(this);
    }

    public void addComplexTypes(final Set<ComplexType> types)
    {
        for(ComplexType type : types)
        {
            this.addComplexType(type);
        }
    }
}
