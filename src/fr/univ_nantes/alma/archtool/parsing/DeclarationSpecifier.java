package fr.univ_nantes.alma.archtool.parsing;

import fr.univ_nantes.alma.archtool.sourceModel.Type;

public abstract class DeclarationSpecifier
{
    public DeclarationSpecifier merge(NullSpecifier specifier)
    {
        return this;
    }
    
    public DeclarationSpecifier merge(TypedefSpecifier specifier)
    {
        return this;
    }
    
    public DeclarationSpecifier merge(VoidSpecifier specifier)
    {
        return specifier;
    }
    
    public DeclarationSpecifier merge(CharSpecifier specifier)
    {
        return specifier;
    }
    
    public DeclarationSpecifier merge(ShortSpecifier specifier)
    {
        return specifier;
    }
    
    public DeclarationSpecifier merge(IntSpecifier specifier)
    {
        return specifier;
    }
    
    public DeclarationSpecifier merge(UnisgnedIntSpecifier specifier)
    {
        return specifier;
    }
    
    public DeclarationSpecifier merge(LongSpecifier specifier)
    {
        return specifier;
    }
    public DeclarationSpecifier merge(UnsignedLongSpecifier specifier)
    {
        return specifier;
    }
    
    public DeclarationSpecifier merge(FloatSpecifier specifier)
    {
        return specifier;
    }
    
    public DeclarationSpecifier merge(DoubleSpecifier specifier)
    {
        return specifier;
    }
    
    public DeclarationSpecifier merge(SignedSpecifier specifier)
    {
        return specifier;
    }
    
    public DeclarationSpecifier merge(UnsignedSpecifier specifier)
    {
        return specifier;
    }
    
    public DeclarationSpecifier merge(UnsignedCharSpecifier specifier)
    {
        return specifier;
    }
    
    public DeclarationSpecifier merge(TypedefNameSpecifier specifier)
    {
        return specifier;
    }
    
    public DeclarationSpecifier merge(EnumSpecifier specifier)
    {
        return specifier;
    }
    
    public DeclarationSpecifier merge(StructOrUnionSpecifier specifier)
    {
        return specifier;
    }
    
    public Type getType()
    {
        throw new RuntimeException("Bad declaration specifier");
    }
    
    @Override
    public String toString()
    {
        return "DeclarationSpecifier";
    }
}
