package app;

/**
 * Indicates that the input number is less than zero.
 *
 * The factorial is defined only for numbers >= 0
 */
public class NumberIsLessThanZeroException extends RuntimeException {

    private final int number;

    public NumberIsLessThanZeroException(int number) {
        this.number = number;
    }

    @Override
    public String getMessage() {
        return "The input contains negative number: <" + number + ">";
    }
}