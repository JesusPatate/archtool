package fr.univ_nantes.alma.archtool.coa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;

public class COAInterfaces
{
    private Map<Function, Interface> fctToItf =
            new HashMap<Function, Interface>();

    private Map<GlobalVariable, Interface> varToItf =
            new HashMap<GlobalVariable, Interface>();

    private Map<ComplexType, Interface> typeToItf =
            new HashMap<ComplexType, Interface>();

    private Map<Interface, Set<Function>> itfToFcts =
            new HashMap<Interface, Set<Function>>();

    private Map<Interface, Set<GlobalVariable>> itfToVars =
            new HashMap<Interface, Set<GlobalVariable>>();

    private Map<Interface, Set<ComplexType>> itfToTypes =
            new HashMap<Interface, Set<ComplexType>>();

    /**
     * Retourne l'ensemble des fonctions d'une interface.
     */
    public Set<Function> getFunctions(Interface itf)
    {
        return this.itfToFcts.get(itf);
    }

    /**
     * Retourne l'ensemble des variables d'une interface.
     */
    public Set<GlobalVariable> getVariables(Interface itf)
    {
        return this.itfToVars.get(itf);
    }

    /**
     * Retourne l'ensemble des types d'une interface.
     */
    public Set<ComplexType> getTypes(Interface itf)
    {
        return this.itfToTypes.get(itf);
    }

    /**
     * Retourne l'interface qui contient une fonction donnée.
     * 
     * @param fct
     *            La fonction recherchée.
     */
    public Interface getInterface(final Function fct)
    {
        return this.fctToItf.get(fct);
    }

    /**
     * Retourne l'interface qui contient une variable donnée.
     * 
     * @param var
     *            La variable recherchée.
     */
    public Interface getInterface(final GlobalVariable var)
    {
        return this.varToItf.get(var);
    }

    /**
     * Retourne le connecteur qui contient un type donné.
     * 
     * @param t
     *            Le type recherché.
     */
    public Interface getInterface(final ComplexType t)
    {
        return this.typeToItf.get(t);
    }

    /**
     * Ajoute une nouvelle interface.
     */
    public boolean newInterface(Interface itf)
    {
        boolean done = false;

        if (this.itfToFcts.containsKey(itf) == false)
        {
            this.itfToFcts.put(itf, new HashSet<Function>());
            this.itfToVars.put(itf, new HashSet<GlobalVariable>());
            this.itfToTypes.put(itf, new HashSet<ComplexType>());

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
            Set<Function> fcts = this.itfToFcts.get(itf);

            for (Function fct : fcts)
            {
                removeFunction(fct, itf);
            }

            Set<GlobalVariable> vars = this.itfToVars.get(itf);

            for (GlobalVariable var : vars)
            {
                removeVariable(var, itf);
            }

            Set<ComplexType> types = this.itfToTypes.get(itf);

            for (ComplexType type : types)
            {
                removeType(type, itf);
            }

            this.itfToFcts.remove(itf);
            this.itfToVars.remove(itf);
            this.itfToTypes.remove(itf);
        }

        return done;
    }

