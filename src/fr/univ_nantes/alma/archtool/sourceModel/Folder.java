package sourceModel;

public class Folder
{
    private String name;

    private Folder parent;

    public Folder(String name, Folder parent)
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
