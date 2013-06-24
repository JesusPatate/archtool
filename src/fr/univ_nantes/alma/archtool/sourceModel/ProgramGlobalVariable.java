package sourceModel;

public class ProgramGlobalVariable extends Variable
{
    private File sourceFile;

    public ProgramGlobalVariable(String name, Type type, File sourceFile)
    {
        super(name, type);
        this.sourceFile = sourceFile;
    }

    public File getSourceFile()
    {
        return this.sourceFile;
    }
}
