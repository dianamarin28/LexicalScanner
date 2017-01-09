package structures;

import java.util.StringTokenizer;

/**
 * Created by dianamohanu on 21/10/2016.
 */
public class BinaryTreeElement implements Comparable<BinaryTreeElement> {
    private Integer ST_pos;
    private String symbol;
    private String left;
    private String right;

    public Integer getST_pos() {
        return ST_pos;
    }

    public void setST_pos(Integer ST_pos) {
        this.ST_pos = ST_pos;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    @Override
    public int compareTo(BinaryTreeElement o) {
        String delimiters = "'\"";

        StringTokenizer thisTokenWithoutSpecialCharacters = new StringTokenizer(this.getSymbol(), delimiters, false);
        String thisToken = thisTokenWithoutSpecialCharacters.nextToken();

        StringTokenizer tokenWithoutSpecialCharacters = new StringTokenizer(o.getSymbol(), delimiters, false);
        String token = tokenWithoutSpecialCharacters.nextToken();

        return thisToken.compareTo(token);
    }
}
