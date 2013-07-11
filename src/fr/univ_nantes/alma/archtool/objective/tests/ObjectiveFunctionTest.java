package fr.univ_nantes.alma.archtool.objective.tests;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
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

public class ObjectiveFunctionTest
{
    private static SourceCode sourceCode;

    private static File file;

    private static Function fct1;
    private static Function fct2;

    private static LocalVariable v1;
    private static LocalVariable v2;
    private static LocalVariable v3;

    private static GlobalVariable v4;

    private ObjectiveFunction obj;
    private COA coa;

    @BeforeClass
    public static void setUpBeforeClass()
    {
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
    public void setUp()
    {
        this.coa = new COA(new SourceCode());
        this.obj = new ObjectiveFunction();
    }

    /**
     * Vérifie que la fonction objectif donne le même résultats pour deux
     * architectures dont seul l'ordre des composants change.
     */

    @Test
    public void testEvaluate1()
    {
        Component comp1 = new Component();
        this.coa.addComponent(comp1);
        this.coa.addFunction(fct1, comp1);

        Component comp2 = new Component();
        this.coa.addComponent(comp2);
        this.coa.addFunction(fct1, comp2);

        Interface itf1 = new Interface();
        this.coa.addInterface(itf1);
        this.coa.addFunction(fct1, itf1);

        Interface itf2 = new Interface();
        this.coa.addInterface(itf2);
        this.coa.addFunction(fct2, itf2);

        comp1.addProvidedInterface(itf1);
        comp2.addRequiredInterface(itf2);

        Architecture arch1 = new Architecture();
        arch1.addComponent(comp1);
        arch1.addComponent(comp2);

        Double result1 = this.obj.evaluate(arch1, this.coa);

        Architecture arch2 = new Architecture();
        arch2.addComponent(comp2);
        arch2.addComponent(comp1);

        Double result2 = this.obj.evaluate(arch2, this.coa);

        assertEquals(result1, result2);
        
        System.out.println(result1);
    }

    /**
     * Vérifie que la fonction objectif donne le même résultats pour deux
     * composants dont seul l'ordre des éléments constitutifs change.
     */
    @Test
    public void testEvaluate2()
    {
        Component comp1 = new Component();
        this.coa.addComponent(comp1);
        this.coa.addFunction(fct1, comp1);
        this.coa.addFunction(fct2, comp1);

        Architecture arch1 = new Architecture();
        arch1.addComponent(comp1);

        Double result1 = this.obj.evaluate(arch1, this.coa);

        Component comp2 = new Component();
        this.coa.addComponent(comp2);
        this.coa.addFunction(fct2, comp2);
        this.coa.addFunction(fct1, comp2);

        Architecture arch2 = new Architecture();
        arch2.addComponent(comp2);

        Double result2 = this.obj.evaluate(arch2, this.coa);

        assertEquals(result1, result2);
        
        System.out.println(result1);
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

        return new Function("fct1", PrimitiveType.intType, false, args, body1, file);
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
