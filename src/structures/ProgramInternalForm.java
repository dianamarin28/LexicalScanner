package structures;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dianamohanu on 20/10/2016.
 */
public class ProgramInternalForm {
    private static List<Pair<Integer, String>> PIF = new ArrayList<>();

    public void add(Integer token_code, String ST_pos) {
        PIF.add(new Pair<>(token_code, ST_pos));
    }

    public void remove() {
        PIF.remove(PIF.size()-1);
    }

    public void printPIF() {
        System.out.println("PIF");
        System.out.format("%5s%10s\n", "token_code", "ST_pos");
        for (Pair p : PIF) {
            System.out.format("%5s%13s\n", p.getKey(), p.getValue());
        }
    }
}
