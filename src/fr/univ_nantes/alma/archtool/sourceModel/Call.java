package fr.univ_nantes.alma.archtool.sourceModel;

import java.util.HashSet;
import java.util.Set;

public class Call
{
    private final Function function;

    private final Set<Variable> parameters = new HashSet<Variable>();

    public Call(final Function function)
    {
        this.function = function;
    }

    public Call(final Function function, final Set<Variable> parameters)
    {
        this.function = function;
        this.parameters.addAll(parameters);
    }

    public Function getFunction()
    {
        return this.function;
    }

    public Set<Variable> getParameters()
    {
        return new HashSet<Variable>(this.parameters);
    }
    
    @Override
    public String toString() 
    {
        StringBuffer call = new StringBuffer(this.function.getName() + "(");
        call.append(this.parameters);
        call.append(")");
        return call.toString();
    }
}