package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.HashSet;
import java.util.Set;

public class Architecture
{
    private Set<Component> components = new HashSet<Component>();

    private Set<Connector> connectors = new HashSet<Connector>();

    private Configuration configuration = new Configuration();

    /**
     * Retourne l'ensemble des composants de l'architecture.
     */
    public Set<Component> getComponents()
    {
        return new HashSet<Component>(this.components);
    }

    /**
     * Ajoute un composant.
     */
    public boolean addComponent(Component comp)
    {
        return this.components.add(comp);
    }

    /**
     * Ajoute une nouvelle interface fournie à un composant.
     * 
     * @param itf
     *            L'interface à ajouter
     * @param comp
     *            Le composant auquel ajouter la nouvelle interface
     */
    public boolean addProvidedInterface(Interface itf, Component comp)
    {
        return comp.addProvidedInterface(itf);
    }

    /**
     * Ajoute une nouvelle interface requise à un composant.
     * 
     * @param itf
     *            L'interface à ajouter
     * @param comp
     *            Le composant auquel ajouter la nouvelle interface
     */
    public boolean addRequiredInterface(Interface itf, Component comp)
    {
        return comp.addRequiredInterface(itf);
    }

    /**
     * Retourne l'ensemble des connecteurs de l'architecture.
     */
    public Set<Connector> getConnectors()
    {
        return new HashSet<Connector>(this.connectors);
    }

    /**
     * Ajoute un connecteur.
     */
    public boolean addConnector(Connector con)
    {
        return this.connectors.add(con);
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
    public boolean addConnection(Component comp, Connector con, Interface itf)
    {
        boolean done = false;
        
        if(comp.providesInterface(itf))
        {
            done = this.configuration.addConnection(comp, con, itf);
        }
        
        return done;
    }

    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer("Architecture [\n");

        for (Component comp : this.components)
        {
            buf.append(comp);
            buf.append("\n");
        }

        for (Connector con : this.connectors)
        {
            buf.append(con);
            buf.append("\n");
        }

        buf.append("]");

        return buf.toString();
    }
}
