package fr.univ_nantes.alma.archtool.sourceModel;

public abstract class Type
{
    private final String name;

    private final File sourceFile;
    
    protected boolean isComplex = false;

    public Type(final String name, final File sourceFile)
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
    
    public boolean isComplex()
    {
        return this.isComplex;
    }
    
    @Override
    public String toString()
    {
        return this.name;
    }
}
