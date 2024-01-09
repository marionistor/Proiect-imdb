package org.example;

import java.util.Comparator;

public class RatingComparator implements Comparator<Production> {

    public int compare(Production prod1, Production prod2) {
        return prod2.getAverageRating().compareTo(prod1.getAverageRating());
    }
}
