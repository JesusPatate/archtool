package fr.univ_nantes.alma.archtool.sourceModel;

public class ComplexType extends Type
{
    public ComplexType(final String name, final File sourceFile)
    {
        super(name, sourceFile);
    }

    public static Type anonymousType()
    {
        return new ComplexType("anonymous", null);
    }
}
