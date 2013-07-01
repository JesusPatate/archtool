package fr.univ_nantes.alma.archtool.parsing;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class TestParser
{
    public static void main(String[] args) throws IOException
    {
        CharStream s = new ANTLRFileStream("/comptes/E071318U/stage/sou/hr/srclib/hrrjou.c");
        CLexer lexer = new CLexer(s);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CParser parser = new CParser(tokens);
        parser.compilationUnit();
        
        /*DeclarationSpecifier ds = new TypedefSpecifier();
        DeclarationSpecifier ds2 = new TypedefNameSpecifier();
        System.out.println(ds.merge(ds2));*/
    } 
}