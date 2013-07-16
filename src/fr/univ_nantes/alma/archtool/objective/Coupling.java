package fr.univ_nantes.alma.archtool.objective;

import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Connector;
import fr.univ_nantes.alma.archtool.architectureModel.Facade;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Variable;

public class Coupling
{
    /**
     * Évalue le couplage interne d'un composant.
     * 
     * @param comp
     *            Le composant à évaluer
     * 
     * @see #coupling(Function, Function)
     * @see #coupling(Function, Variable)
     * @see #coupling(Function, ComplexType)
     * @see #coupling(Variable, ComplexType)
     */
    public double componentCoupling(Component comp)
    {
        double result = 0;
        double nbPairs = 0;

        Function[] functions = new Function[comp.getFunctions().size()];
        comp.getFunctions().toArray(functions);

        GlobalVariable[] variables = new GlobalVariable[comp
                .getGlobalVariables().size()];
        comp.getGlobalVariables().toArray(variables);

        ComplexType[] types = new ComplexType[comp.getComplexTypes().size()];
        comp.getComplexTypes().toArray(types);

        if (functions.length > 0)
        {
            for (int idx1 = 0 ; idx1 < functions.length ; ++idx1)
            {
                for (int idx2 = idx1 + 1 ; idx2 < functions.length ; ++idx2)
                {
                    if(coupling(functions[idx1], functions[idx2]) > 0)
                    {
                        result += 0.75;
                    }
                    
                    ++nbPairs;
                }
            }

            for (Variable var : variables)
            {
                for (Function fct : functions)
                {
                    if(coupling(fct, var) > 0)
                    {
                        result += 0.75;
                    }
                    
                    ++nbPairs;
                }
            }

            for (ComplexType type : types)
            {
                for (Function fct : functions)
                {
                    if(coupling(fct, type) > 0)
                    {
                        ++result;
                    }
                    
                    ++nbPairs;
                }
            }
        }

        for (Variable var : variables)
        {
            for (ComplexType type : types)
            {
                if(coupling(var, type) > 0)
                {
                    ++result;
                }
                
                ++nbPairs;
            }
        }

        if (nbPairs > 0)
        {
            result = 100 * result / nbPairs;
        }
        
        else
        {
            result = 100.0;
        }

        return result;
    }

    /**
     * Évalue le couplage interne d'une interface.
     * 
     * @param itf
     *            L'interface à évaluer
     * 
     * @see #coupling(Function, Function)
     * @see #coupling(Function, Variable)
     * @see #coupling(Function, ComplexType)
     * @see #coupling(Variable, ComplexType)
     */
    public double interfaceCoupling(Interface itf)
    {
        double result = 0;
        double nbPairs = 0;

        Function[] functions = new Function[itf.getFunctions().size()];
        itf.getFunctions().toArray(functions);
        
        Set<GlobalVariable> variables = itf.getGlobalVariables();
        Set<ComplexType> types = itf.getComplexTypes();

        if (functions.length > 0)
        {
            for (int idx1 = 0 ; idx1 < functions.length ; ++idx1)
            {
                for (int idx2 = idx1 + 1 ; idx2 < functions.length ; ++idx2)
                {
                    if(coupling(functions[idx1], functions[idx2]) > 0)
                    {
                        result += 0.75;
                    }
                    
                    ++nbPairs;
                }
            }

            for (Variable var : variables)
            {
                for (Function fct : functions)
                {
                    if(coupling(fct, var) > 0)
                    {
                        result += 0.75;
                    }
                    
                    ++nbPairs;
                }
            }

            for (ComplexType type : types)
            {
                for (Function fct : functions)
                {
                    if(coupling(fct, type) > 0)
                    {
                        ++result;
                    }
                    
                    ++nbPairs;
                }
            }
        }

        for (Variable var : variables)
        {
            for (ComplexType type : types)
            {
                if(coupling(var, type) > 0)
                {
                    ++result;
                }
                
                ++nbPairs;
            }
        }

        if (nbPairs > 0)
        {
            result = 100 * result / nbPairs;
        }
        
        else
        {
            result = 100.0;
        }

        return result;
    }

