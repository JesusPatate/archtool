package fr.univ_nantes.alma.archtool.parsing.specifier;

import java.util.Map;

import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Type;

public abstract class StructuredSpecifier extends DeclarationSpecifier
{
    protected String name;
    protected Map<String, ComplexType> complextypes;
    protected Map<String, ComplexType> otherComplextypes;
    
    public StructuredSpecifier(String name, Map<String, ComplexType> complextypes,
    		Map<String, ComplexType> otherComplextypes)
    {
        this.name = name;
        this.complextypes = complextypes;
        this.otherComplextypes = otherComplextypes;
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
        else if(this.otherComplextypes.containsKey(name))
        {
            type = this.otherComplextypes.get(name);
        }
        // We don't know the declaration of the type
        else
        {
            type = new ComplexType(this.name, null);
            this.otherComplextypes.put(name, (ComplexType) type);
        }
        
        return type;
    }
}
