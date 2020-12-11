package com.numberengineer.aoc;


import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface Consumers extends Consumer<Object[]> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(Object... t);

    /**
     * Returns a composed {@code Consumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default Consumers andThen(Consumers after) {
        Objects.requireNonNull(after);
        return (Object[] t) -> {
            accept(t);
            after.accept(t);
        };
    }
}