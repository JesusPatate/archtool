package fr.univ_nantes.alma.archtool.parsing;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;

public class SourceCodeBuilder 
{
	private Set<String> sourceFilePaths;
	private ExtensionFilter sourceFileFilter;
	private Set<String> includeFilePaths;
	private ExtensionFilter includeFileFilter;
	private CPreprocessor cPreprocessor = new CPreprocessor();
	private CProcessor cProcessor = new CProcessor();
	private Map<String, Context> contexts = new HashMap<String, Context>();
	Map<String, Set<String>> includeFiles = null;
	private SourceCode sourceCode = new SourceCode();
	
	public SourceCodeBuilder(Set<String> sourceFilePaths,
			ExtensionFilter sourceFileFilter, Set<String> includeFilePaths,
			ExtensionFilter includeFileFilter)
	{
		this.sourceFilePaths = sourceFilePaths;
		this.sourceFileFilter = sourceFileFilter;
		this.includeFilePaths = includeFilePaths;
		this.includeFileFilter = includeFileFilter;
	}
	
	public SourceCodeBuilder(String [] sourceFilePaths,
			ExtensionFilter sourceFileFilter, String [] includeFilePaths,
			ExtensionFilter includeFileFilter)
	{
		this.sourceFilePaths = 
				new HashSet<String>(Arrays.asList(sourceFilePaths));
		this.sourceFileFilter = sourceFileFilter;
		this.includeFilePaths = 
				new HashSet<String>(Arrays.asList(includeFilePaths));
		this.includeFileFilter = includeFileFilter;
	}
	public void build()
	{
		this.scannIncludePaths();
		FileSystemScanner scanner = 
				new FileSystemScanner(sourceFilePaths, sourceFileFilter);
		scanner.scann();
		Set<String> sourceFiles = scanner.getPathsFound();
		
		for(String sourceFile : sourceFiles)
		{
			
		}
	}
	
	private void scannIncludePaths()
	{
		FileSystemScanner scanner = 
				new FileSystemScanner(includeFilePaths, includeFileFilter);
		scanner.scann();
		this.includeFiles = scanner.getPathsFoundByName();
	}
	
	private void recursivePreprocess(String filename) throws IOException
	{
		this.cPreprocessor.reset();
		this.cPreprocessor.process(filename);
		Set<String> includeFiles = this.cPreprocessor.getNonStandardIncludes();
		
		for()
	}
	
	public SourceCode getSourceCode()
	{
		return this.sourceCode;
	}

}
