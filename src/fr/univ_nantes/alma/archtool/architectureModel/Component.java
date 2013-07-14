package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import fr.univ_nantes.alma.archtool.coa.COA;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;

public class Component
{
    private final Set<Interface> requiredInterfaces = new HashSet<Interface>();

    private final Set<Interface> providedInterfaces = new HashSet<Interface>();
    
    private Set<Function> cachedFunctions = null;

    private Set<GlobalVariable> cachedGlobalVariables = null;
    
    private Set<ComplexType> cachedComplexType = null;
    
    private COA coa = null;

    /**
     * On crèe ou on coupe la connection avec le coa.
     */
    public void setCOA(COA coa)
    {
        this.coa = coa;
        
        if(this.coa != null)
        {
            this.coa.addComponent(this);
            
            for(Interface required : this.requiredInterfaces)
            {
                required.setCOA(this.coa);
            }
            
            for(Interface provided : this.providedInterfaces)
            {
                provided.setCOA(this.coa);
            }
            
            if(this.cachedFunctions != null)
            {
                this.coa.addFunctions(this.cachedFunctions, this);
                this.cachedFunctions = null;
            }
            
            if(this.cachedComplexType != null)
            {
                this.coa.addComplexTypes(this.cachedComplexType, this);
                this.cachedComplexType = null;
            }
            
            if(this.cachedGlobalVariables != null)
            {
                this.coa.addGlobalVariables(this.cachedGlobalVariables, this);
                this.cachedGlobalVariables = null;
            }
        }
    }
    
    public Set<Interface> getRequiredInterfaces()
    {
        return new HashSet<Interface>(this.requiredInterfaces);
    }

    public boolean requiresInterface(final Interface i)
    {
        return this.requiredInterfaces.contains(i);
    }

    /**
     * On informe le coa uniquement si on le connait.
     */
    public void addRequiredInterface(final Interface i)
    { 
        if(this.coa != null)
        {
            i.setCOA(this.coa);
        }
        
        this.requiredInterfaces.add(i);
    }
    
    /**
     * On informe le coa uniquement si on le connait.
     */
    public void removeRequiredInterface(final Interface i)
    {
        this.requiredInterfaces.remove(i);
        
        if(this.coa != null)
        {
            this.coa.checkInterface(i);
        }
    }

    public Set<Interface> getProvidedInterfaces()
    {
        return new HashSet<Interface>(this.providedInterfaces);
    }

    public boolean providesInterface(final Interface i)
    {
        return this.providedInterfaces.contains(i);
    }
    
    /**
     * On informe le coa uniquement si on le connait.
     */
    public void addProvidedInterface(final Interface i)
    {
        if(this.coa != null)
        {
            i.setCOA(this.coa);
            this.coa.addFunctions(i.getFunctions(), this);
            this.coa.addComplexTypes(i.getComplexTypes(), this);
            this.coa.addGlobalVariables(i.getGlobalVariables(), this);
        }
        else
        {
            this.addFunctions(i.getFunctions());
            this.addComplexTypes(i.getComplexTypes());
            this.addGlobalVariables(i.getGlobalVariables());
        }
        
        this.providedInterfaces.add(i);
    }
    
    /**
     * On informe le coa uniquement si on le connait.
     */
    public void removeProvidedInterface(final Interface i)
    {
        this.providedInterfaces.remove(i);
        
        if(this.coa != null)
        {
            this.coa.checkInterface(i);
        }
    }
    
    /**
     * On prend les infos du coa si on le connait sinon on fait avec les
     *  informations mises en cache.
     */
    public Set<Function> getFunctions()
    {
        Set<Function> functions = null;
        
        if(this.coa == null)
        {
            functions = this.cachedFunctions == null ?
                    new HashSet<Function>() : 
                    new HashSet<Function>(this.cachedFunctions);
        }
        else
        {
            functions = this.coa.getComponentFunctions(this);
        }
                
        return functions;
    }
    
