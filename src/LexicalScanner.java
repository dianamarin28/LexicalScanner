import structures.CodificationTable;
import structures.ProgramInternalForm;
import structures.SymbolTable;
import structures.SymbolTableBinaryTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by dianamohanu on 21/10/2016.
 */
public class LexicalScanner {
    // input
    private static final CodificationTable codificationTable = new CodificationTable();
    private final List<String> sourceCode = readSourceCode();

    private static int lineNumber = 0;
    // output
    private static ProgramInternalForm PIF = new ProgramInternalForm();
    private static SymbolTable ST = new SymbolTableBinaryTree();

    private static final String delimiters = " ()[]{};,+-*/=<>!&|";

    private List<String> readSourceCode() {
        List<String> sourceCode = new ArrayList<>();

        try {
            File file = new File("sourcecode.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sourceCode.add(line);
            }

            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sourceCode;
    }

    private void scanLine(String line) {
        StringTokenizer tokens = new StringTokenizer(line, delimiters, true);

        List<String> lineTokens = new ArrayList<>();

        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            lineTokens.add(token);
            if (codificationTable.containsToken(token)) {
                if (token.equals(">") || token.equals("<")) {
                    PIF.add(checkIfComposedToken(line, token), ST.getPosition(token));
                }
                if (token.equals("=")) {
                    if (!checkIfAlreadyAdded(line, token)) {
                        PIF.add(codificationTable.getCodificationCode(token), ST.getPosition(token));
                    }
                }
                else {
                    PIF.add(codificationTable.getCodificationCode(token), ST.getPosition(token));
                }
            } else if (codificationTable.isIdentifier(token)) {
                ST.add(token);
                PIF.add(0, ST.getPosition(token));
            } else if (codificationTable.isConstant(token)) {
                String checkConstantSign = checkConstantSign(lineTokens);
                if (token.equals(checkConstantSign)) {
                    ST.add(checkConstantSign);
                    PIF.add(1, ST.getPosition(checkConstantSign));
                }
                else {
                    PIF.remove();
                    ST.add(checkConstantSign);
                    PIF.add(1, ST.getPosition(checkConstantSign));
                }
            }
            else {
                exceptionChecker(token, line);
            }
        }
    }

    private List<String> makeLineToList(String line) {
        StringTokenizer tokens = new StringTokenizer(line, delimiters, true);
        List<String> lineTokens = new ArrayList<>();
        while (tokens.hasMoreTokens()) {
            lineTokens.add(tokens.nextToken());
        }

        return lineTokens;
    }

    private Integer checkIfComposedToken(String line, String token) {
        List<String> lineTokens = makeLineToList(line);

        for (int i=0; i<lineTokens.size(); i++) {
            if (lineTokens.get(i).equals(token)) {
                String afterTokenWithOnePosition = lineTokens.get(i+1);

                if (token.equals("<") && afterTokenWithOnePosition.equals("=")) {
                    return codificationTable.getCodificationCode("<=");
                }
                else if (token.equals(">") && afterTokenWithOnePosition.equals("=")) {
                    return codificationTable.getCodificationCode(">=");
                }
            }
        }

        return codificationTable.getCodificationCode(token);
    }

    private boolean checkIfAlreadyAdded(String line, String token) {
        List<String> lineTokens = makeLineToList(line);

        for (int i=0; i<lineTokens.size(); i++) {
            if (lineTokens.get(i).equals(token)) {
                String beforeTokenWithOnePosition = lineTokens.get(i-1);

                if (beforeTokenWithOnePosition.equals("<") || beforeTokenWithOnePosition.equals(">")) {
                    return true;
                }
            }
        }

        return false;
    }

    private String checkConstantSign(List<String> lineTokens) {
        String constant = lineTokens.get(lineTokens.size()-1);
        String beforeConstantWithOnePosition = lineTokens.get(lineTokens.size()-2);

        if (!beforeConstantWithOnePosition.equals(" ")) {
            String beforeConstantWithTwoPositions = lineTokens.get(lineTokens.size()-3);
            if (beforeConstantWithTwoPositions.equals("=")) {
                return beforeConstantWithOnePosition + constant;
            }
            else if (beforeConstantWithTwoPositions.equals(" ")) {
                String beforeConstantWithThreePositions = lineTokens.get(lineTokens.size()-4);
                if (beforeConstantWithThreePositions.equals("=")) {
                    return beforeConstantWithOnePosition + constant;
                }
            }
        }
        return constant;
    }

    private void exceptionChecker(String token, String line) {
        if (token.length() > 250) {
            System.err.println("ERROR ON LINE " + lineNumber + ": Too long identifier!");
        }
        else if (token.startsWith("'") && token.endsWith("'")) {
            if (token.length() > 3) {
                System.err.println("ERROR ON LINE " + lineNumber + ": Invalid char constant: " + token);
            }
        }
        else if (token.matches("[~`@#$%^]")) {
            System.err.println("ERROR ON LINE " + lineNumber + ": Invalid char! " + token + " does not belong to language alphabet!");
        }
        else if (token.startsWith("\"") && !token.endsWith("\"")) {
            System.err.println("ERROR ON LINE " + lineNumber + ": Invalid string constant: " + token);
        }
        else if (Character.isDigit(token.charAt(0)) || token.matches("[0-9a-zA-Z]+")) {
            System.err.println("ERROR ON LINE " + lineNumber + ": Your identifier " + token + " doesn't match any defined type!");
        }
    }

    public void scanSourceCode() {
        for (String line : sourceCode) {
            lineNumber++;
            scanLine(line);
        }
        PIF.printPIF();
        ST.printST();
    }
}
