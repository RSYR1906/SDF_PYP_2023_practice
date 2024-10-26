package MayPYP.src;
import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class TestHashmap {

    static HashMap<String, HashMap<String, Integer>> Map = new HashMap<>();

    static HashMap<String, Integer> testMap = new HashMap<>();

    public static void main(String[] args) throws IOException {

        String fileName = "austen.txt";
        String currentWord = "";
        String nextWord = "";

        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String line = "";

        while ((line = br.readLine()) != null) {

            line = line.trim().replaceAll("\\p{Punct}", "").toLowerCase();

            if (line.isEmpty()) {
                continue;
            }

            String[] words = line.split("\\s+"); // removing whitespaces

            for (int i = 0; i < words.length - 1; i++) {
                currentWord = words[i];
                nextWord = words[i + 1];
            }

            Map.putIfAbsent(currentWord, new HashMap<>()); // putting current word into Map
            HashMap<String, Integer> testMap = Map.get(currentWord); // putting testMap as the value of current word
            testMap.put(nextWord, testMap.getOrDefault(nextWord, 0) + 1);
        }
        br.close();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.printf("Enter a word (enter exit to leave the program): ");
            String word = scanner.nextLine().trim().toLowerCase();

            if (word.equals("exit")) {
                System.out.println("Bye bye!");
                scanner.close();
                break;
            }

            displayNextWordProbability(word);

        }
    }

    public static void displayNextWordProbability(String word) {

        Map<String, Integer> testMap = Map.get(word); // accessing the inner Hashmap through key from outer Hashmap

        if (testMap.isEmpty() || testMap == null) { // error handling
            System.out.println("There is no next word after " + word + ".");
            return;
        }

        int totalNextWord = testMap.values().stream().mapToInt(Integer::intValue).sum(); // using streams to calculate
                                                                                         // sum of all possible next
                                                                                         // words

        System.out.println("Printing out all possible next words and their probabilities");
        for (Entry<String, Integer> entry : testMap.entrySet()) { // iterating through all key-value mappings in the
                                                                  // testMap
            String nextWords = entry.getKey();
            int count = entry.getValue();
            double probability = (double) count / totalNextWord * 100;
            System.out.printf("Next word: %s , Probability: %.2f%% \n", nextWords, probability);
        }
    }
}
