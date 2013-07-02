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
    private final Set<Call> calls =
            new HashSet<Call>();

    /**
     * Les variables globales au programme accédées à l'intérieur du bloc.
     */
    private final Map<ProgramGlobalVariable, Integer> programGlobals =
            new HashMap<ProgramGlobalVariable, Integer>();

    /**
     * Les variables globales au fichier accédées à l'intérieur du bloc.
     */
    private final Map<FileGlobalVariable, Integer> fileGlobals =
            new HashMap<FileGlobalVariable, Integer>();

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
            final Map<ProgramGlobalVariable, Integer> programGlobals,
            final Map<FileGlobalVariable, Integer> fileGlobals,
            final Map<LocalVariable, Integer> locals)
    {
        this.calls.addAll(calls);
        this.programGlobals.putAll(programGlobals);
        this.fileGlobals.putAll(fileGlobals);
        this.locals.putAll(locals);
    }

    public Block(final Set<Call> calls,
            final Map<ProgramGlobalVariable, Integer> programGlobals,
            final Map<FileGlobalVariable, Integer> fileGlobals,
            final Map<LocalVariable, Integer> locals,
            final Set<Block> subBlocks)
    {
        this.calls.addAll(calls);
        this.programGlobals.putAll(programGlobals);
        this.fileGlobals.putAll(fileGlobals);
        this.locals.putAll(locals);
        this.subBlocks.addAll(subBlocks);
    }

    /**
     * Retourne l'ensemble des variables globales au programme du bloc.
     * 
     * @return Une map contenant les variables globales au programme accédées
     *         dans le bloc et pour chacune, le nombre d'accès.
     */
    public Map<ProgramGlobalVariable, Integer> getProgramGlobals()
    {
        final Map<ProgramGlobalVariable, Integer> total =
                new HashMap<ProgramGlobalVariable, Integer>(
                        this.programGlobals);

        for (final Block b : this.subBlocks)
        {
            for (final ProgramGlobalVariable v : b.getProgramGlobals().keySet())
            {
                if (total.containsKey(v))
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
     * @return Une map contenant les variables globales au fichier accédées dans
     *         le bloc et pour chacune, le nombre d'accès.
     */
    public Map<FileGlobalVariable, Integer> getFileGlobals()
    {
        final Map<FileGlobalVariable, Integer> total =
                new HashMap<FileGlobalVariable, Integer>(
                        this.fileGlobals);

        for (final Block b : this.subBlocks)
        {
            for (final FileGlobalVariable v : b.getFileGlobals().keySet())
            {
                if (total.containsKey(v))
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
     * @return Une map contenant les variables locales utilisées dans le bloc et
     *         pour chacune, le nombre d'utilisations.
     */
    public Map<LocalVariable, Integer> getLocals()
    {
        final Map<LocalVariable, Integer> total =
                new HashMap<LocalVariable, Integer>(
                        this.locals);

        for (final Block b : this.subBlocks)
        {
            for (final LocalVariable v : b.getLocals().keySet())
            {
                if (total.containsKey(v))
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
        final Set<Call> total = new HashSet<Call>(this.calls);

        for (final Block b : this.subBlocks)
        {
            total.addAll(b.getCalls());
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
    public Map<Type, Integer> getUsedTypes()
    {
        final Map<Type, Integer> usedTypes = new HashMap<Type, Integer>();

        for (final Variable var : this.programGlobals.keySet())
        {
            usedTypes.put(var.getType(), this.programGlobals.get(var));
        }

        for (final Variable var : this.fileGlobals.keySet())
        {
            usedTypes.put(var.getType(), this.fileGlobals.get(var));
        }

        for (final Variable var : this.locals.keySet())
        {
            usedTypes.put(var.getType(), this.locals.get(var));
        }

        return usedTypes;
    }
}
