package fr.univ_nantes.alma.archtool.objective;

import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;

public class ObjectiveFunction
{
    /**
     * Poids de l'autonomie des composants
     */
    static final double WEIGHT_INDE_COMP = 1.0;

    /**
     * Poids de la spécificité des composants
     */
    static final double WEIGHT_SPECI_COMP = 1.0;

    /**
     * Poids de la composabilité des composants
     */
    static final double WEIGHT_COMPO_COMP = 1.0;

    /**
     * Applique la fonction objectif à un composant.
     * 
     * @param component
     *            Le composant à évaluer
     * 
     * @return Le résultat de la fonction objectif
     */
    public double evaluate(final Component component)
    {
        return this.semantic(component);
    }

    /**
     * Évalue la validité sémantique d'un composant.
     * 
     * @param comp
     *            Le composant à évaluer
     */
    private double semantic(final Component comp)
    {
        double result = 0.0;

        result = ObjectiveFunction.WEIGHT_COMPO_COMP * this.composability(comp);
        result += ObjectiveFunction.WEIGHT_INDE_COMP * this.independence(comp);
        result += ObjectiveFunction.WEIGHT_SPECI_COMP * this.specificity(comp);

        result /= ObjectiveFunction.WEIGHT_COMPO_COMP
                + ObjectiveFunction.WEIGHT_INDE_COMP
                + ObjectiveFunction.WEIGHT_SPECI_COMP;

        return result;
    }

    /**
     * Évalue la composabilité d'un composant.
     * 
     * La composabilité d'un composant est évaluée sur le nombre d'interfaces
     * qu'il requiert et sur la cohésion interne moyenne de ses interfaces
     * fournies.
     * 
     * @param comp
     *            Le composant à évaluer
     */
    private double composability(final Component comp)
    {
        double result = 0.0;

        final double nbReqInterfaces = comp.getRequiredInterfaces().size();
        final double nbProInterfaces = comp.getProvidedInterfaces().size();

        double sumCohesion = 0.0;
        double avgCohesion = 0.0;

        Cohesion cohesion = new Cohesion();
        
        for (final Interface itf : comp.getProvidedInterfaces())
        {
            sumCohesion += cohesion.internalCohesion(itf);
        }

        avgCohesion = sumCohesion / nbProInterfaces;

        result = (avgCohesion / 1 + nbReqInterfaces);

        return result;
    }

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
    private double independence(final Component comp)
    {
        double result = 0.0;
        
        result = 1 / 1 + comp.getRequiredInterfaces().size(); 

        return result;
    }

    /**
     * Évalue la spécificité d'un composant.
     * 
     * @param comp
     *            Le composant à évaluer
     */
    private double specificity(final Component comp)
    {
        double result = 0.0;

        return result;
    }

    
}
