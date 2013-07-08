package fr.univ_nantes.alma.archtool.clustering.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import junitx.util.PrivateAccessor;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.clustering.Dendogram;
import fr.univ_nantes.alma.archtool.clustering.Dendogram.Node;
import fr.univ_nantes.alma.archtool.coa.COA;
import fr.univ_nantes.alma.archtool.sourceModel.Block;
import fr.univ_nantes.alma.archtool.sourceModel.Call;
import fr.univ_nantes.alma.archtool.sourceModel.File;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.LocalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;
import fr.univ_nantes.alma.archtool.sourceModel.Variable;

/**
 * Cas de test avec un code source qui contient uniquement 2 fonctions dont la
 * première est appelée par la seconde.
 */
public class DendogramTest1
{
    private Dendogram dendogram;
    
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
sourceCode = new SourceCode();
        
        file = new File("file");
        v1 = new LocalVariable("x", PrimitiveType.charType());
        v2 = new LocalVariable("v", PrimitiveType.intType());
        v3 = new LocalVariable("y", PrimitiveType.intType());

        v4 = new GlobalVariable("g", PrimitiveType.charType(), false, file);
        sourceCode.addGlobal(v4);
        
        fct1 = createFct1();
        sourceCode.addFunction(fct1);
        
        fct2 = createFct2();
        sourceCode.addFunction(fct2);
        
        Function fct3 = new Function("fct3", PrimitiveType.longType(), false,
                new HashSet<LocalVariable>(), new Block(), new File("file2"));
        
        sourceCode.addFunction(fct3);
        
        fail("Ces tests ne sont plus à jour");
    }
    
    @Before
    public void setUp() throws Exception
    {
        this.dendogram = new Dendogram(DendogramTest1.sourceCode);
    }

    @After
    public void tearDown()
    {
        this.dendogram = null;
    }

    @Test
    public void testInit()
    {
        try
        {
            @SuppressWarnings("unchecked")
            ArrayList<Dendogram.Node> nodes = (ArrayList<Node>)
                    PrivateAccessor.getField(this.dendogram, "nodes");

            assertTrue("Dendogramme vide", nodes.size() != 0);
        }

        catch (NoSuchFieldException e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSize()
    {
        try
        {
            @SuppressWarnings("unchecked")
            ArrayList<Dendogram.Node> nodes = (ArrayList<Node>)
                    PrivateAccessor.getField(this.dendogram, "nodes");

            
            
            assertEquals("Mauvaise taille retournée -",
                    4, this.dendogram.size());
        }

        catch (NoSuchFieldException e)
        {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testExtractFunctions()
    {
        try
        {
            @SuppressWarnings("unchecked")
            ArrayList<Dendogram.Node> nodes = (ArrayList<Node>)
                    PrivateAccessor.getField(this.dendogram, "nodes");

            assertTrue("Nombre de noeuds créés incorrect", nodes.size() == 4);

            Dendogram.Node node1 = nodes.get(0);
            Dendogram.Node node2 = nodes.get(1);

            Architecture arch = this.dendogram.getArchitecture();
            COA coa = this.dendogram.getCOA();
            
            assertEquals(4, arch.getComponents().size());
            
            Iterator<Component> it = arch.getComponents().iterator();
            
            Component comp1 = it.next();
            Component comp2 = it.next();
            
            Set<Function> functions1 = coa.getComponentFunctions(comp1);
            Set<Function> functions2 = coa.getComponentFunctions(comp2);
            
            assertEquals("node1 ne contient pas de fonction -",
                    1, functions1.size());
            assertEquals("node2 ne contient pas de fonction -",
                    1, functions2.size());

            Function fctNode1 = functions1.iterator().next();
            Function fctNode2 = functions2.iterator().next();

            Iterator<Function> it2 = sourceCode.getFunctions().iterator();
            Function fct1 = it2.next();
            Function fct2 = it2.next();

            assertTrue("node1 ne contient ni fct1 ni fct2",
                    (fctNode1.equals(fct1) || (fctNode1.equals(fct2))));
            assertTrue("node2 ne contient ni fct1 ni fct2",
                    fctNode2.equals(fct1) || (fctNode2.equals(fct2)));
        }

        catch (NoSuchFieldException e)
        {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testProcessInterfaces()
    {
        try
        {
            @SuppressWarnings("unchecked")
            ArrayList<Dendogram.Node> nodes = (ArrayList<Node>)
                    PrivateAccessor.getField(this.dendogram, "nodes");
            
            Node node1 = nodes.get(0);
            Node node2 = nodes.get(1);

            Architecture arch = this.dendogram.getArchitecture();
            
            assertEquals(4, arch.getComponents().size());
            
            Iterator<Component> it = arch.getComponents().iterator();
            
            Component comp1 = it.next();
            Component comp2 = it.next();

            Set<Interface> req1 = comp1.getRequiredInterfaces();
            Set<Interface> req2 = comp2.getRequiredInterfaces();
            Set<Interface> pro1 = comp1.getProvidedInterfaces();
            Set<Interface> pro2 = comp2.getProvidedInterfaces();
            
            assertFalse("aucun des 2 noeuds n'a d'interface requise",
                    req1.isEmpty() && req2.isEmpty());
            assertFalse("aucun des 2 noeuds n'a d'interface fournie",
                    pro1.isEmpty() && pro2.isEmpty());
            assertTrue("node1 a des interfaces fournie ET requise",
                    req1.isEmpty() ^ pro1.isEmpty());
            assertTrue("node2 a des interfaces fournie ET requise",
                    req2.isEmpty() ^ pro2.isEmpty());
        }

        catch (NoSuchFieldException e)
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

        Map<GlobalVariable, Integer> globals =
                new HashMap<GlobalVariable, Integer>();
         globals.put(v4, 2);

        Map<LocalVariable, Integer> locals =
                new HashMap<LocalVariable, Integer>();
        locals.put(v2, 1);
        locals.put(v3, 1);

        Block body1 = new Block(new HashSet<Call>(), globals, locals,
                new HashSet<Block>());

        return new Function("fct1", PrimitiveType.intType(), args, body1, file);
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

        return new Function("fct2", PrimitiveType.charType(), args, body2,
                file);
    }
}
