package fr.univ_nantes.alma.archtool.simulatedAnnealing.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junitx.util.PrivateAccessor;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.objective.ObjectiveFunction;
import fr.univ_nantes.alma.archtool.simulatedAnnealing.SimulatedAnnealing;
import fr.univ_nantes.alma.archtool.sourceModel.Block;
import fr.univ_nantes.alma.archtool.sourceModel.Call;
import fr.univ_nantes.alma.archtool.sourceModel.File;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.LocalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;
import fr.univ_nantes.alma.archtool.sourceModel.Variable;

public class SimulatedAnnealingTest
{
    private static SimulatedAnnealing sim;

    private static SourceCode sourceCode;

    private static File file;

    private static Function fct1;
    private static Function fct2;

    private static LocalVariable v1;
    private static LocalVariable v2;
    private static LocalVariable v3;

    private static GlobalVariable v4;

    @BeforeClass
    public static void setUpBeforeClass()
    {
        sim = new SimulatedAnnealing();
        
        sourceCode = new SourceCode();
        
        file = new File("file");
        v1 = new LocalVariable("x", PrimitiveType.charType);
        v2 = new LocalVariable("v", PrimitiveType.intType);
        v3 = new LocalVariable("y", PrimitiveType.intType);

        v4 = new GlobalVariable("g", PrimitiveType.charType, false, file);
        sourceCode.addGlobalVariable(v4);
        
        fct1 = createFct1();
        sourceCode.addFunction(fct1);
        
        fct2 = createFct2();
        sourceCode.addFunction(fct2);
        
        Function fct3 = new Function("fct3", PrimitiveType.longType, false,
                new HashSet<LocalVariable>(), new Block(), new File("file2"));
        
        sourceCode.addFunction(fct3);
    }

    @Test
    public void testStopCriterion1()
    {
        Class[] classes = { Double.class, Double.class };
        Object[] args = { -12.0, 24.0 };

        try
        {
            int before = (Integer) PrivateAccessor.getField(sim, "chances");

            PrivateAccessor.invoke(sim, "stopCriterion", classes, args);

            int after = (Integer) PrivateAccessor.getField(sim, "chances");

            assertEquals("Le nombre de chances devrait être le même "
                    + "avant et après", before, after);
        }

        catch (Throwable e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void testStopCriterion2()
    {
        Class[] classes = { Double.class, Double.class };
        Object[] args = { 23.95, 24.0 };

        try
        {
            int before = (Integer) PrivateAccessor.getField(sim, "chances");

            PrivateAccessor.invoke(sim, "stopCriterion", classes, args);

            int after = (Integer) PrivateAccessor.getField(sim, "chances");

            assertTrue("Une chance aurait dû être consommée",
                    (after == before - 1));
        }

        catch (Throwable e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAcceptanceFct1()
    {
        try
        {
            PrivateAccessor.setField(sim, "currentScore", 20);

            Class[] classes = { Double.class };
            Object[] args = { 10.0 };

            double result1 = (Double) PrivateAccessor.invoke(sim,
                    "acceptanceFct", classes, args);

            args[0] = 15.0;

            double result2 = (Double) PrivateAccessor.invoke(sim,
                    "acceptanceFct", classes, args);

            assertTrue("Une solution moins bonne n'a pas moins de chances " +
                    "d'être acceptée", (result1 < result2));
        }

        catch (Throwable e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAcceptanceFct2()
    {
        try
        {
            PrivateAccessor.setField(sim, "temperature", 20);

            Class[] classes = { Double.class };
            Object[] args = { 10.0 };

            double result1 = (Double) PrivateAccessor.invoke(sim,
                    "acceptanceFct", classes, args);

            PrivateAccessor.setField(sim, "temperature", 10);

            double result2 = (Double) PrivateAccessor.invoke(sim,
                    "acceptanceFct", classes, args);

            assertTrue("Baisser la température ne fait pas décroître " +
                    "la probabilité d'acceptation d'une même solution",
                    (result1 > result2));
        }

        catch (Throwable e)
        {
            fail(e.getMessage());
        }
    }

    /**
     * Crée la première fonction du code source
     */
    private static Function createFct1()
    {
        Set<LocalVariable> args = new HashSet<LocalVariable>();
        args.add(v1);

        Map<GlobalVariable, Integer> pGlobals =
                new HashMap<GlobalVariable, Integer>();
         pGlobals.put(v4, 2);

        Map<LocalVariable, Integer> locals =
                new HashMap<LocalVariable, Integer>();
        locals.put(v2, 1);
        locals.put(v3, 1);

        Block body1 = new Block(new HashSet<Call>(), pGlobals, locals,
                new HashSet<Block>());

        return new Function("fct1", PrimitiveType.intType, false, args, body1,
                file);
    }

    /**
     * Crée la seconde fonction du code source
     */
    private static Function createFct2()
    {
        Set<LocalVariable> args = new HashSet<LocalVariable>();
        args.add(v1);
        args.add(v2);

        Map<LocalVariable, Integer> locals =
                new HashMap<LocalVariable, Integer>();
        locals.put(v3, 1);

        Map<GlobalVariable, Integer> pGlobals =
                new HashMap<GlobalVariable, Integer>();
        // pGlobals.put(v4, 2);

        Set<Variable> params = new HashSet<Variable>();
        params.add(v1);
        params.add(v2);

        Set<Call> calls = new HashSet<Call>();
        calls.add(new Call(fct1, params));

        Block body2 = new Block(calls, pGlobals, locals, new HashSet<Block>());

        return new Function("fct2", PrimitiveType.charType, false, args, body2,
                file);
    }
}
