package fr.univ_nantes.alma.archtool.parsing.specifier;

import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.Type;


public class ShortSpecifier extends DeclarationSpecifier
{    
    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
        return specifier.mergeWithShort(this);
    }
    
    protected DeclarationSpecifier mergeWithInt(IntSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithUnsignedInt(UnisgnedIntSpecifier specifier)
    {
        return new UnsignedShortSpecifier();
    }
    
    protected DeclarationSpecifier mergeWithUnsiged(UnsignedSpecifier specifier)
    {
        return new UnsignedShortSpecifier();
    }
    
    @Override
    public Type getType()
    {
        return PrimitiveType.shortType();
    }
    
    @Override
    public String toString()
    {
        return String.format("short specifier");
    }
}
