package fr.univ_nantes.alma.archtool.parsing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import fr.univ_nantes.alma.archtool.utils.MultiCounter;

public class CallCounter
{
    private MultiCounter<String> use = new MultiCounter<String>();
    private Map<String, Set<Set<String>>> parameters = 
            new HashMap<String, Set<Set<String>>>();
    
    public void increment(String function, Set<String> parameters)
    {        
        if(!this.use.containsCounter(function))
        {
            Set<Set<String>> functionCalls = new HashSet<Set<String>>();
            functionCalls.add(parameters);
            this.parameters.put(function, functionCalls);
        }
        else
        {
            this.parameters.get(function).add(parameters);
        }
        
        this.use.increment(function);
    }
    
    public void incrementAll(CallCounter callCounter)
    {        
        for(Entry<String, Set<Set<String>>> function : 
            callCounter.parameters.entrySet())
        {
            if(!this.parameters.containsKey(function.getKey()))
            {
                Set<Set<String>> functionCalls = 
                        new HashSet<Set<String>>(function.getValue());
                this.parameters.put(function.getKey(), functionCalls);
            }
            else
            {
                this.parameters.get(function.getKey()).
                        addAll(function.getValue());
            }
        }
        
        this.use.incrementAll(callCounter.use);
    }
    
    public Map<String, Set<Set<String>>> getCalls()
    {
        return new HashMap<String, Set<Set<String>>>(this.parameters);
    }
    
    @Override
    public String toString()
    {
        StringBuffer repr = new StringBuffer(this.use.toString());
        repr.append(", ");
        repr.append(this.parameters.toString());
        return repr.toString();
    }
}
