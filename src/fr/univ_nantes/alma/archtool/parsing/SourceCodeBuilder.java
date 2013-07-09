package fr.univ_nantes.alma.archtool.parsing;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;

public class SourceCodeBuilder 
{
	private CPreprocessor cPreprocessor = new CPreprocessor();
	private CProcessor cProcessor = new CProcessor();
	private FileSystemScanner includesScanner;
	private FileSystemScanner sourcesScanner;
	private Map<String, Context> contexts = new HashMap<String, Context>();
	private Stack<String> includes = new Stack<String>();
	private Map<String, Set<String>> includeFiles = null;
	private SourceCode sourceCode = new SourceCode();
	
	public SourceCodeBuilder(Set<String> sourceFilePaths,
			ExtensionFilter sourceFileFilter, Set<String> includeFilePaths,
			ExtensionFilter includeFileFilter)
	{
	    this.sourcesScanner = 
	            new FileSystemScanner(sourceFilePaths, sourceFileFilter);
	    this.includesScanner = 
	            new FileSystemScanner(includeFilePaths, includeFileFilter);
	}
	
	public SourceCodeBuilder(String [] sourceFilePaths,
			ExtensionFilter sourceFileFilter, String [] includeFilePaths,
			ExtensionFilter includeFileFilter)
	{
	    this.sourcesScanner = 
                new FileSystemScanner(sourceFilePaths, sourceFileFilter);
        this.includesScanner = 
                new FileSystemScanner(includeFilePaths, includeFileFilter);
	}
	
	public void build()
	{
		//this.includesScanner.scann();
		this.includeFiles = this.includesScanner.getPathsFoundByName();
		this.sourcesScanner.scann();	
		Set<String> sourceFiles = this.sourcesScanner.getPathsFound();
		
		for(String sourceFile : sourceFiles)
		{
		    System.out.println(sourceFile);
			this.preprocess(sourceFile);
			//Context context = this.createContext();
			
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
	
	private Context createContext()
    {
	    System.out.println("nb includes: " + this.includes.size());
	    
	    Context context = new Context();
	    
	    while(!this.includes.isEmpty())
        {
            String currentInclude = this.includes.pop();
            
            if(this.contexts.containsKey(currentInclude))
            {
                context = context.merge(this.contexts.get(currentInclude));
            }
            else
            {
                for(String includePath : this.includeFiles.get(currentInclude))
                {
                    try
                    {
                        System.out.println("starting parse include: " + includePath);
                        this.cProcessor.process(includePath, context);
                        System.out.println("finishing parse include: " + includePath);
                        context = this.cProcessor.getContext();                    
                    }
                    catch(IOException e)
                    {
                        System.out.println("CProcessor : \"" + includePath +
                                "\", impossible to process.");
                    }
                }
                
                this.contexts.put(currentInclude, context);
            }
        }
	    
	    return context;
    }

    private void preprocess(String filename)
	{    
		try
		{
    		this.cPreprocessor.process(filename);
    		Set<String> newIncludes = 
    		        this.cPreprocessor.getNonStandardIncludes();
    		this.cPreprocessor.reset();
    				
    		for(String newInclude : newIncludes)
    		{
    		    if(this.includeFiles.containsKey(newInclude) && 
    		            !this.includes.contains(newInclude))
        		{
    		        this.includes.push(newInclude);
        		       
    		        for(String includeFile : this.includeFiles.get(newInclude))
    		        {
    		            this.preprocess(includeFile);
        		    }
        		}
    		}
		}
		catch(IOException e)
		{
		    System.out.println("CPreprocessor : \"" + filename +
		            "\", impossible to process.");
		}
	}
	
	public SourceCode getSourceCode()
	{
		return this.sourceCode;
	}

}
