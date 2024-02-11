import java.util.Scanner;
import java.util.TreeMap;

public class sga {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Adding a scanner
        int n = sc.nextInt(); // Reading the number of students
        sc.nextLine(); // Checking the next line

        // TreeMap to count occurrences of the first letter of each name. Has O(log n) complexity.
        TreeMap<Character, Long> count = new TreeMap<>();

        // TreeMap to count occurrences of each unique full name. Has O(log n) complexity.
        TreeMap<String, Long> nameCount = new TreeMap<>();

        readAndCountNames(sc, n, count, nameCount); // Reading and counting names

        long totalPairs = calculateTotalPairs(count, nameCount); // Calculating total valid pairs

        System.out.println(totalPairs);
    }

    // Function to read names and update counts
    private static void readAndCountNames(Scanner sc, int numStudents, TreeMap<Character, Long> count, TreeMap<String, Long> nameCount) {
        for (int i = 0; i < numStudents; i++) {
            String name = sc.nextLine();
            char firstLetter = name.charAt(0);

            // Inserting/updating the first letter count in count. Takes O(log k), where k is the number of different first letters.
            count.put(firstLetter, count.getOrDefault(firstLetter, 0L) + 1);

            // Inserting/updating the full name count in nameCount. Takes O(log m), where m is the number of different full names.
            nameCount.put(name, nameCount.getOrDefault(name, 0L) + 1);
        }
    }

    // Function to calculate the total number of valid pairs
    private static long calculateTotalPairs(TreeMap<Character, Long> count, 
                                            TreeMap<String, Long> nameCount) {
        long totalPairs = 0;

        // Calculating the number of pairs for each first letter. Takes O(k) time.
        for (long cnt : count.values()) {
            totalPairs += cnt * (cnt - 1); // Combination math
        }

        // Subtracting pairs with the same name. Takes O(m) time.
        for (long cnt : nameCount.values()) {
            if (cnt > 1) {
                totalPairs -= cnt * (cnt - 1); // Subtracting self-pairings
            }
        }

        return totalPairs;
    }
}

/* CODE I USED FOR sga100.in which is an input that breaks the code
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomNamesGenerator {

    public static void main(String[] args) {
        String fileName = "random_names.in";
        int numNames = 50000;
        char startLetter = 'A';

        try {
            createFileWithNames(fileName, numNames, startLetter);
            System.out.println("File created successfully with " + numNames + " names.");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
    }

    private static void createFileWithNames(String fileName, int numNames, char startLetter) throws IOException {
        FileWriter writer = new FileWriter(fileName);

        writer.write(numNames + "\n");
        for (int i = 0; i < numNames; i++) {
            String name = generateRandomName(startLetter);
            writer.write(name + "\n");
        }

        writer.close();
    }

    private static String generateRandomName(char startLetter) {
        Random random = new Random();
        int nameLength = random.nextInt(10) + 1; // Random length between 1 and 10
        StringBuilder name = new StringBuilder(String.valueOf(startLetter));

        for (int i = 1; i < nameLength; i++) {
            char nextChar = (char) ('a' + random.nextInt(26)); // Generate a random lowercase letter
            name.append(nextChar);
        }

        return name.toString();
    }
}
 */