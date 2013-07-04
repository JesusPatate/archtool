package fr.univ_nantes.alma.archtool.coa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.architectureModel.Connector;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.Type;
import fr.univ_nantes.alma.archtool.sourceModel.Variable;

public class COAInterfaces
{
    private Map<Function, Interface> fctToItf = new HashMap<Function, Interface>();
    
    private Map<Variable, Interface> varToItf = new HashMap<Variable, Interface>();
    
    private Map<Type, Set<Interface>> typeToItf = new HashMap<Type, Set<Interface>>();
    
    private Map<Interface, Set<Function>> itfToFcts = new HashMap<Interface, Set<Function>>();
    
    private Map<Interface, Set<Variable>> itfToVars = new HashMap<Interface, Set<Variable>>();
    
    private Map<Interface, Set<Type>> itfToTypes = new HashMap<Interface, Set<Type>>();

    
    /**
     * Retourne l'ensemble des fonctions d'une interface.
     */
    public Set<Function> getInterfaceFunctions(Interface itf)
    {
        return this.itfToFcts.get(itf);
    }
    
    /**
     * Retourne l'ensemble des variables d'une interface.
     */
    public Set<Variable> getInterfaceVariables(Interface itf)
    {
        return this.itfToVars.get(itf);
    }
    
    /**
     * Retourne l'ensemble des types d'une interface.
     */
    public Set<Type> getInterfaceTypes(Interface itf)
    {
        return this.itfToTypes.get(itf);
    }
    
    /**
     * Ajoute une nouvelle interface.
     */
    public boolean newInterface(Interface itf)
    {
       boolean done = false;
       
       if(this.itfToFcts.containsKey(itf) == false)
       {
           this.itfToFcts.put(itf, new HashSet<Function>());
           this.itfToVars.put(itf, new HashSet<Variable>());
           this.itfToTypes.put(itf, new HashSet<Type>());
           
           done = true;
       }
       
       return done;
    }

    /**
     * Supprime une interface du COA.
     * 
     * <p>
     * Une interface peut être supprimé uniquement s'il est vide.
     * </p>
     * 
     * @param itf
     *            L'interface à supprimer
     */
    public boolean removeInterface(Interface itf)
    {
        boolean done = false;

        if (this.knows(itf))
        {
            if (this.itfToFcts.get(itf).isEmpty()
                    && this.itfToVars.get(itf).isEmpty()
                    && this.itfToTypes.get(itf).isEmpty())
            {
                this.itfToFcts.remove(itf);
                this.itfToVars.remove(itf);
                this.itfToTypes.remove(itf);

                done = true;
            }
        }

        return done;
    }
    
    /**
     * Ajoute une fonction à un interface.
     */
    public boolean addFunction(Function fct, Interface itf)
    {
        boolean done = false;
        
        Set<Function> itfFcts = this.itfToFcts.get(itf);
        
        if (itfFcts.contains(fct) == false)
        {
            itfFcts.add(fct);
            this.itfToFcts.put(itf, itfFcts);
            this.fctToItf.put(fct, itf);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Retire une fonction d'une interface.
     */
    public boolean removeFunction(Function fct, Interface itf)
    {
        boolean done = false;

        Set<Function> itfFcts = this.itfToFcts.get(itf);
        
        if (itfFcts.contains(fct))
        {
            itfFcts.remove(fct);
            this.itfToFcts.put(itf, itfFcts);
            
            this.fctToItf.remove(fct);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Déplace une fonction d'une interface à une autre.
     */
    public boolean moveFunction(Function fct, Interface from, Interface to)
    {
        boolean done = false;

        Set<Function> fromFcts = this.itfToFcts.get(from);
        Set<Function> toFcts = this.itfToFcts.get(to);
        
        if (fromFcts.contains(fct) && (toFcts.contains(fct) == false))
        {
            fromFcts.remove(fct);
            this.itfToFcts.put(from, fromFcts);
            
            toFcts.add(fct);
            this.itfToFcts.put(from, toFcts);
            
            this.fctToItf.put(fct, to);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Ajoute une variable à une interface.
     */
    public boolean addVariable(Variable var, Interface itf)
    {
        boolean done = false;
        
        Set<Variable> itfVars = this.itfToVars.get(itf);
        
        if (itfVars.contains(var) == false)
        {
            itfVars.add(var);
            this.itfToVars.put(itf, itfVars);
            this.varToItf.put(var, itf);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Retire une variable d'une interface.
     */
    public boolean removeVariable(Variable var, Interface itf)
    {
        boolean done = false;

        Set<Variable> itfVars = this.itfToVars.get(itf);
        
        if (itfVars.contains(var))
        {
            itfVars.remove(var);
            this.itfToVars.put(itf, itfVars);
            
            this.varToItf.remove(var);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Déplace une variable d'une interface à une autre.
     */
    public boolean moveVariable(Variable var, Interface from, Interface to)
    {
        boolean done = false;

        Set<Variable> fromVars = this.itfToVars.get(from);
        Set<Variable> toVars = this.itfToVars.get(to);
        
        if (fromVars.contains(var) && (toVars.contains(var) == false))
        {
            fromVars.remove(var);
            this.itfToVars.put(from, fromVars);
            
            toVars.add(var);
            this.itfToVars.put(from, toVars);
            
            this.varToItf.put(var, to);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Ajoute un type à une interface.
     */
    public boolean addType(Type t, Interface itf)
    {
        boolean done = false;
        
        if ( this.typeToItf.get(t).contains(itf) == false)
        {
            Set<Type> itfTypes = this.itfToTypes.get(itf);
            itfTypes.add(t);
            
            this.itfToTypes.put(itf, itfTypes);
            this.typeToItf.get(t).add(itf);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Retire un type d'une interface.
     */
    public boolean removeType(Type t, Interface itf)
    {
        boolean done = false;

        Set<Type> itfTypes = this.itfToTypes.get(itf);
        
        if (itfTypes.contains(t))
        {
            itfTypes.remove(t);
            this.itfToTypes.put(itf, itfTypes);
            
            this.typeToItf.get(t).remove(itf);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Déplace un type d'une interface à une autre.
     */
    public boolean moveType(Type t, Interface from, Interface to)
    {
        boolean done = false;

        Set<Type> fromTypes = this.itfToTypes.get(from);
        Set<Type> toTypes = this.itfToTypes.get(to);
        
        if (fromTypes.contains(t) && (toTypes.contains(t) == false))
        {
            fromTypes.remove(t);
            this.itfToTypes.put(from, fromTypes);
            
            toTypes.add(t);
            this.itfToTypes.put(to, toTypes);
            
            this.typeToItf.get(t).remove(from);
            this.typeToItf.get(t).add(to);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Teste si une interface est répertoriée par le COA.
     */
    public boolean knows(final Interface itf)
    {
        return this.itfToFcts.containsKey(itf);
    }
}
