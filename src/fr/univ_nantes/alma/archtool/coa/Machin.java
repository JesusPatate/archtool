package fr.univ_nantes.alma.archtool.coa;

import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.architectureModel.ArchitectureImpl;
import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Connector;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;

public class Machin implements Architecture
{
    private Architecture architecture = new ArchitectureImpl();
    
    private SourceCode sourceCode;
    
    public COAComponents coaComponents = new COAComponents();
    
    public COAInterfaces coaInterfaces = new COAInterfaces();
    
    public COAConnectors coaConnectors = new COAConnectors();
    
    public Machin(SourceCode sourceCode)
    {
        this.sourceCode = sourceCode;
    }
    
    /**
     * Ajoute une fonction à un composant.
     */
    public boolean addFunction(Function fct, Component comp)
    {
        return this.coaComponents.addFunction(fct, comp);
    }
    
    /**
     * Ajoute une fonction à une interface.
     */
    public boolean addFunction(Function fct, Interface itf)
    {
        return this.coaInterfaces.addFunction(fct, itf);
    }
    
    /**
     * Ajoute une fonction à un connecteur.
     */
    public boolean addFunction(Function fct, Connector con)
    {
        return this.coaConnectors.addFunction(fct, con);
    }
    
    /**
     * Déplace une fonction d'une interface à une autre.
     */
    public boolean removeFunction(Function fct, Interface itf)
    {
        return this.coaInterfaces.removeFunction(fct, itf);
    }
    
    /**
     * Déplace une fonction d'un connecteur à un autre.
     */
    public boolean removeFunction(Function fct, Connector con)
    {
        return this.coaConnectors.removeFunction(fct, con);
    }
    
    /**
     * Déplace une fonction d'un composant à un autre.
     */
    public boolean moveFunction(Function fct, Component from, Component to)
    {
        return this.coaComponents.moveFunction(fct, from, to);
    }
    
    /**
     * Déplace une fonction d'une interface à une autre.
     */
    public boolean moveFunction(Function fct, Interface from, Interface to)
    {
        return this.coaInterfaces.moveFunction(fct, from, to);
    }
    
    /**
     * Déplace une fonction d'un connecteur à un autre.
     */
    public boolean moveFunction(Function fct, Connector from, Connector to)
    {
        return this.coaConnectors.moveFunction(fct, from, to);
    }

    @Override
    public Set<Component> getComponents()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Connector> getConnectors()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    public Component newComponent()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addConnector(Connector con)
    {
        // TODO Auto-generated method stub
        return false;
    }
}
