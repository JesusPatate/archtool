package fr.univ_nantes.alma.archtool.parsing;

import java.io.IOException;
import java.util.Set;

import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;

public class SourceCodeBuilder 
{
	private CProcessor cProcessor = new CProcessor();
	private FileSystemScanner sourcesScanner;
	private SourceCode sourceCode = new SourceCode();
	
	public SourceCodeBuilder(Set<String> sourceFilePaths,
			ExtensionFilter sourceFileFilter)
	{
	    this.sourcesScanner = 
	            new FileSystemScanner(sourceFilePaths, sourceFileFilter);
	}
	
	public SourceCodeBuilder(String [] sourceFilePaths,
			ExtensionFilter sourceFileFilter)
	{
	    this.sourcesScanner = 
                new FileSystemScanner(sourceFilePaths, sourceFileFilter);
	}
	
	public void build()
	{
		this.sourcesScanner.scann();	
		Set<String> sourceFiles = this.sourcesScanner.getPathsFound();
		
		for(String sourceFile : sourceFiles)
		{			
			try
			{    
			    System.out.println("starting parse source: " + sourceFile);
			    this.cProcessor.process(sourceFile, new Context());
			    System.out.println("finishing parse source: " + sourceFile);
			    this.sourceCode.addFunctions(this.cProcessor.getFunctions());
			    this.sourceCode.addTypes(this.cProcessor.getComplexTypes());
			    this.sourceCode.addGlobals(this.cProcessor.getGlobalVariables());
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
