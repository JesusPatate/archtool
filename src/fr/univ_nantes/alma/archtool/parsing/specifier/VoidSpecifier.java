package fr.univ_nantes.alma.archtool.parsing.specifier;

import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.Type;


public class VoidSpecifier extends DeclarationSpecifier
{
    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
        return specifier.mergeWithVoid(this);
    }
    
    @Override
    public Type getType()
    {
        return PrimitiveType.voidType();
    }
    
    @Override
    public String toString()
    {
        return String.format("void specifier");
    }
}
