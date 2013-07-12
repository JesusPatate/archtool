package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.Set;

import fr.univ_nantes.alma.archtool.coa.COA;
import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;

public class Interface
{
    private COA coa = null;

    public void setCOA(COA coa)
    {
        this.coa = coa;
    }    
    
    public Set<Function> getFunctions()
    {
        return this.coa.getInterfaceFunctions(this);
    }
    
    public Set<GlobalVariable> getGlobalVariables()
    {
        return this.coa.getInterfaceVariables(this);
    }
    
    public Set<ComplexType> getComplexTypes()
    {
        return this.coa.getInterfaceTypes(this);
    }
    
    public boolean addFunction(Function fct)
    {
        return this.coa.addFunction(fct, this);
    }
    
    public void addFunctions(Set<Function> fcts)
    {
       this.coa.addFunctions(fcts, this); 
    }
    
    public boolean addVariable(GlobalVariable var)
    {
        return this.coa.addVariable(var, this);
    }
    
    public void addVariables(Set<GlobalVariable> vars)
    {
       this.coa.addVariables(vars, this);
    }
    
    public boolean addType(ComplexType t)
    {
        return this.coa.addType(t, this);
    }
    
    public void addTypes(Set<ComplexType> types)
    {
       this.coa.addTypes(types, this);
    }
    
    public boolean removeFunction(Function fct)
    {
        return this.coa.removeFunction(fct, this);
    }
    
    public boolean removeVariable(GlobalVariable var)
    {
        return this.coa.removeVariable(var, this);
    }
    
    public boolean removeType(ComplexType t)
    {
        return this.coa.removeType(t, this);
                
    }
}
