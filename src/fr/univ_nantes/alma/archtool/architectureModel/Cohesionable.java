package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.Set;

import fr.univ_nantes.alma.archtool.sourceModel.Function;
import fr.univ_nantes.alma.archtool.sourceModel.Type;
import fr.univ_nantes.alma.archtool.sourceModel.Variable;

public interface Cohesionable
{
    public Set<Function> getFunctions();
    
    public Set<Variable> getVariables();
    
    public Set<Type> getTypes();
}
