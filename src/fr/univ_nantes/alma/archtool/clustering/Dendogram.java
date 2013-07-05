package fr.univ_nantes.alma.archtool.clustering;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.coa.COA;
import fr.univ_nantes.alma.archtool.sourceModel.FileGlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.LocalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.ProgramGlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;
import fr.univ_nantes.alma.archtool.sourceModel.Type;
import fr.univ_nantes.alma.archtool.sourceModel.Variable;

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
        private Set<Variable> variables = new HashSet<Variable>();
        private Set<Type> types = new HashSet<Type>();

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
            this.variables = new HashSet<Variable>(node.variables);
            this.types = new HashSet<Type>(node.types);

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

        public Set<Variable> getVariables()
        {
            return new HashSet<Variable>(this.variables);
        }

        public boolean addVariable(Variable var)
        {
            return this.variables.add(var);
        }

        public Set<Type> getTypes()
        {
            return new HashSet<Type>(this.types);
        }

        public boolean addType(Type t)
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
                buf.append(fct);
                buf.append(", ");
            }

            for (Variable var : this.variables)
            {
                buf.append(var);
                buf.append(", ");
            }

            for (Type t : this.types)
            {
                buf.append(t);
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

    private COA coa;

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
        this.coa = new COA();
        this.init();
    }

    public Dendogram(Dendogram dendo)
    {
        this.sourceCode = dendo.sourceCode;
        this.coa = null;
        this.architecture = null;

        for (Node node : dendo.nodes)
        {
            this.nodes.add(new Node(node));
        }
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
        if(this.architecture == null)
        {
            this.buildArchitecture();
        }
        
        return this.architecture;
    }
    
    public COA getCOA()
    {
        return this.coa;
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
        for (final Function fct : this.sourceCode.getFunctions())
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
        for (final Variable var : this.sourceCode.getProgramGlobals())
        {
            final Dendogram.Node node = new Node();
            node.addVariable(var);

            this.nodes.add(node);
        }

        for (final Variable var : this.sourceCode.getFileGlobals())
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
        for (final Type t : this.sourceCode.getTypes())
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
        this.coa = new COA();

        for (Node node : this.nodes)
        {
            Component comp = new Component();
            this.architecture.addComponent(comp);

            this.coa.addComponent(comp);
            this.coa.addFunctions(node.getFunctions(), comp);
            this.coa.addVariables(node.getVariables(), comp);
            this.coa.addTypes(node.getTypes(), comp);
        }

        this.processInterfaces();
    }

    /**
     * A COMPLETER
     */
    private void processInterfaces()
    {
        for (Component comp : this.architecture.getComponents())
        {
            this.processInterfacesFct(comp);
            this.processInterfacesVar(comp);
            this.processInterfacesType(comp);
        }
    }

    /**
     * A COMPLETER
     * 
     * <p>
     * Recherche les appels à la fonction fournie par un composant. Une
     * interface requise est créée pour chaque composant faisant appel à cette
     * fonction. Une interface fournie est créée pour le composant qui contient
     * la fonction en question.
     * </p>
     * 
     * @param idxNode
     *            Index du composant qui fournit la fonction en question
     * 
     * @see #processInterfaces()
     */
    private void processInterfacesFct(final Component comp)
    {
        for (final Function fct : this.coa.getComponentFunctions(comp))
        {
            Interface itf = new Interface();
            this.coa.addInterface(itf);
            this.coa.addFunction(fct, itf);

            int nbCallers = 0; // Nb of components that call fct

            for (Component comp2 : this.architecture.getComponents())
            {
                if (comp2.equals(comp) == false)
                {
                    Iterator<Function> itFcts =
                            this.coa.getComponentFunctions(comp2).iterator();

                    Function fct2 = null;
                    boolean found = false;

                    while (itFcts.hasNext() && (found == false))
                    {
                        fct2 = itFcts.next();

                        if (fct2.calls(fct))
                        {
                            comp2.addRequiredInterface(itf);
                            ++nbCallers;
                            found = true;
                        }
                    }
                }
            }

            if (nbCallers > 0)
            {
                comp.addProvidedInterface(itf);
            }
        }
    }

    /**
     * A COMPLETER
     * 
     * <p>
     * Recherche les accès à une variable fournie par un composant. Une
     * interface requise est créée pour chaque composant qui accède à cette
     * fonction. Une interface fournie est créée pour le composant qui contient
     * la variable en question.
     * </p>
     * 
     * @param idxNode
     *            Index du composant qui fournit la variable en question
     * 
     * @see #processInterfaces()
     */
    private void processInterfacesVar(Component comp)
    {
        for (final Variable var : this.coa.getComponentVariables(comp))
        {
            Interface itf = new Interface();
            this.coa.addInterface(itf);
            this.coa.addVariable(var, itf);

            int nbAccessors = 0;

            for (Component comp2 : this.architecture.getComponents())
            {
                Iterator<Function> itFcts =
                        this.coa.getComponentFunctions(comp2).iterator();

                Function fct2 = null;
                boolean found = false;

                while (itFcts.hasNext() && (found == false))
                {
                    fct2 = itFcts.next();

                    final Map<FileGlobalVariable, Integer> fgFct2 =
                            fct2.getFileGlobals();
                    final Map<ProgramGlobalVariable, Integer> pgFct2 =
                            fct2.getProgramGlobals();

                    if (fgFct2.containsKey(var) || pgFct2.containsKey(var))
                    {
                        comp2.addRequiredInterface(itf);
                        ++nbAccessors;
                        found = true;
                    }
                }
            }

            if (nbAccessors > 0)
            {
                comp.addProvidedInterface(itf);
            }
        }
    }

    /**
     * Fonction appelée à la création du dendogramme pour initialiser les
     * interfaces liées à un type d'un composant
     * 
     * <p>
     * Recherche les utilisation d'un type fourni par un composant. Une
     * interface requise est créée pour chaque composant qui utilise ce type
     * (contient une fonction qui utilise une variable de ce type ou une
     * variable globale de ce type) Une interface fournie est créée pour le
     * composant qui contient le type en question.
     * </p>
     * 
     * @param idxNode
     *            Index du composant qui fournit le type en question
     * 
     * @see #processInterfaces()
     */

    private void processInterfacesType(Component comp)
    {
        for (final Type t : this.coa.getComponentTypes(comp))
        {
            Interface itf = new Interface();
            this.coa.addInterface(itf);
            this.coa.addType(t, itf);

            int nbUsers = 0;

            for (Component comp2 : this.architecture.getComponents())
            {
                boolean found = false;

                Set<Function> fcts = this.coa.getComponentFunctions(comp2);
                Iterator<Function> itFcts = fcts.iterator();

                // A function of comp2 can use t within its body or it can
                // have an argument of this type
                while (itFcts.hasNext() && (found == false))
                {
                    final Function fct2 = itFcts.next();

                    // Arguments

                    final Set<LocalVariable> args = fct2.getArguments();
                    Iterator<LocalVariable> itArgs = args.iterator();

                    while (itArgs.hasNext() && (found == false))
                    {
                        final LocalVariable arg = itArgs.next();

                        if (arg.ofType(t))
                        {
                            comp2.addRequiredInterface(itf);
                            ++nbUsers;
                            found = true;
                        }
                    }

                    // Body

                    final Map<Type, Integer> types = fct2.getUsedTypes();

                    if (types.containsKey(t))
                    {
                        comp2.addRequiredInterface(itf);
                        ++nbUsers;
                        found = true;
                    }
                }

                if (found == false)
                {
                    // comp2 can have a global variable of type t
                    Set<Variable> vars =
                            this.coa.getComponentVariables(comp2);
                    Iterator<Variable> itVars = vars.iterator();

                    while (itVars.hasNext() && (found == false))
                    {
                        Variable v = itVars.next();

                        if (v.ofType(t))
                        {
                            comp2.addRequiredInterface(itf);
                            ++nbUsers;
                            found = true;
                        }
                    }
                }
            }

            if (nbUsers > 0)
            {
                comp.addProvidedInterface(itf);
            }
        }
    }

    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer("Dendogram (");
        
        for(Node node : this.nodes)
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
