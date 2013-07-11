package fr.univ_nantes.alma.archtool.objective.tests;

import static org.junit.Assert.assertTrue;

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

        Context context =
                new Context(new HashSet<Function>(),
                        new HashSet<ComplexType>(),
                        new HashSet<GlobalVariable>());

        cp.process("/home/gaetan/fac/horoquartz/sou/hr/srclib/hrrjou.c",
                context);

        System.out.println("Parsing completed");
    }

    @Test
    public void testCohesion()
    {
        Class[] classes = { Function.class, Function.class };

        try
        {
            Set<Function> functions = cp.getFunctions();

            Function[] fctArray = new Function[functions.size()];
            functions.toArray(fctArray);

            for (int i = 0; i < functions.size() - 1; ++i)
            {
                for (int j = i + 1; j < functions.size(); ++j)
                {
                    Object[] args = { fctArray[i], fctArray[j] };

                    double result =
                            (Double) PrivateAccessor.invoke(cohesion,
                                    "cohesion", classes, args);
                }
            }
        }

        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testCohesionCalls()
    {
        Class[] classes = { Function.class, Function.class };

        try
        {
            Set<Function> functions = cp.getFunctions();

            Function[] fctArray = new Function[functions.size()];
            functions.toArray(fctArray);

            for (int i = 0; i < functions.size() - 1; ++i)
            {
                for (int j = i + 1; j < functions.size(); ++j)
                {
                    Object[] args = { fctArray[i], fctArray[j] };

                    double result =
                            (Double) PrivateAccessor.invoke(cohesion,
                                    "cohesionCalls", classes, args);

                    assertTrue("Cohésion sur les appels invalide (" + result
                            + ")", result >= 0.0 && result <= 1.0);
                }
            }

            for (int i = 0; i < functions.size() - 1; ++i)
            {
                Function fct = fctArray[i];
                Object[] args = { fct, fct };

                double result =
                        (Double) PrivateAccessor.invoke(cohesion,
                                "cohesionCalls", classes, args);

                if (fct.getCalls().size() > 0)
                {
                    assertTrue("Cohésion sur les appels invalide (" + result
                            + ")", result == 1.0);
                }
                else
                {
                    assertTrue("Cohésion sur les appels invalide (" + result
                            + ")", result == 0.0);
                }
            }
        }

        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testCohesionGlobalVars()
    {
        Class[] classes = { Function.class, Function.class };

        try
        {
            Set<Function> functions = cp.getFunctions();

            Function[] fctArray = new Function[functions.size()];
            functions.toArray(fctArray);

            // for (int i = 0; i < functions.size() - 1; ++i)
            // {
            // for (int j = i + 1; j < functions.size(); ++j)
            // {
            // Object[] args = { fctArray[i], fctArray[j] };
            //
            // double result =
            // (Double) PrivateAccessor.invoke(cohesion,
            // "cohesionGlobalVars", classes, args);
            //
            // assertTrue("Cohésion sur les variables globales invalide ("
            // + result + ")",
            // result >= 0.0 && result <= 1.0);
            // }
            // }

            for (int i = 0; i < functions.size() - 1; ++i)
            {
                Function fct = fctArray[i];
                Object[] args = { fct, fct };

                double result =
                        (Double) PrivateAccessor.invoke(cohesion,
                                "cohesionGlobalVars", classes, args);

                if (fct.getGlobalVariables().size() > 0)
                {
                    assertTrue("Cohésion sur les variables globales invalide ("
                            + result + ")", result == 1.0);
                }
                else
                {
                    assertTrue("Cohésion sur les variables globales invalide ("
                            + fct.getName() + ")", result == 0.0);
                }
            }
        }

        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testCohesionLocalVars()
    {
        Class[] classes = { Function.class, Function.class };

        try
        {
            Set<Function> functions = cp.getFunctions();

            Function[] fctArray = new Function[functions.size()];
            functions.toArray(fctArray);

            for (int i = 0; i < functions.size() - 1; ++i)
            {
                for (int j = i + 1; j < functions.size(); ++j)
                {
                    Object[] args = { fctArray[i], fctArray[j] };

                    double result =
                            (Double) PrivateAccessor.invoke(cohesion,
                                    "cohesionLocalVars", classes, args);

                    assertTrue("Cohésion sur les variables locales invalide ("
                            + result + ")", result >= 0.0 && result <= 1.0);
                }
            }
        }

        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testCohesionTypes()
    {
        Class[] classes = { Function.class, Function.class };

        try
        {
            Set<Function> functions = cp.getFunctions();

            Function[] fctArray = new Function[functions.size()];
            functions.toArray(fctArray);

            for (int i = 0; i < functions.size() - 1; ++i)
            {
                for (int j = i + 1; j < functions.size(); ++j)
                {
                    Object[] args = { fctArray[i], fctArray[j] };

                    double result =
                            (Double) PrivateAccessor.invoke(cohesion,
                                    "cohesionTypes", classes, args);

                    assertTrue("Cohésion sur les types invalide (" + result
                            + ")", result >= 0.0 && result <= 1.0);
                }
            }

            for (int i = 0; i < functions.size() - 1; ++i)
            {
                Function fct = fctArray[i];
                Object[] args = { fct, fct };

                double result =
                        (Double) PrivateAccessor.invoke(cohesion,
                                "cohesionTypes", classes, args);

                if (fct.getUsedTypes().size() > 0)
                {
                    assertTrue("Cohésion sur les types invalide (" + result
                            + ")", result == 1.0);
                }
                else
                {
                    assertTrue("Cohésion sur les types invalide (" + result
                            + ")", result == 0.0);
                }
            }
        }

        catch (Throwable e)
        {
            e.printStackTrace();
        }
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

            for (int i = 0; i < functions.size() - 1; ++i)
            {
                for (int j = i + 1; j < functions.size(); ++j)
                {
                    Object[] args = { fctArray[i], fctArray[j] };

                    double result =
                            (Double) PrivateAccessor.invoke(cohesion,
                                    "cohesionCalls", classes, args);

                    assertTrue("Cohésion sur les arguments invalide",
                            result >= 0.0 && result <= 1.0);
                }
            }

            for (int i = 0; i < functions.size() - 1; ++i)
            {
                Function fct = fctArray[i];

                Object[] args = { fct, fct };

                double result =
                        (Double) PrivateAccessor.invoke(cohesion,
                                "cohesionArguments", classes, args);

                if (fct.getArguments().size() > 0)
                {
                    assertTrue("Cohésion sur les arguments invalide",
                            result == 1.0);
                }
                else
                {
                    assertTrue(
                            "Cohésion sur les arguments invalide ("
                                    + fct.getName() + ")", result == 0.0);
                }
            }
        }

        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
