import java.util.*;

public class Player{
    boolean isDealer;
    boolean isTurn;
    int playerNum;
    private int playerScore = 0;
    ArrayList<Card> hand;
    ArrayList<Card> playedCards;
    public Player(int n){
        isDealer = false;
        isTurn = false;
        playerNum = n;
        hand = new ArrayList<Card>();
        playedCards = new ArrayList<Card>();
    }
    public void setDealer(){isDealer=true;}
    public void setPlayerNum(int num){playerNum = num;}
    public int getPlayerNum(){return playerNum;}
    public void addToHand(Card card){hand.add(card);}
    public void addPlayerScore(int score) {playerScore += score;}
    public int getPlayerScore(){return playerScore;}
    public ArrayList<Card> getHand(){return hand;}
}


