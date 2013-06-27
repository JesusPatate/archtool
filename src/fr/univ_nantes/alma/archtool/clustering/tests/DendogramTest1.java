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

import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.clustering.Dendogram;
import fr.univ_nantes.alma.archtool.clustering.Dendogram.Node;
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

/**
 * Cas de test avec un code source qui contient uniquement 2 fonctions dont la
 * première est appelée par la seconde.
 */
public class DendogramTest1
{
    private static SourceCode sourceCode;

    private Dendogram dendogram;
    
    @BeforeClass
    public static void setUpBeforeClass()
    {
        Folder fold = new Folder("fold", null);
        File file = new File("file", fold);
        
        LocalVariable v1 = new LocalVariable("x", PrimitiveType.charType());
        LocalVariable v2 = new LocalVariable("v", PrimitiveType.intType());

        Function fct1 = DendogramTest1.createFct1(v1, v2, file);
        Function fct2 = DendogramTest1.createFct2(v1, v2, file, fct1);

        DendogramTest1.sourceCode = new SourceCode();
        DendogramTest1.sourceCode.addFunction(fct1);
        DendogramTest1.sourceCode.addFunction(fct2);
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

            assertEquals("Mauvaise taille retournée -", nodes.size(),
                    this.dendogram.size());
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

            assertTrue("Nombre de noeuds créés incorrect", nodes.size() == 2);

            Dendogram.Node node1 = nodes.get(0);
            Dendogram.Node node2 = nodes.get(1);

            Component comp1 = node1.getComponent();
            Component comp2 = node2.getComponent();

            Set<Function> functions1 = comp1.getFunctions();
            Set<Function> functions2 = comp2.getFunctions();

            assertTrue("node1 ne contient pas de fonction",
                    functions1.size() == 1);
            assertTrue("node2 ne contient pas de fonction",
                    functions2.size() == 1);

            Function fctNode1 = functions1.iterator().next();
            Function fctNode2 = functions2.iterator().next();

            Iterator<Function> it = sourceCode.getFunctions().iterator();
            Function f1 = it.next();
            Function f2 = it.next();

            assertTrue("la fonction de node1 est différente de fct1",
                    fctNode1.equals(f1));
            assertTrue("la fonction de node2 est différente de fct2",
                    fctNode2.equals(f2));
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

            Component comp1 = node1.getComponent();
            Component comp2 = node2.getComponent();

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

    @Test
    public void testClusterNodes()
    {
        try
        {
            Node clusterNode = this.dendogram.clusterNodes(0, 1);

            @SuppressWarnings("unchecked")
            ArrayList<Dendogram.Node> nodes = (ArrayList<Node>)
                    PrivateAccessor.getField(this.dendogram, "nodes");

            Node node1 = nodes.get(0);
            Node node2 = nodes.get(1);

            assertTrue("leftChild différent de node1",
                    node1.equals(clusterNode.getLeftChild()));
            assertTrue("rightChild différent de node2",
                    node2.equals(clusterNode.getRightChild()));

            Component comp1 = node1.getComponent();
            Component comp2 = node2.getComponent();
            Component clusterComp = clusterNode.getComponent();

            assertEquals("Nombre de fonctions dans le cluster incorrect",
                    clusterComp.getFunctions().size(),
                    comp1.getFunctions().size() + comp2.getFunctions().size());

            assertTrue(
                    "Le cluster ne contient pas toutes les fonctions de node1",
                    clusterComp.getFunctions()
                            .containsAll(comp1.getFunctions()));

            assertTrue(
                    "Le cluster ne contient pas toutes les fonctions de node2",
                    clusterComp.getFunctions()
                            .containsAll(comp2.getFunctions()));
        }

        catch (NoSuchFieldException e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void testInsertClusterNode()
    {
        try
        {
            Node clusterNode = this.dendogram.clusterNodes(0, 1);

            @SuppressWarnings("unchecked")
            ArrayList<Dendogram.Node> nodesBefore = (ArrayList<Node>)
                    PrivateAccessor.getField(this.dendogram, "nodes");

            Node node1 = nodesBefore.get(0);
            Node node2 = nodesBefore.get(1);

            this.dendogram.insertClusterNode(clusterNode);

            @SuppressWarnings("unchecked")
            ArrayList<Dendogram.Node> nodesAfter = (ArrayList<Node>)
                    PrivateAccessor.getField(this.dendogram, "nodes");

            assertEquals(nodesAfter.size(), 1);

            assertFalse(nodesAfter.contains(node1));
            assertFalse(nodesAfter.contains(node2));

            Node node = nodesAfter.get(0);

            assertTrue(clusterNode.getComponent().equals(node.getComponent()));
        }

        catch (NoSuchFieldException e)
        {
            fail(e.getMessage());
        }
    }

    /**
     * Crée la première fonction du code source
     */
    private static Function createFct1(LocalVariable v1, LocalVariable v2, File file)
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
    private static Function createFct2(LocalVariable v1, LocalVariable v2, File file,
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
