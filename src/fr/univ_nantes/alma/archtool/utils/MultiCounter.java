package fr.univ_nantes.alma.archtool.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MultiCounter<K>
{
    private Map<K, Integer> counters = new HashMap<K, Integer>();
    
    public void increment(K counter)
    {
        this.increment(counter, 1);
    }
    
    public void increment(K counter, int value)
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
    
    public void incrementAll(MultiCounter<K> multiCounter)
    {
        for(Entry<K, Integer> counter : multiCounter.counters.entrySet())
        {
            this.increment(counter.getKey(), counter.getValue());
        }
    }
    
    public void incrementAll(Map<K, Integer> counters)
    {
        for(Entry<K, Integer> counter : counters.entrySet())
        {
            this.increment(counter.getKey(), counter.getValue());
        }
    }
    
    public boolean containsCounter(K counter)
    {
        return this.counters.containsKey(counter);
    }
    
    public int getCounter(K counter)
    {
        int value = 0;
        
        if(this.counters.containsKey(counter))
        {
            value = this.counters.get(counter);
        }
        
        return value;
    }
    
    public Set<K> getCounterKey()
    {
        return new HashSet<K>(this.counters.keySet());
    }
       
    public Map<K, Integer> getCounters()
    {
        return new HashMap<K, Integer>(this.counters);
    }
    
    public void clear()
    {
        this.counters.clear();
    }
    
    @Override
    public String toString()
    {
        return this.counters.toString();
    }
}
