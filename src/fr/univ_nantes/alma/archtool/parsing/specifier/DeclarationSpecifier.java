package fr.univ_nantes.alma.archtool.parsing.specifier;

import fr.univ_nantes.alma.archtool.sourceModel.Type;

public abstract class DeclarationSpecifier
{
	protected boolean isNull = false;
	
    public abstract DeclarationSpecifier merge(DeclarationSpecifier specifier);
    
    protected DeclarationSpecifier mergeWithNull(NullSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithVoid(VoidSpecifier specifier)
    {
        return specifier;
    }
    
    protected DeclarationSpecifier mergeWithChar(CharSpecifier specifier)
    {
        return specifier;
    }
    
    protected DeclarationSpecifier mergeWithShort(ShortSpecifier specifier)
    {
        return specifier;
    }
    
    protected DeclarationSpecifier mergeWithInt(IntSpecifier specifier)
    {
        return specifier;
    }
    
    protected DeclarationSpecifier mergeWithUnsignedInt(UnisgnedIntSpecifier specifier)
    {
        return specifier;
    }
    
    protected DeclarationSpecifier mergeWithLong(LongSpecifier specifier)
    {
        return specifier;
    }
    
    protected DeclarationSpecifier mergeWithUnsignedLong(UnsignedLongSpecifier specifier)
    {
        return specifier;
    }
    
    protected DeclarationSpecifier mergeWithUnsignedLongLong(UnsignedLongLongSpecifier specifier)
    {
        return specifier;
    }
    
    protected DeclarationSpecifier mergeWithLongLong(LongLongSpecifier specifier)
    {
        return specifier;
    }
    
    protected DeclarationSpecifier mergeWithFloat(FloatSpecifier specifier)
    {
        return specifier;
    }
    
    protected DeclarationSpecifier mergeWithDouble(DoubleSpecifier specifier)
    {
        return specifier;
    }
    
    protected DeclarationSpecifier mergeWithSigned(SignedSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithUnsigned(UnsignedSpecifier specifier)
    {
        return specifier;
    }
    
    protected DeclarationSpecifier mergeWithUnsignedChar(UnsignedCharSpecifier specifier)
    {
        return specifier;
    }
    
    protected DeclarationSpecifier mergeWithUnsignedShort(UnsignedShortSpecifier specifier)
    {
        return specifier;
    }
    
    protected DeclarationSpecifier mergeWithTypedefName(TypedefNameSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithEnum(EnumSpecifier specifier)
    {
        return specifier;
    }
    
    protected DeclarationSpecifier mergeWithStructOrUnion(StructOrUnionSpecifier specifier)
    {
        return specifier;
    }
    
    public boolean isNull()
    {
    	return this.isNull;
    }
    
    public Type getType()
    {
        throw new RuntimeException("Bad declaration specifier");
    }
}
