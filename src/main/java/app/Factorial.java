package app;

import java.math.BigInteger;
import java.util.List;

/**
 * INPUT: List of integers
 * OUTPUT: List of factorials in the same order
 */
public interface Factorial {

    /**
     * @throws NumberIsLessThanZeroException If any of inputNumbers is a negative number...
     */
    List<BigInteger> compute(List<Integer> inputNumbers) throws NumberIsLessThanZeroException;
}