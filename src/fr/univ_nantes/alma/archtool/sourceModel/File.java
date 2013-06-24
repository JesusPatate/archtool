package sourceModel;

public class File
{
    private String name;

    private Folder folder;

    public File(String name, Folder folder)
    {
        this.name = name;
        this.folder = folder;
    }

    public String getName()
    {
        return this.name;
    }

    public Folder getFolder()
    {
        return this.folder;
    }
}
