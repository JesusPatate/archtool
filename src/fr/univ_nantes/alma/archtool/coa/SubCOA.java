package fr.univ_nantes.alma.archtool.coa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Type;

public class SubCOA<A>
{
    private Map<Function, A> functionToArchElement = 
            new HashMap<Function, A>();

    private Map<GlobalVariable, A> globalVariableToArchElement = 
            new HashMap<GlobalVariable, A>();

    private Map<ComplexType, A> complexTypeToArchElement = 
            new HashMap<ComplexType, A>();

    private Map<A, Set<Function>> archElementToFunctions = 
            new HashMap<A, Set<Function>>();

    private Map<A, Set<GlobalVariable>> archElementToGlobalVariables = 
            new HashMap<A, Set<GlobalVariable>>();

    private Map<A, Set<ComplexType>> archElementToComplexTypes = 
            new HashMap<A, Set<ComplexType>>();
    
    /**
     * Retourne l'ensemble des fonctions d'un élément architectural.
     */
    public Set<Function> getFunctions(A archElement)
    {
        return new HashSet<Function>(
                this.archElementToFunctions.get(archElement));
    }
    
    /**
     * Retourne l'ensemble des éléments architecturaux du COA.
     */
    public Set<A> getaArchElements()
    {
        return this.archElementToFunctions.keySet();
    }

    /**
     * Retourne l'ensemble des variables globales d'un élément architectural.
     */
    public Set<GlobalVariable> getGlobalVariables(A archElement)
    {
        return new HashSet<GlobalVariable>(
                this.archElementToGlobalVariables.get(archElement));
    }

    /**
     * Retourne l'ensemble des types complexes d'un élément architectural.
     */
    public Set<ComplexType> getComplexTypes(A archElement)
    {
        return new HashSet<ComplexType>(
                this.archElementToComplexTypes.get(archElement));
    }
    
    /**
     * Retourne l'élément architectural qui contient une fonction donnée.
     * 
     * @param function La fonction recherchée.
     */
    public A getArchElement(final Function function)
    {
        return this.functionToArchElement.get(function);
    }

    /**
     * Retourne l'élément architectural qui contient une variable globale
     *  donnée.
     * 
     * @param globalVariable La variable recherchée.
     */
    public A getArchElement(final GlobalVariable globalVariable)
    {
        return this.globalVariableToArchElement.get(globalVariable);
    }

    /**
     * Retourne l'élément architectural qui contient un type complexe donné.
     * 
     * @param type Le type recherché.
     */
    public A getArchElement(final ComplexType type)
    {
        return this.complexTypeToArchElement.get(type);
    }

    /**
     * Ajoute un nouvel élément architectural.
     */
    public boolean newArchElement(A archElement)
    {
        boolean done = false;

        if (this.archElementToFunctions.containsKey(archElement) == false)
        {
            this.archElementToFunctions.put(archElement, 
                    new HashSet<Function>());
            this.archElementToGlobalVariables.put(archElement, 
                    new HashSet<GlobalVariable>());
            this.archElementToComplexTypes.put(archElement, 
                    new HashSet<ComplexType>());
            done = true;
        }

        return done;
    }

    /**
     * Supprime un élément architectural du COA.
     * 
     * <p>
     * Un élément architectural peut être supprimé uniquement s'il est vide.
     * </p>
     * 
     * @param archElement
     *            L'élément architectural à supprimer
     */
    public boolean removeArchElement(A archElement)
    {
        boolean done = false;

        if (this.knows(archElement))
        {
            if (this.archElementToFunctions.get(archElement).isEmpty()
                    && this.archElementToGlobalVariables.get(archElement)
                    .isEmpty() && this.archElementToComplexTypes.
                    get(archElement).isEmpty())
            {
                this.archElementToFunctions.remove(archElement);
                this.archElementToGlobalVariables.remove(archElement);
                this.archElementToComplexTypes.remove(archElement);
                done = true;
            }
        }

        return done;
    }

