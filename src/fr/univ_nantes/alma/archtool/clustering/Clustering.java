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
        Component bestComponent = null;

        while (this.dendogram.size() > 1)
        {
            Dendogram.Node clusterNode = null;

            for (int idx1 = 0; idx1 < (this.dendogram.size() - 1); ++idx1)
            {
                for (int idx2 = idx1 + 1; idx2 < this.dendogram.size(); ++idx2)
                {
                    clusterNode = this.dendogram.clusterNodes(idx1, idx2);
                    
                    final Component comp = clusterNode.getComponent();
                    final double score = this.objectiveFct.evaluate(comp);

                    if (score > bestScore)
                    {
                        bestScore = score;
                        bestPair = new Pair<Integer, Integer>(idx1, idx2);
                        bestComponent = comp;
                    }
                }
            }

            if (bestPair != null && bestComponent != null)
            {
                this.dendogram.insertClusterNode(clusterNode);
            }

            bestScore = 0.0;
            bestPair = null;
            bestComponent = null;
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
