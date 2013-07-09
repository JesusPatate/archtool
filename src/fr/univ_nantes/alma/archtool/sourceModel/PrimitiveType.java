package fr.univ_nantes.alma.archtool.sourceModel;


public class PrimitiveType extends Type
{
    public final static PrimitiveType charType = new PrimitiveType("char");
    
    public final static PrimitiveType unsignedCharType = 
            new PrimitiveType("unsigned char");
    
    public final static PrimitiveType shortType = 
            new PrimitiveType("unsigned char");
    
    public final static PrimitiveType unsignedShortType = 
            new PrimitiveType("unsigned short");
    
    public final static PrimitiveType intType = new PrimitiveType("int");
    
    public final static PrimitiveType unsignedIntType = 
            new PrimitiveType("unsigned int");
    
    public final static PrimitiveType longType = 
            new PrimitiveType("long");
    
    public final static PrimitiveType unsignedLongType = 
            new PrimitiveType("unsigned long");
    
    public final static PrimitiveType floatType = 
            new PrimitiveType("float");
    
    public final static PrimitiveType doubleType = 
            new PrimitiveType("double");
    
    public final static PrimitiveType voidType = 
            new PrimitiveType("void");
    
    public final static PrimitiveType longLongType = 
            new PrimitiveType("long long");
    
    public final static PrimitiveType unsignedLongLongType = 
            new PrimitiveType("unsigned long long");
            
    private PrimitiveType(final String name)
    {
        super(name, null);
    }
}
