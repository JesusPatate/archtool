package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.Set;

import fr.univ_nantes.alma.archtool.sourceModel.CodeEntity;


public class Component
{
    private Set<Interface> requiredInterfaces;
    
    private Set<Interface> providedInterfaces;
    
    private Set<CodeEntity> codeEntities;
    
    
}
