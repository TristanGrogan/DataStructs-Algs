import java.util.*;

public class dyslectionary {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Initialize Scanner
        List<List<String>> Groups = new ArrayList<>(); // Store groups of words
        List<String> thisGroup = new ArrayList<>(); // Store words in current group
        boolean prevLineEmpty = false; // Track consecutive blank lines

        // Read input until end condition (two consecutive blank lines)
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isEmpty()) {
                if (prevLineEmpty) break; // End input if two blank lines
                if (!thisGroup.isEmpty()) {
                    Groups.add(new ArrayList<>(thisGroup)); // Add and reset current group
                    thisGroup.clear();
                }
                prevLineEmpty = true;
            } else {
                thisGroup.add(line); // Add word to group
                prevLineEmpty = false;
            }
        }
        
        // Handle last group
        if (!thisGroup.isEmpty()) {
            Groups.add(thisGroup);
        }

        sc.close(); // Close scanner

        // Process and print each group
        for (int i = 0; i < Groups.size(); i++) {
            if (i > 0) System.out.println(); // Separate groups with a line
            processGroup(Groups.get(i));
        }
    }

    private static void processGroup(List<String> group) {
        // Custom sort based on word endings
        Collections.sort(group, (first, second) -> {
            for (int i = 1; i <= Math.min(first.length(), second.length()); i++) {
                char w1 = first.charAt(first.length() - i);
                char w2 = second.charAt(second.length() - i);
                if (w1 != w2) return w1 - w2; // Sort by character comparison
            }
            return first.length() - second.length(); // Sort by length if endings match
        });

        // Find max length for right-justification
        int maxLength = group.stream().mapToInt(String::length).max().orElse(0);

        // Print words right-justified
        for (String word : group) {
            System.out.printf("%" + maxLength + "s\n", word);
        }
    }
}
