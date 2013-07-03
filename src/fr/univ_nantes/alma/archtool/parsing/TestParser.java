package fr.univ_nantes.alma.archtool.parsing;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import fr.univ_nantes.alma.archtool.parsing.specifier.DeclarationSpecifier;
import fr.univ_nantes.alma.archtool.parsing.specifier.TypedefNameSpecifier;
import fr.univ_nantes.alma.archtool.parsing.specifier.TypedefSpecifier;

public class TestParser
{
    public static void main(String[] args) throws IOException
    {
        CharStream s = new ANTLRFileStream("/comptes/E071318U/stage/sou/hr/srclib/hrrjou.c");
        CLexer lexer = new CLexer(s);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CParser parser = new CParser(tokens);
        parser.compilationUnit();
    } 
}