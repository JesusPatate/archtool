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
    private static final double WEIGHT_COMP_AUTO = 1.0;

    /**
     * Poids de la spécificité des composants dans le calcul de la validité
     * sémantique des composants.
     */
    private static final double WEIGHT_COMP_SPECI = 2.0;

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
     * Poids de la complexité des éléments architecturaux dans le calcul de la
     * qualité de l'architecture.
     */
    static final double WEIGHT_COMP = 1.0;

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

    private Cohesion cohesion;
    
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
        this.cohesion = new Cohesion();
        this.maintainability = new Maintainability();

        result += WEIGHT_SEM * this.evaluateSemanticArchitecture();
        result += WEIGHT_QUAL * this.evaluateArchQuality();

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

        result /= WEIGHT_COMP_SEM
                + WEIGHT_CON_SEM
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
        result -= WEIGHT_COMP * this.evaluateComplexity();

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
            double subresult = ObjectiveFunction.WEIGHT_COMP_COMPO
                    * this.compCompo.evaluate(comp);

            subresult += ObjectiveFunction.WEIGHT_COMP_AUTO
                    * this.compAuto.evaluate(comp);

            subresult += ObjectiveFunction.WEIGHT_COMP_SPECI
                    * this.compSpeci.evaluate(comp);

            subresult /= ObjectiveFunction.WEIGHT_COMP_COMPO
                    + ObjectiveFunction.WEIGHT_COMP_AUTO
                    + ObjectiveFunction.WEIGHT_COMP_SPECI;

            result += subresult;
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
            double subresult = ObjectiveFunction.WEIGHT_CON_SPECI
                    * this.conSpecificity.evaluate(con);

            subresult += ObjectiveFunction.WEIGHT_CON_GEN
                    * this.conGenericity.evaluate(con);

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

    private double evaluateComplexity()
    {
        double result = 0.0;

        for (Component comp : this.architecture.getComponents())
        {
            result += this.evaluateComplexity(comp);
        }

        for (Connector con : this.architecture.getConnectors())
        {
            result += this.evaluateComplexity(con);
        }

        result /= this.architecture.nbComponents()
                + this.architecture.nbConnectors();

        return result;
    }

    private double evaluateComplexity(Component comp)
    {
        double result = 0.0;

        double intra = this.cohesion.internalCohesion(comp);
        double inter = comp.nbInterfaces();

        result = intra * Math.pow(inter, 2);
        result /= Math.pow(10, 4);

        return result;
    }

    private double evaluateComplexity(Connector con)
    {
        double result = 0.0;

        double intra = this.cohesion.internalCohesion(con);
        double inter = con.nbFacades();

        result = intra * Math.pow(inter, 2);
        result /= Math.pow(10, 4);

        return result;
    }
}
