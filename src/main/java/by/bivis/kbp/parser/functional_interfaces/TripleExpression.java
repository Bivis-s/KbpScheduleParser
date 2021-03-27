package by.bivis.kbp.parser.functional_interfaces;

@FunctionalInterface
public interface TripleExpression<T, R, H> {
    H evaluate(T t, R r);
}
