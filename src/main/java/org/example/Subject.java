package org.example;

public interface Subject {
    public void addObserver(User user);
    public void removeObserver(User user);
    public void notifyObserver(String notification);
}
