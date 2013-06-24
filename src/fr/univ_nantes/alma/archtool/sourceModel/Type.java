package sourceModel;

public abstract class Type
{
    private String name;

    private File sourceFile;

    public Type(String name, File sourceFile)
    {
        this.name = name;
        this.sourceFile = sourceFile;
    }

    public String getName()
    {
        return this.name;
    }

    public File getSourceFile()
    {
        return this.sourceFile;
    }
}
