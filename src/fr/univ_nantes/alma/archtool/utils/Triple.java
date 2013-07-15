package fr.univ_nantes.alma.archtool.utils;

public class Triple<A, B, C>
{
    public A first = null;
    public B second = null;
    public C third = null;
    
    public Triple()
    {
    }
    
    public Triple(A first, B second, C third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }
}
