package fr.univ_nantes.alma.archtool.parsing.specifier;


public class UnsignedSpecifier extends DeclarationSpecifier
{
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
        return specifier.mergeWithUnsigned(this);
    }
    
    public DeclarationSpecifier mergeWithShort(ShortSpecifier specifier)
    {
        return new UnsignedShortSpecifier();
    }
    
    public DeclarationSpecifier mergeWithInt(IntSpecifier specifier)
    {
        return new UnisgnedIntSpecifier();
    }
    
    public DeclarationSpecifier mergeWithLong(LongSpecifier specifier)
    {
        return new UnsignedLongSpecifier();
    }
        
    public DeclarationSpecifier mergeWithLongLong(LongLongSpecifier specifier)
    {
        return new UnsignedLongLongSpecifier();
    }
    
    public DeclarationSpecifier mergeWithChar(CharSpecifier specifier)
    {
        return new UnsignedCharSpecifier();
    }
    
    @Override
    public String toString()
    {
        return String.format("unsigned specifier");
    }
}
