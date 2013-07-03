package fr.univ_nantes.alma.archtool.parsing.specifier;

import java.util.Iterator;
import java.util.Set;

import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Type;

public abstract class StructuredSpecifier extends DeclarationSpecifier
{
    protected String name;
    protected Set<ComplexType> types;
    
    public StructuredSpecifier(String name, Set<ComplexType> types)
    {
        this.name = name;
        this.types = types;
    }
    
    @Override
    public Type getType()
    {
        Type type = null;
        
        if(this.name == null)
        {
            type = ComplexType.anonymousType();
        }
        else
        {
            ComplexType newType = new ComplexType(this.name, null);
            Iterator<ComplexType> it = this.types.iterator();
            
            while()
        }
        
        return type;
    }
}
