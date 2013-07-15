package fr.univ_nantes.alma.archtool.objective;

import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Connector;
import fr.univ_nantes.alma.archtool.architectureModel.Facade;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.utils.Graph;

public class ObjectiveFunction
{
    /**
     * Poids de la validité sémantique de l'architecture.
     */
    static final double WEIGHT_SEM = 1.0;

    /**
     * Poids de la qualité de l'architecture.
     */
    static final double WEIGHT_QUAL = 1.0;

    /**
     * Poids de la sémantique des composants dans le calcul de la validité
     * sémantique de l'architecture.
     */
    static final double WEIGHT_COMP_SEM = 1.0;

    /**
     * Poids de la sémantique des connecteurs dans le calcul de la validité
     * sémantique de l'architecture.
     */
    static final double WEIGHT_CON_SEM = 1.0;

    /**
     * Poids de l'autonomie des composants dans le calcul de la validité
     * sémantique des composants.
     */
    static final double WEIGHT_COMP_INDE = 1.0;

    /**
     * Poids de la spécificité des composants dans le calcul de la validité
     * sémantique des composants.
     */
    static final double WEIGHT_COMP_SPECI = 1.0;

    /**
     * Poids de la composabilité des composants dans le calcul de la validité
     * sémantique des composants.
     */
    static final double WEIGHT_COMP_COMPO = 1.0;

    /**
     * Poids de la généricité des connecteurs dans le calcul de la validité
     * sémantique des connecteurs.
     */
    static final double WEIGHT_CON_GEN = 1.0;

    /**
     * Poids de la spécificité des connecteurs dans le calcul de la validité
     * sémantique des connecteurs.
     */
    static final double WEIGHT_CON_SPECI = 1.0;

    /**
     * Poids du nombre d'interfaces requises dans le calcul de la composabilité
     * des composants
     */
    static final double WEIGHT_INDE_ITFS_REQ = 5.0;

    /**
     * Poids du nombre d'interfaces fournies dans le calcul de la spécificité
     * des composants
     */
    static final double WEIGHT_SPECI_ITFS_PRO = 10.0;

    /**
     * Poids du nombre d'interfaces requises dans le calcul de la composabilité
     * des composants
     */
    static final double WEIGHT_COMPO_ITFS_REQ = 10.0;

    /**
     * Poids de la maintenabilité des éléments architecturaux dans le calcul de
     * la qualité de l'architecture.
     */
    static final double WEIGHT_MAIN = 1.0;

    /**
     * Poids de la maintenabilité des composants dans le calcul de la
     * maintenabilité de l'architecture.
     */
    static final double WEIGHT_MAIN_COMP = 1.0;

    /**
     * Poids de la maintenabilité des connecteurs dans le calcul de la
     * maintenabilité de l'architecture.
     */
    static final double WEIGHT_MAIN_CON = 1.0;

    /**
     * Poids de la maintenabilité des connecteurs dans le calcul de la
     * maintenabilité de l'architecture.
     */
    static final double WEIGHT_MAIN_CONF = 1.0;

    private Architecture architecture;

    private Cohesion cohesion;

    private Coupling coupling;

    private Maintainability maintainability;

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
        this.coupling = new Coupling();
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

            double compo = this.composability(comp);
            subresult = ObjectiveFunction.WEIGHT_COMP_COMPO * compo;

            double inde = this.independence(comp);
            subresult += ObjectiveFunction.WEIGHT_COMP_INDE * inde;

            double spec = this.specificity(comp);
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

            double spec = this.specificity(con);
            subresult += ObjectiveFunction.WEIGHT_CON_SPECI * spec;

            double gen = this.genericity(con);
            subresult += ObjectiveFunction.WEIGHT_CON_GEN * gen;

            subresult *= this.independence(con);

            subresult /= ObjectiveFunction.WEIGHT_CON_SPECI + WEIGHT_CON_GEN;

