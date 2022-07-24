package poker.hands.ranking;

import org.junit.jupiter.api.Test;
import poker.hands.Card;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static poker.hands.Card.*;
import static poker.hands.CardValue.*;
import static poker.hands.ranking.Ranking.*;

public class RankingBuilderTest {

    private Ranking rankingFor(Card first, Card second, Card third, Card fourth, Card fifth) {
        return new RankingBuilder(first, second, third, fourth, fifth).build();
    }

    @Test
    void testStraightFlush() {
        assertEquals(straightFlushWithHighCard(N7), rankingFor(clubs(N3), clubs(N6), clubs(N4), clubs(N5), clubs(N7)));
        assertEquals(straightFlushWithHighCard(N8), rankingFor(clubs(N8), clubs(N6), clubs(N4), clubs(N5), clubs(N7)));
        assertEquals(straightFlushWithHighCard(N8), rankingFor(diamonds(N8), diamonds(N6), diamonds(N4), diamonds(N5), diamonds(N7)));
        assertEquals(straightFlushWithHighCard(ACE), rankingFor(hearts(N10), hearts(JACK), hearts(QUEEN), hearts(KING), hearts(ACE)));
    }

    @Test
    void testFourOfAKind() {
        assertEquals(fourOfAKind(N7), rankingFor(clubs(N3), diamonds(N7), spades(N7), hearts(N7), clubs(N7)));
        assertEquals(fourOfAKind(N7), rankingFor(clubs(ACE), diamonds(N7), spades(N7), hearts(N7), clubs(N7)));
        assertEquals(fourOfAKind(ACE), rankingFor(diamonds(ACE), diamonds(N7), spades(ACE), hearts(ACE), clubs(ACE)));
        assertEquals(fourOfAKind(QUEEN), rankingFor(hearts(QUEEN), diamonds(QUEEN), spades(ACE), spades(QUEEN), clubs(QUEEN)));
        assertEquals(fourOfAKind(N3), rankingFor(spades(N3), diamonds(N3), hearts(N3), clubs(N2), clubs(N3)));
        assertEquals(fourOfAKind(N6), rankingFor(clubs(N6), hearts(N6), diamonds(N6), clubs(N6), diamonds(N3)));
    }

    @Test
    void testFullHouse() {
        assertEquals(fullHouseWithThree(N7), rankingFor(clubs(N3), diamonds(N3), spades(N7), hearts(N7), clubs(N7)));
        assertEquals(fullHouseWithThree(N3), rankingFor(clubs(N3), diamonds(N3), spades(N3), hearts(N7), clubs(N7)));
        assertEquals(fullHouseWithThree(KING), rankingFor(diamonds(KING), diamonds(QUEEN), spades(KING), hearts(QUEEN), clubs(KING)));
    }

    @Test
    void testFlush() {
        assertEquals(flush(List.of(ACE, JACK, N10, N3, N2)), rankingFor(clubs(N3), clubs(N2), clubs(JACK), clubs(N10), clubs(ACE)));
        assertEquals(flush(List.of(KING, QUEEN, JACK, N10, N8)), rankingFor(diamonds(KING), diamonds(QUEEN), diamonds(JACK), diamonds(N10), diamonds(N8)));
        assertEquals(flush(List.of(KING, QUEEN, JACK, N10, N8)), rankingFor(diamonds(N8), diamonds(N10), diamonds(JACK), diamonds(QUEEN), diamonds(KING)));
    }

    @Test
    void testStraight() {
        assertEquals(straightWithHighest(ACE), rankingFor(clubs(ACE), clubs(QUEEN), clubs(JACK), clubs(KING), diamonds(N10)));
        assertEquals(straightWithHighest(N6), rankingFor(spades(N2), diamonds(N6), hearts(N3), clubs(N5), diamonds(N4)));
    }

    @Test
    void testThreeOfAKind() {
        assertEquals(threeOfAKind(ACE), rankingFor(spades(ACE), clubs(ACE), clubs(JACK), clubs(KING), diamonds(ACE)));
        assertEquals(threeOfAKind(N3), rankingFor(spades(N3), diamonds(N6), hearts(N3), clubs(N3), diamonds(N4)));
    }

    @Test
    void testTwoPairs() {
        assertEquals(twoPairsWithPairsValuesAndRemainingCard(ACE, KING, QUEEN), rankingFor(spades(ACE), clubs(ACE), clubs(KING), clubs(QUEEN), diamonds(KING)));
        assertEquals(twoPairsWithPairsValuesAndRemainingCard(N4, N2, ACE), rankingFor(clubs(ACE), diamonds(N2), hearts(N4), clubs(N2), diamonds(N4)));
    }

    @Test
    void testPair() {
        assertEquals(pairOfWithRemainingCards(ACE, List.of(N4,N3,N2)), rankingFor(spades(ACE), clubs(ACE), clubs(N3), clubs(N4), diamonds(N2)));
        assertEquals(pairOfWithRemainingCards(N6, List.of(ACE,N10,N3)), rankingFor(clubs(N6), diamonds(N10), spades(ACE), hearts(N6), clubs(N3)));
    }

    @Test
    void testHighCard() {
        assertEquals(highCard(List.of(N7, N6, N4,N3,N2)), rankingFor(spades(N3), clubs(N2), clubs(N6), clubs(N4), diamonds(N7)));
        assertEquals(highCard(List.of(ACE,N10,N5, N4, N3)), rankingFor(clubs(N5), diamonds(N10), spades(ACE), hearts(N3), clubs(N4)));
    }


}
