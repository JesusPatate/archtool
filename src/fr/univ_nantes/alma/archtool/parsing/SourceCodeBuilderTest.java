package fr.univ_nantes.alma.archtool.parsing;

public class SourceCodeBuilderTest
{
    public static void main(String[] args)
    {
        String root = "/home/stan/Documents/development/";
        
        String [] sourceFilePaths = 
                new String []{root + "sou/hr/srclib/hrrjou.c",
                root + "sou/hr/srclib/hrract.c",
                root + "sou/hr/srclib/hrrhor.c",
                root + "sou/hr/srclib/hrrcho.c",
                root + "sou/hr/srclib/hrrplc.c",
                root + "sou/hr/srclib/hrrpro.c",
                root + "sou/hr/srclib/hrrseh.c"};
        ExtensionFilter sourceFileFilter = 
                new ExtensionFilter(new String [] {".c"});        
        SourceCodeBuilder builder = new SourceCodeBuilder(sourceFilePaths,
                sourceFileFilter);
        builder.build();
    }
}
