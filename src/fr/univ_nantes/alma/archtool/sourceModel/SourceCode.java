package sourceModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Contains all elements of the source code.
 */
public class SourceCode
{
    private Set<Function> functions = new HashSet<Function>();

    private Set<ProgramGlobalVariable> programGlobals =
            new HashSet<ProgramGlobalVariable>();

    private Set<FileGlobalVariable> fileGlobals =
            new HashSet<FileGlobalVariable>();

    private Set<ComplexType> types = new HashSet<ComplexType>();
    
    public void addFunction(Function function)
    {
        this.functions.add(function);
    }
    
    public void addProgramGlobal(ProgramGlobalVariable var)
    {
        this.programGlobals.add(var);
    }
    
    public void addFileGlobal(FileGlobalVariable var)
    {
        this.fileGlobals.add(var);
    }
    
    public void addType(ComplexType type)
    {
        this.types.add(type);
    }
    
    public boolean isProgramGlobal(Variable var)
    {
        return this.programGlobals.contains(var);
    }
    
    public boolean isFileGlobal(Variable var)
    {
        return this.fileGlobals.contains(var);
    }
    
    public boolean isComplexType(Type type)
    {
        return this.types.contains(type);
    }
}
