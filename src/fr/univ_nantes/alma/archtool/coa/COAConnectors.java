package fr.univ_nantes.alma.archtool.coa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Connector;
import fr.univ_nantes.alma.archtool.architectureModel.Connector;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Type;

public class COAConnectors
{
    private Map<Function, Connector> fctToCon = new HashMap<Function, Connector>();
    
    private Map<GlobalVariable, Connector> varToCon = new HashMap<GlobalVariable, Connector>();
    
    private Map<Type, Connector> typeToCon = new HashMap<Type, Connector>();
    
    private Map<Connector, Set<Function>> conToFcts = new HashMap<Connector, Set<Function>>();
    
    private Map<Connector, Set<GlobalVariable>> conToVars = new HashMap<Connector, Set<GlobalVariable>>();
    
    private Map<Connector, Set<ComplexType>> conToTypes = new HashMap<Connector, Set<ComplexType>>();

    
    /**
     * Retourne l'ensemble des fonctions d'un connecteur.
     */
    public Set<Function> getFunctions(Connector con)
    {
        return this.conToFcts.get(con);
    }
    
    /**
     * Retourne l'ensemble des variables d'un connecteur.
     */
    public Set<GlobalVariable> getVariables(Connector con)
    {
        return this.conToVars.get(con);
    }
    
    /**
     * Retourne l'ensemble des types d'un connecteur.
     */
    public Set<ComplexType> getTypes(Connector con)
    {
        return this.conToTypes.get(con);
    }
    
    /**
     * Ajoute un nouveau connecteur.
     */
    public boolean newConnector(Connector con)
    {
       boolean done = false;
       
       if(this.conToFcts.containsKey(con) == false)
       {
           this.conToFcts.put(con, new HashSet<Function>());
           this.conToVars.put(con, new HashSet<GlobalVariable>());
           this.conToTypes.put(con, new HashSet<ComplexType>());
           
           done = true;
       }
       
       return done;
    }

    /**
     * Supprime un connecteur du COA.
     * 
     * <p>
     * Un connecteur peut être supprimé uniquement s'il est vide.
     * </p>
     * 
     * @param con
     *            Le connecteur à supprimer
     */
    public boolean removeConnector(Connector con)
    {
        boolean done = false;

        if (this.knows(con))
        {
            if (this.conToFcts.get(con).isEmpty()
                    && this.conToVars.get(con).isEmpty()
                    && this.conToTypes.get(con).isEmpty())
            {
                this.conToFcts.remove(con);
                this.conToVars.remove(con);
                this.conToTypes.remove(con);

                done = true;
            }
        }

        return done;
    }
    
    /**
     * Ajoute une fonction à un connecteur.
     */
    public boolean addFunction(Function fct, Connector con)
    {
        boolean done = false;
        
        Set<Function> conFcts = this.conToFcts.get(con);
        
        if (conFcts.contains(fct) == false)
        {
            conFcts.add(fct);
            this.conToFcts.put(con, conFcts);
            this.fctToCon.put(fct, con);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Retire une fonction d'un connecteur.
     */
    public boolean removeFunction(Function fct, Connector con)
    {
        boolean done = false;

        Set<Function> conFcts = this.conToFcts.get(con);
        
        if (conFcts.contains(fct))
        {
            conFcts.remove(fct);
            this.conToFcts.put(con, conFcts);
            
            this.fctToCon.remove(fct);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Déplace une fonction d'un connecteur à un autre.
     */
    public boolean moveFunction(Function fct, Connector from, Connector to)
    {
        boolean done = false;

        Set<Function> fromFcts = this.conToFcts.get(from);
        
        if (fromFcts.contains(fct))
        {
            fromFcts.remove(fct);
            this.conToFcts.put(from, fromFcts);
            
            Set<Function> toFcts = this.conToFcts.get(to);
            toFcts.add(fct);
            this.conToFcts.put(from, toFcts);
            
            this.fctToCon.put(fct, to);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Ajoute une variable à un connecteur.
     */
    public boolean addVariable(GlobalVariable var, Connector con)
    {
        boolean done = false;
        
        Set<GlobalVariable> conVars = this.conToVars.get(con);
        
        if (this.varToCon.containsKey(var) == false)
        {
            conVars.add(var);
            this.conToVars.put(con, conVars);
            this.varToCon.put(var, con);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Retire une variable d'un connecteur.
     */
    public boolean removeVariable(GlobalVariable var, Connector con)
    {
        boolean done = false;

        Set<GlobalVariable> conVars = this.conToVars.get(con);
        
        if (conVars.contains(var))
        {
            conVars.remove(var);
            this.conToVars.put(con, conVars);
            
            this.varToCon.remove(var);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Déplace une variable d'un connecteur à un autre.
     */
    public boolean moveVariable(GlobalVariable var, Connector from, Connector to)
    {
        boolean done = false;

        Set<GlobalVariable> fromVars = this.conToVars.get(from);
        
        if (fromVars.contains(var))
        {
            fromVars.remove(var);
            this.conToVars.put(from, fromVars);
            
            Set<GlobalVariable> toVars = this.conToVars.get(to);
            toVars.add(var);
            this.conToVars.put(from, toVars);
            
            this.varToCon.put(var, to);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Ajoute un type à un connecteur.
     */
    public boolean addType(ComplexType t, Connector con)
    {
        boolean done = false;

        Set<ComplexType> conTypes = this.conToTypes.get(con);
        
        if (this.typeToCon.containsKey(t) == false)
        {
            conTypes.add(t);
            this.conToTypes.put(con, conTypes);
            this.typeToCon.put(t, con);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Retire un type d'un connecteur.
     */
    public boolean removeType(ComplexType t, Connector con)
    {
        boolean done = false;

        Set<ComplexType> conTypes = this.conToTypes.get(con);
        
        if (conTypes.contains(t))
        {
            conTypes.remove(t);
            this.conToTypes.put(con, conTypes);
            
            this.typeToCon.remove(t);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Déplace un type d'un connecteur à un autre.
     */
    public boolean moveType(ComplexType t, Connector from, Connector to)
    {
        boolean done = false;

        Set<ComplexType> fromTypes = this.conToTypes.get(from);
        
        if (fromTypes.contains(t))
        {
            fromTypes.remove(t);
            this.conToTypes.put(from, fromTypes);
            
            Set<ComplexType> toTypes = this.conToTypes.get(to);
            toTypes.add(t);
            this.conToTypes.put(from, toTypes);
            
            this.typeToCon.put(t, to);
            
            done = true;
        }
        
        return done;
    }
    
    /**
     * Teste si un connecteur est répertorié par le COA.
     */
    public boolean knows(final Connector con)
    {
        return this.conToFcts.containsKey(con);
    }
    
    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        
        for(Connector con : this.conToFcts.keySet())
        {
            buf.append(con + " (");
            
            for(Function fct : this.conToFcts.get(con))
            {
                buf.append(fct.getName());
                buf.append(", ");
            }
            
            for(GlobalVariable var : this.conToVars.get(con))
            {
                buf.append(var.getName());
                buf.append(", ");
            }
            
            for(Type type : this.conToTypes.get(con))
            {
                buf.append(type.getName());
                buf.append(", ");
            }
            
            int idx = buf.lastIndexOf(",");
            
            if(idx > 0)
            {
                buf.delete(idx, buf.length());
            }
            
            buf.append("), ");
        }
        
        int idx = buf.lastIndexOf(",");
        
        if(idx > 0)
        {
            buf.delete(idx, buf.length());
        }
        
        return buf.toString();
    }
}
