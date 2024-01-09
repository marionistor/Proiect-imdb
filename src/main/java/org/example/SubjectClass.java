package org.example;

import java.util.ArrayList;
import java.util.List;

public class SubjectClass implements Subject {
    private List<User> usersList;

    public SubjectClass() {
        usersList = new ArrayList<>();
    }

    @Override
    public void addObserver(User user) {
        if (!usersList.contains(user))
            usersList.add(user);
    }

    @Override
    public void removeObserver(User user) {
        usersList.remove(user);
    }

    @Override
    public void notifyObserver(String notification) {
        for(User user : usersList)
            user.update(notification);
    }
}
