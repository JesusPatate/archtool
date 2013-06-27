package fr.univ_nantes.alma.archtool.objective;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.sourceModel.Call;
import fr.univ_nantes.alma.archtool.sourceModel.FileGlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.LocalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.ProgramGlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Type;
import fr.univ_nantes.alma.archtool.sourceModel.Variable;

public class ObjectiveFunction
{
    /**
     * Poids de l'autonomie des composants
     */
    static final double WEIGHT_INDE_COMP = 1.0;

    /**
     * Poids de la spécificité des composants
     */
    static final double WEIGHT_SPECI_COMP = 1.0;

    /**
     * Poids de la composabilité des composants
     */
    static final double WEIGHT_COMPO_COMP = 1.0;

    /**
     * Bonus accordé pour la mesure de la cohésion lorsque les deux éléments
     * appartiennent au même fichier.
     */
    static final double BONUS_SAME_FILE = 0.5;

    /**
     * Applique la fonction objectif à un composant.
     * 
     * @param component
     *            Le composant à évaluer
     * 
     * @return Le résultat de la fonction objectif
     */
    public double evaluate(final Component component)
    {
        return this.semantic(component);
    }

    /**
     * Évalue la validité sémantique d'un composant.
     * 
     * @param comp
     *            Le composant à évaluer
     */
    private double semantic(final Component comp)
    {
        double result = 0.0;

        result = ObjectiveFunction.WEIGHT_COMPO_COMP * this.composability(comp);
        result += ObjectiveFunction.WEIGHT_INDE_COMP * this.independence(comp);
        result += ObjectiveFunction.WEIGHT_SPECI_COMP * this.specificity(comp);

        result /= ObjectiveFunction.WEIGHT_COMPO_COMP
                + ObjectiveFunction.WEIGHT_INDE_COMP
                + ObjectiveFunction.WEIGHT_SPECI_COMP;

        return result;
    }

    /**
     * Évalue la composabilité d'un composant.
     * 
     * La composabilité d'un composant est évaluée sur le nombre d'interfaces
     * qu'il requiert et sur la cohésion interne moyenne de ses interfaces
     * fournies.
     * 
     * @param comp
     *            Le composant à évaluer
     */
    private double composability(final Component comp)
    {
        double result = 0.0;

        final double nbReqInterfaces = comp.getRequiredInterfaces().size();
        final double nbProInterfaces = comp.getProvidedInterfaces().size();

        double sumCohesion = 0.0;
        double avgCohesion = 0.0;

        for (final Interface itf : comp.getProvidedInterfaces())
        {
            sumCohesion += this.internalCohesion(itf);
        }

        avgCohesion = sumCohesion / nbProInterfaces;

        result = (avgCohesion / nbReqInterfaces);

        return result;
    }

    /**
     * Évalue l'autonomie d'un composant.
     * 
     * <p>
     * L'autonomie d'un composant est évaluée sur le nombre d'interface que
     * celui-ci requiert.
     * </p>
     * 
     * @param comp
     *            Le composant à évaluer
     */
    private double independence(final Component comp)
    {
        double result = 0.0;

        return result;
    }

    /**
     * Évalue la spécificité d'un composant.
     * 
     * @param comp
     *            Le composant à évaluer
     */
    private double specificity(final Component comp)
    {
        double result = 0.0;

        return result;
    }

    /**
     * Évalue la cohésion interne d'une interface.
     * 
     * @param itf
     *            L'interface à évaluer
     */
    private double internalCohesion(final Interface itf)
    {
        double result = 0.0;

        int nbFcts = itf.getFunctions().size();
        int nbVars = itf.getVariables().size();
        int nbTypes = itf.getTypes().size();

        if (nbFcts > 0)
        {
            if (nbFcts > 1)
            {
                result += internalCohesionFct(itf);
            }

            if (nbVars > 1)
            {
                result += internalCohesionFctVar(itf);
            }

            if (nbTypes > 1)
            {
                result += internalCohesionFctType(itf);
            }
        }

        return result;
    }

