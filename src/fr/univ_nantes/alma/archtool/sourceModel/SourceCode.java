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
    
    public void addFunction(final Function function)
    {           
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
    
    public Set<File> getSourceFiles()
    {
        Set<File> files = new HashSet<File>();
        
        for(Function function : this.coreFunctions)
        {
            files.add(function.getSourceFile());
        }
        
        for(ComplexType type : this.coreComplexTypes)
        {
            files.add(type.getSourceFile());
        }
        
        for(GlobalVariable variable : this.coreGlobalVariables)
        {
            files.add(variable.getSourceFile());
        }
        
        return files;
    }
    
    public Set<Function> getFileFunctions(File sourceFile)
    {
        Set<Function> functions = new HashSet<Function>();
        
        for(Function function : this.coreFunctions)
        {
            if(function.getSourceFile() == sourceFile)
            {
                functions.add(function);
            }
        }
        
        return functions;
    }
    
    public Set<ComplexType> getFileComplexTypes(File sourceFile)
    {
        Set<ComplexType> types = new HashSet<ComplexType>();

        for(ComplexType type : this.coreComplexTypes)
        {
            if(type.getSourceFile() == sourceFile)
            {
                types.add(type);
            }
        }
        
        return types;
    }
    
    public Set<GlobalVariable> getFileGlobalVariables(File sourceFile)
    {
        Set<GlobalVariable> variables = new HashSet<GlobalVariable>();

        
        for(GlobalVariable variable : this.coreGlobalVariables)
        {
            if(variable.getSourceFile() == sourceFile)
            {
                variables.add(variable);
            }
        }
        
        return variables;
    }
}
