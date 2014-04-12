import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Solver {
	private static BigDecimal STANDARD_INCOME = new BigDecimal("2.0");
	private static String PROBLEM = "b-large";

	private List<CookieClickerInput> cases = new ArrayList<CookieClickerInput>();

	private class CookieClickerInput extends Case {
		private final BigDecimal farmCost;
		private final BigDecimal farmIncome;
		private final BigDecimal targetCookies;

		public CookieClickerInput(int caseNumber, BigDecimal farmCost, BigDecimal farmIncome, BigDecimal targetCookies) {
			super(caseNumber);
			this.farmCost = farmCost;
			this.farmIncome = farmIncome;
			this.targetCookies = targetCookies;
		}

		public BigDecimal getFarmCost() {
			return farmCost;
		}

		public BigDecimal getFarmIncome() {
			return farmIncome;
		}

		public BigDecimal getTargetCookies() {
			return targetCookies;
		}
	}

	private CookieClickerInput scanCase(BufferedReader reader, int caseNumber) throws NumberFormatException, IOException {
		String[] caseInput = reader.readLine().split(" ");
		CookieClickerInput cookieClickerInput = new CookieClickerInput(caseNumber, new BigDecimal(caseInput[0]), new BigDecimal(caseInput[1]), new BigDecimal(
				caseInput[2]));
		return cookieClickerInput;
	}

	public void solve() {
		for (CookieClickerInput problemCase : cases) {
			BigDecimal currentTime = new BigDecimal(0);
			BigDecimal currentIncome = STANDARD_INCOME;

			BigDecimal totalTime = howLongToReachTarget(problemCase, currentTime, currentIncome, problemCase.getTargetCookies());

			String solution = totalTime.setScale(7, RoundingMode.HALF_DOWN).toString();
			problemCase.setSolution(solution);
		}
	}

	// Current cookies is always 0 after buying a farm
	private BigDecimal howLongToReachTarget(CookieClickerInput problemCase, BigDecimal currentTime, BigDecimal currentIncome,
			BigDecimal previousTotalTimeToReachTarget) {
		BigDecimal timeToGetRemainingCookies = problemCase.getTargetCookies().divide(currentIncome, 10, RoundingMode.HALF_UP);
		BigDecimal totalTimeToReachTarget = currentTime.add(timeToGetRemainingCookies);
		if (totalTimeToReachTarget.compareTo(previousTotalTimeToReachTarget) > 0) {
			return previousTotalTimeToReachTarget;
		}

		BigDecimal timeToBuildFarm = problemCase.getFarmCost().divide(currentIncome, 10, RoundingMode.HALF_UP);
		currentTime = currentTime.add(timeToBuildFarm);
		currentIncome = currentIncome.add(problemCase.getFarmIncome());

		return howLongToReachTarget(problemCase, currentTime, currentIncome, totalTimeToReachTarget);
	}

	/*
	 * Do not need to modify anything below here
	 */
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

	private void output(String outputFile) {
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
			for (Case problemCase : cases) {
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
		solver.scanInput(PROBLEM + ".in");
		solver.solve();
		solver.output(PROBLEM + ".out");
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
