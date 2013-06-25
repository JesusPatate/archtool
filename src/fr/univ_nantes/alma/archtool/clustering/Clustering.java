package fr.univ_nantes.alma.archtool.clustering;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.sourceModel.Call;
import fr.univ_nantes.alma.archtool.sourceModel.FileGlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.ProgramGlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;
import fr.univ_nantes.alma.archtool.sourceModel.Type;
import fr.univ_nantes.alma.archtool.sourceModel.Variable;

/**
 * Méthode de regroupement hiérarchique.
 */
public class Clustering
{
    /**
     * Un noeud du dendogramme.
     * 
     * <p>
     * Chaque noeud est composé d'un composant (la valeur du noeud) et de deux
     * noeud fils.
     * </p>
     */
    private class Node
    {
        private final Component component;

        private Node leftChild;

        private Node rightChild;

        public Node(final Component component)
        {
            this.component = component;
            this.leftChild = null;
            this.rightChild = null;
        }

        public Node(final Component component, final Node leftChild,
                final Node rightChild)
        {
            this.component = component;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        public Component getComponent()
        {
            return this.component;
        }

        public Node getLeftChild()
        {
            return this.leftChild;
        }

        public void setLeftChild(final Node child)
        {
            this.leftChild = child;
        }

        public Node getRightChild()
        {
            return this.rightChild;
        }

        public void setRightChild(final Node child)
        {
            this.rightChild = child;
        }
    }

    /**
     * Le dendogramme résultat de la première phase de l'algorithme.
     * 
     * @see #Clustering.createDendogram()
     */
    private final List<Node> dendogram = new ArrayList<Clustering.Node>();

    /**
     * Applique l'algorithme de clustering à un modèle de code source..
     * 
     * @param sourceCode
     *            Un modèle de code cource
     * 
     * @return Un ensemble de composants générés par l'algorithme.
     */
    public Set<Component> process(final SourceCode sourceCode)
    {
        final HashSet<Component> components = new HashSet<Component>();

        this.extractFunctions(sourceCode);
        this.extractVariables(sourceCode);
        this.extractTypes(sourceCode);

        this.processInterfaces();

        this.createDendogram();

        return components;
    }

    /**
     * Phase 1 : création du dendogramme par regroupement hiérarchique.
     */
    private void createDendogram()
    {
        for (int i = 0; i < (this.dendogram.size() - 1); ++i)
        {
            for (int j = i + 1; j < this.dendogram.size(); ++j)
            {
                clusterNodes(i, j);
            }
        }
    }

    /**
     * Phase 2 : identification des composants à partir du dendogramme.
     */
    private void phase2()
    {
        throw new NotImplementedException();
    }

    /**
     * Crée des noeuds du dendogramme à partir des fonctions d'un modèle de code
     * source.
     * 
     * @param sourceCode
     *            Un modèle de code source
     */
    private void extractFunctions(final SourceCode sourceCode)
    {
        for (final Function fct : sourceCode.getFunctions())
        {
            final Component comp = new Component();
            comp.addFunction(fct);

            final Clustering.Node node = new Node(comp);
            this.dendogram.add(node);
        }
    }

    /**
     * Crée des noeuds du dendogramme à partir des variables globales d'un
     * modèle de code source.
     * 
     * @param sourceCode
     *            Un modèle de code source
     */
    private void extractVariables(final SourceCode sourceCode)
    {
        for (final Variable var : sourceCode.getFileGlobals())
        {
            final Component comp = new Component();
            comp.addVariable(var);

            final Clustering.Node node = new Node(comp);
            this.dendogram.add(node);
        }
    }

    /**
     * Crée des noeuds du dendogramme à partir des définitions de type d'un
     * modèle de code source.
     * 
     * @param sourceCode
     *            Un modèle de code source
     */
    private void extractTypes(final SourceCode sourceCode)
    {
        for (final Type type : sourceCode.getTypes())
        {
            final Component comp = new Component();
            comp.addType(type);

            final Clustering.Node node = new Node(comp);
            this.dendogram.add(node);
        }
    }

    /**
     * Fonction appelée à la création du dendogramme pour initialiser les
     * interfaces des composants.
     */
    private void processInterfaces()
    {
        // At this time, a node is a function, a variable or a type

        for (int idxNode = 0; idxNode < this.dendogram.size(); ++idxNode)
        {
            Component compo = this.dendogram.get(idxNode).getComponent();

            // Function
            if (compo.getFunctions().isEmpty() == false)
            {
                processInterfacesFct(idxNode);
            }

            // Variable
            if (compo.getVariables().isEmpty() == false)
            {
                processInterfacesVar(idxNode);
            }

            // type
            if (compo.getTypes().isEmpty() == false)
            {
                processInterfacesType(idxNode);
            }
        }
    }

