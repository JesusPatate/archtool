package fr.univ_nantes.alma.archtool.objective;

import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Connector;
import fr.univ_nantes.alma.archtool.architectureModel.Interface;
import fr.univ_nantes.alma.archtool.coa.COA;
import fr.univ_nantes.alma.archtool.sourceModel.Call;
import fr.univ_nantes.alma.archtool.sourceModel.FileGlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.LocalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.ProgramGlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Type;
import fr.univ_nantes.alma.archtool.sourceModel.Variable;

public class Coupling
{
    private final COA coa;
    
    public Coupling(COA coa)
    {
        this.coa = coa;
    }
    
    /**
     * Évalue le couplage interne d'un composant.
     * 
     * @param comp
     *            Le composant à évaluer
     * 
     * @see #coupling(Function, Function)
     * @see #coupling(Function, Variable)
     * @see #coupling(Function, Type)
     * @see #coupling(Variable, Type)
     */
    public int componentCoupling(Component comp)
    {
        int result = 0;

        Set<Function> functions = this.coa.getComponentFunctions(comp);
        Set<Variable> variables = this.coa.getComponentVariables(comp);
        Set<Type> types = this.coa.getComponentTypes(comp);

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

            for (Type type : types)
            {
                for (Function fct : functions)
                {
                    result += coupling(fct, type);
                }
            }
        }

        for (Variable var : variables)
        {
            for (Type type : types)
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
     * @see #coupling(Function, Type)
     * @see #coupling(Variable, Type)
     */
    public int interfaceCoupling(Interface itf)
    {
        int result = 0;

        Set<Function> functions = this.coa.getInterfaceFunctions(itf);
        Set<Variable> variables = this.coa.getInterfaceVariables(itf);
        Set<Type> types = this.coa.getInterfaceTypes(itf);

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

            for (Type type : types)
            {
                for (Function fct : functions)
                {
                    result += coupling(fct, type);
                }
            }
        }

        for (Variable var : variables)
        {
            for (Type type : types)
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
     * @see #coupling(Function, Type)
     * @see #coupling(Variable, Type)
     */
    public int connectorCoupling(Connector con)
    {
        int result = 0;

        Set<Function> functions = this.coa.getConnectorFunctions(con);
        Set<Variable> variables = this.coa.getConnectorVariables(con);
        Set<Type> types = this.coa.getConnectorTypes(con);

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

            for (Type type : types)
            {
                for (Function fct : functions)
                {
                    result += coupling(fct, type);
                }
            }
        }

        for (Variable var : variables)
        {
            for (Type type : types)
            {
                result += coupling(var, type);
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
            Set<Call> calls = f1.getCalls();
            Function fctCalled = null;

            for (Call call1 : calls)
            {
                fctCalled = call1.getFunction();

                if (fctCalled.equals(f2))
                {
                    ++result;
                }
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
            Map<ProgramGlobalVariable, Integer> pgVarsFct = fct
                    .getProgramGlobals();
            Map<FileGlobalVariable, Integer> fgVarsFct = fct.getFileGlobals();

            if (pgVarsFct.containsKey(var))
            {
                result = pgVarsFct.get(var);
            }

            else if (fgVarsFct.containsKey(var))
            {
                result = fgVarsFct.get(var);
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
    private int coupling(Function fct, Type type)
    {
        int result = 0;

        // Arguments types

        Set<LocalVariable> locals = fct.getArguments();

        for (LocalVariable var : locals)
        {
            if (var.ofType(type))
            {
                ++result;
            }
        }

        // Types used in the body

        Map<Type, Integer> usedTypes = fct.getUsedTypes();

        if (usedTypes.containsKey(type))
        {
            result += usedTypes.get(type);
        }

        return result;
    }

    /**
     * Mesure le couplage entre deux fonctions.
     * 
     * @param fct1
     * @param fct2
     * @return
     */
    private int coupling(Variable var, Type type)
    {
        int result = 0;

        if (var.ofType(type))
        {
            ++result;
        }

        return result;
    }
}
