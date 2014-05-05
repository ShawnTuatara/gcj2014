import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solver {
    private static final String PROBLEM = "a-small";

    // TODO Solve a single case
    private ProblemCase solveCase(Scanner scanner, int caseNumber) {
        ProblemCase problemCase = new ProblemCase(caseNumber);

        problemCase.setSolution("");

        return problemCase;
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
