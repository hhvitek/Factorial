package app;

import app.parallel.FactorialParallelImpl;
import app.parallel.ParallelException;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;

/**
 * This program computes factorial of all integers read from an input file.
 *
 * Each line in the input file contains just one input integer:
 * Input file:
 * 10
 * 5
 * 24
 * 7
 *
 * produces output like:
 * 10! = 3628800
 * 5! = 120
 * 24! = 620448401733239439360000
 * 7! = 5040
 *
 * - multithreading + no repeated computations
 */
public class Main {

    public static void main(String [] args) {
        if (args.length != 1) {
            System.err.println("This program requires one parameter. Path to an input file.");
            return;
        }

        String pathToFileArg = args[0];

        FileNumberLoader loader = new FileNumberLoader();
        Factorial factorial = new FactorialParallelImpl();

        try {
            List<Integer> inputNumbers = loader.load(Path.of(pathToFileArg));
            List<BigInteger> output = factorial.compute(inputNumbers);
            for(int i=0; i<inputNumbers.size(); i++) {
                System.out.println(inputNumbers.get(i) + "! = " + output.get(i));
            }
        } catch (IOException | InvalidPathException  e) {
            System.err.println("The parameter is not valid path. " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("The input file does not have expected format. Unrecognized value: " + e.getMessage());
        } catch (ParallelException e) {
            System.err.println(e.getMessage());
        }
    }
}