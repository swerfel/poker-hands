package poker.hands;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static poker.hands.Card.*;
import static poker.hands.CardValue.*;

public class CardHandTest {
    @Test
    void testCompareTo_differentCategories() {
        assertThat(new CardHand(clubs(N2), clubs(N3), clubs(N4), clubs(N5), clubs(N6)), // straight flush
                greaterThan(new CardHand(diamonds(N2), clubs(N3), clubs(N4), clubs(N5), clubs(N6)))); // straight
        assertThat(new CardHand(clubs(N7), clubs(N3), clubs(N4), clubs(N5), clubs(N6)), // straight flush
                greaterThan(new CardHand(clubs(N2), clubs(N3), clubs(N4), clubs(N5), clubs(ACE)))); // flush

        assertThat(new CardHand(clubs(N7), clubs(N8), clubs(N4), clubs(N5), clubs(N6)), // straight flush
                greaterThan(new CardHand(clubs(KING), diamonds(ACE), spades(ACE), hearts(ACE), clubs(ACE)))); // four of a kind
        assertThat(new CardHand(clubs(N2), diamonds(N2), spades(N2), hearts(N2), clubs(N3)), // four of a kind
                greaterThan(new CardHand(clubs(KING), diamonds(KING), spades(ACE), hearts(ACE), clubs(ACE)))); // full house
        assertThat(new CardHand(clubs(N2), diamonds(N2), spades(N2), hearts(N3), clubs(N3)), // full house
                greaterThan(new CardHand(clubs(JACK), clubs(QUEEN), clubs(N9), clubs(KING), clubs(ACE)))); // flush
        assertThat(new CardHand(diamonds(N2), diamonds(N4), diamonds(N3), diamonds(N5), diamonds(N7)), // flush
                greaterThan(new CardHand(clubs(JACK), clubs(QUEEN), diamonds(N10), clubs(KING), clubs(ACE)))); // straight
        assertThat(new CardHand(diamonds(N2), diamonds(N4), diamonds(N3), diamonds(N5), hearts(N6)), // straight
                greaterThan(new CardHand(clubs(ACE), hearts(ACE), diamonds(ACE), clubs(KING), clubs(QUEEN)))); // three of a kind
        assertThat(new CardHand(diamonds(N2), spades(N2), clubs(N2), diamonds(N3), hearts(N4)), // three of a kind
                greaterThan(new CardHand(clubs(ACE), hearts(ACE), diamonds(KING), clubs(KING), clubs(QUEEN)))); // two pair
        assertThat(new CardHand(diamonds(N2), spades(N2), clubs(N3), diamonds(N3), hearts(N4)), // two pair
                greaterThan(new CardHand(clubs(ACE), hearts(ACE), diamonds(KING), clubs(JACK), clubs(QUEEN)))); // pair
        assertThat(new CardHand(diamonds(N2), spades(N2), clubs(N3), diamonds(N5), hearts(N4)), // pair
                greaterThan(new CardHand(clubs(ACE), hearts(N9), diamonds(KING), clubs(JACK), clubs(QUEEN)))); // high card
    }

    @Test
    void testCompareTo_highCard() {
        // first high card: ace
        assertThat(new CardHand(diamonds(N2), spades(N3), clubs(ACE), diamonds(N5), hearts(N4)),
                greaterThan(new CardHand(clubs(JACK), hearts(KING), diamonds(N10), clubs(N8), clubs(QUEEN))));
        // second high card: jack, after king
        assertThat(new CardHand(diamonds(N2), spades(KING), clubs(N6), diamonds(N5), hearts(JACK)),
                greaterThan(new CardHand(clubs(N9), hearts(KING), diamonds(N10), clubs(N8), clubs(N5))));
        // third high card: 10, after ace, queen
        assertThat(new CardHand(diamonds(ACE), spades(QUEEN), clubs(N10), diamonds(N5), hearts(N2)),
                greaterThan(new CardHand(clubs(N9), hearts(QUEEN), diamonds(ACE), clubs(N8), clubs(N5))));
        // third high card: 9, after KING, JACK, 10
        assertThat(new CardHand(diamonds(N3), spades(JACK), clubs(N10), diamonds(KING), hearts(N9)),
                greaterThan(new CardHand(clubs(KING), hearts(N10), diamonds(JACK), clubs(N8), clubs(N5))));
        // third high card: 4, after ACE, JACK, 8, 7
        assertThat(new CardHand(diamonds(ACE), spades(N7), clubs(N4), diamonds(N8), hearts(JACK)),
                greaterThan(new CardHand(clubs(N7), hearts(N8), diamonds(JACK), clubs(ACE), clubs(N2))));
    }

