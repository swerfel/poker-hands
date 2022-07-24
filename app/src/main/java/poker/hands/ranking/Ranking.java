package poker.hands.ranking;

import poker.hands.CardValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ranking{

    private final RankingCategory category;
    private final List<CardValue> valuesForSameTypeComparison;

    Ranking(RankingCategory category, List<CardValue> valuesForSameTypeComparison) {
        this.category = category;
        this.valuesForSameTypeComparison = List.copyOf(valuesForSameTypeComparison);
    }

    // convenience methods for better readability
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


    // generated methods:

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ranking ranking = (Ranking) o;
        return category == ranking.category && valuesForSameTypeComparison.equals(ranking.valuesForSameTypeComparison);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, valuesForSameTypeComparison);
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "category=" + category +
                ", valuesForSameTypeComparison=" + valuesForSameTypeComparison +
                '}';
    }
}
