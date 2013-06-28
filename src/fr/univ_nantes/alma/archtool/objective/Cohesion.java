package fr.univ_nantes.alma.archtool.objective;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Cohesionable;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.sourceModel.Call;
import fr.univ_nantes.alma.archtool.sourceModel.FileGlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.LocalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.ProgramGlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Type;
import fr.univ_nantes.alma.archtool.sourceModel.Variable;

public class Cohesion
{
    /**
     * Bonus accordé pour la mesure de la cohésion lorsque deux éléments
     * appartiennent au même fichier.
     */
    static final double BONUS_SAME_FILE = 1.5;
    
    /**
     * Évalue la cohésion entre 2 interfaces.
     */
    public double cohesionInterfaces(Interface itf1, Interface itf2)
    {
        double result = 0.0;
        
        Set<Function> functions1 = itf1.getFunctions();
        Set<Function> functions2 = itf2.getFunctions();
        
        Set<Variable> variables1 = itf1.getVariables();
        Set<Variable> variables2 = itf2.getVariables();
        
        Set<Type> types1 = itf1.getTypes();
        Set<Type> types2 = itf2.getTypes();
        
        for(Function fct1 : functions1)
        {
            
            for(Function fct2 : functions2)
            {
                result += cohesion(fct1, fct2);
            }
            
            for(Variable var2 : variables2)
            {
                result += cohesion(fct1, var2);
            }
            
            for(Type t2 : types2)
            {
                result += cohesion(fct1, t2);
            }
        }
        
        for(Function fct2 : functions2)
        {
            for(Variable var1 : variables1)
            {
                result += cohesion(fct2, var1);
            }
            
            for(Type t1 : types1)
            {
                result += cohesion(fct2, t1);
            }
        }
        
        return result;
    }
    
    /**
     * Évalue la cohésion interne d'un élément architectural.
     * 
     * @param element
     *            L'élément architectural à évaluer
     */
    public double internalCohesion(final Cohesionable element)
    {
        double result = 0.0;

        int nbFcts = element.getFunctions().size();
        int nbVars = element.getVariables().size();
        int nbTypes = element.getTypes().size();

        if (nbFcts > 0)
        {
            if (nbFcts > 1)
            {
                result += internalCohesionFct(element);
            }

            else if (nbVars > 1)
            {
                result += internalCohesionFctVar(element);
            }

            else if (nbTypes > 1)
            {
                result += internalCohesionFctType(element);
            }
            
            else
            {
                result = 1.0;
            }
        }

        return result;
    }
    
    /**
     * Évalue la cohésion des fonctions d'un élément architectural.
     * 
     * @param element
     *            L'élément architectural à évaluer
     */
    private double internalCohesionFct(final Cohesionable element)
    {
        double result = 0.0;
        double sum = 0.0;
        double nbFunctions = element.getFunctions().size();

        final Object[] functions = element.getFunctions().toArray();

        for (int idx1 = 0 ; idx1 < (nbFunctions - 1) ; ++idx1)
        {
            for (final int idx2 = idx1 + 1 ; idx2 < nbFunctions ; ++idx1)
            {
                final Function f1 = (Function) functions[idx1];
                final Function f2 = (Function) functions[idx2];

                sum += this.cohesion(f1, f2);
            }
        }

        result = sum / (nbFunctions * (nbFunctions - 1) / 2);

        return result;
    }

    /**
     * Évalue la cohésion entre une fonction et une variable d'un élément
     * architectural.
     * 
     * @param element
     *            L'élément architectural à évaluer
     */
    private double internalCohesionFctVar(final Cohesionable element)
    {
        double result = 0.0;
        double sum = 0.0;

        for (Function fct : element.getFunctions())
        {
            for (Variable var : element.getVariables())
            {
                sum += this.cohesion(fct, var);
            }
        }

        double nbPairs = element.getFunctions().size() * element.getVariables().size();

        result = 100 * sum / nbPairs;

        return result;
    }

    /**
     * Évalue la cohésion entre une fonction et un type d'un élément
     * architectural.
     * 
     * @param element
     *            L'élément architectural à évaluer
     */
    private double internalCohesionFctType(Cohesionable element)
    {
        double result = 0.0;
        double sum = 0.0;

        for (Function fct : element.getFunctions())
        {
            for (Type type : element.getTypes())
            {
                sum += this.cohesion(fct, type);
            }
        }

        double nbPairs = element.getFunctions().size() * element.getTypes().size();

        result = 100 * sum / nbPairs;

        return result;
    }

