package fr.univ_nantes.alma.archtool.clustering;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.coa.COA;
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
    public Pair<Architecture, COA> process(final SourceCode sourceCode)
    {
        this.dendogram = new Dendogram(sourceCode);
        this.buildDendogram();

        Pair<Architecture, COA> result = this.phase2();

        return result;
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

        Architecture arch = null;
        COA coa = null;

        while (this.dendogram.size() > 1)
        {
            for (int i = 0 ; i < (this.dendogram.size() - 1) ; ++i)
            {
                for (int j = i + 1 ; j < this.dendogram.size() ; ++j)
                {
                    dendo = this.dendogram.clusterNodes(i, j);

                    arch = dendo.getArchitecture();
                    coa = dendo.getCOA();

                    final double score = this.objectiveFct.evaluate(arch, coa);

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
    private Pair<Architecture, COA> phase2()
    {
        Architecture currentArch = null;
        COA currentCOA = null;
        double currentScore = 0.0;

        Dendogram dendo = null;

        Architecture arch2 = null;
        COA coa2 = null;
        double score2 = 0.0;

        int idx = 0;

        while (idx < this.dendogram.size())
        {
            dendo = this.dendogram.splitNode(idx);

            // The node has been splitted successfully
            if (dendo != null)
            {
                currentArch = this.dendogram.getArchitecture();
                currentCOA = this.dendogram.getCOA();
                currentScore = objectiveFct.evaluate(currentArch, currentCOA);

                arch2 = dendo.getArchitecture();
                coa2 = dendo.getCOA();
                score2 = objectiveFct.evaluate(arch2, coa2);

                // Better architecture when the node is splitted
                if (score2 > currentScore)
                {
                    this.dendogram = dendo;
                }

                // Try to split the next node
                else
                {
                    ++idx;
                }
            }

            // Try to split the next node
            else
            {
                ++idx;
            }
        }

        Architecture resultArch = this.dendogram.getArchitecture();
        COA resultCOA = this.dendogram.getCOA();

        return new Pair<Architecture, COA>(resultArch, resultCOA);
    }
}
