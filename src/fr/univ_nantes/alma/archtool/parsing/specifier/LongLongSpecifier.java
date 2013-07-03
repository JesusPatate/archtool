package fr.univ_nantes.alma.archtool.parsing.specifier;

import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.Type;


public class LongLongSpecifier extends DeclarationSpecifier
{        
    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
       return specifier.mergeWithLongLong(this);
    }
    
    protected DeclarationSpecifier mergeWithUnsignedInt(UnisgnedIntSpecifier specifier)
    {
        return new UnsignedLongLongSpecifier();
    }
    
    protected DeclarationSpecifier mergeWithUnsigned(UnsignedSpecifier specifier)
    {
        return new UnsignedLongLongSpecifier();
    }
    
    @Override
    public Type getType()
    {
        return PrimitiveType.longLongType();
    }
    
    @Override
    public String toString()
    {
        return String.format("long long specifier");
    }
}