    /**
     * Évalue le couplage interne d'un connecteur.
     * 
     * @param con
     *            Le connecteur à évaluer
     * 
     * @see #coupling(Function, Function)
     * @see #coupling(Function, Variable)
     * @see #coupling(Function, ComplexType)
     * @see #coupling(Variable, ComplexType)
     */
    public double connectorCoupling(Connector con)
    {
        double result = 0;
        double nbPairs = 0;

        Function[] functions = new Function[con.getFunctions().size()];
        con.getFunctions().toArray(functions);
        
        Set<GlobalVariable> variables = con.getGlobalVariables();
        Set<ComplexType> types = con.getComplexTypes();

        if (functions.length > 0)
        {
            for (int idx1 = 0 ; idx1 < functions.length ; ++idx1)
            {
                for (int idx2 = idx1 + 1 ; idx2 < functions.length ; ++idx2)
                {
                    if(coupling(functions[idx1], functions[idx2]) > 0)
                    {
                        result += 0.75;
                    }
                    
                    ++nbPairs;
                }
            }

            for (Variable var : variables)
            {
                for (Function fct : functions)
                {
                    if(coupling(fct, var) > 0)
                    {
                        result += 0.75;
                    }
                    
                    ++nbPairs;
                }
            }

            for (ComplexType type : types)
            {
                for (Function fct : functions)
                {
                    if(coupling(fct, type) > 0)
                    {
                        ++result;
                    }
                    
                    ++nbPairs;
                }
            }
        }

        for (Variable var : variables)
        {
            for (ComplexType type : types)
            {
                if(coupling(var, type) > 0)
                {
                    ++result;
                }
                
                ++nbPairs;
            }
        }

        if (nbPairs > 0)
        {
            result = 100 * result / nbPairs;
        }
        
        else
        {
            result = 100.0;
        }

        return result;
    }

    /**
     * Évalue le couplage interne d'une facade.
     * 
     * @param fcd
     *            La facade à évaluer
     * 
     * @see #coupling(Function, Function)
     * @see #coupling(Function, Variable)
     * @see #coupling(Function, ComplexType)
     * @see #coupling(Variable, ComplexType)
     */
    public double facadeCoupling(Facade fcd)
    {
        double result = 0;
        double nbPairs = 0;

        Function[] functions = new Function[fcd.getFunctions().size()];
        fcd.getFunctions().toArray(functions);
        
        Set<GlobalVariable> variables = fcd.getGlobalVariables();
        Set<ComplexType> types = fcd.getComplexTypes();

        if (functions.length > 0)
        {
            for (int idx1 = 0 ; idx1 < functions.length ; ++idx1)
            {
                for (int idx2 = idx1 + 1 ; idx2 < functions.length ; ++idx2)
                {
                    if(coupling(functions[idx1], functions[idx2]) > 0)
                    {
                        result += 0.75;
                    }
                    
                    ++nbPairs;
                }
            }

            for (Variable var : variables)
            {
                for (Function fct : functions)
                {
                    if(coupling(fct, var) > 0)
                    {
                        result += 0.75;
                    }
                    
                    ++nbPairs;
                }
            }

            for (ComplexType type : types)
            {
                for (Function fct : functions)
                {
                    if(coupling(fct, type) > 0)
                    {
                        ++result;
                    }
                    
                    ++nbPairs;
                }
            }
        }

        for (Variable var : variables)
        {
            for (ComplexType type : types)
            {
                if(coupling(var, type) > 0)
                {
                    ++result;
                }
                
                ++nbPairs;
            }
        }

        if (nbPairs > 0)
        {
            result = 100 * result / nbPairs;
        }
        
        else
        {
            result = 100.0;
        }

        return result;
    }

