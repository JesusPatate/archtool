package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.HashSet;
import java.util.Set;

public class Configuration
{
    private Set<Component> components = new HashSet<Component>();

    private Set<Connector> connectors = new HashSet<Connector>();

    private Set<Connection> connections = new HashSet<Connection>();
    
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
}
