package fr.univ_nantes.alma.archtool.utils;

public class FileUtils
{
    public static String getExtension(String filename)
    {
        String extension = "";
        int dotIndex = filename.lastIndexOf(".");
        
        if(dotIndex != -1)
        {
            extension = filename.substring(dotIndex);
        }
        
        return extension;
    }
}
