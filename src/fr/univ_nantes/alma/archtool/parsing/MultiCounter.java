package fr.univ_nantes.alma.archtool.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MultiCounter<K>
{
    private Map<K, Integer> counters = new HashMap<K, Integer>();
    
    public void increment(K counter)
    {
        this.add(counter, 1);
    }
    
    public void add(K counter, int value)
    {
        if(this.counters.containsKey(counter))
        {
            int newValue = this.counters.get(counter) + value;
            this.counters.put(counter, newValue);
        }
        else
        {
            this.counters.put(counter, value);
        }
    }
    
    public boolean containsCounter(K counter)
    {
        return this.counters.containsKey(counter);
    }
    
    public void addAll(MultiCounter<K> multiCounter)
    {
        for(Entry<K, Integer> counter : multiCounter.counters.entrySet())
        {
            this.add(counter.getKey(), counter.getValue());
        }
    }
    
    public Map<K, Integer> getCounters()
    {
        return new HashMap<K, Integer>(this.counters);
    }
    
    @Override
    public String toString()
    {
        return this.counters.toString();
    }
}
