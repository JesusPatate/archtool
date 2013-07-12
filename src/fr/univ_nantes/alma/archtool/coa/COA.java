package fr.univ_nantes.alma.archtool.coa;

import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Connector;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;

public class COA
{
    private COAComponents coaComponents;
    
    private COAInterfaces coaInterfaces;
    
    private COAConnectors coaConnectors;
    
    
    private SourceCode sourceCode;
    
    public COA(SourceCode sourceCode)
    {
        this.sourceCode = sourceCode;
        this.coaComponents = new COAComponents(this.sourceCode);
        this.coaInterfaces = new COAInterfaces();
        this.coaConnectors = new COAConnectors();
    }
    
    public SourceCode getSourceCode()
    {
        return this.sourceCode;
    }
    
    /**
     * Retourne l'ensemble des fonctions d'un composant.
     */
    public Set<Function> getComponentFunctions(Component comp)
    {
        return this.coaComponents.getFunctions(comp);
    }
    
    /**
     * Retourne l'ensemble des variables d'un composant.
     */
    public Set<GlobalVariable> getComponentVariables(Component comp)
    {
        return this.coaComponents.getVariables(comp);
    }
    
    /**
     * Retourne l'ensemble des types d'un composant.
     */
    public Set<ComplexType> getComponentTypes(Component comp)
    {
        return this.coaComponents.getTypes(comp);
    }
    
    /**
     * Retourne l'ensemble des fonctions d'une interface.
     */
    public Set<Function> getInterfaceFunctions(Interface itf)
    {
        return this.coaInterfaces.getFunctions(itf);
    }
    
    /**
     * Retourne l'ensemble des variables d'une interface.
     */
    public Set<GlobalVariable> getInterfaceVariables(Interface itf)
    {
        return this.coaInterfaces.getVariables(itf);
    }
    
    /**
     * Retourne l'ensemble des types d'une interface.
     */
    public Set<ComplexType> getInterfaceTypes(Interface itf)
    {
        return this.coaInterfaces.getTypes(itf);
    }
    
    /**
     * Retourne l'ensemble des fonctions d'un connecteur.
     */
    public Set<Function> getConnectorFunctions(Connector con)
    {
        return this.coaConnectors.getFunctions(con);
    }
    
    /**
     * Retourne l'ensemble des variables d'une interface.
     */
    public Set<GlobalVariable> getConnectorVariables(Connector con)
    {
        return this.coaConnectors.getVariables(con);
    }
    
    /**
     * Retourne l'ensemble des types d'une interface.
     */
    public Set<ComplexType> getConnectorTypes(Connector con)
    {
        return this.coaConnectors.getTypes(con);
    }
    
    /**
     * Retourne le composant qui contient une fonction donnée.
     * 
     * @param fct La fonction recherchée.
     */
    public Component getComponent(final Function fct)
    {
        return this.coaComponents.getComponent(fct);
    }

    /**
     * Retourne le composant qui contient une variable donnée.
     * 
     * @param var La variable recherchée.
     */
    public Component getComponent(final GlobalVariable var)
    {
        return this.coaComponents.getComponent(var);
    }

    /**
     * Retourne le composant qui contient un type donné.
     * 
     * @param t Le type recherché.
     */
    public Component getComponent(final ComplexType t)
    {
        return this.coaComponents.getComponent(t);
    }
    
    /**
     * Retourne l'interface qui contient une fonction donnée.
     * 
     * @param fct La fonction recherchée.
     */
    public Interface getInterface(final Function fct)
    {
        return this.coaInterfaces.getInterface(fct);
    }

    /**
     * Retourne l'interface qui contient une variable donnée.
     * 
     * @param var La variable recherchée.
     */
    public Interface getInterface(final GlobalVariable var)
    {
        return this.coaInterfaces.getInterface(var);
    }

    /**
     * Retourne l'interface qui contient un type donné.
     * 
     * @param t Le type recherché.
     */
    public Interface getInterface(final ComplexType t)
    {
        return this.coaInterfaces.getInterface(t);
    }
    
    /**
     * Retourne le connecteur qui contient une fonction donnée.
     * 
     * @param fct La fonction recherchée.
     */
    public Connector getConnector(final Function fct)
    {
        return this.coaConnectors.getConnector(fct);
    }

