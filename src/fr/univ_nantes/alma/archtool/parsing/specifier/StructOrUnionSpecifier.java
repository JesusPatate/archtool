package fr.univ_nantes.alma.archtool.parsing.specifier;

import java.util.Map;

import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;


public class StructOrUnionSpecifier extends StructuredSpecifier
{
    public StructOrUnionSpecifier(String name, Map<String, ComplexType> complextypes)
    {
        super(name, complextypes);
    }

    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
       return specifier.mergeWithStructOrUnion(this);
    }
    
    @Override
    public String toString()
    {
        return String.format("struct or union specifier : %s", this.name);
    }
}