            result += subresult;
        }

        if (this.architecture.nbConnectors() > 0)
        {
            result /= this.architecture.nbConnectors();
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

        if (proInterfaces.size() > 0)
        {
            double sumCohesion = 0.0;
            double avgCohesion = 0.0;

            for (final Interface itf : proInterfaces)
            {
                sumCohesion += this.cohesion.interfaceInternalCohesion(itf);
            }

            avgCohesion = sumCohesion / proInterfaces.size();

            result = avgCohesion- (ObjectiveFunction.WEIGHT_COMPO_ITFS_REQ
                    * nbReqInterfaces);
        }

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

        result -= WEIGHT_INDE_ITFS_REQ * comp.getRequiredInterfaces().size();

        return result;
    }

    /**
     * Évalue l'autonomie d'un connecteur.
     * 
     * <p>
     * Un connecteur est autonome si sa glue est connexe.
     * </p>
     * 
     * @param con
     *            Le connecteur à évaluer
     */
    private double independence(final Connector con)
    {
        double result = 0.0;

        Graph<Object> graph = new Graph<Object>();

        Set<Function> conFcts = con.getFunctions();
        Set<GlobalVariable> conVars = con.getGlobalVariables();
        Set<ComplexType> conTypes = con.getComplexTypes();

        for (Function fct : conFcts)
        {
            graph.addNode(fct);
        }

        for (GlobalVariable var : conVars)
        {
            graph.addNode(var);
        }

        for (ComplexType t : conTypes)
        {
            graph.addNode(t);
        }

        for (Function fct : conFcts)
        {
            for (GlobalVariable var : fct.getCoreGlobalVariables().keySet())
            {
                graph.addEdge(fct, var);
            }

            for (ComplexType t : fct.getTotalComplexTypes().keySet())
            {
                graph.addEdge(fct, t);
            }
        }

        for (GlobalVariable var : conVars)
        {
            graph.addEdge(var, var.getType());
        }

        if (graph.size() > 0)
        {
            if (graph.isConnected())
            {
                result = 1.0;
            }
        }
        else
        {
            result = 1.0;
        }

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

        final Set<Interface> proInterfaces = comp.getProvidedInterfaces();
        final double nbProInterfaces = proInterfaces.size();

        if (nbProInterfaces > 0)
        {
            // Provided interfaces internal cohesion

            for (final Interface itf : comp.getProvidedInterfaces())
            {
                sum += this.cohesion.interfaceInternalCohesion(itf);
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

        result += this.cohesion.componentInternalCohesion(comp);

        // Component internal coupling

        result += coupling.componentCoupling(comp);

        // Number of provided interfaces

        result -= WEIGHT_SPECI_ITFS_PRO * nbProInterfaces;

        return 0.25 * result; // TODO Déplacer dans une constante
    }

    /**
     * Évalue la spécificité d'un connecteur.
     * 
     * La spécificité d'un connecteur est évaluée sur sa cohésion interne et sa
     * cohésion interne.
     * 
     * @param con
     *            Le connecteur à évaluer
     */
    private double specificity(final Connector con)
    {
        double result = 0.0;

        result += this.cohesion.connectorInternalCohesion(con);
        result += coupling.connectorCoupling(con);

        return 0.5 * result; // TODO Déplacer dans une constante
    }

    /**
     * Évalue la généricité d'un connecteur.
     * 
     * @param con
     *            Le connecteur à évaluer
     */
    private double genericity(final Connector con)
    {
        double result = 0.0;
        double sum = 0.0;

        Facade[] facades = new Facade[con.getFacades().size()];
        con.getFacades().toArray(facades);

        // Cohesion between roles

        double rolesCohesion = 0.0;

        for (int idx1 = 0; idx1 < (facades.length - 1); ++idx1)
        {
            for (int idx2 = idx1 + 1; idx2 < facades.length; ++idx2)
            {
                sum += this.cohesion.facadesCohesion(facades[idx1],
                                facades[idx2]);
            }
        }

        double nbPairs = (facades.length * (facades.length - 1)) / 2;

        rolesCohesion += sum / nbPairs;

        // Coupling between roles

        double rolesCoupling = 0.0;
        sum = 0.0;

        for (int idx1 = 0; idx1 < (facades.length - 1); ++idx1)
        {
            for (int idx2 = idx1 + 1; idx2 < facades.length; ++idx2)
            {
                sum += this.coupling.facadesCoupling(facades[idx1],
                                facades[idx2]);
            }
        }

        nbPairs = (facades.length * (facades.length - 1)) / 2;

        rolesCohesion += sum / nbPairs;

        // TODO Déplacer dans une constante
        result = 0.25 * (rolesCohesion + rolesCoupling);

        // Cohesion and coupling of each role

        sum = 0.0;

        for (Facade fcd : con.getFacades())
        {
            sum += this.cohesion.facadeInternalCohesion(fcd);
            sum += this.coupling.facadeCoupling(fcd);
        }

        result += sum / 8 * facades.length; // TODO Déplacer dans une constante

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
