package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.HashSet;
import java.util.Set;

public class ArchitectureImpl implements Architecture
{
    private Set<Component> components = new HashSet<Component>();
    
    private Set<Connector> connectors = new HashSet<Connector>();
    
    private Configuration configuration = new Configuration();
    
    public Set<Component> getComponents()
    {
        return new HashSet<Component>(this.components);
    }
    
    public Set<Connector> getConnectors()
    {
        return new HashSet<Connector>(this.connectors);
    }
    
    public boolean addConnection(Component comp, Connector con, Interface itf)
    {
        if(this.components.contains(comp) == false)
        {
            this.components.add(comp);
        }
        
        if(this.connectors.contains(con) == false)
        {
            this.connectors.add(con);
        }
        
        return this.configuration.addConnection(comp, con, itf);
    }
    
    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer("Architecture [\n");
        
        for(Component comp : this.components)
        {
            buf.append(comp);
            buf.append("\n");
        }
        
        for(Connector con : this.connectors)
        {
            buf.append(con);
            buf.append("\n");
        }
        
        buf.append("]");
        
        return buf.toString();
    }

    @Override
    public Component newComponent()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Connector newConnector()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
