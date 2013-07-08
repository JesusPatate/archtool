package fr.univ_nantes.alma.archtool.coa;

import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Connector;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Type;

public class COA
{
    public COAComponents coaComponents = new COAComponents();
    
    public COAInterfaces coaInterfaces = new COAInterfaces();
    
    public COAConnectors coaConnectors = new COAConnectors();
    
    /**
     * Retourne l'ensemble des fonctions d'un composant.
     */
    public Set<Function> getComponentFunctions(Component comp)
    {
        return this.coaComponents.getComponentFunctions(comp);
    }
    
    /**
     * Retourne l'ensemble des variables d'un composant.
     */
    public Set<GlobalVariable> getComponentVariables(Component comp)
    {
        return this.coaComponents.getComponentVariables(comp);
    }
    
    /**
     * Retourne l'ensemble des types d'un composant.
     */
    public Set<Type> getComponentTypes(Component comp)
    {
        return this.coaComponents.getComponentTypes(comp);
    }
    
    /**
     * Retourne l'ensemble des fonctions d'une interface.
     */
    public Set<Function> getInterfaceFunctions(Interface itf)
    {
        return this.coaInterfaces.getInterfaceFunctions(itf);
    }
    
    /**
     * Retourne l'ensemble des variables d'une interface.
     */
    public Set<GlobalVariable> getInterfaceVariables(Interface itf)
    {
        return this.coaInterfaces.getInterfaceVariables(itf);
    }
    
    /**
     * Retourne l'ensemble des types d'une interface.
     */
    public Set<Type> getInterfaceTypes(Interface itf)
    {
        return this.coaInterfaces.getInterfaceTypes(itf);
    }
    
    /**
     * Retourne l'ensemble des fonctions d'un connecteur.
     */
    public Set<Function> getConnectorFunctions(Connector con)
    {
        return this.coaConnectors.getConnectorFunctions(con);
    }
    
    /**
     * Retourne l'ensemble des variables d'une interface.
     */
    public Set<GlobalVariable> getConnectorVariables(Connector con)
    {
        return this.coaConnectors.getConnectorVariables(con);
    }
    
    /**
     * Retourne l'ensemble des types d'une interface.
     */
    public Set<Type> getConnectorTypes(Connector con)
    {
        return this.coaConnectors.getConnectorTypes(con);
    }
    
    /**
     * Ajoute une fonction à un composant.
     */
    public boolean addFunction(Function fct, Component comp)
    {
        return this.coaComponents.addFunction(fct, comp);
    }
    
    /**
     * Ajoute un ensemble de fonctions à un composant.
     */
    public void addFunctions(Set<Function> fcts, Component comp)
    {
       for(Function f : fcts)
       {
           this.coaComponents.addFunction(f, comp);
       }
    }
    
    /**
     * Ajoute une variable à un composant.
     */
    public boolean addVariable(GlobalVariable var, Component comp)
    {
        return this.coaComponents.addVariable(var, comp);
    }
    
    /**
     * Ajoute un ensemble de variables à un composant.
     */
    public void addVariables(Set<GlobalVariable> vars, Component comp)
    {
       for(GlobalVariable v : vars)
       {
           this.coaComponents.addVariable(v, comp);
       }
    }
    
    /**
     * Ajoute un type à un composant.
     */
    public boolean addType(Type t, Component comp)
    {
        return this.coaComponents.addType(t, comp);
    }
    
    /**
     * Ajoute un ensemble de types à un composant.
     */
    public void addTypes(Set<Type> types, Component comp)
    {
       for(Type t : types)
       {
           this.coaComponents.addType(t, comp);
       }
    }
    
    /**
     * Ajoute une fonction à une interface.
     */
    public boolean addFunction(Function fct, Interface itf)
    {
        return this.coaInterfaces.addFunction(fct, itf);
    }
    
    /**
     * Ajoute un ensemble de fonctions à une interface.
     */
    public void addFunctions(Set<Function> fcts, Interface itf)
    {
       for(Function f : fcts)
       {
           this.coaInterfaces.addFunction(f, itf);
       }
    }
    
    /**
     * Ajoute une variable à une interface.
     */
    public boolean addVariable(GlobalVariable var, Interface itf)
    {
        return this.coaInterfaces.addVariable(var, itf);
    }
    
    /**
     * Ajoute un ensemble de variables à une interface.
     */
    public void addVariables(Set<GlobalVariable> vars, Interface itf)
    {
       for(GlobalVariable v : vars)
       {
           this.coaInterfaces.addVariable(v, itf);
       }
    }
    
    /**
     * Ajoute un type à une interface.
     */
    public boolean addType(Type t, Interface itf)
    {
        return this.coaInterfaces.addType(t, itf);
    }
    
    /**
     * Ajoute un ensemble de types à une interface.
     */
    public void addTypes(Set<Type> types, Interface itf)
    {
       for(Type t : types)
       {
           this.coaInterfaces.addType(t, itf);
       }
    }
    
    /**
     * Ajoute une fonction à un connecteur.
     */
    public boolean addFunction(Function fct, Connector con)
    {
        return this.coaConnectors.addFunction(fct, con);
    }
    
    /**
     * Ajoute un ensemble de fonctions à un connecteur.
     */
    public void addFunctions(Set<Function> fcts, Connector con)
    {
       for(Function f : fcts)
       {
           this.coaConnectors.addFunction(f, con);
       }
    }
    
    /**
     * Ajoute une variable à un connecteur.
     */
    public boolean addVariable(GlobalVariable var, Connector con)
    {
        return this.coaConnectors.addVariable(var, con);
    }
    