    /**
     * Retourne le connecteur qui contient une variable donnée.
     * 
     * @param var La variable recherchée.
     */
    public Connector getConnector(final GlobalVariable var)
    {
        return this.coaConnectors.getConnector(var);
    }

    /**
     * Retourne le connecteur qui contient un type donné.
     * 
     * @param t Le type recherché.
     */
    public Connector getConnector(final ComplexType t)
    {
        return this.coaConnectors.getConnector(t);
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
    public boolean addType(ComplexType t, Component comp)
    {
        return this.coaComponents.addType(t, comp);
    }
    
    /**
     * Ajoute un ensemble de types à un composant.
     */
    public void addTypes(Set<ComplexType> types, Component comp)
    {
       for(ComplexType t : types)
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
    public boolean addType(ComplexType t, Interface itf)
    {
        return this.coaInterfaces.addType(t, itf);
    }
    
    /**
     * Ajoute un ensemble de types à une interface.
     */
    public void addTypes(Set<ComplexType> types, Interface itf)
    {
       for(ComplexType t : types)
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
    public boolean addType(ComplexType t, Connector con)
    {
        return this.coaConnectors.addType(t, con);
    }
    
    /**
     * Ajoute un ensemble de types à un connecteur.
     */
    public void addTypes(Set<ComplexType> types, Connector con)
    {
       for(ComplexType t : types)
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
    public boolean removeType(ComplexType t, Component comp)
    {
        return this.coaComponents.removeType(t, comp);
    }
    
    /**
     * Retire une fonction d'une interface.
     */
    public boolean removeFunction(Function fct, Interface itf)
    {
        boolean done = this.coaInterfaces.removeFunction(fct, itf);
        
        if(done && this.coaInterfaces.getFunctions(itf).isEmpty() &&
                this.coaInterfaces.getTypes(itf).isEmpty() &&
                this.coaInterfaces.getVariables(itf).isEmpty())
        {
            this.removeInterface(itf);
        }
        
        return done;
    }
    
    /**
     * Retire une variable d'une interface.
     */
    public boolean removeVariable(GlobalVariable var, Interface itf)
    {
        boolean done = this.coaInterfaces.removeVariable(var, itf);
        
        if(done && this.coaInterfaces.getFunctions(itf).isEmpty() &&
                this.coaInterfaces.getTypes(itf).isEmpty() &&
                this.coaInterfaces.getVariables(itf).isEmpty())
        {
            this.removeInterface(itf);
        }
        
        return done;
        
    }
    
    /**
     * Retire un type d'une interface.
     */
    public boolean removeType(ComplexType t, Interface itf)
    {
        boolean done = this.coaInterfaces.removeType(t, itf);
        
        if(done && this.coaInterfaces.getFunctions(itf).isEmpty() &&
                this.coaInterfaces.getTypes(itf).isEmpty() &&
                this.coaInterfaces.getVariables(itf).isEmpty())
        {
            this.removeInterface(itf);
        }
        
        return done;
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
    public boolean removeType(ComplexType t, Connector con)
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
    public boolean moveType(ComplexType t, Component from, Component to)
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
    public boolean moveType(ComplexType t, Interface from, Interface to)
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
    public boolean moveType(ComplexType t, Connector from, Connector to)
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
    
    public Set<Function> getFunctionsToOut(Component component)
    {
        return this.coaComponents.getFunctionsToOut(component);
    }
    
    public Set<GlobalVariable> getGlobalsToOut(Component component)
    {
        return this.coaComponents.getGlobalsToOut(component);
    }
    
    public Set<Function> getFunctionsToIn(Component component)
    {
        return this.coaComponents.getFunctionsToIn(component);
    }
    
    public Set<GlobalVariable> getGlobalsToIn(Component component)
    {
        return this.coaComponents.getGlobalsToIn(component);
    }
    
    public String toString()
    {
        StringBuffer buf = new StringBuffer("COA [");
        
        buf.append(this.coaComponents);
        buf.append(this.coaInterfaces);
        buf.append(this.coaConnectors);
        
        int idx = buf.lastIndexOf(",");
        
        if(idx > 0)
        {
            buf.delete(idx, buf.length());
        }
        
        buf.append("]");
        
        return buf.toString();
    }
}
