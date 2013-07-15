package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.coa.COA;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.utils.Pair;

public class Architecture implements Cloneable
{
    private COA coa = new COA();
    
    private Configuration configuration = new Configuration(this.coa);

    /**
     * Retourne l'ensemble des composants de l'architecture.
     */
    public Set<Component> getComponents()
    {
        return this.configuration.getComponents();
    }
    
    /**
     * Retourne l'ensemble des composants qui requiert cette interface.
     */
    public Set<Component> getComponents(Interface required)
    {
        return this.configuration.getComponents(required);
    }
    
    /**
     * Retourne le nombre de composants de l'architecture.
     */
    public int nbComponents()
    {
        return this.configuration.nbComponents();
    }

    /**
     * Ajoute un composant.
     */
    public boolean addComponent(Component comp)
    {
        return this.configuration.addComponent(comp);
    }
    
    /**
     * Supprime un composant.
     */
    public boolean removeComponent(Component comp)
    {
        return this.configuration.removeComponent(comp);
    }
    
    /**
     * Retourne l'ensemble des composants reliés par un connecteur donné.
     */
    public Set<Component> getComponents(Connector con)
    {
        return this.configuration.getComponents(con);
    }

    /**
     * Retourne l'ensemble des connecteurs de l'architecture.
     */
    public Set<Connector> getConnectors()
    {
        return this.configuration.getConnectors();
    }
    
    /**
     * Retourne le nombre de connecteurs de l'architecture.
     */
    public int nbConnectors()
    {
        return this.configuration.nbConnectors();
    }

    /**
     * Ajoute un connecteur.
     */
    public boolean addConnector(Connector con)
    {
        return this.configuration.addConnector(con);
    }
    
    /**
     * Supprime un connecteur.
     */
    public boolean removeConnector(Connector con)
    {
        return this.configuration.removeConnector(con);
    }
    
    /**
     * Retourne la configuration.
     */
    public Configuration getConfiguration()
    {
        return this.configuration;
    }

    /**
     * Crée une nouvelle connexion entre un composant et un connecteur.
     * 
     * @param comp
     *            Le composant à connecter
     * @param con
     *            Le connecteur à connecter
     * @param itf
     *            L'interface par lequel le composant est connecté
     */
    public boolean addConnection(Component comp, Connector con)
    {
        return this.configuration.addConnection(comp, con);
    }
    
    public Component getComponent(final Function fct)
    {
        return this.coa.getComponent(fct);
    }
    
    public Component getComponent(final GlobalVariable var)
    {
        return this.coa.getComponent(var);
    }
    
    public Component getComponent(final ComplexType t)
    {
        return this.coa.getComponent(t);
    }
    
    public Interface getInterface(final Function fct)
    {
        return this.coa.getInterface(fct);
    }

    public Interface getInterface(final GlobalVariable var)
    {
        return this.coa.getInterface(var);
    }

    public Interface getInterface(final ComplexType t)
    {
        return this.coa.getInterface(t);
    }
    
    public Connector getConnector(final Function fct)
    {
        return this.coa.getConnector(fct);
    }

    public Connector getConnector(final GlobalVariable var)
    {
        return this.coa.getConnector(var);
    }

    public Connector getConnector(final ComplexType t)
    {
        return this.coa.getConnector(t);
    }
    
    public boolean moveFunction(Function fct, Component from, Component to)
    {
        return this.coa.moveFunction(fct, from, to);
    }
    
    public boolean moveVariable(GlobalVariable var, Component from,
            Component to)
    {
        return this.coa.moveVariable(var, from, to);
    }
    
    public boolean moveType(ComplexType t, Component from, Component to)
    {
        return this.coa.moveType(t, from, to);
    }
    
    public boolean moveFunction(Function fct, Interface from, Interface to)
    {
        return this.coa.moveFunction(fct, from, to);
    }
    
    public boolean moveVariable(GlobalVariable var, Interface from,
            Interface to)
    {
        return this.coa.moveVariable(var, from, to);
    }
    
    public boolean moveType(ComplexType t, Interface from, Interface to)
    {
        return this.coa.moveType(t, from, to);
    }
    
