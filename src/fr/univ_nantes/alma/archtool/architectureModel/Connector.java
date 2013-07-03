package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.HashSet;
import java.util.Set;

import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.Type;
import fr.univ_nantes.alma.archtool.sourceModel.Variable;


public class Connector implements Cohesionable
{
    private Set<Function> functions = new HashSet<Function>();
    
    private Set<Variable> variables = new HashSet<Variable>();
    
    private Set<Type> types = new HashSet<Type>();
    
    @Override
    public Set<Function> getFunctions()
    {
        return new HashSet<Function>(this.functions);
    }

    @Override
    public Set<Variable> getVariables()
    {
        return new HashSet<Variable>(this.variables);
    }

    @Override
    public Set<Type> getTypes()
    {
        return new HashSet<Type>(this.types);
    }
    
    @Override
    public String toString()
    {
        String str = "Connecteur (";
        
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
        
        str = str.substring(0, str.lastIndexOf(","));
        str += ")";
        
        return str;
    }
}