    /**
     * On prend les infos du coa si on le connait sinon on fait avec les
     *  informations mises en cache.
     */
    public Set<GlobalVariable> getGlobalVariables()
    {
        Set<GlobalVariable> globalVariables = null;
        
        if(this.coa == null)
        {
            globalVariables = this.cachedGlobalVariables == null ?
                    new HashSet<GlobalVariable>() : 
                    new HashSet<GlobalVariable>(this.cachedGlobalVariables);
        }
        else
        {
            globalVariables = this.coa.getComponentGlobalVariables(this);
        }
                
        return globalVariables;
    }
    
    /**
     * On prend les infos du coa si on le connait sinon on fait avec les
     *  informations mises en cache.
     */
    public Set<ComplexType> getComplexTypes()
    {
        Set<ComplexType> complexTypes = null;
        
        if(this.coa == null)
        {
            complexTypes = this.cachedComplexType == null ?
                    new HashSet<ComplexType>() : 
                    new HashSet<ComplexType>(this.cachedComplexType);
        }
        else
        {
            complexTypes = this.coa.getComponentComplexTypes(this);
        }
                
        return complexTypes;
    }

    /**
     * On informe le coa uniquement si on le connait sinon on met en cache.
     */
    public boolean addFunction(Function fct)
    {
        boolean done;
        
        if(this.coa == null)
        {
            if(this.cachedFunctions == null)
            {
                this.cachedFunctions = new HashSet<Function>();
            }
            
            done = this.cachedFunctions.add(fct);
        }
        else
        {
            done = this.coa.addFunction(fct, this);
        }
        
        return done;
    }
    
    /**
     * On informe le coa uniquement si on le connait sinon on met en cache.
     */
    public void addFunctions(Set<Function> fcts)
    {
        if(this.coa == null)
        {
            if(this.cachedFunctions == null)
            {
                this.cachedFunctions = new HashSet<Function>();
            }
            
            this.cachedFunctions.addAll(fcts);
        }
        else
        {
            this.coa.addFunctions(fcts, this);
        }
    }
    
    /**
     * On informe le coa uniquement si on le connait sinon on met en cache.
     */
    public boolean addGlobalVariable(GlobalVariable var)
    {
        boolean done;
        
        if(this.coa == null)
        {
            if(this.cachedGlobalVariables == null)
            {
                this.cachedGlobalVariables = new HashSet<GlobalVariable>();
            }
            
            done = this.cachedGlobalVariables.add(var);
        }
        else
        {
            done = this.coa.addGlobalVariable(var, this);
        }
        
        return done;
    }
    
    /**
     * On informe le coa uniquement si on le connait sinon on met en cache.
     */
    public void addGlobalVariables(Set<GlobalVariable> vars)
    {        
        if(this.coa == null)
        {
            if(this.cachedGlobalVariables == null)
            {
                this.cachedGlobalVariables = new HashSet<GlobalVariable>();
            }
            
            this.cachedGlobalVariables.addAll(vars);
        }
        else
        {
            this.coa.addGlobalVariables(vars, this);
        }
    }
    
    /**
     * On informe le coa uniquement si on le connait sinon on met en cache.
     */
    public boolean addComplexType(ComplexType t)
    {
        boolean done;
        
        if(this.coa == null)
        {
            if(this.cachedComplexType == null)
            {
                this.cachedComplexType = new HashSet<ComplexType>();
            }
            
            done = this.cachedComplexType.add(t);
        }
        else
        {
            done = this.coa.addComplexType(t, this);
        }
        
        return done;
    }
    
    /**
     * On informe le coa uniquement si on le connait sinon on met en cache.
     */
    public void addComplexTypes(Set<ComplexType> types)
    {
        if(this.coa == null)
        {
            if(this.cachedComplexType == null)
            {
                this.cachedComplexType = new HashSet<ComplexType>();
            }
            
            this.cachedComplexType.addAll(types);
        }
        else
        {
            this.coa.addComplexTypes(types, this);
        }
    }
    
