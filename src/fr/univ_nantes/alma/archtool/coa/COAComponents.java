package fr.univ_nantes.alma.archtool.coa;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.Type;
import fr.univ_nantes.alma.archtool.sourceModel.Variable;

class COAComponents
{
    private Map<Function, Component> fctToComp = new HashMap<Function, Component>();
    
    private Map<Variable, Component> varToComp = new HashMap<Variable, Component>();
    
    private Map<Type, Component> typeToComp = new HashMap<Type, Component>();
    
    private Map<Component, Set<Function>> compToFcts = new HashMap<Component, Set<Function>>();
    
    private Map<Component, Set<Variable>> compToVars = new HashMap<Component, Set<Variable>>();
    
    private Map<Component, Set<Type>> compToTypes = new HashMap<Component, Set<Type>>();
    
    /**
     * Ajoute une fonction à un composant.
     */
    public boolean addFunction(Function fct, Component comp)
    {
        boolean done = false;
        
        if (this.fctToComp.containsKey(fct) == false)
        {
            Set<Function> compFcts = this.compToFcts.get(comp);
            this.compToFcts.put(comp, compFcts);
            this.fctToComp.put(fct, comp);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Retire une fonction d'une fonction.
     */
    public boolean removeFunction(Function fct, Component comp)
    {
        boolean done = false;

        Set<Function> compFcts = this.compToFcts.get(comp);
        
        if (compFcts.contains(fct))
        {
            compFcts.remove(fct);
            this.compToFcts.put(comp, compFcts);
            
            this.fctToComp.remove(fct);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Déplace une fonction d'un composant à un autre.
     */
    public boolean moveFunction(Function fct, Component from, Component to)
    {
        boolean done = false;

        Set<Function> fromFcts = this.compToFcts.get(from);
        
        if (fromFcts.contains(fct))
        {
            fromFcts.remove(fct);
            this.compToFcts.put(from, fromFcts);
            
            Set<Function> toFcts = this.compToFcts.get(to);
            toFcts.add(fct);
            this.compToFcts.put(from, toFcts);
            
            this.fctToComp.put(fct, to);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Ajoute une variable à un composant.
     */
    public boolean addVariable(Variable var, Component comp)
    {
        boolean done = false;
        
        if (this.varToComp.containsKey(var) == false)
        {
            Set<Variable> compVars = this.compToVars.get(comp);
            this.compToVars.put(comp, compVars);
            this.varToComp.put(var, comp);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Retire une variable d'une fonction.
     */
    public boolean removeVariable(Variable var, Component comp)
    {
        boolean done = false;

        Set<Variable> compVars = this.compToVars.get(comp);
        
        if (compVars.contains(var))
        {
            compVars.remove(var);
            this.compToVars.put(comp, compVars);
            
            this.varToComp.remove(var);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Déplace une variable d'un composant à un autre.
     */
    public boolean moveVariable(Variable var, Component from, Component to)
    {
        boolean done = false;

        Set<Variable> fromVars = this.compToVars.get(from);
        
        if (fromVars.contains(var))
        {
            fromVars.remove(var);
            this.compToVars.put(from, fromVars);
            
            Set<Variable> toVars = this.compToVars.get(to);
            toVars.add(var);
            this.compToVars.put(from, toVars);
            
            this.varToComp.put(var, to);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Ajoute un type à un composant.
     */
    public boolean addType(Type t, Component comp)
    {
        boolean done = false;
        
        if (this.typeToComp.containsKey(t) == false)
        {
            Set<Type> compTypes = this.compToTypes.get(comp);
            this.compToTypes.put(comp, compTypes);
            this.typeToComp.put(t, comp);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Retire un type d'une fonction.
     */
    public boolean removeType(Type t, Component comp)
    {
        boolean done = false;

        Set<Type> compTypes = this.compToTypes.get(comp);
        
        if (compTypes.contains(t))
        {
            compTypes.remove(t);
            this.compToTypes.put(comp, compTypes);
            
            this.typeToComp.remove(t);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Déplace un type d'un composant à un autre.
     */
    public boolean moveType(Type t, Component from, Component to)
    {
        boolean done = false;

        Set<Type> fromTypes = this.compToTypes.get(from);
        
        if (fromTypes.contains(t))
        {
            fromTypes.remove(t);
            this.compToTypes.put(from, fromTypes);
            
            Set<Type> toTypes = this.compToTypes.get(to);
            toTypes.add(t);
            this.compToTypes.put(from, toTypes);
            
            this.typeToComp.put(t, to);
            
            done = true;
        }
        
        return done;
    }
}
