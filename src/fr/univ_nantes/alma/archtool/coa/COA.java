package fr.univ_nantes.alma.archtool.coa;

import java.util.Iterator;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Connector;
import fr.univ_nantes.alma.archtool.architectureModel.Facade;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;

public class COA
{
    private SubCOA<Component> coaComponents = new SubCOA<Component>();
    
    private SubCOA<Interface> coaInterfaces = new SubCOA<Interface>();
    
    private SubCOA<Connector> coaConnectors = new SubCOA<Connector>();
    
    private SubCOA<Facade> coaFacades = new SubCOA<Facade>();
    
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
    public Set<GlobalVariable> getComponentGlobalVariables(Component comp)
    {
        return this.coaComponents.getGlobalVariables(comp);
    }
    
    /**
     * Retourne l'ensemble des types d'un composant.
     */
    public Set<ComplexType> getComponentComplexTypes(Component comp)
    {
        return this.coaComponents.getComplexTypes(comp);
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
    public Set<GlobalVariable> getInterfaceGlobalVariables(Interface itf)
    {
        return this.coaInterfaces.getGlobalVariables(itf);
    }
    
    /**
     * Retourne l'ensemble des types d'une interface.
     */
    public Set<ComplexType> getInterfaceComplexTypes(Interface itf)
    {
        return this.coaInterfaces.getComplexTypes(itf);
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
    public Set<GlobalVariable> getConnectorGlobalVariables(Connector con)
    {
        return this.coaConnectors.getGlobalVariables(con);
    }
    
    /**
     * Retourne l'ensemble des types d'une interface.
     */
    public Set<ComplexType> getConnectorComplexTypes(Connector con)
    {
        return this.coaConnectors.getComplexTypes(con);
    }
    
    /**
     * Retourne l'ensemble des fonctions d'une facade.
     */
    public Set<Function> getFacadeFunctions(Facade fcd)
    {
        return this.coaFacades.getFunctions(fcd);
    }
    
    /**
     * Retourne l'ensemble des variables d'une facade.
     */
    public Set<GlobalVariable> getFacadeGlobalVariables(Facade fcd)
    {
        return this.coaFacades.getGlobalVariables(fcd);
    }
    
    /**
     * Retourne l'ensemble des types d'une facade.
     */
    public Set<ComplexType> getFacadeComplexTypes(Facade fcd)
    {
        return this.coaFacades.getComplexTypes(fcd);
    }
    
    /**
     * Retourne le composant qui contient une fonction donnée.
     * 
     * @param fct La fonction recherchée.
     */
    public Component getComponent(final Function fct)
    {
        return this.coaComponents.getArchElement(fct);
    }

    /**
     * Retourne le composant qui contient une variable donnée.
     * 
     * @param var La variable recherchée.
     */
    public Component getComponent(final GlobalVariable var)
    {
        return this.coaComponents.getArchElement(var);
    }

    /**
     * Retourne le composant qui contient un type donné.
     * 
     * @param t Le type recherché.
     */
    public Component getComponent(final ComplexType t)
    {
        return this.coaComponents.getArchElement(t);
    }
    
    /**
     * Retourne l'interface qui contient une fonction donnée.
     * 
     * @param fct La fonction recherchée.
     */
    public Interface getInterface(final Function fct)
    {
        return this.coaInterfaces.getArchElement(fct);
    }

    /**
     * Retourne l'interface qui contient une variable donnée.
     * 
     * @param var La variable recherchée.
     */
    public Interface getInterface(final GlobalVariable var)
    {
        return this.coaInterfaces.getArchElement(var);
    }

    /**
     * Retourne l'interface qui contient un type donné.
     * 
     * @param t Le type recherché.
     */
    public Interface getInterface(final ComplexType t)
    {
        return this.coaInterfaces.getArchElement(t);
    }
    
    /**
     * Retourne le connecteur qui contient une fonction donnée.
     * 
     * @param fct La fonction recherchée.
     */
    public Connector getConnector(final Function fct)
    {
        return this.coaConnectors.getArchElement(fct);
    }

    /**
     * Retourne le connecteur qui contient une variable donnée.
     * 
     * @param var La variable recherchée.
     */
    public Connector getConnector(final GlobalVariable var)
    {
        return this.coaConnectors.getArchElement(var);
    }

    /**
     * Retourne le connecteur qui contient un type donné.
     * 
     * @param t Le type recherché.
     */
    public Connector getConnector(final ComplexType t)
    {
        return this.coaConnectors.getArchElement(t);
    }
    
    /**
     * Retourne la facade qui contient une fonction donnée.
     * 
     * @param fct La fonction recherchée.
     */
    public Facade getFacade(final Function fct)
    {
        return this.coaFacades.getArchElement(fct);
    }

    /**
     * Retourne la facade qui contient une variable donnée.
     * 
     * @param var La variable recherchée.
     */
    public Facade getFacade(final GlobalVariable var)
    {
        return this.coaFacades.getArchElement(var);
    }

    /**
     * Retourne la facade qui contient un type donné.
     * 
     * @param t Le type recherché.
     */
    public Facade getFacade(final ComplexType t)
    {
        return this.coaFacades.getArchElement(t);
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
    public boolean addGlobalVariable(GlobalVariable var, Component comp)
    {
        return this.coaComponents.addGlobalVariable(var, comp);
    }
    
    /**
     * Ajoute un ensemble de variables à un composant.
     */
    public void addGlobalVariables(Set<GlobalVariable> vars, Component comp)
    {
       for(GlobalVariable v : vars)
       {
           this.coaComponents.addGlobalVariable(v, comp);
       }
    }
    
    /**
     * Ajoute un type à un composant.
     */
    public boolean addComplexType(ComplexType t, Component comp)
    {
        return this.coaComponents.addComplexType(t, comp);
    }
    
    /**
     * Ajoute un ensemble de types à un composant.
     */
    public void addComplexTypes(Set<ComplexType> types, Component comp)
    {
       for(ComplexType t : types)
       {
           this.coaComponents.addComplexType(t, comp);
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
    public boolean addGlobalVariable(GlobalVariable var, Interface itf)
    {
        return this.coaInterfaces.addGlobalVariable(var, itf);
    }
    
    /**
     * Ajoute un ensemble de variables à une interface.
     */
    public void addGlobalVariables(Set<GlobalVariable> vars, Interface itf)
    {
       for(GlobalVariable v : vars)
       {
           this.coaInterfaces.addGlobalVariable(v, itf);
       }
    }
    
    /**
     * Ajoute un type à une interface.
     */
    public boolean addComplexType(ComplexType t, Interface itf)
    {
        return this.coaInterfaces.addComplexType(t, itf);
    }
    
    /**
     * Ajoute un ensemble de types à une interface.
     */
    public void addComplexTypes(Set<ComplexType> types, Interface itf)
    {
       for(ComplexType t : types)
       {
           this.coaInterfaces.addComplexType(t, itf);
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
    public boolean addGlobalVariable(GlobalVariable var, Connector con)
    {
        return this.coaConnectors.addGlobalVariable(var, con);
    }
    
    /**
     * Ajoute un ensemble de variables à un connecteur.
     */
    public void addGlobalVariables(Set<GlobalVariable> vars, Connector con)
    {
       for(GlobalVariable v : vars)
       {
           this.coaConnectors.addGlobalVariable(v, con);
       }
    }
    
    /**
     * Ajoute un type à un connecteur.
     */
    public boolean addComplexType(ComplexType t, Connector con)
    {
        return this.coaConnectors.addComplexType(t, con);
    }
    
    /**
     * Ajoute un ensemble de types à un connecteur.
     */
    public void addComplexTypes(Set<ComplexType> types, Connector con)
    {
       for(ComplexType t : types)
       {
           this.coaConnectors.addComplexType(t, con);
       }
    }
    
    /**
     * Ajoute une fonction à une facade.
     */
    public boolean addFunction(Function fct, Facade itf)
    {
        return this.coaFacades.addFunction(fct, itf);
    }
    
    /**
     * Ajoute un ensemble de fonctions à une facade.
     */
    public void addFunctions(Set<Function> fcts, Facade itf)
    {
       for(Function f : fcts)
       {
           this.coaFacades.addFunction(f, itf);
       }
    }
    
    /**
     * Ajoute une variable à une facade.
     */
    public boolean addGlobalVariable(GlobalVariable var, Facade itf)
    {
        return this.coaFacades.addGlobalVariable(var, itf);
    }
    
    /**
     * Ajoute un ensemble de variables à une facade.
     */
    public void addGlobalVariables(Set<GlobalVariable> vars, Facade itf)
    {
       for(GlobalVariable v : vars)
       {
           this.coaFacades.addGlobalVariable(v, itf);
       }
    }
    
    /**
     * Ajoute un type à une facade.
     */
    public boolean addComplexType(ComplexType t, Facade itf)
    {
        return this.coaFacades.addComplexType(t, itf);
    }
    
    /**
     * Ajoute un ensemble de types à une facade.
     */
    public void addComplexTypes(Set<ComplexType> types, Facade itf)
    {
       for(ComplexType t : types)
       {
           this.coaFacades.addComplexType(t, itf);
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
    public boolean removeGlobalVariable(GlobalVariable var, Component comp)
    {
        return this.coaComponents.removeGlobalVariable(var, comp);
    }
    
    /**
     * Retire un type d'un composant.
     */
    public boolean removeComplexType(ComplexType t, Component comp)
    {
        return this.coaComponents.removeComplexType(t, comp);
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
    public boolean removeGlobalVariable(GlobalVariable var, Interface itf)
    {
        return this.coaInterfaces.removeGlobalVariable(var, itf);
    }
    
    /**
     * Retire un type d'une interface.
     */
    public boolean removeComplexType(ComplexType t, Interface itf)
    {
        return this.coaInterfaces.removeComplexType(t, itf);
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
    public boolean removeGlobalVariable(GlobalVariable var, Connector con)
    {
        return this.coaConnectors.removeGlobalVariable(var, con);
    }
    
    /**
     * Retire un type d'un connecteur.
     */
    public boolean removeComplexType(ComplexType t, Connector con)
    {
        return this.coaConnectors.removeComplexType(t, con);
    }
    
    /**
     * Retire une fonction d'une facade.
     */
    public boolean removeFunction(Function fct, Facade fcd)
    {
        return this.coaFacades.removeFunction(fct, fcd);
    }
    
    /**
     * Retire une variable d'une facade.
     */
    public boolean removeGlobalVariable(GlobalVariable var, Facade fcd)
    {
        return this.coaFacades.removeGlobalVariable(var, fcd);
    }
    
    /**
     * Retire un type d'une facade.
     */
    public boolean removeComplexType(ComplexType t, Facade fcd)
    {
        return this.coaFacades.removeComplexType(t, fcd);
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
        return this.coaComponents.moveGlobalVariable(var, from, to);
    }
    
    /**
     * Déplace un type d'un composant à un autre.
     */
    public boolean moveType(ComplexType t, Component from, Component to)
    {
        return this.coaComponents.moveComplexType(t, from, to);
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
        return this.coaInterfaces.moveGlobalVariable(var, from, to);
    }
    
    /**
     * Déplace un type d'une interface à une autre.
     */
    public boolean moveType(ComplexType t, Interface from, Interface to)
    {
        return this.coaInterfaces.moveComplexType(t, from, to);
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
        return this.coaConnectors.moveGlobalVariable(var, from, to);
    }
    
    /**
     * Déplace un type d'un connecteur à un autre.
     */
    public boolean moveType(ComplexType t, Connector from, Connector to)
    {
        return this.coaConnectors.moveComplexType(t, from, to);
    }
    
    /**
     * Déplace une fonction d'une facade à une autre.
     */
    public boolean moveFunction(Function fct, Facade from, Facade to)
    {
        return this.coaFacades.moveFunction(fct, from, to);
    }
    
    /**
     * Déplace une variable d'une facade à une autre.
     */
    public boolean moveVariable(GlobalVariable var, Facade from, Facade to)
    {
        return this.coaFacades.moveGlobalVariable(var, from, to);
    }
    
    /**
     * Déplace un type d'une facade à une autre.
     */
    public boolean moveType(ComplexType t, Facade from, Facade to)
    {
        return this.coaFacades.moveComplexType(t, from, to);
    }
    
    /**
     * Ajoute un composant au COA.
     */
    public boolean addComponent(Component comp)
    {
       return this.coaComponents.newArchElement(comp);
    }
    
    /**
     * Ajoute une interface au COA.
     */
    public boolean addInterface(Interface itf)
    {
        return this.coaInterfaces.newArchElement(itf);
    }

    /**
     * Ajoute un connecteur au COA.
     */
    public boolean addConnector(Connector con)
    {
        return this.coaConnectors.newArchElement(con);
    }
    
    /**
     * Ajoute une facade au COA.
     */
    public boolean addFacade(Facade fcd)
    {
        return this.coaFacades.newArchElement(fcd);
    }
    
    /**
     * Supprime un composant du COA.
     */
    public boolean removeComponent(Component comp)
    {
       return this.coaComponents.removeArchElement(comp);
    }
    
    /**
     * Supprime une interface du COA.
     */
    public boolean removeInterface(Interface itf)
    {
       return this.coaInterfaces.removeArchElement(itf);
    }
    
    /**
     * Supprime un connecteur du COA.
     */
    public boolean removeConnector(Connector con)
    {
       return this.coaConnectors.removeArchElement(con);
    }
    
    /**
     * Supprime une facade du COA.
     */
    public boolean removeFacade(Facade fcd)
    {
       return this.coaFacades.removeArchElement(fcd);
    }
    
    /**
     * Vérifie si une interface est encore utile.
     * 
     * <p>
     * Méthode à appeler après avoir retiré une interface d'un composant.
     * </p>
     */
    public void checkInterface(Interface itf)
    {
        boolean found = false;
        
        Iterator<Component> it = 
                this.coaComponents.getaArchElements().iterator();
        
        while(it.hasNext() && found == false)
        {
            Component comp = it.next();
            found = comp.requiresInterface(itf) || comp.providesInterface(itf);
        }
        
        if(found == false)
        {
            this.removeInterface(itf);
        }
    }
    
    public Set<Function> getFunctionsToOut(Component component)
    {
        return this.coaComponents.getFunctionsToOut(component);
    }
    
    public Set<GlobalVariable> getGlobalsToOut(Component component)
    {
        return this.coaComponents.getGlobalVariablesToOut(component);
    }
    
    public Set<Function> getFunctionsToIn(Component component)
    {
        return this.coaComponents.getFunctionsToIn(component);
    }
    
    public Set<GlobalVariable> getGlobalsToIn(Component component)
    {
        return this.coaComponents.getGlobalVariablesToIn(component);
    }
    
    public Set<ComplexType> getTypesToIn(Component component)
    {
        return this.coaComponents.getComplexTypesToIn(component);
    }
    
    public String toString()
    {
        StringBuffer buf = new StringBuffer("COA [");
        
        buf.append(this.coaComponents);
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
