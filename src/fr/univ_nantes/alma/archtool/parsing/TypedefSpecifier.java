package fr.univ_nantes.alma.archtool.parsing;

import fr.univ_nantes.alma.archtool.sourceModel.Type;

public class TypedefSpecifier extends DeclarationSpecifier
{        
    public DeclarationSpecifier merge(VoidSpecifier specifier)
    {
        return this;
    }
    
    public DeclarationSpecifier merge(CharSpecifier specifier)
    {
        return this;
    }
    
    public DeclarationSpecifier merge(ShortSpecifier specifier)
    {
        return this;
    }
    
    public DeclarationSpecifier merge(IntSpecifier specifier)
    {
        return this;
    }
    
    public DeclarationSpecifier merge(LongSpecifier specifier)
    {
        return this;
    }
    
    public DeclarationSpecifier merge(FloatSpecifier specifier)
    {
        return this;
    }
    
    public DeclarationSpecifier merge(DoubleSpecifier specifier)
    {
        return this;
    }
    
    public DeclarationSpecifier merge(SignedSpecifier specifier)
    {
        return this;
    }
    
    public DeclarationSpecifier merge(UnsignedSpecifier specifier)
    {
        return this;
    }
    
    public DeclarationSpecifier merge(EnumSpecifier specifier)
    {
        return this;
    }
    
    public DeclarationSpecifier merge(StructOrUnionSpecifier specifier)
    {
        return this;
    }
    
    public Type getType()
    {
        throw new RuntimeException("Bad declaration specifier");
    }
    
    @Override
    public String toString()
    {
        return "TypedefSpecifier";
    }
}