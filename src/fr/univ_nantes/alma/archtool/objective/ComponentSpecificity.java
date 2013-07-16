package fr.univ_nantes.alma.archtool.objective;

import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;

public class ComponentSpecificity implements Evaluator<Component>
{
    /**
     * Poids du nombre d'interfaces fournies.
     */
    private static final double WEIGHT_ITFS_PRO = 10.0;
    
    private Cohesion cohesion = new Cohesion();
    private Coupling coupling = new Coupling();
    
    /**
     * Évalue la spécificité d'un composant.
     * 
     * @param comp
     *            Le composant à évaluer
     */
    @Override
    public double evaluate(Component comp)
    {
        double result = 0.0;
        double sum = 0.0;

        final Set<Interface> proInterfaces = comp.getProvidedInterfaces();
        final double nbProInterfaces = proInterfaces.size();

        if (nbProInterfaces > 0)
        {
            // Provided interfaces internal cohesion

            for (final Interface itf : comp.getProvidedInterfaces())
            {
                sum += this.cohesion.internalCohesion(itf);
            }

            result += sum / nbProInterfaces;

            // Provided interfaces cohesion

            if (nbProInterfaces > 1)
            {
                final Interface[] itfs = new Interface[proInterfaces.size()];
                proInterfaces.toArray(itfs);

                sum = 0.0;

                for (int idx1 = 0; idx1 < (itfs.length - 1); ++idx1)
                {
                    for (int idx2 = idx1 + 1; idx2 < itfs.length; ++idx2)
                    {
                        sum += this.cohesion.interfacesCohesion(itfs[idx1],
                                        itfs[idx2]);
                    }
                }

                final double nbPairs = 
                        (nbProInterfaces * (nbProInterfaces - 1)) / 2;

                result += sum / nbPairs;
            }

            else
            {
                result += 1.0;
            }
        }

        // Component internal cohesion

        result += this.cohesion.internalCohesion(comp);

        // Component internal coupling

        result += coupling.componentCoupling(comp);

        // Number of provided interfaces

        result -= WEIGHT_ITFS_PRO * nbProInterfaces;

        return result; // TODO Déplacer dans une constante
    }
}
