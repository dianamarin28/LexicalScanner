package structures;

/**
 * Created by dianamohanu on 20/10/2016.
 */
public interface SymbolTable {

    void add(String symbol);

    void printST();

    String getPosition(String token);
}
