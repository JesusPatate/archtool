package fr.univ_nantes.alma.archtool.sourceModel;

public class ComplexType extends Type
{
    public final static Type anonymousType = new ComplexType("anonymous", null);
    
    public ComplexType(final String name, final File sourceFile)
    {
        super(name, sourceFile);
        this.isComplex = true;
    }
}
