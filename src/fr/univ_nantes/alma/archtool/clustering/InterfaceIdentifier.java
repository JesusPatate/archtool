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
import fr.univ_nantes.alma.archtool.sourceModel.SourceCode;

public class InterfaceIdentifier
{
    void identify(Architecture architecture, COA coa)
    {
        for(Component component : architecture.getComponents())
        {
            this.identifyFunctionRequired(component, coa);
        }
    }
    
    private void identifyFunctionRequired(Component component, COA coa)
    {
        SourceCode sourceCode = coa.getSourceCode();
        Queue<Function> functionsToOut = 
                new LinkedList<Function>(coa.getFunctionsToOut(component));
        
        while(!functionsToOut.isEmpty())
        {
            Function f = functionsToOut.poll();
            
            // Entities used by function f
            Set<Function> functionsOut = 
                    sourceCode.getCoreFunctionsCalledBy(f);
            Set<ComplexType> typesOut = sourceCode.getCoreTypesUsedBy(f);
            Set<GlobalVariable> globalsOut = 
                    sourceCode.getCoreGlobalsUsedBy(f);
            
            // Entities of the component
            Set<Function> functionsComponent = 
                    coa.getComponentFunctions(component);
            Set<ComplexType> typesComponent = 
                    coa.getComponentTypes(component);
            Set<GlobalVariable> globalsComponent = 
                    coa.getComponentVariables(component);
            
            // Entities using function f
            Set<Function> functionsIn = 
                    sourceCode.getCoreFunctionsCalling(f);
            
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
                coa.addFunction(f, requiredInterface);
                
                for(Function functionOut : functionsOut)
                {
                    Interface requiredInterface = 
                            coa.getInterfaceFunctions(functionOut);
                    coa.removeFunction(functionOut, requiredInterface);
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
        }
    }
}