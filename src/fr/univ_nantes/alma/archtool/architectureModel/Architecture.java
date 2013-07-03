package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.HashSet;
import java.util.Set;

public class Architecture
{
    private Set<Component> components = new HashSet<Component>();
    
    private Set<Connector> connectors = new HashSet<Connector>();
    
    private Configuration configuration = new Configuration();
    
    public Set<Component> getComponents()
    {
        return new HashSet<Component>(this.components);
    }
    
    public boolean addComponent(Component comp)
    {
        return this.components.add(comp);
    }
    
    public Set<Connector> getConnectors()
    {
        return new HashSet<Connector>(this.connectors);
    }
    
    public boolean addConnector(Connector con)
    {
        return this.connectors.add(con);
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
        String str = "Architecture [\n";
        
        for(Component comp : this.components)
        {
            str += comp + "\n";
        }
        
        for(Connector con : this.connectors)
        {
            str += con + "\n";
        }
        
        str +=  "]";
        
        return str;
    }
}
