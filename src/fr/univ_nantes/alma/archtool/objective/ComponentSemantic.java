package fr.univ_nantes.alma.archtool.objective;

import java.util.HashMap;
import java.util.Map;

import fr.univ_nantes.alma.archtool.architectureModel.Architecture;
import fr.univ_nantes.alma.archtool.architectureModel.Component;

public class ComponentSemantic implements Evaluator<Architecture>, ComposableWeighted<Component>
{    
    private Map<Evaluator<Component>, Double> components = 
            new HashMap<Evaluator<Component>, Double>();

    @Override
    public double evaluate(Architecture architecture)
    {
        double result = 0.0;

        for (Component comp : architecture.getComponents())
        {
            double weightSum = 0.0;
            double subresult = 0.0;
            
            for(Evaluator<Component> component : this.components.keySet())
            {
                subresult += this.components.get(component) * 
                        component.evaluate(comp);
                weightSum += this.components.get(component);
                subresult /= weightSum;
            }
            
            result += subresult;
        }

        return result;
    }
    
    @Override
    public boolean addComponent(Evaluator<Component> component, 
            double weight)
    {
        boolean done = false;
        
        if(!this.components.containsKey(component))
        {
            this.components.put(component, weight);
            done = true;
        }
            
        return done;
    }

    @Override
    public boolean removeComponent(Evaluator<Component> component)
    {
        boolean done = false;
        
        if(this.components.containsKey(component))
        {
            this.components.remove(component);
            done = true;
        }
            
        return done;
    }

    @Override
    public boolean setWeightComponent(Evaluator<Component> component,
            double weight)
    {
        boolean done = false;
        
        if(this.components.containsKey(component))
        {
            this.components.put(component, weight);
            done = true;
        }
            
        return done;
    }
}
