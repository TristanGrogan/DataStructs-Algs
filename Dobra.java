import java.util.*;

public class Dobra {
    
    // Constants representing the number of vowels and consonants.
    private static final int VOWEL_COUNT = 5; // A, E, I, O, U
    private static final int CONSONANT_COUNT = 20; // All uppercase letters excluding vowels and L
    
    // Variable to store the final answer.
    private static long totalPleasantWords = 0;
    
    // The input string.
    private static String inputString;
    
    // Array to store the number of ways each character can be replaced.
    private static int[] replacementWays;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        inputString = scanner.nextLine();
        scanner.close();

        replacementWays = new int[inputString.length()];
        Arrays.fill(replacementWays, 1); // Initialize all to 1 for non-underscore characters.

        calculatePleasantWords(0);
        System.out.println(totalPleasantWords);
    }

    // Checks if the current string configuration is valid.
    private static boolean isValidConfiguration() {
        boolean containsL = false;
        int consecutiveCount = 0;
        boolean isCurrentVowel = false;

        for (char character : inputString.toCharArray()) {
            if (character == 'L') containsL = true;

            if ("AEIOU".indexOf(character) >= 0) {
                if (isCurrentVowel) consecutiveCount++;
                else {
                    isCurrentVowel = true;
                    consecutiveCount = 1;
                }
            } else {
                if (!isCurrentVowel) consecutiveCount++;
                else {
                    isCurrentVowel = false;
                    consecutiveCount = 1;
                }
            }

            if (consecutiveCount >= 3) return false; // Invalid if 3 consecutive vowels or consonants.
        }

        return containsL; // Valid only if at least one 'L' is present.
    }

    // Recursive method to calculate the number of pleasant words.
    private static void calculatePleasantWords(int index) {
        if (index == inputString.length()) {
            if (isValidConfiguration()) {
                long count = 1;
                for (int j = 0; j < inputString.length(); j++) {
                    count *= replacementWays[j];
                }
                totalPleasantWords += count;
            }
            return;
        }

        if (inputString.charAt(index) != '_') {
            calculatePleasantWords(index + 1);
        } else {
            // Try replacing underscore with 'L'.
            inputString = inputString.substring(0, index) + 'L' + inputString.substring(index + 1);
            calculatePleasantWords(index + 1);

            // Try replacing underscore with a consonant.
            inputString = inputString.substring(0, index) + 'X' + inputString.substring(index + 1);
            replacementWays[index] = CONSONANT_COUNT;
            calculatePleasantWords(index + 1);

            // Try replacing underscore with a vowel.
            inputString = inputString.substring(0, index) + 'A' + inputString.substring(index + 1);
            replacementWays[index] = VOWEL_COUNT;
            calculatePleasantWords(index + 1);

            // Reset the character and replacement ways for backtracking.
            inputString = inputString.substring(0, index) + '_' + inputString.substring(index + 1);
            replacementWays[index] = 1;
        }
    }
}