    /**
     * Ajoute une fonction à un interface.
     */
    public boolean addFunction(Function fct, Interface itf)
    {
        boolean done = false;

        this.fctToItf.put(fct, itf);

        if (this.itfToFcts.containsKey(itf) == false)
        {
            Set<Function> itfFcts = new HashSet<Function>();
            itfFcts.add(fct);

            this.itfToFcts.put(itf, itfFcts);
            this.itfToTypes.put(itf, new HashSet<ComplexType>());
            this.itfToVars.put(itf, new HashSet<GlobalVariable>());
            done = true;
        }

        else
        {
            done = this.itfToFcts.get(itf).add(fct);
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

        if (itfFcts != null)
        {
            if (itfFcts.contains(fct))
            {
                itfFcts.remove(fct);
                this.itfToFcts.put(itf, itfFcts);

                this.fctToItf.remove(fct);

                done = true;
            }
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

        if(fromFcts != null && toFcts != null)
        {
            if (fromFcts.contains(fct) && (toFcts.contains(fct) == false))
            {
                fromFcts.remove(fct);
                this.itfToFcts.put(from, fromFcts);
    
                toFcts.add(fct);
                this.itfToFcts.put(from, toFcts);
    
                this.fctToItf.put(fct, to);
    
                done = true;
            }
        }

        return done;
    }

    /**
     * Ajoute une variable à une interface.
     */
    public boolean addVariable(GlobalVariable var, Interface itf)
    {
        boolean done = false;

        this.varToItf.put(var, itf);

        if (this.itfToVars.containsKey(itf) == false)
        {
            Set<GlobalVariable> itfVars = new HashSet<GlobalVariable>();
            itfVars.add(var);

            this.itfToVars.put(itf, itfVars);
            this.itfToFcts.put(itf, new HashSet<Function>());
            this.itfToTypes.put(itf, new HashSet<ComplexType>());
            done = true;
        }

        else
        {
            done = this.itfToVars.get(itf).add(var);
        }

        return done;
    }

    /**
     * Retire une variable d'une interface.
     */
    public boolean removeVariable(GlobalVariable var, Interface itf)
    {
        boolean done = false;

        Set<GlobalVariable> itfVars = this.itfToVars.get(itf);

        if (itfVars != null)
        {
            if (itfVars.contains(var))
            {
                itfVars.remove(var);
                this.itfToVars.put(itf, itfVars);

                this.varToItf.remove(var);

                done = true;
            }
        }

        return done;
    }

    /**
     * Déplace une variable d'une interface à une autre.
     */
    public boolean
            moveVariable(GlobalVariable var, Interface from, Interface to)
    {
        boolean done = false;

        Set<GlobalVariable> fromVars = this.itfToVars.get(from);
        Set<GlobalVariable> toVars = this.itfToVars.get(to);

        if(fromVars != null && toVars != null)
        {
            if (fromVars.contains(var) && (toVars.contains(var) == false))
            {
                fromVars.remove(var);
                this.itfToVars.put(from, fromVars);
    
                toVars.add(var);
                this.itfToVars.put(from, toVars);
    
                this.varToItf.put(var, to);
    
                done = true;
            }
        }

        return done;
    }

    /**
     * Ajoute un type à une interface.
     */
    public boolean addType(ComplexType t, Interface itf)
    {
        boolean done = false;

        this.typeToItf.put(t, itf);

        if (this.itfToTypes.containsKey(itf) == false)
        {
            Set<ComplexType> itfTypes = new HashSet<ComplexType>();
            itfTypes.add(t);

            this.itfToTypes.put(itf, itfTypes);
            this.itfToFcts.put(itf, new HashSet<Function>());
            this.itfToVars.put(itf, new HashSet<GlobalVariable>());
            done = true;
        }

        else
        {
            done = this.itfToTypes.get(itf).add(t);
        }

        return done;
    }

    /**
     * Retire un type d'une interface.
     */
    public boolean removeType(ComplexType t, Interface itf)
    {
        boolean done = false;

        Set<ComplexType> itfTypes = this.itfToTypes.get(itf);

        if (itfTypes != null)
        {
            if (itfTypes.contains(t))
            {
                itfTypes.remove(t);
                this.itfToTypes.put(itf, itfTypes);

                this.typeToItf.remove(t);

                done = true;
            }
        }

        return done;
    }

    /**
     * Déplace un type d'une interface à une autre.
     */
    public boolean moveType(ComplexType t, Interface from, Interface to)
    {
        boolean done = false;

        Set<ComplexType> fromTypes = this.itfToTypes.get(from);
        Set<ComplexType> toTypes = this.itfToTypes.get(to);

        if(fromTypes != null & toTypes != null)
        {
            if (fromTypes.contains(t) && (toTypes.contains(t) == false))
            {
                fromTypes.remove(t);
                this.itfToTypes.put(from, fromTypes);
    
                toTypes.add(t);
                this.itfToTypes.put(to, toTypes);
    
                this.typeToItf.put(t, to);
    
                done = true;
            }
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

    public String toString()
    {
        StringBuffer buf = new StringBuffer();

        for (Interface itf : this.itfToFcts.keySet())
        {
            buf.append(itf + " (");

            for (Function fct : this.itfToFcts.get(itf))
            {
                buf.append(fct.getName());
                buf.append(", ");
            }

            for (GlobalVariable var : this.itfToVars.get(itf))
            {
                buf.append(var.getName());
                buf.append(", ");
            }

            for (ComplexType type : this.itfToTypes.get(itf))
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
