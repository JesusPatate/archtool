package fr.univ_nantes.alma.archtool.clustering;

import java.util.HashMap;
import java.util.Map;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.objective.ObjectiveFunction;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;
import fr.univ_nantes.alma.archtool.utils.Pair;

/**
 * Méthode de regroupement hiérarchique.
 */
public class Clustering
{
    private Dendogram dendogram;
    
    private InterfaceIdentifier itfIdentifier = new InterfaceIdentifier();
    
    private ConnectorIdentifier conIdentifier = new ConnectorIdentifier();

    private ObjectiveFunction objectiveFct = new ObjectiveFunction();

    private Architecture resultArch = null;

    public Architecture getArchitecture()
    {
        return this.resultArch;
    }

    /**
     * Applique l'algorithme de clustering à un modèle de code source..
     * 
     * @param sourceCode
     *            Un modèle de code cource
     * 
     * @return Un ensemble de composants générés par l'algorithme.
     */
    public Architecture process(final SourceCode sourceCode)
    {
        this.dendogram = new Dendogram(sourceCode);
        this.buildDendogram();
        this.phase2();
        
//        this.resultArch.clear();
//        this.itfIdentifier.identify(this.resultArch);
//        this.conIdentifier.identify(this.resultArch);
        
        return this.resultArch;
    }

    /**
     * Phase 1 : création du dendogramme par regroupement hiérarchique.
     * 
     * <p>
     * A chaque itération, les deux noeuds formant la meilleure paire au vu de
     * la fonction objectif (qui ont donc le plus de chance d'appartenir à un
     * même composant) sont regroupés dans un nouveau noeud. Ceci jusqu'à ce
     * qu'il ne reste plus qu'un noeud racine.
     * </p>
     */
    private void buildDendogram()
    {
        double bestScore = Double.NEGATIVE_INFINITY;
        Pair<Integer, Integer> bestPair = null;

        Dendogram dendo = null;
        Dendogram bestDendo = null;

        Architecture arch = null;

        while (this.dendogram.size() > 1)
        {
            for (int i = 0 ; i < (this.dendogram.size() - 1) ; ++i)
            {
                for (int j = i + 1 ; j < this.dendogram.size() ; ++j)
                {
                    dendo = this.dendogram.clusterNodes(i, j);

                    arch = dendo.getArchitecture();

                    final double score = this.objectiveFct.evaluate(arch);

                    if (score > bestScore)
                    {
                        bestScore = score;
                        bestPair = new Pair<Integer, Integer>(i, j);
                        bestDendo = dendo;
                    }
                }
            }

            if (bestPair != null && bestDendo != null)
            {
                this.dendogram = bestDendo;
            }

            bestScore = Double.NEGATIVE_INFINITY;
            bestPair = null;
            bestDendo = null;
        }

        return;
    }

    /**
     * Phase 2 : identification des composants à partir du dendogramme.
     */
    private void phase2()
    {
        Architecture currentArch = null;
        double currentScore = 0.0;

        Architecture arch2 = null;
        double newScore = 0.0;
        
        Map<Integer, Double> scores = new HashMap();
        boolean stop = false;
        
        currentArch = this.dendogram.getArchitecture();
        currentScore = objectiveFct.evaluate(currentArch);
        
        while(stop == false)
        {
            int idx = 0;
            
            double bestScore = Double.NEGATIVE_INFINITY;
            int bestIdx = 0;

            Dendogram dendo = null;
            Dendogram bestDendo = null;
        
            while (idx < this.dendogram.size())
            {
                dendo = this.dendogram.splitNode(idx);
    
                // The node has been splitted successfully
                if (dendo != null)
                {
                    arch2 = dendo.getArchitecture();
                    
                    if(scores.containsKey(idx))
                    {
                        newScore = scores.get(idx);
                    }
                    else
                    {
                        newScore = objectiveFct.evaluate(arch2);
                        scores.put(idx, newScore);
                    }
                    
                    // Better architecture when the node is splitted
                    if (newScore > currentScore && newScore > bestScore)
                    {
                        bestDendo = dendo;
                        bestScore = newScore;
                        bestIdx = idx;
                    }
                }
                
                ++idx;
            }
            
            if(bestDendo != null)
            {
                this.dendogram = bestDendo;
                currentScore = bestScore;
                scores.remove(bestIdx);
            }
            else
            {
                stop = true;
            }
        }

        this.resultArch = this.dendogram.getArchitecture();
    }
}
