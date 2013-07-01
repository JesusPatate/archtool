package fr.univ_nantes.alma.archtool.parsing;

public class LongSpecifier extends DeclarationSpecifier
{
    public DeclarationSpecifier merge(SignedSpecifier specifier)
    {
        return this;
    }
    
    public DeclarationSpecifier merge(UnsignedSpecifier specifier)
    {
        return new UnsignedLongSpecifier();
    }
    
    public DeclarationSpecifier merge(IntSpecifier specifier)
    {
        return this;
    }
    
    public DeclarationSpecifier merge(LongSpecifier specifier)
    {
        return new LongLongSpecifier();
    }
}