    /**
     * Évalue la cohésion des fonctions d'une interface.
     * 
     * <p>
     * La cohésion interne d'une interface est évaluée sur la cohésion moyenne
     * de ses services.
     * </p>
     * 
     * @param itf
     *            L'interface à évaluer
     */
    private double internalCohesionFct(final Interface itf)
    {
        double result = 0.0;
        double sum = 0.0;

        final Object[] functions = itf.getFunctions().toArray();

        for (int idx1 = 0; idx1 < (functions.length - 1); ++idx1)
        {
            for (final int idx2 = idx1 + 1; idx2 < functions.length; ++idx1)
            {
                final Function f1 = (Function) functions[idx1];
                final Function f2 = (Function) functions[idx2];

                sum += this.cohesion(f1, f2);
            }
        }

        result = sum / functions.length;

        return result;
    }

    private double internalCohesionFctVar(Interface itf)
    {
        double result = 0.0;
        double sum = 0.0;

        for (Function fct : itf.getFunctions())
        {
            for (Variable var : itf.getVariables())
            {
                sum += this.cohesion(fct, var);
            }
        }

        return 0;
    }

    private double internalCohesionFctType(Interface itf)
    {
        double result = 0.0;
        double sum = 0.0;
        
        for (Function fct : itf.getFunctions())
        {
            for (Type type : itf.getTypes())
            {
                sum += this.cohesion(fct, type);
            }
        }

        double nbPairs = itf.getFunctions().size() * itf.getTypes().size();
        
        result = sum / nbPairs;

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
     * et sur l'appartenance à un même fichier
     * </p>
     * 
     * @param f1
     *            La première fonction à évaluer
     * @param f2
     *            La seconde fonction à évaluer
     */
    private double cohesion(final Function f1, final Function f2)
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
            result += BONUS_SAME_FILE;
        }

        return result;
    }

    private double cohesion(final Function fct, ProgramGlobalVariable var)
    {
        Map<ProgramGlobalVariable, Integer> pgVars = fct.getProgramGlobals();

        double nbAccessToVar = pgVars.get(var);
        double sum = 0.0;

        for (Integer n : pgVars.values())
        {
            sum += n;
        }

        return nbAccessToVar / sum;
    }

    private double cohesion(final Function fct, FileGlobalVariable var)
    {
        Map<FileGlobalVariable, Integer> fgVars = fct.getFileGlobals();

        double nbAccessToVar = fgVars.get(var);
        double sum = 0.0;

        for (Integer n : fgVars.values())
        {
            sum += n;
        }

        return nbAccessToVar / sum;
    }

    private double cohesion(final Function fct, Type type)
    {
        // Types used in the body

        Map<Type, Integer> usedTypes = fct.getUsedTypes();

        double nbUsesOfType = usedTypes.get(type);
        double sum = 0.0;

        for (Entry<Type, Integer> e : usedTypes.entrySet())
        {
            Type t = e.getKey();
            Integer n = e.getValue();

            if (type.equals(t))
            {
                nbUsesOfType += n;
            }

            sum += n;
        }

        // Arguments

        Set<LocalVariable> args = fct.getArguments();

        for (LocalVariable arg : args)
        {
            if (arg.getType().equals(type))
            {
                ++nbUsesOfType;
            }

            ++sum;
        }

        return nbUsesOfType / sum;
    }

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
        result = nbCommon / nbPGVars;

        return result;
    }

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
        result = nbCommon / nbFGVars;

        return result;
    }

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
        result = nbCommon / nbLocalVars;

        return result;
    }

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
        result = nbCommon / nbUsedTypes;

        return result;
    }

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
        result = nbCommon / nbCalls;

        return result;
    }

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
        result = nbCommon / nbArgs;

        return result;
    }
}
