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
    public int componentCoupling(Component comp)
    {
        int result = 0;

        Set<Function> functions = comp.getFunctions();
        Set<GlobalVariable> variables = comp.getGlobalVariables();
        Set<ComplexType> types = comp.getComplexTypes();

        if (functions.size() > 0)
        {
            for (Function fct1 : functions)
            {
                for (Function fct2 : functions)
                {
                    result += coupling(fct1, fct2);
                }
            }

            for (Variable var : variables)
            {
                for (Function fct : functions)
                {
                    result += coupling(fct, var);
                }
            }

            for (ComplexType type : types)
            {
                for (Function fct : functions)
                {
                    result += coupling(fct, type);
                }
            }
        }

        for (Variable var : variables)
        {
            for (ComplexType type : types)
            {
                result += coupling(var, type);
            }
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
    public int interfaceCoupling(Interface itf)
    {
        int result = 0;

        Set<Function> functions = itf.getFunctions();
        Set<GlobalVariable> variables = itf.getGlobalVariables();
        Set<ComplexType> types = itf.getComplexTypes();

        if (functions.size() > 0)
        {
            for (Function fct1 : functions)
            {
                for (Function fct2 : functions)
                {
                    result += coupling(fct1, fct2);
                }
            }

            for (Variable var : variables)
            {
                for (Function fct : functions)
                {
                    result += coupling(fct, var);
                }
            }

            for (ComplexType type : types)
            {
                for (Function fct : functions)
                {
                    result += coupling(fct, type);
                }
            }
        }

        for (Variable var : variables)
        {
            for (ComplexType type : types)
            {
                result += coupling(var, type);
            }
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
    public int connectorCoupling(Connector con)
    {
        int result = 0;

        Set<Function> functions = con.getFunctions();
        Set<GlobalVariable> variables = con.getGlobalVariables();
        Set<ComplexType> types = con.getComplexTypes();

        if (functions.size() > 0)
        {
            for (Function fct1 : functions)
            {
                for (Function fct2 : functions)
                {
                    result += coupling(fct1, fct2);
                }
            }

            for (Variable var : variables)
            {
                for (Function fct : functions)
                {
                    result += coupling(fct, var);
                }
            }

            for (ComplexType type : types)
            {
                for (Function fct : functions)
                {
                    result += coupling(fct, type);
                }
            }
        }

        for (Variable var : variables)
        {
            for (ComplexType type : types)
            {
                result += coupling(var, type);
            }
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
    public int facadeCoupling(Facade fcd)
    {
        int result = 0;

        Set<Function> functions = fcd.getFunctions();
        Set<GlobalVariable> variables = fcd.getGlobalVariables();
        Set<ComplexType> types = fcd.getComplexTypes();

        if (functions.size() > 0)
        {
            for (Function fct1 : functions)
            {
                for (Function fct2 : functions)
                {
                    result += coupling(fct1, fct2);
                }
            }

            for (Variable var : variables)
            {
                for (Function fct : functions)
                {
                    result += coupling(fct, var);
                }
            }

            for (ComplexType type : types)
            {
                for (Function fct : functions)
                {
                    result += coupling(fct, type);
                }
            }
        }

        for (Variable var : variables)
        {
            for (ComplexType type : types)
            {
                result += coupling(var, type);
            }
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
    public int facadesCoupling(Facade fcd1, Facade fcd2)
    {
        int result = 0;

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
                    result += coupling(fct1, fct2);
                }
            }
            
            for (Function fct1 : fcd1Fcts)
            {
                for (GlobalVariable var2 : fcd2Vars)
                {
                    result += coupling(fct1, var2);
                }
            }
            
            for (Function fct1 : fcd1Fcts)
            {
                for (ComplexType type2 : fcd2Types)
                {
                    result += coupling(fct1, type2);
                }
            }
        }
        
        for (GlobalVariable var1 : fcd1Vars)
        {
            for (ComplexType type2 : fcd2Types)
            {
                result += coupling(var1, type2);
            }
        }
        
        if (fcd2Fcts.size() > 0)
        {
            for (Function fct2 : fcd2Fcts)
            {
                for (GlobalVariable var1 : fcd1Vars)
                {
                    result += coupling(fct2, var1);
                }
            }
            
            for (Function fct2 : fcd1Fcts)
            {
                for (ComplexType type1 : fcd1Types)
                {
                    result += coupling(fct2, type1);
                }
            }
        }
        
        for (GlobalVariable var2 : fcd2Vars)
        {
            for (ComplexType type1 : fcd1Types)
            {
                result += coupling(var2, type1);
            }
        }
        
        return result;
    }

    /**
     * Mesure le couplage d'une fonction par rapport à une autre.
     * 
     * <p>
     * Le couplage d'une fonction <em>f1</em> par rapport à une autre fonction
     * <em>f2</em> est égal au nombre d'appels à <em>f2</em> par <em>f1</em>.
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
            
            if(calledF1.containsKey(f2) == true)
            {
                result += calledF1.get(f2);
            }
            
            if(calledF2.containsKey(f1) == true)
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
