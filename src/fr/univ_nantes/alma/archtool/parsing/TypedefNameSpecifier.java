package fr.univ_nantes.alma.archtool.parsing;

public class TypedefNameSpecifier extends DeclarationSpecifier
{
    private String name;
    
    public TypedefNameSpecifier(String name)
    {
        this.name = name;
    }

    public DeclarationSpecifier merge(TypedefSpecifier typedef)
    {
        return this;
    }
    
    @Override
    public String toString()
    {
        return "TypedefNameSpecifier";
    }
}
