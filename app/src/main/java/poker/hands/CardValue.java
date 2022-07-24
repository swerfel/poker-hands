package poker.hands;

import java.util.List;

public enum CardValue {
    N2, N3, N4, N5, N6, N7, N8, N9, N10, JACK, QUEEN, KING, ACE;

    @Override
    public String toString() {
        if (List.of(JACK, QUEEN, KING, ACE).contains(this))
            return this.name();
        return this.name().substring(1); // skip "N"
    }
}
