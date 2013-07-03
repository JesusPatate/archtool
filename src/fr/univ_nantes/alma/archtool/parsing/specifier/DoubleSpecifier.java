package fr.univ_nantes.alma.archtool.parsing.specifier;

import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.Type;


public class DoubleSpecifier extends DeclarationSpecifier
{
    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
        return specifier.mergeWithDouble(this);
    }
    
    @Override
    public Type getType()
    {
        return PrimitiveType.doubleType();
    }
    
    @Override
    public String toString()
    {
        return String.format("double specifier");
    }
}
