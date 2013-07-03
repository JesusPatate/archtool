package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.HashSet;
import java.util.Set;

import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.Type;
import fr.univ_nantes.alma.archtool.sourceModel.Variable;


public class Interface implements Cohesionable
{
    private final Set<Function> functions = new HashSet<Function>();

    private final Set<Variable> variables = new HashSet<Variable>();

    private final Set<Type> types = new HashSet<Type>();

    @Override
    public Set<Function> getFunctions()
    {
        return new HashSet<Function>(this.functions);
    }
    
    public boolean constainsFunction(Function fct)
    {
        return this.functions.contains(fct);
    }
    
    public boolean addFunction(final Function fct)
    {
        return this.functions.add(fct);
    }

    public boolean addFunctions(final Set<Function> fcts)
    {
        return this.functions.addAll(fcts);
    }

    @Override
    public Set<Variable> getVariables()
    {
        return new HashSet<Variable>(this.variables);
    }
    
    public boolean constainsVariable(Function fct)
    {
        return this.functions.contains(fct);
    }

    public boolean addVariable(final Variable var)
    {
        return this.variables.add(var);
    }

    public boolean addVariables(final Set<Variable> vars)
    {
        return this.variables.addAll(vars);
    }

    @Override
    public Set<Type> getTypes()
    {
        return new HashSet<Type>(this.types);
    }

    public boolean addType(final Type type)
    {
        return this.types.add(type);
    }

    public boolean addTypes(final Set<Type> types)
    {
        return this.types.addAll(types);
    }
    
    public boolean equals(Object o)
    {
        boolean res = false;
        
        if(this.getClass() == o.getClass())
        {
            Interface i = (Interface) o;
            
            if(this.functions.equals(i.functions)
                    && this.variables.equals(i.variables)
                    && this.types.equals(i.types))
            {
                res = true;
            }
        }
        
        return res;
    }
    
    @Override
    public String toString()
    {
        String str = "Interface (";
        
        for(Function fct : this.functions)
        {
            str += fct + ", ";
        }
        
        for(Variable var: this.variables)
        {
            str += var + ", ";
        }
        
        for(Type t: this.types)
        {
            str += t + ", ";
        }
        
        int idx = str.lastIndexOf(",");
        
        if(idx > 0)
        {
            str = str.substring(0, idx);
        }
        
        str += ")";
        
        return str;
    }
}
