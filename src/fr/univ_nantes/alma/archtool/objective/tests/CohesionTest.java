package fr.univ_nantes.alma.archtool.objective.tests;

import java.util.HashSet;
import java.util.Set;

import junitx.util.PrivateAccessor;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.univ_nantes.alma.archtool.objective.Cohesion;
import fr.univ_nantes.alma.archtool.parsing.CProcessor;
import fr.univ_nantes.alma.archtool.parsing.Context;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;

public class CohesionTest
{
    private static Cohesion cohesion = new Cohesion(null);
    private static CProcessor cp = new CProcessor();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {

        Context context = new Context(
                new HashSet<Function>(),
                new HashSet<ComplexType>(),
                new HashSet<GlobalVariable>());

        cp.process("/comptes/E10A345H/Fac/horoquartz/sou/hr/srclib/hrrjou.c",
                context);
        
        System.out.println("Parsing completed");
    }

    @Test
    public void testCohesionArguments()
    {
        Class[] classes = { Function.class, Function.class };

        try
        {
            Set<Function> functions = cp.getFunctions();

            Function[] fctArray = new Function[functions.size()];
            functions.toArray(fctArray);

            for (int i = 0 ; i < functions.size() - 1 ; ++i)
            {
                for (int j = i ; j < functions.size() ; ++j)
                {
                    Object[] args = {fctArray[i], fctArray[j] };

                    double result = (Double) PrivateAccessor.invoke(
                            cohesion, "cohesion", classes, args);
                }
            }
        }

        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
