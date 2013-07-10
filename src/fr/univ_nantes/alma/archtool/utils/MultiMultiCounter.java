package fr.univ_nantes.alma.archtool.utils;

import java.util.HashMap;
import java.util.Map;

public class MultiMultiCounter<K, V>
{
    private Map<K, MultiCounter<V>> counters = 
            new HashMap<K, MultiCounter<V>>();
        
    public void increment(K key, V counter, int value)
    {
        MultiCounter<V> multiCounter = null;
        
        if(this.counters.containsKey(key))
        {
            multiCounter = this.counters.get(key);
            multiCounter.increment(counter, value);
        }
        else
        {
            multiCounter = new MultiCounter<V>();
            multiCounter.increment(counter, value);
            this.counters.put(key, multiCounter);
        }
    }
    
    public void increment(K key, V counter)
    {
        this.increment(key, counter, 1);
    }
    
    public int getCounter(K key, V counter)
    {
        return this.counters.get(key).getCounter(counter);
    }

    // TODO
    public void clear()
    {
        this.counters.clear();        
    }
}
