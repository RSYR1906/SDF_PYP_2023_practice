package src;

import java.io.*;
import java.util.*;

public class WordGeneratorApp {

    private static HashMap<String, Map<String, Integer>> nextWordMap = new HashMap<>();

    public static void main(String[] args) throws IOException {

        String fileName = args[0];
        // Read text from file
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = br.readLine()) != null) {
            // Remove punctuation, convert to lowercase
            line = line.trim().replaceAll("\\p{Punct}", "").toLowerCase();
            if (line.isEmpty()) continue;

            String[] words = line.split("\\s+");
            for (int i = 0; i < words.length - 1; i++) {
                String curr = words[i];
                String next = words[i + 1];

                // Update nextWordMap with current and next word distribution
                nextWordMap.putIfAbsent(curr, new HashMap<>());
                Map<String, Integer> wordDistrib = nextWordMap.get(curr);
                wordDistrib.put(next, wordDistrib.getOrDefault(next, 0) + 1);
            }
        }
        br.close();

        // Create scanner to take user input
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a word to get next word probabilities (or 'exit' to quit): ");
            String inputWord = scanner.nextLine().trim().toLowerCase();

            if (inputWord.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            displayNextWordProbabilities(inputWord);
        }
    }

    // Method to display possible next words and their probabilities
    public static void displayNextWordProbabilities(String word) {
        Map<String, Integer> wordDistrib = nextWordMap.get(word);

        if (wordDistrib == null || wordDistrib.isEmpty()) {
            System.out.println("No next words found for the word: " + word);
            return;
        }

        // Calculate total occurrences for the word
        int totalOccurrences = wordDistrib.values().stream().mapToInt(Integer::intValue).sum();

        System.out.println("Possible next words for '" + word + "' and their probabilities:");
        for (Map.Entry<String, Integer> entry : wordDistrib.entrySet()) {
            String nextWord = entry.getKey();
            int count = entry.getValue();
            double probability = (double) count / totalOccurrences *100;
            System.out.printf("Word: '%s', Probability: %.2f%%\n", nextWord, probability);
        }
    }
}