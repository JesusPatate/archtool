package fr.univ_nantes.alma.archtool.objective;

public interface Evaluator<E>
{
    double evaluate(final E element);
}