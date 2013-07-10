package fr.univ_nantes.alma.archtool.parsing.specifier;

import java.util.Map;

import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;


public class EnumSpecifier extends StructuredSpecifier
{  
    public EnumSpecifier(String name, Map<String, ComplexType> complextypes,
    		Map<String, ComplexType> otherComplextypes)
    {
        super(name, complextypes, otherComplextypes);
    }
    
    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
        return specifier.mergeWithEnum(this);
    }
    
    @Override
    public String toString()
    {
        return String.format("enum specifier : %s", this.name);
    }
}
