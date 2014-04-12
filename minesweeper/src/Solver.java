import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solver {
	private static final String PROBLEM = "c-small";

	private static final char MINE = '*';
	private static final char NO_MINE = '.';
	private static final char CLICK = 'c';

	private List<MinesweeperCase> cases = new ArrayList<MinesweeperCase>();

	private class MinesweeperCase extends Case {
		private int rows;
		private int columns;
		private int numberOfMines;
		private char[][] board;
		private boolean reverse = false;

		public MinesweeperCase(int caseNumber, int rows, int columns, int numberOfMines) {
			super(caseNumber);
			this.numberOfMines = numberOfMines;
			if (columns < rows) {
				reverse = true;
				this.rows = columns;
				this.columns = rows;
			} else {
				this.rows = rows;
				this.columns = columns;
			}
			board = new char[this.rows][this.columns];
			initBoard();
		}

		private void initBoard() {
			for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
				for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
					board[rowIndex][columnIndex] = NO_MINE;
				}
			}
		}

		public int getRows() {
			return rows;
		}

		public int getColumns() {
			return columns;
		}

		public int getNumberOfMines() {
			return numberOfMines;
		}

		public char[][] getBoard() {
			return board;
		}
	}

	private MinesweeperCase scanCase(BufferedReader reader, int caseNumber) throws NumberFormatException, IOException {
		String[] caseSplit = reader.readLine().split(" ");
		return new MinesweeperCase(caseNumber, Integer.parseInt(caseSplit[0]), Integer.parseInt(caseSplit[1]), Integer.parseInt(caseSplit[2]));
	}

	// TODO Solve the problem
	public void solve() {
		for (MinesweeperCase problemCase : cases) {
			char[][] board = problemCase.getBoard();

			int rows = problemCase.getRows();
			int columns = problemCase.getColumns();

			int numberOfMinesRemaining = problemCase.getNumberOfMines();

			int[] rowIndex = new int[2];
			rowIndex[1] = rows - 1;

			boolean top = true;

			if (numberOfMinesRemaining <= columns) {
				for (int mineToPlaceColumnIndex = 0; mineToPlaceColumnIndex < numberOfMinesRemaining; mineToPlaceColumnIndex++) {
					board[top ? rowIndex[0] : rowIndex[1]][mineToPlaceColumnIndex] = MINE;
				}

				board[top ? rowIndex[1] : rowIndex[0]][0] = CLICK;
				problemCase.setSolution(serializeBoard(problemCase));
				continue;
			}

			String solution = null;
			problemCase.setSolution(solution);
		}
	}

	private String serializeBoard(MinesweeperCase problemCase) {
		char[][] board = problemCase.getBoard();

		if (problemCase.reverse) {
			char[][] reversed = new char[problemCase.getColumns()][problemCase.getRows()];
			for (int row = 0; row < board.length; row++) {
				for (int column = 0; column < board[row].length; column++) {
					reversed[column][row] = board[row][column];
				}
			}

			board = reversed;
		}

		StringBuilder solution = new StringBuilder();
		solution.append("\n");
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board[row].length; column++) {
				solution.append(board[row][column]);
			}
			solution.append("\n");
		}

		return solution.toString();
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
