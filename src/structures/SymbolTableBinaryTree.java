package structures;

import structures.BinaryTreeElement;
import structures.SymbolTable;

import java.util.*;

/**
 * Created by dianamohanu on 21/10/2016.
 */
public class SymbolTableBinaryTree implements SymbolTable {
    private static List<BinaryTreeElement> ST = new ArrayList<>();
    private static Integer count = 0;

    @Override
    public void add(String symbol) {
        if (!alreadyInSymbolTable(symbol)) {
            BinaryTreeElement element = new BinaryTreeElement();
            element.setST_pos(count);
            count++;
            element.setSymbol(symbol);
            element.setLeft("-1");
            element.setRight("-1");

            ST.add(element);

            sortST(element);
        }
    }

    private boolean alreadyInSymbolTable(String token) {
        for (BinaryTreeElement element : ST) {
            if (element.getSymbol().equals(token)) {
                return true;
            }
        }
        return false;
    }

    private boolean alreadyUsedLeft(Integer st_pos) {
        for (BinaryTreeElement element : ST) {
            if (element.getLeft() != null) {
                if (element.getLeft().equals(st_pos.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean alreadyUsedRight(Integer st_pos) {
        for (BinaryTreeElement element : ST) {
            if (element.getRight() != null) {
                if (element.getRight().equals(st_pos.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<BinaryTreeElement> getSTCopy() {
        List<BinaryTreeElement> STcopy = new ArrayList<>();
        for (BinaryTreeElement e : ST) {
            STcopy.add(e);
        }

        Collections.sort(STcopy);

        return STcopy;
    }

    private void sortST(BinaryTreeElement element) {
        List<BinaryTreeElement> STcopy = getSTCopy();

        int current = STcopy.indexOf(element);

        List<BinaryTreeElement> elementsWithCurrentAsPossibleLeft = new ArrayList<>();
        for (int i=current+1; i<STcopy.size(); i++) {
            elementsWithCurrentAsPossibleLeft.add(STcopy.get(i));
        }

        List<BinaryTreeElement> elementsWithCurrentAsPossibleRight = new ArrayList<>();
        for (int i=0; i<current; i++) {
            elementsWithCurrentAsPossibleRight.add(STcopy.get(i));
        }
        Collections.reverse(elementsWithCurrentAsPossibleRight);

//        ---------------------------------------------------------------------------------------------------------------

        if (elementsWithCurrentAsPossibleLeft.size() == 0) {
            for (BinaryTreeElement e : elementsWithCurrentAsPossibleRight) {
                if (e.getRight().equals("-1")) {
                    if (!alreadyUsedLeft(element.getST_pos()) && !alreadyUsedRight(element.getST_pos())) {
                        e.setRight(element.getST_pos().toString());
                    }
                }
            }
        }
        else if (elementsWithCurrentAsPossibleRight.size() == 0){
            for (BinaryTreeElement e : elementsWithCurrentAsPossibleLeft) {
                if (e.getLeft().equals("-1")) {
                    if (!alreadyUsedLeft(element.getST_pos()) && !alreadyUsedRight(element.getST_pos())) {
                        e.setLeft(element.getST_pos().toString());
                    }
                }
            }
        }
        else {
            if (elementsWithCurrentAsPossibleLeft.get(0).getST_pos() > elementsWithCurrentAsPossibleRight.get(0).getST_pos()) {
                for (BinaryTreeElement e : elementsWithCurrentAsPossibleLeft) {
                    if (e.getLeft().equals("-1")) {
                        if (!alreadyUsedLeft(element.getST_pos()) && !alreadyUsedRight(element.getST_pos())) {
                            e.setLeft(element.getST_pos().toString());
                        }
                    }
                }
            }
            else {
                for (BinaryTreeElement e : elementsWithCurrentAsPossibleRight) {
                    if (e.getRight().equals("-1")) {
                        if (!alreadyUsedLeft(element.getST_pos()) && !alreadyUsedRight(element.getST_pos())) {
                            e.setRight(element.getST_pos().toString());
                        }
                    }
                }
            }
        }

    }

    @Override
    public String getPosition(String token) {
        for (BinaryTreeElement element : ST) {
            if (element.getSymbol().equals(token)) {
                return element.getST_pos().toString();
            }
        }
        return "-1";
    }

    @Override
    public void printST() {
        System.out.println("ST");
        System.out.format("%5s%10s%10s%10s\n", "ST_pos", "Symbol", "left", "right");
        for (BinaryTreeElement element : ST) {
            System.out.format("%3s%12s%10s%10s\n", element.getST_pos(), element.getSymbol(), element.getLeft(), element.getRight());
        }
    }
}
