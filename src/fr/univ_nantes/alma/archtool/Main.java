package fr.univ_nantes.alma.archtool;

import java.util.Arrays;
import java.util.List;

import fr.univ_nantes.alma.archtool.clustering.Clustering;
import fr.univ_nantes.alma.archtool.dot.DotGraph;
import fr.univ_nantes.alma.archtool.objective.ObjectiveFunction;
import fr.univ_nantes.alma.archtool.parsing.SourceCodeBuilder;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;

public class Main
{
    public static void main(String[] args)
    {
        String root = "/home/gaetan/fac/horoquartz/";

        String[] sourceFilePaths = new String[] {root + "sou/qhe/hrdjou1.qhe",
                root + "sou/hr/srclib/hrrjou.c"};

//        String[] sourceFilePaths = new String[] {root + "sou/hr/srclib/hrrjou.c"};
        
        List<String> pathList = Arrays.asList(sourceFilePaths);

        SourceCodeBuilder builder = new SourceCodeBuilder(pathList);
        builder.build();

        SourceCode sourceCode = builder.getSourceCode();

        ObjectiveFunction obj = new ObjectiveFunction();
        Clustering clustering = new Clustering();

        clustering.process(sourceCode);

        System.out.println(clustering.getArchitecture());

        System.out.println(obj.evaluate(clustering.getArchitecture()));

        DotGraph dot = new DotGraph();
        dot.createGraph(clustering.getArchitecture());
        dot.writeToFile("archi.dot");
    }
}