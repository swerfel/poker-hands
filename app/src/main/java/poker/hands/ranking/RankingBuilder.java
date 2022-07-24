package poker.hands.ranking;

import poker.hands.Card;
import poker.hands.CardValue;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Comparator.naturalOrder;
import static poker.hands.ranking.Ranking.*;

public class RankingBuilder {
    private final List<Card> cards;
    private final CardValue firstValue;
    private final CardValue thirdValue;
    private final CardValue fifthValue;

    public RankingBuilder(Card first, Card second, Card third, Card fourth, Card fifth) {

        this.cards = Stream.of(first, second, third, fourth, fifth)
                .sorted(Comparator.comparing(Card::getValue))
                .toList();
        // select rank relevant cards after sorting, so we can work with assumptions that simplify the logic
        this.firstValue = cards.get(0).getValue();
        this.thirdValue = cards.get(2).getValue();
        this.fifthValue = cards.get(4).getValue();
    }

    public Ranking build() {
        List<Supplier<Optional<Ranking>>> factories = List.of(
                this::tryAsStraightFlush,
                this::tryAsFourOfAKind,
                this::tryAsFullHouse,
                this::tryAsFlush,
                this::tryAsStraight,
                this::tryAsThreeOfAKind,
                this::tryAsTwoPairs,
                this::tryAsPair);

        for (var factory : factories) {
            Optional<Ranking> ranking = factory.get();
            if (ranking.isPresent())
                return ranking.get();
        }
        return createHighCard();
    }

    private Ranking createHighCard() {
        return highCard(valuesDesc());
    }

    private Optional<Ranking> tryAsPair() {
        // assert higher ranks excluded
        // possible cases: ABCXX, ABXXC, AXXBC, XXABC
        if (valueOccurrencesOf(firstValue) == 2)
            return Optional.of(pairOfWithRemainingCards(firstValue, valuesDescWithout(firstValue)));
        else if (valueOccurrencesOf(thirdValue) == 2)
            return Optional.of(pairOfWithRemainingCards(thirdValue, valuesDescWithout(thirdValue)));
        else if (valueOccurrencesOf(fifthValue) == 2)
            return Optional.of(pairOfWithRemainingCards(fifthValue, valuesDescWithout(fifthValue)));
        else
            return Optional.empty();
    }

    private List<CardValue> valuesDescWithout(CardValue valueToSkip) {
        return valuesDesc().stream().filter(v -> !v.equals(valueToSkip)).toList();
    }

    private List<CardValue> valuesDesc() {
        return cards.stream().map(Card::getValue).sorted(Comparator.reverseOrder()).toList();
    }

    private Optional<Ranking> tryAsTwoPairs() {
        // assert higher ranks excluded
        if (getDistinctValues().size() == 3) { // three of a kind excluded, so two pairs
            // possible cases: YXXZZ, XXYZZ, XXZZY
            if (valueOccurrencesOf(firstValue) == 1)
                return Optional.of(twoPairsWithPairsValuesAndRemainingCard(fifthValue, thirdValue, firstValue));
            else if (valueOccurrencesOf(thirdValue) == 1)
                return Optional.of(twoPairsWithPairsValuesAndRemainingCard(fifthValue, firstValue, thirdValue));
            else if (valueOccurrencesOf(fifthValue) == 1)
                return Optional.of(twoPairsWithPairsValuesAndRemainingCard(thirdValue, firstValue, fifthValue));
        }
        return Optional.empty();
    }

    private Optional<Ranking> tryAsThreeOfAKind() {
        // assert higher ranks excluded
        // possible cases: XXXYZ, XYYYZ or XYZZZ
        if (valueOccurrencesOf(firstValue) == 3)
            return Optional.of(threeOfAKind(firstValue));
        else if (valueOccurrencesOf(thirdValue) == 3)
            return Optional.of(threeOfAKind(thirdValue));
        else if (valueOccurrencesOf(fifthValue) == 3)
            return Optional.of(threeOfAKind(fifthValue));
        else
            return Optional.empty();
    }

    private Optional<Ranking> tryAsStraight() {
        if (isStraight())
            return Optional.of(straightWithHighest(highestValue()));
        else
            return Optional.empty();
    }


    private Optional<Ranking> tryAsFlush() {
        if (isFlush())
            return Optional.of(flush(valuesDesc()));
        else
            return Optional.empty();
    }

    private Optional<Ranking> tryAsFullHouse() {
        // possible cases: XXYYY or XXXYY
        long firstOccurrence = valueOccurrencesOf(firstValue);
        long lastOccurrence = valueOccurrencesOf(fifthValue);

        if (firstOccurrence == 2 && lastOccurrence == 3)
            return Optional.of(fullHouseWithThree(fifthValue));
        else if (firstOccurrence == 3 && lastOccurrence == 2)
            return Optional.of(fullHouseWithThree(firstValue));
        else
            return Optional.empty();
    }

    private Optional<Ranking> tryAsFourOfAKind() {
        // possible cases: XYYYY or XXXXY
        if (valueOccurrencesOf(firstValue) == 4)
            return Optional.of(fourOfAKind(firstValue));
        else if (valueOccurrencesOf(fifthValue) == 4)
            return Optional.of(fourOfAKind(fifthValue));
        else
            return Optional.empty();
    }

    private long valueOccurrencesOf(CardValue value) {
        return cards.stream().map(Card::getValue).filter(v -> v.equals(value)).count();
    }

    private Optional<Ranking> tryAsStraightFlush() {
        if (isStraight() && isFlush())
            return Optional.of(straightFlushWithHighCard(highestValue()));
        else
            return Optional.empty();
    }

    private boolean isFlush() {
        return cards.stream().map(Card::getSuit).distinct().count() == 1;
    }

    private boolean isStraight() {
        var distinctValues = getDistinctValues();
        if (distinctValues.size() != cards.size())
            return false; // at least one duplicate value

        var min = lowestValue();
        var max = highestValue();
        return (max.ordinal() - min.ordinal()) == cards.size() - 1;
    }

    private List<CardValue> getDistinctValues() {
        return cards.stream().map(Card::getValue).distinct().toList();
    }

    private CardValue highestValue() {
        //noinspection OptionalGetWithoutIsPresent
        return cards.stream().map(Card::getValue).max(naturalOrder()).get();
    }

    private CardValue lowestValue() {
        //noinspection OptionalGetWithoutIsPresent
        return cards.stream().map(Card::getValue).min(naturalOrder()).get();
    }
}