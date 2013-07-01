package fr.univ_nantes.alma.archtool.parsing;

public class IntSpecifier extends DeclarationSpecifier
{
    public DeclarationSpecifier merge(SignedSpecifier specifier)
    {
        return this;
    }
    
    public DeclarationSpecifier merge(UnsignedSpecifier specifier)
    {
        return new UnisgnedIntSpecifier();
    }
    
    public DeclarationSpecifier merge(ShortSpecifier specifier)
    {
        return new ShortSpecifier();
    }
    
    public DeclarationSpecifier merge(LongSpecifier specifier)
    {
        return new LongSpecifier();
    }
}
