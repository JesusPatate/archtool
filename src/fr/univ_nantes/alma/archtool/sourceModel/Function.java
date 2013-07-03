package fr.univ_nantes.alma.archtool.sourceModel;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe du modèle de code source représentant une fonction.
 * 
 * <p>
 * Une fonction est composée de
 * <ul>
 * <li>son nom,</li>
 * <li>son type de retour,</li>
 * <li>l'ensemble de ses arguments,</li>
 * <li>le fichier dans lequel elle est définie,
 * <li>son corps (bloc de code)</li>
 * </ul>
 * </p>
 */
public class Function
{
    private final String name;

    private final Set<LocalVariable> arguments = new HashSet<LocalVariable>();

    private final Type returnType;

    private final File sourceFile;

    private final Block body;

    /**
     * Constructeur pour les fonctions dont on ne connait pas la définition.
     * 
     * @param name
     * @param sourceFile
     */
    public Function(final String name)
    {
        this.name = name;
        this.returnType = null;
        this.sourceFile = null;
        this.body = null;
    }

    public Function(final String name, final Set<LocalVariable> arguments,
            final Type returnType,
            final File sourceFile, final Block body)
    {
        this.name = name;
        this.arguments.addAll(arguments);
        this.returnType = returnType;
        this.sourceFile = sourceFile;
        this.body = body;
    }

    /**
     * Retourne le nom de la fonction.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Retourne la liste des arguments de la fonction.
     */
    public Set<LocalVariable> getArguments()
    {
        return new HashSet<LocalVariable>(this.arguments);
    }

    /**
     * Retourne le type de retour de la fonction.
     */
    public Type getReturnType()
    {
        return this.returnType;
    }

    /**
     * Retourne le fichier source dans lequel est définie la fonction.
     */
    public File getSourceFile()
    {
        return this.sourceFile;
    }

    /**
     * Renvoie l'ensemble des variables globales au programme utilisées par la
     * fonction.
     * 
     * @return Une map ayant pour clés les variables globales au programme
     *         accédées dans le corps de la fonction et pour valeurs leur nombre
     *         d'accès.
     */
    public Map<ProgramGlobalVariable, Integer> getProgramGlobals()
    {
        return this.body.getProgramGlobals();
    }

    /**
     * Renvoie l'ensemble des variables globales au fichier utilisées par la
     * fonction.
     * 
     * @return Une map ayant pour clés les variables globales au fichier
     *         accédées dans le corps de la fonction et pour valeurs leur nombre
     *         d'accès.
     */
    public Map<FileGlobalVariable, Integer> getFileGlobals()
    {
        return this.body.getFileGlobals();
    }

    /**
     * Renvoie l'ensemble des variables locales de la fonction.
     * 
     * <p>
     * Les arguments ne sont pas considérés comme des variables locales.
     * </p>
     * 
     * @return Une map ayant pour clés les variables locales utilisées dans le
     *         corps de la fonction et pour valeurs leur nombre d'utilisations.
     */
    public Map<LocalVariable, Integer> getLocals()
    {
        return this.body.getLocals();
    }

    /**
     * Renvoie l'ensemble des appels réalisés dans la fonction.
     * 
     * @return Un set contenant les appels réalisés dans le corps de la
     *         fonction.
     */
    public Set<Call> getCalls()
    {
        return this.body.getCalls();
    }

    public boolean calls(final Function fct)
    {
        boolean called = false;

        for (final Call call : this.getCalls())
        {
            if (call.getFunction().equals(fct))
            {
                called = true;
            }
        }

        return called;
    }

    /**
     * Renvoie l'ensemble des types utilisés par la fonction.
     * 
     * <p>
     * Les types utilisés peuvent être primitifs ou complexes. Les arguments ne
     * sont pas pris en compte.
     * </p>
     * 
     * @return Un set contenant les types utilisés dans le corps de la fonction.
     */
    public Map<Type, Integer> getUsedTypes()
    {
        return this.body.getUsedTypes();
    }
    
    @Override
    public String toString()
    {
        return String.format("Function : %s", this.name);
    }
}
