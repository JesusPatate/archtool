package fr.univ_nantes.alma.archtool.sourceModel;

public class LocalVariable extends Variable
{
    public LocalVariable(final String name, final Type type)
    {
        super(name, type);
    }
    
    @Override
    public String toString()
    {
        return String.format("name : %s, %s", this.getName(), this.getType());
    }
}
