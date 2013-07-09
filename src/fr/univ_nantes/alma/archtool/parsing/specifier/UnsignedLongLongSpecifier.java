package fr.univ_nantes.alma.archtool.parsing.specifier;

import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.Type;

public class UnsignedLongLongSpecifier extends DeclarationSpecifier
{    
    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
        return specifier.mergeWithUnsignedLongLong(this);
    }
    
    protected DeclarationSpecifier mergeWithInt(IntSpecifier specifier)
    {
        return this;
    }
    
    @Override
    public Type getType()
    {
        return PrimitiveType.unsignedLongLongType;
    }
    
    @Override
    public String toString()
    {
        return String.format("unsigned long long specifier");
    }
}
