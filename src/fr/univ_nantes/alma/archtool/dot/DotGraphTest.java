package fr.univ_nantes.alma.archtool.dot;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.parsing.ExtensionFilter;
import fr.univ_nantes.alma.archtool.parsing.SourceCodeBuilder;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;

public class DotGraphTest
{
	public static void main(String [] args)
	{
		Function f1 = new Function("f1", null);
		Function f2 = new Function("f2", null);
		ComplexType t1 = new ComplexType("t1", null);
		ComplexType t2 = new ComplexType("t2", null);
		
		Architecture a = new Architecture();
		Component c1 = new Component();
		Component c2 = new Component();
		
		c1.addFunction(f1);
		c1.addFunction(f2);
		
		Interface i = new Interface();
		i.addComplexType(t1);
		c2.addComplexType(t2);
		c2.addProvidedInterface(i);
		
		a.addComponent(c1);
		a.addComponent(c2);
		
		DotGraph dot = new DotGraph();
		dot.createGraph(a);
		dot.writeToFile("architecture.dot");
		
		String root = "/home/stan/Documents/development/";
        
        String [] sourceFilePaths = 
                new String []{root + "sou/hr/srclib/hrrjou.c",
                root + "sou/hr/srclib/hrract.c",
                root + "sou/hr/srclib/hrrhor.c",
                root + "sou/hr/srclib/hrrcho.c",
                root + "sou/hr/srclib/hrrplc.c",
                root + "sou/hr/srclib/hrrpro.c",
                root + "sou/hr/srclib/hrrseh.c"};
        ExtensionFilter sourceFileFilter = 
                new ExtensionFilter(new String [] {".c"});        
        SourceCodeBuilder builder = new SourceCodeBuilder(sourceFilePaths,
                sourceFileFilter);
        builder.build();
        
        SourceCode sc = builder.getSourceCode();
        dot.createGraph(sc);
        dot.writeToFile("sources.dot");
	}
}
