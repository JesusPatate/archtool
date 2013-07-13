package fr.univ_nantes.alma.archtool.clustering;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Connector;
import fr.univ_nantes.alma.archtool.architectureModel.Facade;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;

public class Dendogram implements Iterable<Dendogram.Node>
{
    /**
     * Un noeud du dendogramme.
     * 
     * <p>
     * Chaque noeud est composé d'un ensemble de fonctions, variables et types,
     * et de deux noeud fils.
     * </p>
     */
    public class Node
    {
        private Set<Function> functions = new HashSet<Function>();
        private Set<GlobalVariable> variables = new HashSet<GlobalVariable>();
        private Set<ComplexType> types = new HashSet<ComplexType>();

        private Node leftChild = null;
        private Node rightChild = null;

        /**
         * Construit un nouveau noeud vide.
         */
        public Node()
        {
        }

        /**
         * Construit un nouveau noeud parent.
         * 
         * <p>
         * Le contenu des enfants est recopié dans le noeud parent.
         * </p>
         * 
         * @param leftChild
         *            Premier noeud fils
         * @param rightChild
         *            Second noeud fils
         */
        public Node(final Node leftChild, final Node rightChild)
        {
            this.functions.addAll(leftChild.getFunctions());
            this.variables.addAll(leftChild.getVariables());
            this.types.addAll(leftChild.getTypes());

            this.functions.addAll(rightChild.getFunctions());
            this.variables.addAll(rightChild.getVariables());
            this.types.addAll(rightChild.getTypes());

            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        /**
         * Crée une copie d'un noeud.
         */
        public Node(final Node node)
        {
            this.functions = new HashSet<Function>(node.functions);
            this.variables = new HashSet<GlobalVariable>(node.variables);
            this.types = new HashSet<ComplexType>(node.types);

            this.leftChild = node.leftChild;
            this.rightChild = node.rightChild;
        }

        public Set<Function> getFunctions()
        {
            return new HashSet<Function>(this.functions);
        }

        public boolean addFunction(Function fct)
        {
            return this.functions.add(fct);
        }

        public Set<GlobalVariable> getVariables()
        {
            return new HashSet<GlobalVariable>(this.variables);
        }

        public boolean addVariable(GlobalVariable var)
        {
            return this.variables.add(var);
        }

        public Set<ComplexType> getTypes()
        {
            return new HashSet<ComplexType>(this.types);
        }

        public boolean addType(ComplexType t)
        {
            return this.types.add(t);
        }

        public Node getLeftChild()
        {
            return this.leftChild;
        }

        public Node getRightChild()
        {
            return this.rightChild;
        }

        public String toString()
        {
            StringBuffer buf = new StringBuffer("Node [");

            for (Function fct : this.functions)
            {
                buf.append(fct.getName());
                buf.append(", ");
            }

            for (GlobalVariable var : this.variables)
            {
                buf.append(var.getName());
                buf.append(", ");
            }

            for (ComplexType t : this.types)
            {
                buf.append(t.getName());
                buf.append(", ");
            }

            int idx = buf.lastIndexOf(",");
            buf.deleteCharAt(idx);

            buf.append("]");

            return buf.toString();
        }
    }

    /**
     * Les noeuds du dendogramme.
     */
    private List<Node> nodes = new ArrayList<Node>();

    /**
     * Architecture correspondant au dendogramme.
     */
    private Architecture architecture = null;

    private final SourceCode sourceCode;

    /**
     * Initialise un nouveau dendogramme à partir d'un modèle de code source.
     * 
     * <p>
     * Les feuilles du nouveaux dendogrammes correspondent aux différentes
     * fonctions, variables globales et définitions de type de modèle de code
     * source.
     * </p>
     * 
     * @param sourceCode
     */
    public Dendogram(SourceCode sourceCode)
    {
        this.sourceCode = sourceCode;
        this.init();
    }

    public Dendogram(Dendogram dendo)
    {
        this.sourceCode = dendo.sourceCode;
        this.architecture = null;

        for (Node node : dendo.nodes)
        {
            this.nodes.add(new Node(node));
        }
    }
    
    public Node getNode(int index)
    {
        return (index < this.size()) ? this.nodes.get(index) : null;
    }

    /**
     * Retourne le nombre de composants du dendogramme.
     * 
     * <p>
     * ATTENTION : ne revoit pas le nombre total de noeuds du dendogramme mais
     * le nombre de noeuds qui n'ont pas de parent. Deux composants qui ont été
     * regroupés ne forment plus qu'un seul noeud.
     * </p>
     * 
     * @return Le nombre de noeuds du dendogramme qui n'ont pas de parent.
     */
    public int size()
    {
        return this.nodes.size();
    }

