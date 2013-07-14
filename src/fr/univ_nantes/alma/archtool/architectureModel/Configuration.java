package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.coa.COA;
import fr.univ_nantes.alma.archtool.utils.Pair;
import fr.univ_nantes.alma.archtool.utils.Triple;

public class Configuration
{
    private Set<Component> components = new HashSet<Component>();
    
    private Map<Connector, Set<Pair<Component, Interface>>> 
        connectorToComponents = 
        new HashMap<Connector, Set<Pair<Component, Interface>>>();
    
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
                    new HashSet<Pair<Component, Interface>>());
            done = true;
        }
        
        return done;
    }
    
    public Set<Triple<Connector, Component, Interface>> getConnections()
    {
        Set<Triple<Connector, Component, Interface>> connections = 
                new HashSet<Triple<Connector, Component, Interface>>();
        
        for(Connector con : this.connectorToComponents.keySet())
        {
            for(Pair<Component, Interface> connect : 
                this.connectorToComponents.get(con))
            {
                 connections.add(
                         new Triple<Connector, Component, Interface>(con,
                         connect.first, connect.second));
            }
        }

        return connections;
    }
    
    public boolean addConnection(Component comp, Connector con, Interface itf)
    {
        boolean done = false;
        
        if(this.connectorToComponents.containsKey(con))
        {
            this.connectorToComponents.get(con).add(
                    new Pair<Component, Interface>(comp, itf));
        }
        
        return done;
    }

    /**
     * Retourne l'ensemble des connexions auxquelles participe un composant.
     * 
     * @param comp
     *            Le composant recherché
     */
    public Set<Triple<Connector, Component, Interface>> getConnections(
            Component comp)
    {
        Set<Triple<Connector, Component, Interface>> connections = 
                new HashSet<Triple<Connector, Component, Interface>>();

        if(this.components.contains(comp))
        {
            for(Connector con : this.connectorToComponents.keySet())
            {
                for(Pair<Component, Interface> connect : 
                    this.connectorToComponents.get(con))
                {
                    if(connect.first == comp)
                    {
                        connections.add(
                                new Triple<Connector, Component, Interface>(con, 
                                connect.first, connect.second));
                    }
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
    public Set<Triple<Connector, Component, Interface>> getConnections(
            Connector con)
    {
        Set<Triple<Connector, Component, Interface>> connections = 
                new HashSet<Triple<Connector, Component, Interface>>();
        
        if(this.connectorToComponents.containsKey(con))
        {
            for(Pair<Component, Interface> connect : 
                this.connectorToComponents.get(con))
            {
                connections.add(new Triple<Connector, Component, Interface>(con, 
                        connect.first, connect.second));
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
            
            for(Set<Pair<Component, Interface>> connections 
                    : this.connectorToComponents.values())
            {
                Iterator<Pair<Component, Interface>> conIter =
                        connections.iterator();
                
                while(conIter.hasNext())
                {
                    Pair<Component, Interface> con = conIter.next();
                    
                    if(con.first == comp)
                    {
                        conIter.remove();
                    }
                }
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
            for(Pair<Component, Interface> connection : 
                this.connectorToComponents.get(con))
            {
                components.add(connection.first);
            }
        }
            
        return components;
    }
}
