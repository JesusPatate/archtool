package fr.univ_nantes.alma.archtool.parsing;

import java.io.FilenameFilter;

public class FileSystemScannerTest
{
    public static void main(String[] args)
    {
        String [] paths = new String [] { "/comptes/E071318U/stage/sou"};
        String [] extensions = new String [] { ".h", ".qhe"};
        FilenameFilter filter = new ExtensionFilter(extensions);
        
        FileSystemScanner fss = new FileSystemScanner(paths, filter);
        fss.scann();
        
        System.out.println(fss.getPathsFoundByName().values());
    }
}
