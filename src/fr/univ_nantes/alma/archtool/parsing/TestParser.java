package fr.univ_nantes.alma.archtool.parsing;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;

public class TestParser
{
    public static void main(String[] args) throws IOException
    {
    	CProcessor cp = new CProcessor();
    	Set<Function> functions = new HashSet<Function>();
    	Set<ComplexType> complexTypes = new HashSet<ComplexType>();
    	Set<GlobalVariable> globalVariables = new HashSet<GlobalVariable>();
    	Context context = new Context(functions, complexTypes, globalVariables);
    	cp.process("Makefile", context);
    	System.out.println(cp.getGlobalVariables());
        
        /*CPreprocessor cpp = new CPreprocessor();
        cpp.process("/comptes/E071318U/dev/projects/archtool/Makefile");
        System.out.println(cpp.getNonStandardIncludes());
        
        cpp.process("/comptes/E071318U/stage/sou/hr/srclib/hrrjou.c");
        System.out.println(cpp.getNonStandardIncludes());*/
    }
}