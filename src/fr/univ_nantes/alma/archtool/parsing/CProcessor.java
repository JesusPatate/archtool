package fr.univ_nantes.alma.archtool.parsing;

import java.io.IOException;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.File;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;

public class CProcessor 
{
	private CLexer lexer = new CLexer(null);
    private CParser parser = new CParser(null);
	
    public void process(String filename, Context context) throws IOException
    {
        CharStream stream = new ANTLRFileStream(filename);
        this.lexer.setInputStream(stream);
        this.parser.setTokenStream(new CommonTokenStream(this.lexer));
        this.parser.setCurrentFile(new File(filename));
        this.parser.setContext(context);
        this.parser.compilationUnit();
    }
    
    public void reset()
    {
        this.parser.cleanUp();
    }
    
    public Set<Function> getFunctions()
    {
    	return this.parser.getFunctions();
    }
    
    public Set<ComplexType> getComplexTypes()
    {
    	return this.parser.getComplexTypes();
    }
    
    public Set<GlobalVariable> getGlobalVariables()
    {
    	return this.parser.getGlobalVariables();
    }
}