    /**
     * Ajoute une fonction à un élément architectural.
     * L'élémnent architectural doit déjà être connu du coa.
     */
    public boolean addFunction(Function function, A archElement)
    {
        this.functionToArchElement.put(function, archElement);
        return this.archElementToFunctions.get(archElement).add(function);
    }

    /**
     * Retire une fonction d'un élément architectural.
     */
    public boolean removeFunction(Function function, A archElement)
    {
        boolean done = false;
        Set<Function> compFcts = this.archElementToFunctions.get(archElement);

        if (compFcts.contains(function))
        {
            compFcts.remove(function);
            this.functionToArchElement.remove(function);
            done = true;
            this.removeArchElement(archElement);
        }
        
        return done;
    }

    /**
     * Déplace une fonction d'un élément architectural à un autre.
     */
    public boolean moveFunction(Function function, A from, A to)
    {
        boolean done = false;
        Set<Function> fromFcts = this.archElementToFunctions.get(from);

        if (fromFcts.contains(function))
        {
            fromFcts.remove(function);
            Set<Function> toFcts = this.archElementToFunctions.get(to);
            toFcts.add(function);
            this.functionToArchElement.put(function, to);
            done = true;
            this.removeArchElement(from);
        }

        return done;
    }

    /**
     * Ajoute une variable à un élément architectural.
     * L'élémnent architectural doit déjà être connu du coa.
     */
    public boolean addGlobalVariable(GlobalVariable globalVariable, A archElement)
    {
        this.globalVariableToArchElement.put(globalVariable, archElement);
        return this.archElementToGlobalVariables.get(archElement).
                add(globalVariable);
    }

    /**
     * Retire une variable d'un élément architectural.
     */
    public boolean removeGlobalVariable(GlobalVariable globalVariable, A archElement)
    {
        boolean done = false;
        Set<GlobalVariable> compVars = 
                this.archElementToGlobalVariables.get(archElement);

        if (compVars.contains(globalVariable))
        {
            compVars.remove(globalVariable);
            this.globalVariableToArchElement.remove(globalVariable);
            done = true;
            this.removeArchElement(archElement);
        }

        return done;
    }

    /**
     * Déplace une variable d'un élément architectural à un autre.
     */
    public boolean moveGlobalVariable(GlobalVariable var, A from, A to)
    {
        boolean done = false;
        Set<GlobalVariable> fromVars = 
                this.archElementToGlobalVariables.get(from);

        if (fromVars.contains(var))
        {
            fromVars.remove(var);
            Set<GlobalVariable> toVars = 
                    this.archElementToGlobalVariables.get(to);
            toVars.add(var);
            this.globalVariableToArchElement.put(var, to);
            done = true;
            this.removeArchElement(from);
        }

        return done;
    }

    /**
     * Ajoute un type à un élément architectural.
     * L'élémnent architectural doit déjà être connu du coa.
     */
    public boolean addComplexType(ComplexType t, A archElement)
    {
        this.complexTypeToArchElement.put(t, archElement);
        return this.archElementToComplexTypes.get(archElement).add(t);
    }

    /**
     * Retire un type d'un élément architectural.
     */
    public boolean removeComplexType(ComplexType t, A archElement)
    {
        boolean done = false;
        Set<ComplexType> compTypes = 
                this.archElementToComplexTypes.get(archElement);

        if (compTypes.contains(t))
        {
            compTypes.remove(t);
            this.complexTypeToArchElement.remove(t);
            done = true;
            this.removeArchElement(archElement);
        }

        return done;
    }

    /**
     * Déplace un type d'un élément architectural à un autre.
     */
    public boolean moveComplexType(ComplexType t, A from, A to)
    {
        boolean done = false;
        Set<ComplexType> fromTypes = this.archElementToComplexTypes.get(from);

        if (fromTypes.contains(t))
        {
            fromTypes.remove(t);
            Set<ComplexType> toTypes = this.archElementToComplexTypes.get(to);
            toTypes.add(t);
            this.complexTypeToArchElement.put(t, to);
            done = true;
            this.removeArchElement(from);
        }

        return done;
    }
    
