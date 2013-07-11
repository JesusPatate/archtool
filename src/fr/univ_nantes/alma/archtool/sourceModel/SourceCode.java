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
    
    private SourceCodeCoreMediator coreMediator = new SourceCodeCoreMediator();
    
    private SourceCodeTotalMediator totalMediator = new SourceCodeTotalMediator();

    
    public Set<Function> getFunctions()
    {
        return new HashSet<Function>(this.functions);
    }
    
    public Set<Function> getTotalFunctions()
    {
        Set<Function> total = new HashSet<Function>(this.functions);
        
        for(Function f : this.functions)
        {
            for(Call c : f.getCalls())
            {
                total.add(c.getFunction());
            }
        }
        
        return total;
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
  
    public void optimizeRelations()
    {
        this.coreMediator.createRelations(this.functions, this.types,
                this.globals);
        this.totalMediator.createRelations(this.functions, this.types,
                this.globals);
    }
    
    public Set<Function> getCoreFunctionsCalledBy(Function function)
    {
        return this.coreMediator.getFunctionsCalledBy(function);
    }
    
    public Set<Function> getCoreFunctionsCalling(Function function)
    {
        return this.coreMediator.getFunctionsCalling(function);
    }
    
    public Set<GlobalVariable> getCoreGlobalsUsedBy(Function function)
    {
        return this.coreMediator.getGlobalsUsedBy(function);
    }
    
    public Set<Function> getCoreFunctionUsing(GlobalVariable global)
    {
        return this.coreMediator.getFunctionUsing(global);
    }
    
    public Set<ComplexType> getCoreTypesUsedBy(Function function)
    {
        return this.coreMediator.getTypesUsedBy(function);
    }
    
    public Set<Function> getCoreFunctionUsing(ComplexType type)
    {
        return this.coreMediator.getFunctionUsing(type);
    }
    
    public Set<GlobalVariable> getCoreGlobalsUsing(ComplexType type)
    {
        return this.coreMediator.getGlobalsUsing(type);
    }
    
    public Set<Function> getTotalFunctionsCalledBy(Function function)
    {
        return this.totalMediator.getFunctionsCalledBy(function);
    }
    
    public Set<Function> getTotalFunctionsCalling(Function function)
    {
        return this.totalMediator.getFunctionsCalling(function);
    }
    
    public Set<GlobalVariable> getTotalGlobalsUsedBy(Function function)
    {
        return this.totalMediator.getGlobalsUsedBy(function);
    }
    
    public Set<Function> getTotalFunctionUsing(GlobalVariable global)
    {
        return this.totalMediator.getFunctionUsing(global);
    }
    
    public Set<ComplexType> getTotalTypesUsedBy(Function function)
    {
        return this.totalMediator.getTypesUsedBy(function);
    }
    
    public Set<Function> getTotalFunctionUsing(ComplexType type)
    {
        return this.totalMediator.getFunctionUsing(type);
    }
    
    public Set<GlobalVariable> getTotalGlobalsUsing(ComplexType type)
    {
        return this.totalMediator.getGlobalsUsing(type);
    }
}
