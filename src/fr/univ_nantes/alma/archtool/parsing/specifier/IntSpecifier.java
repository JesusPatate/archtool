package fr.univ_nantes.alma.archtool.parsing.specifier;

import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.Type;


public class IntSpecifier extends DeclarationSpecifier
{               
    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
        return specifier.mergeWithInt(this);
    }
    
    protected DeclarationSpecifier mergeWithUnsigned(UnsignedSpecifier specifier)
    {
        return new UnisgnedIntSpecifier();
    }
    
    @Override
    public Type getType()
    {
        return PrimitiveType.intType();
    }
    
    @Override
    public String toString()
    {
        return String.format("int specifier");
    }
}