    public Architecture getArchitecture()
    {
        if (this.architecture == null)
        {
            this.buildArchitecture();
        }

        return this.architecture;
    }

    /**
     * Génère un nouveau dendogramme en fusionnant deux noeuds.
     * 
     * @param idxChild1
     *            Index du premier noeud à regrouper
     * @param idxChild2
     *            Index du second noeud à regrouper
     * 
     * @return Le nouveau dendogramme
     */
    public Dendogram clusterNodes(final int idxChild1, final int idxChild2)
    {
        Dendogram dendo = null;

        if (idxChild1 < this.nodes.size() && idxChild2 < this.nodes.size())
        {
            dendo = new Dendogram(this);

            Node childNode1 = dendo.nodes.get(idxChild1);
            Node childNode2 = dendo.nodes.get(idxChild2);
            Node cluster = new Node(childNode1, childNode2);

            dendo.nodes.remove(childNode1);
            dendo.nodes.remove(childNode2);
            dendo.nodes.add(idxChild1, cluster);
        }

        else
        {
            throw new RuntimeException("Clustering.clusterNodes(int, int) : "
                    + "index passé(s) en paramètre(s) invalide(s).");
        }

        return dendo;
    }

    /**
     * Crée un nouveau dendogramme dans lequel un noeud a été remplacé par ses
     * fils.
     * 
     * <p>
     * Les fils d'un noeud à l'index <em>i</em> seront insérés aux index
     * <em>i</em> et <em>i</em> + 1.
     * </p>
     * 
     * @param index
     *            L'index du noeud à séparer
     */
    public Dendogram splitNode(final int index)
    {
        Dendogram dendo = null;

        if (index < this.nodes.size())
        {
            Node node = this.nodes.get(index);

            if (node.getLeftChild() != null && node.getRightChild() != null)
            {
                Node leftChild = node.getLeftChild();
                Node rightChild = node.getRightChild();

                dendo = new Dendogram(this);

                dendo.nodes.remove(index);
                dendo.nodes.add(index, leftChild);
                dendo.nodes.add((index + 1), rightChild);
            }
        }

        else
        {
            throw new RuntimeException("Clustering.splitNode(int) : "
                    + "index passé en paramètre invalide.");
        }

        return dendo;
    }

    /**
     * Crée les feuilles d'un nouveau dendogramme.
     * 
     * <p>
     * Méthode appelée à la création du dendogramme.
     * </p>
     * 
     * @param sourceCode
     *            Le modèle de code source sur lequel doit être construit le
     *            dendogramme.
     */
    private void init()
    {
        this.extractFunctions();
        this.extractVariables();
        this.extractTypes();
    }

    /**
     * Crée des noeuds du dendogramme à partir des fonctions d'un modèle de code
     * source.
     * 
     * @param sourceCode
     *            Un modèle de code source
     */
    private void extractFunctions()
    {
        for (final Function fct : this.sourceCode.getCoreFunctions())
        {
            final Dendogram.Node node = new Node();
            node.addFunction(fct);

            this.nodes.add(node);
        }
    }

    /**
     * Crée des noeuds du dendogramme à partir des variables globales d'un
     * modèle de code source.
     * 
     * @param sourceCode
     *            Un modèle de code source
     */
    private void extractVariables()
    {
        for (final GlobalVariable var : this.sourceCode.getCoreGlobalVariables())
        {
            final Dendogram.Node node = new Node();
            node.addVariable(var);

            this.nodes.add(node);
        }
    }

    /**
     * Crée des noeuds du dendogramme à partir des définitions de type d'un
     * modèle de code source.
     * 
     * @param sourceCode
     *            Un modèle de code source
     */
    private void extractTypes()
    {
        for (final ComplexType t : this.sourceCode.getCoreComplexTypes())
        {
            final Dendogram.Node node = new Node();
            node.addType(t);

            this.nodes.add(node);
        }
    }

    /**
     * ???
     */
    private void buildArchitecture()
    {
        this.architecture = new Architecture();

        for (Node node : this.nodes)
        {
            Component comp = new Component();
            this.architecture.addComponent(comp);
            
            comp.addFunctions(node.getFunctions());
            comp.addGlobalVariables(node.getVariables());
            comp.addComplexTypes(node.getTypes());
        }

        this.buildInterfaces();
        this.buildConnections();
    }

    /**
     * A COMPLETER
     */
    private void buildInterfaces()
    {
        for (Component comp : this.architecture.getComponents())
        {
            this.processInterfacesFct(comp);
            this.processInterfacesVar(comp);
            this.processInterfacesType(comp);
        }
    }

