package fr.univ_nantes.alma.archtool.clustering.tests;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.clustering.Clustering;
import fr.univ_nantes.alma.archtool.coa.COA;
import fr.univ_nantes.alma.archtool.objective.ObjectiveFunction;
import fr.univ_nantes.alma.archtool.sourceModel.Block;
import fr.univ_nantes.alma.archtool.sourceModel.Call;
import fr.univ_nantes.alma.archtool.sourceModel.File;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.LocalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;
import fr.univ_nantes.alma.archtool.sourceModel.Variable;
import fr.univ_nantes.alma.archtool.utils.Pair;

public class ClusteringTest
{
    private static ObjectiveFunction obj;

    private static SourceCode sourceCode;

    private static File file;

    private static Function fct1;
    private static Function fct2;

    private static LocalVariable v1;
    private static LocalVariable v2;
    private static LocalVariable v3;

    private static GlobalVariable v4;

    private Clustering clustering;

    @BeforeClass
    public static void setUpBeforeClass()
    {
        ClusteringTest.obj = new ObjectiveFunction();

        sourceCode = new SourceCode();
        
        file = new File("file");
        v1 = new LocalVariable("x", PrimitiveType.charType);
        v2 = new LocalVariable("v", PrimitiveType.intType);
        v3 = new LocalVariable("y", PrimitiveType.intType);

        v4 = new GlobalVariable("g", PrimitiveType.charType, false, file);
        sourceCode.addGlobal(v4);
        
        fct1 = createFct1();
        sourceCode.addFunction(fct1);
        
        fct2 = createFct2();
        sourceCode.addFunction(fct2);
        
        Function fct3 = new Function("fct3", PrimitiveType.longType, false,
                new HashSet<LocalVariable>(), new Block(), new File("file2"));
        
        sourceCode.addFunction(fct3);
    }

    @Before
    public void setUp() throws Exception
    {
        this.clustering = new Clustering(ClusteringTest.obj);
    }

    @After
    public void tearDown()
    {
        this.clustering = null;
    }

    @Test
    public void test()
    {

        this.clustering.process(this.sourceCode);

        System.out.println(this.clustering.getArchitecture());
        System.out.println(this.clustering.getCOA());
        
        System.out.println(this.obj.evaluate(this.clustering.getArchitecture(),
                this.clustering.getCOA()));
    }

    /**
     * Crée la première fonction du code source
     */
    private static Function createFct1()
    {
        Set<LocalVariable> args = new HashSet<LocalVariable>();
        args.add(v1);

        Map<GlobalVariable, Integer> globals =
                new HashMap<GlobalVariable, Integer>();
         globals.put(v4, 2);

        Map<LocalVariable, Integer> locals =
                new HashMap<LocalVariable, Integer>();
        locals.put(v2, 1);
        locals.put(v3, 1);

        Block body1 = new Block(new HashSet<Call>(), globals, locals,
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

        Map<GlobalVariable, Integer> globals =
                new HashMap<GlobalVariable, Integer>();
        // pGlobals.put(v4, 2);

        Set<Variable> params = new HashSet<Variable>();
        params.add(v1);
        params.add(v2);

        Set<Call> calls = new HashSet<Call>();
        calls.add(new Call(fct1, params));

        Block body2 = new Block(calls, globals, locals, new HashSet<Block>());

        return new Function("fct2", PrimitiveType.charType, false, args, body2,
                file);
    }
}
