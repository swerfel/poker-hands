package poker.hands.ranking;

import poker.hands.Card;
import poker.hands.CardValue;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Comparator.naturalOrder;
import static poker.hands.ranking.RankingCategory.*;

public class RankingBuilder {
    private final List<Card> cards;
    private final Card first;
    private final Card third;
    private final Card fifth;

    public RankingBuilder(Card first, Card second, Card third, Card fourth, Card fifth) {

        this.cards = Stream.of(first, second, third, fourth, fifth)
                .sorted(Comparator.comparing(Card::getValue))
                .toList();
        // select rank relevant cards after sorting, so we can work with assumptions that simplify the logic
        this.first = cards.get(0);
        this.third = cards.get(2);
        this.fifth = cards.get(4);
    }

    public Ranking build() {
        List<Supplier<Optional<Ranking>>> factories = List.of(
                this::tryAsStraightFlush,
                this::tryAsFourOfAKind,
                this:: tryAsFullHouse,
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
        return new Ranking(HIGH_CARD, List.of(highestValue()));
    }

    private Optional<Ranking> tryAsPair() {
        // assert higher ranks excluded
        // possible cases: ABCXX, ABXXC, AXXBC, XXABC
        if (valueOccurrencesOf(first) == 2)
            return Optional.of(new Ranking(PAIR, distinctValuesDescendingButFirst(first)));
        else if (valueOccurrencesOf(third) == 2)
            return Optional.of(new Ranking(PAIR, distinctValuesDescendingButFirst(third)));
        else if (valueOccurrencesOf(fifth) == 2)
            return Optional.of(new Ranking(PAIR, distinctValuesDescendingButFirst(fifth)));
        else
            return Optional.empty();
    }

    private List<CardValue> distinctValuesDescendingButFirst(Card card) {
        CardValue first = card.getValue();

        var otherValues = cards.stream().map(Card::getValue).distinct().filter(v -> !v.equals(first)).toList();

        List<CardValue> result = new ArrayList<>(otherValues);
        result.add(first);
        Collections.reverse(result);
        return result;
    }

    private Optional<Ranking> tryAsTwoPairs() {
        // assert higher ranks excluded
        // possible cases: YXXZZ, XXYZZ, XXZZY
        if (valueOccurrencesOf(first) == 1)
            return Optional.of(new Ranking(TWO_PAIRS, valuesOf(fifth, third, first)));
        else if (valueOccurrencesOf(third) == 1)
            return Optional.of(new Ranking(TWO_PAIRS, valuesOf(fifth, first, third)));
        else if (valueOccurrencesOf(fifth) == 1)
            return Optional.of(new Ranking(TWO_PAIRS, valuesOf(third, first, fifth)));
        else
            return Optional.empty();
    }

    private Optional<Ranking> tryAsThreeOfAKind() {
        // assert higher ranks excluded
        // possible cases: XXXYZ, XYYYZ or XYZZZ
        if (valueOccurrencesOf(first) == 3)
            return Optional.of(new Ranking(THREE_OF_A_KIND, valuesOf(first)));
        else if (valueOccurrencesOf(third) == 3)
            return Optional.of(new Ranking(THREE_OF_A_KIND, valuesOf(third)));
        else if (valueOccurrencesOf(fifth) == 3)
            return Optional.of(new Ranking(THREE_OF_A_KIND, valuesOf(fifth)));
        else
            return Optional.empty();
    }

    private Optional<Ranking> tryAsStraight() {
        if (isStraight())
            return Optional.of(new Ranking(STRAIGHT, List.of(highestValue())));
        else
            return Optional.empty();
    }


    private Optional<Ranking> tryAsFlush() {
        if (isFlush())
            return Optional.of(new Ranking(FLUSH, List.of(highestValue())));
        else
            return Optional.empty();
    }

    private Optional<Ranking> tryAsFullHouse() {
        // possible cases: XXYYY or XXXYY
        long firstOccurrence = valueOccurrencesOf(first);
        long lastOccurrence = valueOccurrencesOf(fifth);

        if (firstOccurrence == 2 && lastOccurrence == 3)
            return Optional.of(new Ranking(FULL_HOUSE, valuesOf(fifth)));
        else if (firstOccurrence == 3 && lastOccurrence == 2)
            return Optional.of(new Ranking(FULL_HOUSE, valuesOf(first)));
        else
            return Optional.empty();
    }

    private Optional<Ranking> tryAsFourOfAKind() {
        // possible cases: XYYYY or XXXXY
        if (valueOccurrencesOf(first) == 4)
            return Optional.of(new Ranking(FOUR_OF_A_KND, valuesOf(first)));
        else if (valueOccurrencesOf(fifth) == 4)
            return Optional.of(new Ranking(FOUR_OF_A_KND, valuesOf(fifth)));
        else
            return Optional.empty();
    }

    private long valueOccurrencesOf(Card card) {
        return cards.stream().map(Card::getValue).filter(v -> v.equals(card.getValue())).count();
    }

    private Optional<Ranking> tryAsStraightFlush() {
        if (isStraight() && isFlush())
            return Optional.of(new Ranking(STRAIGHT_FLUSH, List.of(highestValue())));
        else
            return Optional.empty();
    }

    private boolean isFlush() {
        return cards.stream().map(Card::getSuit).distinct().count() == 1;
    }

    private boolean isStraight() {
        var distinctValues = cards.stream().map(Card::getValue).distinct().toList();
        if (distinctValues.size() != cards.size())
            return false; // at least one duplicate value

        var min = lowestValue();
        var max = highestValue();
        return (max.ordinal() - min.ordinal()) == cards.size() - 1;
    }

    private List<CardValue> valuesOf(Card... cards) {
        return Arrays.stream(cards).map(Card::getValue).toList();
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
