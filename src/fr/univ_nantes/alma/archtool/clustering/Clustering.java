package fr.univ_nantes.alma.archtool.clustering;

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

    private ObjectiveFunction objectiveFct;
    
    public Clustering(ObjectiveFunction objFct)
    {
        this.objectiveFct = objFct;
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
        
        Architecture arch = this.phase2();
        
        return arch;
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
        double bestScore = 0.0;
        Pair<Integer, Integer> bestPair = null;

        Dendogram dendo = null;
        Dendogram bestDendo = null;

        while (this.dendogram.size() > 1)
        {
            for (int i = 0 ; i < (this.dendogram.size() - 1) ; ++i)
            {
                for (int j = i + 1 ; j < this.dendogram.size() ; ++j)
                {
                    dendo = this.dendogram.clusterNodes(i, j);

                    final double score = this.objectiveFct.evaluate(
                            dendo.getArchitecture());
                    
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

            bestScore = 0.0;
            bestPair = null;
            bestDendo = null;
        }
        
        return;
    }

    /**
     * Phase 2 : identification des composants à partir du dendogramme.
     */
    private Architecture phase2()
    {
        Architecture result = null;
        
        Dendogram dendo = null;
        Architecture arch1 = null;
        Architecture arch2 = null;

        int idx = 0;

        while (idx < this.dendogram.size())
        {
            dendo = this.dendogram.splitNode(idx);

            if(dendo!= null)
            {
                arch1 = this.dendogram.getArchitecture();
                arch2 = dendo.getArchitecture();
    
                double score1 = objectiveFct.evaluate(arch1);
                double score2 = objectiveFct.evaluate(arch2);
                
                // Best architecture when the node is splitted
                if (score2 > score1)
                {
                    this.dendogram = dendo;
                    result = arch2;
                }
    
                // Otherwise try to split the next node
                else
                {
                    ++idx;
                    result = arch1;
                }
            }
            
            else
            {
                ++idx;
            }
        }

        return result;
    }
}
