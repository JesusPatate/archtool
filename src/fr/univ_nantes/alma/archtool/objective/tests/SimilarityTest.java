package fr.univ_nantes.alma.archtool.objective.tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.univ_nantes.alma.archtool.objective.Similarity;

public class SimilarityTest
{
    private static Similarity sim;
    
    @BeforeClass
    public static void setUpBeforeClass()
    {
        sim = new Similarity();
    }
    
    @Test
    public void equality()
    {
        String str = "mghqqreh";
        assertTrue(sim.similar(str, str));
    }
    
    @Test
    public void emptyString()
    {
        String str1 = "lmkgipipfy";
        String str2 = "lmkpgipiyf";
        
        assertTrue(sim.similar(str1, str2));
    }
}
