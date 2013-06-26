package fr.univ_nantes.alma.archtool.sourceModel.tests;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.univ_nantes.alma.archtool.sourceModel.Block;
import fr.univ_nantes.alma.archtool.sourceModel.Call;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.File;
import fr.univ_nantes.alma.archtool.sourceModel.FileGlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Folder;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.LocalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.ProgramGlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Type;
import fr.univ_nantes.alma.archtool.sourceModel.Variable;

public class FunctionTest
{
    private Type t1;

    private Function fct1;

    private Function fct2;

    private ProgramGlobalVariable vPG;

    private FileGlobalVariable vFG;

    private Set<LocalVariable> args1;

    private Set<LocalVariable> args2;

    private Map<LocalVariable, Integer> locals1;

    private Map<LocalVariable, Integer> locals2;

    private Map<FileGlobalVariable, Integer> fileGlobals1;

    private Map<FileGlobalVariable, Integer> fileGlobals2;

    private Map<ProgramGlobalVariable, Integer> programGlobals1;

    private Map<ProgramGlobalVariable, Integer> programGlobals2;

    private File fic1;

    private File fic2;

    private Folder folder;

    private Block body1;

    private Block body2;

    private Set<Variable> params;

    private Set<Call> calls;

    @Before
    public void setUp()
    {
        this.folder = new Folder("folder", null);
        this.fic1 = new File("fic", this.folder);
        this.t1 = new ComplexType("type1", this.fic1);
        this.vPG = new ProgramGlobalVariable("p", this.t1, this.fic2);

        this.fct1 = this.createFct1();
        this.fct2 = this.createFct2();
    }

    @Test
    public void testGetProgramGlobals()
    {
        Assert.assertEquals(this.programGlobals1, this.fct1.getProgramGlobals());
    }

    @Test
    public void testGetFileGlobals()
    {
        Assert.assertEquals(this.fileGlobals1, this.fct1.getFileGlobals());
    }

    @Test
    public void testGetLocals()
    {
        Assert.assertEquals(this.locals1, this.fct1.getLocals());
    }

    @Test
    public void testGetCalls()
    {
        Assert.assertEquals(this.locals1,
                this.fct1.getLocals());
    }

    @Test
    public void testCalls()
    {
        assertTrue(this.fct2.calls(this.fct1));
    }

    @Test
    public void testGetUsedTypes()
    {
        Assert.assertEquals(this.body1.getUsedTypes(), this.fct1.getUsedTypes());
    }

    /**
     * Crée la première fonction du code source
     */
    private Function createFct1()
    {
        this.args1 = new HashSet<LocalVariable>();
        this.locals1 = new HashMap<LocalVariable, Integer>();
        this.fileGlobals1 = new HashMap<FileGlobalVariable, Integer>();
        this.programGlobals1 = new HashMap<ProgramGlobalVariable, Integer>();

        final LocalVariable vLoc1 = new LocalVariable("x",
                PrimitiveType.charType());
        final LocalVariable vLoc2 = new LocalVariable("y", this.t1);
        final LocalVariable vLoc3 = new LocalVariable("v",
                PrimitiveType.intType());

        this.args1.add(vLoc1);
        this.args1.add(vLoc2);

        this.locals1.put(vLoc1, 1);
        this.locals1.put(vLoc3, 1);

        this.body1 = new Block(
                new HashSet<Call>(),
                this.programGlobals1,
                this.fileGlobals1,
                this.locals1,
                new HashSet<Block>());

        return new Function("fct", this.args1, PrimitiveType.intType(),
                this.fic1,
                this.body1);
    }

    /**
     * Crée la seconde fonction du code source
     */
    private Function createFct2()
    {
        final LocalVariable vLoc1 = new LocalVariable("a",
                PrimitiveType.charType());
        final LocalVariable vLoc2 = new LocalVariable("b",
                PrimitiveType.intType());

        this.args2 = new HashSet<LocalVariable>();
        this.locals2 = new HashMap<LocalVariable, Integer>();
        this.fileGlobals2 = new HashMap<FileGlobalVariable, Integer>();
        this.programGlobals2 = new HashMap<ProgramGlobalVariable, Integer>();

        this.locals2.put(vLoc1, 2);
        this.locals2.put(vLoc2, 3);

        this.programGlobals2.put(this.vPG, 2);

        this.params = new HashSet<Variable>();
        this.params.add(vLoc1);
        this.params.add(this.vPG);

        this.calls = new HashSet<Call>();
        this.calls.add(new Call(this.fct1, this.params));

        this.body2 =
                new Block(this.calls, this.programGlobals2,
                        this.fileGlobals2,
                        this.locals2);

        return new Function("fct2", this.args2, PrimitiveType.charType(),
                this.fic1,
                this.body2);
    }
}
