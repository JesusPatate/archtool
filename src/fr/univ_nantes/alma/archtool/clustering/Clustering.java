package fr.univ_nantes.alma.archtool.clustering;

import java.util.HashSet;
import java.util.Set;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import fr.univ_nantes.alma.archtool.architectureModel.Component;
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
    public Set<Component> process(final SourceCode sourceCode)
    {
        final HashSet<Component> components = new HashSet<Component>();

        this.dendogram = new Dendogram(sourceCode);
        this.buildDendogram();

        return components;
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
        Dendogram bestDendo = null;

        while (this.dendogram.size() > 1)
        {
            Dendogram dendo = null;

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
    }

    /**
     * Phase 2 : identification des composants à partir du dendogramme.
     */
    private void phase2()
    {
        throw new NotImplementedException();
    }
}