    /**
     * Génère les interfaces liées aux fonctions fournies par un composant.
     */
    private void processInterfacesFct(final Component comp)
    {
        for (final Function fct : comp.getFunctions())
        {
            Interface itf = new Interface();
            boolean required = false;
            
            Set<Function> callers = fct.getCoreCallingFunctions().keySet();

            for (Function fct2 : callers)
            {
                Component comp2 = this.architecture.getComponent(fct2);
                
                if(comp2.equals(comp) == false)
                {
                    comp2.addRequiredInterface(itf);
                    required = true;
                }
            }
            
            if(required == true)
            {
                comp.addProvidedInterface(itf);
                itf.addFunction(fct);
            }
        }
    }

    /**
     * Génère les interfaces liées aux variables fournies par un composant.
     */
    private void processInterfacesVar(Component comp)
    {
        for (final GlobalVariable var : comp.getGlobalVariables())
        {
            Interface itf = new Interface();
            Set<Function> userFcts = var.getCoreUsingFunctions().keySet();

            boolean required = false;

            for (Function fct : userFcts)
            {
                Component comp2 = this.architecture.getComponent(fct);

                if (comp2.equals(comp) == false)
                {
                    comp2.addRequiredInterface(itf);
                    required = true;
                }
            }

            if (required == true)
            {
                comp.addProvidedInterface(itf);
                itf.addGlobalVariable(var);
            }
        }
    }

    /**
     * Génère les interfaces liées aux types fournis par un composant.
     */
    private void processInterfacesType(Component comp)
    {
        for (final ComplexType t : comp.getComplexTypes())
        {
            Interface itf = new Interface();

            boolean required = false;

            Set<Function> userFcts = t.getCoreUsingFunctions().keySet();
            Set<GlobalVariable> userVars = t.getCoreUsingGlobalVariables();

            for (Function fct : userFcts)
            {
                Component comp2 = this.architecture.getComponent(fct);

                if (comp2.equals(comp) == false)
                {
                    comp2.addRequiredInterface(itf);
                    required = true;
                }
            }

            for (GlobalVariable var : userVars)
            {
                Component comp2 = this.architecture.getComponent(var);

                if (comp2.equals(comp) == false)
                {
                    comp2.addRequiredInterface(itf);
                    required = true;
                }
            }

            if (required == true)
            {
                comp.addProvidedInterface(itf);
                itf.addComplexType(t);
            }
        }
    }

    private void buildConnections()
    {
        Component[] compArray = new Component[this.architecture.nbComponents()];
        this.architecture.getComponents().toArray(compArray);
        
        for(int i = 0 ; i < compArray.length - 1 ; ++i)
        {
            for(int j = i + 1 ; j < compArray.length ; ++j)
            {
                Component comp1 = compArray[i];
                Component comp2 = compArray[j];
                
                Connector connector = new Connector();
                Facade facade1 = new Facade();
                Facade facade2 = new Facade();
                
                boolean useless = true;
                
                for(Interface itf : comp1.getRequiredInterfaces())
                {
                    if(comp2.providesInterface(itf))
                    {
                        useless = false;
                        
                        facade1.addFunctions(itf.getFunctions());
                        facade1.addGlobalVariables(itf.getGlobalVariables());
                        facade1.addComplexTypes(itf.getComplexTypes());
                        
                        facade2.addFunctions(itf.getFunctions());
                        facade2.addGlobalVariables(itf.getGlobalVariables());
                        facade2.addComplexTypes(itf.getComplexTypes());
                    }
                }
                
                for(Interface itf : comp2.getRequiredInterfaces())
                {
                    if(comp1.providesInterface(itf))
                    {
                        useless = false;
                        
                        facade1.addFunctions(itf.getFunctions());
                        facade1.addGlobalVariables(itf.getGlobalVariables());
                        facade1.addComplexTypes(itf.getComplexTypes());
                        
                        facade2.addFunctions(itf.getFunctions());
                        facade2.addGlobalVariables(itf.getGlobalVariables());
                        facade2.addComplexTypes(itf.getComplexTypes());
                    }
                }
                
                if(useless == false)
                {
                    connector.addFacade(facade1);
                    connector.addFacade(facade2);
                    this.architecture.addConnector(connector);
                }
            }
        }
    }

    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer("Dendogram (");

        for (Node node : this.nodes)
        {
            buf.append(node);
            buf.append(", ");
        }

        int idx = buf.lastIndexOf(",");
        buf.delete(idx, buf.length());

        buf.append(")");

        return buf.toString();
    }

    @Override
    public Iterator<Node> iterator()
    {
        return nodes.iterator();
    }
}
