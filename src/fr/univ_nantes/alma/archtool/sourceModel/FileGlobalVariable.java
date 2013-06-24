package fr.univ_nantes.alma.archtool.sourceModel;

public class FileGlobalVariable extends Variable
{
    private File sourceFile;

    public FileGlobalVariable(String name, Type type, File sourceFile)
    {
        super(name, type);
        this.sourceFile = sourceFile;
    }

    public File getSourceFile()
    {
        return this.sourceFile;
    }
}
