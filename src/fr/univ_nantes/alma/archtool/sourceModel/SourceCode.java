package fr.univ_nantes.alma.archtool.sourceModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe du modèle de code source regroupant l'ensemble des éléments
 * d'un programme.
 */
public class SourceCode
{
    private Set<Function> functions = new HashSet<Function>();

    private Set<ProgramGlobalVariable> programGlobals =
            new HashSet<ProgramGlobalVariable>();

    private Set<FileGlobalVariable> fileGlobals =
            new HashSet<FileGlobalVariable>();

    private Set<ComplexType> types = new HashSet<ComplexType>();

    
    public Set<Function> getFunctions()
    {
        return new HashSet<Function>(this.functions);
    }
    
    public void addFunction(Function function)
    {
        this.functions.add(function);
    }
    
    
    public Set<ProgramGlobalVariable> getProgramGlobals()
    {
        return new HashSet<ProgramGlobalVariable>(this.programGlobals);
    }
    
    public void addProgramGlobal(ProgramGlobalVariable var)
    {
        this.programGlobals.add(var);
    }
    
    public boolean isProgramGlobal(Variable var)
    {
        return this.programGlobals.contains(var);
    }
    
    
    public Set<FileGlobalVariable> getFileGlobals()
    {
        return new HashSet<FileGlobalVariable>(this.fileGlobals);
    }
    
    public void addFileGlobal(FileGlobalVariable var)
    {
        this.fileGlobals.add(var);
    }
    
    public boolean isFileGlobal(Variable var)
    {
        return this.fileGlobals.contains(var);
    }
    
    
    public Set<Type> getTypes()
    {
        return new HashSet<Type>(this.types);
    }
    
    public void addType(ComplexType t1)
    {
        this.types.add(t1);
    }
    
    public boolean isComplexType(Type type)
    {
        return this.types.contains(type);
    }
}
