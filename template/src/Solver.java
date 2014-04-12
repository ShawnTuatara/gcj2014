import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solver {
	private List<Case> cases = new ArrayList<Case>();

	public Solver() {
	}

	private void scanInput(String inputFile) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(inputFile));
			int numberOfCases = Integer.parseInt(reader.readLine());
			for (int caseNumber = 1; caseNumber <= numberOfCases; caseNumber++) {
				cases.add(scanCase(reader, caseNumber));
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

	private Case scanCase(BufferedReader reader, int caseNumber)
			throws NumberFormatException, IOException {
		// TODO Scan in a single case
		return new Case(caseNumber);
	}

	public void solve() {
		for (Case problemCase : cases) {
			// TODO Solve the problem
			String solution = null;
			problemCase.setSolution(solution);
		}
	}

	private void output(String outputFile) {
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
			for (Case problemCase : cases) {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("Case #")
						.append(problemCase.getCaseNumber()).append(": ")
						.append(problemCase.getSolution());
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
		solver.scanInput("a-small.in");
		solver.solve();
		solver.output("a-small.out");
	}

	public class Case {
		private final int caseNumber;

		private String solution;

		public Case(int caseNumber) {
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
