package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileNumberLoader {
    /**
     * @throws IOException
     * @throws NumberFormatException if cannot parse input file line as Integer...
     */
    public List<Integer> load(Path pathToFile) throws IOException, NumberFormatException {
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile.toFile()))) {
            List<Integer> output = new ArrayList<>();

            String lineExpectedNumber;
            while ((lineExpectedNumber = br.readLine()) != null) {
                    int number = Integer.parseInt(lineExpectedNumber);
                    output.add(number);

            }

            return output;
        }
    }
}