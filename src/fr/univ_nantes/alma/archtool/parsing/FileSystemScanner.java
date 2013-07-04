package fr.univ_nantes.alma.archtool.parsing;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.utils.FileUtils;

public class FileSystemScanner
{
    private Set<String> paths;
    private Set<String> allowedExtensions;
    private Map<String, Set<String>> filesFound = 
            new HashMap<String, Set<String>>();
    
    public FileSystemScanner(String [] paths, String [] extensions)
    {
        this.paths = new HashSet<String>(Arrays.asList(paths));
        this.allowedExtensions = new HashSet<String>(Arrays.asList(extensions));
    }
    
    public FileSystemScanner(Set<String> paths, Set<String> extensions)
    {
        this.paths = paths;
        this.allowedExtensions = extensions;
    }
    
    public Map<String, Set<String>> getFilesFound()
    {
        return new HashMap<String, Set<String>>(this.filesFound);
    }
    
    public void scann()
    {
        for(String path : this.paths)
        {
            this.scannPath(path);
        }
    }
    
    public void reset()
    {
        this.filesFound.clear();
    }
    
    private void scannPath(String path)
    {
        File file  = new File(path);
        
        if(file.isFile())
        {
            String extension = FileUtils.getExtension(file.getName());
            
            if(this.allowedExtensions.contains(extension))
            {
                this.addFoundFile(file.getName(), file.getAbsolutePath());
            }
        }
        else
        {
            File [] children = file.listFiles(
                    new ExtensionFilter(allowedExtensions));
            
            for(File child : children)
            {
                if(child.isFile())
                {
                    this.addFoundFile(child.getName(), child.getAbsolutePath());
                }
                else
                {
                    this.scannDirectory(child);
                }
            }
        }
    }
    
    private void scannDirectory(File Directory)
    {
        File [] children = Directory.listFiles(new ExtensionFilter(
                allowedExtensions));
        
        for(File child : children)
        {
            if(child.isFile())
            {
                this.addFoundFile(child.getName(), child.getAbsolutePath());
            }
            else
            {
                this.scannDirectory(child);
            }
        }

    }
    
    private void addFoundFile(String name, String path)
    {
        if(this.filesFound.containsKey(name))
        {
            this.filesFound.get(name).add(path);
        }
        else
        {
            Set<String> paths = new HashSet<String>();
            paths.add(path);
            this.filesFound.put(name, paths);
        }
    }
}
