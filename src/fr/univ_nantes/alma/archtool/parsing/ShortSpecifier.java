package fr.univ_nantes.alma.archtool.parsing;

public class ShortSpecifier extends DeclarationSpecifier
{
    public DeclarationSpecifier merge(SignedSpecifier specifier)
    {
        return this;
    }
    
    public DeclarationSpecifier merge(UnsignedSpecifier specifier)
    {
        return new UnsignedShortSpecifier();
    }
    
    public DeclarationSpecifier merge(IntSpecifier specifier)
    {
        return this;
    }
}
