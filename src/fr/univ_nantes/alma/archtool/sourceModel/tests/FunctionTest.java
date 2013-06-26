package fr.univ_nantes.alma.archtool.sourceModel.tests;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

public class FunctionTest
{
    private Type t1;
    private Function fct1;
    private Set<LocalVariable> args =
            new HashSet<LocalVariable>();
    private Map<LocalVariable, Integer> vars =
            new HashMap<LocalVariable, Integer>();
    private File fic1;
    private Folder folder1;
    private Block body;
    
    @Before
    public void setUp() {
        this.folder1 = new Folder("folder", null);
        this.fic1 = new File("fic", this.folder1);
        
        this.t1 = new ComplexType("type1", this.fic1);
        
        LocalVariable vX = new LocalVariable("x", PrimitiveType.charType());
        LocalVariable vY = new LocalVariable("y", this.t1);
        LocalVariable vZ = new LocalVariable("v", PrimitiveType.intType());
        
        this.args.add(vX);
        this.args.add(vY);

        this.vars.put(vX, 1);
        this.vars.put(vZ, 1);
        
        this.body = new Block(
                new HashSet<Call>(),
                new HashMap<ProgramGlobalVariable, Integer>(),
                new HashMap<FileGlobalVariable, Integer>(),
                this.vars,
                new HashSet<Block>());
        
        fct1 = new Function("fct", args, PrimitiveType.intType(), this.fic1,
                this.body);
    }

    @Test
    public void testGetProgramGlobals()
    {
        assertEquals(this.body.getProgramGlobals(),
                this.fct1.getProgramGlobals());
    }

    @Test
    public void testGetFileGlobals()
    {
        assertEquals(this.body.getFileGlobals(),
                this.fct1.getFileGlobals());
    }

    @Test
    public void testGetLocals()
    {
        assertEquals(this.body.getLocals(),
                this.fct1.getLocals());
    }

    @Test
    public void testGetCalls()
    {
        assertEquals(this.body.getLocals(),
                this.fct1.getLocals());
    }

    @Test
    public void testGetUsedTypes()
    {
        assertEquals(this.body.getUsedTypes(), this.fct1.getUsedTypes());
    }

}
