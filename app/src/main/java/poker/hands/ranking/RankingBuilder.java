package poker.hands.ranking;

import poker.hands.Card;
import poker.hands.CardValue;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Comparator.*;
import static poker.hands.ranking.Rankings.*;

/* exercise hint: this class uses some kind of inlined chain of responsibility
(inlined, because poker rules will never change, so there is no need for ability to extend)
 */
public class RankingBuilder {
    private final List<Card> cards;
    private final CardValue firstValue;
    private final CardValue thirdValue;
    private final CardValue fifthValue;

    public RankingBuilder(Card first, Card second, Card third, Card fourth, Card fifth) {
        this.cards = Stream.of(first, second, third, fourth, fifth)
                .sorted(comparing(Card::value))
                .toList();
        // select rank relevant cards after sorting, so we can work with assumptions that simplify the logic
        this.firstValue = cards.get(0).value();
        this.thirdValue = cards.get(2).value();
        this.fifthValue = cards.get(4).value();
    }

    public Ranking build() {
        return beginWithStraightFlush();
    }

    private Ranking beginWithStraightFlush() {
        if (isStraight() && isFlush())
            return straightFlushWithHighCard(highestValue());

        return continueWithFourOfAKind();
    }

    private Ranking continueWithFourOfAKind() {
        if (valueOccurrenceOf(firstValue) == 4) // case XXXXY
            return fourOfAKind(firstValue);
        else if (valueOccurrenceOf(fifthValue) == 4) // case XYYYY
            return fourOfAKind(fifthValue);

        return continueWithFullHouse();
    }

    private Ranking continueWithFullHouse() {
        long firstOccurrence = valueOccurrenceOf(firstValue);
        long lastOccurrence = valueOccurrenceOf(fifthValue);

        if (firstOccurrence == 2 && lastOccurrence == 3) // case XXYYY
            return fullHouseWithThree(fifthValue);
        else if (firstOccurrence == 3 && lastOccurrence == 2) // case XXXYY
            return fullHouseWithThree(firstValue);

        return continueWithFlush();
    }

    private Ranking continueWithFlush() {
        if (isFlush())
            return flush(valuesDesc());

        return continueWithStraight();
    }

    private Ranking continueWithStraight() {
        if (isStraight())
            return straightWithHighest(highestValue());

        return continueWithThreeOfAKind();
    }

    private Ranking continueWithThreeOfAKind() {
        if (valueOccurrenceOf(firstValue) == 3) // case XXXYZ
            return threeOfAKind(firstValue);
        else if (valueOccurrenceOf(thirdValue) == 3) // case XYYYZ
            return threeOfAKind(thirdValue);
        else if (valueOccurrenceOf(fifthValue) == 3) // case XYZZZ
            return threeOfAKind(fifthValue);

        return continueWithTwoPairs();
    }

    private Ranking continueWithTwoPairs() {
        if (getDistinctValues().size() == 3) { // three of a kind excluded, so two pairs
            if (valueOccurrenceOf(firstValue) == 1) // case YXXZZ
                return twoPairsWithPairsValuesAndRemainingCard(fifthValue, thirdValue, firstValue);
            else if (valueOccurrenceOf(thirdValue) == 1) // case XXYZZ
                return twoPairsWithPairsValuesAndRemainingCard(fifthValue, firstValue, thirdValue);
            else if (valueOccurrenceOf(fifthValue) == 1) // case XXZZY
                return twoPairsWithPairsValuesAndRemainingCard(thirdValue, firstValue, fifthValue);
        }
        return continueWithPair();
    }

    private Ranking continueWithPair() {
        if (valueOccurrenceOf(firstValue) == 2) // case XXABC
            return pairOfWithRemainingCards(firstValue, valuesDescWithout(firstValue));
        else if (valueOccurrenceOf(thirdValue) == 2) // cases AXXBC and ABXXC
            return pairOfWithRemainingCards(thirdValue, valuesDescWithout(thirdValue));
        else if (valueOccurrenceOf(fifthValue) == 2) // case ABCXX
            return pairOfWithRemainingCards(fifthValue, valuesDescWithout(fifthValue));

        return finishWithHighCard();
    }

    private Ranking finishWithHighCard() {
        return highCard(valuesDesc());
    }

    private List<CardValue> valuesDescWithout(CardValue valueToSkip) {
        return valuesDesc().stream().filter(v -> !v.equals(valueToSkip)).toList();
    }

    private List<CardValue> valuesDesc() {
        return cards.stream().map(Card::value).sorted(reverseOrder()).toList();
    }

    private long valueOccurrenceOf(CardValue value) {
        return cards.stream().map(Card::value).filter(v -> v.equals(value)).count();
    }

    private boolean isFlush() {
        return cards.stream().map(Card::suit).distinct().count() == 1;
    }

    private boolean isStraight() {
        boolean hasDuplicates = getDistinctValues().size() != cards.size();
        if (hasDuplicates)
            return false;

        int maxCardDistanceInStraight = 4;
        return (highestValue().ordinal() - lowestValue().ordinal()) == maxCardDistanceInStraight;
    }

    private List<CardValue> getDistinctValues() {
        return cards.stream().map(Card::value).distinct().toList();
    }

    private CardValue highestValue() {
        //noinspection OptionalGetWithoutIsPresent
        return cards.stream().map(Card::value).max(naturalOrder()).get();
    }

    private CardValue lowestValue() {
        //noinspection OptionalGetWithoutIsPresent
        return cards.stream().map(Card::value).min(naturalOrder()).get();
    }
}