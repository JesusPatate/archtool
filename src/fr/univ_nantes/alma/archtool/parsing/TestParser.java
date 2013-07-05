package fr.univ_nantes.alma.archtool.parsing;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class TestParser
{
    public static void main(String[] args) throws IOException
    {
        CharStream s = new ANTLRFileStream("/comptes/E071318U/dev/projects/archtool/Makefile");
        CLexer lexer = new CLexer(s);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CParser parser = new CParser(tokens);
        parser.compilationUnit();
        
        /*CPreprocessor cpp = new CPreprocessor();
        cpp.process("/comptes/E071318U/dev/projects/archtool/Makefile");
        System.out.println(cpp.getNonStandardIncludes());
        
        cpp.process("/comptes/E071318U/stage/sou/hr/srclib/hrrjou.c");
        System.out.println(cpp.getNonStandardIncludes());*/
    }
}