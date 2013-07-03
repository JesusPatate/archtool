package fr.univ_nantes.alma.archtool.parsing.specifier;


public class TypedefSpecifier extends DeclarationSpecifier
{
    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
        return specifier.mergeWithTypedef(this);
    }
    
    @Override
    public String toString()
    {
        return String.format("typedef specifier");
    }
}