    /**
     * Fonction appelée à la création du dendogramme pour initialiser les
     * interfaces liées à la fonction d'un composant
     * 
     * <p>
     * Recherche les appels à la fonction fournie par un composant. Une
     * interface requise est créée pour chaque composant faisant appel à cette
     * fonction. Une interface fournie est créée pour le composant qui contient
     * la fonction en question.
     * </p>
     * 
     * @param idxNode
     *            Index dans le dendogramme du composant qui contient la
     *            fonction en question
     * 
     * @see #processInterfaces()
     */
    private void processInterfacesFct(int idxNode)
    {
        Component compo1 = this.dendogram.get(idxNode).getComponent();
        Function fct = compo1.getFunctions().iterator().next();

        Interface interfaceFct = new Interface();
        interfaceFct.addFunction(fct);

        int nbCallers = 0;

        for (int idx = 0; idx < this.dendogram.size(); ++idx)
        {
            Component compo2 = this.dendogram.get(idx).getComponent();

            // If compo2 contains no function, it cannot call fct
            if (compo2.getFunctions().isEmpty() == false)
            {
                Function fct2 = compo2.getFunctions().iterator().next();
                Set<Call> callsFct2 = fct2.getCalls();

                for (Call call : callsFct2)
                {
                    if (call.getFunction().equals(fct))
                    {
                        compo2.addRequiredInterface(interfaceFct);
                        nbCallers++;
                    }
                }
            }
        }

        if (nbCallers > 0)
        {
            compo1.addProvidedInterface(interfaceFct);
        }
    }

    /**
     * Fonction appelée à la création du dendogramme pour initialiser les
     * interfaces liées à une variable d'un composant
     * 
     * <p>
     * Recherche les accès à une variable fournie par un composant. Une
     * interface requise est créée pour chaque composant qui accède à cette
     * fonction. Une interface fournie est créée pour le composant qui contient
     * la variable en question.
     * </p>
     * 
     * @param idxNode
     *            Index dans le dendogramme du composant qui contient la
     *            variable en question
     * 
     * @see #processInterfaces()
     */
    private void processInterfacesVar(int idxNode)
    {
        Component compo1 = this.dendogram.get(idxNode).getComponent();
        Variable var = compo1.getVariables().iterator().next();

        Interface interfaceVar = new Interface();
        interfaceVar.addVariable(var);

        int nbAccessors = 0;

        for (int idx = 0; idx < this.dendogram.size(); ++idx)
        {
            Component compo2 = this.dendogram.get(idx).getComponent();

            // If compo2 contains no function, it cannot access var
            if (compo2.getFunctions().isEmpty() == false)
            {
                Function fct2 = compo2.getFunctions().iterator().next();

                Map<FileGlobalVariable, Integer> fileGlobalsFct = fct2
                        .getFileGlobals();
                Map<ProgramGlobalVariable, Integer> progGlobalsFct = fct2
                        .getProgramGlobals();

                if (fileGlobalsFct.containsKey(var)
                        || progGlobalsFct.containsKey(var))
                {
                    compo2.addRequiredInterface(interfaceVar);
                    nbAccessors++;
                }
            }
        }

        if (nbAccessors > 0)
        {
            compo1.addProvidedInterface(interfaceVar);
        }
    }

    /**
     * Fonction appelée à la création du dendogramme pour initialiser les
     * interfaces liées à un type d'un composant
     * 
     * <p>
     * Recherche les utilisation d'un type fourni par un composant. Une
     * interface requise est créée pour chaque composant qui utilise ce type.
     * Une interface fournie est créée pour le composant qui contient le type en
     * question.
     * </p>
     * 
     * @param idxNode
     *            Index dans le dendogramme du composant qui contient le type en
     *            question
     * 
     * @see #processInterfaces()
     */

    private void processInterfacesType(int idxNode)
    {
        Component compo1 = this.dendogram.get(idxNode).getComponent();
        Type type = compo1.getTypes().iterator().next();

        Interface interfaceType = new Interface();
        interfaceType.addType(type);

        int nbUsers = 0;

        for (int idx = 0; idx < this.dendogram.size(); ++idx)
        {
            Component compo2 = this.dendogram.get(idx).getComponent();

            // If compo2 contains no function, it cannot access var
            if (compo2.getFunctions().isEmpty() == false)
            {
                Function fct2 = compo2.getFunctions().iterator().next();

                Set<Type> typesFct = fct2.getUsedTypes();

                if (typesFct.contains(type))
                {
                    compo2.addRequiredInterface(interfaceType);
                    nbUsers++;
                }
            }
        }

        if (nbUsers > 0)
        {
            compo1.addProvidedInterface(interfaceType);
        }
    }

    private void clusterNodes(int i, int j)
    {
        Component compo1 = this.dendogram.get(i).getComponent();
        Component compo2 = this.dendogram.get(j).getComponent();

        Component newCompo = new Component();

        newCompo.addFunctions(compo1.getFunctions());
        newCompo.addFunctions(compo2.getFunctions());
        newCompo.addVariables(compo1.getVariables());
        newCompo.addVariables(compo2.getVariables());
        newCompo.addTypes(compo1.getTypes());
        newCompo.addTypes(compo2.getTypes());
        
        updateRequiredInterfaces(newCompo, compo1, compo2);
    }
    
    private void updateRequiredInterfaces(Component parent, Component child1,
            Component child2)
    {
        Set<Interface> reqIs1 = child1.getRequiredInterfaces();
        Set<Interface> proIs1 = child1.getProvidedInterfaces();
        Set<Interface> reqIs2 = child2.getRequiredInterfaces();
        Set<Interface> proIs2 = child2.getProvidedInterfaces();
        
        for(Interface reqI : reqIs1)
        {
            for(Interface proI : proIs2)
            {
                
            }
        }
        
        for(Interface reqI : reqIs2)
        {
            for(Interface proI : proIs1)
            {
                
            }
        }
    }
}
