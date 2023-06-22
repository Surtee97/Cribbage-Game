public class Card implements Comparable<Card>{
    String suit;
    int rank;
    Card(String s, int r){
        suit = s;
        rank = r;
    }
    public void setRank(int num){rank = num;}
    public void setSuit(String value){suit = value;}
    public int getRank(){return rank;}
    public String getSuit(){return suit;}
    @Override
    public int compareTo(Card o) {
        int compareRank = ((Card)o).getRank();
        return this.getRank() - compareRank;
    }
}
