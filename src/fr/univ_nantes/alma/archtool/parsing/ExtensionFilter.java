package fr.univ_nantes.alma.archtool.parsing;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import fr.univ_nantes.alma.archtool.utils.FileUtils;

public class ExtensionFilter implements FilenameFilter
{
    private Set<String> allowedExtensions;
    
    public ExtensionFilter(String [] allowedExtensions)
    {
        this.allowedExtensions = new HashSet<String>(Arrays.asList(
                allowedExtensions));
    }
    
    public ExtensionFilter(Set<String> allowedExtensions)
    {
        this.allowedExtensions = allowedExtensions;
    }
    
    @Override
    public boolean accept(File directory, String name)
    {               
        boolean accepted = true;
        File file = new File(directory, name);
        
        if(file.isFile())
        {
            accepted = this.allowedExtensions.contains(
                    FileUtils.getExtension(name));
        }
        
        return accepted;
    }
}