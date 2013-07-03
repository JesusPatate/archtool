package fr.univ_nantes.alma.archtool.parsing.specifier;

import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.Type;


public class UnisgnedIntSpecifier extends DeclarationSpecifier
{
    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
        return specifier.mergeWithUnsignedInt(this);
    }    
    
    protected DeclarationSpecifier mergeWithLongLong(LongLongSpecifier specifier)
    {
        return new UnsignedLongLongSpecifier();
    }
    
    protected DeclarationSpecifier mergeWithLong(LongSpecifier specifier)
    {
        return new UnsignedLongSpecifier();
    }
    
    protected DeclarationSpecifier mergeWithShort(ShortSpecifier specifier)
    {
        return new UnsignedShortSpecifier();
    }
    
    @Override
    public Type getType()
    {
        return PrimitiveType.unsignedIntType();
    }
    
    @Override
    public String toString()
    {
        return String.format("unsigned int specifier");
    }
}
