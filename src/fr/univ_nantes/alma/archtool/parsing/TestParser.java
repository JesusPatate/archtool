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
    	/*CProcessor cp = new CProcessor();
    	Set<Function> functions = new HashSet<Function>();
    	Set<ComplexType> complexTypes = new HashSet<ComplexType>();
    	Set<GlobalVariable> globalVariables = new HashSet<GlobalVariable>();
    	cp.process("/comptes/E071318U/stage/sou/common/include/libtpt_cmn.h", 
    	        new Context());
    	System.out.println(cp.getFunctions());*/
        
        CPreprocessor cpp = new CPreprocessor();
        cpp.process("/comptes/E071318U/stage/sou/hr/include/libtpt_hr.h");
        //System.out.println(cpp.getNonStandardIncludes());
        
        /*cpp.reset();
        
        cpp.process("/comptes/E071318U/stage/sou/qhe/hrdabs1.qhe");
        System.out.println(cpp.getNonStandardIncludes());*/
    }
}