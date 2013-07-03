package fr.univ_nantes.alma.archtool.sourceModel;

public class Folder
{
    private final String name;

    private final Folder parent;

    public Folder(final String name, final Folder parent)
    {
        this.name = name;
        this.parent = parent;
    }

    public String getName()
    {
        return this.name;
    }

    public Folder getParent()
    {
        return this.parent;
    }
}
