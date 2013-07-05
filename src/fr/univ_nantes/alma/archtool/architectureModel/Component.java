package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.HashSet;
import java.util.Set;

public class Component
{
    private final Set<Interface> requiredInterfaces = new HashSet<Interface>();

    private final Set<Interface> providedInterfaces = new HashSet<Interface>();

    public Set<Interface> getRequiredInterfaces()
    {
        return new HashSet<Interface>(this.requiredInterfaces);
    }

    public boolean requiresInterface(final Interface i)
    {
        return this.requiredInterfaces.contains(i);
    }

    public boolean addRequiredInterface(final Interface i)
    {
        return this.requiredInterfaces.add(i);
    }

    public boolean removeRequiredInterface(final Interface i)
    {
        return this.requiredInterfaces.remove(i);
    }

    public Set<Interface> getProvidedInterfaces()
    {
        return new HashSet<Interface>(this.providedInterfaces);
    }

    public boolean providesInterface(final Interface i)
    {
        return this.providedInterfaces.contains(i);
    }

    public boolean addProvidedInterface(final Interface i)
    {
        return this.providedInterfaces.add(i);
    }

    public boolean removeProvidedInterface(final Interface i)
    {
        return this.providedInterfaces.remove(i);
    }
}