    /**
     * On informe le coa uniquement si on le connait sinon on met en cache.
     */
    public boolean removeFunction(Function fct)
    {
        boolean done = false;
        
        if(this.coa == null && this.cachedFunctions != null)
        {            
            done = this.cachedFunctions.remove(fct);
        }
        else if(this.coa != null)
        {
            done = this.coa.removeFunction(fct, this);
        }
        
        return done;
    }
    
    /**
     * On informe le coa uniquement si on le connait sinon on met en cache.
     */
    public boolean removeGlobalVariable(GlobalVariable var)
    {
        boolean done = false;
        
        if(this.coa == null && this.cachedGlobalVariables != null)
        {            
            done = this.cachedGlobalVariables.remove(var);
        }
        else if(this.coa != null)
        {
            done = this.coa.removeGlobalVariable(var, this);
        }
        
        return done;
    }
    
    /**
     * On informe le coa uniquement si on le connait sinon on met en cache.
     */
    public boolean removeComplexType(ComplexType t)
    {
        boolean done = false;
        
        if(this.coa == null && this.cachedComplexType != null)
        {            
            done = this.cachedComplexType.remove(t);
        }
        else if(this.coa != null)
        {
            done = this.coa.removeComplexType(t, this);
        }
        
        return done;
    }
    
    /**
     * On prend les infos du coa si on le connait.
     */
    public Set<Function> getFunctionsToOut()
    {
        Set<Function> functions = null;
        
        if(this.coa == null)
        {
            functions = new HashSet<Function>();
        }
        else
        {
            functions = this.coa.getFunctionsToOut(this);
        }
                
        return functions;
    }
    
    /**
     * On prend les infos du coa si on le connait.
     */
    public Set<GlobalVariable> getGlobalsToOut()
    {
        Set<GlobalVariable> globalVariables = null;
        
        if(this.coa == null)
        {
            globalVariables = new HashSet<GlobalVariable>();
        }
        else
        {
            globalVariables = this.coa.getGlobalsToOut(this);
        }
                
        return globalVariables;
    }
    
    /**
     * On prend les infos du coa si on le connait.
     */
    public Set<Function> getFunctionsToIn()
    {
        Set<Function> functions = null;
        
        if(this.coa == null)
        {
            functions = new HashSet<Function>();
        }
        else
        {
            functions = this.coa.getFunctionsToIn(this);
        }
                
        return functions;
    }
    
    /**
     * On prend les infos du coa si on le connait.
     */
    public Set<GlobalVariable> getGlobalsToIn()
    {
        Set<GlobalVariable> globalVariables = null;
        
        if(this.coa == null)
        {
            globalVariables = new HashSet<GlobalVariable>();
        }
        else
        {
            globalVariables = this.coa.getGlobalsToIn(this);
        }
                
        return globalVariables;
    }
    
    /**
     * On prend les infos du coa si on le connait.
     */
    public Set<ComplexType> getTypesToIn()
    {
        Set<ComplexType> complexTypes = null;
        
        if(this.coa == null)
        {
            complexTypes = new HashSet<ComplexType>();
        }
        else
        {
            complexTypes = this.coa.getTypesToIn(this);
        }
                
        return complexTypes;
    }
    
    public void clearInterfaces()
    {
    	Iterator<Interface> itPro = this.providedInterfaces.iterator();
        Iterator<Interface> itReq = this.requiredInterfaces.iterator();
    	
        while(itPro.hasNext())
        {
            Interface itf = itPro.next();
            itPro.remove();
            
            if(this.coa != null)
            {
                this.coa.checkInterface(itf);
            }
        }
        
        while(itReq.hasNext())
        {
            Interface itf = itReq.next();
            itReq.remove();
            
            if(this.coa != null)
            {
                this.coa.checkInterface(itf);
            }
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
