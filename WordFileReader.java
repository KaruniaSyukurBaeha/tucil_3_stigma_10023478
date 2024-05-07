import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordFileReader {
    private String filePath;

    public WordFileReader(String filePath) {
        this.filePath = filePath;
    }

    public List<String> readDictionary() {
        List<String> dictionary = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
            String word;
            while ((word = br.readLine()) != null) {
                dictionary.add(word.trim());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Dictionary file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading dictionary file: " + e.getMessage());
        }
        return dictionary;
    }
}
