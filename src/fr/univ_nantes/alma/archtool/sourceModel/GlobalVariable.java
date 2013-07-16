package fr.univ_nantes.alma.archtool.sourceModel;

import java.util.HashMap;
import java.util.Map;

public class GlobalVariable extends Variable
{
    private final boolean isStatic;
    private File sourceFile;
    private SourceCode sourceCode = null;
    
    // Cached data
    private Map<Function, Integer> coreUsingFunctions = null;   

    public GlobalVariable(final String name, final Type type, boolean isStatic,
            final File sourceFile)
    {
        super(name, type);
        this.isStatic = isStatic;
        this.sourceFile = sourceFile;
    }
    
    public GlobalVariable(final String name, final Type type, boolean isStatic)
    {
        super(name, type);
        this.isStatic = isStatic;
        this.sourceFile = null;
    }
    
    public void setSourceFile(File sourceFile)
    {
        this.sourceFile = sourceFile;
    }

    public File getSourceFile()
    {
        return this.sourceFile;
    }
    
    public boolean isStatic()
    {
        return this.isStatic;
    }
    
    public void setSourceCode(SourceCode sourceCode)
    {
        this.sourceCode = sourceCode;
    }
    
    public Map<Function, Integer> getCoreUsingFunctions()
    {
        if(this.sourceCode != null && this.coreUsingFunctions == null)
        {            
            this.coreUsingFunctions = new HashMap<Function, Integer>();
            
            for(Function function : this.sourceCode.getCoreFunctions())
            {
                Map<GlobalVariable, Integer> usedGlobalVariables = 
                        function.getCoreGlobalVariables();
                
                if(usedGlobalVariables.containsKey(this))
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
