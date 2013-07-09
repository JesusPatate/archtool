package fr.univ_nantes.alma.archtool.parsing.specifier;

import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.Type;


public class LongSpecifier extends DeclarationSpecifier
{    
    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
       return specifier.mergeWithLong(this);
    }
    
    protected DeclarationSpecifier merge(IntSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithUnsignedInt(UnisgnedIntSpecifier specifier)
    {
        return new UnsignedLongSpecifier();
    }
    
    protected DeclarationSpecifier mergeWithLong(LongSpecifier specifier)
    {
        return new LongLongSpecifier();
    }
    
    protected DeclarationSpecifier mergeWithUnsignedLong(UnsignedLongSpecifier specifier)
    {
        return new UnsignedLongLongSpecifier();
    }
    
    protected DeclarationSpecifier mergeWithUnsigned(UnsignedSpecifier specifier)
    {
        return new UnsignedLongSpecifier();
    }
    
    @Override
    public Type getType()
    {
        return PrimitiveType.longType;
    }
    
    @Override
    public String toString()
    {
        return String.format("long specifier");
    }
}
