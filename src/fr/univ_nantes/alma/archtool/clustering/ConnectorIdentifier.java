package fr.univ_nantes.alma.archtool.clustering;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Connector;
import fr.univ_nantes.alma.archtool.architectureModel.Facade;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Type;

public class ConnectorIdentifier
{
    private Set<Connector> requiredConnectors = new HashSet<Connector>();
    private Set<Connector> providedConnectors = new HashSet<Connector>();
    private Architecture architecture;
    
    void identify(Architecture architecture)
    {  
        this.architecture = architecture;
        
        for (Component component : architecture.getComponents())
        {
            this.createRequiredConnectors(component);
            this.createProvidedConnectors(component);
        }
        
        this.createConnectors();
    }

    void createRequiredConnectors(Component component)
    {
        for(Interface required : component.getRequiredInterfaces())
        {
            Set<Function> itfFunctions = required.getFunctions();
            Set<ComplexType> itfTypes = required.getComplexTypes();
            Set<GlobalVariable> itfVariables = required.getGlobalVariables();
            
            Connector connector = new Connector();
            Facade facade = new Facade();
            connector.addFacade(facade);
            
            if(component.getFunctions().containsAll(itfFunctions) &&
                    component.getComplexTypes().containsAll(itfTypes) &&
                    component.getGlobalVariables().containsAll(
                    itfVariables))
            {
                connector.addFunctions(itfFunctions);
                connector.addComplexTypes(itfTypes);
                connector.addGlobalVariables(itfVariables);
                
                for(Function f : itfFunctions)
                {
                    facade.addFunctions(
                            f.getCoreCalledFunctions().keySet());
                    facade.addComplexTypes(
                            f.getCoreComplexTypes().keySet());
                    facade.addGlobalVariables(
                            f.getCoreGlobalVariables().keySet());
                }
                
                for(GlobalVariable v : itfVariables)
                {
                    Type type = v.getType();
                    
                    if(type.isComplex())
                    {
                        facade.addComplexType((ComplexType) type);
                    }
                }                    
            }
            else
            {
                facade.addFunctions(itfFunctions);
                facade.addComplexTypes(itfTypes);
                facade.addGlobalVariables(itfVariables);
            }
            
            this.requiredConnectors.add(connector);
        }
    }
    
    private void createProvidedConnectors(Component component)
    {
        for(Interface provided : component.getProvidedInterfaces())
        {
            Set<Function> itfFunctions = provided.getFunctions();
            Set<ComplexType> itfTypes = provided.getComplexTypes();
            Set<GlobalVariable> itfVariables = provided.getGlobalVariables();
                       
            // i.in()
            Set<Function> itfInFunctions = new HashSet<Function>();
            Set<GlobalVariable> itfInVariables = new HashSet<GlobalVariable>();
            
            for(Function f : itfFunctions)
            {
                itfInFunctions.addAll(f.getCoreCallingFunctions().keySet());
            }
            
            for(GlobalVariable v : itfVariables)
            {
                itfInFunctions.addAll(v.getCoreUsingFunctions().keySet());
            }
            
            for(ComplexType t : itfTypes)
            {
                itfInFunctions.addAll(t.getCoreUsingFunctions().keySet());
                itfInVariables.addAll(t.getCoreUsingGlobalVariables());
            }
            
            // c.in()
            Set<Function> compInFunctions = component.getFunctionsToIn();
            Set<GlobalVariable> compInVariables = component.getGlobalsToIn();
            
            // intersection
            compInFunctions.retainAll(itfFunctions);
            compInVariables.retainAll(itfInVariables);
            
            if(!compInFunctions.isEmpty() || !compInVariables.isEmpty())
            {   
                Connector connector = new Connector();
                connector.addFunctions(compInFunctions);
                connector.addGlobalVariables(compInVariables);
            
                Facade facade = new Facade();
                connector.addFacade(facade);
                facade.addFunctions(itfFunctions);
                facade.addComplexTypes(itfTypes);
                facade.addGlobalVariables(itfVariables);
                this.providedConnectors.add(connector);
            }
        }
    }
    
    private void createConnectors()
    {
       for(Connector requiredConnector : this.requiredConnectors)
       {
           Set<Function> reqFunctions = new HashSet<Function>();
           Set<ComplexType> reqTypes = new HashSet<ComplexType>();
           Set<GlobalVariable> reqVariables = new HashSet<GlobalVariable>();
           
           // R <- c1.req()
           for(Facade facade : requiredConnector.getFacades())
           {
               reqFunctions.addAll(facade.getFunctions());
               reqTypes.addAll(facade.getComplexTypes());
               reqVariables.addAll(facade.getGlobalVariables());
           }
           
           for(Connector c2 : this.architecture.getConnectors())
           {
               if(!Collections.disjoint(reqFunctions, c2.getFunctions()) ||
                       !Collections.disjoint(reqTypes, c2.getComplexTypes()) ||
                       !Collections.disjoint(reqVariables,
                       c2.getGlobalVariables()))
               {
                   // Merge content of connector
                   c2.addFunctions(requiredConnector.getFunctions());
                   c2.addComplexTypes(requiredConnector.getComplexTypes());
                   c2.addGlobalVariables(
                           requiredConnector.getGlobalVariables());
                   
                   for(Facade facade : requiredConnector.getFacades())
                   {
                       c2.addFacade(facade);
                   }
                   
                   // Remove from facades internal entities of required
                   // connector
                   for(Facade facade : c2.getFacades())
                   {
                       for(Function f : requiredConnector.getFunctions())
                       {
                           facade.removeFunction(f);
                       }
                       
                       for(ComplexType t : requiredConnector.getComplexTypes())
                       {
                           facade.removeComplexType(t);
                       }
                       
                       for(GlobalVariable v : 
                           requiredConnector.getGlobalVariables())
                       {
                           facade.removeGlobalVariable(v);
                       }
                   }
               }
           }
           
           for(Connector c2 : this.providedConnectors)
           {
               if(!Collections.disjoint(reqFunctions, c2.getFunctions()) ||
                       !Collections.disjoint(reqTypes, c2.getComplexTypes()) ||
                       !Collections.disjoint(reqVariables,
                       c2.getGlobalVariables()))
               {
                   // Merge content of connector
                   c2.addFunctions(requiredConnector.getFunctions());
                   c2.addComplexTypes(requiredConnector.getComplexTypes());
                   c2.addGlobalVariables(
                           requiredConnector.getGlobalVariables());
                   
                   for(Facade facade : requiredConnector.getFacades())
                   {
                       c2.addFacade(facade);
                   }
                   
                   // Remove from facades internal entities of required
                   // connector
                   for(Facade facade : c2.getFacades())
                   {
                       for(Function f : requiredConnector.getFunctions())
                       {
                           facade.removeFunction(f);
                       }
                       
                       for(ComplexType t : requiredConnector.getComplexTypes())
                       {
                           facade.removeComplexType(t);
                       }
                       
                       for(GlobalVariable v : 
                           requiredConnector.getGlobalVariables())
                       {
                           facade.removeGlobalVariable(v);
                       }
                   }
                   
                   this.architecture.addConnector(c2);
               }
               
               
               
           }
       } 
    }
}
