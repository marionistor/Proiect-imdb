package org.example;

public interface Subject {
    public void addObserver(User<?> user, Event event);
    public void removeObserver(User<?> user, Event event);
    public void notifyObserver(Event event, String type, String username, int rating, String ratingUsername);
}
