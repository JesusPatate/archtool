package fr.univ_nantes.alma.archtool.clustering;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Type;

public class InterfaceIdentifier
{
    void identify(Architecture architecture)
    {
        for(Component component : architecture.getComponents())
        {
            this.identifyFunctionRequired(component, architecture);
            this.identifyGlobalVariableRequired(component, architecture);
            this.identifyFunctionProvided(component);
            this.identifyGlobalVariableProvided(component);
            this.identifyTypeProvided(component);
        }
    }

    private void identifyFunctionRequired(Component component,
            Architecture architecture)
    {
        Queue<Function> functionsToOut = 
                new LinkedList<Function>(component.getFunctionsToOut());
        
        while(!functionsToOut.isEmpty())
        {
            Function function = functionsToOut.poll();
            
            // Entities used by function
            Set<Function> functionsOut = 
                    function.getCoreCalledFunctions().keySet();
            Set<ComplexType> typesOut = function.getCoreComplexTypes().keySet();
            Set<GlobalVariable> globalsOut = 
                    function.getCoreGlobalVariables().keySet();
            
            // Entities of the component
            Set<Function> functionsComponent = component.getFunctions();
            Set<ComplexType> typesComponent = component.getComplexTypes();
            Set<GlobalVariable> globalsComponent =
                    component.getGlobalVariables();
            
            // Entities using function
            Set<Function> functionsIn = 
                    function.getCoreCallingFunctions().keySet();
            
            if((!Collections.disjoint(functionsOut, functionsComponent) && 
                    !Collections.disjoint(typesOut, typesComponent) && 
                    !Collections.disjoint(globalsOut, globalsComponent)) ||
                    functionsIn.isEmpty())
            {
                functionsOut.removeAll(functionsComponent);
                typesOut.removeAll(typesComponent);
                globalsOut.removeAll(globalsComponent);
                
                for(Function functionOut : functionsOut)
                {
//                    Interface requiredInterface = new Interface();
                    
                    Interface requiredInterface =
                            architecture.getInterface(functionOut);
                    
                    component.addRequiredInterface(requiredInterface);
                    requiredInterface.addFunction(functionOut);
                }
                
                for(ComplexType typeOut : typesOut)
                {
                    Interface requiredInterface = new Interface();
                    component.addRequiredInterface(requiredInterface);
                    requiredInterface.addType(typeOut);
                }
                
                for(GlobalVariable globalOut : globalsOut)
                {
                    Interface requiredInterface = new Interface();
                    component.addRequiredInterface(requiredInterface);
                    requiredInterface.addVariable(globalOut);
                }
            }
            else
            {
                functionsToOut.addAll(functionsIn);
                Interface requiredInterface = new Interface();
                component.addRequiredInterface(requiredInterface);
                requiredInterface.addFunction(function);
                
                for(Function functionOut : functionsOut)
                {
                    Interface itf = architecture.getInterface(functionOut);
                    component.removeRequiredInterface(itf);
                }
                
                for(ComplexType typeOut : typesOut)
                {
                    Interface itf = architecture.getInterface(typeOut);
                    component.removeRequiredInterface(itf);
                }
                
                for(GlobalVariable globalOut : globalsOut)
                {
                    Interface itf = architecture.getInterface(globalOut);
                    component.removeRequiredInterface(itf);
                }
            }
        }
    }
    
    private void identifyGlobalVariableRequired(Component component,
            Architecture architecture)
    {
        Queue<GlobalVariable> globalsToOut = 
                new LinkedList<GlobalVariable>(component.getGlobalVariables());
        
        while(!globalsToOut.isEmpty())
        {
            GlobalVariable variable = globalsToOut.poll();
            
            // Entities used by global variable
            Type typeOut = variable.getType();
                        
            // Entities of the component
            Set<ComplexType> typesComponent = component.getComplexTypes();
            
            // Entities using global variable
            Set<Function> functionsIn = 
                    variable.getCoreUsingFunctions().keySet();
            
            if((typeOut.isComplex() &&
                    typesComponent.contains((ComplexType) typeOut)) ||
                    functionsIn.isEmpty())
            {
                if(typeOut.isComplex() && 
                        !typesComponent.contains((ComplexType) typeOut))
                {
                    Interface requiredInterface = new Interface();
                    component.addRequiredInterface(requiredInterface);
                    requiredInterface.addType((ComplexType) typeOut);
                }
            }
            else
            {
                Interface requiredInterface = new Interface();
                component.addRequiredInterface(requiredInterface);
                requiredInterface.addVariable(variable);
                
                if(typeOut.isComplex())
                {
                    Interface itf = architecture.getInterface((ComplexType) typeOut);
                    component.removeRequiredInterface(itf);
                }
            }
        }
    }
    
    private void identifyFunctionProvided(Component component)
    {
        Queue<Function> functionsToIn = 
                new LinkedList<Function>(component.getFunctionsToIn());
        
        while(!functionsToIn.isEmpty())
        {
            Function function = functionsToIn.poll();
            
            // Entities using function
            Set<Function> functionsIn = 
                    function.getCoreCallingFunctions().keySet();
            
            // Entities used by function
            Set<Function> functionsOut = 
                    function.getCoreCalledFunctions().keySet();
            Set<ComplexType> typesOut = function.getCoreComplexTypes().keySet();
            Set<GlobalVariable> globalsOut = 
                    function.getCoreGlobalVariables().keySet();
    
            // Entities of the component
            Set<Function> functionsComponent = component.getFunctions();
                
            if(!Collections.disjoint(functionsIn, functionsComponent) ||
                    (functionsOut.isEmpty() && typesOut.isEmpty() &&
                    globalsOut.isEmpty()))
            {
                Interface providedInterface = new Interface();
                component.addProvidedInterface(providedInterface);
                providedInterface.addFunction(function);
            }
            else
            {
                functionsToIn.addAll(functionsOut);
            }
        }
    }
    
    private void identifyGlobalVariableProvided(Component component)
    {
        Queue<GlobalVariable> globalsToIn = 
                new LinkedList<GlobalVariable>(component.getGlobalsToIn());
        
        while(!globalsToIn.isEmpty())
        {
            GlobalVariable variable = globalsToIn.poll();
            
            // Entities using global variable
            Set<Function> functionsIn = 
                    variable.getCoreUsingFunctions().keySet();
            
            // Entities used by global variable
            Type typeOut = variable.getType();
                        
            // Entities of the component
            Set<Function> functionsComponent = component.getFunctions();
                            
            if(!Collections.disjoint(functionsIn, functionsComponent) ||
                    !typeOut.isComplex())
            {
                Interface providedInterface = new Interface();
                component.addProvidedInterface(providedInterface);
                providedInterface.addVariable(variable);
            }
        }
    }
    
    private void identifyTypeProvided(Component component)
    {
        Queue<ComplexType> typesToIn = 
                new LinkedList<ComplexType>(component.getTypesToIn());
        
        while(!typesToIn.isEmpty())
        {
            ComplexType type = typesToIn.poll();                            
            Interface providedInterface = new Interface();
            component.addProvidedInterface(providedInterface);
            providedInterface.addType(type);
        }
    }
}