package fr.univ_nantes.alma.archtool.sourceModel;

import java.util.Set;
import java.util.Map.Entry;

import fr.univ_nantes.alma.archtool.utils.MultiMultiCounter;

public class SourceCodeMediator
{
    private MultiMultiCounter<Function, Function> functionsCalledBy =
            new MultiMultiCounter<Function, Function>();
    
    private MultiMultiCounter<Function, Function> functionsCalling = 
            new MultiMultiCounter<Function, Function>();
    
    private MultiMultiCounter<Function, ComplexType> typesUsedByFunction = 
            new MultiMultiCounter<Function, ComplexType>();
    
    private MultiMultiCounter<Function, GlobalVariable> globalsUsedByFunction = 
            new MultiMultiCounter<Function, GlobalVariable>();
    
    private MultiMultiCounter<ComplexType, Function> typesUsingFunction = 
            new MultiMultiCounter<ComplexType, Function>();
    
    private MultiMultiCounter<ComplexType, GlobalVariable> typesUsingGlobals = 
            new MultiMultiCounter<ComplexType, GlobalVariable>();
    
    private MultiMultiCounter<GlobalVariable, Function> globalsUsingFunction = 
            new MultiMultiCounter<GlobalVariable, Function>();
    
    
    public void createRelations(Set<Function> functions, Set<ComplexType> types,
            Set<GlobalVariable> globals)
    {
        functionsCalledBy.clear();
        functionsCalling.clear();
        typesUsedByFunction.clear();
        globalsUsedByFunction.clear();
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
                this.functionsCalling.increment(function, call.getFunction());
                this.functionsCalledBy.increment(call.getFunction(), function);
            }
            
            for(Entry<GlobalVariable, Integer> variableUse : 
                function.getFileGlobals().entrySet())
            {
                this.globalsUsedByFunction.increment(function,
                        variableUse.getKey(), variableUse.getValue());
                this.globalsUsingFunction.increment(variableUse.getKey(),
                        function, variableUse.getValue());
            }
            
            for(Entry<ComplexType, Integer> typeUse : 
                function.getUsedTypes().entrySet())
            {
                if(types.contains(typeUse.getKey()))
                {
                    this.typesUsedByFunction.increment(function,
                            typeUse.getKey(), typeUse.getValue());
                    this.typesUsingFunction.increment(typeUse.getKey(),
                            function, typeUse.getValue());
                }
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
}
