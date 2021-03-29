package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileNumberLoader {
    /**
     *  Parses an input file line by line looking for Integers
     *  If it fails to parse the current line it throws InvalidLineException(lineNumber, line).
     *  It internally uses Integer.parseInt() method
     *
     * @throws IOException
     * @throws InvalidLineException if cannot parse input file line as an Integer...
     */
    public List<Integer> load(Path pathToFile) throws IOException, InvalidLineException {

        int lineCount = 0;
        String lineExpectedNumber = null;

        try (BufferedReader br = new BufferedReader(
                new FileReader(pathToFile.toFile(), StandardCharsets.UTF_8))
        ) {
            List<Integer> output = new ArrayList<>();

            while ((lineExpectedNumber = br.readLine()) != null) {
                lineCount++;
                int number = Integer.parseInt(lineExpectedNumber);
                output.add(number);
            }

            return output;
        } catch (NumberFormatException e) {
            throw new InvalidLineException(lineCount, lineExpectedNumber);
        }
    }
}