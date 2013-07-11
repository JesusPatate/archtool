package fr.univ_nantes.alma.archtool.parsing;

import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCodeMediator;

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
        
        SourceCode sc = builder.getSourceCode();
        SourceCodeMediator m = new SourceCodeMediator();
        m.createRelations(sc.getFunctions(), sc.getTypes(),
                sc.getGlobalVariables());
        
        System.out.println(sc.getFunctions().size());
        
        /*for(Function f : sc.getFunctions())
        {
            System.out.println(f);
            System.out.println(m.getFunctionsCalledBy(f));
            System.out.println();
        }*/
    }
}
