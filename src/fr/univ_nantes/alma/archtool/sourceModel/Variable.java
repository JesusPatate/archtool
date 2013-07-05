package fr.univ_nantes.alma.archtool.sourceModel;

public abstract class Variable
{
    private final String name;

    private final Type type;

    public Variable(final String name, final Type type)
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

    public boolean ofType(final Type t)
    {
        return t.equals(this.type);
    }
    
    @Override
    public String toString()
    {
        return String.format("name : %s, %s", this.getName(), this.getType());
    }
}
