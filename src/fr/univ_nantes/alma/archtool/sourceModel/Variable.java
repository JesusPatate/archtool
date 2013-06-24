package fr.univ_nantes.alma.archtool.sourceModel;

public abstract class Variable
{
    private String name;
    
    private Type type;

    public Variable(String name, Type type)
    {
        this.name = name;
        this.type = type;
    }

    public String getName()
    {
        return this.name;
    }
    
    public Type getType()
    {
        return this.type;
    }
}
