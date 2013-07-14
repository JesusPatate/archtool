package fr.univ_nantes.alma.archtool.parsing;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;

public class TestParser
{
    public static void main(String[] args) throws IOException
    {
    	/*CProcessor cp = new CProcessor();
    	cp.process("/home/stan/Documents/development/sou/hr/srclib/hrrjou.c", 
    	        new Context());
    	Set<Function> functions = cp.getFunctions();
    	
    	for(Function f : functions)
    	{
    	    System.out.println(f);
    	}*/
        
        
        /*CPreprocessor cpp = new CPreprocessor();
        cpp.process("/comptes/E071318U/stage/sou/hr/include/libtpt_hr.h");*/
        //System.out.println(cpp.getNonStandardIncludes());
        
        /*cpp.reset();
        
        cpp.process("/comptes/E071318U/stage/sou/qhe/hrdabs1.qhe");
        System.out.println(cpp.getNonStandardIncludes());*/
        
        CharStream s = new ANTLRFileStream("/home/stan/Documents/development/sou/hr/srclib/hrrjou.c");
        CPreprocessorLexer lexer = new CPreprocessorLexer(s);
        lexer.getToken();
        
        /*CLexer cl = new CLexer(lexer.getInputStream());
        CommonTokenStream tokens = new CommonTokenStream(cl);
        CParser parser = new CParser(tokens);
        parser.setContext(new Context());
        parser.compilationUnit();*/
    }
}