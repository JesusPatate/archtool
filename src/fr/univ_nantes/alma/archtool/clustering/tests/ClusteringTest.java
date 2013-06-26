package fr.univ_nantes.alma.archtool.clustering.tests;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univ_nantes.alma.archtool.clustering.Clustering;
import fr.univ_nantes.alma.archtool.objective.ObjectiveFunction;
import fr.univ_nantes.alma.archtool.sourceModel.Block;
import fr.univ_nantes.alma.archtool.sourceModel.Call;
import fr.univ_nantes.alma.archtool.sourceModel.File;
import fr.univ_nantes.alma.archtool.sourceModel.FileGlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Folder;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.LocalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.ProgramGlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;
import fr.univ_nantes.alma.archtool.sourceModel.Variable;

public class ClusteringTest extends TestCase
{
    private Clustering clustering;
    private SourceCode sourceCode;

    @Before
    public void setUp() throws Exception
    {
        ObjectiveFunction obj = new ObjectiveFunction();
        this.clustering = new Clustering(obj);
        
        Folder fold = new Folder("fold", null);
        File file = new File("file", fold);

        LocalVariable v1 = new LocalVariable("x", PrimitiveType.charType());
        LocalVariable v2 = new LocalVariable("v", PrimitiveType.intType());
        
        Function fct1 = createFct1(v1, v2, file);
        Function fct2 = createFct2(v1, v2, file, fct1);

        this.sourceCode = new SourceCode();
        this.sourceCode.addFunction(fct1);
        this.sourceCode.addFunction(fct2);
        
        this.clustering.process(sourceCode);
    }

    @After
    public void tearDown()
    {
        this.clustering = null;
        this.sourceCode = null;
    }

    @Test
    public void test()
    {
        fail("Not yet implemented");
    }

    /**
     * Crée la première fonction du code source
     */
    private Function createFct1(LocalVariable v1, LocalVariable v2, File file)
    {
        Set<LocalVariable> args1 = new HashSet<LocalVariable>();
        Map<LocalVariable, Integer> locals1 =
                new HashMap<LocalVariable, Integer>();
        Map<FileGlobalVariable, Integer> fileGlobals1 =
                new HashMap<FileGlobalVariable, Integer>();

        args1.add(v1);
        locals1.put(v2, 1);

        Block body1 = new Block(new HashSet<Call>(),
                new HashMap<ProgramGlobalVariable, Integer>(),
                fileGlobals1, locals1, new HashSet<Block>());

        return new Function("fct1", args1, PrimitiveType.intType(), file, body1);
    }
    
    /**
     * Crée la seconde fonction du code source
     */
    private Function createFct2(LocalVariable v1, LocalVariable v2, File file,
            Function fct)
    {
        Set<LocalVariable> args2 = new HashSet<LocalVariable>();
        args2.add(v1);
        args2.add(v2);

        Set<Variable> params = new HashSet<Variable>();
        params.add(v1);
        params.add(v2);

        Set<Call> calls = new HashSet<Call>();
        calls.add(new Call(fct, params));

        Block body2 =
                new Block(calls, new HashMap<ProgramGlobalVariable, Integer>(),
                        new HashMap<FileGlobalVariable, Integer>(),
                        new HashMap<LocalVariable, Integer>(),
                        new HashSet<Block>());

        return new Function("fct2", args2, PrimitiveType.charType(), file,
                body2);
    }

}
