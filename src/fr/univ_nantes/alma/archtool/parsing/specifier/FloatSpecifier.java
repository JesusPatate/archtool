package fr.univ_nantes.alma.archtool.parsing.specifier;

import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.Type;


public class FloatSpecifier extends DeclarationSpecifier
{
    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
        return specifier.mergeWithFloat(this);
    }
    
    @Override
    public Type getType()
    {
        return PrimitiveType.floatType();
    }
    
    @Override
    public String toString()
    {
        return String.format("float specifier");
    }
}
