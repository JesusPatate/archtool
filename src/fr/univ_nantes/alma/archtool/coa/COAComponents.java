package fr.univ_nantes.alma.archtool.coa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Type;

class COAComponents
{
    private Map<Function, Component> fctToComp = 
            new HashMap<Function, Component>();

    private Map<GlobalVariable, Component> varToComp = 
            new HashMap<GlobalVariable, Component>();

    private Map<ComplexType, Component> typeToComp = 
            new HashMap<ComplexType, Component>();

    private Map<Component, Set<Function>> compToFcts = 
            new HashMap<Component, Set<Function>>();

    private Map<Component, Set<GlobalVariable>> compToVars = 
            new HashMap<Component, Set<GlobalVariable>>();

    private Map<Component, Set<ComplexType>> compToTypes = 
            new HashMap<Component, Set<ComplexType>>();
    
    /**
     * Retourne l'ensemble des fonctions d'un composant.
     */
    public Set<Function> getFunctions(Component comp)
    {
        return new HashSet<Function>(this.compToFcts.get(comp));
    }

    /**
     * Retourne l'ensemble des variables d'un composant.
     */
    public Set<GlobalVariable> getVariables(Component comp)
    {
        return new HashSet<GlobalVariable>(this.compToVars.get(comp));
    }

    /**
     * Retourne l'ensemble des types d'un composant.
     */
    public Set<ComplexType> getTypes(Component comp)
    {
        return new HashSet<ComplexType>(this.compToTypes.get(comp));
    }
    
    /**
     * Retourne le composant qui contient une fonction donnée.
     * 
     * @param fct La fonction recherchée.
     */
    public Component getComponent(final Function fct)
    {
        return this.fctToComp.get(fct);
    }

    /**
     * Retourne le composant qui contient une variable donnée.
     * 
     * @param var La variable recherchée.
     */
    public Component getComponent(final GlobalVariable var)
    {
        return this.varToComp.get(var);
    }

    /**
     * Retourne le composant qui contient un type donné.
     * 
     * @param t Le type recherché.
     */
    public Component getComponent(final ComplexType t)
    {
        return this.typeToComp.get(t);
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
            this.compToTypes.put(comp, new HashSet<ComplexType>());

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

        this.fctToComp.put(fct, comp);

        if (this.compToFcts.containsKey(comp) == false)
        {
            Set<Function> compFcts = new HashSet<Function>();
            compFcts.add(fct);

            this.compToFcts.put(comp, compFcts);
            done = true;
        }

        else
        {
            done = this.compToFcts.get(comp).add(fct);
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

        this.varToComp.put(var, comp);

        if (this.compToVars.containsKey(comp) == false)
        {
            Set<GlobalVariable> compVars = new HashSet<GlobalVariable>();
            compVars.add(var);

            this.compToVars.put(comp, compVars);
            done = true;
        }

        else
        {
            done = this.compToVars.get(comp).add(var);
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
    public boolean addType(ComplexType t, Component comp)
    {
        boolean done = false;

        this.typeToComp.put(t, comp);

        if (this.compToVars.containsKey(comp) == false)
        {
            Set<ComplexType> compTypes = new HashSet<ComplexType>();
            compTypes.add(t);

            this.compToTypes.put(comp, compTypes);
            done = true;
        }

        else
        {
            done = this.compToTypes.get(comp).add(t);
        }

        return done;
    }

    /**
     * Retire un type d'une fonction.
     */
    public boolean removeType(ComplexType t, Component comp)
    {
        boolean done = false;

        Set<ComplexType> compTypes = this.compToTypes.get(comp);

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
    public boolean moveType(ComplexType t, Component from, Component to)
    {
        boolean done = false;

        Set<ComplexType> fromTypes = this.compToTypes.get(from);

        if (fromTypes.contains(t))
        {
            fromTypes.remove(t);
            this.compToTypes.put(from, fromTypes);

            Set<ComplexType> toTypes = this.compToTypes.get(to);
            toTypes.add(t);
            this.compToTypes.put(from, toTypes);

            this.typeToComp.put(t, to);

            done = true;
        }

        return done;
    }
    
    /**
     * Retourne toutes les fonctions d'un composant qui utilise des éléments
     *  extérieurs au composant
     */
    public Set<Function> getFunctionsToOut(Component component)
    {
        Set<Function> toOut = new HashSet<Function>();
        
        for(Function function : this.compToFcts.get(component))
        {
            if(!this.compToFcts.get(component).
                    containsAll(function.getCoreCalledFunctions().keySet()) ||
                    !this.compToTypes.get(component).
                    containsAll(function.getCoreComplexTypes().keySet()) ||
                    !this.compToVars.get(component).
                    containsAll(function.getCoreGlobalVariables().keySet()))
            {
                toOut.add(function);
            }
        }
        
        return toOut;
    }
    
    /**
     * Retourne toutes les variables globales d'un composant qui utilise des
     * éléments extérieurs au composant
     */
    public Set<GlobalVariable> getGlobalsToOut(Component component)
    {
        Set<GlobalVariable> toOut = new HashSet<GlobalVariable>();
        
        for(GlobalVariable global : this.compToVars.get(component))
        {
            Type type = global.getType();
            
            if(type.isComplex() && 
                    this.typeToComp.get((ComplexType) type) != component)
            {
                toOut.add(global);
            }
        }
        
        return toOut;
    }
    
    /**
     * Retourne toutes les fonctions du composant appelées par des fonctions
     * n'appartenant pas au composant.
     */
    public Set<Function> getFunctionsToIn(Component component)
    {
        Set<Function> toIn = new HashSet<Function>();
        Set<Function> componentFunctions = this.compToFcts.get(component);   
        
        for(Function function : componentFunctions)
        {
            if(!componentFunctions.
                    containsAll(function.getCoreCallingFunctions().keySet()))
            {
                toIn.add(function);
            }
        }
        
        return toIn;
    }
    
    /**
     * Retourne toutes les variables globales du composant utilisées par des
     * fonctions n'appartenant pas au composant.
     */
    public Set<GlobalVariable> getGlobalsToIn(Component component)
    {
        Set<GlobalVariable> toIn = new HashSet<GlobalVariable>();
        
        for(GlobalVariable global : this.compToVars.get(component))
        {
            if(!this.compToFcts.get(component).
                    containsAll(global.getCoreUsingFunctions().keySet()))
            {
                toIn.add(global);
            }
        }
        
        return toIn;
    }
    
    /**
     * Retourne tous les types du composant utilisés par des
     * fonctions ou des variables globales n'appartenant pas au composant.
     */
    public Set<ComplexType> getTypesToIn(Component component)
    {
        Set<ComplexType> toIn = new HashSet<ComplexType>();
        
        for(ComplexType type : this.compToTypes.get(component))
        {
            if(!this.compToFcts.get(component).containsAll(
                    type.getCoreUsingFunctions().keySet()) ||
                    !this.compToVars.get(component).
                    containsAll(type.getCoreUsingGlobalVariables()))
            {
                toIn.add(type);
            }
        }
        
        return toIn;
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
            buf.append(comp + " (");
            
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
            
            for(ComplexType type : this.compToTypes.get(comp))
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
