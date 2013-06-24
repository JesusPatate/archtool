package fr.univ_nantes.alma.archtool.sourceModel;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe du modèle de code source représentant une fonction.
 * 
 * <p>
 *     Une fonction est composée de
 *     <ul>
 *         <li>son nom,</li>
 *         <li>son type de retour,</li>
 *         <li>l'ensemble de ses arguments,</li>
 *         <li>le fichier dans lequel elle est définie,
 *         <li>son corps (bloc de code)</li>
 *     </ul>
 * </p>
 */
public class Function
{
    private String name;

    private Type returnType;

    private Set<LocalVariable> arguments;

    private File sourceFile;

    private Block body;

    public Function(String name, Set<LocalVariable> arguments, Type returnType,
            File sourceFile, Block body)
    {
        this.name = name;
        this.arguments = arguments;
        this.returnType = returnType;
        this.sourceFile = sourceFile;
        this.body = body;
    }

    public String getName()
    {
        return this.name;
    }

    public Set<LocalVariable> getArguments()
    {
        return new HashSet<LocalVariable>(this.arguments);
    }

    public Type getReturnType()
    {
        return this.returnType;
    }

    public File getSourceFile()
    {
        return this.sourceFile;
    }

    public Map<ProgramGlobalVariable, Integer> getProgramGlobals()
    {
        return this.body.getProgramGlobals();
    }

    public Map<FileGlobalVariable, Integer> getFileGlobals()
    {
        return this.body.getFileGlobals();
    }

    public Map<LocalVariable, Integer> getLocals()
    {
        return this.body.getLocals();
    }

    public Set<Call> getCalls()
    {
        return this.body.getCalls();
    }
    
    /**
     * Renvoie l'ensemble des types complexes ou primitifs utilisés
     * par la fonction (arguments compris).
     * 
     * @return Un ensemble des types utilisés dans la fonction.
     */
    public Set<Type> getUsedTypes()
    {
        Set<Type> usedTypes = new HashSet<Type>();
        
        for(Variable var : this.arguments)
        {
            usedTypes.add(var.getType());
        }
        
        usedTypes.addAll(this.body.getUsedTypes());
        
        return usedTypes;
    }
}
