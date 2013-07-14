package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import fr.univ_nantes.alma.archtool.coa.COA;

public class Configuration
{
    private Set<Component> components = new HashSet<Component>();

    private Set<Connector> connectors = new HashSet<Connector>();

    private Set<Connection> connections = new HashSet<Connection>();
    
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
        return new HashSet<Connector>(this.connectors);
    }
    
    /**
     * Retourne le nombre de connecteurs.
     */
    public int nbConnectors()
    {
        return this.connectors.size();
    }

    /**
     * Ajoute un connecteur.
     */
    public boolean addConnector(Connector con)
    {
        con.setCOA(this.coa);
        return this.connectors.add(con);
    }
    
    public Set<Connection> getConnections()
    {
        return new HashSet<Connection>(this.connections);
    }
    
    public boolean addConnection(Component comp, Connector con, Interface itf)
    {
        return this.connections.add(new Connection(comp, con, itf));
    }

    /**
     * Retourne l'ensemble des connexions auxquelles participe un composant.
     * 
     * @param comp
     *            Le composant recherché
     */
    public Set<Connection> getConnections(Component comp)
    {
        Set<Connection> connections = new HashSet<Connection>();

        for (Connection c : this.connections)
        {
            if (c.participates(comp))
            {
                connections.add(c);
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
    public Set<Connection> getConnections(Connector con)
    {
        Set<Connection> connections = new HashSet<Connection>();

        for (Connection c : this.connections)
        {
            if (c.participates(con))
            {
                connections.add(c);
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
            this.components.remove(comp);
            done = true;
        }
                
        return done;
    }

    public boolean removeConnector(Connector con)
    {
        boolean done = false;
        
        if(this.connectors.contains(con))
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
            this.connectors.remove(con);
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
        
        Iterator<Connector> it = this.connectors.iterator();
        
        while(it.hasNext())
        {
            Connector con = it.next();
            con.clearFacades();
            con.setCOA(null);
            it.remove();
        }
    }
}
