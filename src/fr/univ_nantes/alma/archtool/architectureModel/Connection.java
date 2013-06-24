package fr.univ_nantes.alma.archtool.architectureModel;

import java.util.Set;

import fr.univ_nantes.alma.archtool.utils.Pair;


/**
 * A connection between components linked by a connector.
 * @author E10A345H
 *
 */
public class Connection
{
    private Set<Pair<Component, Interface>> lesBouts;
    
    private Connector connector;
}
