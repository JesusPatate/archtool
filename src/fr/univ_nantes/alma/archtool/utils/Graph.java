package fr.univ_nantes.alma.archtool.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph<E>
{

    private Map<E, Set<E>> nodes = new HashMap<E, Set<E>>();

    public Map<E, Set<E>> getNodes()
    {
        return new HashMap<E, Set<E>>(this.nodes);
    }

    public boolean addNode(E node)
    {
        boolean done = false;

        if (this.nodes.containsKey(node) == false)
        {
            this.nodes.put(node, new HashSet<E>());
            done = true;
        }

        return done;
    }

    public boolean addNodes(Set<E> nodes)
    {
        boolean done = true;

        for (E node : nodes)
        {
            if (this.addNode(node) == false)
            {
                done = false;
            }
        }

        return done;
    }

    public boolean addEdge(E node1, E node2)
    {
        boolean done = false;

        if (this.nodes.containsKey(node1) && this.nodes.containsKey(node2))
        {
            Set<E> adjacentNodes1 = this.nodes.get(node1);
            Set<E> adjacentNodes2 = this.nodes.get(node2);

            if (adjacentNodes1.contains(node2) == false
                    && adjacentNodes2.contains(node1) == false)
            {
                this.nodes.get(node1).add(node2);
                this.nodes.get(node2).add(node1);

                done = true;
            }
        }

        return done;
    }

    public int size()
    {
        return this.nodes.size();
    }

    public int degree(E node)
    {
        int deg = 0;

        if (this.nodes.containsKey(node))
        {
            deg = this.nodes.get(node).size();
        }

        return deg;
    }

    public boolean isConnected()
    {
        boolean connected = false;

        Set<E> visitedNodes = new HashSet<E>();

        if (this.nodes.isEmpty() == false)
        {
            E node = this.nodes.keySet().iterator().next(); // Argh
            this.visitNodes(node, visitedNodes);

            if (visitedNodes.containsAll(this.nodes.keySet()))
            {
                connected = true;
            }
        }

        return connected;
    }

    private void visitNodes(E startNode, Set<E> visitedNodes)
    {
        visitedNodes.add(startNode);

        Set<E> adjacentNodes = this.nodes.get(startNode);

        for (E node : adjacentNodes)
        {
            if (visitedNodes.contains(node) == false)
            {
                this.visitNodes(node, visitedNodes);
            }
        }
    }
}