    @Test
    void testCompareTo_pair() {
        // highest pair: 3
        assertThat(new CardHand(diamonds(N2), spades(N3), clubs(N3), diamonds(N4), hearts(N5)),
                greaterThan(new CardHand(clubs(N2), hearts(N2), diamonds(JACK), clubs(ACE), clubs(KING))));
        // first remaining card: ace
        assertThat(new CardHand(diamonds(N2), spades(N3), clubs(N3), diamonds(N4), hearts(ACE)),
                greaterThan(new CardHand(hearts(N3), diamonds(N3), diamonds(JACK), clubs(QUEEN), clubs(KING))));
        // second remaining card: jack, after king
        assertThat(new CardHand(diamonds(KING), spades(N3), clubs(N3), diamonds(N4), hearts(JACK)),
                greaterThan(new CardHand(hearts(N3), diamonds(N3), diamonds(N10), clubs(KING), clubs(N9))));
        // third remaining card: 10, after ace, queen
        assertThat(new CardHand(diamonds(ACE), spades(N3), clubs(N10), diamonds(QUEEN), hearts(ACE)),
                greaterThan(new CardHand(hearts(N3), diamonds(N3), diamonds(QUEEN), clubs(ACE), clubs(N9))));
    }

    @Test
    void testCompareTo_twoPair() {
        // highest pair: 5
        assertThat(new CardHand(diamonds(N2), spades(N2), clubs(N5), diamonds(N4), hearts(N5)),
                greaterThan(new CardHand(clubs(N3), hearts(N3), diamonds(N4), clubs(N4), clubs(KING))));
        // second-highest pair: 3 after 6
        assertThat(new CardHand(diamonds(N6), spades(N6), clubs(N3), diamonds(N3), hearts(N2)),
                greaterThan(new CardHand(hearts(N6), diamonds(N6), diamonds(N2), clubs(N2), clubs(ACE))));
        // highest remaining card: 7 after King and Ace pairs
        assertThat(new CardHand(diamonds(KING), spades(KING), clubs(ACE), diamonds(ACE), hearts(N7)),
                greaterThan(new CardHand(hearts(N6), hearts(KING), diamonds(KING), clubs(ACE), spades(ACE))));
    }

    @Test
    void testCompareTo_threeOfAKind() {
        // highest triple: 5
        assertThat(new CardHand(diamonds(N5), spades(N2), clubs(N5), diamonds(N4), hearts(N5)),
                greaterThan(new CardHand(clubs(ACE), hearts(N4), diamonds(N4), spades(N4), clubs(KING))));

        // after three of a kind the highest card rule doesn't apply, because with 4 suits and each card once, there cannot be 6 cards with same value
    }

    @Test
    void testCompareTo_straight() {
        // highest straight: 7
        assertThat(new CardHand(diamonds(N3), spades(N4), clubs(N5), diamonds(N6), hearts(N7)),
                greaterThan(new CardHand(clubs(N2), hearts(N3), diamonds(N4), spades(N5), clubs(N6))));
    }

    @Test
    void testCompareTo_flush() {
        // first high card: ace
        assertThat(new CardHand(diamonds(N2), diamonds(N3), diamonds(ACE), diamonds(N5), diamonds(N4)),
                greaterThan(new CardHand(clubs(JACK), clubs(KING), clubs(N10), clubs(N8), clubs(QUEEN))));
        // second high card: jack, after king
        assertThat(new CardHand(diamonds(N2), diamonds(KING), diamonds(N6), diamonds(N5), diamonds(JACK)),
                greaterThan(new CardHand(hearts(N9), hearts(KING), hearts(N10), hearts(N8), hearts(N5))));
        // third high card: 10, after ace, queen
        assertThat(new CardHand(spades(ACE), spades(QUEEN), spades(N10), spades(N5), spades(N2)),
                greaterThan(new CardHand(diamonds(N9), diamonds(QUEEN), diamonds(ACE), diamonds(N8), diamonds(N5))));
        // third high card: 9, after KING, JACK, 10
        assertThat(new CardHand(clubs(N3), clubs(JACK), clubs(N10), clubs(KING), clubs(N9)),
                greaterThan(new CardHand(spades(KING), spades(N10), spades(JACK), spades(N8), spades(N5))));
        // third high card: 4, after ACE, JACK, 8, 7
        assertThat(new CardHand(hearts(ACE), hearts(N7), hearts(N4), hearts(N8), hearts(JACK)),
                greaterThan(new CardHand(diamonds(N7), diamonds(N8), diamonds(JACK), diamonds(ACE), diamonds(N2))));
    }

    @Test
    void testCompareTo_fullHouse() {
        // highest triple: 5
        assertThat(new CardHand(diamonds(N5), spades(N2), clubs(N5), diamonds(N2), hearts(N5)),
                greaterThan(new CardHand(clubs(ACE), hearts(N4), diamonds(N4), spades(N4), clubs(ACE))));

        // after three of a kind the highest card rule doesn't apply, because with 4 suits and each card once, there cannot be 6 cards with same value
    }

    @Test
    void testCompareTo_fourOfAKind() {
        // highest four: 6
        assertThat(new CardHand(diamonds(N6), spades(N6), clubs(N6), diamonds(N4), hearts(N6)),
                greaterThan(new CardHand(clubs(ACE), hearts(N4), diamonds(N4), spades(N4), clubs(N4))));

        // after three of a kind the highest card rule doesn't apply, because with 4 suits and each card once, there cannot be 6 cards with same value
    }

    @Test
    void testCompareTo_straightFlush() {
        // highest straight: 7
        assertThat(new CardHand(diamonds(N3), diamonds(N4), diamonds(N5), diamonds(N6), diamonds(N7)),
                greaterThan(new CardHand(spades(N2), spades(N3), spades(N4), spades(N5), spades(N6))));
    }
}
