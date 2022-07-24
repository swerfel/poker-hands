package poker.hands.ranking;

import poker.hands.CardValue;

import java.util.ArrayList;
import java.util.List;

// convenience factory methods for better readability
public class Rankings {
    public static Ranking straightFlushWithHighCard(CardValue value) {
        return new Ranking(RankingCategory.STRAIGHT_FLUSH, List.of(value));
    }

    public static Ranking fourOfAKind(CardValue fourCardsValue) {
        return new Ranking(RankingCategory.FOUR_OF_A_KND, List.of(fourCardsValue));
    }

    public static Ranking fullHouseWithThree(CardValue threeCardsValue) {
        return new Ranking(RankingCategory.FULL_HOUSE, List.of(threeCardsValue));
    }

    public static Ranking flush(List<CardValue> valuesDescending) {
        return new Ranking(RankingCategory.FLUSH, valuesDescending);
    }

    public static Ranking straightWithHighest(CardValue value) {
        return new Ranking(RankingCategory.STRAIGHT, List.of(value));
    }

    public static Ranking threeOfAKind(CardValue threeCardsValue) {
        return new Ranking(RankingCategory.THREE_OF_A_KIND, List.of(threeCardsValue));
    }

    public static Ranking twoPairsWithPairsValuesAndRemainingCard(CardValue highestPair, CardValue lowestPair, CardValue remainingCard) {
        return new Ranking(RankingCategory.TWO_PAIRS, List.of(highestPair, lowestPair, remainingCard));
    }

    public static Ranking pairOfWithRemainingCards(CardValue pairValue, List<CardValue> remainingValuesDescending) {
        List<CardValue> values = new ArrayList<>();
        values.add(pairValue);
        values.addAll(remainingValuesDescending);
        return new Ranking(RankingCategory.PAIR, values);
    }

    public static Ranking highCard(List<CardValue> valuesDescending) {
        return new Ranking(RankingCategory.HIGH_CARD, valuesDescending);
    }
}
