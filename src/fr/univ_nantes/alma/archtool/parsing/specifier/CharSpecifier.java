package fr.univ_nantes.alma.archtool.parsing.specifier;

import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.Type;


public class CharSpecifier extends DeclarationSpecifier
{
    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
        return specifier.mergeWithChar(this);
    }
    
    protected DeclarationSpecifier mergeWithUnsigned(UnsignedSpecifier specifier)
    {
        return new UnsignedCharSpecifier();
    }
    
    @Override
    public Type getType()
    {
        return PrimitiveType.charType();
    }
    
    @Override
    public String toString()
    {
        return String.format("char specifier");
    }
}
