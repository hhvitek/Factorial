package app;

/**<pre>
 * Indicates that the input file is not in a valid format.
 *
 * This exception contains two variables:
 *  - The line number indicating which line is not valid.
 *  - The actual invalid line
 * </pre>
 */
public class InvalidLineException extends RuntimeException {

    private final int lineCount;
    private final String invalidLine;

    public InvalidLineException(int lineCount, String invalidLine) {
        this.lineCount = lineCount;
        this.invalidLine = invalidLine;
    }

    public int getLineCount() {
        return lineCount;
    }

    public String getInvalidLine() {
        return invalidLine;
    }

    @Override
    public String getMessage() {
        return "The line number: <" + lineCount + "> is not valid. Line: <" + invalidLine + ">";
    }
}