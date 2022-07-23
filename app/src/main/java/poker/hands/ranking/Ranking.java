package poker.hands.ranking;

import poker.hands.CardValue;

import java.util.List;

public class Ranking{

    private final RankingCategory category;
    private final List<CardValue> valuesForSameTypeComparison;

    Ranking (RankingCategory category, List<CardValue> valuesForSameTypeComparison) {
        this.category  = category;
        this.valuesForSameTypeComparison = List.copyOf(valuesForSameTypeComparison);
    }

}
