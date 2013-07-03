package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.Set;

public interface Architecture
{
    public Set<Component> getComponents();
    
    public Set<Connector> getConnectors();
    
    public Component newComponent();
    
    public Connector newConnector();
}
