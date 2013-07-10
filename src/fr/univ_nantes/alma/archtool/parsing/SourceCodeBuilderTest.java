package fr.univ_nantes.alma.archtool.parsing;

public class SourceCodeBuilderTest
{
    public static void main(String[] args)
    {
        String [] sourceFilePaths = 
                new String []{"/comptes/E071318U/stage/sou/hr/srclib/hrrjou.c",
        		"/comptes/E071318U/stage/sou/hr/srclib/hrract.c",
        		"/comptes/E071318U/stage/sou/hr/srclib/hrrhor.c",
        		"/comptes/E071318U/stage/sou/hr/srclib/hrrcho.c",
        		"/comptes/E071318U/stage/sou/hr/srclib/hrrplc.c",
        		"/comptes/E071318U/stage/sou/hr/srclib/hrrpro.c",
        		"/comptes/E071318U/stage/sou/hr/srclib/hrrseh.c"};
        ExtensionFilter sourceFileFilter = 
                new ExtensionFilter(new String [] {".c"});        
        SourceCodeBuilder builder = new SourceCodeBuilder(sourceFilePaths,
                sourceFileFilter);
        builder.build();
    }
}
