package fr.univ_nantes.alma.archtool.objective;

import fr.univ_nantes.alma.archtool.architectureModel.Connector;
import fr.univ_nantes.alma.archtool.architectureModel.Facade;

public class ConnectorGenericity implements Evaluator<Connector>
{
    private Cohesion cohesion = new Cohesion();
    private Coupling coupling = new Coupling();

    /**
     * Évalue la généricité d'un connecteur.
     * 
     * @param con
     *            Le connecteur à évaluer
     */
    @Override
    public double evaluate(Connector con)
    {
        double result = 0.0;
        double sum = 0.0;

        Facade[] facades = new Facade[con.getFacades().size()];
        con.getFacades().toArray(facades);

        // Cohesion between roles

        for (int idx1 = 0 ; idx1 < (facades.length - 1) ; ++idx1)
        {
            for (int idx2 = idx1 + 1 ; idx2 < facades.length ; ++idx2)
            {
                sum += this.cohesion.facadesCohesion(facades[idx1],
                        facades[idx2]);
            }
        }

        double nbPairs = (facades.length * (facades.length - 1)) / 2;

        result += sum / nbPairs;

        // Coupling between roles

        sum = 0.0;

        for (int idx1 = 0 ; idx1 < (facades.length - 1) ; ++idx1)
        {
            for (int idx2 = idx1 + 1 ; idx2 < facades.length ; ++idx2)
            {
                sum += this.coupling.facadesCoupling(facades[idx1],
                        facades[idx2]);
            }
        }

        nbPairs = (facades.length * (facades.length - 1)) / 2;

        result += sum / nbPairs;

        result *= 0.25; // TODO Déplacer dans une constante

        // Cohesion and coupling of each role

        sum = 0.0;

        for (Facade fcd : con.getFacades())
        {
            sum += this.cohesion.internalCohesion(fcd);
            sum += this.coupling.facadeCoupling(fcd);
        }

        result += sum / 8 * facades.length; // TODO Déplacer dans une constante

        return result;
    }
}
