import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MagicTrick {
    private static final String BAD_MAGICIAN = "!";
    private static final String VOLUNTEER_CHEATED = "*";
    private String inputFile;
    private String outputFile;

    private List<Case> cases = new ArrayList<Case>();

    public MagicTrick(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        scanInput();
    }

    private void scanInput() {
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

    private Case scanCase(BufferedReader reader, int caseNumber) throws NumberFormatException, IOException {
        int firstAnswer = Integer.parseInt(reader.readLine());
        String[][] cards = getCards(reader);
        String[] firstAnswerCards = cards[firstAnswer - 1];
        int secondAnswer = Integer.parseInt(reader.readLine());
        cards = getCards(reader);
        String[] secondAnswerCards = cards[secondAnswer - 1];

        return new Case(caseNumber, firstAnswerCards, secondAnswerCards);
    }

    private String[][] getCards(BufferedReader reader) throws IOException {
        String[][] cards = new String[4][4];
        cards[0] = reader.readLine().split(" ");
        cards[1] = reader.readLine().split(" ");
        cards[2] = reader.readLine().split(" ");
        cards[3] = reader.readLine().split(" ");
        return cards;
    }

    public void solve() {
        for (Case problemCase : cases) {
            String[] getFirstAnswerCards = problemCase.getGetFirstAnswerCards();
            String[] secondAnswerCards = problemCase.getSecondAnswerCards();
            Arrays.sort(secondAnswerCards);
            String foundCard = VOLUNTEER_CHEATED;
            for (String card : getFirstAnswerCards) {
                if (Arrays.binarySearch(secondAnswerCards, card) >= 0) {
                    if (foundCard != VOLUNTEER_CHEATED) {
                        foundCard = BAD_MAGICIAN;
                        break;
                    } else {
                        foundCard = card;
                    }
                }
            }
            problemCase.setSolution(foundCard);
        }
    }

    private void output() {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
            for (Case problemCase : cases) {
                String solution = problemCase.getSolution();

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Case #").append(problemCase.getCaseNumber()).append(": ");
                if (BAD_MAGICIAN.equals(solution)) {
                    stringBuilder.append("Bad magician!");
                } else if (VOLUNTEER_CHEATED.equals(solution)) {
                    stringBuilder.append("Volunteer cheated!");
                } else {
                    stringBuilder.append(solution);
                }

                String outputLine = stringBuilder.toString();
                bufferedWriter.write(outputLine);
                bufferedWriter.newLine();
                System.out.println(outputLine);
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
        MagicTrick magicTrick = new MagicTrick("a-small.in", "a-small.out");
        magicTrick.solve();
        magicTrick.output();
    }

    public class Case {
        private int caseNumber;
        private String[] firstAnswerCards;
        private String[] secondAnswerCards;
        private String foundCard;

        public Case(int caseNumber, String[] firstAnswerCards, String[] secondAnswerCards) {
            this.caseNumber = caseNumber;
            this.firstAnswerCards = firstAnswerCards;
            this.secondAnswerCards = secondAnswerCards;
        }

        public int getCaseNumber() {
            return caseNumber;
        }

        public String getSolution() {
            return foundCard;
        }

        public void setSolution(String foundCard) {
            this.foundCard = foundCard;
        }

        public String[] getSecondAnswerCards() {
            return secondAnswerCards;
        }

        public String[] getGetFirstAnswerCards() {
            return firstAnswerCards;
        }
    }
}
