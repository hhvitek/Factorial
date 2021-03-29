package app;

import app.parallel.FactorialParallelImpl;
import app.parallel.ParallelException;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;

/**<pre>
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
 *

 * </pre>
 */
public class Main {

    public static void main(String[] args) {
        if (!processCommandLine(args)) {
            return;
        }

        String pathToFileArg = args[0];

        FileNumberLoader loader = new FileNumberLoader();
        Factorial factorial = new FactorialParallelImpl();

        try {
            List<Integer> inputNumbers = loader.load(Path.of(pathToFileArg));

            List<BigInteger> output = factorial.compute(inputNumbers);
            for (int i = 0; i < inputNumbers.size(); i++) {
                System.out.println(inputNumbers.get(i) + "! = " + output.get(i));
            }
        } catch (IOException | InvalidPathException e) {
            System.err.println("The parameter is not valid path. " + e.getMessage());
        } catch (InvalidLineException e) {
            ifInputNumberIsBiggerThanIntegerPrintRelevantMessageOtherwisePrintGeneralError(e);
        } catch (ParallelException | NumberIsLessThanZeroException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * https://picocli.info/ seems pretty good for java command line applications.
     * However lets just print Help manually
     *
     * Returns false if any error has been encountered during command line parsing OR
     *               the program should exist gracefully (-h argument).
     * Returns true on success
     */
    private static boolean processCommandLine(String[] args) {
        if (args.length != 1) {
            System.err.println("This program requires just one cmd argument");
            System.err.println(getHelpMessage());
            return false;
        } else if ("-h".equals(args[0]) || "--help".equals(args[0])) {
            System.out.println(getHelpMessage());
            return false;
        } else if (args[0].startsWith("-")) {
            System.err.println("Unrecognized cmd argument: " + args[0]);
            System.err.println(getHelpMessage());
            return false;
        }

        return true;
    }

    private static String getHelpMessage() {
        StringBuilder helpBuilder = new StringBuilder();
        helpBuilder.append("Usage: java -jar factorial.jar <file>\n\n");
        helpBuilder.append("This program computes factorial of all integers read from an input file and prints results to STDOUT.\n");
        helpBuilder.append("One line in the input file represents one number. The number must be in a range <0; 2,147,483,647>\n");
        helpBuilder.append("  <file>        The input file containing one number on each line.\n");
        helpBuilder.append("  -h, --help    Show this message and exit.\n");
        return helpBuilder.toString();
    }

    /**
     * FileNumberLoader parses input file line by line looking for Integers
     * If it fails to parse current line it throws InvalidLineException
     * It uses Integer.parseInt() method. Therefore it fails for numbers > Integer.MAX_VALUE or numbers < Integer.MIN_VALUE.
     *
     * This method ensures that in such a case it prints a unique message saying that an input number is too large.
     * Any big negative number (>Integer.MIN_VALUE) falls also into this category...
     *
     * It uses BigInteger constructor to parse this potential line. Another solution may be in regular expressions (\d+)
     */
    private static void ifInputNumberIsBiggerThanIntegerPrintRelevantMessageOtherwisePrintGeneralError(InvalidLineException invalidLineException) {
        try {
            new BigInteger(invalidLineException.getInvalidLine());
            System.err.println(
                    "The input number: <" + invalidLineException.getInvalidLine() +
                            "> is too large. Maximum supported number is: " + Integer.MAX_VALUE
            );
        } catch (NumberFormatException e) {
            System.err.println(invalidLineException.getMessage());
        }
    }
}