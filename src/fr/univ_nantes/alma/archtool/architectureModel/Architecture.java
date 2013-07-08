package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.Set;

public class Architecture
{
    private Configuration configuration = new Configuration();

    /**
     * Retourne l'ensemble des composants de l'architecture.
     */
    public Set<Component> getComponents()
    {
        return this.configuration.getComponents();
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
        return this.configuration.addConnection(comp, con, itf);
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
