package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.coa.COA;
import fr.univ_nantes.alma.archtool.utils.Pair;

public class Configuration
{
    private Set<Component> components = new HashSet<Component>();
    
    private Map<Connector, Set<Component>> connectorToComponents = 
        new HashMap<Connector, Set<Component>>();
    
    private COA coa;
    
    public Configuration(COA coa)
    {
        this.coa = coa;
    }
    
    /**
     * Retourne l'ensemble des composants.
     */
    public Set<Component> getComponents()
    {
        return new HashSet<Component>(this.components);
    }
    
    /**
     * Retourne le nombre de composants.
     */
    public int nbComponents()
    {
        return this.components.size();
    }

    /**
     * Ajoute un composant.
     */
    public boolean addComponent(Component comp)
    {
        comp.setCOA(coa);
        return this.components.add(comp);
    }

    /**
     * Retourne l'ensemble des connecteurs.
     */
    public Set<Connector> getConnectors()
    {
        return new HashSet<Connector>(this.connectorToComponents.keySet());
    }
    
    /**
     * Retourne le nombre de connecteurs.
     */
    public int nbConnectors()
    {
        return this.connectorToComponents.size();
    }

    /**
     * Ajoute un connecteur.
     */
    public boolean addConnector(Connector con)
    {
        boolean done = false;
        con.setCOA(this.coa);
        
        if(!this.connectorToComponents.containsKey(con))
        {
            this.connectorToComponents.put(con, 
                    new HashSet<Component>());
            done = true;
        }
        
        return done;
    }
    
    public Set<Pair<Connector, Component>> getConnections()
    {
        Set<Pair<Connector, Component>> connections = 
                new HashSet<Pair<Connector, Component>>();
        
        for(Connector con : this.connectorToComponents.keySet())
        {
            for(Component component : this.connectorToComponents.get(con))
            {
                 connections.add(new Pair<Connector, Component>(con,
                         component));
            }
        }

        return connections;
    }
    
    public boolean addConnection(Component comp, Connector con)
    {
        boolean done = false;
        
        if(this.connectorToComponents.containsKey(con))
        {
            this.connectorToComponents.get(con).add(comp);
        }
        
        return done;
    }

    /**
     * Retourne l'ensemble des connexions auxquelles participe un composant.
     * 
     * @param comp
     *            Le composant recherché
     */
    public Set<Pair<Connector, Component>> getConnections(Component comp)
    {
        Set<Pair<Connector, Component>> connections = 
                new HashSet<Pair<Connector, Component>>();

        if(this.components.contains(comp))
        {
            for(Connector con : this.connectorToComponents.keySet())
            {
                if(this.connectorToComponents.get(con).contains(comp))
                {
                    connections.add(new Pair<Connector, Component>(con, comp));
                }
            }
        }

        return connections;
    }

    /**
     * Retourne l'ensemble des connexions auxquelles participe un connecteur.
     * 
     * @param con
     *            Le composant recherché
     */
    public Set<Pair<Connector, Component>> getConnections(Connector con)
    {
        Set<Pair<Connector, Component>> connections = 
                new HashSet<Pair<Connector, Component>>();
        
        if(this.connectorToComponents.containsKey(con))
        {
            for(Component comp : this.connectorToComponents.get(con))
            {
                connections.add(new Pair<Connector, Component>(con, comp));
            }
        }

        return connections;
    }

    public boolean removeComponent(Component comp)
    {
        boolean done = false;
        
        if(this.components.contains(comp))
        {
            comp.clearInterfaces();
            /*Iterator<Connection> conIter = this.connections.iterator();
            
            while(conIter.hasNext())
            {
                Connection connection = conIter.next();
                
                if(connection.participates(comp))
                {
                    conIter.remove();
                }
            }*/
            
            comp.setCOA(null);
            
            for(Set<Component> comps : this.connectorToComponents.values())
            {
                comps.remove(comp);
            }

            this.components.remove(comp);
            this.coa.removeComponent(comp);
            done = true;
        }
                
        return done;
    }

    public boolean removeConnector(Connector con)
    {
        boolean done = false;
        
        if(this.connectorToComponents.containsKey(con))
        {
            con.clearFacades();
            /*Iterator<Connection> conIter = this.connections.iterator();
        
            while(conIter.hasNext())
            {
                Connection connection = conIter.next();
                
                if(connection.participates(con))
                {
                    conIter.remove();
                }
            }*/
            
            con.setCOA(null);
            this.connectorToComponents.remove(con);
            this.coa.removeConnector(con);
            done = true;
        }
                
        return done;
    }
    
    /**
     * Efface l'ensemble des interfaces et connecteurs.
     */
    public void clear()
    {
        for(Component comp : this.components)
        {
            comp.clearInterfaces();
        }
        
        Iterator<Connector> it = this.connectorToComponents.keySet().iterator();
        
        while(it.hasNext())
        {
            Connector con = it.next();
            con.clearFacades();
            this.coa.removeConnector(con);
            con.setCOA(null);
            it.remove();
        }
    }

    public Set<Component> getComponents(Connector con)
    {
        Set<Component> components = new HashSet<Component>();
        
        if(this.connectorToComponents.containsKey(con))
        {
            components.addAll(this.connectorToComponents.get(con));
        }
            
        return components;
    }

    public Set<Component> getComponents(Interface required)
    {
        Set<Component> components = new HashSet<Component>();
        
        for(Component component : this.components)
        {
            if(component.getRequiredInterfaces().contains(required))
            {
                components.add(component);
            }
        }
        
        return components;
    }
}
