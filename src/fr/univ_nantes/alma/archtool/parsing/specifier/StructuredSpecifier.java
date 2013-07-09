package fr.univ_nantes.alma.archtool.parsing.specifier;

import java.util.Map;

import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Type;

public abstract class StructuredSpecifier extends DeclarationSpecifier
{
    protected String name;
    protected Map<String, ComplexType> complextypes;
    
    public StructuredSpecifier(String name, Map<String, ComplexType> complextypes)
    {
        this.name = name;
        this.complextypes = complextypes;
    }
    
    @Override
    public Type getType()
    {
        Type type = null;
        
        if(this.name == null)
        {
            type = ComplexType.anonymousType;
        }
        else if(this.complextypes.containsKey(name))
        {
            type = this.complextypes.get(name);
        }
        
        // We don't know the declaration of the type
        else
        {
            type = ComplexType.anonymousType;
        }
        
        return type;
    }
}