    /**
     * Évalue la cohésion de 2 fonctions.
     * 
     * <p>
     * La cohésion de 2 fonctions est évaluée sur la moyenne des ratios :
     * <ul>
     * <li>de variables globales communes</li>
     * <li>de variables locales communes</li>
     * <li>de types communs</li>
     * <li>d'appels de fonction communs</li>
     * </ul>
     * et sur l'appartenance à un même fichier.
     * </p>
     * 
     * @param f1
     *            La première fonction à évaluer
     * @param f2
     *            La seconde fonction à évaluer
     */
    public double cohesion(final Function f1, final Function f2)
    {
        double result = 0.0;

        result += this.cohesionPGVars(f1, f2);
        result += this.cohesionFGVars(f1, f2);
        result += this.cohesionLocalVars(f1, f2);
        result += this.cohesionTypes(f1, f2);
        result += this.cohesionCalls(f1, f2);
        result += this.cohesionArguments(f1, f2);

        if (f1.getSourceFile().equals(f2.getSourceFile()))
        {
            result *= BONUS_SAME_FILE;
        }

        return result;
    }

    /**
     * Évalue la cohésion entre une fonction et une variable globale.
     * 
     * <p>
     * La cohésion entre une fonction f et une variable globale au programme
     * (resp. au fichier) v est évaluée sur le ratio des accès à v sur le nombre
     * total d'accès à des variables globales au programme (resp. au fichier).
     * Si v à une portée limitée à un fichier, l'appartenance au même fichier
     * est aussi prise en compte.
     * </p>
     */
    private double cohesion(final Function fct, Variable var)
    {
        double result = 0.0;
        double total = 0.0; // Total accesses
        double nbAccessToVar = 0.0;

        Map<ProgramGlobalVariable, Integer> pgVars = fct.getProgramGlobals();
        Map<ProgramGlobalVariable, Integer> fgVars = fct.getProgramGlobals();

        // Program global variable
        if (pgVars.containsKey(var))
        {
            nbAccessToVar = pgVars.get(var);

            for (Integer n : pgVars.values())
            {
                total += n;
            }
        }

        // File global variable
        else if (fgVars.containsKey(var))
        {
            FileGlobalVariable v = (FileGlobalVariable) var;

            nbAccessToVar = fgVars.get(var);

            for (Integer n : fgVars.values())
            {
                total += n;
            }

            // Same file ?
            if (fct.getSourceFile().equals(v.getSourceFile()))
            {
                nbAccessToVar *= BONUS_SAME_FILE;
            }
        }

        result = nbAccessToVar / total;

        return result;
    }

    /**
     * Évalue la cohésion entre une fonction et un type.
     */
    private double cohesion(final Function fct, Type type)
    {
        double result = 0.0;
        double nbUsesOfType = 0.0;
        double total = 0.0; // Total uses of types

        // Types used in the body

        Map<Type, Integer> usedTypes = fct.getUsedTypes();

        for (Type t : usedTypes.keySet())
        {
            if (t.equals(type))
            {
                nbUsesOfType += usedTypes.get(t);
            }

            total += usedTypes.get(t);
        }

        // Arguments

        Set<LocalVariable> args = fct.getArguments();

        for (LocalVariable arg : args)
        {
            if (arg.getType().equals(type))
            {
                ++nbUsesOfType;
            }

            ++total;
        }

        result = nbUsesOfType / total;

        return result;
    }

    /**
     * Méthode appelée pour le calcul de la cohésion entre 2 fonctions.
     * 
     * <p>
     * Calcule le ratio des variables globales au programme accédées en commun
     * sur le nombre total de variables globales au programme accédées dans les
     * deux fonctions.
     * </p>
     * 
     * @see #cohesion(Function, Function)
     */
    private double cohesionPGVars(Function f1, Function f2)
    {
        double result = 0.0;
        double nbPGVars = 0;
        double nbCommon = 0;

        Map<ProgramGlobalVariable, Integer> pgVars1 = f1.getProgramGlobals();
        Map<ProgramGlobalVariable, Integer> pgVars2 = f2.getProgramGlobals();

        for (ProgramGlobalVariable var : pgVars1.keySet())
        {
            if (pgVars2.containsKey(var))
            {
                ++nbCommon;
            }
        }

        nbPGVars = pgVars1.size() + pgVars2.size() - nbCommon;
        
        if(nbPGVars > 0)
        {
            result = nbCommon / nbPGVars;
        }

        return result;
    }

    /**
     * Méthode appelée pour le calcul de la cohésion entre 2 fonctions.
     * 
     * <p>
     * Calcule le ratio des variables globales à un fichier accédées en commun
     * sur le nombre total de variables globales à un fichier accédées dans les
     * deux fonctions.
     * </p>
     * 
     * @see #cohesion(Function, Function)
     */
    private double cohesionFGVars(Function f1, Function f2)
    {
        double result = 0.0;
        double nbFGVars = 0;
        double nbCommon = 0;

        Map<FileGlobalVariable, Integer> fgVars1 = f1.getFileGlobals();
        Map<FileGlobalVariable, Integer> fgVars2 = f2.getFileGlobals();

        for (FileGlobalVariable var : fgVars1.keySet())
        {
            if (fgVars2.containsKey(var))
            {
                ++nbCommon;
            }
        }

        nbFGVars = fgVars1.size() + fgVars2.size() - nbCommon;
        
        if(nbFGVars > 0)
        {
            result = nbCommon / nbFGVars;
        }

        return result;
    }

