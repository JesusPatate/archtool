package fr.univ_nantes.alma.archtool.parsing;

import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;

public class SourceCodeBuilderTest
{
    public static void main(String[] args)
    {
        String root = "/home/stan/Documents/development/";
        
        String [] sourceFilePaths = 
                new String []{/*root + "sou/hr/srclib/hrrjou.c",
                root + "sou/hr/srclib/hrract.c",
                root + "sou/hr/srclib/hrrhor.c",
                root + "sou/hr/srclib/hrrcho.c",*/
                root + "sou/hr/srclib/hrrplc.c"/*,
                root + "sou/hr/srclib/hrrpro.c",
                root + "sou/hr/srclib/hrrseh.c"*/};
        ExtensionFilter sourceFileFilter = 
                new ExtensionFilter(new String [] {".c"});        
        SourceCodeBuilder builder = new SourceCodeBuilder(sourceFilePaths,
                sourceFileFilter);
        builder.build();
        
        SourceCode sc = builder.getSourceCode();
        sc.optimizeRelations();
        
        System.out.println(sc.getTypes().size());
        
        for(Function f : sc.getTotalFunctions())
        {
            System.out.println(f);
            System.out.println(sc.getTotalFunctionsCalledBy(f));
            System.out.println();
        }
    }
}
