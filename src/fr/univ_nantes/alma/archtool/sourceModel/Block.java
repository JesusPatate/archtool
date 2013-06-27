package fr.univ_nantes.alma.archtool.sourceModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe du modèle de code source représentant un bloc de code d'une fonction. 
 */
public class Block
{
    /**
     * Les appels effectués dans le bloc.
     */
    private Set<Call> calls;

    /**
     * Les variables globales au programme accédées à l'intérieur du bloc.
     */
    private Map<ProgramGlobalVariable, Integer> programGlobals;

    /**
     * Les variables globales au fichier accédées à l'intérieur du bloc.
     */
    private Map<FileGlobalVariable, Integer>  fileGlobals;

    /**
     * Les variables locales utilisées à l'intérieur du bloc.
     */
    private Map<LocalVariable, Integer> locals;

    /**
     * Les sous-blocs du bloc.
     */
    private Set<Block> subBlocks;

    public Block(Set<Call> calls,
           Map<ProgramGlobalVariable, Integer> programGlobals,
           Map<FileGlobalVariable, Integer> fileGlobals,
           Map<LocalVariable, Integer> locals)
    {
        this.calls = calls;
        this.programGlobals = programGlobals;
        this.fileGlobals = fileGlobals;
        this.locals = locals;
        this.subBlocks = new HashSet<Block>();
    }
    
    public Block(Set<Call> calls,
           Map<ProgramGlobalVariable, Integer> programGlobals,
           Map<FileGlobalVariable, Integer> fileGlobals,
           Map<LocalVariable, Integer> locals,
           Set<Block> subBlocks)
    {
        this.calls = calls;
        this.programGlobals = programGlobals;
        this.fileGlobals = fileGlobals;
        this.locals = locals;
        this.subBlocks = subBlocks;
    }

    /**
     * Retourne l'ensemble des variables globales au programme du bloc.
     * 
     * @return Une map contenant les variables globales au programme accédées
     * dans le bloc et pour chacune, le nombre d'accès.
     */
    public Map<ProgramGlobalVariable, Integer> getProgramGlobals()
    {
        Map<ProgramGlobalVariable, Integer> total =
                new HashMap<ProgramGlobalVariable, Integer>(
                        this.programGlobals);
        
        for(Block b : this.subBlocks)
        {
            for(ProgramGlobalVariable v : b.getProgramGlobals().keySet())
            {
                if(total.containsKey(v))
                {
                    total.put(v, total.get(v) + b.getProgramGlobals().get(v));
                }
                else
                {
                    total.put(v, b.getProgramGlobals().get(v));
                }
            }
        }
        
        return total;
    }

    /**
     * Retourne l'ensemble des variables globales au fichier du bloc.
     * 
     * @return Une map contenant les variables globales au fichier accédées
     * dans le bloc et pour chacune, le nombre d'accès.
     */
    public Map<FileGlobalVariable, Integer> getFileGlobals()
    {
        Map<FileGlobalVariable, Integer> total =
                new HashMap<FileGlobalVariable, Integer>(
                        this.fileGlobals);
        
        for(Block b : this.subBlocks)
        {
            for(FileGlobalVariable v : b.getFileGlobals().keySet())
            {
                if(total.containsKey(v))
                {
                    total.put(v, total.get(v) + b.getFileGlobals().get(v));
                }
                else
                {
                    total.put(v, b.getFileGlobals().get(v));
                }
            }
        }
        
        return total;
    }

    /**
     * Retourne l'ensemble des variables locales du bloc.
     * 
     * @return Une map contenant les variables locales utilisées dans le bloc
     * et pour chacune, le nombre d'utilisations.
     */
    public Map<LocalVariable, Integer> getLocals()
    {
        Map<LocalVariable, Integer> total =
                new HashMap<LocalVariable, Integer>(
                        this.locals);
        
        for(Block b : this.subBlocks)
        {
            for(LocalVariable v : b.getLocals().keySet())
            {
                if(total.containsKey(v))
                {
                    total.put(v, total.get(v) + b.getLocals().get(v));
                }
                else
                {
                    total.put(v, b.getLocals().get(v));
                }
            }
        }
        
        return total;
    }

    /**
     * Retourne l'ensemble des appels du bloc.
     * 
     * @return Un set des appels de fonction effectués à l'intérieur du bloc.
     */
    public Set<Call> getCalls()
    {
        Set<Call> total = new HashSet<Call>(this.calls);
        
        for(Block b : this.subBlocks)
        {
            total.addAll(b.getCalls());
        }
        
        return total;
    }
    
    /**
     * Renvoie l'ensemble des types complexes ou primitifs
     * utilisés dans le bloc.
     * 
     * @return Une map contenant l'ensemble des types utilisés dans le bloc
     * et pour chacun d'eux, le nombre d'utilisations.
     */
    public Set<Type> getUsedTypes()
    {
        Set<Type> usedTypes = new HashSet<Type>();
        
        for(Variable var : this.programGlobals.keySet())
        {
            usedTypes.add(var.getType());
        }
        
        for(Variable var : this.fileGlobals.keySet())
        {
            usedTypes.add(var.getType());
        }

        for(Variable var : this.locals.keySet())
        {
            usedTypes.add(var.getType());
        }
        
        return usedTypes;
    }
}
