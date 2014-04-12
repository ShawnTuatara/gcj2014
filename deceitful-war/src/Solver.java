import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solver {
	private static final String PROBLEM = "d-large";

	private List<War> cases = new ArrayList<War>();

	private class War extends Case {
		private BigDecimal[] naomisBlocks;
		private BigDecimal[] kensBlocks;
		private int numberOfBlocks;

		public War(int caseNumber, int numberOfBlocks, BigDecimal[] naomisBlocks, BigDecimal[] kensBlocks) {
			super(caseNumber);
			this.numberOfBlocks = numberOfBlocks;
			this.naomisBlocks = naomisBlocks;
			this.kensBlocks = kensBlocks;
		}

		public int getNumberOfBlocks() {
			return numberOfBlocks;
		}

		public BigDecimal[] getNaomisBlocks() {
			return naomisBlocks;
		}

		public BigDecimal[] getKensBlocks() {
			return kensBlocks;
		}
	}

	private War scanCase(BufferedReader reader, int caseNumber) throws NumberFormatException, IOException {
		int startingBlocks = Integer.parseInt(reader.readLine());

		String[] naomisBlocksAsStrings = reader.readLine().split(" ");
		BigDecimal[] naomisBlocks = convertStringsToBigDecimals(naomisBlocksAsStrings);
		Arrays.sort(naomisBlocks);

		String[] kensBlocksAsStrings = reader.readLine().split(" ");
		BigDecimal[] kensBlocks = convertStringsToBigDecimals(kensBlocksAsStrings);
		Arrays.sort(kensBlocks);

		return new War(caseNumber, startingBlocks, naomisBlocks, kensBlocks);
	}

	private BigDecimal[] convertStringsToBigDecimals(String[] blocksAsStrings) {
		BigDecimal[] blocks = new BigDecimal[blocksAsStrings.length];
		for (int blockIndex = 0; blockIndex < blocksAsStrings.length; blockIndex++) {
			String block = blocksAsStrings[blockIndex];
			blocks[blockIndex] = new BigDecimal(block);
		}
		return blocks;
	}

	// TODO Solve the problem
	public void solve() {
		for (War problemCase : cases) {
			System.out.println(Arrays.toString(problemCase.getNaomisBlocks()));
			System.out.println(Arrays.toString(problemCase.getKensBlocks()));
			System.out.println();

			int numberOfBlocks = problemCase.getNumberOfBlocks();
			BigDecimal[] naomisBlocks = problemCase.getNaomisBlocks();
			BigDecimal[] kensBlocks = problemCase.getKensBlocks();

			int war = war(numberOfBlocks, naomisBlocks, kensBlocks);
			int deceitfulWar = deceitfulWar(numberOfBlocks, naomisBlocks, kensBlocks);

			String solution = deceitfulWar + " " + war;
			problemCase.setSolution(solution);
		}
	}

	private int war(int numberOfBlocks, BigDecimal[] naomisOriginalBlocks, BigDecimal[] kensOriginalBlocks) {
		BigDecimal[] naomisBlocks = Arrays.copyOf(naomisOriginalBlocks, naomisOriginalBlocks.length);
		BigDecimal[] kensBlocks = Arrays.copyOf(kensOriginalBlocks, kensOriginalBlocks.length);

		int kenWins = 0;

		for (int currentBlockIndex = numberOfBlocks - 1; currentBlockIndex >= 0; currentBlockIndex--) {
			if (kensBlocks[currentBlockIndex].compareTo(naomisBlocks[currentBlockIndex]) > 0) {
				kenWins++;
			} else {
				BigDecimal tempBlock = kensBlocks[0];
				System.arraycopy(kensBlocks, 1, kensBlocks, 0, currentBlockIndex);
				kensBlocks[currentBlockIndex] = tempBlock;
			}
		}

		return numberOfBlocks - kenWins;
	}

	private int deceitfulWar(int numberOfBlocks, BigDecimal[] naomisOriginalBlocks, BigDecimal[] kensOriginalBlocks) {
		BigDecimal[] naomisBlocks = Arrays.copyOf(naomisOriginalBlocks, naomisOriginalBlocks.length);
		BigDecimal[] kensBlocks = Arrays.copyOf(kensOriginalBlocks, kensOriginalBlocks.length);

		int naomiWins = 0;

		for (int currentBlockIndex = numberOfBlocks - 1; currentBlockIndex >= 0; currentBlockIndex--) {
			if (naomisBlocks[currentBlockIndex].compareTo(kensBlocks[currentBlockIndex]) > 0) {
				naomiWins++;
			} else {
				BigDecimal tempBlock = naomisBlocks[0];
				System.arraycopy(naomisBlocks, 1, naomisBlocks, 0, currentBlockIndex);
				naomisBlocks[currentBlockIndex] = tempBlock;
			}
		}

		return naomiWins;
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