    /**
     * Retourne toutes les fonctions d'un élément architectural qui utilise des
     *  éléments architecturaux extérieurs à lui.
     */
    public Set<Function> getFunctionsToOut(A archElement)
    {
        Set<Function> toOut = new HashSet<Function>();
        
        for(Function function : this.archElementToFunctions.get(archElement))
        {
            if(!this.archElementToFunctions.get(archElement).
                    containsAll(function.getCoreCalledFunctions().keySet()) ||
                    !this.archElementToComplexTypes.get(archElement).
                    containsAll(function.getCoreComplexTypes().keySet()) ||
                    !this.archElementToGlobalVariables.get(archElement).
                    containsAll(function.getCoreGlobalVariables().keySet()))
            {
                toOut.add(function);
            }
        }
        
        return toOut;
    }
    
    /**
     * Retourne toutes les variables globales d'un élément architectural qui 
     * utilise des éléments extérieurs à lui.
     */
    public Set<GlobalVariable> getGlobalVariablesToOut(A archElement)
    {
        Set<GlobalVariable> toOut = new HashSet<GlobalVariable>();
        
        for(GlobalVariable global : 
            this.archElementToGlobalVariables.get(archElement))
        {
            Type type = global.getType();
            
            if(type.isComplex() && 
                    this.complexTypeToArchElement.
                    get((ComplexType) type) != archElement)
            {
                toOut.add(global);
            }
        }
        
        return toOut;
    }
    
    /**
     * Retourne toutes les fonctions de l'élément architectural appelées par des
     * fonctions n'appartenant pas à cet élément.
     */
    public Set<Function> getFunctionsToIn(A archElement)
    {
        Set<Function> toIn = new HashSet<Function>();
        Set<Function> componentFunctions = 
                this.archElementToFunctions.get(archElement);   
        
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
     * Retourne toutes les variables globales de l'élément architectural 
     * utilisées par des fonctions n'appartenant pas à cet élément.
     */
    public Set<GlobalVariable> getGlobalVariablesToIn(A archElement)
    {
        Set<GlobalVariable> toIn = new HashSet<GlobalVariable>();
        
        for(GlobalVariable global : 
            this.archElementToGlobalVariables.get(archElement))
        {
            if(!this.archElementToFunctions.get(archElement).
                    containsAll(global.getCoreUsingFunctions().keySet()))
            {
                toIn.add(global);
            }
        }
        
        return toIn;
    }
    
    /**
     * Retourne tous les types de l'élément architectural utilisés par des
     * fonctions ou des variables globales n'appartenant pas à cet élément.
     */
    public Set<ComplexType> getComplexTypesToIn(A archElement)
    {
        Set<ComplexType> toIn = new HashSet<ComplexType>();
        
        for(ComplexType type : this.archElementToComplexTypes.get(archElement))
        {
            if(!this.archElementToFunctions.get(archElement).containsAll(
                    type.getCoreUsingFunctions().keySet()) ||
                    !this.archElementToGlobalVariables.get(archElement).
                    containsAll(type.getCoreUsingGlobalVariables()))
            {
                toIn.add(type);
            }
        }
        
        return toIn;
    }
    
    /**
     * Teste si un élément architectural est répertorié par le COA.
     */
    public boolean knows(A archElement)
    {
        return this.archElementToFunctions.containsKey(archElement);
    }
    
    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        
        for(A archElement : this.archElementToFunctions.keySet())
        {
            buf.append(archElement + " (");
            
            for(Function fct : this.archElementToFunctions.get(archElement))
            {
                buf.append(fct.getName());
                buf.append(", ");
            }
            
            for(GlobalVariable var : 
                this.archElementToGlobalVariables.get(archElement))
            {
                buf.append(var.getName());
                buf.append(", ");
            }
            
            for(ComplexType type : 
                this.archElementToComplexTypes.get(archElement))
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
