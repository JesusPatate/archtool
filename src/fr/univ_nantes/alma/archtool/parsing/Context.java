package fr.univ_nantes.alma.archtool.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.GlobalVariable;

public class Context 
{
	private Map<String, Function> functions = new HashMap<String, Function>();
	private Map<String, ComplexType> complexTypes =
			new HashMap<String, ComplexType>();
	private Map<String, GlobalVariable> globalVariables = 
			new HashMap<String, GlobalVariable>();
	
	public Context(Set<Function> functions, Set<ComplexType> complexTypes,
			Set<GlobalVariable> globalVariables)
	{
		for(Function function : functions)
		{
			this.functions.put(function.getName(), function);
		}
		
		for(ComplexType complexType : complexTypes)
		{
			this.complexTypes.put(complexType.getName(), complexType);
		}
		
		for(GlobalVariable globalVariable : globalVariables)
		{
			this.globalVariables.put(globalVariable.getName(), globalVariable);
		}
	}
	
	public Map<String, Function> getFunctions() 
	{
		return new HashMap<String, Function>(this.functions);
	}

	public Map<String, ComplexType> getComplexTypes()
	{
		return new HashMap<String, ComplexType>(this.complexTypes);
	}

	public Map<String, GlobalVariable> getGlobalVariables()
	{
		return new HashMap<String, GlobalVariable>(this.globalVariables);
	}
}
