package fr.univ_nantes.alma.archtool.parsing;

public class FileSystemScannerTest
{
    public static void main(String[] args)
    {
        String [] paths = new String [] { "/comptes/E071318U/stage/sou"};
        String [] extensions = new String [] { ".h", ".qhe"};
        
        FileSystemScanner fss = new FileSystemScanner(paths, extensions);
        fss.scann();
        
        System.out.println(fss.getFilesFound().values().size());
    }
}
