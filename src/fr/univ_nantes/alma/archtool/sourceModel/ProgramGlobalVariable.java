package fr.univ_nantes.alma.archtool.sourceModel;

public class ProgramGlobalVariable extends Variable
{
    private final File sourceFile;

    public ProgramGlobalVariable(final String name, final Type type,
            final File sourceFile)
    {
        super(name, type);
        this.sourceFile = sourceFile;
    }

    public File getSourceFile()
    {
        return this.sourceFile;
    }
}
