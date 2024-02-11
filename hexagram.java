import java.util.*;

public class hexagram {

    // Stores the input values for the hexagram puzzle
    private int[] values = new int[12];
    // Tracks which values have been placed in the hexagram
    private boolean[] used = new boolean[12];
    // Counts the number of valid solutions found
    private int count = 0;
    // The sum that each line in the hexagram must equal
    private int target;
    // Stores the number of solutions for each processed puzzle
    private List<Integer> solutionCounts = new ArrayList<>();

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        hexagram solver = new hexagram();
        // Holds the current configuration of the hexagram
        int[] puzzle = new int[12];

        // Process puzzles until a puzzle with all zeros is inputted
        while (stdin.hasNextInt()) {
            // Populate the puzzle array with input values
            for (int i = 0; i < 12; i++) {
                puzzle[i] = stdin.nextInt();
                solver.values[i] = puzzle[i];
            }
            // Exit loop if the input puzzle consists entirely of zeros
            if (zero(puzzle)) {
                // Print all the solution counts except for the terminating puzzle
                for (int count : solver.solutionCounts) {
                    System.out.println(count);
                }
                break;
            }
            // Prepare for a new puzzle by resetting counters and flags
            solver.count = 0;
            Arrays.fill(solver.used, false);
            // Determine the target line sum based on the input values
            solver.target = Arrays.stream(solver.values).sum() / 3;

            // Attempt to solve the puzzle by fixing the first value at different positions
            for (int i = 0; i < 2; i++) {
                solver.used[0] = true;
                puzzle[i] = solver.values[0];
                solver.solvehexagram(puzzle, 1);
                solver.used[0] = false;
            }

            // Store the number of unique solutions (accounting for reflection)
            solver.solutionCounts.add(solver.count / 2);
        }

        stdin.close();
    }

    // Solves the hexagram puzzle using backtracking
    private void solvehexagram(int[] puzzle, int position) {
        // Base case: all positions are filled
        if (position == 12) {
            // Check if the filled hexagram is a valid solution
            if (isSolution(puzzle)) {
                count++;
            }
            return;
        }

        // Attempt to place each unused value in the current position
        for (int i = 0; i < 12; i++) {
            if (!used[i]) {
                used[i] = true;
                puzzle[position] = values[i];
                // Continue only if the current placement doesn't cause a conflict
                if (noConflicts(puzzle, position)) {
                    solvehexagram(puzzle, position + 1);
                }
                // Backtrack if the placement isn't viable
                used[i] = false;
            }
        }
    }

    // Checks for conflicts against the hexagram rules at the current level of recursion
    // Checks for conflicts against the hexagram rules at the current level of recursion
    private boolean noConflicts(int[] puzzle, int level) {
        // Determines if the current partial solution violates the hexagram's line sum rule
        int exact = -1;
        if (level == 4) {
            // Check the sum of the first line when the fourth value is placed
            target = puzzle[0] + puzzle[1] + puzzle[2] + puzzle[3];
        } else if (level == 6) {
            // Check the sum of the second line when the sixth value is placed
            exact = target - puzzle[0] - puzzle[4] - puzzle[5];
            if (exact < 0 || exact != puzzle[6]) return false;
        } else if (level == 8) {
            // Check the sum of the third line when the eighth value is placed
            exact = target - puzzle[3] - puzzle[6] - puzzle[7];
            if (exact < 0 || exact != puzzle[8]) return false;
        } else if (level == 10) {
            // Check the sum of the fourth line when the tenth value is placed
            exact = target - puzzle[9] - puzzle[4] - puzzle[1];
            if (exact < 0 || exact != puzzle[10]) return false;
        } else if (level == 11) {
            // Check the sum of the fifth and sixth lines when the eleventh value is placed
            exact = target - puzzle[9] - puzzle[5] - puzzle[7];
            if (exact < 0 || exact != puzzle[11] || exact != target - puzzle[10] - puzzle[2] - puzzle[8]) return false;
        }
        // Return true if no conflicts are found
        return true;
    }
    
    // Verify if the current configuration is a valid solution
    private boolean isSolution(int[] puzzle) {
        // Check if the sum of each line equals the target sum
    return (puzzle[0] + puzzle[1] + puzzle[2] + puzzle[3] == target) && // Line 1
            (puzzle[0] + puzzle[4] + puzzle[5] + puzzle[6] == target) && // Line 2
            (puzzle[6] + puzzle[7] + puzzle[8] + puzzle[3] == target) && // Line 3
            (puzzle[9] + puzzle[4] + puzzle[1] + puzzle[10] == target) && // Line 4
            (puzzle[9] + puzzle[5] + puzzle[7] + puzzle[11] == target) && // Line 5
            (puzzle[10] + puzzle[2] + puzzle[8] + puzzle[11] == target); // Line 6
    }
    
    // Determine if all values in the puzzle array are zero
    private static boolean zero(int[] puzzle) {
        // Iterate through the puzzle array to check for non-zero values
        for (int num : puzzle) {
            if (num != 0) {
                return false; // Return false if any value is not zero
            }
        }
        return true; // Return true if all values are zero, indicating the end of input
    }
    
}