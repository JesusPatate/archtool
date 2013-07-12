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
     * Retourne l'ensemble des connexions auxquelles participe un composant.
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
        Iterator<Connection> conIter = this.connections.iterator();
        
        while(conIter.hasNext())
        {
            Connection connection = conIter.next();
            
            if(connection.participates(comp))
            {
                this.connections.remove(connection);
            }
        }
        
        return this.components.remove(comp);
    }

    public boolean removeConnector(Connector con)
    {
        Iterator<Connection> conIter = this.connections.iterator();
        
        while(conIter.hasNext())
        {
            Connection connection = conIter.next();
            
            if(connection.participates(con))
            {
                this.connections.remove(connection);
            }
        }
        
        return this.connectors.remove(con);
    }
}
