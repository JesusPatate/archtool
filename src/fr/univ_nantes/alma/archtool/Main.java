package fr.univ_nantes.alma.archtool;

import fr.univ_nantes.alma.archtool.clustering.Clustering;
import fr.univ_nantes.alma.archtool.objective.ObjectiveFunction;
import fr.univ_nantes.alma.archtool.parsing.ExtensionFilter;
import fr.univ_nantes.alma.archtool.parsing.SourceCodeBuilder;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;

public class Main
{
    public static void main(String[] args)
    {
        String root = "/home/gaetan/fac/horoquartz/";
        
        String [] sourceFilePaths = 
                new String []{root + "sou/hr/srclib/hrrhor.c",
                root + "sou/hr/srclib/hrrcmv.c"};
        
        ExtensionFilter sourceFileFilter = 
                new ExtensionFilter(new String [] {".c"});        
        SourceCodeBuilder builder = new SourceCodeBuilder(sourceFilePaths,
                sourceFileFilter);
        builder.build();
        
        SourceCode sourceCode = builder.getSourceCode();
        sourceCode.optimizeRelations();
        
        ObjectiveFunction obj = new ObjectiveFunction();
        Clustering clustering = new Clustering(obj);
        
        clustering.process(sourceCode);

        System.out.println(clustering.getArchitecture());
        System.out.println(clustering.getCOA());

        System.out.println(obj.evaluate(clustering.getArchitecture(),
                clustering.getCOA()));
    }
}