package fr.univ_nantes.alma.archtool.objective;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Connector;

public class ObjectiveFunction
{
    /**
     * Poids de la validité sémantique de l'architecture.
     */
    private static final double WEIGHT_SEM = 1.0;

    /**
     * Poids de la qualité de l'architecture.
     */
    private static final double WEIGHT_QUAL = 1.0;

    /**
     * Poids de la sémantique des composants dans le calcul de la validité
     * sémantique de l'architecture.
     */
    private static final double WEIGHT_COMP_SEM = 1.0;

    /**
     * Poids de la sémantique des connecteurs dans le calcul de la validité
     * sémantique de l'architecture.
     */
    private static final double WEIGHT_CON_SEM = 1.0;

    /**
     * Poids de l'autonomie des composants dans le calcul de la validité
     * sémantique des composants.
     */
    private static final double WEIGHT_COMP_INDE = 1.0;

    /**
     * Poids de la spécificité des composants dans le calcul de la validité
     * sémantique des composants.
     */
    private static final double WEIGHT_COMP_SPECI = 1.0;

    /**
     * Poids de la composabilité des composants dans le calcul de la validité
     * sémantique des composants.
     */
    private static final double WEIGHT_COMP_COMPO = 1.0;

    /**
     * Poids de la généricité des connecteurs dans le calcul de la validité
     * sémantique des connecteurs.
     */
    private static final double WEIGHT_CON_GEN = 1.0;

    /**
     * Poids de la spécificité des connecteurs dans le calcul de la validité
     * sémantique des connecteurs.
     */
    private static final double WEIGHT_CON_SPECI = 1.0;

    /**
     * Poids de la maintenabilité des éléments architecturaux dans le calcul de
     * la qualité de l'architecture.
     */
    private static final double WEIGHT_MAIN = 1.0;

    /**
     * Poids de la maintenabilité des composants dans le calcul de la
     * maintenabilité de l'architecture.
     */
    private static final double WEIGHT_MAIN_COMP = 1.0;

    /**
     * Poids de la maintenabilité des connecteurs dans le calcul de la
     * maintenabilité de l'architecture.
     */
    private static final double WEIGHT_MAIN_CON = 1.0;

    /**
     * Poids de la maintenabilité des connecteurs dans le calcul de la
     * maintenabilité de l'architecture.
     */
    private static final double WEIGHT_MAIN_CONF = 1.0;

    private Architecture architecture;

    private Maintainability maintainability;
    
    private Evaluator<Component> compCompo = new ComponentComposability();
    private Evaluator<Component> compSpeci = new ComponentSpecificity();
    private Evaluator<Component> compAuto = new ComponentAutonomy();
    
    private Evaluator<Connector> conGenericity = new ConnectorGenericity();
    private Evaluator<Connector> conSpecificity = new ConnectorSpecificity();
    private Evaluator<Connector> conAuto = new ConnectorAutonomy();

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
        double result = 0.0;

        this.architecture = arch;
        this.maintainability = new Maintainability();

        result += WEIGHT_SEM * this.evaluateSemanticArchitecture();
//        result += WEIGHT_QUAL * this.evaluateArchQuality();

        return result;
    }

    /**
     * Évalue la validité sémantique de l'architecture.
     */
    private double evaluateSemanticArchitecture()
    {
        double result = 0.0;

        result += WEIGHT_COMP_SEM * this.evaluateSemanticComponent();
        result += WEIGHT_CON_SEM * this.evaluateSemanticConnector();

        result /= WEIGHT_COMP_SEM + WEIGHT_CON_SEM
                        + this.architecture.nbComponents()
                        + this.architecture.nbConnectors();

        return result;
    }

    /**
     * 
     * @return
     */
    private double evaluateArchQuality()
    {
        double result = 0.0;

        result += WEIGHT_MAIN * this.evaluateMaintainability();

        return result;
    }

    /**
     * Évalue la validité sémantique des composant de l'architecture.
     */
    private double evaluateSemanticComponent()
    {
        double result = 0.0;

        for (Component comp : this.architecture.getComponents())
        {
            double subresult = 0.0;

            double compo = this.compCompo.evaluate(comp);
            subresult = ObjectiveFunction.WEIGHT_COMP_COMPO * compo;

            double inde = this.compAuto.evaluate(comp);
            subresult += ObjectiveFunction.WEIGHT_COMP_INDE * inde;

            double spec = this.compSpeci.evaluate(comp);
            subresult += ObjectiveFunction.WEIGHT_COMP_SPECI * spec;

            subresult /= ObjectiveFunction.WEIGHT_COMP_COMPO
                            + ObjectiveFunction.WEIGHT_COMP_INDE
                            + ObjectiveFunction.WEIGHT_COMP_SPECI;

            result += subresult;
        }

        if (this.architecture.nbComponents() > 0)
        {
            result /= this.architecture.nbComponents();
        }

        return result;
    }

    /**
     * Évalue la validité sémantique des connecteurs de l'architecture.
     */
    private double evaluateSemanticConnector()
    {
        double result = 0.0;

        for (Connector con : this.architecture.getConnectors())
        {
            double subresult = 0.0;

            double spec = this.conSpecificity.evaluate(con);
            subresult += ObjectiveFunction.WEIGHT_CON_SPECI * spec;

            double gen = this.conGenericity.evaluate(con);
            subresult += ObjectiveFunction.WEIGHT_CON_GEN * gen;

            subresult *= this.conAuto.evaluate(con);

            subresult /= ObjectiveFunction.WEIGHT_CON_SPECI + WEIGHT_CON_GEN;

            result += subresult;
        }

        if (this.architecture.nbConnectors() > 0)
        {
            result /= this.architecture.nbConnectors();
        }

        return result;
    }
    
    private double evaluateMaintainability()
    {
        double result = 0.0;
        double subresult = 0.0;

        for (Component comp : this.architecture.getComponents())
        {
            subresult += this.maintainability.process(comp);
        }

        if (this.architecture.nbComponents() > 0)
        {
            subresult /= WEIGHT_MAIN_COMP * this.architecture.nbComponents();
        }

        result += subresult;
        subresult = 0.0;

        for (Connector con : this.architecture.getConnectors())
        {
            subresult += this.maintainability.process(con);
        }

        if (this.architecture.nbConnectors() > 0)
        {
            subresult /= WEIGHT_MAIN_CON * this.architecture.nbConnectors();
        }

        result += subresult;

        result += (1 / WEIGHT_MAIN_CONF)
                        * this.maintainability.process(this.architecture
                                .getConfiguration());

        return result;
    }
}
