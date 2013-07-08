package fr.univ_nantes.alma.archtool.objective.tests;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.univ_nantes.alma.archtool.objective.Cohesion;
import fr.univ_nantes.alma.archtool.sourceModel.File;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.LocalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.ProgramGlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;

public class CohesionTest
{
    private static Cohesion cohesion;

    private static SourceCode sourceCode;

    private static File file;

    private static Function fct1;
    private static Function fct2;

    private static LocalVariable localVar1;
    private static LocalVariable localVar2;
    private static LocalVariable localVar3;
    private static LocalVariable localVar4;
    private static LocalVariable localVar5;
    private static LocalVariable localVar6;

    private static ProgramGlobalVariable globalVar1;
    private static ProgramGlobalVariable globalVar2;
    private static ProgramGlobalVariable globalVar3;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        file = new File("file");

        localVar1 = new LocalVariable("localVar1", PrimitiveType.charType());
        localVar2 = new LocalVariable("localVar2", PrimitiveType.intType());
        localVar3 = new LocalVariable("localVar3", PrimitiveType.intType());
        localVar4 = new LocalVariable("localVar4", PrimitiveType.charType());
        localVar5 = new LocalVariable("localVar5", PrimitiveType.intType());
        localVar6 = new LocalVariable("localVar6", PrimitiveType.intType());

        globalVar1 = new ProgramGlobalVariable("g", PrimitiveType.charType(), file);
        globalVar2 = new ProgramGlobalVariable("g", PrimitiveType.charType(), file);
        globalVar3 = new ProgramGlobalVariable("g", PrimitiveType.charType(), file);
    }
    
    @Test
    public void testCohesionArguments()
    {
        
    }
}