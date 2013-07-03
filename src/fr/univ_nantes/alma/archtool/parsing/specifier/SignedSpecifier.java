package fr.univ_nantes.alma.archtool.parsing.specifier;


public class SignedSpecifier extends DeclarationSpecifier
{
    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
        return specifier.mergeWithSigned(this);
    }
    
    @Override
    public String toString()
    {
        return String.format("signed specifier");
    }
}
