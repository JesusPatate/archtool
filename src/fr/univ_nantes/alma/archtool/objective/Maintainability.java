package fr.univ_nantes.alma.archtool.objective;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.architectureModel.Component;
import fr.univ_nantes.alma.archtool.architectureModel.Configuration;
import fr.univ_nantes.alma.archtool.architectureModel.Connection;
import fr.univ_nantes.alma.archtool.architectureModel.Connector;
import fr.univ_nantes.alma.archtool.coa.COA;
import fr.univ_nantes.alma.archtool.sourceModel.Call;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;
import fr.univ_nantes.alma.archtool.sourceModel.Type;

public class Maintainability
{
    private Map<Function, Set<Object>> functionNodes;

    private Map<GlobalVariable, Set<Object>> variableNodes;

    private Map<Type, Set<Object>> typeNodes;

    private Map<Component, Set<Object>> componentNodes;

    private Map<Integer, Integer> degreesMap;

    private COA coa;
    
    public Maintainability(COA coa)
    {
        this.coa = coa;
    }
    
    public double process(Component comp, COA coa)
    {
        double result = 0.0;
        
        this.functionNodes = new HashMap<Function, Set<Object>>();
        this.variableNodes = new HashMap<GlobalVariable, Set<Object>>();
        this.typeNodes = new HashMap<Type, Set<Object>>();
        this.degreesMap = new HashMap<Integer, Integer>();
        
        this.computeDegrees(comp, coa);
        this.fillDegreesMap();
        
        int solution = this.resolveEquation();
        double nbLower = 0.0;
        double total = 0.0;
        
        for(Integer n : this.degreesMap.keySet())
        {
            int value = this.degreesMap.get(n);
            
            if(n < solution)
            {
                nbLower += value;
            }
            
            total += value;
        }
        
        if(total > 0)
        {
            result = nbLower / total;
        }
        else
        {
            result = 1.0;
        }
        
        return result;
    }

    public double process(Connector con, COA coa)
    {
        double result = 0.0;

        this.functionNodes = new HashMap<Function, Set<Object>>();
        this.variableNodes = new HashMap<GlobalVariable, Set<Object>>();
        this.typeNodes = new HashMap<Type, Set<Object>>();
        this.degreesMap = new HashMap<Integer, Integer>();
        
        this.computeDegrees(con, coa);
        this.fillDegreesMap();
        
        int solution = this.resolveEquation();
        double nbLower = 0.0;
        double total = 0.0;
        
        for(Integer n : this.degreesMap.keySet())
        {
            int value = this.degreesMap.get(n);
            
            if(n < solution)
            {
                nbLower += value;
            }
            
            total += value;
        }
        
        if(total > 0)
        {
            result = nbLower / total;
        }
        else
        {
            result = 1.0;
        }
        
        return result;
    }

    public double process(Configuration conf)
    {
        double result = 0.0;
        
        this.componentNodes = new HashMap<Component, Set<Object>>();
        this.degreesMap = new HashMap<Integer, Integer>();
        
        this.computeDegrees(conf);
        
        int solution = this.resolveEquation();
        double nbLower = 0.0;
        double total = 0.0;
        
        for(Integer n : this.degreesMap.keySet())
        {
            int value = this.degreesMap.get(n);
            
            if(n < solution)
            {
                nbLower += value;
            }
            
            total += value;
        }
        
        if(total > 0)
        {
            result = nbLower / total;
        }
        else
        {
            result = 1.0;
        }
        
        return result;
    }

    private void computeDegrees(Component comp, COA coa)
    {
        Set<Function> fcts = coa.getComponentFunctions(comp);
        Set<GlobalVariable> vars = coa.getComponentVariables(comp);

        for (Function fct : fcts)
        {
            this.computeDegreesFct(fct);
        }

        for (GlobalVariable var : vars)
        {
            this.computeDegreesVar(var);
        }
    }

    private void computeDegrees(Connector con, COA coa)
    {
        Set<Function> fcts = coa.getConnectorFunctions(con);
        Set<GlobalVariable> vars = coa.getConnectorVariables(con);

        for (Function fct : fcts)
        {
            this.computeDegreesFct(fct);
        }

        for (GlobalVariable var : vars)
        {
            this.computeDegreesVar(var);
        }
    }

