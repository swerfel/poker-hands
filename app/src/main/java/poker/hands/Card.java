package poker.hands;

public record Card(CardSuit suit, CardValue value) {

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

    @Override
    public String toString() {
        return suit + "(" + value + ')';
    }
}