    public boolean moveFunction(Function fct, Connector from, Connector to)
    {
        return this.coa.moveFunction(fct, from, to);
    }
    
    public boolean moveVariable(GlobalVariable var, Connector from,
            Connector to)
    {
        return this.coa.moveVariable(var, from, to);
    }
    
    public boolean moveType(ComplexType t, Connector from, Connector to)
    {
        return this.coa.moveType(t, from, to);
    }
    
    /**
     * Efface l'ensemble des interfaces et connecteurs de l'architecture.
     */
    public void clear()
    {
        this.configuration.clear();
    }
    
    @Override
    public Object clone()
    {
        Architecture cloned = new Architecture();
        Map<Component, Component> originalComponentToClone = 
                new HashMap<Component, Component>();
        Map<Connector, Connector> originalConnectorToClone = 
                new HashMap<Connector, Connector>();
        Map<Interface, Interface> originalInterfaceToClone = 
                new HashMap<Interface, Interface>();
        
        // Clone components
        for(Component component : this.getComponents())
        {
            Component clonedComponent = new Component();
            originalComponentToClone.put(component, clonedComponent);
            cloned.addComponent(clonedComponent);
            
            for(Interface required : component.getRequiredInterfaces())
            {
                Interface clonedRequired = null;
                
                if(originalInterfaceToClone.containsKey(required))
                {
                    clonedRequired = originalInterfaceToClone.get(required);
                }
                else
                {
                    clonedRequired = new Interface();
                    originalInterfaceToClone.put(required, clonedRequired);
                }
                
                clonedComponent.addRequiredInterface(clonedRequired);
                clonedRequired.addFunctions(required.getFunctions());
                clonedRequired.addComplexTypes(required.getComplexTypes());
                clonedRequired.addGlobalVariables(
                        required.getGlobalVariables());
            }
            
            for(Interface provided : component.getProvidedInterfaces())
            {
                Interface clonedProvided = null;
                
                if(originalInterfaceToClone.containsKey(provided))
                {
                    clonedProvided = originalInterfaceToClone.get(provided);
                }
                else
                {
                    clonedProvided = new Interface();
                    originalInterfaceToClone.put(provided, clonedProvided);
                }
                
                clonedComponent.addProvidedInterface(clonedProvided);
                clonedProvided.addFunctions(provided.getFunctions());
                clonedProvided.addComplexTypes(provided.getComplexTypes());
                clonedProvided.addGlobalVariables(
                        provided.getGlobalVariables());
            }
            
            clonedComponent.addFunctions(component.getFunctions());
            clonedComponent.addComplexTypes(component.getComplexTypes());
            clonedComponent.addGlobalVariables(component.getGlobalVariables()); 
        }
        
        // Clone connectors
        for(Connector connector : this.getConnectors())
        {
            Connector clonedConnector = new Connector();
            originalConnectorToClone.put(connector, clonedConnector);
            cloned.addConnector(clonedConnector);
            
            for(Facade facade : connector.getFacades())
            {
                Facade clonedFacade = new Facade();        
                clonedConnector.addFacade(clonedFacade);
                clonedFacade.addFunctions(facade.getFunctions());
                clonedFacade.addComplexTypes(facade.getComplexTypes());
                clonedFacade.addGlobalVariables(facade.getGlobalVariables());
            }
            
            clonedConnector.addFunctions(connector.getFunctions());
            clonedConnector.addComplexTypes(connector.getComplexTypes());
            clonedConnector.addGlobalVariables(connector.getGlobalVariables()); 
        }
        
        // Clone connections
        for(Pair<Connector, Component> connection : 
            this.configuration.getConnections())
        {
            Component clonedComponent = 
                    originalComponentToClone.get(connection.second);
            Connector clonedConnector = 
                    originalConnectorToClone.get(connection.first);
            
            cloned.addConnection(clonedComponent, clonedConnector);
        }
        
        return cloned;
    }

    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer("Architecture [\n");

        for (Component comp : this.configuration.getComponents())
        {
            buf.append(comp);
            buf.append("\n");
        }

        for (Connector con : this.configuration.getConnectors())
        {
            buf.append(con);
            buf.append("\n");
        }

        buf.append("]");

        return buf.toString();
    }
}
