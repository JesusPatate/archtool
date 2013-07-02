package fr.univ_nantes.alma.archtool.sourceModel;

public class PrimitiveType extends Type
{
    private PrimitiveType(final String name)
    {
        super(name, null);
    }

    public static PrimitiveType charType()
    {
        return new PrimitiveType("char");
    }

    public static PrimitiveType shortType()
    {
        return new PrimitiveType("short");
    }

    public static PrimitiveType intType()
    {
        return new PrimitiveType("int");
    }

    public static PrimitiveType longType()
    {
        return new PrimitiveType("long");
    }

    public static PrimitiveType floatType()
    {
        return new PrimitiveType("float");
    }

    public static PrimitiveType doubleType()
    {
        return new PrimitiveType("double");
    }

    public static PrimitiveType voidType()
    {
        return new PrimitiveType("void");
    }
}
