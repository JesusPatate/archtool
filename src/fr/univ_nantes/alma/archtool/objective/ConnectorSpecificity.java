package fr.univ_nantes.alma.archtool.objective;

import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Connector;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.utils.Graph;

public class ConnectorSpecificity implements Evaluator<Connector>
{
    /**
     * Évalue la spécificité d'un connecteur.
     * 
     * La spécificité d'un connecteur est évaluée sur sa cohésion interne et sa
     * cohésion interne.
     * 
     * @param con
     *            Le connecteur à évaluer
     */
    @Override
    public double evaluate(Connector con)
    {
        double result = 0.0;

        Graph<Object> graph = new Graph<Object>();

        Set<Function> conFcts = con.getFunctions();
        Set<GlobalVariable> conVars = con.getGlobalVariables();
        Set<ComplexType> conTypes = con.getComplexTypes();

        for (Function fct : conFcts)
        {
            graph.addNode(fct);
        }

        for (GlobalVariable var : conVars)
        {
            graph.addNode(var);
        }

        for (ComplexType t : conTypes)
        {
            graph.addNode(t);
        }

        for (Function fct : conFcts)
        {
            for (GlobalVariable var : fct.getCoreGlobalVariables().keySet())
            {
                graph.addEdge(fct, var);
            }

            for (ComplexType t : fct.getTotalComplexTypes().keySet())
            {
                graph.addEdge(fct, t);
            }
        }

        for (GlobalVariable var : conVars)
        {
            graph.addEdge(var, var.getType());
        }

        if (graph.size() > 0)
        {
            if (graph.isConnected())
            {
                result = 1.0;
            }
        }
        else
        {
            result = 1.0;
        }

        return result;
    }
}
