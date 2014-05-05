import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solver {
    private static final String PROBLEM = "a-large";

    private class CharacterInfo {

        public char character;
        public int count;

        public CharacterInfo(char character, int count) {
            this.character = character;
            this.count = count;
        }
    }

    // TODO Solve a single case
    private ProblemCase solveCase(Scanner scanner, int caseNumber) {
        ProblemCase problemCase = new ProblemCase(caseNumber);

        int numberOfStrings = scanner.nextInt();
        scanner.nextLine();

        List<List<CharacterInfo>> strings = new ArrayList<List<CharacterInfo>>();

        for (int stringIndex = 0; stringIndex < numberOfStrings; stringIndex++) {
            String currentString = scanner.nextLine();
            strings.add(parseString(currentString));
        }

        List<CharacterInfo> characterSequence = strings.get(0);

        int totalMoves = 0;
        for (int characterSequenceIndex = 0; characterSequenceIndex < characterSequence.size(); characterSequenceIndex++) {
            int totalCount = 0;
            for (List<CharacterInfo> string : strings) {
                if (characterSequence.size() != string.size()
                        || characterSequence.get(characterSequenceIndex).character != string.get(characterSequenceIndex).character) {
                    totalMoves = -1;
                    break;
                }
                totalCount += string.get(characterSequenceIndex).count;
            }

            if (totalMoves == -1) {
                break;
            }

            int minMoves = Integer.MAX_VALUE;
            for (int charCount = 1; charCount <= 100; charCount++) {
                int tempMoves = 0;
                for (List<CharacterInfo> string : strings) {
                    tempMoves += Math.abs(string.get(characterSequenceIndex).count - charCount);
                }
                minMoves = Math.min(minMoves, tempMoves);
            }
            totalMoves += minMoves;

            // long avg = Math.round(totalCount / (double) strings.size());
            //
            // for (List<CharacterInfo> string : strings) {
            // totalMoves += Math.abs(string.get(characterSequenceIndex).count - avg);
            // }
        }

        if (totalMoves == -1) {
            problemCase.setSolution("Fegla Won");
        } else {
            problemCase.setSolution(Integer.toString(totalMoves));
        }

        return problemCase;
    }

    private List<CharacterInfo> parseString(String currentString) {
        char previousChar = currentString.charAt(0);
        int charCount = 1;
        List<CharacterInfo> characterInfo = new ArrayList<CharacterInfo>();
        for (int charIndex = 1; charIndex < currentString.length(); charIndex++) {
            char currentChar = currentString.charAt(charIndex);
            if (currentChar != previousChar) {
                characterInfo.add(new CharacterInfo(previousChar, charCount));
                charCount = 0;
            }

            previousChar = currentChar;
            charCount++;
        }

        characterInfo.add(new CharacterInfo(previousChar, charCount));
        return characterInfo;
    }

    /*
     * Do not need to modify anything below here
     */
    private List<ProblemCase> cases = new ArrayList<ProblemCase>();

    private void solve(String inputFile) throws FileNotFoundException {
        Scanner scanner = null;
        scanner = new Scanner(new File(inputFile));
        int numberOfCases = scanner.nextInt();
        for (int caseNumber = 1; caseNumber <= numberOfCases; caseNumber++) {
            cases.add(solveCase(scanner, caseNumber));
        }
    }

    private void output(String outputFile) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
            for (ProblemCase problemCase : cases) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Case #").append(problemCase.getCaseNumber()).append(": ").append(problemCase.getSolution());
                String caseOutput = stringBuilder.toString();

                bufferedWriter.write(caseOutput);
                bufferedWriter.newLine();
                System.out.println(caseOutput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Solver solver = new Solver();
        try {
            solver.solve(PROBLEM + ".in");
            solver.output(PROBLEM + ".out");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public class ProblemCase {
        private final int caseNumber;

        private String solution;

        public ProblemCase(int caseNumber) {
            this.caseNumber = caseNumber;
        }

        public int getCaseNumber() {
            return caseNumber;
        }

        public void setSolution(String solution) {
            this.solution = solution;
        }

        public String getSolution() {
            return solution;
        }
    }
}
