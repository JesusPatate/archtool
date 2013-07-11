package fr.univ_nantes.alma.archtool.sourceModel;

import java.util.Set;

public interface SourceCodeMediator
{
    public void createRelations(Set<Function> functions, Set<ComplexType> types,
            Set<GlobalVariable> globals);
    
    public Set<Function> getFunctionsCalledBy(Function function);
    
    public Set<Function> getFunctionsCalling(Function function);
    
    public Set<GlobalVariable> getGlobalsUsedBy(Function function);
    
    public Set<Function> getFunctionUsing(GlobalVariable global);
    
    public Set<ComplexType> getTypesUsedBy(Function function);
    
    public Set<Function> getFunctionUsing(ComplexType type);
    
    public Set<GlobalVariable> getGlobalsUsing(ComplexType type);
}
