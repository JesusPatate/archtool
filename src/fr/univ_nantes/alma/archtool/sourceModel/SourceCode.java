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

    private final Set<ProgramGlobalVariable> programGlobals =
            new HashSet<ProgramGlobalVariable>();

    private final Set<FileGlobalVariable> fileGlobals =
            new HashSet<FileGlobalVariable>();

    private final Set<ComplexType> types = new HashSet<ComplexType>();

    public Set<Function> getFunctions()
    {
        return new HashSet<Function>(this.functions);
    }

    public void addFunction(final Function function)
    {
        this.functions.add(function);
    }

    public Set<ProgramGlobalVariable> getProgramGlobals()
    {
        return new HashSet<ProgramGlobalVariable>(this.programGlobals);
    }

    public void addProgramGlobal(final ProgramGlobalVariable var)
    {
        this.programGlobals.add(var);
    }

    public boolean isProgramGlobal(final Variable var)
    {
        return this.programGlobals.contains(var);
    }

    public Set<FileGlobalVariable> getFileGlobals()
    {
        return new HashSet<FileGlobalVariable>(this.fileGlobals);
    }

    public void addFileGlobal(final FileGlobalVariable var)
    {
        this.fileGlobals.add(var);
    }

    public boolean isFileGlobal(final Variable var)
    {
        return this.fileGlobals.contains(var);
    }

    public Set<Type> getTypes()
    {
        return new HashSet<Type>(this.types);
    }

    public void addType(final ComplexType t1)
    {
        this.types.add(t1);
    }

    public boolean isComplexType(final Type type)
    {
        return this.types.contains(type);
    }
}
