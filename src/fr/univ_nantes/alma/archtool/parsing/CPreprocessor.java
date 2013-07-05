package fr.univ_nantes.alma.archtool.parsing;

import java.io.IOException;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class CPreprocessor
{
    private CPreprocessorLexer lexer = new CPreprocessorLexer(null);
    private CPreprocessorParser parser = 
            new CPreprocessorParser(new CommonTokenStream(this.lexer));
    
    public void process(String filename) throws IOException
    {
        CharStream stream = new ANTLRFileStream(filename);
        this.lexer.setInputStream(stream);
        this.parser.reset();
        this.parser.preprocessingFile();
    }
    
    public Set<String> getNonStandardIncludes()
    {
        return this.parser.getNonStandardIncludes();
    }
    
    public void reset()
    {
        this.parser.cleanUp();
    }
}
