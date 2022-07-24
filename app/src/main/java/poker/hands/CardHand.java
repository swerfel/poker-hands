package poker.hands;

public class CardHand implements Comparable<CardHand> {
    private final Card[] cards;

    public CardHand(Card first, Card second, Card third, Card fourth, Card fifth) {
        this.cards = new Card[]{first, second, third, fourth, fifth};
    }


    @Override
    public int compareTo(CardHand other) {
        return 0;
    }
}
