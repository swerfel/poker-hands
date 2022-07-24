package poker.hands.ranking;

import poker.hands.CardValue;
import util.Lists;

import java.util.List;
import java.util.Objects;

public class Ranking implements Comparable<Ranking> {

    private final RankingCategory category;
    private final List<CardValue> valuesForSameTypeComparison;

    Ranking(RankingCategory category, List<CardValue> valuesForSameTypeComparison) {
        this.category = category;
        this.valuesForSameTypeComparison = List.copyOf(valuesForSameTypeComparison);
    }

    @Override
    public int compareTo(Ranking o) {
        if (category.equals(o.category)) {
            assert valuesForSameTypeComparison.size() == o.valuesForSameTypeComparison.size();
            return Lists.compareByElements(valuesForSameTypeComparison, o.valuesForSameTypeComparison);
        } else
            return category.compareTo(o.category);
    }

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
