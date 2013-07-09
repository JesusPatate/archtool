package fr.univ_nantes.alma.archtool.coa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.Type;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;

class COAComponents
{
    private Map<Function, Component> fctToComp = new HashMap<Function, Component>();

    private Map<GlobalVariable, Component> varToComp = new HashMap<GlobalVariable, Component>();

    private Map<Type, Component> typeToComp = new HashMap<Type, Component>();

    private Map<Component, Set<Function>> compToFcts = new HashMap<Component, Set<Function>>();

    private Map<Component, Set<GlobalVariable>> compToVars = new HashMap<Component, Set<GlobalVariable>>();

    private Map<Component, Set<Type>> compToTypes = new HashMap<Component, Set<Type>>();

    /**
     * Retourne l'ensemble des fonctions d'un composant.
     */
    public Set<Function> getComponentFunctions(Component comp)
    {
        return this.compToFcts.get(comp);
    }

    /**
     * Retourne l'ensemble des variables d'un composant.
     */
    public Set<GlobalVariable> getComponentVariables(Component comp)
    {
        return this.compToVars.get(comp);
    }

    /**
     * Retourne l'ensemble des types d'un composant.
     */
    public Set<Type> getComponentTypes(Component comp)
    {
        return this.compToTypes.get(comp);
    }

    /**
     * Ajoute un nouveau composant.
     */
    public boolean newComponent(Component comp)
    {
        boolean done = false;

        if (this.compToFcts.containsKey(comp) == false)
        {
            this.compToFcts.put(comp, new HashSet<Function>());
            this.compToVars.put(comp, new HashSet<GlobalVariable>());
            this.compToTypes.put(comp, new HashSet<Type>());

            done = true;
        }

        return done;
    }

    /**
     * Supprime un composant du COA.
     * 
     * <p>
     * Un composant peut être supprimé uniquement s'il est vide.
     * </p>
     * 
     * @param comp
     *            Le composant à supprimer
     */
    public boolean removeComponent(Component comp)
    {
        boolean done = false;

        if (this.knows(comp))
        {
            if (this.compToFcts.get(comp).isEmpty()
                    && this.compToVars.get(comp).isEmpty()
                    && this.compToTypes.get(comp).isEmpty())
            {
                this.compToFcts.remove(comp);
                this.compToVars.remove(comp);
                this.compToTypes.remove(comp);

                done = true;
            }
        }

        return done;
    }

    /**
     * Ajoute une fonction à un composant.
     */
    public boolean addFunction(Function fct, Component comp)
    {
        boolean done = false;

        Set<Function> compFcts = this.compToFcts.get(comp);

        if (compFcts.contains(fct) == false)
        {
            compFcts.add(fct);
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
    public boolean addVariable(GlobalVariable var, Component comp)
    {
        boolean done = false;

        Set<GlobalVariable> compVars = this.compToVars.get(comp);

        if (compVars.contains(var) == false)
        {
            compVars.add(var);
            this.compToVars.put(comp, compVars);
            this.varToComp.put(var, comp);

            done = true;
        }

        return done;
    }

    /**
     * Retire une variable d'une fonction.
     */
    public boolean removeVariable(GlobalVariable var, Component comp)
    {
        boolean done = false;

        Set<GlobalVariable> compVars = this.compToVars.get(comp);

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
    public boolean moveVariable(GlobalVariable var, Component from, Component to)
    {
        boolean done = false;

        Set<GlobalVariable> fromVars = this.compToVars.get(from);

        if (fromVars.contains(var))
        {
            fromVars.remove(var);
            this.compToVars.put(from, fromVars);

            Set<GlobalVariable> toVars = this.compToVars.get(to);
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

        Set<Type> compTypes = this.compToTypes.get(comp);

        if (compTypes.contains(t) == false)
        {
            compTypes.add(t);
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

    /**
     * Teste si un composant est répertorié par le COA.
     */
    public boolean knows(final Component comp)
    {
        return this.compToFcts.containsKey(comp);
    }
    
    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        
        for(Component comp : this.compToFcts.keySet())
        {
            buf.append("Composant(");
            
            for(Function fct : this.compToFcts.get(comp))
            {
                buf.append(fct.getName());
                buf.append(", ");
            }
            
            for(GlobalVariable var : this.compToVars.get(comp))
            {
                buf.append(var.getName());
                buf.append(", ");
            }
            
            for(Type type : this.compToTypes.get(comp))
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
        
        return buf.toString();
    }
}
