package poker.hands;

import poker.hands.ranking.Ranking;
import poker.hands.ranking.RankingBuilder;

import java.util.Arrays;

public class CardHand implements Comparable<CardHand> {
    private final Card[] cards;
    private final Ranking ranking;

    public CardHand(Card first, Card second, Card third, Card fourth, Card fifth) {
        this.cards = new Card[]{first, second, third, fourth, fifth};
        this.ranking = new RankingBuilder(first, second, third, fourth, fifth).build();
    }

    @Override
    public int compareTo(CardHand other) {
        return ranking.compareTo(other.ranking);
    }

    @Override
    public String toString() {
        return "CardHand(" +
                Arrays.toString(cards) +
                ')';
    }
}