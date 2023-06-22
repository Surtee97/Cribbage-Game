import java.util.*;
class Deck{
    ArrayList<Card> deck;
    public Deck(){
        deck = new ArrayList<Card>();
    }
    public void generateDeck(){
        for(int i=0; i < 52; i++){
            int cardCounter = i;
            if(i<13){
                Card cardToBeAdded = new Card("Spades", cardCounter);
                deck.add(cardToBeAdded);
            }else if(i<26){
                Card cardToBeAdded = new Card("Hearts", cardCounter-13);
                deck.add(cardToBeAdded);
            }else if(i<39){
                Card cardToBeAdded = new Card("Clubs", cardCounter-26);
                deck.add(cardToBeAdded);
            }else{
                Card cardToBeAdded = new Card("Diamonds", cardCounter-39);
                deck.add(cardToBeAdded);
            }
        }
    }
    public void shuffle(){
        Collections.shuffle(deck);
    }
    public Card draw(){
        Card copy = deck.get(0);
        deck.remove(0);
        return copy;
    }
    public Card drawIndexedCard(int index){
        Card copy = deck.get(index);
        deck.remove(index);
        return copy;
    }
    public void transferCard(Card trash, ArrayList<Card> source, ArrayList<Card> dest){
        for(int i = 0; i < source.size(); i++){
            if(source.get(i)==trash){
                dest.add(source.get(i));
                source.remove(i);
            }
        }
    }
}
