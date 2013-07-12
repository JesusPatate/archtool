package fr.univ_nantes.alma.archtool.coa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Facade;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;

public class COAFacades
{
    private Map<Function, Facade> fctToFcd =
            new HashMap<Function, Facade>();

    private Map<GlobalVariable, Facade> varToFcd =
            new HashMap<GlobalVariable, Facade>();

    private Map<ComplexType, Facade> typeToFcd =
            new HashMap<ComplexType, Facade>();

    private Map<Facade, Set<Function>> fcdToFcts =
            new HashMap<Facade, Set<Function>>();

    private Map<Facade, Set<GlobalVariable>> fcdToVars =
            new HashMap<Facade, Set<GlobalVariable>>();

    private Map<Facade, Set<ComplexType>> fcdToTypes =
            new HashMap<Facade, Set<ComplexType>>();

    /**
     * Retourne l'ensemble des fonctions d'une facade.
     */
    public Set<Function> getFunctions(Facade fcd)
    {
        return this.fcdToFcts.get(fcd);
    }

    /**
     * Retourne l'ensemble des variables d'une facade.
     */
    public Set<GlobalVariable> getVariables(Facade fcd)
    {
        return this.fcdToVars.get(fcd);
    }

    /**
     * Retourne l'ensemble des types d'une facade.
     */
    public Set<ComplexType> getTypes(Facade fcd)
    {
        return this.fcdToTypes.get(fcd);
    }

    /**
     * Retourne l'facade qui contient une fonction donnée.
     * 
     * @param fct
     *            La fonction recherchée.
     */
    public Facade getFacade(final Function fct)
    {
        return this.fctToFcd.get(fct);
    }

    /**
     * Retourne l'facade qui contient une variable donnée.
     * 
     * @param var
     *            La variable recherchée.
     */
    public Facade getFacade(final GlobalVariable var)
    {
        return this.varToFcd.get(var);
    }

    /**
     * Retourne le connecteur qui contient un type donné.
     * 
     * @param t
     *            Le type recherché.
     */
    public Facade getFacade(final ComplexType t)
    {
        return this.typeToFcd.get(t);
    }

    /**
     * Ajoute une nouvelle facade.
     */
    public boolean newFacade(Facade fcd)
    {
        boolean done = false;

        if (this.fcdToFcts.containsKey(fcd) == false)
        {
            this.fcdToFcts.put(fcd, new HashSet<Function>());
            this.fcdToVars.put(fcd, new HashSet<GlobalVariable>());
            this.fcdToTypes.put(fcd, new HashSet<ComplexType>());

            done = true;
        }

        return done;
    }

    /**
     * Supprime une facade du COA.
     * 
     * <p>
     * Une facade peut être supprimé uniquement s'il est vide.
     * </p>
     * 
     * @param fcd
     *            L'facade à supprimer
     */
    public boolean removeFacade(Facade fcd)
    {
        boolean done = false;

        if (this.knows(fcd))
        {
            Set<Function> fcts = this.fcdToFcts.get(fcd);

            for (Function fct : fcts)
            {
                removeFunction(fct, fcd);
            }

            Set<GlobalVariable> vars = this.fcdToVars.get(fcd);

            for (GlobalVariable var : vars)
            {
                removeVariable(var, fcd);
            }

            Set<ComplexType> types = this.fcdToTypes.get(fcd);

            for (ComplexType type : types)
            {
                removeType(type, fcd);
            }

            this.fcdToFcts.remove(fcd);
            this.fcdToVars.remove(fcd);
            this.fcdToTypes.remove(fcd);
        }

        return done;
    }

    /**
     * Ajoute une fonction à un facade.
     */
    public boolean addFunction(Function fct, Facade fcd)
    {
        boolean done = false;

        this.fctToFcd.put(fct, fcd);

        if (this.fcdToFcts.containsKey(fcd) == false)
        {
            Set<Function> fcdFcts = new HashSet<Function>();
            fcdFcts.add(fct);

            this.fcdToFcts.put(fcd, fcdFcts);
            this.fcdToTypes.put(fcd, new HashSet<ComplexType>());
            this.fcdToVars.put(fcd, new HashSet<GlobalVariable>());
            done = true;
        }

        else
        {
            done = this.fcdToFcts.get(fcd).add(fct);
        }

        return done;
    }

    /**
     * Retire une fonction d'une facade.
     */
    public boolean removeFunction(Function fct, Facade fcd)
    {
        boolean done = false;

        Set<Function> fcdFcts = this.fcdToFcts.get(fcd);

        if (fcdFcts != null)
        {
            if (fcdFcts.contains(fct))
            {
                fcdFcts.remove(fct);
                this.fcdToFcts.put(fcd, fcdFcts);

                this.fctToFcd.remove(fct);

                done = true;
            }
        }

        return done;
    }

    /**
     * Déplace une fonction d'une facade à une autre.
     */
    public boolean moveFunction(Function fct, Facade from, Facade to)
    {
        boolean done = false;

        Set<Function> fromFcts = this.fcdToFcts.get(from);
        Set<Function> toFcts = this.fcdToFcts.get(to);

        if(fromFcts != null && toFcts != null)
        {
            if (fromFcts.contains(fct) && (toFcts.contains(fct) == false))
            {
                fromFcts.remove(fct);
                this.fcdToFcts.put(from, fromFcts);
    
                toFcts.add(fct);
                this.fcdToFcts.put(from, toFcts);
    
                this.fctToFcd.put(fct, to);
    
                done = true;
            }
        }

        return done;
    }

