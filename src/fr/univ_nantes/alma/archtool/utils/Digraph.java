package fr.univ_nantes.alma.archtool.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Digraph<E>
{
    private Map<E, Set<Pair<E, Integer>>> nodes = 
            new HashMap<E, Set<Pair<E, Integer>>>();
    
    public Digraph()
    {
    }
    
    public Digraph(Digraph<E> digraph)
    {
        for(E node : digraph.nodes.keySet())
        {
            Set<Pair<E, Integer>> edges = digraph.nodes.get(node);
            this.nodes.put(node, new HashSet<Pair<E, Integer>>(edges));
        } 
    }
    
    public boolean addNode(E node)
    {
        boolean done = false;
        
        if(!this.nodes.containsKey(node))
        {
            this.nodes.put(node, new HashSet<Pair<E, Integer>>());
            done = true;
        }
        
        return done;
    }
    
    public void addEdge(E from, E to, int weight)
    {
        this.addNode(from);
        this.addNode(to);
        this.nodes.get(from).add(new Pair<E, Integer>(to, weight));
    }
    
    public void addEdge(E from, E to)
    {
        this.addEdge(from, to, 1);
    }
    
    public boolean contains(E element)
    {
        return this.nodes.containsKey(element);
    }
    
    public void clear()
    {
        this.nodes.clear();
    }
    
    public Set<Triple<E, E, Integer>> getEdges()
    {
        Set<Triple<E, E, Integer>> edges = new HashSet<Triple<E, E, Integer>>();
        
        for(E node : this.nodes.keySet())
        {
            for(Pair<E, Integer> to : this.nodes.get(node))
            {
                edges.add(new Triple<E, E, Integer>(node, to.first, to.second));
            }
        }
        
        return edges;
    }
}
