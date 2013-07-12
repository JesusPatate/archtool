package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.Set;

import fr.univ_nantes.alma.archtool.coa.COA;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;

public class Architecture
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
        return this.coa.addComponent(comp) && 
                this.configuration.addComponent(comp);
    }
    
    /**
     * Supprime un composant.
     */
    public boolean removeComponent(Component comp)
    {
       return this.coa.removeComponent(comp) &&
               this.configuration.removeComponent(comp);
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
        return this.coa.addConnector(con) && 
                this.configuration.addConnector(con);
    }
    
    /**
     * Supprime un connecteur.
     */
    public boolean removeConnector(Connector con)
    {
       return this.coa.removeConnector(con) &&
               this.configuration.removeConnector(con);
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
    
    public boolean moveVariable(GlobalVariable var, Component from, Component to)
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
    
    public boolean moveVariable(GlobalVariable var, Interface from, Interface to)
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
    
    public boolean moveVariable(GlobalVariable var, Connector from, Connector to)
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
