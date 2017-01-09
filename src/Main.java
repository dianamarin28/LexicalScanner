import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dianamohanu on 20/10/2016.
 */
public class Main {

    public static void main(String[] args) {
        LexicalScanner lexicalScanner = new LexicalScanner();
        lexicalScanner.scanSourceCode();
    }
}
