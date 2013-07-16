package fr.univ_nantes.alma.archtool.objective;

import fr.univ_nantes.alma.archtool.architectureModel.Connector;

public class ConnectorSpecificity implements Evaluator<Connector>
{
    private Cohesion cohesion = new Cohesion();
    private Coupling coupling = new Coupling();
    
    /**
     * Évalue la spécificité d'un connecteur.
     * 
     * La spécificité d'un connecteur est évaluée sur sa cohésion interne et sa
     * cohésion interne.
     * 
     * @param con
     *            Le connecteur à évaluer
     */
    @Override
    public double evaluate(Connector con)
    {
        double result = 0.0;

        result += this.cohesion.internalCohesion(con);
        result += coupling.connectorCoupling(con);

        return 0.5 * result; // TODO Déplacer dans une constante
    }
}
