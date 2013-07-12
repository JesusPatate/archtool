package fr.univ_nantes.alma.archtool.sourceModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fr.univ_nantes.alma.archtool.utils.MultiCounter;

/**
 * Classe du modèle de code source représentant un bloc de code d'une fonction.
 */
public class Block
{
    /**
     * Les appels effectués dans le bloc.
     */
    private final Set<Call> calls =
            new HashSet<Call>();

    /**
     * Les variables globales accédées à l'intérieur du bloc.
     */
    private final Map<GlobalVariable, Integer> globals =
            new HashMap<GlobalVariable, Integer>();
    /**
     * Les variables locales utilisées à l'intérieur du bloc.
     */
    private final Map<LocalVariable, Integer> locals =
            new HashMap<LocalVariable, Integer>();

    /**
     * Les sous-blocs du bloc.
     */
    private final Set<Block> subBlocks =
            new HashSet<Block>();

    public Block()
    {
    }

    public Block(final Set<Call> calls,
            final Map<GlobalVariable, Integer> globalVariables,
            final Map<LocalVariable, Integer> locals)
    {
        this.calls.addAll(calls);
        this.globals.putAll(globalVariables);
        this.locals.putAll(locals);
    }

    public Block(final Set<Call> calls,
            final Map<GlobalVariable, Integer> globalVariables,
            final Map<LocalVariable, Integer> locals,
            final Set<Block> subBlocks)
    {
        this.calls.addAll(calls);
        this.globals.putAll(globalVariables);
        this.locals.putAll(locals);
        this.subBlocks.addAll(subBlocks);
    }

    /**
     * Retourne l'ensemble des variables globales du bloc.
     * 
     * @return Une map contenant les variables globales accédées dans le bloc et
     *         pour chacune, le nombre d'accès.
     */
    public Map<GlobalVariable, Integer> getGlobalVariables()
    {
        MultiCounter<GlobalVariable> globalsUse =
                new MultiCounter<GlobalVariable>();
        globalsUse.incrementAll(this.globals);

        for (final Block block : this.subBlocks)
        {
            globalsUse.incrementAll(block.globals);
        }

        return globalsUse.getCounters();
    }

    /**
     * Retourne l'ensemble des variables globales au programme accédées dans le
     * bloc.
     * 
     * @return Une map contenant les variables globales au programme accédées
     *         dans le bloc et pour chacune, le nombre d'accès.
     */
    public Map<GlobalVariable, Integer> getProgramGlobals()
    {
        MultiCounter<GlobalVariable> globalsUse =
                new MultiCounter<GlobalVariable>();

        for (GlobalVariable global : this.globals.keySet())
        {
            if (!global.isStatic())
            {
                globalsUse.increment(global, this.globals.get(global));
            }
        }

        for (final Block block : this.subBlocks)
        {
            globalsUse.incrementAll(block.getProgramGlobals());
        }

        return globalsUse.getCounters();
    }

    /**
     * Retourne l'ensemble des variables globales au fichier accédées dans le
     * bloc.
     * 
     * @return Une map contenant les variables globales au fichier accédées dans
     *         le bloc et pour chacune, le nombre d'accès.
     */
    public Map<GlobalVariable, Integer> getFileGlobals()
    {
        MultiCounter<GlobalVariable> globalsUse =
                new MultiCounter<GlobalVariable>();

        for (GlobalVariable global : this.globals.keySet())
        {
            if (global.isStatic())
            {
                globalsUse.increment(global, this.globals.get(global));
            }
        }

        for (final Block block : this.subBlocks)
        {
            globalsUse.incrementAll(block.getProgramGlobals());
        }

        return globalsUse.getCounters();
    }

    /**
     * Retourne l'ensemble des variables locales utilisées dans le bloc.
     * 
     * @return Une map contenant les variables locales utilisées dans le bloc et
     *         pour chacune, le nombre d'utilisations.
     */
    public Map<LocalVariable, Integer> getLocalVariables()
    {
        MultiCounter<LocalVariable> localsUse =
                new MultiCounter<LocalVariable>();
        localsUse.incrementAll(this.locals);

        for (final Block block : this.subBlocks)
        {
            localsUse.incrementAll(block.getLocalVariables());
        }

        return localsUse.getCounters();
    }

    /**
     * Retourne l'ensemble des appels de fonction du bloc.
     * 
     * @return Un set des appels de fonction effectués à l'intérieur du bloc.
     */
    public Set<Call> getCalls()
    {
        final Set<Call> total = new HashSet<Call>(this.calls);

        for (final Block block : this.subBlocks)
        {
            total.addAll(block.getCalls());
        }

        return total;
    }

    /**
     * Renvoie l'ensemble des types complexes ou primitifs utilisés dans le
     * bloc.
     * 
     * @return Une map contenant l'ensemble des types utilisés dans le bloc et
     *         pour chacun d'eux, le nombre d'utilisations.
     */
    public Map<ComplexType, Integer> getComplexTypes()
    {
        MultiCounter<ComplexType> typesUse =
                new MultiCounter<ComplexType>();
        
        for(Entry<GlobalVariable, Integer> variableUse : 
            this.globals.entrySet())
        {
            Type type = variableUse.getKey().getType();
            
            if(type.isComplex && type != ComplexType.anonymousType)
            {          
                typesUse.increment((ComplexType) type, variableUse.getValue());
            }
        }
        
        for(Entry<LocalVariable, Integer> variableUse : this.locals.entrySet())
        {
            Type type = variableUse.getKey().getType();
            
            if(type.isComplex && type != ComplexType.anonymousType)
            {          
                typesUse.increment((ComplexType) type, variableUse.getValue());
            }
        }
        
        for (final Block block : this.subBlocks)
        {
            typesUse.incrementAll(block.getComplexTypes());
        }

        return typesUse.getCounters();
    }
}
