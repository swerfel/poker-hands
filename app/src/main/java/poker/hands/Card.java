package poker.hands;

public class Card {
    private final CardSuit suit;
    private final CardValue value;

    public Card(CardSuit suit, CardValue value) {
        this.suit = suit;
        this.value = value;
    }

    public CardValue getValue() {
        return value;
    }

    public CardSuit getSuit() {
        return suit;
    }

    /* convenience methods for generation of cards */
    public static Card clubs(CardValue value) {
        return new Card(CardSuit.CLUBS, value);
    }

    public static Card diamonds(CardValue value) {
        return new Card(CardSuit.DIAMONDS, value);
    }

    public static Card hearts(CardValue value) {
        return new Card(CardSuit.HEARTS, value);
    }

    public static Card spades(CardValue value) {
        return new Card(CardSuit.SPADES, value);
    }
}
