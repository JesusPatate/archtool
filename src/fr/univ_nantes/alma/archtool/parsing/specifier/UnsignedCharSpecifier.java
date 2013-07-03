package fr.univ_nantes.alma.archtool.parsing.specifier;

import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.Type;


public class UnsignedCharSpecifier extends DeclarationSpecifier
{
    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
        return specifier.mergeWithUnsignedChar(this);
    }
    
    @Override
    public Type getType()
    {
        return PrimitiveType.unsignedCharType();
    }
    
    @Override
    public String toString()
    {
        return String.format("unsigned char specifier");
    }
}
