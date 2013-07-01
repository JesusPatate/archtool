package fr.univ_nantes.alma.archtool.parsing;

public class NullSpecifier extends DeclarationSpecifier
{    
    public DeclarationSpecifier merge(TypedefSpecifier specifier)
    {
        return specifier;
    }
}
