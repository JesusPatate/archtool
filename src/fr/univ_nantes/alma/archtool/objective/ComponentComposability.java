package fr.univ_nantes.alma.archtool.objective;

import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;

public class ComponentComposability implements Evaluator<Component>
{
    /**
     * Poids du nombre d'interfaces requises dans le calcul de la composabilité
     * des composants
     */
    private static final double WEIGHT_COMPO_ITFS_REQ = 10.0;
    
    private Cohesion cohesion = new Cohesion();
    
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
    @Override
    public double evaluate(Component comp)
    {
        double result = 0.0;

        final Set<Interface> proInterfaces = comp.getProvidedInterfaces();
        final double nbReqInterfaces = comp.getRequiredInterfaces().size();

        if (proInterfaces.size() > 0)
        {
            double sumCohesion = 0.0;
            double avgCohesion = 0.0;

            for (final Interface itf : proInterfaces)
            {
                sumCohesion += this.cohesion.interfaceInternalCohesion(itf);
            }

            avgCohesion = sumCohesion / proInterfaces.size();

            result = avgCohesion - (WEIGHT_COMPO_ITFS_REQ * nbReqInterfaces);
        }

        return result;
    }
}
