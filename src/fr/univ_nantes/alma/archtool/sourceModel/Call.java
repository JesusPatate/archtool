package fr.univ_nantes.alma.archtool.sourceModel;

import java.util.HashSet;
import java.util.Set;

public class Call
{
    private Function function;

    private Set<Variable> parameters;

    public Call(Function function, Set<Variable> parameters)
    {
        this.function = function;
        this.parameters = parameters;
    }

    public Function getFunction()
    {
        return this.function;
    }

    public Set<Variable> getParameters()
    {
        return new HashSet<Variable>(this.parameters);
    }
}
