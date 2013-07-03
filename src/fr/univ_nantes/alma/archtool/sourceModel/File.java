package fr.univ_nantes.alma.archtool.sourceModel;

public class File
{
    private final String name;

    private final Folder folder;

    public File(final String name, final Folder folder)
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
