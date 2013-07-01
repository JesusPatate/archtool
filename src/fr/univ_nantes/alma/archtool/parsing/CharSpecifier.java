package fr.univ_nantes.alma.archtool.parsing;

public class CharSpecifier extends DeclarationSpecifier
{           
    public DeclarationSpecifier merge(UnsignedSpecifier specifier)
    {
        return new UnsignedCharSpecifier();
    }
    
    public DeclarationSpecifier merge(TypedefNameSpecifier specifier)
    {
        return specifier;
    }
}
