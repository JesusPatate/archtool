package fr.univ_nantes.alma.archtool.objective;

import fr.univ_nantes.alma.archtool.architectureModel.Component;

public class ComponentAutonomy implements Evaluator<Component>
{
    /**
     * Poids du nombre d'interfaces requises dans le calcul de la composabilité
     * des composants
     */
    private static final double WEIGHT_INDE_ITFS_REQ = 5.0;
    
    /**
     * Évalue l'autonomie d'un composant.
     * 
     * <p>
     * L'autonomie d'un composant est évaluée sur le nombre d'interface que
     * celui-ci requiert.
     * </p>
     * 
     * @param comp
     *            Le composant à évaluer
     */
    @Override
    public double evaluate(Component element)
    {
        double result = 0.0;
        result -= WEIGHT_INDE_ITFS_REQ * element.getRequiredInterfaces().size();
        return result;
    }
}