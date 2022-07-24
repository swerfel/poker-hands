package poker.hands;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static poker.hands.CardValue.*;

public class CardValueTest {
    @Test
    public void correctValueComparison() {
        assertIsBetween(N2, List.of(), List.of(N3, N4, N5, N6, N7, N8, N9, N10, JACK, QUEEN, KING, ACE));
        assertIsBetween(N3, List.of(N2), List.of(N4, N5, N6, N7, N8, N9, N10, JACK, QUEEN, KING, ACE));
        assertIsBetween(N4, List.of(N2, N3), List.of(N5, N6, N7, N8, N9, N10, JACK, QUEEN, KING, ACE));
        assertIsBetween(N5, List.of(N2, N3, N4), List.of(N6, N7, N8, N9, N10, JACK, QUEEN, KING, ACE));
        assertIsBetween(N6, List.of(N2, N3, N4, N5), List.of(N7, N8, N9, N10, JACK, QUEEN, KING, ACE));
        assertIsBetween(N7, List.of(N2, N3, N4, N5, N6), List.of(N8, N9, N10, JACK, QUEEN, KING, ACE));
        assertIsBetween(N8, List.of(N2, N3, N4, N5, N6, N7), List.of(N9, N10, JACK, QUEEN, KING, ACE));
        assertIsBetween(N9, List.of(N2, N3, N4, N5, N6, N7, N8), List.of(N10, JACK, QUEEN, KING, ACE));
        assertIsBetween(N10, List.of(N2, N3, N4, N5, N6, N7, N8, N9), List.of(JACK, QUEEN, KING, ACE));
        assertIsBetween(JACK, List.of(N2, N3, N4, N5, N6, N7, N8, N9, N10), List.of(QUEEN, KING, ACE));
        assertIsBetween(QUEEN, List.of(N2, N3, N4, N5, N6, N7, N8, N9, N10, JACK), List.of(KING, ACE));
        assertIsBetween(KING, List.of(N2, N3, N4, N5, N6, N7, N8, N9, N10, JACK, QUEEN), List.of(ACE));
        assertIsBetween(ACE, List.of(N2, N3, N4, N5, N6, N7, N8, N9, N10, JACK, QUEEN, KING), List.of());
    }

    private void assertIsBetween(CardValue currentValue, List<CardValue> lowerValues, List<CardValue> higherValues) {
        for (CardValue val : lowerValues) {
            assertThat(currentValue, greaterThan(val));
        }
        for (CardValue val : higherValues) {
            assertThat(currentValue, lessThan(val));
        }
    }
}
