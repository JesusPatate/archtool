package fr.univ_nantes.alma.archtool.objective;

import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Connector;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.coa.COA;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Type;
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
    static final double WEIGHT_COMP_SPECI = 0.25;

    /**
     * Poids de la composabilité des composants dans le calcul de la validité
     * sémantique des composants.
     */
    static final double WEIGHT_COMP_COMPO = 3.0;

    /**
     * Poids du nombre d'interfaces requises dans le calcul de la composabilité
     * des composants
     */
    static final double WEIGHT_INDE_ITFS_REQ = 5.0;

    /**
     * Poids du nombre d'interfaces fournies dans le calcul de la spécificité
     * des composants
     */
    static final double WEIGHT_SPECI_ITFS_PRO = 1.0;

    /**
     * Poids du nombre d'interfaces requises dans le calcul de la composabilité
     * des composants
     */
    static final double WEIGHT_COMPO_ITFS_REQ = 1.0;

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

    private COA coa;

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
    public double evaluate(final Architecture arch, final COA coa)
    {
        double result = 0.0;

        this.architecture = arch;
        this.coa = coa;

        this.cohesion = new Cohesion(this.coa);
        this.coupling = new Coupling(this.coa);
        this.maintainability = new Maintainability(this.coa);

        result += WEIGHT_SEM * this.evaluateSemanticArchitecture();
        result +=  WEIGHT_QUAL * this.evaluateArchQuality();

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

        result /= this.architecture.getComponents().size();

        return result;
    }

    /**
     * Évalue la validité sémantique des composant de l'architecture.
     */
    private double evaluateSemanticConnector()
    {
        double result = 0.0;

        for (Connector comp : this.architecture.getConnectors())
        {
            double subresult = 0.0;

            /*
            double compo = this.composability(comp);
            subresult = ObjectiveFunction.WEIGHT_COMP_COMPO * compo;
             */
            
            double inde = this.independence(comp);
            subresult += ObjectiveFunction.WEIGHT_COMP_INDE * inde;

            double spec = this.specificity(comp);
            subresult += ObjectiveFunction.WEIGHT_COMP_SPECI * spec;

            subresult /= ObjectiveFunction.WEIGHT_COMP_COMPO
                    + ObjectiveFunction.WEIGHT_COMP_INDE
                    + ObjectiveFunction.WEIGHT_COMP_SPECI;

            result += subresult;
        }

        result /= this.architecture.getComponents().size();

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

            result = avgCohesion
                    - (ObjectiveFunction.WEIGHT_COMPO_ITFS_REQ * nbReqInterfaces);
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

        result = 1.0 / (1 + comp.getRequiredInterfaces().size());

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
        
        Set<Function> conFcts = this.coa.getConnectorFunctions(con);
        Set<GlobalVariable> conVars = this.coa.getConnectorVariables(con);
        Set<Type> conTypes = this.coa.getConnectorTypes(con);
        
        for(Function fct : conFcts)
        {
            graph.addNode(fct);
        }

        for(GlobalVariable var : conVars)
        {
            graph.addNode(var);
        }

        for(Type t : conTypes)
        {
            graph.addNode(t);
        }
        
        for(Function fct : conFcts)
        {
            for(GlobalVariable var : fct.getGlobalVariables().keySet())
            {
                graph.addEdge(fct, var);
            }
            
            for(Type t : fct.getUsedTypes().keySet())
            {
                graph.addEdge(fct, t);
            }
        }
        
        for(GlobalVariable var : conVars)
        {
            graph.addEdge(var, var.getType());
        }
        
        if(graph.size() > 0)
        {
            if(graph.isConnected())
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

                for (int idx1 = 0 ; idx1 < (itfs.length - 1) ; ++idx1)
                {
                    for (int idx2 = idx1 + 1 ; idx2 < itfs.length ; ++idx2)
                    {
                        sum += this.cohesion.interfacesCohesion(
                                itfs[idx1], itfs[idx2]);
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
    private double specificity(final Connector con)
    {
        double result = 0.0;

        result += this.cohesion.connectorInternalCohesion(con);
        result += coupling.connectorCoupling(con);

        return result;
    }

    private double evaluateMaintainability()
    {
        double result = 0.0;
        double subresult = 0.0;

        for (Component comp : this.architecture.getComponents())
        {
            subresult += this.maintainability.process(comp, this.coa);
        }

        if(this.architecture.nbComponents() > 0)
        {
            subresult /= WEIGHT_MAIN_COMP * this.architecture.nbComponents();
        }

        result += subresult;
        subresult = 0.0;

        for (Connector con : this.architecture.getConnectors())
        {
            subresult += this.maintainability.process(con, this.coa);
        }

        if(this.architecture.nbConnectors() > 0)
        {
            subresult /= WEIGHT_MAIN_CON * this.architecture.nbConnectors();
        }

        result += subresult;

        result += (1 / WEIGHT_MAIN_CONF) * this.maintainability.process(
                this.architecture.getConfiguration());

        return result;
    }
}
