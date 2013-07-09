package fr.univ_nantes.alma.archtool.parsing;

public class SourceCodeBuilderTest
{
    public static void main(String[] args)
    {
        String [] sourceFilePaths = new String []{"/comptes/E071318U/stage/sou/hr/srclib/hrrjou.c"};
        ExtensionFilter sourceFileFilter = new ExtensionFilter(new String [] {".c"});
        String [] includeFilePaths = new String []{"/comptes/E071318U/stage/sou"};
        ExtensionFilter includeFileFilter = new ExtensionFilter(new String [] {".h", ".qhe", ".qhh"});
        
        SourceCodeBuilder builder = new SourceCodeBuilder(sourceFilePaths,
                sourceFileFilter, includeFilePaths, includeFileFilter);
        
        builder.build();
    }
}
