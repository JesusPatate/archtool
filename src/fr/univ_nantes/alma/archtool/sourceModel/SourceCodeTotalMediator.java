package fr.univ_nantes.alma.archtool.sourceModel;

import java.util.Set;
import java.util.Map.Entry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import fr.univ_nantes.alma.archtool.utils.MultiMultiCounter;

public class SourceCodeTotalMediator implements SourceCodeMediator
{    
    private MultiMultiCounter<Function, Function> functionsCalling = 
            new MultiMultiCounter<Function, Function>();
    
    private MultiMultiCounter<ComplexType, Function> typesUsingFunction = 
            new MultiMultiCounter<ComplexType, Function>();
    
    private MultiMultiCounter<ComplexType, GlobalVariable> typesUsingGlobals = 
            new MultiMultiCounter<ComplexType, GlobalVariable>();
    
    private MultiMultiCounter<GlobalVariable, Function> globalsUsingFunction = 
            new MultiMultiCounter<GlobalVariable, Function>();
    
    @Override
    public void createRelations(Set<Function> functions,
            Set<ComplexType> types, Set<GlobalVariable> globals)
    {
        functionsCalling.clear();
        typesUsingFunction.clear();
        typesUsingGlobals.clear();
        globalsUsingFunction.clear();
        
        this.createFunctionRelations(functions, types);
        this.createGlobalVariableRelations(globals);
    }
    
    private void createFunctionRelations(Set<Function> functions,
            Set<ComplexType> types)
    {
        for(Function function : functions)
        {
            for(Call call : function.getCalls())
            {
                this.functionsCalling.increment(call.getFunction(), function);     
            }
            
            for(Entry<GlobalVariable, Integer> variableUse : 
                function.getGlobalVariables().entrySet())
            {
                this.globalsUsingFunction.increment(variableUse.getKey(),
                        function, variableUse.getValue());
            }
            
            for(Entry<ComplexType, Integer> typeUse : 
                function.getTotalComplexTypes().entrySet())
            {
                this.typesUsingFunction.increment(typeUse.getKey(),
                            function, typeUse.getValue());
            }
        }
    }
    
    private void createGlobalVariableRelations(Set<GlobalVariable> globals)
    {
        for(GlobalVariable global : globals)
        {
            Type type = global.getType();
            
            if(type.isComplex())
            {
                this.typesUsingGlobals.increment((ComplexType) type, global);
            }
        }
    }
    
    @Override
    public Set<Function> getFunctionsCalledBy(Function function)
    {
        throw new NotImplementedException();
    }
    
    @Override
    public Set<Function> getFunctionsCalling(Function function)
    {
        return this.functionsCalling.getCounters(function);
    }
    
    @Override
    public Set<GlobalVariable> getGlobalsUsedBy(Function function)
    {
        throw new NotImplementedException();
    }
    
    @Override
    public Set<Function> getFunctionUsing(GlobalVariable global)
    {
        return this.globalsUsingFunction.getCounters(global);
    }
    
    @Override
    public Set<ComplexType> getTypesUsedBy(Function function)
    {
        throw new NotImplementedException();
    }
    
    @Override
    public Set<Function> getFunctionUsing(ComplexType type)
    {
        return this.typesUsingFunction.getCounters(type);
    }
    
    @Override
    public Set<GlobalVariable> getGlobalsUsing(ComplexType type)
    {
        return this.typesUsingGlobals.getCounters(type);
    }
}
