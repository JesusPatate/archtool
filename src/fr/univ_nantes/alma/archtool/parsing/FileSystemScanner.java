package fr.univ_nantes.alma.archtool.parsing;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileSystemScanner
{
    private Set<String> paths;
    private FilenameFilter filter;
    private Map<String, Set<String>> pathsFoundByName = 
            new HashMap<String, Set<String>>();
    
    public FileSystemScanner(String [] paths, FilenameFilter filter)
    {
        this.paths = new HashSet<String>(Arrays.asList(paths));
        this.filter = filter;
    }
    
    public FileSystemScanner(Set<String> paths, FilenameFilter filter)
    {
        this.paths = paths;
        this.filter = filter;
    }
    
    public Map<String, Set<String>> getPathsFoundByName()
    {
        return new HashMap<String, Set<String>>(this.pathsFoundByName);
    }
    
    public Set<String> getPathsFound()
    {
        Set<String> pathsFound = new HashSet<String>();
        
        for(Set<String> paths : this.pathsFoundByName.values())
        {
            pathsFound.addAll(paths);
        }
        
        return pathsFound;
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
        this.pathsFoundByName.clear();
    }
    
    private void scannPath(String path)
    {
        File file  = new File(path);
        
        if(file.isFile())
        {            
            if(this.filter.accept(file.getParentFile(), file.getName()))
            {
                this.addFoundFile(file.getName(), file.getAbsolutePath());
            }
        }
        else
        {
            for(File child : file.listFiles(filter))
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
    
    private void scannDirectory(File directory)
    {        
        for(File child : directory.listFiles(filter))
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
        if(this.pathsFoundByName.containsKey(name))
        {
            this.pathsFoundByName.get(name).add(path);
        }
        else
        {
            Set<String> paths = new HashSet<String>();
            paths.add(path);
            this.pathsFoundByName.put(name, paths);
        }
    }
}
