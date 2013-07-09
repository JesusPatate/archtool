package fr.univ_nantes.alma.archtool.parsing;

public class SourceCodeBuilderTest
{
    public static void main(String[] args)
    {
        String [] sourceFilePaths = 
                new String []{"/home/stan/Documents/development/sou/hr/srclib/"};
        ExtensionFilter sourceFileFilter = 
                new ExtensionFilter(new String [] {".c"});        
        SourceCodeBuilder builder = new SourceCodeBuilder(sourceFilePaths,
                sourceFileFilter);
        builder.build();
    }
}
