package app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

public abstract class FactorialTest {

    protected Factorial factorial;

    protected FactorialTest(Factorial factorial) {
        this.factorial = factorial;
    }

    @Test
    public void simpleTest1() {
        List<Integer> inputNumbers = List.of(1, 4, 5, 7);
        List<BigInteger> expectedOutput = List.of(
                BigInteger.valueOf(1),
                BigInteger.valueOf(24),
                BigInteger.valueOf(120),
                BigInteger.valueOf(5040)
        );

        List<BigInteger> actualOutput = factorial.compute(inputNumbers);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void simpleTest1ButReversed() {
        List<Integer> inputNumbers = List.of(7, 5, 4, 1);
        List<BigInteger> expectedOutput = List.of(
                BigInteger.valueOf(5040),
                BigInteger.valueOf(120),
                BigInteger.valueOf(24),
                BigInteger.valueOf(1)
        );

        List<BigInteger> actualOutput = factorial.compute(inputNumbers);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void simpleTestDuplicateNumber() {
        List<Integer> inputNumbers = List.of(5, 7, 2, 7);
        List<BigInteger> expectedOutput = List.of(
                BigInteger.valueOf(120),
                BigInteger.valueOf(5040),
                BigInteger.valueOf(2),
                BigInteger.valueOf(5040)
        );

        List<BigInteger> actualOutput = factorial.compute(inputNumbers);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void simpleTestOneNumber() {
        List<Integer> inputNumbers = List.of(5);
        List<BigInteger> expectedOutput = List.of(BigInteger.valueOf(120));

        List<BigInteger> actualOutput = factorial.compute(inputNumbers);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void exampleTest() {
        List<Integer> inputNumbers = List.of(10, 5, 24, 7);
        List<BigInteger> expectedOutput = List.of(
                BigInteger.valueOf(3628800),
                BigInteger.valueOf(120),
                new BigInteger("620448401733239439360000"),
                BigInteger.valueOf(5040)
        );

        List<BigInteger> actualOutput = factorial.compute(inputNumbers);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void negativeNumberInInputThrowsExceptionTest() {
        List<Integer> inputNumbers = List.of(10, 5, -1, 7);

        Assertions.assertThrows(
                NumberIsLessThanZeroException.class,
                () -> factorial.compute(inputNumbers)
        );
    }

    @Test
    public void containsZeroTest() {
        List<Integer> inputNumbers = List.of(10, 5, 0, 7);
        List<BigInteger> expectedOutput = List.of(
                BigInteger.valueOf(3628800),
                BigInteger.valueOf(120),
                BigInteger.ONE,
                BigInteger.valueOf(5040)
        );

        Assertions.assertEquals(expectedOutput, factorial.compute(inputNumbers));
    }

}