package fr.univ_nantes.alma.archtool.architectureModel;

/**
 * A connection between components linked by a connector.
 * 
 * @author E10A345H
 * 
 */
public class Connection
{
    /**
     * Connected component
     */
    private Component component;

    /**
     * Interface used for the connection
     */
    private Interface compInterface;

    /**
     * Connected connector
     */
    private Connector connector;

    public Connection(Component comp, Connector con, Interface itf)
    {
        this.component = comp;
        this.connector = con;
        this.compInterface = itf;
    }
    
    public Component getComponent()
    {
        return this.component;
    }

    public Interface getInterface()
    {
        return this.compInterface;
    }

    public Connector getConnector()
    {
        return this.connector;
    }

    /**
     * Teste si un composant participe à la connexion.
     * 
     * @param comp
     *            Le composant recherché
     */
    public boolean participates(Component comp)
    {
        return this.component.equals(comp);
    }

    /**
     * Teste si un connecteur participe à la connexion.
     * 
     * @param con
     *            Le connecteur recherché
     */
    public boolean participates(Connector con)
    {
        return this.connector.equals(con);
    }
}
