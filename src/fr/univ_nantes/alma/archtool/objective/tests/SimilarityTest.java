package fr.univ_nantes.alma.archtool.objective.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.univ_nantes.alma.archtool.objective.Similarity;

public class SimilarityTest
{
    @Test
    public void equality()
    {
        Similarity sim = new Similarity("test", "test");
        
        assertTrue(sim.getSimilarity() == 0);
    }
    
    @Test
    public void emptyString()
    {
        Similarity sim = new Similarity("", "pouet");
        
        assertTrue(sim.getSimilarity() == 5);
    }
}