    /**
     * Évalue le couplage entre 2 facades.
     * 
     * @param itf
     *            La facade à évaluer
     * 
     * @see #coupling(Function, Function)
     * @see #coupling(Function, Variable)
     * @see #coupling(Function, ComplexType)
     * @see #coupling(Variable, ComplexType)
     */
    public double facadesCoupling(Facade fcd1, Facade fcd2)
    {
        double result = 0;
        double nbPairs = 0;
        
        Set<Function> fcd1Fcts = fcd1.getFunctions();
        Set<GlobalVariable> fcd1Vars = fcd1.getGlobalVariables();
        Set<ComplexType> fcd1Types = fcd1.getComplexTypes();

        Set<Function> fcd2Fcts = fcd2.getFunctions();
        Set<GlobalVariable> fcd2Vars = fcd2.getGlobalVariables();
        Set<ComplexType> fcd2Types = fcd2.getComplexTypes();

        if (fcd1Fcts.size() > 0)
        {
            for (Function fct1 : fcd1Fcts)
            {
                for (Function fct2 : fcd2Fcts)
                {
                    if(coupling(fct1, fct2) > 0)
                    {
                        result += 0.75;
                    }
                    
                    ++nbPairs;
                }
            }

            for (Function fct1 : fcd1Fcts)
            {
                for (GlobalVariable var2 : fcd2Vars)
                {
                    if(coupling(fct1, var2) > 0)
                    {
                        result += 0.75;
                    }
                    
                    ++nbPairs;
                }
            }

            for (Function fct1 : fcd1Fcts)
            {
                for (ComplexType type2 : fcd2Types)
                {
                    if(coupling(fct1, type2) > 0)
                    {
                        ++result;
                    }
                    
                    ++nbPairs;
                }
            }
        }

        for (GlobalVariable var1 : fcd1Vars)
        {
            for (ComplexType type2 : fcd2Types)
            {
                if(coupling(var1, type2) > 0)
                {
                    ++result;
                }
                
                ++nbPairs;
            }
        }

        if (fcd2Fcts.size() > 0)
        {
            for (Function fct2 : fcd2Fcts)
            {
                for (GlobalVariable var1 : fcd1Vars)
                {
                    if(coupling(fct2, var1) > 0)
                    {
                        result += 0.75;
                    }
                    
                    ++nbPairs;
                }
            }

            for (Function fct2 : fcd1Fcts)
            {
                for (ComplexType type1 : fcd1Types)
                {
                    if(coupling(fct2, type1) > 0)
                    {
                        ++result;
                    }
                    
                    ++nbPairs;
                }
            }
        }

        for (GlobalVariable var2 : fcd2Vars)
        {
            for (ComplexType type1 : fcd1Types)
            {
                if(coupling(var2, type1) > 0)
                {
                    ++result;
                }
                
                ++nbPairs;
            }
        }

        if (nbPairs > 0)
        {
            result = 100 * result / nbPairs;
        }
        
        else
        {
            result = 100.0;
        }

        return result;
    }

    /**
     * Mesure le couplage d'une fonction par rapport à une autre.
     * 
     * <p>
     * Le couplage d'une fonction <em>f1</em> par rapport à une autre fonction
     * <em>f2</em> est égal au nombre d'appels à <em>f2</em> par <em>f1</em> et
     * inversement.
     * </p>
     * 
     * @param f1
     *            Une fonction d'un modèle de code source
     * @param f2
     *            Une fonction d'un modèle de code source
     */
    private int coupling(Function f1, Function f2)
    {
        int result = 0;

        if ((f1 != null) && (f2 != null) && (f1.equals(f2) == false))
        {
            Map<Function, Integer> calledF1 = f1.getCoreCalledFunctions();
            Map<Function, Integer> calledF2 = f2.getCoreCalledFunctions();

            if (calledF1.containsKey(f2) == true)
            {
                result += calledF1.get(f2);
            }

            if (calledF2.containsKey(f1) == true)
            {
                result += calledF2.get(f1);
            }
        }

        return result;
    }

    /**
     * Mesure le couplage entre une fonction et une variable globale.
     * 
     * <p>
     * Le couplage entre une fonction <em>f</em> et une variable <em>v</em> est
     * égal au nombre d'accès à <em>v</em> par <em>f</em>.
     * </p>
     * 
     * @param fct1
     *            Une fonction d'un modèle de code source
     * @param var
     *            Une variable globlale d'un modèle de code source
     */
    private int coupling(Function fct, Variable var)
    {
        int result = 0;

        if (fct != null && var != null)
        {
            Map<GlobalVariable, Integer> globalVarsFct = fct
                    .getCoreGlobalVariables();

            if (globalVarsFct.containsKey(var))
            {
                result = globalVarsFct.get(var);
            }
        }

        return result;
    }

    /**
     * Mesure le couplage entre une fonction et un type.
     * 
     * <p>
     * Le couplage entre une fonction <em>f</em> et un type <em>t</em> est égal
     * au nombre d'utilisations de <em>t</em> par <em>f</em>.
     * </p>
     * 
     * @param fct
     *            Une fonction d'un modèle de code source
     * @param type
     *            Un type d'un modèle de code source
     */
    private int coupling(Function fct, ComplexType type)
    {
        int result = 0;

        Map<ComplexType, Integer> usedTypes = fct.getTotalComplexTypes();

        if (usedTypes.containsKey(type))
        {
            result += usedTypes.get(type);
        }

        return result;
    }

    /**
     * Mesure le couplage entre une variable et un type.
     */
    private int coupling(Variable var, ComplexType type)
    {
        return var.ofType(type) ? 1 : 0;
    }
}
