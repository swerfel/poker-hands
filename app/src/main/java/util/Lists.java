package util;

import java.util.List;

public class Lists {
    // should be tested in general, omitted in this exercise because no focus on "business" logic
    public static <T extends Comparable<T>> int compareByElements(List<T> left, List<T> right) {
        for (int i = 0; i < Math.min(left.size(), right.size()); i++) {
            int step = left.get(i).compareTo(right.get(i));
            if (step != 0)
                return step;
        }
        return left.size()-right.size();
    }
}
