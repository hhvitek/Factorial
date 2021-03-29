package app.parallel;

import app.Factorial;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**<pre>
 * Algorithm:
 * INPUT array of integers
 * OUTPUT array of factorials
 *
 * requirement: INPUT array must be sorted!
 *
 * INPUT [1, 4, 5, 7]
 * modified INPUT [1, 1, 4, 5, 7] <= added "1" at the beginning
 *
 * Let's split modified INPUT array into chunks and compute partial factorial in the each chunk independently:
 * chunk1 1, 1 => 1
 * chunk2 1, 4 => 24 4*3*2
 * chunk3 4, 5 => 5 5
 * chunk4 5, 7 => 42 7*6
 * PARTIAL [1, 24, 5, 42]
 *
 * Then let's perform sequential reduction:
 * FACTORIALS [1, 24, 120, 5040]
 *
 * Individual thread are working directly with two Arrays
 *  * INPUT - reading chunk boundaries
 *  * PARTIAL - writing chunk factorial into its unique array index
 * </pre>
 */
public class FactorialParallelImpl implements Factorial {

    public static int MAX_COMPUTATION_TIME_IN_SECONDS = 10;

    private int numberOfComputingThreads = 1;

    // inputNumbers sorted and additional 1 is at the beginning (0 index)
    private Integer[] input;
    private int inputSize = 0;

    private BigInteger[] partialFactorials;

    private NumbersContainer numbers;

    private ExecutorService executor;

    public FactorialParallelImpl() {
        int numberOfCpus = Runtime.getRuntime().availableProcessors();
        this.numberOfComputingThreads = numberOfCpus;
    }

    public FactorialParallelImpl(int numberOfComputingThreads) {
        this.numberOfComputingThreads = numberOfComputingThreads;
    }

    private ExecutorService createExecutor(int numberOfComputingThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfComputingThreads);
        return executor;
    }

    /**
     * @throws ParallelException may throw if the computation is interrupted or is taking too long
     */
    @Override
    public List<BigInteger> compute(List<Integer> inputNumbers) {
        executor = createExecutor(this.numberOfComputingThreads);
        initializeRequiredStructures(inputNumbers);

        // for each chunk submit runnable
        for(int chunkIndex=0; chunkIndex<inputSize - 1; chunkIndex++) {
            int finalChunkIndex = chunkIndex;
            Runnable runnable =  () -> computePartialFactorial(finalChunkIndex);
            executor.submit(runnable);
        }

        // wait for all tasks to finish...
        waitForChunksComputationToFinishIfErrorThrow(executor);

        // sequential reduction
        List<BigInteger> output = new ArrayList<>();
        BigInteger tmpFactorial = BigInteger.ONE;
        for(int i=0; i<inputSize - 1; i++) {
            tmpFactorial = tmpFactorial.multiply(partialFactorials[i]);
            output.add(tmpFactorial);
        }

        return numbers.reorderFactorialsAccordingToOriginalOrder(output);
    }

    private void initializeRequiredStructures(List<Integer> inputNumbers) {
        this.input = new Integer[inputNumbers.size() + 1];
        this.inputSize = inputNumbers.size() + 1;
        this.partialFactorials = new BigInteger[inputNumbers.size() + 1];

        this.numbers = new NumbersContainer(inputNumbers);
        Integer [] sortedInputNumbers = numbers.getAsSortedArray();

        this.input[0] = 1; // input array starts with 1 O(1)
        System.arraycopy(sortedInputNumbers, 0, this.input, 1, sortedInputNumbers.length);
    }

    private void waitForChunksComputationToFinishIfErrorThrow(ExecutorService executor) throws ParallelException{
        executor.shutdown();
        try {
            boolean retValue = executor.awaitTermination(MAX_COMPUTATION_TIME_IN_SECONDS, TimeUnit.SECONDS);
            if (!retValue) {
                executor.shutdownNow();
                throw new ParallelException(
                        "The chunks computation is taking too long. Timeout after seconds: " + MAX_COMPUTATION_TIME_IN_SECONDS);
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            throw new ParallelException("The waiting has been interrupted");
        }
    }

    private void computePartialFactorial(int chunkIndex) {
        int valueMin = input[chunkIndex];
        int valueMax = input[chunkIndex + 1];

        BigInteger partialOutput = computePartialFactorialInChunk(valueMin, valueMax);
        this.partialFactorials[chunkIndex] = partialOutput;
    }

    private BigInteger computePartialFactorialInChunk(int valueMin, int valueMax) {
        BigInteger currentValue = BigInteger.ONE;

        for (int i=valueMin + 1; i<=valueMax; i++) {
            currentValue = currentValue.multiply(BigInteger.valueOf(i));
        }
        return currentValue;
    }

}