    private void computeDegrees(Configuration conf)
    {
        for(Component comp : conf.getComponents())
        {
            if (componentNodes.containsKey(comp) ==  false)
            {
               this.componentNodes.put(comp, new HashSet<Object>());
            }
            
            Set<Connection> compConnections = conf.getConnections(comp);
            
            for(Connection cComp : compConnections)
            {
                Connector con = cComp.getConnector();
                Set<Connection> conConnections = conf.getConnections(con);
                
                for(Connection cCon : conConnections)
                {
                    Component comp2 = cCon.getComponent();
                    
                    if(comp2.equals(comp) == false)
                    {
                        this.componentNodes.get(comp).add(comp2);
                        
                        if (componentNodes.containsKey(comp2))
                        {
                            this.componentNodes.get(comp2).add(comp);
                        }
                        else
                        {
                            Set<Object> edges2 = new HashSet<Object>();
                            edges2.add(comp);
                            componentNodes.put(comp2, edges2);
                        }
                    }
                }
            }
        }
        
        for (Component comp : this.componentNodes.keySet())
        {
            int degree = this.componentNodes.get(comp).size();

            int value = (this.degreesMap.containsKey(degree)) ?
                    this.degreesMap.get(degree) : 0;

            this.degreesMap.put(degree, value + 1);
        }
    }

    /**
     * Calcule les dépendances d'une fonction.
     * 
     * <p>
     * Méthode appelée pour le calcul du graphe de dépendances.
     * </p>
     * 
     * @param fct
     */
    private void computeDegreesFct(Function fct)
    {
        if (functionNodes.containsKey(fct) == false)
        {
            this.functionNodes.put(fct, new HashSet<Object>());
        }

        for (Call call : fct.getTotalCalls())
        {
            Function callee = call.getFunction();
            
            this.functionNodes.get(fct).add(callee);

            if (functionNodes.containsKey(callee))
            {
                this.functionNodes.get(callee).add(fct);
            }
            else
            {
                Set<Object> edges2 = new HashSet<Object>();
                edges2.add(fct);
                functionNodes.put(callee, edges2);
            }
        }

        for (GlobalVariable var : fct.getCoreGlobalVariables().keySet())
        {
            this.functionNodes.get(fct).add(var);

            if (functionNodes.containsKey(var))
            {
                this.functionNodes.get(var).add(fct);
            }
            else
            {
                Set<Object> edges2 = new HashSet<Object>();
                edges2.add(fct);
                variableNodes.put(var, edges2);
            }
        }

        for (Type type : fct.getTotalComplexTypes().keySet())
        {
            this.functionNodes.get(fct).add(type);

            if (typeNodes.containsKey(type))
            {
                this.typeNodes.get(type).add(fct);
            }
            else
            {
                Set<Object> edges2 = new HashSet<Object>();
                edges2.add(fct);
                typeNodes.put(type, edges2);
            }
        }
    }

    /**
     * Calcule les dépendances d'une variable.
     * 
     * <p>
     * Méthode appelée pour le calcul du graphe de dépendances.
     * </p>
     * 
     * @param fct
     */
    private void computeDegreesVar(GlobalVariable var)
    {
        if (variableNodes.containsKey(var) == false)
        {
            variableNodes.put(var, new HashSet<Object>());
        }

        Type type = var.getType();
        
        this.variableNodes.get(var).add(type);

        if (typeNodes.containsKey(type))
        {
            this.typeNodes.get(type).add(var);
        }
        else
        {
            Set<Object> edges2 = new HashSet<Object>();
            edges2.add(var);
            typeNodes.put(type, edges2);
        }
    }

    private void fillDegreesMap()
    {
        for (Function fct : this.functionNodes.keySet())
        {
            int degree = this.functionNodes.get(fct).size();

            int value = (this.degreesMap.containsKey(degree)) ?
                    this.degreesMap.get(degree) : 0;

            this.degreesMap.put(degree, value + 1);
        }

        for (GlobalVariable var : this.variableNodes.keySet())
        {
            int degree = this.variableNodes.get(var).size();

            int value = (this.degreesMap.containsKey(degree)) ?
                    this.degreesMap.get(degree) : 0;

            this.degreesMap.put(degree, value + 1);
        }

        for (Type type : this.typeNodes.keySet())
        {
            int degree = this.typeNodes.get(type).size();

            int value = (this.degreesMap.containsKey(degree)) ?
                    this.degreesMap.get(degree) : 0;

            this.degreesMap.put(degree, value + 1);
        }
    }
    
    /**
     * Résout l'équation de changement de phase.
     */
    private int resolveEquation()
    {
        int result = 0;
        boolean stop = false;
        
        int maxDegree = 0;
        
        for(Integer degree : this.degreesMap.keySet())
        {
            if(degree > maxDegree)
            {
                maxDegree = degree;
            }
        }
        
        while(stop == false && result <= maxDegree)
        {
            int leftSide = 0;
            int rightSide = 0;
            
            for(int n = 1 ; n <= result ; ++n)
            {
                if(this.degreesMap.containsKey(n))
                {
                    leftSide += n * this.degreesMap.get(n); 
                }
            }
            
            for(int n = result ; n <= maxDegree ; ++n)
            {
                if(this.degreesMap.containsKey(n))
                {
                    rightSide += n * this.degreesMap.get(n); 
                }
            }
            
            if(leftSide >= rightSide)
            {
                stop = true;
            }
            else
            {
                ++result;
            }
        }
        
        return result;
    }
}
