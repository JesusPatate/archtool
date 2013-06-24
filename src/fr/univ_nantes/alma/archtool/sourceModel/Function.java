package sourceModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Function
{
    private String name;

    private Type returnType;

    private Set<LocalVariable> arguments;

    private File sourceFile;

    private Block body;

    public Function(String name, Set<LocalVariable> arguments, Type returnType,
            File sourceFile, Block body)
    {
        this.name = name;
        this.arguments = arguments;
        this.returnType = returnType;
        this.sourceFile = sourceFile;
        this.body = body;
    }

    public String getName()
    {
        return this.name;
    }

    public Set<LocalVariable> getArguments()
    {
        return new HashSet<LocalVariable>(this.arguments);
    }

    public Type getReturnType()
    {
        return this.returnType;
    }

    public File getSourceFile()
    {
        return this.sourceFile;
    }

    public Map<ProgramGlobalVariable, Integer> getProgramGlobals()
    {
        return this.body.getProgramGlobals();
    }

    public Map<FileGlobalVariable, Integer> getFileGlobals()
    {
        return this.body.getFileGlobals();
    }

    public Map<LocalVariable, Integer> getLocals()
    {
        return this.body.getLocals();
    }

    public Set<Call> getCalls()
    {
        return this.body.getCalls();
    }
    
    public Map<Type, Integer> getUsedTypes()
    {
        Map<Type, Integer> total = new HashMap<Type, Integer>();
        
        for(Variable var : this.arguments)
        {
            Type t = var.ge
        }
        
        return total;
    }
}
