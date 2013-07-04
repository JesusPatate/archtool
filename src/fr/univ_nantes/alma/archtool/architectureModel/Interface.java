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
        StringBuffer buf = new StringBuffer("Interface (");
        
        for(Function fct : this.functions)
        {
            buf.append(fct);
            buf.append(", ");
        }
        
        for(Variable var: this.variables)
        {
            buf.append(var);
            buf.append(", ");
        }
        
        for(Type type: this.types)
        {
            buf.append(type);
            buf.append(", ");
        }
        
        int idx = buf.lastIndexOf(",");
        
        if(idx > 0)
        {
            buf.delete(idx, buf.length());
        }
        
        buf.append(")");
        
        return buf.toString();
    }
}
