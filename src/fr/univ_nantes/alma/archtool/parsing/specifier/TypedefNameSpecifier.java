package fr.univ_nantes.alma.archtool.parsing.specifier;

import java.util.Map;

import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;


public class TypedefNameSpecifier extends StructuredSpecifier
{
    public TypedefNameSpecifier(String name, Map<String, ComplexType> complextypes,
    		Map<String, ComplexType> otherComplextypes)
    {
        super(name, complextypes, otherComplextypes);
    }

    @Override
    public DeclarationSpecifier merge(DeclarationSpecifier specifier)
    {
        return specifier.mergeWithTypedefName(this);
    }
    
    protected DeclarationSpecifier mergeWithVoid(VoidSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithChar(CharSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithShort(ShortSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithInt(IntSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithUnsignedInt(UnisgnedIntSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithLong(LongSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithUnsignedLong(UnsignedLongSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithUnsignedLongLong(UnsignedLongLongSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithLongLong(LongLongSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithFloat(FloatSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithDouble(DoubleSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithUnsignedChar(UnsignedCharSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithUnsignedShort(UnsignedShortSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithTypedefName(TypedefNameSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithEnum(EnumSpecifier specifier)
    {
        return this;
    }
    
    protected DeclarationSpecifier mergeWithStructOrUnion(StructOrUnionSpecifier specifier)
    {
        return this;
    }  
    
    @Override
    public String toString()
    {
        return String.format("typedef name specifier : %s", this.name);
    }
}
