package fr.univ_nantes.alma.archtool.parsing;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;

public class SourceCodeBuilder 
{
	private CProcessor cProcessor = new CProcessor();
	private FileSystemScanner sourcesScanner;
	private SourceCode sourceCode = new SourceCode();
	private List<String> paths = null;
	
	public SourceCodeBuilder(Set<String> sourceFilePaths,
			ExtensionFilter sourceFileFilter)
	{
	    this.sourcesScanner = 
	            new FileSystemScanner(sourceFilePaths, sourceFileFilter);
	}
	
	public SourceCodeBuilder(List<String> paths)
	{
	    this.paths = paths;
	}
	
	public SourceCodeBuilder(String [] sourceFilePaths,
			ExtensionFilter sourceFileFilter)
	{
	    this.sourcesScanner = 
                new FileSystemScanner(sourceFilePaths, sourceFileFilter);
	}
	
	public void build()
	{
		Collection<String> sourceFiles = null;
		
		if(this.paths != null)
		{
		    sourceFiles = this.paths;
		}
		else
		{
		    this.sourcesScanner.scann();
		    sourceFiles = this.sourcesScanner.getPathsFound();
		}
		
		Context context = new Context();
		
		for(String sourceFile : sourceFiles)
		{			
			try
			{    
			    System.out.println("starting parse source: " + sourceFile);
			    this.cProcessor.process(sourceFile, context);
			    context = context.merge(this.cProcessor.getContext());
			    System.out.println("finishing parse source: " + sourceFile);
			    this.sourceCode.addFunctions(this.cProcessor.getFunctions());
			    this.sourceCode.addComplexTypes(
			            this.cProcessor.getComplexTypes());
			    this.sourceCode.addGlobalVariables(
			            this.cProcessor.getGlobalVariables());
			}
			catch(IOException e)
			{
			    System.out.println("CProcessor : \"" + sourceFile +
	                    "\", impossible to process.");
			}
		}
	}
	
	public SourceCode getSourceCode()
	{
		return this.sourceCode;
	}

}
