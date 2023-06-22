import java.util.*;

public class Crib {
    ArrayList<Card> crib;
    ArrayList<Card> tempCrib;

    public Crib() {
        crib = new ArrayList<Card>();
        tempCrib = crib;
        Collections.copy(tempCrib, crib);
    }

    public ArrayList<Card> getCrib() {
        return crib;
    }

    public ArrayList<Card> getTemp() {
        return tempCrib;
    }

    public void addToCrib(Card card) {
        crib.add(card);
    }

    public void addToTemp(Card card) {
        tempCrib.add(card);
    }
}