    /**
     * Méthode appelée pour le calcul de la cohésion entre 2 fonctions.
     * 
     * <p>
     * Calcule le ratio de variables locales similaires entre les deux fonctions
     * sur le nombre total de variables locales utilisées dans les deux
     * fonctions.
     * </p>
     * 
     * @see #cohesion(Function, Function)
     */
    private double cohesionLocalVars(Function f1, Function f2)
    {
        double result = 0.0;
        double nbLocalVars = 0;
        double nbCommon = 0;

        Map<LocalVariable, Integer> localVars1 = f1.getLocals();
        Map<LocalVariable, Integer> localVars2 = f2.getLocals();

        for (LocalVariable var1 : localVars1.keySet())
        {
            for (LocalVariable var2 : localVars2.keySet())
            {
                if ((var1.getName().compareTo(var2.getName()) == 0)
                        && (var1.getType().equals(var2.getType())))
                {
                    ++nbCommon;
                }
            }
        }

        nbLocalVars = localVars1.size() + localVars2.size() - nbCommon;
        
        if(nbLocalVars > 0)
        {
            result = nbCommon / nbLocalVars;
        }

        return result;
    }

    /**
     * Méthode appelée pour le calcul de la cohésion entre 2 fonctions.
     * 
     * <p>
     * Calcule le ratio de types utilisés en commun dans les deux fonctions sur
     * le nombre total de types utilisés dans les deux fonctions.
     * </p>
     * 
     * @see #cohesion(Function, Function)
     */
    private double cohesionTypes(Function f1, Function f2)
    {
        double result = 0.0;
        double nbUsedTypes = 0;
        double nbCommon = 0;

        Map<Type, Integer> usedTypes1 = f1.getUsedTypes();
        Map<Type, Integer> usedTypes2 = f2.getUsedTypes();

        for (Type t : usedTypes1.keySet())
        {
            if (usedTypes2.containsKey(t))
            {
                ++nbCommon;
            }
        }

        nbUsedTypes = usedTypes1.size() + usedTypes2.size() - nbCommon;
        
        if(nbUsedTypes > 0)
        {
            result = nbCommon / nbUsedTypes;
        }

        return result;
    }

    /**
     * Méthode appelée pour le calcul de la cohésion entre 2 fonctions.
     * 
     * <p>
     * Calcule le ratio d'appels de fonction communs dans les deux fonctions sur
     * le nombre total d'appels dans les deux fonctions. Les paramètres des
     * appels ne sont pas pris en compte.
     * </p>
     * 
     * @see #cohesion(Function, Function)
     */
    private double cohesionCalls(Function f1, Function f2)
    {
        double result = 0.0;
        double nbCalls = 0;
        double nbCommon = 0;

        Set<Call> calls1 = f1.getCalls();
        Set<Call> calls2 = f2.getCalls();

        for (Call call : calls1)
        {
            Function fct = call.getFunction();

            Iterator<Call> it = calls2.iterator();
            boolean found = false;

            while (it.hasNext() && found == false)
            {
                Call c = it.next();

                if (c.getFunction().equals(fct))
                {
                    ++nbCommon;
                }
            }
        }

        nbCalls = calls1.size() + calls2.size() - nbCommon;
        
        if(nbCalls > 0)
        {
            result = nbCommon / nbCalls;
        }

        return result;
    }

    /**
     * Méthode appelée pour le calcul de la cohésion entre 2 fonctions.
     * 
     * <p>
     * Calcule le ratio d'arguments similaires entre les deux fonctions sur le
     * nombre total d'arguments des deux fonctions.
     * </p>
     * 
     * @see #cohesion(Function, Function)
     */
    private double cohesionArguments(Function f1, Function f2)
    {
        double result = 0.0;
        double nbArgs = 0;
        double nbCommon = 0;

        Set<LocalVariable> args1 = f1.getArguments();
        Set<LocalVariable> args2 = f2.getArguments();

        for (LocalVariable var1 : args1)
        {
            for (LocalVariable var2 : args2)
            {
                if ((var1.getName().compareTo(var2.getName()) == 0)
                        && (var1.getType().equals(var2.getType())))
                {
                    ++nbCommon;
                }
            }
        }

        nbArgs = args1.size() + args2.size() - nbCommon;
        
        if(nbArgs > 0)
        {
            result = nbCommon / nbArgs;
        }

        return result;
    }
}
