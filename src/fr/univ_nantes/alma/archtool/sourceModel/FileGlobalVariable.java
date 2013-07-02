package fr.univ_nantes.alma.archtool.sourceModel;

public class FileGlobalVariable extends Variable
{
    private final File sourceFile;

    public FileGlobalVariable(final String name, final Type type,
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
