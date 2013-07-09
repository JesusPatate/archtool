package fr.univ_nantes.alma.archtool.clustering.tests;

import java.util.HashSet;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.univ_nantes.alma.archtool.clustering.Clustering;
import fr.univ_nantes.alma.archtool.objective.ObjectiveFunction;
import fr.univ_nantes.alma.archtool.parsing.CProcessor;
import fr.univ_nantes.alma.archtool.parsing.Context;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;

public class ClusteringTest2
{
    private static SourceCode sourceCode;

    private static ObjectiveFunction obj;

    private Clustering clustering;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        CProcessor cp = new CProcessor();
        Context context = new Context(new HashSet<Function>(),
                new HashSet<ComplexType>(), new HashSet<GlobalVariable>());
        
        cp.process("/comptes/E10A345H/Fac/horoquartz/sou/hr/srclib/hrrjou.c",
                context);

        System.out.println("Parsing completed");

        sourceCode = new SourceCode();
        sourceCode.addFunctions(cp.getFunctions());
        sourceCode.addGlobals(cp.getGlobalVariables());
        sourceCode.addTypes(cp.getComplexTypes());

        obj = new ObjectiveFunction();
    }

    @Test
    public void test()
    {
        this.clustering = new Clustering(obj);

        this.clustering.process(sourceCode);

        System.out.println(this.clustering.getArchitecture());
        System.out.println(this.clustering.getCOA());

        System.out.println(obj.evaluate(this.clustering.getArchitecture(),
                this.clustering.getCOA()));
    }

}
