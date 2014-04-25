import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solver {
	private static final String PROBLEM = "a-small";

	// TODO Solve a single case
	private ProblemCase solveCase(BufferedReader reader, int caseNumber) throws NumberFormatException, IOException {
		ProblemCase problemCase = new ProblemCase(caseNumber);

		problemCase.setSolution("");

		return problemCase;
	}

	/*
	 * Do not need to modify anything below here
	 */
	private List<ProblemCase> cases = new ArrayList<ProblemCase>();

	private void solve(String inputFile) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(inputFile));
			int numberOfCases = Integer.parseInt(reader.readLine());
			for (int caseNumber = 1; caseNumber <= numberOfCases; caseNumber++) {
				cases.add(solveCase(reader, caseNumber));
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
		solver.solve(PROBLEM + ".in");
		solver.output(PROBLEM + ".out");
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
