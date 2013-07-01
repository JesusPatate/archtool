package fr.univ_nantes.alma.archtool.objective;

import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;

public class ObjectiveFunction
{
    /**
     * Poids de l'autonomie des composants
     */
    static final double WEIGHT_COMP_INDE = 1.0;

    /**
     * Poids de la spécificité des composants
     */
    static final double WEIGHT_COMP_SPECI = 1.0;

    /**
     * Poids du nombre d'interfaces fournies pour le calcul de la spécificité
     * des composants
     */
    static final double WEIGHT_SPECI_ITFS_PRO = 1.0;

    /**
     * Poids de la composabilité des composants
     */
    static final double WEIGHT_COMP_COMPO = 1.0;

    /**
     * Poids du nombre d'interfaces requises pour le calcul de la composabilité
     * des composants
     */
    static final double WEIGHT_COMPO_ITFS_REQ = 1.0;

    static final Cohesion cohesion = new Cohesion();

    static final Coupling coupling = new Coupling();

    /**
     * Applique la fonction objectif à un composant.
     * 
     * @param component
     *            Le composant à évaluer
     * 
     * @return Le résultat de la fonction objectif
     */
    public double evaluate(final Architecture arch)
    {
        return this.evaluateArchSemantic(arch);
    }

    /**
     * Évalue la validité sémantique d'un composant.
     * 
     * @param comp
     *            Le composant à évaluer
     */
    private double evaluateArchSemantic(final Architecture arch)
    {
        double result = 0.0;

        for (Component comp : arch.getComponents())
        {
            result = ObjectiveFunction.WEIGHT_COMP_COMPO
                    * this.composability(comp);
            
            result += ObjectiveFunction.WEIGHT_COMP_INDE
                    * this.independence(comp);
            
            result += ObjectiveFunction.WEIGHT_COMP_SPECI
                    * this.specificity(comp);

            result /= ObjectiveFunction.WEIGHT_COMP_COMPO
                    + ObjectiveFunction.WEIGHT_COMP_INDE
                    + ObjectiveFunction.WEIGHT_COMP_SPECI;
        }

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

        final Set<Interface> proInterfaces = comp.getProvidedInterfaces();
        final double nbReqInterfaces = comp.getRequiredInterfaces().size();

        double sumCohesion = 0.0;
        double avgCohesion = 0.0;

        for (final Interface itf : proInterfaces)
        {
            sumCohesion += ObjectiveFunction.cohesion.internalCohesion(itf);
        }

        avgCohesion = sumCohesion / proInterfaces.size();

        result = avgCohesion
                - (ObjectiveFunction.WEIGHT_COMPO_ITFS_REQ * nbReqInterfaces);

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

        result = 1 / (1 + comp.getRequiredInterfaces().size());

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
        double sum = 0.0;

        // Provided interfaces internal cohesion

        final Set<Interface> proInterfaces = comp.getProvidedInterfaces();
        final double nbProInterfaces = proInterfaces.size();

        for (final Interface itf : comp.getProvidedInterfaces())
        {
            sum += ObjectiveFunction.cohesion.internalCohesion(itf);
        }

        result += sum / nbProInterfaces;

        // Provided interfaces cohesion

        final Interface[] itfs = new Interface[proInterfaces.size()];
        proInterfaces.toArray(itfs);

        sum = 0.0;

        for (int idx1 = 0 ; idx1 < (itfs.length - 1) ; ++idx1)
        {
            for (int idx2 = idx1 + 1 ; idx2 < itfs.length ; ++idx2)
            {
                sum += ObjectiveFunction.cohesion.cohesionInterfaces(
                        itfs[idx1], itfs[idx2]);
            }
        }

        final double nbPairs = (nbProInterfaces * (nbProInterfaces - 1)) / 2;

        result += sum / nbPairs;

        // Component internal cohesion

        result += ObjectiveFunction.cohesion.internalCohesion(comp);

        // Component internal coupling

        result += coupling.coupling(comp);

        // Number of provided interfaces

        result -= WEIGHT_SPECI_ITFS_PRO * nbProInterfaces;

        return result;
    }
}
