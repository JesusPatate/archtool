package fr.univ_nantes.alma.archtool.parsing.specifier;

import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.Type;


public class UnsignedLongSpecifier extends DeclarationSpecifier
{    
    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
        return specifier.mergeWithUnsignedLong(this);
    }
    
    public DeclarationSpecifier mergeWithLong(LongSpecifier specifier)
    {
        return new UnsignedLongLongSpecifier();
    }
    
    protected DeclarationSpecifier mergeWithInt(IntSpecifier specifier)
    {
        return this;
    }
    
    @Override
    public Type getType()
    {
        return PrimitiveType.unsignedLongType();
    }
    
    @Override
    public String toString()
    {
        return String.format("unsigned long specifier");
    }
}
