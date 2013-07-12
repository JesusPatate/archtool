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

    private final Set<GlobalVariable> globalVariables =
            new HashSet<GlobalVariable>();

    private final Set<ComplexType> coreComplexTypes = new HashSet<ComplexType>();

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
            for(Call c : f.getCalls())
            {
                total.add(c.getFunction());
            }
        }
        
        return total;
    }
    
    /*public Set<ComplexType> getTotalComplexTypes()
    {
        Set<ComplexType> total = new HashSet<ComplexType>();
        
        for(Function f : this.functions)
        {
            
        }
        
        return total;
    }*/
    
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

    public Set<GlobalVariable> getProgramGlobals()
    {
        Set<GlobalVariable> programGlobals = 
                new HashSet<GlobalVariable>();
        
        for(GlobalVariable gv : this.globalVariables)
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
        
        for(GlobalVariable gv : this.globalVariables)
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
        return new HashSet<GlobalVariable>(this.globalVariables);
    }

    public void addGlobalVariable(final GlobalVariable globalVariable)
    {
        if(!this.globalVariables.contains(globalVariable))
        {
            this.hasChanged = true;
        }
        
        this.globalVariables.add(globalVariable);
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
    
    public Set<Function> getCoreFunctionsCalledBy(Function function)
    {
        return function.getCoreCalledFunctions().keySet();
    }
    
    public Set<Function> getCoreFunctionsCalling(Function function)
    {
        return function.getCallingFunctions().keySet();
    }
    
    public Set<GlobalVariable> getCoreGlobalsUsedBy(Function function)
    {
        return function.getGlobalVariables().keySet();
    }
    
    public Set<Function> getCoreFunctionUsing(GlobalVariable global)
    {
        return global.getUsingFunctions().keySet();
    }
    
    public Set<ComplexType> getCoreTypesUsedBy(Function function)
    {
        return function.getCoreComplexTypes().keySet();
    }
    
    public Set<Function> getCoreFunctionUsing(ComplexType type)
    {
        return type.getCoreUsingFunctions().keySet();
    }
    
    public Set<GlobalVariable> getCoreGlobalsUsing(ComplexType type)
    {
        return type.getUsingGlobalVariables();
    }
    
    public Set<Function> getTotalFunctionsCalledBy(Function function)
    {
        return function.getTotalCalledFunctions().keySet();
    }
    
    public Set<Function> getTotalFunctionsCalling(Function function)
    {
        return function.getCallingFunctions().keySet();
    }
    
    public Set<GlobalVariable> getTotalGlobalsUsedBy(Function function)
    {
        return function.getGlobalVariables().keySet();
    }
    
    public Set<Function> getTotalFunctionUsing(GlobalVariable global)
    {
        return global.getUsingFunctions().keySet();
    }
    
    public Set<ComplexType> getTotalTypesUsedBy(Function function)
    {
        return function.getTotalComplexTypes().keySet();
    }
    
    public Set<Function> getTotalFunctionUsing(ComplexType type)
    {
        return type.getTotalUsingFunctions().keySet();
    }
    
    public Set<GlobalVariable> getTotalGlobalsUsing(ComplexType type)
    {
        return type.getUsingGlobalVariables();
    }
}
