package fr.univ_nantes.alma.archtool.architectureModel;

import fr.univ_nantes.alma.archtool.dot.DotGraph;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;

public class ArchitectureTest
{
    public static void main(String[] args)
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
        
        Architecture cloned = (Architecture) a.clone();
        
        c2.addComplexType(new ComplexType("t3", null));
        
        DotGraph dot = new DotGraph();
        dot.createGraph(a);
        dot.writeToFile("architecture.dot");
        
        dot.createGraph(cloned);
        dot.writeToFile("clone.dot");
        
    }
}
