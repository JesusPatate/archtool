package fr.univ_nantes.alma.archtool.clustering;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
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
            for (int idx1 = 0; idx1 < (this.dendogram.size() - 1); ++idx1)
            {
                for (int idx2 = idx1 + 1; idx2 < this.dendogram.size(); ++idx2)
                {
                    final Dendogram.Node clusterNode = this.clusterNodes(idx1, idx2);
                    
                    final Component comp = clusterNode.getComponent();
                    final double score = this.objectiveFct.evaluate(comp);
                    
                    if(score > bestScore)
                    {
                        bestScore = score;
                        bestPair = new Pair<Integer, Integer>(idx1, idx2);
                        bestComponent = comp;
                    }
                }
            }
            
            if(bestPair != null && bestComponent != null)
            {
                this.dendogram.clusterNodes(bestComponent,
                        bestPair.getFirst(), bestPair.getSecond());
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

    /**
     * Calcule les interfaces requises d'un nouveau cluster.
     * 
     * <p>
     * Une interface requise d'un des composants fils ne doit pas être
     * transférée au cluster si l'autre composant fils la fournit.
     * </p>
     * 
     * @param cluster
     *            Le composant cluster
     * @param idxChild1
     *            L'index du premier noeud fils
     * @param idxChild2
     *            L'index du second noeud fils
     */
    private void updateRequiredInterfaces(final Component cluster,
            final int idxChild1, final int idxChild2)
    {
        final Component child1 = this.dendogram.getNode(idxChild1)
                .getComponent();
        final Component child2 = this.dendogram.getNode(idxChild2)
                .getComponent();

        for (final Interface reqI : child1.getRequiredInterfaces())
        {
            if (child2.providesInterface(reqI) == false)
            {
                cluster.addRequiredInterface(reqI);
            }
        }

        for (final Interface reqI : child2.getRequiredInterfaces())
        {
            if (child1.providesInterface(reqI))
            {
                cluster.addRequiredInterface(reqI);
            }
        }
    }

    /**
     * Calcule les interfaces fournies d'un nouveau cluster.
     * 
     * <p>
     * Une interface fournie d'un des composants fils ne doit pas être
     * transférée au cluster si seul l'autre composant fils la requérais.
     * </p>
     * 
     * @param cluster
     *            L'index du noeud cluster
     * @param idxChild1
     *            L'index du premier noeud fils
     * @param idxChild2
     *            L'index du second noeud fils
     */
    private void updateProvidedInterfaces(final Component cluster,
            final int idxChild1, final int idxChild2)
    {
        final Component child1 = this.dendogram.getNode(idxChild1)
                .getComponent();
        final Component child2 = this.dendogram.getNode(idxChild2)
                .getComponent();

        for (final Interface proI : child1.getProvidedInterfaces())
        {
            final Iterator<Dendogram.Node> itNodes = this.dendogram.iterator();
            boolean required = false;

            while (itNodes.hasNext() && (required == false))
            {
                final Component compo = itNodes.next().getComponent();

                if ((compo != child1) && (compo != child2))
                {
                    if (compo.requiresInterface(proI))
                    {
                        cluster.addProvidedInterface(proI);
                        required = true;
                    }
                }
            }
        }

        for (final Interface proI : child2.getProvidedInterfaces())
        {
            final Iterator<Dendogram.Node> itNodes = this.dendogram.iterator();
            boolean required = false;

            while (itNodes.hasNext() && (required == false))
            {
                final Component compo = itNodes.next().getComponent();

                if ((compo != child1) && (compo != child2))
                {
                    if (compo.requiresInterface(proI))
                    {
                        cluster.addProvidedInterface(proI);
                        required = true;
                    }
                }
            }
        }
    }

    /**
     * Regroupe deux noeuds dans un nouveau noeud parent (cluster)
     * 
     * @param idxChild1
     *            Index du premier noeud fils
     * @param idxChild2
     *            Index du second noeud fils
     */
    private Dendogram.Node clusterNodes(final int idxChild1, final int idxChild2)
    {
        final Dendogram.Node clusterNode = this.dendogram
                .clusterNodes(idxChild1, idxChild2);

        this.updateRequiredInterfaces(clusterNode.getComponent(), idxChild1, idxChild2);
        this.updateProvidedInterfaces(clusterNode.getComponent(), idxChild1, idxChild2);

        return clusterNode;
    }
}
