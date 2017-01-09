package structures;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by dianamohanu on 20/10/2016.
 */
public class CodificationTable {
    private static final Map<String, Integer> codificationTable = new HashMap<>();

    public CodificationTable() {
        codificationTable.put("identifier", 0);
        codificationTable.put("constant", 1);
        codificationTable.put("main", 2);
        codificationTable.put("int", 3);
        codificationTable.put("char", 4);
        codificationTable.put("string", 5);
        codificationTable.put("array", 6);
        codificationTable.put("var", 7);
        codificationTable.put("read", 8);
        codificationTable.put("write", 9);
        codificationTable.put("(", 10);
        codificationTable.put(")", 11);
        codificationTable.put("{", 12);
        codificationTable.put("}", 13);
        codificationTable.put("[", 14);
        codificationTable.put("]", 15);
        codificationTable.put(";", 16);
        codificationTable.put("if", 17);
        codificationTable.put("else", 18);
        codificationTable.put("while", 19);
        codificationTable.put("+", 20);
        codificationTable.put("-", 21);
        codificationTable.put("*", 22);
        codificationTable.put("/", 23);
        codificationTable.put("<", 24);
        codificationTable.put(">", 25);
        codificationTable.put("=", 26);
        codificationTable.put("!", 27);
        codificationTable.put("&&", 28);
        codificationTable.put("||", 29);
        codificationTable.put(",", 30);
        codificationTable.put("<=", 31);
        codificationTable.put(">=", 32);
    }

    public boolean checkIfIdentifier(String token) {
        if (Character.isDigit(token.charAt(0)) || !token.matches("[0-9a-zA-Z]+")) {
            return false;
        }
        return true;
    }

    public boolean isIdentifier(String token) {
        if (checkIfIdentifier(token) && token.length() <= 250) {
            return true;
        }
        return false;
    }

    public boolean checkIfInteger(String token) {
        if (token.matches("[-+]?[0-9]+")) {
            return true;
        }
        return false;
    }

    public boolean checkIfCharacter(String token) {
        if (token.startsWith("'") && token.endsWith("'")) {
            if (token.length() == 3) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfString(String token) {
        if (token.startsWith("\"") && token.endsWith("\"")) {
            return true;
        }
        return false;
    }

    public boolean isConstant(String token) {
        if (checkIfInteger(token) || checkIfCharacter(token) || checkIfString(token)) {
            return true;
        }
        return false;
    }

    public boolean containsToken(String token) {
        return codificationTable.containsKey(token);
    }

    public Integer getCodificationCode(String token) {
        return codificationTable.get(token);
    }
}
