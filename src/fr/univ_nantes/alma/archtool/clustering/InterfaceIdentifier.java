package fr.univ_nantes.alma.archtool.clustering;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.coa.COA;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Type;

public class InterfaceIdentifier
{
    void identify(Architecture architecture, COA coa)
    {
        for(Component component : architecture.getComponents())
        {
            this.identifyFunctionRequired(component, coa);
            this.identifyGlobalVariableRequired(component, coa);
            this.identifyFunctionProvided(component, coa);
            this.identifyGlobalVariableProvided(component, coa);
            this.identifyTypeProvided(component, coa);
        }
    }

    private void identifyFunctionRequired(Component component, COA coa)
    {
        Queue<Function> functionsToOut = 
                new LinkedList<Function>(coa.getFunctionsToOut(component));
        
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
            Set<Function> functionsComponent = 
                    coa.getComponentFunctions(component);
            Set<ComplexType> typesComponent = 
                    coa.getComponentTypes(component);
            Set<GlobalVariable> globalsComponent = 
                    coa.getComponentVariables(component);
            
            // Entities using function
            Set<Function> functionsIn = function.getCoreCallingFunctions().keySet();
            
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
                    Interface requiredInterface = new Interface();
                    component.addRequiredInterface(requiredInterface);
                    coa.addFunction(functionOut, requiredInterface);
                }
                
                for(ComplexType typeOut : typesOut)
                {
                    Interface requiredInterface = new Interface();
                    component.addRequiredInterface(requiredInterface);
                    coa.addType(typeOut, requiredInterface);
                }
                
                for(GlobalVariable globalOut : globalsOut)
                {
                    Interface requiredInterface = new Interface();
                    component.addRequiredInterface(requiredInterface);
                    coa.addVariable(globalOut, requiredInterface);
                }
            }
            else
            {
                functionsToOut.addAll(functionsIn);
                Interface requiredInterface = new Interface();
                component.addRequiredInterface(requiredInterface);
                coa.addFunction(function, requiredInterface);
                
                for(Function functionOut : functionsOut)
                {
                    Interface inteface = coa.getInterface(functionOut);
                    coa.removeFunction(functionOut, inteface);
                }
                
                for(ComplexType typeOut : typesOut)
                {
                    Interface inteface = coa.getInterface(typeOut);
                    coa.removeType(typeOut, inteface);
                }
                
                for(GlobalVariable globalOut : globalsOut)
                {
                    Interface inteface = coa.getInterface(globalOut);
                    coa.removeVariable(globalOut, inteface);
                }
            }
        }
    }
    
    private void identifyGlobalVariableRequired(Component component, COA coa)
    {
        Queue<GlobalVariable> globalsToOut = 
                new LinkedList<GlobalVariable>(coa.getGlobalsToOut(component));
        
        while(!globalsToOut.isEmpty())
        {
            GlobalVariable variable = globalsToOut.poll();
            
            // Entities used by global variable
            Type typeOut = variable.getType();
                        
            // Entities of the component
            Set<ComplexType> typesComponent = 
                    coa.getComponentTypes(component);
            
            // Entities using global variable
            Set<Function> functionsIn = variable.getCoreUsingFunctions().keySet();
            
            if((typeOut.isComplex() &&
                    typesComponent.contains((ComplexType) typeOut)) ||
                    functionsIn.isEmpty())
            {
                if(typeOut.isComplex() && 
                        !typesComponent.contains((ComplexType) typeOut))
                {
                    Interface requiredInterface = new Interface();
                    component.addRequiredInterface(requiredInterface);
                    coa.addType((ComplexType) typeOut, requiredInterface);
                }
            }
            else
            {
                Interface requiredInterface = new Interface();
                component.addRequiredInterface(requiredInterface);
                coa.addVariable(variable, requiredInterface);
                
                if(typeOut.isComplex())
                {
                    Interface itf = coa.getInterface((ComplexType) typeOut));
                    coa.removeType((ComplexType) typeOut, itf);
                }
            }
        }
    }
    
    private void identifyFunctionProvided(Component component, COA coa)
    {
        Queue<Function> functionsToIn = 
                new LinkedList<Function>(coa.getFunctionsToIn(component));
        
        while(!functionsToIn.isEmpty())
        {
            Function function = functionsToIn.poll();
            
            // Entities using function
            Set<Function> functionsIn = function.getCoreCallingFunctions().keySet();
            
            // Entities used by function
            Set<Function> functionsOut = 
                    function.getCoreCalledFunctions().keySet();
            Set<ComplexType> typesOut = function.getCoreComplexTypes().keySet();
            Set<GlobalVariable> globalsOut = 
                    function.getCoreGlobalVariables().keySet();
    
            // Entities of the component
            Set<Function> functionsComponent = 
                    coa.getComponentFunctions(component);
                
            if(!Collections.disjoint(functionsIn, functionsComponent) ||
                    (functionsOut.isEmpty() && typesOut.isEmpty() &&
                    globalsOut.isEmpty()))
            {
                Interface providedInterface = new Interface();
                component.addProvidedInterface(providedInterface);
                coa.addFunction(function, providedInterface);
            }
            else
            {
                functionsToIn.addAll(functionsOut);
            }
        }
    }
    
    private void identifyGlobalVariableProvided(Component component, COA coa)
    {
        Queue<GlobalVariable> globalsToIn = 
                new LinkedList<GlobalVariable>(coa.getGlobalsToIn(component));
        
        while(!globalsToIn.isEmpty())
        {
            GlobalVariable variable = globalsToIn.poll();
            
            // Entities using global variable
            Set<Function> functionsIn = 
                    variable.getCoreUsingFunctions().keySet();
            
            // Entities used by global variable
            Type typeOut = variable.getType();
                        
            // Entities of the component
            Set<Function> functionsComponent = 
                    coa.getComponentFunctions(component);
                            
            if(!Collections.disjoint(functionsIn, functionsComponent) ||
                    !typeOut.isComplex())
            {
                Interface providedInterface = new Interface();
                component.addProvidedInterface(providedInterface);
                coa.addVariable(variable, providedInterface);
            }
        }
    }
    
    private void identifyTypeProvided(Component component, COA coa)
    {
        Queue<ComplexType> typesToIn = 
                new LinkedList<ComplexType>(coa.getTypesToIn(component));
        
        while(!typesToIn.isEmpty())
        {
            ComplexType type = typesToIn.poll();                            
            Interface providedInterface = new Interface();
            component.addProvidedInterface(providedInterface);
            coa.addType(type, providedInterface);
        }
    }
}