    /**
     * Ajoute une variable à une facade.
     */
    public boolean addVariable(GlobalVariable var, Facade fcd)
    {
        boolean done = false;

        this.varToFcd.put(var, fcd);

        if (this.fcdToVars.containsKey(fcd) == false)
        {
            Set<GlobalVariable> fcdVars = new HashSet<GlobalVariable>();
            fcdVars.add(var);

            this.fcdToVars.put(fcd, fcdVars);
            this.fcdToFcts.put(fcd, new HashSet<Function>());
            this.fcdToTypes.put(fcd, new HashSet<ComplexType>());
            done = true;
        }

        else
        {
            done = this.fcdToVars.get(fcd).add(var);
        }

        return done;
    }

    /**
     * Retire une variable d'une facade.
     */
    public boolean removeVariable(GlobalVariable var, Facade fcd)
    {
        boolean done = false;

        Set<GlobalVariable> fcdVars = this.fcdToVars.get(fcd);

        if (fcdVars != null)
        {
            if (fcdVars.contains(var))
            {
                fcdVars.remove(var);
                this.fcdToVars.put(fcd, fcdVars);

                this.varToFcd.remove(var);

                done = true;
            }
        }

        return done;
    }

    /**
     * Déplace une variable d'une facade à une autre.
     */
    public boolean
            moveVariable(GlobalVariable var, Facade from, Facade to)
    {
        boolean done = false;

        Set<GlobalVariable> fromVars = this.fcdToVars.get(from);
        Set<GlobalVariable> toVars = this.fcdToVars.get(to);

        if(fromVars != null && toVars != null)
        {
            if (fromVars.contains(var) && (toVars.contains(var) == false))
            {
                fromVars.remove(var);
                this.fcdToVars.put(from, fromVars);
    
                toVars.add(var);
                this.fcdToVars.put(from, toVars);
    
                this.varToFcd.put(var, to);
    
                done = true;
            }
        }

        return done;
    }

    /**
     * Ajoute un type à une facade.
     */
    public boolean addType(ComplexType t, Facade fcd)
    {
        boolean done = false;

        this.typeToFcd.put(t, fcd);

        if (this.fcdToTypes.containsKey(fcd) == false)
        {
            Set<ComplexType> fcdTypes = new HashSet<ComplexType>();
            fcdTypes.add(t);

            this.fcdToTypes.put(fcd, fcdTypes);
            this.fcdToFcts.put(fcd, new HashSet<Function>());
            this.fcdToVars.put(fcd, new HashSet<GlobalVariable>());
            done = true;
        }

        else
        {
            done = this.fcdToTypes.get(fcd).add(t);
        }

        return done;
    }

    /**
     * Retire un type d'une facade.
     */
    public boolean removeType(ComplexType t, Facade fcd)
    {
        boolean done = false;

        Set<ComplexType> fcdTypes = this.fcdToTypes.get(fcd);

        if (fcdTypes != null)
        {
            if (fcdTypes.contains(t))
            {
                fcdTypes.remove(t);
                this.fcdToTypes.put(fcd, fcdTypes);

                this.typeToFcd.remove(t);

                done = true;
            }
        }

        return done;
    }

    /**
     * Déplace un type d'une facade à une autre.
     */
    public boolean moveType(ComplexType t, Facade from, Facade to)
    {
        boolean done = false;

        Set<ComplexType> fromTypes = this.fcdToTypes.get(from);
        Set<ComplexType> toTypes = this.fcdToTypes.get(to);

        if(fromTypes != null & toTypes != null)
        {
            if (fromTypes.contains(t) && (toTypes.contains(t) == false))
            {
                fromTypes.remove(t);
                this.fcdToTypes.put(from, fromTypes);
    
                toTypes.add(t);
                this.fcdToTypes.put(to, toTypes);
    
                this.typeToFcd.put(t, to);
    
                done = true;
            }
        }

        return done;
    }

    /**
     * Teste si une facade est répertoriée par le COA.
     */
    public boolean knows(final Facade fcd)
    {
        return this.fcdToFcts.containsKey(fcd);
    }

    public String toString()
    {
        StringBuffer buf = new StringBuffer();

        for (Facade fcd : this.fcdToFcts.keySet())
        {
            buf.append(fcd + " (");

            for (Function fct : this.fcdToFcts.get(fcd))
            {
                buf.append(fct.getName());
                buf.append(", ");
            }

            for (GlobalVariable var : this.fcdToVars.get(fcd))
            {
                buf.append(var.getName());
                buf.append(", ");
            }

            for (ComplexType type : this.fcdToTypes.get(fcd))
            {
                buf.append(type.getName());
                buf.append(", ");
            }

            int idx = buf.lastIndexOf(",");

            if (idx > 0)
            {
                buf.delete(idx, buf.length());
            }

            buf.append("), ");
        }

        return buf.toString();
    }
}
