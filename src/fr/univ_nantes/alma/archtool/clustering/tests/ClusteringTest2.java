package fr.univ_nantes.alma.archtool.clustering.tests;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.univ_nantes.alma.archtool.clustering.Clustering;
import fr.univ_nantes.alma.archtool.objective.ObjectiveFunction;
import fr.univ_nantes.alma.archtool.parsing.ExtensionFilter;
import fr.univ_nantes.alma.archtool.parsing.SourceCodeBuilder;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;

public class ClusteringTest2
{
    private static SourceCode sourceCode;

    private static ObjectiveFunction obj;

    private Clustering clustering;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
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
        
        sourceCode = builder.getSourceCode();
    }

    @Test
    public void test()
    {
        this.clustering = new Clustering();

        this.clustering.process(sourceCode);

        System.out.println(this.clustering.getArchitecture());
        System.out.println(obj.evaluate(this.clustering.getArchitecture()));
    }

}
