package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.HashSet;
import java.util.Set;

import fr.univ_nantes.alma.archtool.coa.COA;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;

public class Component
{
    private final Set<Interface> requiredInterfaces = new HashSet<Interface>();

    private final Set<Interface> providedInterfaces = new HashSet<Interface>();
    
    private COA coa = null;

    public void setCOA(COA coa)
    {
        this.coa = coa;
    }
    
    public Set<Interface> getRequiredInterfaces()
    {
        return new HashSet<Interface>(this.requiredInterfaces);
    }

    public boolean requiresInterface(final Interface i)
    {
        return this.requiredInterfaces.contains(i);
    }

    public void addRequiredInterface(final Interface i)
    { 
        i.setCOA(this.coa);
        this.coa.addInterface(i);
        this.requiredInterfaces.add(i);
    }

    public void removeRequiredInterface(final Interface i)
    {
        this.requiredInterfaces.remove(i);
        this.coa.checkInterface(i);
    }

    public Set<Interface> getProvidedInterfaces()
    {
        return new HashSet<Interface>(this.providedInterfaces);
    }

    public boolean providesInterface(final Interface i)
    {
        return this.providedInterfaces.contains(i);
    }

    public void addProvidedInterface(final Interface i)
    {
        i.setCOA(this.coa);
        this.coa.addInterface(i);
        this.providedInterfaces.add(i);
    }

    public void removeProvidedInterface(final Interface i)
    {
        this.providedInterfaces.remove(i);
        this.coa.checkInterface(i);
    }
    
    public Set<Function> getFunctions()
    {
        return this.coa.getComponentFunctions(this);
    }
    
    public Set<GlobalVariable> getGlobalVariables()
    {
        return this.coa.getComponentVariables(this);
    }
    
    public Set<ComplexType> getComplexTypes()
    {
        return this.coa.getComponentTypes(this);
    }
    
    public boolean addFunction(Function fct)
    {
        return this.coa.addFunction(fct, this);
    }
    
    public void addFunctions(Set<Function> fcts)
    {
        this.coa.addFunctions(fcts, this);
    }
    
    public boolean addVariable(GlobalVariable var)
    {
        return this.coa.addVariable(var, this);
    }
    
    public void addVariables(Set<GlobalVariable> vars)
    {
        this.coa.addVariables(vars, this);
    }
    
    public boolean addType(ComplexType t)
    {
        return this.coa.addType(t, this);
    }
    
    public void addTypes(Set<ComplexType> types)
    {
       this.coa.addTypes(types, this);
    }
    
    public boolean removeFunction(Function fct)
    {
        return this.coa.removeFunction(fct, this);
    }
    
    public boolean removeVariable(GlobalVariable var)
    {
        return this.coa.removeVariable(var, this);
    }
    
    public boolean removeType(ComplexType t)
    {
        return this.coa.removeType(t, this);
    }
    
    public Set<Function> getFunctionsToOut()
    {
        return this.coa.getFunctionsToOut(this);
    }
    
    public Set<GlobalVariable> getGlobalsToOut()
    {
        return this.coa.getGlobalsToOut(this);
    }
    
    public Set<Function> getFunctionsToIn()
    {
        return this.coa.getFunctionsToIn(this);
    }
    
    public Set<GlobalVariable> getGlobalsToIn()
    {
        return this.coa.getGlobalsToIn(this);
    }
    
    public Set<ComplexType> getTypesToIn()
    {
        return this.coa.getTypesToIn(this);
    }
    
    public void clearInterfaces()
    {
        for(Interface itf : this.getProvidedInterfaces())
        {
            removeProvidedInterface(itf);
        }
        
        for(Interface itf : this.getRequiredInterfaces())
        {
            removeRequiredInterface(itf);
        }
    }
    
    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer("Composant [");
        
        for(Function fct : this.getFunctions())
        {
            buf.append(fct);
            buf.append(", ");
        }
        
        for(GlobalVariable var : this.getGlobalVariables())
        {
            buf.append(var);
            buf.append(", ");
        }
        
        for(ComplexType t : this.getComplexTypes())
        {
            buf.append(t);
            buf.append(", ");
        }
        
        for(Interface itf : this.getProvidedInterfaces())
        {
            buf.append(itf);
            buf.append(", ");
        }
        
        for(Interface itf : this.getRequiredInterfaces())
        {
            buf.append(itf);
            buf.append(", ");
        }
        
        int idx = buf.lastIndexOf(",");
        
        if(idx > 0)
        {
            buf.delete(idx, buf.length());
        }
        
        buf.append("]");
        
        return buf.toString();
    }
}
