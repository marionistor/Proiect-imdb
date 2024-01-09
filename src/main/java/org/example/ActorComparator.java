package org.example;

import java.util.Comparator;

public class ActorComparator implements Comparator<Actor> {

    @Override
    public int compare(Actor actor1, Actor actor2) {
        return actor1.getName().compareTo(actor2.getName());
    }
}
