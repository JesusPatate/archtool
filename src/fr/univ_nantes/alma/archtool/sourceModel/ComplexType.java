package fr.univ_nantes.alma.archtool.sourceModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ComplexType extends Type
{
    public final static Type anonymousType = new ComplexType("anonymous", null);
    
    private SourceCode sourceCode = null;
    
    // Cached data
    private Set<GlobalVariable> coreUsingGlobalVariables = null;
    
    // Cached data
    private Map<Function, Integer> totalUsingFunctions = null;
    
    // Cached data
    private Map<Function, Integer> coreUsingFunctions = null;
    
    public ComplexType(final String name, final File sourceFile)
    {
        super(name, sourceFile);
        this.isComplex = true;
    }
    
    public void setSourceCode(SourceCode sourceCode)
    {
        this.sourceCode = sourceCode;
    }
    
    public Set<GlobalVariable> getCoreUsingGlobalVariables()
    {
        if(this.sourceCode != null && (this.coreUsingGlobalVariables == null ||
                this.sourceCode.hasChanged()))
        {            
            this.coreUsingGlobalVariables = new HashSet<GlobalVariable>();
            
            for(GlobalVariable globalVariable : 
                this.sourceCode.getCoreGlobalVariables())
            {
                Type type = globalVariable.getType();
                
                if(type.isComplex && (ComplexType) type == this)
                {
                    this.coreUsingGlobalVariables.add(globalVariable);
                }
            }
        }
        
        return this.coreUsingGlobalVariables == null ?
                new HashSet<GlobalVariable>() : 
                new HashSet<GlobalVariable>(this.coreUsingGlobalVariables);
    }   
    
    public Map<Function, Integer> getTotalUsingFunctions()
    {
        if(this.sourceCode != null && (this.totalUsingFunctions == null ||
                this.sourceCode.hasChanged()))
        {            
            this.totalUsingFunctions = new HashMap<Function, Integer>();
            
            for(Function function : this.sourceCode.getCoreFunctions())
            {
                Map<ComplexType, Integer> usedTypes = 
                        function.getTotalComplexTypes();
                
                if(usedTypes.containsKey(this))
                {
                    this.totalUsingFunctions.put(function,
                            totalUsingFunctions.get(this));
                }
            }
        }
        
        return this.totalUsingFunctions == null ?
                new HashMap<Function, Integer>() : 
                new HashMap<Function, Integer>(this.totalUsingFunctions);
    }
    
    public Map<Function, Integer> getCoreUsingFunctions()
    {
        if(this.sourceCode != null && (this.coreUsingFunctions == null ||
                this.sourceCode.hasChanged()))
        {            
            this.coreUsingFunctions = new HashMap<Function, Integer>();
            
            for(Function function : this.sourceCode.getCoreFunctions())
            {
                Map<ComplexType, Integer> usedTypes = 
                        function.getCoreComplexTypes();
                
                if(usedTypes.containsKey(this))
                {
                    this.coreUsingFunctions.put(function,
                            coreUsingFunctions.get(this));
                }
            }
        }
        
        return this.coreUsingFunctions == null ?
                new HashMap<Function, Integer>() : 
                new HashMap<Function, Integer>(this.coreUsingFunctions);
    }
}
