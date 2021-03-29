package app.parallel;

import java.math.BigInteger;
import java.util.*;

/**
 * Helper class required additional Hash structure
 * to remember original indexes before vs after sorting.
 */
public class NumbersContainer {

    private Map<Integer, List<Integer>> inputNumberToOriginalIndexes = new HashMap<>();

    private List<Integer> originalInputNumbers;

    private Integer[] sortedArray;

    public NumbersContainer(List<Integer> inputNumbers) {
        for(int i=0; i<inputNumbers.size(); i++) {
            int inputNumber = inputNumbers.get(i);

            insertIntoHashMap(inputNumber, i);
        }

        this.originalInputNumbers = new ArrayList<>(inputNumbers);

        List<Integer> sortedInputNumbers = new ArrayList<>(originalInputNumbers);
        sortedInputNumbers.sort(Comparator.naturalOrder());

        sortedArray = new Integer[this.originalInputNumbers.size()];
        sortedInputNumbers.toArray(sortedArray);
    }

    private void insertIntoHashMap(int inputNumber, int index) {
        List<Integer> indexes = inputNumberToOriginalIndexes.getOrDefault(inputNumber, new ArrayList<>());
        indexes.add(index);
        inputNumberToOriginalIndexes.put(inputNumber, indexes);
    }

    /**
     * The copy is returned.
     */
    public Integer[] getAsSortedArray() {
        return Arrays.copyOf(sortedArray, sortedArray.length);
    }

    public List<BigInteger> reorderFactorialsAccordingToOriginalOrder(List<BigInteger> factorials) {
        BigInteger []output = new BigInteger[factorials.size()];
        for (int i=0; i<originalInputNumbers.size(); i++) {
            List<Integer> indexes = inputNumberToOriginalIndexes.get(sortedArray[i]);
            int finalI = i;
            indexes.stream().forEach(index ->
                    output[index] = factorials.get(finalI)
            );
        }

        return Arrays.asList(output);
    }
}