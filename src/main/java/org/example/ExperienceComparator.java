package org.example;

import java.util.Comparator;

public class ExperienceComparator implements Comparator<Rating> {

    @Override
    public int compare(Rating o1, Rating o2) {
        return Integer.compare(o2.getUserExperience(), o1.getUserExperience());
    }
}
