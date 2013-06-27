package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.HashSet;
import java.util.Set;

import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.Type;
import fr.univ_nantes.alma.archtool.sourceModel.Variable;

public class Component
{
    private final Set<Interface> requiredInterfaces = new HashSet<Interface>();

    private final Set<Interface> providedInterfaces = new HashSet<Interface>();

    private final Set<Function> functions = new HashSet<Function>();

    private final Set<Variable> variables = new HashSet<Variable>();

    private final Set<Type> types = new HashSet<Type>();

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

    public Set<Interface> getRequiredInterfaces()
    {
        return new HashSet<Interface>(this.requiredInterfaces);
    }

    public boolean requiresInterface(final Interface i)
    {
        return this.requiredInterfaces.contains(i);
    }
    
    public boolean addRequiredInterface(final Interface i)
    {
        return this.requiredInterfaces.add(i);
    }

    public boolean removeRequiredInterface(final Interface i)
    {
        return this.requiredInterfaces.remove(i);
    }

    public Set<Interface> getProvidedInterfaces()
    {
        return new HashSet<Interface>(this.providedInterfaces);
    }

    public boolean providesInterface(final Interface i)
    {
        return this.providedInterfaces.contains(i);
    }

    public boolean addProvidedInterface(final Interface i)
    {
        return this.providedInterfaces.add(i);
    }

    public boolean removeProvidedInterface(final Interface i)
    {
        return this.providedInterfaces.remove(i);
    }
}
