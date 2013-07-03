package fr.univ_nantes.alma.archtool.parsing.specifier;

import java.util.Set;

import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;


public class TypedefNameSpecifier extends StructuredSpecifier
{
    public TypedefNameSpecifier(String name, Set<ComplexType> types)
    {
        super(name, types);
    }

    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
        return specifier.mergeWithTypedefName(this);
    }
    
    protected DeclarationSpecifier mergeWithTypedef(TypedefSpecifier specifier)
    {
        return this;
    }
    
    @Override
    public String toString()
    {
        return String.format("typedef name specifier : %s", this.name);
    }
}