    /**
     * Ajoute un ensemble de variables à un connecteur.
     */
    public void addVariables(Set<GlobalVariable> vars, Connector con)
    {
       for(GlobalVariable v : vars)
       {
           this.coaConnectors.addVariable(v, con);
       }
    }
    
    /**
     * Ajoute un type à un connecteur.
     */
    public boolean addType(Type t, Connector con)
    {
        return this.coaConnectors.addType(t, con);
    }
    
    /**
     * Ajoute un ensemble de types à un connecteur.
     */
    public void addTypes(Set<Type> types, Connector con)
    {
       for(Type t : types)
       {
           this.coaConnectors.addType(t, con);
       }
    }
    
    /**
     * Retire une fonction d'un composant.
     */
    public boolean removeFunction(Function fct, Component comp)
    {
        return this.coaComponents.removeFunction(fct, comp);
    }
    
    /**
     * Retire une variable d'un composant.
     */
    public boolean removeVariable(GlobalVariable var, Component comp)
    {
        return this.coaComponents.removeVariable(var, comp);
    }
    
    /**
     * Retire un type d'un composant.
     */
    public boolean removeType(Type t, Component comp)
    {
        return this.coaComponents.removeType(t, comp);
    }
    
    /**
     * Retire une fonction d'une interface.
     */
    public boolean removeFunction(Function fct, Interface itf)
    {
        return this.coaInterfaces.removeFunction(fct, itf);
    }
    
    /**
     * Retire une variable d'une interface.
     */
    public boolean removeVariable(GlobalVariable var, Interface itf)
    {
        return this.coaInterfaces.removeVariable(var, itf);
    }
    
    /**
     * Retire un type d'une interface.
     */
    public boolean removeType(Type t, Interface itf)
    {
        return this.coaInterfaces.removeType(t, itf);
    }
    
    /**
     * Retire une fonction d'un connecteur.
     */
    public boolean removeFunction(Function fct, Connector con)
    {
        return this.coaConnectors.removeFunction(fct, con);
    }
    
    /**
     * Retire une variable d'un connecteur.
     */
    public boolean removeVariable(GlobalVariable var, Connector con)
    {
        return this.coaConnectors.removeVariable(var, con);
    }
    
    /**
     * Retire un type d'un connecteur.
     */
    public boolean removeType(Type t, Connector con)
    {
        return this.coaConnectors.removeType(t, con);
    }
    
    /**
     * Déplace une fonction d'un composant à un autre.
     */
    public boolean moveFunction(Function fct, Component from, Component to)
    {
        return this.coaComponents.moveFunction(fct, from, to);
    }
    
    /**
     * Déplace une variable d'un composant à un autre.
     */
    public boolean moveVariable(GlobalVariable var, Component from, Component to)
    {
        return this.coaComponents.moveVariable(var, from, to);
    }
    
    /**
     * Déplace un type d'un composant à un autre.
     */
    public boolean moveType(Type t, Component from, Component to)
    {
        return this.coaComponents.moveType(t, from, to);
    }
    
    /**
     * Déplace une fonction d'une interface à une autre.
     */
    public boolean moveFunction(Function fct, Interface from, Interface to)
    {
        return this.coaInterfaces.moveFunction(fct, from, to);
    }
    
    /**
     * Déplace une variable d'une interface à une autre.
     */
    public boolean moveVariable(GlobalVariable var, Interface from, Interface to)
    {
        return this.coaInterfaces.moveVariable(var, from, to);
    }
    
    /**
     * Déplace un type d'une interface à une autre.
     */
    public boolean moveType(Type t, Interface from, Interface to)
    {
        return this.coaInterfaces.moveType(t, from, to);
    }
    
    /**
     * Déplace une fonction d'un connecteur à un autre.
     */
    public boolean moveFunction(Function fct, Connector from, Connector to)
    {
        return this.coaConnectors.moveFunction(fct, from, to);
    }
    
    /**
     * Déplace une variable d'un connecteur à un autre.
     */
    public boolean moveVariable(GlobalVariable var, Connector from, Connector to)
    {
        return this.coaConnectors.moveVariable(var, from, to);
    }
    
    /**
     * Déplace un type d'un connecteur à un autre.
     */
    public boolean moveType(Type t, Connector from, Connector to)
    {
        return this.coaConnectors.moveType(t, from, to);
    }
    
    /**
     * Ajoute un composant au COA.
     */
    public boolean addComponent(Component comp)
    {
       return this.coaComponents.newComponent(comp);
    }
    
    /**
     * Supprime un composant.
     */
    public boolean removeComponent(Component comp)
    {
       return this.coaComponents.removeComponent(comp);
    }
    
    /**
     * Supprime une interface.
     */
    public boolean removeInterface(Interface itf)
    {
       return this.coaInterfaces.removeInterface(itf);
    }
    
    /**
     * Supprime un connecteur.
     */
    public boolean removeConnector(Connector con)
    {
       return this.coaConnectors.removeConnector(con);
    }
    
    /**
     * Ajoute une interface au COA.
     */
    public boolean addInterface(Interface itf)
    {
        return this.coaInterfaces.newInterface(itf);
    }

    /**
     * Ajoute un connecteur au COA.
     */
    public boolean addConnector(Connector con)
    {
        return this.coaConnectors.newConnector(con);
    }
    
    /**
     * Teste si un composant est présent dans le COA.
     */
    private boolean knows(Component comp)
    {
        return this.coaComponents.knows(comp);
    }
    
    /**
     * Teste si un connecteur est présent dans le COA.
     */
    private boolean knows(Connector con)
    {
        return this.coaConnectors.knows(con);
    }
    
    /**
     * Teste si une interface est présente dans le COA.
     */
    private boolean knows(Interface itf)
    {
        return this.coaInterfaces.knows(itf);
    }
}
